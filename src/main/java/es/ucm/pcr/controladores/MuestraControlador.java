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

import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.validadores.MuestraValidador;

@Controller
@RequestMapping(value = "/centroSalud")
public class MuestraControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	
	@Autowired
	private MuestraValidador validadorMuestra;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@InitBinder("beanMuestra")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session) throws Exception {  
		binder.setValidator(validadorMuestra);
	}
	
	@RequestMapping(value="/muestra", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscadorMuestras(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");
	
		MuestraBusquedaBean beanBusqueda = new MuestraBusquedaBean();
		
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value="/muestra/list", method=RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView buscarMuestras(HttpSession session, @ModelAttribute MuestraBusquedaBean beanBusqueda) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestraListado");
		
		List<MuestraListadoBean> list = new ArrayList<MuestraListadoBean>();
		
		for (int i = 0; i<10; i++) {
			list.add(getBean(i));
		}
		
		vista.addObject("beanBusquedaMuestra", beanBusqueda);
		vista.addObject("listadoMuestras", list);
		return vista;
	}
	
	@RequestMapping(value="/muestra/nueva", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView nuevaMuestra(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
	
		MuestraCentroBean beanMuestra = new MuestraCentroBean();
		beanMuestra.setFechaEntrada(new Date());
		
		vista.addObject("editable", muestraEditable(beanMuestra));		
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView consultaMuestras(HttpSession session, @PathVariable Integer id) throws Exception {
		ModelAndView vista = new ModelAndView("VistaMuestra");
		
		MuestraCentroBean beanMuestra = getBeanCentro(id.intValue());
	
		vista.addObject("editable", muestraEditable(beanMuestra));
		vista.addObject("notificable", muestraNotificable(beanMuestra));
		vista.addObject("beanMuestra", beanMuestra);
		return vista;
	}
	
	@RequestMapping(value="/muestra/guardar", method=RequestMethod.POST)
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
	
	@RequestMapping(value="/notificarTelefono/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView notificarTelefono(HttpSession session, @PathVariable Integer id) throws Exception {
		// TODO - llamada servicio rellenar fecha notificacion
		
		// redirige a la consulta
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}
	
	@RequestMapping(value="/notificarCorreo/{id}", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ModelAndView notificarCorreo(HttpSession session, @PathVariable Integer id) throws Exception {
		// TODO - llamada servicio envio correo y rellenar fecha notificacion		
		
		// redirige a la consulta		
		ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/" + id, true));
		return respuesta;
	}
	
	/**
	 * TODO - ESTADOS MUESTRA
	 * La muestra es editable mientras tenga estado pendiente
	 * No se puede editar si esta en un lote enviado o esta resuelta
	 * Si esta resuelta se muestran acciones de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraEditable(MuestraCentroBean beanMuestra) {
		return (beanMuestra.getId() == null || (beanMuestra.getId() != null && beanMuestra.getResultado().equals("Pendiente")));
	}
	
	/**
	 * La muestra es noficable si ya se ha resuelto y no tiene fecha de notificacion
	 * @param beanMuestra
	 * @return
	 */
	private boolean muestraNotificable(MuestraCentroBean beanMuestra) {
		return beanMuestra.getId() != null && beanMuestra.getResultado().equals("Resuelta") && beanMuestra.getFechaNotificacion() == null;
	}
	
	public MuestraListadoBean getBean(int i) {
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
	
	public MuestraCentroBean getBeanCentro(int i) {
		MuestraCentroBean bean = new MuestraCentroBean();
		bean.setId(i);
		bean.setNombreApellidos("Paciente " + i);
		bean.setNhc("nhc-" + i);
		bean.setEtiqueta("etiquetaM-" + i);
		bean.setRefInterna("refInternaM-" + i);
		bean.setFechaEntrada(new Date());
		bean.setFechaResultado(new Date());
		bean.setResultado("Pendiente");
		if (i > 2) {
			bean.setResultado("Enviado");
		}
		if (i > 5) {
			bean.setResultado("Resuelta");
		}
		bean.setTipoMuestra("N");
		
		return bean;
	}

}
