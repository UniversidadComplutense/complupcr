package es.ucm.pcr.controladores;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.config.security.PcrUserDetailsService;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.servicios.UsuarioServicio;
import es.ucm.pcr.utilidades.Enviocorreo;

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
	private Enviocorreo envioCorreoImp;

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
		Usuario user = usuarioServicio.findByEmail(userEmail);
		if (user == null) {
			return "redirect:/regenerarContrasena?error";
		}
		String token = UUID.randomUUID().toString();
		usuarioServicio.createPasswordResetTokenForUser(user, token);
		SimpleMailMessage simpleMailMessage = envioCorreoImp.constructResetTokenEmail(envioCorreoImp.getAppUrl(request), token, user);
		envioCorreoImp.send(userEmail, simpleMailMessage.getSubject(), simpleMailMessage.getText(), null, "",
				"<p><strong>Este es un correo automático enviado por la aplicación PCR Covid-19.</strong></p>"
						+ "<p><strong>No responda a este mensaje.</strong></p>",
				"");
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
		User user = (User) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		usuarioServicio.cambiarContrasena(user.getUsername(), matchPassword);
		session.invalidate();
		return "redirect:/acceso";
	}

}
