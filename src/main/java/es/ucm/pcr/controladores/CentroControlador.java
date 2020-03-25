package es.ucm.pcr.controladores;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.bean.BeanCentro;


@Controller
public class CentroControlador {
	
	@RequestMapping(value="AltaCentro", method=RequestMethod.GET)
	public ModelAndView AltaCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaCentros");
	
		BeanCentro beanCentro = new BeanCentro();
		
		vista.addObject("formBeanCentro", beanCentro);
		return vista;
	}
	
	   // Alta de centro 
		@RequestMapping(value="AltaCentro", method=RequestMethod.POST)	
		public ModelAndView grabarAltaCentro ( @ModelAttribute("formBeanCentro") BeanCentro beanCentro, HttpSession session) throws Exception {
	
			System.out.println("Centro a grabar: " + beanCentro.toString());
			
			// Volvemos a grabar mas centros
			ModelAndView vista = new ModelAndView(new RedirectView("AltaCentro",true));	
			return vista;
			
		}

}
