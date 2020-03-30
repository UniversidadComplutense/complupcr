package es.ucm.pcr.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.validadores.LaboratorioCentroValidador;

@Controller
@RequestMapping(value = "/laboratorioCentro")
public class LaboratorioCentroControlador {

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(InicioControlador.class);

	@Autowired
	private LaboratorioCentroServicio laboratorioCentroServicio;
	
	@Autowired
	private LaboratorioCentroValidador laboratorioCentroValidador;

	@InitBinder("LaboratorioCentroBean")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(laboratorioCentroValidador);
	}
	
	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasVisavetGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoRecepcionPlacasVisavet");

		BusquedaRecepcionPlacasVisavetBean criteriosBusqueda = new BusquedaRecepcionPlacasVisavetBean();

		// TODO Inicializar los criterios de búsqueda con las placas VISAVET asignadas al laboratorio

		// Page<PlacaLaboratorioVisavetBean> paginaPlacasVisavet = servicioLaboratorioUni.buscarPlacas(criteriosBusqueda, pageable);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		//vista.addObject("paginaPlacas", paginaPlacasVisavet);
		return vista;

	}

	
	@RequestMapping(value = "/recepcionPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasVisavetPOST(HttpSession session, @ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda,
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoRecepcionPlacasVisavet");
		//Page<PlacaLaboratorioVisavetBean> paginaPlacasVisavet = servicioLaboratorioUni.buscarPlacas(criteriosBusqueda, pageable);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		//vista.addObject("paginaPlacas", paginaPlacasVisavet);
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/preparacion", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrGET(HttpSession session) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasListasParaPCR");

		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();

		// TODO Inicializar los criterios de búsqueda con las placas asignadas al
		// laboratorio

		List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas);
		return vista;

	}

	
	@RequestMapping(value = "/gestionPlacas/preparacion", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasListasParaPcrPOST(HttpSession session, @ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasListasParaPCR");
		List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas);
		return vista;
	}
	
	
	@RequestMapping(value = "/gestionPlacas/resultados", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasEsperandoResultadosGET(HttpSession session) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasEsperandoResultadosPCR");

		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();

		// TODO Inicializar los criterios de búsqueda con las placas asignadas al
		// laboratorio

		List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas);
		return vista;

	}

	
	@RequestMapping(value = "/gestionPlacas/resultados", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasEsperandoResultadosPOST(HttpSession session, @ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasEsperandoResultadosPCR");
		List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		vista.addObject("listaPlacas", listaPlacas);
		return vista;
	}

	
	@RequestMapping(value = "/gestionPlacas/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView consultarPlaca(HttpSession session, @PathVariable Integer idPlaca) throws Exception {

		return new ModelAndView("PlacaLaboratorio").addObject("placa", laboratorioCentroServicio.buscarPlaca(idPlaca));
	}
	
	
	@RequestMapping(value = "/gestionPlacas/guardar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView guardarPlaca(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			laboratorioCentroServicio.guardarPlaca(Integer.valueOf(placa.getId()));
			return new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas/preparacion", true));
		} else {
			return new ModelAndView("PlacaLaboratorio").addObject("placa", placa);
		}
	}
	
	
	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView nuevaPlaca(HttpSession session) throws Exception {
	
		return new ModelAndView("PlacaLaboratorio").addObject("placa", new PlacaLaboratorioCentroBean());
	}
	

	@RequestMapping(value = "/gestionPlacas/nueva", method = RequestMethod.POST)
	public ModelAndView nuevaPlaca(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			laboratorioCentroServicio.guardarPlaca(Integer.valueOf(placa.getId()));
			return new ModelAndView(new RedirectView("/laboratorioCentro/gestionPlacas/preparacion", true));
		} else {
			return new ModelAndView("PlacaLaboratorio").addObject("placa", placa);
		}
	}

}
