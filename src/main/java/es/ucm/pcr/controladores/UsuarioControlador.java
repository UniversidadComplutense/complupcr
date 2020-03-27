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
import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Controller
public class UsuarioControlador {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	
//	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.GET)
//	public ModelAndView GestionUsuario(HttpSession session) throws Exception {
//		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
//	
//		List<BeanUsuario> listaUsuarios = new ArrayList<BeanUsuario>();
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "bbbbbbbb", "bbbbbbbbbbbb", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "dddddddd", "dddddddddddd", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//
//		listaUsuarios.add(new BeanUsuario(0, "ccccccccc", "ccccccccccc", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "Fernando", "de las Heras", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		listaUsuarios.add(new BeanUsuario(0, "aaaAAAAA", "aaaaaaaaaaaa", "martin", "999", "666", "fherasm@ucm,es", "I", "rol"));
//		
//		Collections.sort(listaUsuarios);
//		vista.addObject("listaUsuarios", listaUsuarios);
//	
//		return vista;
//	}
	
	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.GET)
	public ModelAndView GestionUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
	
		List<BeanUsuario> listaUsuarios = new ArrayList<BeanUsuario>();
		for (Usuario usuario: usuarioRepositorio.findAll())
		{
			listaUsuarios.add(new BeanUsuario(usuario.getId(), usuario.getNombre(), 
					 						  usuario.getApellido1(), usuario.getApellido2(), 
					 						  usuario.getEmail(), usuario.getEmail(), 
					 						  usuario.getEmail(), "I", "rol", "L"));
		}
		Collections.sort(listaUsuarios);
		vista.addObject("listaUsuarios", listaUsuarios);
	
		return vista;
	}
	
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.GET)
	public ModelAndView AltaUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaUsuario");
	
		BeanUsuario beanUsuario = new BeanUsuario();
		// le indicamos la acción a relizar: A alta de un usuario
		beanUsuario.setAccion("A");
		vista.addObject("formBeanUsuario", beanUsuario);

		return vista;
	}
	
   // Alta de centro 
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.POST)	
	public ModelAndView grabarAltaUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuario beanUsuario, HttpSession session) throws Exception {

		System.out.println("Accion a realizar: "+ beanUsuario.getAccion());
		
		if (beanUsuario.getAccion().equals("A"))
		{
			Usuario usuario = new Usuario();
			usuario.setApellido1(beanUsuario.getAp1());
			usuario.setApellido2(beanUsuario.getAp2());
			usuario.setNombre(beanUsuario.getNom());
			usuario.setEmail(beanUsuario.getMail());
			usuario.setPassword(beanUsuario.getMail());
			usuarioRepositorio.save(usuario);
		}
		
		if (beanUsuario.getAccion().equals("M"))
		{
			System.out.println("Voy a modificar: " );
			System.out.println("Voy a modificar: " + beanUsuario.toString());
			
			// No todos los campos son modificables, el mail por ejemplo
			// va asociado a la pwd, y es único, por lo que no modificable
			// Buscamos el usuario a modificar, y volcamos los datos recogidos por el formulario
			Optional<Usuario> usuario = usuarioRepositorio.findById(beanUsuario.getId());
			
			usuario.get().setApellido1(beanUsuario.getAp1());
			usuario.get().setApellido2(beanUsuario.getAp2());
			usuario.get().setNombre(beanUsuario.getNom());
			usuarioRepositorio.save(usuario.get());
		}

		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;
		
	}	
	
	
	@RequestMapping(value = "/gestor/editarUsuario", method = RequestMethod.GET)
	public ModelAndView editarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {

		ModelAndView vista = new ModelAndView("VistaUsuario");
		
		Optional<Usuario> usuario = usuarioRepositorio.findById(idUsuario);
		
		BeanUsuario beanUsuario = new BeanUsuario();
		beanUsuario.setId(usuario.get().getId());
		beanUsuario.setAp1(usuario.get().getApellido1());
		beanUsuario.setAp2(usuario.get().getApellido2());
		beanUsuario.setNom(usuario.get().getNombre());
		
		// le indicamos la acción a relizar: M modificación de un usuario
		beanUsuario.setAccion("M");
		
		vista.addObject("formBeanUsuario", beanUsuario);
	
		return vista;
	}
	
//	@RequestMapping(value="/gestor/editarUsuario", method=RequestMethod.POST)	
//	public ModelAndView modificarUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuario beanUsuario, HttpSession session) throws Exception {
//
//		// No todos los campos son modificables, el mail por ejemplo
//		// va asociado a la pwd, y es único, por lo que no modificable
//		// Buscamos el usuario a modificar, y volcamos los datos recogidos por el formulario
//		Optional<Usuario> usuario = usuarioRepositorio.findById(beanUsuario.getId());
//		
//		usuario.get().setApellido1(beanUsuario.getAp1());
//		usuario.get().setApellido2(beanUsuario.getAp2());
//		usuario.get().setNombre(beanUsuario.getNom());
//	
//		System.out.println("usuario a modificar; " + usuario.toString());
//		
////		usuarioRepositorio.save(usuario.get());
//		
//		// Volvemos a grabar mas centros
//		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
//		return vista;
//		
//	}	
	
	@RequestMapping(value = "/gestor/borrarUsuario", method = RequestMethod.GET)
	public ModelAndView borrarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {

//		System.out.println("Usuario a borrar: " + idUsuario);
		
		usuarioRepositorio.deleteById(idUsuario);
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;
	}
	

	
}
