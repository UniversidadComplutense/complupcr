package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.validadores.LaboratorioCentroValidador;

@Controller
@RequestMapping(value = "/laboratorioCentro")
public class LabCentroControlador {


	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(LabCentroControlador.class);

	@Autowired
	private LaboratorioCentroServicio laboratorioCentroServicio;
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LaboratorioCentroValidador laboratorioCentroValidador;
	
	
	private void agregarListasDesplegables (ModelAndView vista) {
		
		// Estados de una placa de laboratorio
		List<BeanEstado> estadosPlacaLaboratorio = BeanEstado.estadosPlacaLabCentro();	
		vista.addObject("estadosPlacaLaboratorio", estadosPlacaLaboratorio);
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
	
	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasVisavetGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoRecepcionPlacasVisavet");

		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();

		// TODO Inicializar los criterios de búsqueda con las placas VISAVET asignadas al laboratorio

		// Page<PlacaLaboratorioVisavetBean> paginaPlacasVisavet = servicioLaboratorioUni.buscarPlacas(criteriosBusqueda, pageable);

		vista.addObject("BusquedaRecepcionPlacasVisavetBean", criteriosBusqueda);
		//vista.addObject("paginaPlacas", paginaPlacasVisavet);
		return vista;

	}

	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasVisavetPOST(HttpSession session, @ModelAttribute BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoRecepcionPlacasVisavet");
		//Page<PlacaLaboratorioVisavetBean> paginaPlacasVisavet = servicioLaboratorioUni.buscarPlacas(criteriosBusqueda, pageable);

		vista.addObject("BusquedaPlacaLaboratorioBean", criteriosBusqueda);
		//vista.addObject("paginaPlacas", paginaPlacasVisavet);
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/preparacion", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");

		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();
		
		// Inicializamos búsqueda con estado 'PreparadaParaPCR' y laboratorio al que pertenece el usuario
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACA_PREPARADA_PARA_PCR.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());

		Page<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable);
		this.agregarListasDesplegables(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		return vista;

	}

	
	@RequestMapping(value = "/gestionPlacas/preparacion", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrPOST(HttpSession session, 
			@ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda, 
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());
		
		Page<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable);
		this.agregarListasDesplegables(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/resultados", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasEsperandoResultadosGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");

		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();

		// Inicializamos búsqueda con estado 'FinalizadoPCR'
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACA_FINALIZADA_PCR.getCodNum());

		List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable).getContent();
		this.agregarListasDesplegables(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas);
		return vista;

	}

	
	@RequestMapping(value = "/gestionPlacas/resultados", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasEsperandoResultadosPOST(HttpSession session, 
			@ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda, 
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");
		List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable).getContent();

		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas);
		return vista;
	}

	
	@RequestMapping(value = "/gestionPlacas/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView consultarPlaca(HttpSession session, @PathVariable Integer idPlaca) throws Exception {

		return new ModelAndView("PlacaLaboratorio").addObject("placa", laboratorioCentroServicio.buscarPlaca(idPlaca));
	}
		
	
	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView nuevaPlaca(HttpSession session) throws Exception {
	
		ModelAndView vista = new ModelAndView("PlacaLaboratorio");		
		PlacaLaboratorioCentroBean placa = new PlacaLaboratorioCentroBean();		
		
		vista.addObject("editable", esEditable(placa));
		vista.addObject("placa", new PlacaLaboratorioCentroBean());		
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/guardar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView guardarPlaca(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			placa = laboratorioCentroServicio.guardarPlaca(placa);
		}
		return new ModelAndView("PlacaLaboratorio").addObject("placa", placa);
	}
	
	private boolean esEditable(PlacaLaboratorioCentroBean placa) {
		return (placa.getId() == null);		
	}
	
	
	@RequestMapping(value = "/gestionPlacas/modificar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView modificarPlaca(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		
		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		PlacaLaboratorioCentroBean placa = new PlacaLaboratorioCentroBean();
	
		vista.addObject("editable", esEditable(placa));
		vista.addObject("placa", new PlacaLaboratorioCentroBean());
		return vista;
	}
	

}
