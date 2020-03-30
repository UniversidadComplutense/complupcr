package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.validadores.LoteValidador;

@Controller
@RequestMapping(value = "/centroSalud")
public class LoteControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	// TODO - LOG, TRAZAR SERVICIOS
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "fechaEnvio");
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Autowired
	private LoteValidador validadorLote;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@InitBinder("beanLote")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session) throws Exception {  
		binder.setValidator(validadorLote);
	}	
	
	@RequestMapping(value="/lote", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscadorLotes(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLoteListado");
	
		LoteBusquedaBean beanBusqueda = new LoteBusquedaBean();
		addListsToView(vista);
		
		vista.addObject("beanBusquedaLote", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value="/lote/list", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscarMuestras(HttpSession session, @ModelAttribute LoteBusquedaBean beanBusqueda) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLoteListado");
		
		beanBusqueda.setIdCentro(sesionServicio.getCentro().getId());
		Page<LoteListadoBean> lotesPage = loteServicio.findLoteByParam(beanBusqueda, PageRequest.of(0, Integer.MAX_VALUE, ORDENACION)); 
		
		vista.addObject("beanBusquedaLote", beanBusqueda);
		vista.addObject("listadoLotes", lotesPage.getContent());
		return vista;
	}
	
	@RequestMapping(value="/lote/nuevo", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView nuevaMuestra(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
	
		// estado iniciado para nuevo lote
		LoteCentroBean beanLote = new LoteCentroBean(new BeanEstado(TipoEstado.EstadoLote, Estado.INICIADO), sesionServicio.getCentro().getId());		
		
		vista.addObject("editable", loteEditable(beanLote));		
		vista.addObject("beanLote", beanLote);
		return vista;
	}
	
	@RequestMapping(value="/lote/modificar", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView modificarMuestras(HttpSession session, @RequestParam(value = "id", required = true) Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
		
		LoteCentroBean beanLote = loteServicio.findById(id);
	
		vista.addObject("editable", loteEditable(beanLote));	
		vista.addObject("beanLote", beanLote);
		return vista;
	}
	
	@RequestMapping(value="/lote/guardar", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView nuevaMuestra(@Valid @ModelAttribute("beanLote") LoteCentroBean beanLote, BindingResult result) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
	
		if (result.hasErrors()) {
			vista.addObject("beanLote", beanLote);
			return vista;			
		} else {
			// guardar lote
			LoteCentroBean lote = loteServicio.guardar(beanLote);
			ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/lote/" + lote.getId(), true));
			return respuesta;
		}
	}
	
	private void addListsToView(ModelAndView vista) {
		
		// estados del lote
		List<BeanEstado> estadosLote = new ArrayList<>();
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.INICIADO));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.ASIGNADO_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.ENVIADO_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.RECIBIDO_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.PROCESADO_CENTRO_ANALISIS));
		
		vista.addObject("listaEstadosLote", estadosLote);
	}
	
	/**
	 * TODO - ESTADOS LOTE
	 * El lote es editable mientras NO tenga estado enviado
	 * @param beanMuestra
	 * @return
	 */
	private boolean loteEditable(LoteCentroBean beanLote) {
		//return (beanLote.getId() == null || (beanLote.getId() != null && beanLote.getEstado().get.equals("Pendiente")));
		return true;
	}
	
	
	public LoteListadoBean getBean(int i) {
		LoteListadoBean bean = new LoteListadoBean();
		bean.setId(i);
		bean.setNumLote("codLote-" + i);
		bean.setDescEstado("Iniciado");
		if (i > 0) {
			bean.getMuestras().add(getBeanMuestra(i));
		}
		if (i > 3) {
			bean.setDescEstado("AsignadoCentroAnalisis");
			bean.setDescLaboratorio("laboratorio-" + i);
			bean.getMuestras().add(getBeanMuestra(++i));
			bean.getMuestras().add(getBeanMuestra(++i));
		}
		if (i > 7) {
			bean.setFechaEnvio(new Date());
			bean.setDescEstado("EnviadoLaboratorio");	
			bean.setDescLaboratorio("laboratorio-" + i);
			bean.getMuestras().add(getBeanMuestra(++i));
			bean.getMuestras().add(getBeanMuestra(++i));
			bean.getMuestras().add(getBeanMuestra(++i));
			bean.getMuestras().add(getBeanMuestra(++i));
		}
		
		return bean;
	}
	
	public MuestraListadoBean getBeanMuestra(int i) {
		MuestraListadoBean bean = new MuestraListadoBean();
		bean.setId(i);
		bean.setNombrePaciente("Paciente " + i);
		bean.setNhcPaciente("nhc-" + i);
		bean.setEtiquetaMuestra("etiquetaM-" + i);
		bean.setRefInternaMuestra("refInternaM-" + i);
		bean.setFechaEnvioMuestraIni(new Date());
		bean.setFechaResultadoMuestraIni(new Date());
		bean.setEstadoMuestra("Pendiente");
		if (i > 2) {
			bean.setEstadoMuestra("Enviado");
		}
		if (i > 5) {
			bean.setEstadoMuestra("Resuelta");
		}
		bean.setCodNumLote("codLote-" + i);
		
		return bean;
	}

}
