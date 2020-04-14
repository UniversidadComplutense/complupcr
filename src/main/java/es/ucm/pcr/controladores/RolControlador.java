package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.servicios.RolServicio;

@Controller
public class RolControlador {
	
	@Autowired
	RolServicio rolServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los rols
	// Punto de entrada a la gestión de rols
	@RequestMapping(value="/gestor/listaRoles", method=RequestMethod.GET)
	public ModelAndView GestionRol(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionRol");
	
		// cargo todos los rols de BBDD
		List<BeanRol> listaRoles = new ArrayList<BeanRol>();
		for (Rol rol: rolServicio.findAll())
		{
			listaRoles.add(new BeanRol(rol.getId(), rol.getNombre(), true, "L"));
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaRoles);
		vista.addObject("listaRoles", listaRoles);
	
		return vista;
	}	
	
	// da de alta un nuevo rol
	@RequestMapping(value="/gestor/altaRol", method=RequestMethod.GET)
	public ModelAndView AltaRol(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaRol");
	
		BeanRol beanRol = new BeanRol();
		// le indicamos la acción a relizar: A alta de un rol
		beanRol.setAccion("A");
		vista.addObject("formBeanRol", beanRol);

		return vista;
	}
	
   // Alta/modificación de rol 
	@RequestMapping(value="/gestor/altaRol", method=RequestMethod.POST)	
	public ModelAndView grabarAltaRol ( @ModelAttribute("formBeanRol") BeanRol beanRol, HttpSession session) throws Exception {

		// Damos de alta nuevo rol
		if (beanRol.getAccion().equals("A"))
		{
			Rol rol = new Rol();
			rol.setNombre(beanRol.getNombre());
			rolServicio.save(rol);
		}
		// Modificamos rol existente, menos mail
		if (beanRol.getAccion().equals("M"))
		{
			// No todos los campos son modificables, el mail por ejemplo
			// va asociado a la pwd, y es único, por lo que no modificable
			// Buscamos el rol a modificar, y volcamos los datos recogidos por el formulario
			Optional<Rol> rol = rolServicio.findById(beanRol.getId());
			// añadimos campos del formulario
			rol.get().setNombre(beanRol.getNombre());
			rolServicio.save(rol.get());
		}

		// Volvemos a grabar mas roles
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaRoles",true));	
		return vista;		
	}	
	
	// Modificamos un rol
	@RequestMapping(value = "/gestor/editarRol", method = RequestMethod.GET)
	public ModelAndView editarRol(@RequestParam("idRol") Integer idRol) throws Exception {

		ModelAndView vista = new ModelAndView("VistaRol");
		
		// Busco el rol a modificar
		Optional<Rol> rol = rolServicio.findById(idRol);
		// cargo el beanRol con lo datos del rol a modificar
		BeanRol beanRol = new BeanRol();
		beanRol.setId(rol.get().getId());
		beanRol.setNombre(rol.get().getNombre());
		
		// le indicamos la acción a relizar: M modificación de un rol
		beanRol.setAccion("M");
		
		vista.addObject("formBeanRol", beanRol);
	
		return vista;
	}	
	
	@RequestMapping(value = "/gestor/borrarRol", method = RequestMethod.GET)
	public ModelAndView borrarRol(@RequestParam("idRol") Integer idRol) throws Exception {
		
		rolServicio.deleteById(idRol);
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaRoles",true));	
		return vista;
	}	

}
