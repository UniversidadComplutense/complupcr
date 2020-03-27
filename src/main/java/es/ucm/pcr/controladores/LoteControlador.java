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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.validadores.LoteValidador;

@Controller
@RequestMapping(value = "/centroSalud/lote")
public class LoteControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	
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
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscadorLotes(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLoteListado");
	
		LoteBusquedaBean beanBusqueda = new LoteBusquedaBean();
		
		vista.addObject("beanBusquedaLote", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscarMuestras(HttpSession session, @ModelAttribute LoteBusquedaBean beanBusqueda) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLoteListado");
		
		List<LoteListadoBean> list = new ArrayList<LoteListadoBean>();
		
		for (int i = 0; i<10; i++) {
			list.add(getBean(i));
		}
		
		vista.addObject("beanBusquedaLote", beanBusqueda);
		vista.addObject("listadoLotes", list);
		return vista;
	}
	
	/*@RequestMapping(value="/nuevo", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView nuevaMuestra(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLote");
	
		MuestraCentroBean beanMuestra = new MuestraCentroBean();
		beanMuestra.setFechaEntrada(new Date());
		
		vista.addObject("editable", muestraEditable(beanMuestra));		
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView consultaMuestras(HttpSession session, @PathVariable Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
		
		MuestraCentroBean beanMuestra = getBeanCentro(id.intValue());
	
		vista.addObject("editable", muestraEditable(beanMuestra));
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView nuevaMuestra(@Valid @ModelAttribute("beanMuestra") MuestraCentroBean beanMuestra, BindingResult result) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
	
		if (result.hasErrors()) {
			vista.addObject("beanMuestra", beanMuestra);
			return vista;			
		} else {
			// TODO - GUARDAR
			ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/", true));
			return respuesta;
		}
	}
	*/
	
	/**
	 * TODO - ESTADOS MUESTRA
	 * La muestra es editable mientras tenga estado pendiente
	 * No se puede editar si esta en un lote enviado o esta resuelta
	 * Si esta resuelta se muestran acciones de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean loteEditable(MuestraCentroBean beanMuestra) {
		return (beanMuestra.getId() == null || (beanMuestra.getId() != null && beanMuestra.getResultado().equals("Pendiente")));
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
