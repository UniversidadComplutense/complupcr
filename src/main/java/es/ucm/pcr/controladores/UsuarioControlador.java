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
import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.RolRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;
import es.ucm.pcr.servicios.RolServicio;
import es.ucm.pcr.servicios.UsuarioServicio;

@Controller
public class UsuarioControlador {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	RolRepositorio rolRepositorio;
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Autowired
	RolServicio rolServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los usuarios
	// Punto de entrada a la gestión de usuarios
	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.GET)
	public ModelAndView GestionUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
	
		// cargo todos los usuarios de BBDD
		List<BeanUsuarioGestion> listaUsuarios = new ArrayList<BeanUsuarioGestion>();
		for (Usuario usuario: usuarioRepositorio.findAll())
		{
			listaUsuarios.add(new BeanUsuarioGestion(
								usuario.getId(), 
								usuario.getCentro(),
								usuario.getNombre(), 
					 			usuario.getApellido1(), 
					 			usuario.getApellido2(), 
					 			usuario.getEmail(), 
					 			usuario.getPassword(), 
					 			usuario.getIdLaboratorioVisavet(),
					 			usuario.getIdLaboratorioCentro(),
					 			usuario.getAsignadas(),
								usuario.getAcertadas(),
								usuario.getDocumentos(),
								usuario.getUsuarioMuestras(),
								usuario.getRols(),
								usuario.getHabilitado(),
								"L"
								));
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaUsuarios);
		vista.addObject("listaUsuarios", listaUsuarios);
	
		return vista;
	}
	
	// da de alta un nuevo usuario
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.GET)
	public ModelAndView AltaUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaUsuario");
	
		BeanUsuarioGestion beanUsuario = new BeanUsuarioGestion();
		// le indicamos la acción a relizar: A alta de un usuario
		beanUsuario.setAccion("A");
		vista.addObject("formBeanUsuario", beanUsuario);
		
		// Añadimos los roles en BBDD
		List<BeanRol> listaRoles = rolServicio.generarListaRoles();
		vista.addObject("listaRoles", listaRoles);
		
		boolean seleccionado = true;
		vista.addObject("seleccionado", seleccionado);
		
		return vista;
	}
	
   // Alta/modificación de usuario 
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.POST)	
	public ModelAndView grabarAltaUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuarioGestion beanUsuario, 
			 								@RequestParam(value = "roles" , required = false) int[] roles,
											HttpSession session) throws Exception {
		
		System.out.println("Habilitado: " + beanUsuario.getHabilitado());
		// Damos de alta nuevo usuario
		if (beanUsuario.getAccion().equals("A"))
		{
			usuarioRepositorio.save(usuarioServicio.mapeoBeanEntidadUsuarioAlta(beanUsuario, roles));
		}
		// Modificamos usuario existente, menos mail
		if (beanUsuario.getAccion().equals("M"))
		{
			// No todos los campos son modificables, el mail por ejemplo
			// va asociado a la pwd, y es único, por lo que no modificable
			// Buscamos el usuario a modificar, y volcamos los datos recogidos por el formulario
			Optional<Usuario> usuario = usuarioRepositorio.findById(beanUsuario.getId());
			// añadimos campos del formulario
			usuarioRepositorio.save(usuarioServicio.mapeoBeanEntidadUsuarioModificar(beanUsuario, usuario.get(), roles));
		}

		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;		
	}	
	
	// Modificamos un usuario
	@RequestMapping(value = "/gestor/editarUsuario", method = RequestMethod.GET)
	public ModelAndView editarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {

		ModelAndView vista = new ModelAndView("VistaUsuario");
		
		// Busco el usuario a modificar
		Optional<Usuario> usuario = usuarioRepositorio.findById(idUsuario);
		// cargo el beanUsuario con lo datos del usuario a modificar
		BeanUsuarioGestion beanUsuario = usuarioServicio.mapeoEntidadBeanUsuario(usuario.get());
		
		// le indicamos la acción a relizar: M modificación de un usuario
		beanUsuario.setAccion("M");
		
		// Añadimos los roles en BBDD
		List<BeanRol> listaRoles = rolServicio.generarListaRolesUsuario(usuario.get());
		vista.addObject("listaRoles", listaRoles);
		
		vista.addObject("formBeanUsuario", beanUsuario);
	
		return vista;
	}
		
	@RequestMapping(value = "/gestor/borrarUsuario", method = RequestMethod.GET)
	public ModelAndView borrarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {
		
		usuarioRepositorio.deleteById(idUsuario);
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;
	}
	

	
}
