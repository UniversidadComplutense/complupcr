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

import es.ucm.pcr.beans.BeanBusquedaMuestra;
import es.ucm.pcr.beans.BeanListadoMuestra;
import es.ucm.pcr.beans.BeanMuestraCentro;
//import es.ucm.pcr.validadores.ValidadorMuestra;


@Controller
@RequestMapping(value = "/analisis")
public class AnalisisControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	
		//@Autowired
		//private ValidadorMuestra validadorMuestra;
		
		@InitBinder
		public void initBinder(WebDataBinder binder) {
		    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		    binder.registerCustomEditor(Date.class, editor);
		}
		
//		@InitBinder("beanMuestra")
//		public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session) throws Exception {  
//			binder.setValidator(validadorMuestra);
//		}

		
		@RequestMapping(value="/", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView buscadorMuestras(HttpSession session) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestraListado");
		
			BeanBusquedaMuestra beanBusqueda = new BeanBusquedaMuestra();
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);
			return vista;
		}
		
		@RequestMapping(value="/list", method=RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView buscarMuestras(HttpSession session, @ModelAttribute BeanBusquedaMuestra beanBusqueda) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestraListado");
			
			List<BeanListadoMuestra> list = new ArrayList<BeanListadoMuestra>();
			
			for (int i = 0; i<10; i++) {
				list.add(getBean(i));
			}
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);
			vista.addObject("listadoMuestras", list);
			return vista;
		}
		
		@RequestMapping(value="/nueva", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView nuevaMuestra(HttpSession session) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestra");
		
			BeanMuestraCentro beanMuestra = new BeanMuestraCentro();
			beanMuestra.setFechaEntrada(new Date());
			
			vista.addObject("beanMuestra", beanMuestra);
			return vista;
		}
		
		@RequestMapping(value="/{id}", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView consultaMuestras(HttpSession session, @PathVariable Integer id) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestra");
			
			BeanMuestraCentro beanMuestra = getBeanCentro(id.intValue());
		
			
			vista.addObject("beanMuestra", beanMuestra);
			return vista;
		}
		
		@RequestMapping(value="/guardar", method=RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView nuevaMuestra(@Valid @ModelAttribute("beanMuestra") BeanMuestraCentro beanMuestra, BindingResult result) throws Exception {
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
		
		public BeanListadoMuestra getBean(int i) {
			BeanListadoMuestra bean = new BeanListadoMuestra();
			bean.setId(i);
			bean.setNombrePaciente("Paciente " + i);
			bean.setNhcPaciente("nhc-" + i);
			bean.setEtiquetaMuestra("etiquetaM-" + i);
			bean.setRefInternaMuestra("refInternaM-" + i);
			bean.setFechaEnvioMuestraIni(new Date());
			bean.setFechaResultadoMuestraIni(new Date());
			bean.setEstadoMuestra("Pendiente");
			if (i > 2) {
				bean.setEstadoMuestra("Negativo");
			}
			bean.setCodNumLote("codLote-" + i);
			
			return bean;
		}
		
		public BeanMuestraCentro getBeanCentro(int i) {
			BeanMuestraCentro bean = new BeanMuestraCentro();
			bean.setId(i);
			bean.setNombreApellidos("Paciente " + i);
			bean.setNhc("nhc-" + i);
			bean.setEtiqueta("etiquetaM-" + i);
			bean.setRefInterna("refInternaM-" + i);
			bean.setFechaEntrada(new Date());
			bean.setFechaResultado(new Date());
			//bean.setResultado("Pendiente");
			bean.setTipoMuestra("N");
			
			return bean;
		}
		
		/*@RequestMapping(value="", method=RequestMethod.POST)	
		public ModelAndView grabarAltaLaboratorio ( @ModelAttribute("formBeanLaboratorio") BeanLaboratorio beanLaboratorio, HttpSession session) throws Exception {

			System.out.println("Laboratorio a grabar: " + beanLaboratorio.toString());
			
			// Volvemos a grabar mas centros
			ModelAndView vista = new ModelAndView(new RedirectView("AltaLaboratorio",true));	
			return vista;
			
		}*/	


}
