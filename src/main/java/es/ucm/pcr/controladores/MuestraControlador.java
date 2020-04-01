package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.servicios.LoteServicio;
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
	private LoteServicio loteServicio;
	
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
	
	@ModelAttribute("listaLotes")
    public List<LoteListadoBean> lotes() {
        return loteServicio.findLoteByEstados(sesionServicio.getCentro().getId(), BeanEstado.getEstadosLotesDisponiblesCentro());
    }	
	
	@RequestMapping(value="/muestra", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");
	
		MuestraBusquedaBean beanBusqueda = new MuestraBusquedaBean();
		
		addListsToView(vista);
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value="/muestra/list", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView buscar(HttpSession session, @ModelAttribute MuestraBusquedaBean beanBusqueda) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");
		
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		Page<MuestraListadoBean> muestrasPage = muestraServicio.findMuestraByParam(beanBusqueda, PageRequest.of(0, Integer.MAX_VALUE, ORDENACION));
		
		addListsToView(vista);
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		vista.addObject("listadoMuestras", muestrasPage.getContent());
		return vista;
	}
	
	@RequestMapping(value="/muestra/nueva", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView nueva(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
	
		MuestraCentroBean beanMuestra = new MuestraCentroBean();
		beanMuestra.setFechaEntrada(new Date());
		
		vista.addObject("editable", muestraEditable(beanMuestra));		
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
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
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView modificar(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
		
		MuestraCentroBean beanMuestra = muestraServicio.findById(id);
	
		vista.addObject("editable", muestraEditable(beanMuestra));
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/guardar", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
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
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView notificarTelefono(HttpSession session, @PathVariable Integer id) throws Exception {
		
		muestraServicio.actualizarNotificacionMuestra(id, false);
		
		// redirige a la consulta
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}
	
	@RequestMapping(value="/notificarCorreo/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView notificarCorreo(HttpSession session, @PathVariable Integer id) throws Exception {
		
		muestraServicio.actualizarNotificacionMuestra(id, true);
		
		// redirige a la consulta		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}
	
	/**
	 * La muestra es editable mientras tenga estado iniciada o asignada a lote
	 * Si esta resuelta se muestran acciones de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraEditable(MuestraCentroBean beanMuestra) {
		boolean nuevaMuestra = beanMuestra.getId() == null;
		boolean tieneLote = beanMuestra.getIdLote() != null;
		boolean tieneLoteEstado = tieneLote && (beanMuestra.getIdEstadoLote().intValue() == Estado.LOTE_INICIADO.getCodNum() 
				|| beanMuestra.getIdEstadoLote().intValue() == Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum());
				
		return nuevaMuestra || (!nuevaMuestra && (!tieneLote || tieneLoteEstado));	
	}
	
	private void addListsToView(ModelAndView vista) {
		vista.addObject("listaResultadosMuestra", BeanResultado.resultadosMuestra());
	}
	
	/**
	 * La muestra es noficable si ya se ha resuelto y no tiene fecha de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraNotificable(MuestraCentroBean beanMuestra) {
		return beanMuestra.getId() != null 
				&& beanMuestra.getResultado() != null 
				&& beanMuestra.getEstado().getEstado().getCodNum() == (Estado.MUESTRA_RESUELTA.getCodNum()) 	
				&& beanMuestra.getFechaNotificacion() == null;
	}
	
}
