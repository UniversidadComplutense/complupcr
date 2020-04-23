package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.servicios.EquipoServicio;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.LaboratorioVisavetServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.validadores.LaboratorioCentroValidador;

@Controller
@RequestMapping(value = "/laboratorioCentro")
public class LabCentroControlador {


	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(LabCentroControlador.class);

	@Autowired
	private LaboratorioCentroServicio laboratorioCentroServicio;
	
	@Autowired
	private LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private EquipoServicio equipoServicio;
	
	@Autowired
	private LaboratorioCentroValidador laboratorioCentroValidador;
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "fechaCreacion");
	public static final Sort ORDENACION_RECEPCION = Sort.by(Direction.ASC, "fechaRecepcionLaboratorioCentro");
	
	private void agregarEstadosBusquedaPlacaLaboratorio (ModelAndView vista) {
		
		// Estados de una placa de laboratorio para búsquedas (todos)
		vista.addObject("estadosPlacaLaboratorio", BeanEstado.estadosPlacaLabCentro());
	}
	
	private void agregarEstadosBusquedaPlacaVisavet (ModelAndView vista) {
		
		// Estados de una placa Visavet para búsquedas (sólo por los que buscará el laboratorio receptor)
		vista.addObject("estadosPlacaVisavet", BeanEstado.estadosPlacaVisavetParaLaboratorioCentro());
	}
	
	private void agregarEquiposPCR (ModelAndView vista) throws Exception {
		
		// Equipos PCR disponibles en el laboratorio
		vista.addObject("equiposPCR", equipoServicio.findByLaboratorioCentro(sesionServicio.getLaboratorioCentro()));
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@InitBinder("LaboratorioCentroBean")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(laboratorioCentroValidador);
	}
	
	@RequestMapping(value = "/recepcionPlacas/list", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView buscarPlacasVisavetGETList(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {

		Integer currentPage = 1;
		session.setAttribute("paginaActual", currentPage);
		
		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();		
		// Inicializamos búsqueda con estado 'PLACAVISAVET_ENVIADA' y laboratorio al que pertenece el usuario
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACAVISAVET_ENVIADA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getLaboratorioCentro().getId());
		session.setAttribute("beanBusquedaRecepcion", criteriosBusqueda);
		
		ModelAndView vista = new ModelAndView("ListadoRecepcionarPlacasVisavet");

		recepcionPlacas(vista, currentPage, session);
		
		return vista;

	}
	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView buscarPlacasVisavetGET(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {

		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
		session.setAttribute("paginaActual", currentPage);
		
		ModelAndView vista = new ModelAndView("ListadoRecepcionarPlacasVisavet");

		recepcionPlacas(vista, currentPage, session);
		
		return vista;

	}
	
	private void recepcionPlacas(ModelAndView vista, Integer currentPage, HttpSession session) throws Exception {
		
		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = (BusquedaRecepcionPlacasVisavetBean) session.getAttribute("beanBusquedaRecepcion");
		criteriosBusqueda = criteriosBusqueda != null ? criteriosBusqueda : new BusquedaRecepcionPlacasVisavetBean();
		session.setAttribute("beanBusquedaRecepcion", criteriosBusqueda);

		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, 
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION_RECEPCION));
		this.agregarEstadosBusquedaPlacaVisavet(vista);
		vista.addObject("busquedaPlacaVisavetBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(listaPlacas.getTotalPages(), currentPage, listaPlacas.getTotalElements(), "/laboratorioCentro/recepcionPlacas");		
		vista.addObject("paginadorBean", paginadorBean);
		
	}

	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView buscarPlacasVisavetPOST(HttpSession session, @ModelAttribute BusquedaRecepcionPlacasVisavetBean criteriosBusqueda) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoRecepcionarPlacasVisavet");
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getLaboratorioCentro().getId());
		
		session.setAttribute("beanBusquedaRecepcion", criteriosBusqueda);
		
		Integer currentPage = 1;		
		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, 
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION_RECEPCION));
		this.agregarEstadosBusquedaPlacaVisavet(vista);
		vista.addObject("busquedaPlacaVisavetBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(listaPlacas.getTotalPages(), currentPage, listaPlacas.getTotalElements(), "/laboratorioCentro/recepcionPlacas");		
		vista.addObject("paginadorBean", paginadorBean);
		return vista;
	}
	
	
	@RequestMapping(value = "/recepcionPlacas/recepcionar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView recepcionarPlacaVisavetGET(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		
		ModelAndView vista = new ModelAndView("PlacaVisavetRecepcionar");
		PlacaLaboratorioVisavetBean placa = laboratorioVisavetServicio.buscarPlaca(id);
		
		if (placa.getBeanEstado().getEstado().getCodNum() == Estado.PLACAVISAVET_ENVIADA.getCodNum()) {
			vista.addObject("recepcionable", true);
		} else {
			vista.addObject("recepcionable", false);
		}
		vista.addObject("placa", placa);
		return vista;
	}
	
	
	@RequestMapping(value = "/recepcionPlacas/recepcionar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView recepcionarPlacaVisavetPOST(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		ModelAndView vista = new ModelAndView("PlacaVisavetRecepcionar");	
		
		if (!result.hasErrors()) {
			if (laboratorioVisavetServicio.recepcionarPlaca(placa.getId())) {
				vista.addObject("mensaje", "La placa " + placa.getId() + " se ha recepcionado correctamente.");
				vista.addObject("recepcionable", false);
			} else {
				vista.addObject("mensaje", "No ha sido posible recepcionar la placa " +  placa.getId() + ".");
			}						
		}
				
		vista.addObject("placa", laboratorioVisavetServicio.buscarPlaca(placa.getId()));
		return vista;
	}
	
	@RequestMapping(value = "/recepcionPlacas/recepciona", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView recepcionarPlacaVisavetDesdeModalGET(@RequestParam(value = "id", required = true) Integer id, RedirectAttributes redirectAttributes)  throws Exception {

		if (laboratorioVisavetServicio.recepcionarPlaca(id)) {
			redirectAttributes.addFlashAttribute("mensaje", "La placa " + id + " se ha recepcionado correctamente.");
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "No ha sido posible recepcionar la placa " +  id + ".");
		}				
		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/laboratorioCentro/recepcionPlacas", true));
		return respuesta;
	}
	
	@RequestMapping(value = "/gestionPlacas/list", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrGETList(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {

		Integer currentPage = 1;
		session.setAttribute("paginaActual", currentPage);
		
		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();
		// Inicializamos búsqueda con el laboratorio al que pertenece el usuario
		criteriosBusqueda.setIdEstadoPlaca(0);
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getLaboratorioCentro().getId());
		session.setAttribute("beanBusqueda", criteriosBusqueda);
		
		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");

		gestionPlacas(vista, currentPage, session);
		
		return vista;

	}	
	
	@RequestMapping(value = "/gestionPlacas", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrGET(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {

		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
		session.setAttribute("paginaActual", currentPage);
		
		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");
		this.agregarEquiposPCR(vista);
		gestionPlacas(vista, currentPage, session);
		
		return vista;

	}
	
	private void gestionPlacas(ModelAndView vista, Integer currentPage, HttpSession session) throws Exception {
		BusquedaPlacaLaboratorioBean criteriosBusqueda = (BusquedaPlacaLaboratorioBean) session.getAttribute("beanBusqueda");
		criteriosBusqueda = criteriosBusqueda != null ? criteriosBusqueda : new BusquedaPlacaLaboratorioBean();		

		Page<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, 
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION));
		this.agregarEstadosBusquedaPlacaLaboratorio(vista);
		this.agregarEquiposPCR(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(listaPlacas.getTotalPages(), currentPage, listaPlacas.getTotalElements(), "/laboratorioCentro/gestionPlacas");		
		vista.addObject("paginadorBean", paginadorBean);
	}

	
	@RequestMapping(value = "/gestionPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrPOST(HttpSession session, 
			@ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");
		this.agregarEquiposPCR(vista);
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getLaboratorioCentro().getId());
		
		if (criteriosBusqueda.getNumeroMuestras() == "") {
			criteriosBusqueda.setNumeroMuestras(null);
		}
		
		session.setAttribute("beanBusqueda", criteriosBusqueda);
		
		Integer currentPage = 1;
		Page<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, 
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION));
		this.agregarEstadosBusquedaPlacaLaboratorio(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
				
		PaginadorBean paginadorBean = new PaginadorBean(listaPlacas.getTotalPages(), currentPage, listaPlacas.getTotalElements(), "/laboratorioCentro/gestionPlacas");		
		vista.addObject("paginadorBean", paginadorBean);
		return vista;
	}
		
	
	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView nuevaPlacaGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {
	
		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		this.agregarEquiposPCR(vista);
		PlacaLaboratorioCentroBean placa = new PlacaLaboratorioCentroBean();


		// Recuperamos las placas VISAVET que se pueden combinar (recepcionadas).
		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACAVISAVET_RECIBIDA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getLaboratorioCentro().getId());
		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, pageable);
		placa.setPlacasVisavetParaCombinar(listaPlacas.getContent());
		
		// Una placa nueva inicialmente no tiene tamaño ni placas Visavet seleccionadas
		placa.setNumeroMuestras("");
		placa.setPlacasVisavetSeleccionadas("");
		
		vista.addObject("nueva", true);
		vista.addObject("editable", laboratorioCentroServicio.esEditable(placa.getId()));
		vista.addObject("rellenable", true);
		vista.addObject("placa", placa);		
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView nuevaPlacaPOST(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		this.agregarEquiposPCR(vista);
		
		if (!result.hasErrors() && laboratorioCentroServicio.esRellenable(placa, Integer.valueOf(placa.getNumeroMuestras()))){
			placa = laboratorioCentroServicio.rellenarPlaca(placa, Integer.valueOf(placa.getNumeroMuestras()));
			if (placa != null) {
				vista.addObject("mensaje", "La placa " + placa.getId() + " se ha creado correctamente.");
				vista.addObject("rellenable", false);
			} else {
				vista.addObject("mensaje", "No ha sido posible crear la placa.");
				vista.addObject("rellenable", true);
			}
		} else {
			vista.addObject("mensaje", "Las placas Visavet seleccionadas superan la capacidad de la nueva placa.");
		}
		
		vista.addObject("nueva", false);
		vista.addObject("editable", false);
		vista.addObject("placa", placa);
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/modificar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView modificarPlacaGET(HttpSession session, @RequestParam(value = "id", required = true) Integer id, 
										@PageableDefault(page = 0, value = 120) Pageable pageable) throws Exception {
		
		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		this.agregarEquiposPCR(vista);
		PlacaLaboratorioCentroBean placa = laboratorioCentroServicio.buscarPlaca(id);

		// Recuperamos las placas VISAVET que se pueden combinar (recepcionadas).
		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACAVISAVET_RECIBIDA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getLaboratorioCentro().getId());
		Page<PlacaLaboratorioVisavetBean> placasParaCombinar = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, pageable);
		placa.setPlacasVisavetParaCombinar(placasParaCombinar.getContent());
		
		placa.setPlacasVisavetSeleccionadas("");
		
		vista.addObject("nueva", false);
		vista.addObject("rellenable", laboratorioCentroServicio.espacioLibreParaMuestras(placa, 0) > 0);
		vista.addObject("editable", laboratorioCentroServicio.esEditable(placa.getId()));
		vista.addObject("placa", placa);
		return vista;
	}
	
	
	@RequestMapping(value="/gestionPlacas/finalPCR", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView finalPCRplacaPOST(@ModelAttribute("placa") PlacaLaboratorioCentroBean placa, RedirectAttributes redirectAttributes) throws Exception {
		
		if (laboratorioCentroServicio.finalizarPCR(placa.getId())){
			redirectAttributes.addFlashAttribute("mensaje", "La placa " + placa.getId() + " ha finalizado correctamente la prueba PCR.");
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "No ha sido posible dar por finalizada la prueba PCR en la placa " + placa.getId() + ".");
		}
		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas/modificar?id=" + placa.getId(), true));
		return respuesta;
	}
	
	
	@RequestMapping(value="/gestionPlacas/asignarEquipo", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView asignarEquipoPCRPOST(@ModelAttribute("placa") PlacaLaboratorioCentroBean placa, RedirectAttributes redirectAttributes) throws Exception {
		
		if (laboratorioCentroServicio.asignarEquipoPCR(placa.getId(), placa.getIdEquipo())) {
			redirectAttributes.addFlashAttribute("mensaje", "La placa " + placa.getId() + " está preparada para la prueba PCR.");
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "No ha sido posible dar por preparada para PCR a la placa " + placa.getId() + ".");
		}
		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas/modificar?id=" + placa.getId(), true));
		return respuesta;
	}
	
	@RequestMapping(value="/gestionPlacas/asignaEquipo", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView asignarEquipoPCRDesdeModalGET(@RequestParam(value = "id", required = true) Integer id, @RequestParam(value = "idEquipo", required = true) Integer idEquipo, 
														RedirectAttributes redirectAttributes)  throws Exception {
		
		if (laboratorioCentroServicio.asignarEquipoPCR(id, idEquipo)) {
			redirectAttributes.addFlashAttribute("mensaje", "La placa " + id + " está preparada para la prueba PCR.");
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "No ha sido posible dar por preparada para PCR a la placa " + id + ".");
		}				
		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas", true));
		return respuesta;
	}
	
	@RequestMapping(value="/gestionPlacas/resultados", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView placaListaParaAnalizarPOST(@ModelAttribute("placa") PlacaLaboratorioCentroBean placa, RedirectAttributes redirectAttributes) throws Exception {
				
		if (laboratorioCentroServicio.placaListaParaAnalizar(placa.getId())) {
			redirectAttributes.addFlashAttribute("mensaje", "La placa " + placa.getId() + " está lista para ser analizada.");
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "No ha sido posible que la placa " + placa.getId() + " pase a estar lista para analizar.\nRevise que tiene documentación adjunta asociada a los resultados PCR.");
		}

		ModelAndView respuesta = new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas/modificar?id=" + placa.getId(), true));
		return respuesta;
	}
	
	@RequestMapping(value="/gestionPlacas/rellenar", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSABLEPCR','ADMIN')")
	public ModelAndView rellenarPlacaPOST(@ModelAttribute("placa") PlacaLaboratorioCentroBean placa, RedirectAttributes redirectAttributes) throws Exception {
				
		PlacaLaboratorioCentroBean placaParaRellenar = laboratorioCentroServicio.buscarPlaca(placa.getId());
		placaParaRellenar.setPlacasVisavetSeleccionadas(placa.getPlacasVisavetSeleccionadas());
		
		if (laboratorioCentroServicio.esRellenable(placa, 0)) {
			placaParaRellenar = laboratorioCentroServicio.rellenarPlaca(placaParaRellenar, 0);
			if (placaParaRellenar != null) {
				redirectAttributes.addFlashAttribute("mensaje", "La placa " + placa.getId() + " se ha rellenado correctamente.");
			} else {
				redirectAttributes.addFlashAttribute("mensaje", "No ha sido posible rellenar la placa " + placa.getId() + ".");
			}
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "Las placas Visavet seleccionadas superan la capacidad de la placa " + placa.getId() + ".");
		}

		ModelAndView respuesta = new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas/modificar?id=" + placaParaRellenar.getId(), true));
		return respuesta;
	}
	

}
