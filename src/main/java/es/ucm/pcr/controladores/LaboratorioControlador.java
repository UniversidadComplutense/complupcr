package es.ucm.pcr.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanLaboratorio;

@Controller
public class LaboratorioControlador {
	
	@RequestMapping(value="AltaLaboratorio", method=RequestMethod.GET)
	public ModelAndView AltaLaboratorio(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLaboratorio");
	
		BeanLaboratorio beanLaboratorio = new BeanLaboratorio();
		
		vista.addObject("formBeanLaboratorio", beanLaboratorio);
		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="AltaLaboratorio", method=RequestMethod.POST)	
	public ModelAndView grabarAltaLaboratorio ( @ModelAttribute("formBeanLaboratorio") BeanLaboratorio beanLaboratorio, HttpSession session) throws Exception {

		System.out.println("Laboratorio a grabar: " + beanLaboratorio.toString());
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("AltaLaboratorio",true));	
		return vista;
		
	}	

}
