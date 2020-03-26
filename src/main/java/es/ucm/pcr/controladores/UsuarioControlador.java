package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.beans.BeanUsuario;

@Controller
public class UsuarioControlador {
	
	@RequestMapping(value="AltaUsuario", method=RequestMethod.GET)
	public ModelAndView AltaUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaUsuario");
	
		BeanUsuario beanUsuario = new BeanUsuario();
		
		vista.addObject("formBeanUsuario", beanUsuario);
		
//		// Añado los roles
//		List<BeanRol> listaRoles = new ArrayList<BeanRol>();
//		listaRoles.add(new BeanRol(1, "Analista"));
//		listaRoles.add(new BeanRol(2, "Supersivor"));
//		listaRoles.add(new BeanRol(3, "Técnicp laboratorio"));
//		listaRoles.add(new BeanRol(4, "Auditor"));		
//		vista.addObject("listaRoles", listaRoles);
		
		List<String> listaRoles2 = new ArrayList<String>();
		listaRoles2.add("Analista");
		listaRoles2.add("Programador");
		listaRoles2.add("Jefe proyecto");
		vista.addObject("listaRoles2", listaRoles2);
		
		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="AltaUsuario", method=RequestMethod.POST)	
	public ModelAndView grabarAltaUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuario beanUsuario, HttpSession session) throws Exception {

		System.out.println("Usuario a grabar: " + beanUsuario.toString());
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("AltaUsuario",true));	
		return vista;
		
	}		
	

}
