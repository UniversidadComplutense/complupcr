package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BeanPlacaVisavetUCM;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.LotePlacaVisavetBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.servicios.CentroServicio;
import es.ucm.pcr.servicios.DocumentoServicio;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.LaboratorioVisavetServicio;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.MuestraServicio;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCM;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.validadores.DocumentoValidador;
import es.ucm.pcr.validadores.LoteValidador;

@Controller
public class LaboratorioVisavetUCMController {
	@Autowired
	ServicioLaboratorioVisavetUCM servicioLaboratorioUni;
	@Autowired
	LoteServicio loteServicio;

	@Autowired
	SesionServicio sesionServicio;

	@Autowired
	CentroServicio centroServicio;

	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;
	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;
	@Autowired
	MuestraServicio muestraServicio;

	@Autowired
	private DocumentoValidador documentoValidador;

	@Autowired
	private DocumentoServicio documentoServicio;

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(LaboratorioVisavetUCMController.class);
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "fechaEnvio");
	public static final Sort ORDENACION_PLACAS = Sort.by(Direction.ASC, "fechaCreacion");

	public static final String URLBUSCARLOTES = "/laboratorioUni/buscarLotes?estado=4&rol=T";
	public static final String URLBUSCARPLACAS = "/laboratorioUni/buscarPlacas";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@InitBinder("elementoDoc")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(documentoValidador);
	}

	private Boolean mostrarGuardarRef(List<LoteBeanPlacaVisavet> lotes) {
		Boolean mostrar = false;
		for (LoteBeanPlacaVisavet lote : lotes) {
			if (lote.getEstado().getEstado().equals(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS)) {
				mostrar = true;
				break;
			}
		}
		return mostrar;
	}

	private BusquedaLotesBean rellenarBusquedaLotes(BusquedaLotesBean busquedaLotes) throws Exception {

		busquedaLotes.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
		busquedaLotes.setListaCentros(centroServicio.listaCentrosOrdenada());

		return busquedaLotes;
	}

	public ModelAndView buscarLotes(BusquedaLotesBean busquedaLotes, String rolURL, HttpServletRequest request,
			HttpSession session, Pageable pageable) throws Exception {

		Page<LoteBeanPlacaVisavet> paginaLotes = null;
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		busquedaLotes.setMostrarProcesar(false);

		for (String rol : sesionServicio.getRoles()) {
			if ((rol.equals("ROLE_TECNICOLABORATORIO") || rol.equals("ROLE_ADMIN")) && rolURL.equals("T")) {
				busquedaLotes.setMostrarProcesar(true);
				break;
			}
		}
		busquedaLotes.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
		busquedaLotes.setListaCentros(centroServicio.listaCentrosOrdenada());

		busquedaLotes.setRolURL(rolURL);
		busquedaLotes.setFechaFinEntrada(Utilidades.fechafinBuscador(busquedaLotes.getFechaFinEntrada()));
		paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), ORDENACION));

		vista.addObject("paginaLotes", paginaLotes);
		vista.addObject("busquedaLotes", busquedaLotes);
		busquedaLotes.setMostrarGuardarRef(this.mostrarGuardarRef(paginaLotes.getContent()));
		// guardo los criterios de busqueda de lotes
		session.setAttribute("busquedaLotes", busquedaLotes);
		return vista;
	}

	// presenta la pagina con unos criterios de busqueda iniciales
	@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public ModelAndView buscarLotesGet(@RequestParam("estado") Integer estado,
			@RequestParam(required = false) String rol, Model model, HttpServletRequest request, HttpSession session,
			@PageableDefault(page = 0, value = 500000000) Pageable pageable) throws Exception {
		// tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le
		// muestro unicamente sus loooooootes
		BusquedaLotesBean busquedaLotes = new BusquedaLotesBean();

		if (session.getAttribute("busquedaLotes") != null) {
			busquedaLotes = (BusquedaLotesBean) session.getAttribute("busquedaLotes");

		}
		if (estado != null) {
			if (estado == 0)
				busquedaLotes.setCodNumEstadoSeleccionado(null);
			else
				busquedaLotes.setCodNumEstadoSeleccionado(estado);
		}
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");

		List<LoteBeanPlacaVisavet> list = new ArrayList();
		Page<LoteBeanPlacaVisavet> paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable, 50000000);

		model.addAttribute("paginaLotes", paginaLotes);
		;
		vista.addObject("busquedaLotes", busquedaLotes);
		vista.addObject("paginaLotes", paginaLotes);

		return this.buscarLotes(busquedaLotes, rol, request, session, pageable);

	}

	@RequestMapping(value = "/laboratorioUni/guardarReferenciaLotes", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public ModelAndView guardarReferenciasLotes(@RequestParam("lotes") String lotes, Model model,
			HttpServletRequest request, HttpSession session,
			@PageableDefault(page = 0, value = 50000000, sort = "lote", direction = Sort.Direction.DESC) Pageable pageable)
			throws Exception {

		String[] lotesList = lotes.split(":");
		List<LoteCentroBean> lotesError = new ArrayList();
		for (String lote : lotesList) {
			String[] unLote = lote.split("_");
			LoteCentroBean beanLote;
			if (unLote.length > 1 && unLote[0] != "") {
				beanLote = loteServicio.findById(Integer.parseInt(unLote[0]));
				beanLote.setReferenciaInternaLote(unLote[1]);
				List<LoteListadoBean> listaLote = loteServicio
						.findLoteByReferenciaExterna(beanLote.getReferenciaInternaLote());
				if (listaLote == null || listaLote.size() == 0)
					loteServicio.guardarLote(beanLote);
				else {
					boolean incluir = true;
					for (LoteListadoBean loteBbdd : listaLote) {
						if (loteBbdd.getId() == beanLote.getId()) {
							incluir = false;
							break;
						}
					}
					if (incluir) {
						beanLote.setErrorReferenciaInternaLote("La referencia interna esta repetida");
						lotesError.add(beanLote);
					}
				}
			}
		}

		BusquedaLotesBean busqueda = (BusquedaLotesBean) session.getAttribute("busquedaLotes");
		if (busqueda == null)
			busqueda = new BusquedaLotesBean();
		if (busqueda.getRolURL() == null)
			busqueda.setRolURL("R");

		Page<LoteBeanPlacaVisavet> paginaLotes = null;
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		busqueda.setMostrarProcesar(false);

		for (String rol : sesionServicio.getRoles()) {
			if ((rol.equals("ROLE_TECNICOLABORATORIO") || rol.equals("ROLE_ADMIN"))
					&& busqueda.getRolURL().equals("T")) {
				busqueda.setMostrarProcesar(true);
				break;
			}
		}
		busqueda.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
		busqueda.setListaCentros(centroServicio.listaCentrosOrdenada());
		paginaLotes = servicioLaboratorioUni.buscarLotes(busqueda,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), ORDENACION));
		for (LoteBeanPlacaVisavet lote : paginaLotes.getContent()) {
			for (LoteCentroBean loteError : lotesError) {
				if (lote.getId() == loteError.getId()) {
					lote.setErrorReferenciaLote(loteError.getErrorReferenciaInternaLote());
					lote.setReferenciaInternaLote(loteError.getReferenciaInternaLote());
					break;
				}
			}

		}

		vista.addObject("paginaLotes", paginaLotes);
		vista.addObject("busquedaLotes", busqueda);

		busqueda.setMostrarGuardarRef(this.mostrarGuardarRef(paginaLotes.getContent()));

		session.setAttribute("busquedaLotes", busqueda);
		return vista;

	}

	// buscar lotes segun los criterios de busqueda

	@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public String buscarLotesPost(@ModelAttribute("busquedaLotes") BusquedaLotesBean busquedaLotes, Model model,
			HttpServletRequest request, HttpSession session,
			@PageableDefault(page = 0, value = 50000000, sort = "lote", direction = Sort.Direction.DESC) Pageable pageable)
			throws Exception {
		session.setAttribute("busquedaLotes", busquedaLotes);
		if (busquedaLotes.getCodNumEstadoSeleccionado() == null)
			busquedaLotes.setCodNumEstadoSeleccionado(0);
		if (busquedaLotes.getRolURL() == null)
			busquedaLotes.setRolURL("R");
		return "redirect:/laboratorioUni/buscarLotes?estado=" + busquedaLotes.getCodNumEstadoSeleccionado() + "&rol="
				+ busquedaLotes.getRolURL();

	}

	@RequestMapping(value = "/laboratorioUni/confirmarReciboLote", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public ModelAndView confirmarReciboLote(@RequestParam("id") Integer id, Model model, HttpServletRequest request,
			HttpSession session,
			@PageableDefault(page = 0, value = 50000000, sort = "lote", direction = Sort.Direction.DESC) Pageable pageable)
			throws Exception {

		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		LoteCentroBean beanLote = loteServicio.findById(id);
		beanLote.setFechaRecibido(new Date());
		BeanEstado estado = new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoLote);
		estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);

		loteServicio.actualizarEstadoLote(beanLote, estado);

		BusquedaLotesBean busqueda = (BusquedaLotesBean) session.getAttribute("busquedaLotes");
		if (busqueda == null)
			busqueda = new BusquedaLotesBean();
		if (busqueda.getRolURL() == null)
			busqueda.setRolURL("R");
		return this.buscarLotes(busqueda, busqueda.getRolURL(), request, session, pageable);

	}

	@RequestMapping(value = "/laboratorioUni/cancelarReciboLote", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public ModelAndView cancelarReciboLote(@RequestParam("id") Integer id, Model model, HttpServletRequest request,
			HttpSession session,
			@PageableDefault(page = 0, value = 50000000, sort = "lote", direction = Sort.Direction.DESC) Pageable pageable)
			throws Exception {

		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		LoteCentroBean beanLote = loteServicio.findById(id);
		beanLote.setFechaRecibido(new Date());
		BeanEstado estado = new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoLote);
		estado.setEstado(Estado.LOTE_ENVIADO_CENTRO_ANALISIS);

		loteServicio.actualizarEstadoLote(beanLote, estado);
		BusquedaLotesBean busqueda = (BusquedaLotesBean) session.getAttribute("busquedaLotes");
		if (busqueda == null)
			busqueda = new BusquedaLotesBean();
		if (busqueda.getRolURL() == null)
			busqueda.setRolURL("R");
		return this.buscarLotes(busqueda, busqueda.getRolURL(), request, session, pageable);

	}

	@RequestMapping(value = "/laboratorioUni/cancelarEnvioPlaca", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public String cancelarEnvioPlaca(@RequestParam("id") Integer id, Model model, HttpServletRequest request,
			HttpSession session) throws Exception {

		ModelAndView vista = new ModelAndView("VistaListadoPlacasVisavet");
		BeanPlacaVisavetUCM beanPlaca = servicioLaboratorioUni.buscarPlacaById(id);

		BeanEstado estado = new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		estado.setEstado(Estado.PLACAVISAVET_ASIGNADA);
		beanPlaca.setEstado(estado);

		servicioLaboratorioUni.guardarPlacaConLaboratorio(beanPlaca, beanPlaca.getLaboratorioCentro().getId());

		return "redirect:/laboratorioUni/buscarPlacas";

	}

	@RequestMapping(value = "/laboratorioUni/confirmarEnviadaPlaca", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public String confirmarEnviadaPlaca(@RequestParam("id") Integer id, Model model, HttpServletRequest request,
			HttpSession session) throws Exception {
		BeanPlacaVisavetUCM placa = servicioLaboratorioUni.buscarPlacaById(id);

		BeanEstado estado = new BeanEstado();
		estado.setEstado(Estado.PLACAVISAVET_ENVIADA);
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		placa.setEstado(estado);
		placa.setFechaEnviadaLaboratorio(new Date());
		BeanPlacaVisavetUCM placab = servicioLaboratorioUni.guardar(placa);

		return "redirect:/laboratorioUni/buscarPlacas";
	}

	// refrescar datos con ajax
	@RequestMapping(value = "/laboratorioUni/actualizarLote", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public String buscarPlacasPost(@RequestParam("id") String id, Model model, HttpServletRequest request,
			HttpSession session, @PageableDefault(page = 0, value = 50000000) Pageable pageable) {
		Page<LoteBeanPlacaVisavet> paginaLotes = null;
		BusquedaLotesBean busquedaLotes = (BusquedaLotesBean) model.getAttribute("busquedaLotes");
		paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
		List<LoteBeanPlacaVisavet> list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			// list.add(getBean2(i));
		}
		paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable, 50000000);
		model.addAttribute("paginaLotes", paginaLotes);

		return "VistaListadoRecepcionLotes :: #trGroup";
	}

	@RequestMapping(value = "/laboratorioUni/mostrarMuestras", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public String consultarMuestrasLote(@RequestParam("id") Integer id, Model model, HttpServletRequest request,
			HttpSession session,
			@PageableDefault(page = 0, value = 50000000, sort = "lote", direction = Sort.Direction.DESC) Pageable pageable) {
		LoteBeanPlacaVisavet loteBeanPlacaVisavet = servicioLaboratorioUni.buscarLote(id);
		List<MuestraBeanLaboratorioVisavet> muestras = loteBeanPlacaVisavet.getListaMuestras();

		model.addAttribute("muestras", muestras);

		return "Vist)aListadoRecepcionLotes :: #trMuestra";
	}

	private Boolean isLotesConPlacas(List<LoteBeanPlacaVisavet> lotes) {
		Boolean mostrar = true;
		for (LoteBeanPlacaVisavet lote : lotes) {
			if (lote.getIdPlacaVisavet() == null) {
				mostrar = false;
				break;
			}
		}
		return mostrar;
	}

	// este metodo obtiene los lotes q estan listos para ser procesados en placas
	// visavet y muestran en las placas
	@RequestMapping(value = "/laboratorioUni/procesarLotes", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public ModelAndView buscarPlacasGet(@RequestParam("lotes") String lotes, @RequestParam("url") String url,
			Model model, HttpServletRequest request, HttpSession session) {
		ModelAndView vista = new ModelAndView("VistaLotesPlacasVisavet");
		LotePlacaVisavetBean lotePlacaVisavetBean = new LotePlacaVisavetBean();
		// obtenemos los lotes con sus muestras
		String[] idsLotes = lotes.split(":");
		List<LoteBeanPlacaVisavet> listaLotes = new ArrayList<LoteBeanPlacaVisavet>();
		Integer numeroMuestras = 0;

		for (int i = 0; i < idsLotes.length; i++) {

			if (idsLotes[i] != "") {
				LoteBeanPlacaVisavet lotePlaca = loteServicio.findByIdByPlacas(Integer.parseInt(idsLotes[i]));
				numeroMuestras += lotePlaca.getListaMuestras().size();
				listaLotes.add(lotePlaca);
			}

		}
		lotePlacaVisavetBean.setTotalMuestras(numeroMuestras);

		List<Integer> tamanoLista = new ArrayList<Integer>();
		tamanoLista.add(20);
		tamanoLista.add(96);

		lotePlacaVisavetBean.setListaTamanosDisponibles(tamanoLista);

		BeanPlacaVisavetUCM placaVisavet = new BeanPlacaVisavetUCM();

		placaVisavet.setListaLotes(listaLotes);
		lotePlacaVisavetBean.setPlaca(placaVisavet);
		lotePlacaVisavetBean.setListaLotesDisponibles(listaLotes);
		session.setAttribute("lotePlacaVisavetBean", lotePlacaVisavetBean);

		vista.addObject("lotePlacaVisavetBean", lotePlacaVisavetBean);
		vista.addObject("botonAltaNoMostrar", this.isLotesConPlacas(listaLotes));
		if (url.equals("P")) {

			vista.addObject("urlVolver", URLBUSCARPLACAS);
		} else
			vista.addObject("urlVolver", URLBUSCARLOTES);

		vista.addObject("paginaAtras", url);
		return vista;
	}

	@ResponseBody
	@RequestMapping(value = "/laboratorioUni/altaPlacaVisavet", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public Integer altaPlacasGet(Model model, HttpServletRequest request, HttpSession session) {

		BeanPlacaVisavetUCM placaVisavet = new BeanPlacaVisavetUCM();
		BeanEstado estado = new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		estado.setEstado(Estado.PLACA_INICIADA);

		placaVisavet.setEstado(estado);
		placaVisavet = servicioLaboratorioUni.guardar(placaVisavet);

		return placaVisavet.getId();
	}

	private BusquedaPlacasVisavetBean rellenarBusquedaPlacas(BusquedaPlacasVisavetBean busquedaPlacasVisavetBean)
			throws Exception {

		busquedaPlacasVisavetBean.setListaBeanEstado(BeanEstado.estadosPlacaVisavet());
		busquedaPlacasVisavetBean
				.setListaLaboratorioCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
		busquedaPlacasVisavetBean.setIdLaboratorioVisavet(sesionServicio.getLaboratorioVisavet().getId());
		return busquedaPlacasVisavetBean;
	}

	@RequestMapping(value = "/laboratorioUni/buscarPlacas/list", method = RequestMethod.GET)
	public String buscarPlacasGetMenu(HttpSession session) throws Exception {
		session.setAttribute("paginaActual", 1);
		return "redirect:/laboratorioUni/buscarPlacas";
	}

	// buscar placas segun los criterios de busqueda
	@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.GET)
	public ModelAndView buscarPlacasGet(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam("pagina") Optional<Integer> page) throws Exception {

		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null
				? (session.getAttribute("paginaActual") != null ? (Integer) session.getAttribute("paginaActual") : 1)
				: page.get();
		session.setAttribute("paginaActual", currentPage);

		BusquedaPlacasVisavetBean busquedaPlacasVisavetBean;
		ModelAndView vista = new ModelAndView("VistaListadoPlacasVisavet");

		if (session.getAttribute("busquedaPlacasVisavetBean") == null) {
			busquedaPlacasVisavetBean = new BusquedaPlacasVisavetBean();
			busquedaPlacasVisavetBean.setIdLaboratorioCentro(null);
			busquedaPlacasVisavetBean = this.rellenarBusquedaPlacas(busquedaPlacasVisavetBean);

		} else
			busquedaPlacasVisavetBean = (BusquedaPlacasVisavetBean) session.getAttribute("busquedaPlacasVisavetBean");
		vista.addObject("pagina", null);
		vista.addObject("busqueda", busquedaPlacasVisavetBean);

		return this.buscarPlacas(busquedaPlacasVisavetBean, request, session,
				PageRequest.of(currentPage - 1, Utilidades.NUMERO_PAGINACION, ORDENACION_PLACAS));

	}

	public ModelAndView buscarPlacas(BusquedaPlacasVisavetBean busqueda, HttpServletRequest request,
			HttpSession session, Pageable pageable) throws Exception {

		Page<BeanPlacaVisavetUCM> pagina = null;
		ModelAndView vista = new ModelAndView("VistaListadoPlacasVisavet");
		if (busqueda == null)
			new BusquedaPlacasVisavetBean();
		busqueda = this.rellenarBusquedaPlacas(busqueda);
		busqueda.setFechaCreacionFin(Utilidades.fechafinBuscador(busqueda.getFechaCreacionFin()));

		pagina = servicioLaboratorioUni.buscarPlacas(busqueda, pageable);

		vista.addObject("pagina", pagina);
		vista.addObject("busqueda", busqueda);

		session.setAttribute("busqueda", busqueda);

		PaginadorBean paginadorBean = new PaginadorBean(pagina.getTotalPages(), pageable.getPageNumber() + 1,
				pagina.getTotalElements(), "/laboratorioUni/buscarPlacas");
		vista.addObject("paginadorBean", paginadorBean);

		return vista;
	}

	@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public ModelAndView buscarPlacasPost(
			@ModelAttribute("busquedaPlacasVisavetBean") BusquedaPlacasVisavetBean busquedaPlacasVisavetBean,
			Model model, HttpServletRequest request, HttpSession session) throws Exception {
		session.setAttribute("busquedaPlacasVisavetBean", busquedaPlacasVisavetBean);
		session.setAttribute("paginaActual", 1);

		return this.buscarPlacas(busquedaPlacasVisavetBean, request, session,
				PageRequest.of(0, Utilidades.NUMERO_PAGINACION, ORDENACION_PLACAS));

	}

	// buscar placas segun los criterios de busqueda
	@RequestMapping(value = "/laboratorioUni/buscarPlacasProcesar", method = RequestMethod.POST)
	public String buscarPlacasPost(@ModelAttribute("lotePlacaVisavetBean") LotePlacaVisavetBean lotePlacaVisavetBean,
			Model model, HttpServletRequest request, HttpSession session,
			@PageableDefault(page = 0, value = 50000000) Pageable pageable) throws Exception {
		ModelAndView vista = new ModelAndView("VistaBuscarPlacas");
		BeanEstado estado = new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		if (lotePlacaVisavetBean.getAccion().equals("grabaryFinalizar")) {
			estado.setEstado(Estado.PLACAVISAVET_FINALIZADA);

		} else {

			estado.setEstado(Estado.PLACAVISAVET_PREPARADA);

		}

		BeanPlacaVisavetUCM placa = lotePlacaVisavetBean.getPlaca();

		for (LoteBeanPlacaVisavet lote : lotePlacaVisavetBean.getPlaca().getListaLotes()) {
			placa.setId(lote.getIdPlacaVisavet());
			placa.setEstado(estado);
			placa = servicioLaboratorioUni.guardar(placa);

			// obtenemos las muestras y guardamos la referencia
			for (MuestraBeanLaboratorioVisavet m : lote.getListaMuestras()) {
				m = muestraServicio.guardarReferencia(m);
			}

		}

		BusquedaPlacasVisavetBean busquedaPlacasVisavetBean = new BusquedaPlacasVisavetBean();
		busquedaPlacasVisavetBean.setIdLaboratorioCentro(0);
		busquedaPlacasVisavetBean = this.rellenarBusquedaPlacas(busquedaPlacasVisavetBean);

		return "redirect:/laboratorioUni/buscarPlacas";

	}

	@RequestMapping(value = "/laboratorioUni/asignarPlaca", method = RequestMethod.GET)

	public String asignarPlacasGet(@RequestParam("idPlaca") int idPlaca,
			@ModelAttribute("lotePlacaVisavetBean") LotePlacaVisavetBean loteFormulario, Model model,
			HttpServletRequest request, HttpSession session) {

		LotePlacaVisavetBean lotePlacaVisavetBean = (LotePlacaVisavetBean) session.getAttribute("lotePlacaVisavetBean");
		List<LoteBeanPlacaVisavet> listaLotesDisponibles = new ArrayList<LoteBeanPlacaVisavet>();
		if (lotePlacaVisavetBean == null)
			lotePlacaVisavetBean = new LotePlacaVisavetBean();
		for (LoteBeanPlacaVisavet lote : lotePlacaVisavetBean.getListaLotesDisponibles()) {

			lote.setIdPlacaVisavet(idPlaca);

			BeanEstado estado = new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoLote);
			estado.setEstado(Estado.LOTE_PROCESADO_CENTRO_ANALISIS);
			lote.setEstado(estado);
			listaLotesDisponibles.add(lote);

		}
		lotePlacaVisavetBean.setListaLotesDisponibles(listaLotesDisponibles);
		BeanPlacaVisavetUCM placaVisavet = new BeanPlacaVisavetUCM();
		placaVisavet.setId(idPlaca);
		placaVisavet.setNombrePlacaVisavet(loteFormulario.getPlaca().getNombrePlacaVisavet());
		placaVisavet.setTamano(loteFormulario.getPlaca().getTamano());
		placaVisavet.setListaLotes(lotePlacaVisavetBean.getListaLotesDisponibles());
		BeanEstado estado = new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		estado.setEstado(Estado.PLACAVISAVET_INICIADA);
		placaVisavet.setEstado(estado);
		placaVisavet.setFechaCreacion(new Date());

		placaVisavet = servicioLaboratorioUni.guardarConLote(placaVisavet);

		lotePlacaVisavetBean.setPlaca(placaVisavet);
		model.addAttribute("lotePlacaVisavetBean", lotePlacaVisavetBean);
		return "VistaLotesPlacasVisavet :: trGroup";

	}

	@RequestMapping(value = "/laboratorioUni/quitarLotePlaca", method = RequestMethod.GET)

	public String quitarLotePlaca(@RequestParam("id") int idLote, @RequestParam("url") String url, Model model,
			HttpServletRequest request, HttpSession session) {

		BeanPlacaVisavetUCM placaVisavet = servicioLaboratorioUni.eliminarLotedePlaca(idLote);
		String lotes = "";
		if (placaVisavet.getListaLotes() != null && placaVisavet.getListaLotes().size() > 0) {
			for (LoteBeanPlacaVisavet lote : placaVisavet.getListaLotes()) {
				lotes += lote.getId() + ":";
			}

			return "redirect:/laboratorioUni/procesarLotes?lotes=" + lotes + "&url=" + url;
		} else {// tenemos que borrar la placa
			servicioLaboratorioUni.eliminarPlaca(placaVisavet.getId());
			return "redirect:/laboratorioUni/buscarPlacas";
		}
	}

	@RequestMapping(value = "/laboratorioUni/eliminarPlaca", method = RequestMethod.GET)

	public String eliminarPlaca(@RequestParam("id") int idPlaca, Model model, HttpServletRequest request,
			HttpSession session) {

		servicioLaboratorioUni.eliminarPlacayLotes(idPlaca);
		return "redirect:/laboratorioUni/buscarPlacas";

	}

	private Integer calcularPlacasVisavetEspera(BeanLaboratorioCentro laboratorio,
			@PageableDefault(page = 0, value = 50000000) Pageable pageable) {
		Integer suma = 0;
		BusquedaPlacasVisavetBean busqueda = new BusquedaPlacasVisavetBean();
		busqueda.setIdLaboratorioCentro(laboratorio.getId());
		Page<BeanPlacaVisavetUCM> pagina = servicioLaboratorioUni.buscarPlacas(busqueda,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), ORDENACION_PLACAS));
		List<BeanPlacaVisavetUCM> placas = pagina.getContent();
		for (BeanPlacaVisavetUCM placaLaboratorio : placas) {
			if (placaLaboratorio.getEstado().getEstado().getCodNum() == 4
					|| placaLaboratorio.getEstado().getEstado().getCodNum() == 5)
				suma++;

		}

		return suma;
	}

	@RequestMapping(value = "/laboratorioUni/consultarOcupacionLaboratorios", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public String asignarPlacasGet(@RequestParam("idPlaca") Integer idPlaca, Model model, HttpServletRequest request,
			HttpSession session, @PageableDefault(page = 0, value = 50000000) Pageable pageable) throws Exception {

		List<BeanLaboratorioCentro> laboratorios = laboratorioCentroServicio.listaLaboratoriosCentroOrdenada();

		for (BeanLaboratorioCentro laboratorio : laboratorios) {
			laboratorio.setPlacasVisavetaLaEspera(this.calcularPlacasVisavetEspera(laboratorio, pageable));
		}
		model.addAttribute("laboratorios", laboratorios);
		return "VistaListadoPlacasVisavet :: #trLaboratorio";
	}

	@RequestMapping(value = "/laboratorioUni/asignarLaboratorio", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public String asignarPlacasPost(@RequestParam("idPlaca") Integer idPlaca,
			@RequestParam("laboratorio") Integer laboratorio, Model model, HttpServletRequest request,
			HttpSession session, @PageableDefault(page = 0, value = 50000000) Pageable pageable) throws Exception {
		BeanPlacaVisavetUCM placa = servicioLaboratorioUni.buscarPlacaById(idPlaca);
		BeanEstado estado = new BeanEstado();
		estado.setEstado(Estado.PLACAVISAVET_ASIGNADA);
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		placa.setEstado(estado);
		placa.setFechaAsignadaLaboratorio(new Date());

		BeanPlacaVisavetUCM placab = servicioLaboratorioUni.guardarPlacaConLaboratorio(placa, laboratorio);
		return "redirect:/laboratorioUni/buscarPlacas";
	}

	// subida referencias
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	@RequestMapping(value = "/laboratorioUni/cargaReferencias", method = RequestMethod.GET)
	public ModelAndView cargarResultadosPlacaLaboratorio(HttpSession session,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "url", required = true) Integer url) throws Exception {
		ModelAndView vista = new ModelAndView("VistaCargarReferencias");

		// buscamos los documentos de la placa que sean de tipo RES (excel de
		// resultados)
		ElementoDocumentacionBean elementoDoc = documentoServicio.obtenerDocumentosPlacaVisavetConTipo(id, "REF");

		elementoDoc.setCodiUrl(url);
		elementoDoc.setUrlVolver("/laboratorioUni/buscarPlacas");
		vista.addObject("elementoDoc", elementoDoc);
		return vista;
	}

	@RequestMapping(value = "/laboratorioUni/guardarReferencias", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public ModelAndView guardarResultadosPlacaLaboratorio(
			@Valid @ModelAttribute("elementoDoc") ElementoDocumentacionBean bean, BindingResult result,
			RedirectAttributes redirectAttributes) throws Exception {
		if (result.hasErrors()) {
			ModelAndView vista = new ModelAndView("VistaCargarReferencias");
			vista.addObject("elementoDoc", bean);
			return vista;
		} else {
			System.out.println("El nombre de la hoja es: " + bean.getHoja());
			System.out.println("El nombre de la columna es: " + bean.getColumna());

			// guardamos las valoraciones de las muestras que ha puesto el analista en el
			// excel y si es el ultimo analista de los numAnalistas globales de la
			// aplicacion
			// entonces guardamos el resultado definitivo
			servicioLaboratorioUni.guardarReferenciasMuestraPlaca(bean);
			// guardamos el documento excel asociandolo a la placa
			documentoServicio.guardar(bean);
		}

		redirectAttributes.addFlashAttribute("mensaje", "Resultado guardado correctamente");
		return new ModelAndView(new RedirectView(
				"/laboratorioUni/cargaReferencias?id=" + bean.getId() + "&url=" + bean.getCodiUrl(), true));
	}

}
