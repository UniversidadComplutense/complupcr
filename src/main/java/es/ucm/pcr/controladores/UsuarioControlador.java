package es.ucm.pcr.controladores;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanBusquedaUsuario;
import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.servicios.CentroServicio;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.LaboratorioVisavetServicio;
import es.ucm.pcr.servicios.RolServicio;
import es.ucm.pcr.servicios.UsuarioServicio;
import es.ucm.pcr.utilidades.Utilidades;

@Controller
public class UsuarioControlador {
	
	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Autowired
	RolServicio rolServicio;
	
	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;
	
	@Autowired
	CentroServicio centroServicio;
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "apellido1", "apellido2", "nombre");
	
	@RequestMapping(value = "/gestor/lista", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");

		BeanBusquedaUsuario beanBusquedaUsuario = new BeanBusquedaUsuario();
		vista.addObject("formBeanBusquedaUsuario", beanBusquedaUsuario);
		return vista;
	}
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los usuarios
	// Punto de entrada a la gestión de usuarios
	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView GestionUsuario(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {
		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
		
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			session.removeAttribute("mensajeError");
		}
		// cargo todos los usuarios de BBDD
		
		BeanBusquedaUsuario beanBusqueda = (BeanBusquedaUsuario) session.getAttribute("formBeanBusquedaUsuario");
		beanBusqueda = beanBusqueda != null ? beanBusqueda : new BeanBusquedaUsuario();
		
		Page<BeanUsuarioGestion> usuariosPage = usuarioServicio.findUsuarioByParam(beanBusqueda, PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION_USUARIO, ORDENACION));
		vista.addObject("listaUsuarios", usuariosPage.getContent());
		PaginadorBean paginadorBean = new PaginadorBean(usuariosPage.getTotalPages(), currentPage, usuariosPage.getTotalElements(), "/gestor/listaUsuarios");		
		vista.addObject("paginadorBean", paginadorBean);
		session.setAttribute("paginaActual", currentPage);
		
		// Bean para la busqueda de usuario
		vista.addObject("formBeanBusquedaUsuario", beanBusqueda);
		
		return vista;
	}
	
	
	//	Muestra una lista de usuarios según criterio de busqueda
	@RequestMapping(value="/gestor/listaUsuarios", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView busquedaUsuario(
			@ModelAttribute("formBeanBusquedaUsuario") BeanBusquedaUsuario beanBusquedaUsuario, 
			HttpSession session) throws Exception {
		
		ModelAndView vista = new ModelAndView("VistaGestionUsuario");
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			session.removeAttribute("mensajeError");
		}
		// cargo todos los usuarios de BBDD por el criterio de busqueda
		
		session.setAttribute("formBeanBusquedaUsuario", beanBusquedaUsuario);
		
		Integer currentPage = 1;
		Page<BeanUsuarioGestion> usuariosPage = usuarioServicio.findUsuarioByParam(beanBusquedaUsuario, PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION_USUARIO, ORDENACION));
		session.setAttribute("paginaActual", currentPage);
		
		vista.addObject("listaUsuarios", usuariosPage.getContent());
		PaginadorBean paginadorBean = new PaginadorBean(usuariosPage.getTotalPages(), currentPage, usuariosPage.getTotalElements(), "/gestor/listaUsuarios");		
		vista.addObject("paginadorBean", paginadorBean);
		
		return vista;
	}
	
	
	// da de alta un nuevo usuario
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView AltaUsuario(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaUsuario");
		BeanUsuarioGestion beanUsuario = new BeanUsuarioGestion();
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			beanUsuario = (BeanUsuarioGestion) session.getAttribute("beanUsuario");
			session.removeAttribute("mensajeError");
			session.removeAttribute("beanUsuario");
		}
		// le indicamos la acción a relizar: A alta de un usuario
		beanUsuario.setAccion("A");
		// por defecto, en un alta el usuario se activa
		beanUsuario.setHabilitado("A");
		vista.addObject("formBeanUsuario", beanUsuario);
		
		// Añadimos los roles en BBDD
		List<BeanRol> listaRoles = rolServicio.generarListaRoles();
		vista.addObject("listaRoles", listaRoles);
		
		// añadimos los laboratorios Visavet
		Map<Integer,String> mapaLaboratoriosVisavet = laboratorioVisavetServicio.mapaLaboratoriosVisavet(laboratorioVisavetServicio.listaLaboratoriosVisavetOrdenada());
		vista.addObject("mapaLaboratoriosVisavet", mapaLaboratoriosVisavet);
		
		// añadimos los laboratorios Centro
		Map<Integer,String> mapaLaboratoriosCentro = laboratorioCentroServicio.mapaLaboratoriosCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
		vista.addObject("mapaLaboratoriosCentro", mapaLaboratoriosCentro);
		
		// añadimos los centros
		Map<Integer,String> mapaCentros = centroServicio.mapaCentros(centroServicio.listaCentrosOrdenada());
		vista.addObject("mapaCentros", mapaCentros);
		
		return vista;
	}
	
   // Alta/modificación de usuario 
	@RequestMapping(value="/gestor/altaUsuario", method=RequestMethod.POST)	
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView grabarAltaUsuario ( @ModelAttribute("formBeanUsuario") BeanUsuarioGestion beanUsuario, 
			 								@RequestParam(value = "roles" , required = false) int[] roles,
											HttpSession session) throws Exception {
		String mensaje = null;
		// Damos de alta nuevo usuario
		if (beanUsuario.getAccion().equals("A"))
		{
			try {
			usuarioServicio.save(usuarioServicio.mapeoBeanEntidadUsuarioAlta(beanUsuario, roles));
			} catch (DataIntegrityViolationException e) {
				mensaje = "Ya existe un usuario con ese email.";
				session.setAttribute("mensajeError", mensaje);
				session.setAttribute("beanUsuario", beanUsuario);
				ModelAndView vista = new ModelAndView(new RedirectView("/gestor/altaUsuario",true));
				return vista;
			}
		}
		// Modificamos usuario existente, menos mail
		if (beanUsuario.getAccion().equals("M"))
		{
			// No todos los campos son modificables, el mail por ejemplo
			// va asociado a la pwd, y es único, por lo que no modificable
			// Buscamos el usuario a modificar, y volcamos los datos recogidos por el formulario
			Optional<Usuario> usuario = usuarioServicio.findById(beanUsuario.getId());
			// añadimos campos del formulario
			usuarioServicio.save(usuarioServicio.mapeoBeanEntidadUsuarioModificar(beanUsuario, usuario.get(), roles));
		}

		// Volvemos a centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;		
	}	
	
	// Modificamos un usuario
	@RequestMapping(value = "/gestor/editarUsuario", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView editarUsuario(@RequestParam("idUsuario") Integer idUsuario) throws Exception {

		ModelAndView vista = new ModelAndView("VistaUsuario");
		
		// Busco el usuario a modificar
		Optional<Usuario> usuario = usuarioServicio.findById(idUsuario);
		// cargo el beanUsuario con lo datos del usuario a modificar
		BeanUsuarioGestion beanUsuario = usuarioServicio.mapeoEntidadBeanUsuario(usuario.get());
		
		// le indicamos la acción a relizar: M modificación de un usuario
		beanUsuario.setAccion("M");
		
		// Añadimos los roles en BBDD
		List<BeanRol> listaRoles = rolServicio.generarListaRolesUsuario(usuario.get());
		vista.addObject("listaRoles", listaRoles);
		
		// añadimos los laboratorios Visavet
		Map<Integer,String> mapaLaboratoriosVisavet = laboratorioVisavetServicio.mapaLaboratoriosVisavet(laboratorioVisavetServicio.listaLaboratoriosVisavetOrdenada());
		vista.addObject("mapaLaboratoriosVisavet", mapaLaboratoriosVisavet);
		
		// añadimos los laboratorios Centro
		Map<Integer,String> mapaLaboratoriosCentro = laboratorioCentroServicio.mapaLaboratoriosCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
		vista.addObject("mapaLaboratoriosCentro", mapaLaboratoriosCentro);
		
		// añadimos los centros
		Map<Integer,String> mapaCentros = centroServicio.mapaCentros(centroServicio.listaCentrosOrdenada());
		vista.addObject("mapaCentros", mapaCentros);
		
		vista.addObject("formBeanUsuario", beanUsuario);
	
		return vista;
	}
		
	@RequestMapping(value = "/gestor/borrarUsuario", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView borrarUsuario(@RequestParam("idUsuario") Integer idUsuario, HttpSession session) throws Exception {
		try {
		usuarioServicio.deleteById(idUsuario);
		} catch (DataIntegrityViolationException e) {
			String mensaje = "No se puede borrar el usuario " + usuarioServicio.findById(idUsuario).get().getEmail() + " porque tiene información asociada.";
			session.setAttribute("mensajeError", mensaje);
		}
			
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaUsuarios",true));	
		return vista;
	}	
}