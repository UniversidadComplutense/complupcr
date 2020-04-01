package es.ucm.pcr.controladores;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.config.security.PcrUserDetails;
import es.ucm.pcr.config.security.PcrUserDetailsService;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.servicios.UsuarioServicio;

/**
 * Controladores de inicio del portal
 * 
 * @author pmarrasant
 *
 */
@Controller
public class InicioControlador {

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(InicioControlador.class);

	@Autowired
	private UsuarioServicio usuarioServicio;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PcrUserDetailsService pcrUserDetailsService;
	
	@Autowired
	private Environment env;

	@RequestMapping(value = "/acceso", method = RequestMethod.GET)
	public String accesoGet(Model model, HttpServletRequest request,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpSession session) {

		if (error != null) {
			model.addAttribute("mensajeError", "Login incorrecto.");
		}
		if (logout != null) {
			model.addAttribute("mensajeLogout", "Se ha desconectado correctamente.");
		}
		// Redirige a la pantalla de acceso
		return "acceso";
	}

	@RequestMapping(value = "/")
	public String indexGet(Model model, HttpSession session) {
		return "redirect:/inicio";
	}

	@RequestMapping(value = "/inicio")
	public ModelAndView inicioGet() {
		ModelAndView vista = new ModelAndView("inicio");
		return vista;
	}

	@RequestMapping(value = "/cerrarSesion")
	public String cerrarSesion() {
		return "logout";
	}

	@RequestMapping(value = "/regenerarContrasena", method = RequestMethod.GET)
	public String regenerarContrasena(Model model, HttpServletRequest request,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "enviado", required = false) String enviado) {
		if (error != null) {
			model.addAttribute("mensajeError", "No se han encontrado usuarios con ese email.");
		}
		if (enviado != null) {
			model.addAttribute("mensajeEnviado",
					"Gracias. Consulta tu bandeja de entrada para restablecer tu contraseña.");
		}
		return "olvidoContrasena";
	}

	@RequestMapping(value = "/regenerarContrasena", method = RequestMethod.POST)
	public String regenerarContrasenaPost(HttpServletRequest request, @RequestParam("email") String userEmail) {
		Usuario user = usuarioServicio.buscarUsuarioPorEmail(userEmail);
		if (user == null) {
			return "redirect:/regenerarContrasena?error";
		}
		String token = UUID.randomUUID().toString();
		usuarioServicio.createPasswordResetTokenForUser(user, token);
		mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));
		return "redirect:/regenerarContrasena?enviado";
	}

	@RequestMapping(value = "/modificarContrasena", method = RequestMethod.GET)
	public String modificarContrasena(Model model, @RequestParam("id") long id, @RequestParam("token") String token,
			HttpSession session) {
		String result = pcrUserDetailsService.validarPasswordResetToken(id, token);
		if (result != null) {
			session.invalidate();
			model.addAttribute("mensajeError", "El enlace esta caducado o es incorrecto.");
			return "redirect:/acceso?error";
		}
		return "actualizarContrasena";
	}

	@RequestMapping(value = "/modificarContrasena", method = RequestMethod.POST)
	public String modificarContrasenaPost(HttpServletRequest request, @RequestParam("newPassword") String matchPassword,
			HttpSession session) {
		PcrUserDetails pcrUserDetails = (PcrUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		usuarioServicio.cambiarContrasena(pcrUserDetails.getUser().getEmail(), matchPassword);
		session.invalidate();
		return "redirect:/acceso";
	}

	// ============== Metodos privados ============
	
	@Scheduled(cron ="${cron.expression}")
	public void scheduleEnvioMailInicio() {
		List<Usuario> userList = usuarioServicio.buscarUsuarioInhabilitados();
		for (Usuario user :userList) {
			mailSender.send(constructWelcomeEmail(env.getProperty("app.url"),user));
			user.setHabilitado("E");
			usuarioServicio.guardar(user);
		}
		
	}

	private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Usuario user) {
		String url = contextPath + "/modificarContrasena?id=" + user.getId() + "&token=" + token;
		String message = "Este es un correo automático enviado por la aplicación COVID-19.\n"
				+ "No contestes a este mensaje.\r\n\n\n"
				+ "Puede realizar el cambio de contraseña a través del siguente enlace:";
		return constructEmail("Restablecer contraseña COVID-19",
				message + " \r\n\n" + url + " \r\n\n\n Un cordial saludo.", user);
	}

	private SimpleMailMessage constructWelcomeEmail(String contextPath, Usuario user) {
		String url = contextPath + "/regenerarContrasena";
		String message = "Este es un correo automático enviado por la aplicación COVID-19.\n"
				+ "No contestes a este mensaje.\r\n\n\n"
				+ "Bien venido "+user.getNombre()+",\n\nHa sido dado de alta en la aplicación COVID-19.\r\n"+
				"Para poder acceder debe solicitar el cambio de contraseña, indicando su e-mail ("+user.getEmail()+") a través del siguente enlace:";
		return constructEmail("Bien venido COVID-19",
				message + " \r\n\n" + url + " \r\n\n\n Un cordial saludo.", user);
	}
	private SimpleMailMessage constructEmail(String subject, String body, Usuario user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom("no-reply@ucm.es");
		return email;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
