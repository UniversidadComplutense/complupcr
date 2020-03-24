package es.ucm.pcr.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InicioControlador {

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(InicioControlador.class);
	
	@RequestMapping(value = "/acceso", method = RequestMethod.GET)
	public String accesoGet(Model model, HttpServletRequest request,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			HttpSession session) {

		if (error != null) {
			model.addAttribute("mensajeError","Login incorrecto.");
		}
		if (logout != null) {
			model.addAttribute("mensajeLogout","Se ha desconectado correctamente.");
		}
		// Redirige a la pantalla de acceso
		return "acceso";
	}
	
	@RequestMapping(value = "/")
	public String indexGet(Model model, HttpSession session) {
		return "redirect:/inicio";
	}
	
	@RequestMapping(value ="/inicio")
	public ModelAndView inicioGet() {
		ModelAndView vista = new ModelAndView("inicio");
		return vista;
	}
	
	@RequestMapping(value = "/cerrarSession")
	public String cerrarSesion() {
		return "logout";
	}
	
	@RequestMapping(value="/tecnico", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO')")
	public String tecnico()
	{
		return "inicioTecnico";
	}
}
