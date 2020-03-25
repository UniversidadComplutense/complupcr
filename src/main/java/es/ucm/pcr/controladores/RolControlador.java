package es.ucm.pcr.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanRol;

@Controller
public class RolControlador {
	
	@RequestMapping(value="AltaRol", method=RequestMethod.GET)
	public ModelAndView AltaRol(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaRol");
	
		BeanRol beanRol = new BeanRol();
		
		vista.addObject("formBeanRol", beanRol);
		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="AltaRol", method=RequestMethod.POST)	
	public ModelAndView grabarAltaRol ( @ModelAttribute("formBeanRol") BeanRol beanRol, HttpSession session) throws Exception {

		System.out.println("Rol a grabar: " + beanRol.toString());
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("AltaRol",true));	
		return vista;
		
	}		

}
