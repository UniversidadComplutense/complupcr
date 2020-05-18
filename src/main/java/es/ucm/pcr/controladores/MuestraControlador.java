/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.MuestraServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.validadores.MuestraValidador;

@Controller
@RequestMapping(value = "/centroSalud")
public class MuestraControlador {

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(MuestraControlador.class);

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

	private Map<Integer, String> ACCIONES_MENSAJE = Stream
			.of(new Object[][] { { ACCION_GUARDAR_MUESTRA, "Muestra guardada correctamente" },
					{ ACCION_NOTIFICAR_MUESTRA, "Muestra notificada correctamente" },
					{ ACCION_BORRAR_MUESTRA_OK, "Muestra borrarda correctamente" },
					{ ACCION_BORRAR_MUESTRA_KO, "Se ha producido un error al borrar la muestra" },
					{ ACCION_VALIDAR_BORRAR_MUESTRA, "El estado del lote no permite borrar la muestra" } })
			.collect(Collectors.toMap(d -> (Integer) d[0], d -> (String) d[1]));

	public static final Integer ACCION_GUARDAR_MUESTRA = 1;
	public static final Integer ACCION_NOTIFICAR_MUESTRA = 2;
	public static final Integer ACCION_BORRAR_MUESTRA_OK = 3;
	public static final Integer ACCION_BORRAR_MUESTRA_KO = 4;
	public static final Integer ACCION_VALIDAR_BORRAR_MUESTRA = 5;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@InitBinder("beanMuestra")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(validadorMuestra);
	}

	@ModelAttribute("listaLotes")
	public List<LoteListadoBean> lotes() {
		return loteServicio.findLoteByEstados(sesionServicio.getCentro().getId(),
				BeanEstado.getEstadosLotesDisponiblesCentro());
	}

	@RequestMapping(value = "/muestra", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");

		MuestraBusquedaBean beanBusqueda = new MuestraBusquedaBean();

		addListsToView(vista);
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		return vista;
	}

	@RequestMapping(value = "/muestra/list", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public String buscar(HttpSession session, @ModelAttribute MuestraBusquedaBean beanBusqueda) throws Exception {
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		session.setAttribute("beanBusquedaMuestras", beanBusqueda);
		session.setAttribute("paginaActual", 1);
		return "redirect:/centroSalud/muestra/list";
	}

	@RequestMapping(value = "/muestra/list", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView buscarPaginado(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {
		
		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
		
		ModelAndView vista = new ModelAndView("VistaMuestraListado");

		MuestraBusquedaBean beanBusqueda = (MuestraBusquedaBean) session.getAttribute("beanBusquedaMuestras");
		beanBusqueda = beanBusqueda != null ? beanBusqueda : new MuestraBusquedaBean();
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		session.setAttribute("paginaActual", currentPage);

		buscarMuestras(beanBusqueda, vista, session);
		return vista;
	}

	private void buscarMuestras(MuestraBusquedaBean beanBusqueda, ModelAndView vista, HttpSession session) {
		Integer currentPage = (Integer)session.getAttribute("paginaActual");
		
		Page<MuestraListadoBean> muestrasPage = muestraServicio.findMuestraByParam(beanBusqueda,
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION));
		addListsToView(vista);
		
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		vista.addObject("listadoMuestras", muestrasPage.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(muestrasPage.getTotalPages(), currentPage, muestrasPage.getTotalElements(), "/centroSalud/muestra/list");		
		vista.addObject("paginadorBean", paginadorBean);
	}

	@RequestMapping(value = "/muestra/nueva", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView nueva(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");

		MuestraCentroBean beanMuestra = new MuestraCentroBean();
		beanMuestra.setFechaEntrada(new Date());

		vista.addObject("editable", muestraEditable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}

	@RequestMapping(value = "/muestra/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView consulta(HttpSession session, HttpServletRequest request, @PathVariable Integer id)
			throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");

		MuestraCentroBean beanMuestra = muestraServicio.findById(id);
		
		vista.addObject("editable", false);
		// se tienen que mostrar todos los lotes, no solo los no enviados
		vista.addObject("listaLotes", loteServicio.findLoteByParam(new LoteBusquedaBean(sesionServicio.getCentro().getId())));
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}

	@RequestMapping(value = "/muestra/modificar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView modificar(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");

		MuestraCentroBean beanMuestra = muestraServicio.findById(id);

		boolean editable = muestraEditable(beanMuestra);
		vista.addObject("editable", editable);
		if (!editable) {
			// 	se tienen que mostrar todos los lotes, no solo los no enviados
			vista.addObject("listaLotes", loteServicio.findLoteByParam(new LoteBusquedaBean(sesionServicio.getCentro().getId())));
		}
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}

	@RequestMapping(value = "/muestra/guardar", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView guardar(@Valid @ModelAttribute("beanMuestra") MuestraCentroBean beanMuestra,
			BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");

		if (result.hasErrors()) {
			vista.addObject("editable", muestraEditable(beanMuestra));
			vista.addObject("beanMuestra", beanMuestra);
			return vista;
		} else {
			beanMuestra.setIdCentro(sesionServicio.getCentro().getId());
			muestraServicio.guardar(beanMuestra);

			redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_GUARDAR_MUESTRA));
			ModelAndView respuesta = new ModelAndView(
					new RedirectView("/centroSalud/muestra/list", true));
			return respuesta;
		}
	}

	@RequestMapping(value = "/muestra/borrar", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView borrar(HttpSession session, @RequestParam(value = "id", required = true) Integer id,
			RedirectAttributes redirectAttributes) throws Exception {

		boolean muestraValidaBorrar = muestraServicio.validateBorrar(id);
		if (muestraValidaBorrar) {
			boolean borrado = muestraServicio.borrar(id);

			if (borrado) {
				redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_BORRAR_MUESTRA_OK));
			} else {
				redirectAttributes.addFlashAttribute("mensajeError", ACCIONES_MENSAJE.get(ACCION_BORRAR_MUESTRA_KO));
			}
		} else {
			redirectAttributes.addFlashAttribute("mensajeError", ACCIONES_MENSAJE.get(ACCION_VALIDAR_BORRAR_MUESTRA));
		}
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/list", true));
		return respuesta;
	}

	@RequestMapping(value = "/muestra/notificarTelefono/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView notificarTelefono(HttpSession session, @PathVariable Integer id,
			RedirectAttributes redirectAttributes) throws Exception {

		muestraServicio.actualizarNotificacionMuestra(id, false);

		// redirige a la consulta

		redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_NOTIFICAR_MUESTRA));
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}

	@RequestMapping(value = "/muestra/notificarCorreo/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView notificarCorreo(HttpSession session, @PathVariable Integer id,
			RedirectAttributes redirectAttributes) throws Exception {

		muestraServicio.actualizarNotificacionMuestra(id, true);

		// redirige a la consulta

		redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_NOTIFICAR_MUESTRA));
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}

	/**
	 * La muestra es editable mientras tenga estado iniciada o asignada a lote Si
	 * esta resuelta se muestran acciones de notificacion
	 * 
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraEditable(MuestraCentroBean beanMuestra) {
		boolean nuevaMuestra = beanMuestra.getId() == null;
		boolean tieneLote = beanMuestra.getIdLote() != null;
		Lote lote = null;
		if (tieneLote) {
			lote = loteServicio.findByIdLote(beanMuestra.getIdLote());
		}
//		boolean tieneLoteEstado = tieneLote && (beanMuestra.getIdEstadoLote().intValue() == Estado.LOTE_INICIADO.getCodNum() 
//				|| beanMuestra.getIdEstadoLote().intValue() == Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum());
		boolean tieneLoteEstado = tieneLote
				&& (lote.getEstadoLote().getId().intValue() == Estado.LOTE_INICIADO.getCodNum()
						|| lote.getEstadoLote().getId().intValue() == Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum());

		return nuevaMuestra || (!nuevaMuestra && (!tieneLote || tieneLoteEstado));
	}

	private void addListsToView(ModelAndView vista) {
		vista.addObject("listaResultadosMuestra", BeanResultado.resultadosMuestra());
	}

	/**
	 * La muestra es noficable si ya se ha resuelto y no tiene fecha de notificacion
	 * 
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraNotificable(MuestraCentroBean beanMuestra) {
		return beanMuestra.getId() != null && beanMuestra.getResultado() != null
				&& beanMuestra.getEstado().getEstado().getCodNum() == (Estado.MUESTRA_RESUELTA.getCodNum())
				&& beanMuestra.getFechaNotificacion() == null;
	}

}
