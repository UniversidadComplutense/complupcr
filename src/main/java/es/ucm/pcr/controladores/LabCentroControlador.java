package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.LaboratorioVisavetServicio;
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
	private LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LaboratorioCentroValidador laboratorioCentroValidador;
	
	
	private void agregarEstadosBusquedaPlacaLaboratorio (ModelAndView vista) {
		
		// Estados de una placa de laboratorio para búsquedas (todos)
		vista.addObject("estadosPlacaLaboratorio", BeanEstado.estadosPlacaLabCentro());
	}
	
	private void agregarEstadosBusquedaPlacaVisavet (ModelAndView vista) {
		
		// Estados de una placa Visavet para búsquedas (sólo por los que buscará el laboratorio receptor)
		vista.addObject("estadosPlacaVisavet", BeanEstado.estadosPlacaVisavetParaLaboratorioCentro());
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

		ModelAndView vista = new ModelAndView("ListadoRecepcionarPlacasVisavet");

		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();

		// Inicializamos búsqueda con estado 'PLACAVISAVET_ASIGNADA' y laboratorio al que pertenece el usuario
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACAVISAVET_ASIGNADA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());

		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, pageable);
		this.agregarEstadosBusquedaPlacaVisavet(vista);
		vista.addObject("busquedaPlacaVisavetBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		return vista;

	}

	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasVisavetPOST(HttpSession session, @ModelAttribute BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoRecepcionarPlacasVisavet");
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());
		
		
		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, pageable);
		this.agregarEstadosBusquedaPlacaVisavet(vista);
		vista.addObject("busquedaPlacaVisavetBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		return vista;
	}
	
	
	@RequestMapping(value = "/recepcionPlacas/recepcionar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView recepcionarPlacaVisavetGET(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		
		ModelAndView vista = new ModelAndView("PlacaVisavetRecepcionar");
		PlacaLaboratorioVisavetBean placa = laboratorioVisavetServicio.buscarPlaca(id);
		vista.addObject("mostrarMensaje", false);
		
		if (placa.getBeanEstado().getEstado().getCodNum() == Estado.PLACAVISAVET_ASIGNADA.getCodNum()) {
			vista.addObject("recepcionable", true);
		}			
		vista.addObject("placa", placa);
		return vista;
	}
	
	
	@RequestMapping(value = "/recepcionPlacas/recepcionar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView recepcionarPlacaVisavetPOST(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		ModelAndView vista = new ModelAndView("PlacaVisavetRecepcionar");	
		vista.addObject("mostrarMensaje", false);
		
		if (!result.hasErrors()) {
			laboratorioVisavetServicio.recepcionarPlaca(placa.getId());
			vista.addObject("mensaje", "La placa se ha recepcionado correctamente.");
			vista.addObject("mostrarMensaje", true);
			vista.addObject("recepcionable", false);
		}
				
		vista.addObject("placa", laboratorioVisavetServicio.buscarPlaca(placa.getId()));
		return vista;
	}
		
	
	@RequestMapping(value = "/gestionPlacas", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");

		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();
		
		// Inicializamos búsqueda con estado 'PLACA_INICIADA' y laboratorio al que pertenece el usuario
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACA_INICIADA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());

		Page<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable);
		this.agregarEstadosBusquedaPlacaLaboratorio(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		return vista;

	}

	
	@RequestMapping(value = "/gestionPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrPOST(HttpSession session, 
			@ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda, 
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorio");
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());
		
		if (criteriosBusqueda.getNumeroMuestras() == "") {
			criteriosBusqueda.setNumeroMuestras(null);
		}
		
		Page<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable);
		this.agregarEstadosBusquedaPlacaLaboratorio(vista);
		vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas.getContent());
		return vista;
	}

	
	@RequestMapping(value = "/gestionPlacas/consultar{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView consultarPlaca(HttpSession session, @PathVariable Integer idPlaca) throws Exception {

		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		vista.addObject("mostrarMensaje", false);
		vista.addObject("placa", laboratorioCentroServicio.buscarPlaca(idPlaca));		
		return vista;
	}
		
	
	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView nuevaPlacaGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {
	
		ModelAndView vista = new ModelAndView("PlacaLaboratorio");		
		PlacaLaboratorioCentroBean placa = new PlacaLaboratorioCentroBean();


		// Recuperamos las placas VISAVET que se pueden combinar (recepcionadas).
		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACAVISAVET_RECIBIDA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());
		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, pageable);
		placa.setPlacasVisavetParaCombinar(listaPlacas.getContent());
		
		// Una placa nueva inicialmente no tiene tamaño ni placas Visavet seleccionadas
		placa.setNumeroMuestras("");
		placa.setPlacasVisavetSeleccionadas("");
		
		vista.addObject("mostrarMensaje", false);
		vista.addObject("nueva", true);
		vista.addObject("editable", placa.esEditable());
		vista.addObject("rellenable", placa.esRellenable());
		vista.addObject("placa", placa);		
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView nuevaPlacaPOST(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		vista.addObject("mostrarMensaje", false);
		
		// Transformamos en un Array el String que viene de la vista con los IDs de las placas Visavet seleccionadas
		String[] listaIDsPlacasVisavetSeleccinadas = placa.getPlacasVisavetSeleccionadas().split(":");
		
		for (String idPlacaVisavet : listaIDsPlacasVisavetSeleccinadas) {
			System.out.println(idPlacaVisavet);
		}
		
		
		if (!result.hasErrors()) {
			placa = laboratorioCentroServicio.guardarPlaca(placa);
			vista.addObject("mostrarMensaje", true);
			vista.addObject("mensaje", "La placa " + placa.getId() + " se ha creado correctamente.");			
		}
		
		vista.addObject("nueva", false);
		vista.addObject("editable", false);
		vista.addObject("rellenable", placa.esEditable());
		vista.addObject("placa", placa);
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/modificar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
	public ModelAndView modificarPlaca(HttpSession session, @RequestParam(value = "id", required = true) Integer id, 
										@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {
		
		ModelAndView vista = new ModelAndView("PlacaLaboratorio");
		PlacaLaboratorioCentroBean placa = laboratorioCentroServicio.buscarPlaca(id);
				
		// Recuperamos las placas VISAVET que se pueden combinar (recepcionadas).
		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();
		criteriosBusqueda.setIdEstadoPlaca(BeanEstado.Estado.PLACAVISAVET_RECIBIDA.getCodNum());
		criteriosBusqueda.setIdLaboratorioCentro(sesionServicio.getCentro().getId());
		Page<PlacaLaboratorioVisavetBean> listaPlacas = laboratorioVisavetServicio.buscarPlacas(criteriosBusqueda, pageable);
		placa.setPlacasVisavetParaCombinar(listaPlacas.getContent());
		
		placa.setPlacasVisavetSeleccionadas("");
		
		
		vista.addObject("mostrarMensaje", false);
		vista.addObject("nueva", false);
		vista.addObject("rellenable", placa.esRellenable());
		vista.addObject("editable", placa.esEditable());
		vista.addObject("placa", placa);
		return vista;
	}
	

}
