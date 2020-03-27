package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanAnalisis;
import es.ucm.pcr.beans.BeanAsignacion;
import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanListaAsignaciones;
import es.ucm.pcr.beans.BeanListadoMuestraAnalisis;
import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.beans.MuestraBean;
//import es.ucm.pcr.beans.BeanMuestraCentro;
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
			ModelAndView vista = new ModelAndView("VistaMuestraListadoAnalisis");
		
			BeanBusquedaMuestraAnalisis beanBusqueda = new BeanBusquedaMuestraAnalisis();
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);
			return vista;
		}
		
		@RequestMapping(value="/list", method=RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView buscarMuestras(HttpSession session, @ModelAttribute BeanBusquedaMuestraAnalisis beanBusqueda) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestraListadoAnalisis");
			
			List<BeanListadoMuestraAnalisis> list = new ArrayList<BeanListadoMuestraAnalisis>();
			
			for (int i = 0; i<10; i++) {
				list.add(getBean(i));
			}
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);
			vista.addObject("listadoMuestras", list);
			return vista;
		}
		
		
		@RequestMapping(value="/asignar", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView asignarMuestra(HttpSession session, @RequestParam("idMuestra") Integer idMuestra) throws Exception {
			ModelAndView vista = new ModelAndView("VistaAsignarAnalistasAMuestra");
						
			MuestraBean beanMuestra = getBeanMuestra(idMuestra);
			
			List<BeanUsuario> beanListadoAnalistaLab =getBeanListadoAnalistasLaboratorio();
			List<BeanUsuario> beanListadoAnalistaVol =getBeanListadoAnalistasVoluntarios();
			
			//para recoger los analistas y voluntarios seleccionados
			List<Integer> listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
			
			vista.addObject("beanMuestra", beanMuestra);
			vista.addObject("beanListadoAnalistaLab", beanListadoAnalistaLab);
			vista.addObject("listaIdsAnalistasLabSeleccionados", listaIdsAnalistasLabSeleccionados);
			vista.addObject("beanListadoAnalistaVol", beanListadoAnalistaVol);
			return vista;
		}
		
						
		@GetMapping("/consultaAnalistas")
		public String consultaCentrosUsuario(@RequestParam("id") String id, Model modelo, HttpSession session) {
			System.out.println("id vale: " + id);
			
			List<BeanUsuario> beanListadoAnalistaLab =getBeanListadoAnalistasLaboratorio();
						
			modelo.addAttribute("beanListadoAnalistaLab", beanListadoAnalistaLab);
						
			return "VistaAsignarAnalistasAMuestra :: #analistasLabSeleccionado";
		}
		
		
		
//		@RequestMapping(value="/nueva", method=RequestMethod.GET)
//		@PreAuthorize("hasAnyRole('ADMIN')")
//		public ModelAndView nuevaMuestra(HttpSession session) throws Exception {
//			ModelAndView vista = new ModelAndView("VistaMuestra");
//		
//			BeanMuestraCentro beanMuestra = new BeanMuestraCentro();
//			beanMuestra.setFechaEntrada(new Date());
//			
//			vista.addObject("beanMuestra", beanMuestra);
//			return vista;
//		}
		
//		@RequestMapping(value="/{id}", method=RequestMethod.GET)
//		@PreAuthorize("hasAnyRole('ADMIN')")
//		public ModelAndView consultaMuestras(HttpSession session, @PathVariable Integer id) throws Exception {
//			ModelAndView vista = new ModelAndView("VistaMuestra");
//			
//			BeanMuestraCentro beanMuestra = getBeanCentro(id.intValue());
//		
//			
//			vista.addObject("beanMuestra", beanMuestra);
//			return vista;
//		}
		
//		@RequestMapping(value="/guardar", method=RequestMethod.POST)
//		@PreAuthorize("hasAnyRole('ADMIN')")
//		public ModelAndView nuevaMuestra(@Valid @ModelAttribute("beanMuestra") BeanMuestraCentro beanMuestra, BindingResult result) throws Exception {
//			ModelAndView vista = new ModelAndView("VistaMuestra");
//		
//			if (result.hasErrors()) {
//				vista.addObject("beanMuestra", beanMuestra);
//				return vista;			
//			} else {
//				// TODO - GUARDAR
//				ModelAndView respuesta = new ModelAndView(new RedirectView("/centroSalud/muestra/", true));
//				return respuesta;
//			}
//		}
		
		public BeanListadoMuestraAnalisis getBean(int i) {
			BeanListadoMuestraAnalisis bean = new BeanListadoMuestraAnalisis();
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
		
		
		
		public List<BeanUsuario> getBeanListadoAnalistasLaboratorio() {
			List<BeanUsuario> listaAnalistas = new ArrayList<BeanUsuario>();		
			// 15 analistas 
			for(int j=0;j<15; j++) {				
				BeanUsuario ana = new BeanUsuario();
				ana.setId(j);
				ana.setNom("analista-" + j);
				ana.setRol("ANALISTA_LAB");
				//TODO aciertos y posibles				
				listaAnalistas.add(ana);				
			}
			return listaAnalistas;
		}
		
		public List<BeanUsuario> getBeanListadoAnalistasVoluntarios() {
			List<BeanUsuario> listaVoluntarios = new ArrayList<BeanUsuario>();		
			// 20 voluntarios 
			for(int j=0;j<20; j++) {				
				BeanUsuario vol = new BeanUsuario();
				vol.setId(j);
				vol.setNom("voluntario-" + j);
				vol.setRol("ANALISTA_VOLUNTARIO");
				//TODO aciertos y posibles				
				listaVoluntarios.add(vol);				
			}
			return listaVoluntarios;
		}
		
		
		
		
		
		public MuestraBean getBeanMuestra(int i) {
			MuestraBean bean = new MuestraBean();
			bean.setId(i);
			bean.setEtiqueta("etiquetaM-" + i);
			bean.setTipoMuestra("tipoMuestra-" + i);
			//bean.setFecha(fecha);
			//bean.setReferenciaInterna(referenciaInterna);
			//bean.setEstado(estado); //pendiente de analizar
			Date date = new Date(); // your date
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
			BeanAnalisis beanAnalisis  = new BeanAnalisis();
			BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
			//usuarios que analizan, 2 analistas y 2 voluntario
			for(int j=0;j<2; j++) {
				BeanAsignacion beanAsigAna = new BeanAsignacion();
				BeanUsuario ana = new BeanUsuario();
				ana.setId(j);
				ana.setNom("analista-" + j);
				ana.setRol("ANALISTA_LAB");
				beanAsigAna.setBeanUsuario(ana);				
				beanAsigAna.setFechaAsignacion(cal);
				beanAsigAna.setValoracion("P");
				beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);
				
				BeanAsignacion beanAsigVol = new BeanAsignacion();
				BeanUsuario vol = new BeanUsuario();
				vol.setId(j);
				vol.setNom("voluntario-" + j);
				vol.setRol("ANALISTA_VOLUNTARIO");
				beanAsigVol.setBeanUsuario(vol);				
				beanAsigVol.setFechaAsignacion(cal);
				beanAsigVol.setValoracion("N");
				beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
			}
			beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
			bean.setBeanAnalisis(beanAnalisis);					
			
			return bean;
		}
		
		
		
		
//		public BeanMuestraCentro getBeanCentro(int i) {
//			BeanMuestraCentro bean = new BeanMuestraCentro();
//			bean.setId(i);
//			bean.setNombreApellidos("Paciente " + i);
//			bean.setNhc("nhc-" + i);
//			bean.setEtiqueta("etiquetaM-" + i);
//			bean.setRefInterna("refInternaM-" + i);
//			bean.setFechaEntrada(new Date());
//			bean.setFechaResultado(new Date());
//			//bean.setResultado("Pendiente");
//			bean.setTipoMuestra("N");
//			
//			return bean;
//		}
		
		/*@RequestMapping(value="", method=RequestMethod.POST)	
		public ModelAndView grabarAltaLaboratorio ( @ModelAttribute("formBeanLaboratorio") BeanLaboratorio beanLaboratorio, HttpSession session) throws Exception {

			System.out.println("Laboratorio a grabar: " + beanLaboratorio.toString());
			
			// Volvemos a grabar mas centros
			ModelAndView vista = new ModelAndView(new RedirectView("AltaLaboratorio",true));	
			return vista;
			
		}*/	


}
