package es.ucm.pcr.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.bean.BeanAnalista;

@Controller
public class AnalistaControlador {
	
	@RequestMapping(value="AltaAnalista", method=RequestMethod.GET)
	public ModelAndView AltaAnalista(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaAnalista");
	
		BeanAnalista beanAnalista = new BeanAnalista();
		
		vista.addObject("formBeanAnalista", beanAnalista);
		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="AltaAnalista", method=RequestMethod.POST)	
	public ModelAndView grabarAltaAnalista ( @ModelAttribute("formBeanAnalista") BeanAnalista beanAnalista, HttpSession session) throws Exception {

		System.out.println("Analista a grabar: " + beanAnalista.toString());
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("AltaAnalista",true));	
		return vista;
		
	}		

}
