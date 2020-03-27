package es.ucm.pcr.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLaboratorioCentro");

		BusquedaPlacaLaboratorioBean criteriosBusqueda = new BusquedaPlacaLaboratorioBean();

		// TODO Inicializar los criterios de búsqueda con las placas asignadas al
		// laboratorio

		Page<PlacaLaboratorioCentroBean> paginaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		vista.addObject("paginaPlacas", paginaPlacas);
		return vista;

	}

	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView buscarPlacasPOST(HttpSession session,
			@ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda,
			@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

		ModelAndView vista = new ModelAndView("ListadoPlacasLabotatorioCentro");
		Page<PlacaLaboratorioCentroBean> paginaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable);

		vista.addObject("criteriosBusqueda", criteriosBusqueda);
		vista.addObject("paginaPlacas", paginaPlacas);
		return vista;
	}

	@RequestMapping(value = "/placa/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView consultarPlaca(HttpSession session, @PathVariable Integer idPlaca) throws Exception {

		return new ModelAndView("PlacaLaboratorio").addObject("placa", laboratorioCentroServicio.buscarPlaca(idPlaca));
	}

	@RequestMapping(value = "/placa/guardar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
	public ModelAndView guardarPlaca(@Valid @ModelAttribute("placa") PlacaLaboratorioCentroBean placa, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			laboratorioCentroServicio.guardarPlaca(Integer.valueOf(placa.getId()));
			return new ModelAndView(new RedirectView("/laboratorioCentro/buscar/", true));
		} else {
			return new ModelAndView("PlacaLaboratorio").addObject("placa", placa);
		}
	}

}
