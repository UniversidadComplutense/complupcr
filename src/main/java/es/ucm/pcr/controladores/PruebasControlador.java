package es.ucm.pcr.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.utilidades.Enviocorreo;


@Controller
@RequestMapping(value="/gestor/prueba")
public class PruebasControlador {
	
	@Autowired
	private Enviocorreo envioCorreoImp;	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/correo", method=RequestMethod.GET)
	public ModelAndView PruebaCorreo(HttpSession session) throws Exception {
		
		/*
		 * String txtAviso = String.format("En el lote con codnum: %s" +
		 * " NO firmado, existe una preinscripcion con codNum %s con PropuestaAutorización que NO esta pendiente de sección"
		 * , beanDetalleLote.getCodNum(), detalleLote.getCodNumPreinscripcion());
		 */
		String txtAviso = "el paciente pepe ha resultado Negativo";
		System.out.println(txtAviso);
		
		envioCorreoImp.send("dades@ucm.es", "Notificacion",
		 txtAviso, null, "","","");   //enviamos el mensaje sin adjuntos, ni pie, ni cabecera ni convocatoria
	
		ModelAndView vista = new ModelAndView("VistaCentro");
	
		BeanCentro beanCentro = new BeanCentro();
		
		vista.addObject("formBeanCentro", beanCentro);
		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="/alta", method=RequestMethod.POST)	
	public ModelAndView grabarAltaCentro ( @ModelAttribute("formBeanCentro") BeanCentro beanCentro, HttpSession session) throws Exception {

		System.out.println("Centro a grabar: " + beanCentro.toString());
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("AltaCentro",true));	
		return vista;
		
	}

}
