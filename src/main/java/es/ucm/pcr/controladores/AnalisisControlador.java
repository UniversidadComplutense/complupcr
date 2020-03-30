package es.ucm.pcr.controladores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanAnalisis;
import es.ucm.pcr.beans.BeanAsignacion;
import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanListaAsignaciones;
import es.ucm.pcr.beans.BeanListadoMuestraAnalisis;
import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.beans.GuardarAsignacionMuestraBean;
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
		//@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView buscadorMuestras(HttpSession session) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestraListadoAnalisis");
		
			BeanBusquedaMuestraAnalisis beanBusqueda = new BeanBusquedaMuestraAnalisis();
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);
			return vista;
		}
		
		@RequestMapping(value="/list", method=RequestMethod.POST)
		//@PreAuthorize("hasAnyRole('ADMIN')")
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
		//@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView asignarMuestra(HttpSession session, HttpServletRequest request, @RequestParam("idMuestra") Integer idMuestra) throws Exception {
			ModelAndView vista = new ModelAndView("VistaAsignarAnalistasAMuestra");
						
			String mensaje = null;
			// Comprobamos si hay mensaje enviado desde guardarAsignacion.
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			if (inputFlashMap != null) {
				mensaje = (String) inputFlashMap.get("mensaje");
				System.out.println("mensaje vale: " + mensaje);
			}
			vista.addObject("mensaje", mensaje);

			
			MuestraBean beanMuestra = getBeanMuestra(idMuestra);

			//para recoger los analistas y voluntarios seleccionados
			GuardarAsignacionMuestraBean formBeanGuardarAsignacionMuestra = new GuardarAsignacionMuestraBean(beanMuestra);
			
			//List<Integer> listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
			//listaIdsAnalistasLabSeleccionados.add(3);
			//listaIdsAnalistasLabSeleccionados.add(7);
			
			
			List<BeanUsuario> beanListadoAnalistaLab =getBeanListadoAnalistasLaboratorio();
			List<BeanUsuario> beanListadoAnalistaVol =getBeanListadoAnalistasVoluntarios();	
			
			//de los listados totales quitamos los analistaslab y analistasvol que ya tiene asignados la muestra para no mostrarlos como posibles a asignar en el desplegable
			//listaAnalistasLab
			List<BeanAsignacion> beanListadoAnalistaLabAsignados = beanMuestra.getBeanAnalisis().getBeanListaAsignaciones().getListaAnalistasLab();
			List<BeanUsuario> beanListadoAnalistaLabABorrar = new ArrayList<BeanUsuario>();
			//convierto la lista BeanAsignacion en lista BeanUsuario
			for(BeanAsignacion beanAsig: beanListadoAnalistaLabAsignados) {
				beanListadoAnalistaLabABorrar.add(beanAsig.getBeanUsuario());				
			}
			System.out.println("beanListadoAnalistaLabABorrar tiene: " + beanListadoAnalistaLabABorrar.size());
			//borro de la lista todal de analistaslab los asignados
			beanListadoAnalistaLab.removeAll(beanListadoAnalistaLabABorrar);
			
			//listaAnalistasVol			
			List<BeanAsignacion> beanListadoAnalistaVolAsignados = beanMuestra.getBeanAnalisis().getBeanListaAsignaciones().getListaAnalistasVol();
			List<BeanUsuario> beanListadoAnalistaVolABorrar = new ArrayList<BeanUsuario>();
			//convierto la lista BeanAsignacion en lista BeanUsuario
			for(BeanAsignacion beanAsig: beanListadoAnalistaVolAsignados) {
				beanListadoAnalistaVolABorrar.add(beanAsig.getBeanUsuario());				
			}
			System.out.println("beanListadoAnalistaVolABorrar tiene: " + beanListadoAnalistaVolABorrar.size());
			//borro de la lista todal de analistaslab los asignados
			beanListadoAnalistaVol.removeAll(beanListadoAnalistaVolABorrar);
			
			
			vista.addObject("beanMuestra", beanMuestra);
			vista.addObject("formBeanGuardarAsignacionMuestra", formBeanGuardarAsignacionMuestra);
			vista.addObject("beanListadoAnalistaLab", beanListadoAnalistaLab);			
			vista.addObject("beanListadoAnalistaVol", beanListadoAnalistaVol);
			return vista;
		}
		
		
		@RequestMapping(value = "/guardarAsignacion", method = RequestMethod.POST)
		public RedirectView guardarAsignacion(@ModelAttribute("formBeanGuardarAsignacionMuestra") GuardarAsignacionMuestraBean formBeanGuardarAsignacionMuestra,
				HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
			
			System.out.println("muestra id: " + formBeanGuardarAsignacionMuestra.getId());
			System.out.println("analistasLab seleccionados para asignar: " + formBeanGuardarAsignacionMuestra.getListaIdsAnalistasLabSeleccionados().toString());
			System.out.println("analistasVol seleccionados para asignar: " + formBeanGuardarAsignacionMuestra.getListaIdsAnalistasVolSeleccionados().toString());
			
			//TODO llamar a metodo de servicio que a partir de formBeanGuardarAsignacionMuestra recupere la muestra y le asigne los nuevos analistas lab y analistas vol
			
			//muestraSevicio.guardarAsignacion(formBeanGuardarAsignacionMuestra);
						
			//vuelvo al formulario de asignacion de la muestra
			String idMuestra = String.valueOf(formBeanGuardarAsignacionMuestra.getId());
			redirectAttributes.addFlashAttribute("mensaje", "Asignacion de muestra guardada");
			return new RedirectView("/analisis/asignar?idMuestra="+idMuestra, true);
		}
		
		
		//muestra pantalla al jefe para que resuelva la muestra
		@RequestMapping(value="/revisar", method=RequestMethod.GET)
		//@PreAuthorize("hasAnyRole('ADMIN')")
		public ModelAndView revisarMuestra(HttpSession session, HttpServletRequest request, @RequestParam("idMuestra") Integer idMuestra) throws Exception {
			ModelAndView vista = new ModelAndView("VistaRevisarMuestra");
						
			String mensaje = null;
			// Comprobamos si hay mensaje enviado desde guardarAsignacion.
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			if (inputFlashMap != null) {
				mensaje = (String) inputFlashMap.get("mensaje");
				System.out.println("mensaje vale: " + mensaje);
			}
			vista.addObject("mensaje", mensaje);

			
			MuestraBean beanMuestra = getBeanMuestra(idMuestra);
			//beanMuestra.setResultado("P");
			
			List<BeanElemento> beanListadoPosiblesResultadosMuestra = getBeanListadoPosiblesResultadosMuestra();
			System.out.println("el beanListadoPosiblesResultadosMuestra tiene: " + beanListadoPosiblesResultadosMuestra.toString());
			
			vista.addObject("beanMuestra", beanMuestra);
			vista.addObject("beanListadoPosiblesResultadosMuestra", beanListadoPosiblesResultadosMuestra);	
		
		
			return vista;
		}
		
		
		@RequestMapping(value = "/guardarRevision", method = RequestMethod.POST)
		public RedirectView guardarAsignacion(@ModelAttribute("beanMuestra") MuestraBean beanMuestra,
				HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
			
			System.out.println("muestra id: " + beanMuestra.getId());
			System.out.println("resultado seleccionado por el jefe: " + beanMuestra.getResultado());			
			
			//TODO llamar a metodo de servicio que a partir de beanMuestra recupere la muestra y le asigne el resultado escogido por el jefe
			
			//la muestra se cierra desde la pagina principal (creo) alli habrá que 
			//cambiar el estado de la muestra a resuelta (cerrada) con su fecha de resolucion
			//TODO actualizar estadisticas de aciertos a los asignados a la muestra
			
			//muestraSevicio.guardarResultado (beanMuestra);
						
			//vuelvo al formulario de asignacion de la muestra
			String idMuestra = String.valueOf(beanMuestra.getId());
			redirectAttributes.addFlashAttribute("mensaje", "Resultado de muestra guardado");
			return new RedirectView("/analisis/revisar?idMuestra="+idMuestra, true);
		}
		

		/*
		@GetMapping("/consultaAnalistas")
		public String consultaAnalistas(@RequestParam("id") String id, Model modelo, HttpSession session) {
			System.out.println("id vale: " + id);
			
			List<BeanUsuario> beanListadoAnalistaLab =getBeanListadoAnalistasLaboratorio();
			
			//para recoger los analistas y voluntarios seleccionados
			List<Integer> listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
						
			modelo.addAttribute("beanListadoAnalistaLab", beanListadoAnalistaLab);
			
			modelo.addAttribute("listaIdsAnalistasLabSeleccionados", listaIdsAnalistasLabSeleccionados);			
						
			return "VistaAsignarAnalistasAMuestra :: #analistasLabSeleccionado";
		}
		*/
		
		
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
			bean.setEsCerrable(true); //lo pongo a true para que esté habilitado el check
			
			
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
		
		public List<BeanElemento> getBeanListadoPosiblesResultadosMuestra() {
			List<BeanElemento> listaPosiblesResultadosMuestra = new ArrayList<BeanElemento>();		
			BeanElemento negativo = new BeanElemento();
			negativo.setCodigo(1);
			negativo.setCodigoString("N");
			negativo.setDescripcion("Negativo");
			listaPosiblesResultadosMuestra.add(negativo);
			BeanElemento positivo = new BeanElemento();
			positivo.setCodigo(2);
			positivo.setCodigoString("P");
			positivo.setDescripcion("Positivo");
			listaPosiblesResultadosMuestra.add(positivo);
			BeanElemento repetir = new BeanElemento();
			repetir.setCodigo(3);
			repetir.setCodigoString("R");
			repetir.setDescripcion("Repetir");
			listaPosiblesResultadosMuestra.add(repetir);
			BeanElemento ayuda = new BeanElemento();
			ayuda.setCodigo(4);
			ayuda.setCodigoString("A");
			ayuda.setDescripcion("Ayuda");
			listaPosiblesResultadosMuestra.add(ayuda);		
			
			return listaPosiblesResultadosMuestra;
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
