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

import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.UsuarioRepositorio;
import es.ucm.pcr.servicios.UsuarioServicio;

@Controller
public class UsuarioControlador {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los usuarios
	// Punto de entrada a la gestión de usuarios
	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.GET)
	public ModelAndView GestionUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
	
		// cargo todos los usuarios de BBDD
		List<BeanUsuario> listaUsuarios = new ArrayList<BeanUsuario>();
		for (Usuario usuario: usuarioRepositorio.findAll())
		{
			listaUsuarios.add(new BeanUsuario(
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
	
		BeanUsuario beanUsuario = new BeanUsuario();
		// le indicamos la acción a relizar: A alta de un usuario
		beanUsuario.setAccion("A");
		vista.addObject("formBeanUsuario", beanUsuario);

		return vista;
	}
	
   // Alta/modificación de usuario 
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.POST)	
	public ModelAndView grabarAltaUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuario beanUsuario, HttpSession session) throws Exception {

		// Damos de alta nuevo usuario
		if (beanUsuario.getAccion().equals("A"))
		{
//			Usuario usuario = new Usuario();
//			usuario.setApellido1(beanUsuario.getAp1());
//			usuario.setApellido2(beanUsuario.getAp2());
//			usuario.setNombre(beanUsuario.getNom());
//			usuario.setEmail(beanUsuario.getMail());
//			usuario.setPassword(beanUsuario.getMail());
//			usuarioRepositorio.save(usuario);
			usuarioRepositorio.save(usuarioServicio.mapeoBeanEntidadUsuarioAlta(beanUsuario));
		}
		// Modificamos usuario existente, menos mail
		if (beanUsuario.getAccion().equals("M"))
		{
			// No todos los campos son modificables, el mail por ejemplo
			// va asociado a la pwd, y es único, por lo que no modificable
			// Buscamos el usuario a modificar, y volcamos los datos recogidos por el formulario
			Optional<Usuario> usuario = usuarioRepositorio.findById(beanUsuario.getId());
			// añadimos campos del formulario
//			usuario.get().setApellido1(beanUsuario.getAp1());
//			usuario.get().setApellido2(beanUsuario.getAp2());
//			usuario.get().setNombre(beanUsuario.getNom());
//			usuarioRepositorio.save(usuario.get());
			usuarioRepositorio.save(usuarioServicio.mapeoBeanEntidadUsuarioModificar(beanUsuario, usuario.get()));
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
//		BeanUsuario beanUsuario = new BeanUsuario();
//		beanUsuario.setId(usuario.get().getId());
//		beanUsuario.setAp1(usuario.get().getApellido1());
//		beanUsuario.setAp2(usuario.get().getApellido2());
//		beanUsuario.setNom(usuario.get().getNombre());
		BeanUsuario beanUsuario = usuarioServicio.mapeoEntidadBeanUsuario(usuario.get());
		
		// le indicamos la acción a relizar: M modificación de un usuario
		beanUsuario.setAccion("M");
		
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
