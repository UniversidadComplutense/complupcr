package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.servicios.MuestraServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.validadores.MuestraValidador;

@Controller
@RequestMapping(value = "/centroSalud")
public class MuestraControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	// TODO - LOG, TRAZAR SERVICIOS
	// TODO - MUESTRA - ACCIONES, ORDENACION, PAGINACION
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "etiqueta");
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private MuestraServicio muestraServicio;
	
	@Autowired
	private MuestraValidador validadorMuestra;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@InitBinder("beanMuestra")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session) throws Exception {  
		binder.setValidator(validadorMuestra);
	}
	
	@RequestMapping(value="/muestra", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");
	
		MuestraBusquedaBean beanBusqueda = new MuestraBusquedaBean();
		
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value="/muestra/list", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscar(HttpSession session, @ModelAttribute MuestraBusquedaBean beanBusqueda) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");
		
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		Page<MuestraListadoBean> muestrasPage = muestraServicio.findMuestraByParam(beanBusqueda, PageRequest.of(0, Integer.MAX_VALUE, ORDENACION));
		
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		vista.addObject("listadoMuestras", muestrasPage.getContent());
		return vista;
	}
	
	@RequestMapping(value="/muestra/nueva", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView nueva(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
	
		MuestraCentroBean beanMuestra = new MuestraCentroBean();
		beanMuestra.setFechaEntrada(new Date());
		
		vista.addObject("editable", muestraEditable(beanMuestra));		
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView consulta(HttpSession session, @PathVariable Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
		
		MuestraCentroBean beanMuestra = muestraServicio.findById(id);
	
		// TODO - consulta/modificacion
		//vista.addObject("editable", muestraEditable(beanMuestra));
		vista.addObject("editable", false);
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/modificar", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView modificar(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
		
		MuestraCentroBean beanMuestra = muestraServicio.findById(id);
	
		vista.addObject("editable", muestraEditable(beanMuestra));
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/guardar", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView guardar(@Valid @ModelAttribute("beanMuestra") MuestraCentroBean beanMuestra, BindingResult result) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
	
		if (result.hasErrors()) {
			vista.addObject("beanMuestra", beanMuestra);
			return vista;			
		} else {
			beanMuestra.setIdCentro(sesionServicio.getCentro().getId());
			MuestraCentroBean muestra = muestraServicio.guardar(beanMuestra);
			// TODO - VER A DONDE REDIRIGIR AL GUARDAR LOTE 
			ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + muestra.getId(), true));
			return respuesta;
		}
	}
	
	@RequestMapping(value="/notificarTelefono/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView notificarTelefono(HttpSession session, @PathVariable Integer id) throws Exception {
		// TODO - llamada servicio rellenar fecha notificacion
		
		// redirige a la consulta
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}
	
	@RequestMapping(value="/notificarCorreo/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView notificarCorreo(HttpSession session, @PathVariable Integer id) throws Exception {
		// TODO - llamada servicio envio correo y rellenar fecha notificacion		
		
		// redirige a la consulta		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}
	
	/**
	 * TODO - ESTADOS MUESTRA
	 * La muestra es editable mientras tenga estado pendiente
	 * No se puede editar si esta en un lote enviado o esta resuelta
	 * Si esta resuelta se muestran acciones de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraEditable(MuestraCentroBean beanMuestra) {
		return (beanMuestra.getId() == null || (beanMuestra.getId() != null 
					&& (beanMuestra.getEstado().getEstado().getCodNum() == Estado.MUESTRA_INICIADA.getCodNum() 
						|| beanMuestra.getEstado().getEstado().getCodNum() == Estado.MUESTRA_ASIGNADA_LOTE.getCodNum())));	
	}
	
	/**
	 * La muestra es noficable si ya se ha resuelto y no tiene fecha de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraNotificable(MuestraCentroBean beanMuestra) {
		// TODO - PENDIETE MUESTRA NOTIFICABLE
		return beanMuestra.getId() != null && beanMuestra.getResultado() != null && beanMuestra.getResultado().equals("Resuelta") && beanMuestra.getFechaNotificacion() == null;
	}
	
}
