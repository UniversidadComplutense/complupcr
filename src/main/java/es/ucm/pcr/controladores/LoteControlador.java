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

import java.beans.PropertyEditorSupport;
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
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCM;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.validadores.LoteValidador;

@Controller
@RequestMapping(value = "/centroSalud")
public class LoteControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	// TODO - LOG, TRAZAR SERVICIOS
	// TODO - LOTE - LABORATORIO
	// TODO - LOTE - MUESTRAS
	// TODO - LOTE - ACCION, ORDENACION, PAGINACION
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "fechaEnvio");
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Autowired
	private ServicioLaboratorioVisavetUCM servicioLaboratorioVisavetUCM;
	
	@Autowired
	private LoteValidador validadorLote;
	
	private Map<Integer, String> ACCIONES_MENSAJE = Stream.of(new Object[][] { 
	    { ACCION_GUARDAR_LOTE, "Lote guardado correctamente" }, 
	    { ACCION_ENVIAR_LOTE, "Lote enviado correctamente" }, 
	    { ACCION_BORRAR_LOTE_OK, "Lote borrardo correctamente" },
	    { ACCION_BORRAR_LOTE_KO, "Se ha producido un error al borrar el lote" },
	    { ACCION_ASIGNAR_LABORATORIO, "Asignado laboratorio correctamente" }
		}).collect(Collectors.toMap(d -> (Integer) d[0], d -> (String) d[1]));
		
	public static final Integer ACCION_GUARDAR_LOTE = 1;
	public static final Integer ACCION_ENVIAR_LOTE = 2;
	public static final Integer ACCION_BORRAR_LOTE_OK = 3;
	public static final Integer ACCION_BORRAR_LOTE_KO = 4;
	public static final Integer ACCION_ASIGNAR_LABORATORIO = 5;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@InitBinder("beanLote")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session) throws Exception {  
		binder.setValidator(validadorLote);
	}
	
	@InitBinder("beanLote")
	public void initBinder(ServletRequestDataBinder binder) {
	    binder.registerCustomEditor(BeanEstado.class, "estado", new PropertyEditorSupport() {
	    	public void setAsText(String text) {
	            Integer id = Integer.parseInt(text);
	            BeanEstado beanEstado = new BeanEstado();
	            beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoLote, id);	            
	            setValue(beanEstado);               
	        }
	    });

	}
	
	@ModelAttribute("listaLaboratorios")
	public List<BeanLaboratorioVisavet> lotes() {
		return servicioLaboratorioVisavetUCM.findAll();
	}
	
	@RequestMapping(value="/lote", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLoteListado");
	
		LoteBusquedaBean beanBusqueda = new LoteBusquedaBean();
		addListsToView(vista);
		
		vista.addObject("beanBusquedaLote", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value="/lote/list", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public String buscar(HttpSession session, @ModelAttribute LoteBusquedaBean beanBusqueda) throws Exception {
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		session.setAttribute("beanBusquedaLotes", beanBusqueda);
		session.setAttribute("paginaActual", 1);
		return "redirect:/centroSalud/lote/list";
	}
	
	@RequestMapping(value="/lote/list", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView buscarGet(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLoteListado");
		
		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
		
		LoteBusquedaBean beanBusqueda = (LoteBusquedaBean)session.getAttribute("beanBusquedaLotes");
		beanBusqueda = beanBusqueda != null ? beanBusqueda : new LoteBusquedaBean();				
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		session.setAttribute("paginaActual", currentPage);
		
		buscarLotes(beanBusqueda, vista, session);
		return vista;
	}
	
	private void buscarLotes(LoteBusquedaBean beanBusqueda, ModelAndView vista, HttpSession session) {
		Integer currentPage = (Integer)session.getAttribute("paginaActual");
	
		//Sara
		beanBusqueda.setFechaEnvioFin(
			Utilidades.fechafinBuscador(beanBusqueda.getFechaEnvioFin( )));
        
		
		
		Page<LoteListadoBean> lotesPage = loteServicio.findLoteByParam(beanBusqueda, 
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION)); 
		addListsToView(vista);
		vista.addObject("beanBusquedaLote", beanBusqueda);
		vista.addObject("listadoLotes", lotesPage.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(lotesPage.getTotalPages(), currentPage, lotesPage.getTotalElements(), "/centroSalud/lote/list");		
		vista.addObject("paginadorBean", paginadorBean);
	}
	
	@RequestMapping(value="/lote/nuevo", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView nuevo(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
	
		// estado iniciado para nuevo lote
		LoteCentroBean beanLote = new LoteCentroBean();		
		
		vista.addObject("listaLaboratorios", servicioLaboratorioVisavetUCM.findAll());
		vista.addObject("editable", loteEditable(beanLote));		
		vista.addObject("beanLote", beanLote);
		return vista;
	}
	
	@RequestMapping(value="/lote/modificar", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView modificar(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
		
		LoteCentroBean beanLote = loteServicio.findById(id);
	
		vista.addObject("listaLaboratorios", servicioLaboratorioVisavetUCM.findAll());
		vista.addObject("editable", loteEditable(beanLote));	
		vista.addObject("beanLote", beanLote);
		return vista;
	}
	
	@RequestMapping(value="/lote/borrar", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView borrar(HttpSession session, @RequestParam(value = "id", required = true) Integer id, RedirectAttributes redirectAttributes) throws Exception {
		
		boolean borrado = loteServicio.borrar(id);
	
		if (borrado) {
			redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_BORRAR_LOTE_OK));
		} else {
			redirectAttributes.addFlashAttribute("mensajeError", ACCIONES_MENSAJE.get(ACCION_BORRAR_LOTE_KO));
		}
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/lote/list", true));
		return respuesta;
	}
	
	@RequestMapping(value="/lote/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView consultar(HttpSession session, HttpServletRequest request, @PathVariable Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
		
		LoteCentroBean beanLote = loteServicio.findById(id);
	
		vista.addObject("listaLaboratorios", servicioLaboratorioVisavetUCM.findAll());
		vista.addObject("editable", loteEditable(beanLote));	
		vista.addObject("beanLote", beanLote);
		return vista;
	}
	
	@RequestMapping(value="/lote/guardar", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView guardar(@Valid @ModelAttribute("beanLote") LoteCentroBean beanLote, BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
	
		if (result.hasErrors()) {
			vista.addObject("listaLaboratorios", servicioLaboratorioVisavetUCM.findAll());
			vista.addObject("editable", loteEditable(beanLote));
			vista.addObject("beanLote", beanLote);
			return vista;			
		} else {
			// guardar lote
			beanLote.setIdCentro(sesionServicio.getCentro().getId());
			beanLote.setReferenciaInternaLote("");
			loteServicio.guardar(beanLote);
			
			redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_GUARDAR_LOTE));
			ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/lote/list", true));
			return respuesta;
		}
	}
	
	@RequestMapping(value="/lote/enviado", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView loteEnviado(@ModelAttribute("beanLote") LoteCentroBean beanLote, RedirectAttributes redirectAttributes) throws Exception {
		
		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum());
		loteServicio.actualizarEstadoLote(beanLote, beanEstado);		
		// redirige a la consulta
		
		redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_ENVIAR_LOTE));
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/lote/" + beanLote.getId(), true));
		return respuesta;
	}
	
	@RequestMapping(value="/lote/enviado", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView loteEnviadoGet(@RequestParam(value = "id", required = true) Integer id, RedirectAttributes redirectAttributes) throws Exception {
		
		LoteCentroBean beanLote = loteServicio.findById(id);		
		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum());
		loteServicio.actualizarEstadoLote(beanLote, beanEstado);		
		// redirige a la lista
		
		redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_ENVIAR_LOTE));
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/lote/list", true));
		return respuesta;
	}
	
	@RequestMapping(value="/lote/asignarLaboratorio", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','CENTROSALUD')")
	public ModelAndView asignarLaboratorio(@ModelAttribute("beanLote") LoteCentroBean beanLote, RedirectAttributes redirectAttributes) throws Exception {
		
		LoteCentroBean beanLoteE = loteServicio.findById(beanLote.getId());
		beanLoteE.setIdLaboratorio(beanLote.getIdLaboratorio());
		loteServicio.guardar(beanLoteE);		
		// redirige a la lista
		
		redirectAttributes.addFlashAttribute("mensaje", ACCIONES_MENSAJE.get(ACCION_ASIGNAR_LABORATORIO));
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/lote/list", true));
		return respuesta;
	}
	
	private void addListsToView(ModelAndView vista) {
		
		vista.addObject("listaEstadosLote", BeanEstado.estadosLote());
	}	
	
	/**
	 * El lote es editable si es nuevo o si existe y NO tiene estado INICIADO o ASIGNADO_CENTRO_ANALISIS
	 * @param beanMuestra
	 * @return
	 */
	private boolean loteEditable(LoteCentroBean beanLote) {
		return (beanLote.getId() == null 
				|| (beanLote.getId() != null 
						&& (beanLote.getEstado().getEstado().getCodNum() == Estado.LOTE_INICIADO.getCodNum() 
								|| beanLote.getEstado().getEstado().getCodNum() == Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum())));		
	}
	
}
