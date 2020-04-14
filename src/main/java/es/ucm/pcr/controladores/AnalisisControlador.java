package es.ucm.pcr.controladores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.BeanRolUsuario;
import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioAnalistaBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.beans.GuardarAsignacionMuestraBean;
import es.ucm.pcr.beans.GuardarAsignacionPlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.GuardarCogerYDevolverPlacasBean;
import es.ucm.pcr.beans.MuestraBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesBean;
//import es.ucm.pcr.beans.BeanMuestraCentro;
//import es.ucm.pcr.validadores.ValidadorMuestra;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.BeanResultado.ResultadoMuestra;
import es.ucm.pcr.beans.BeanRolUsuario.RolUsuario;
import es.ucm.pcr.config.security.PcrUserDetails;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.MuestraServicio;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.servicios.UsuarioServicio;


@Controller
@RequestMapping(value = "/analisis")
public class AnalisisControlador {
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private LaboratorioCentroServicio laboratorioCentroServicio;
	
	@Autowired
	private MuestraServicio muestraServicio;
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "etiqueta");
	
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
				
		@ModelAttribute("beanListaPlacasDeJefe")
		public  List<BeanElemento> rellenaListaPlacasJefe() {
			
			//si el usuario tiene rol jefe cargará la lista con las placas que el jefe cogió bajo su responsabilidad
			List<BeanElemento> beanListaPlacasDeJefe = laboratorioCentroServicio.buscarPlacasBeanElementoAsignadasAJefe(sesionServicio.getUsuario());			
			return beanListaPlacasDeJefe;
		}
		
		/**
		 * Posibles Resultados de la muestra
		 * 
		 * @return
		 */
		public static List<BeanResultado> resultadosMuestra() {
			// posibles resultados de la muestra
			List<BeanResultado> estadosMuestra = new ArrayList<>();
			estadosMuestra.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE));
			estadosMuestra.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_NEGATIVO));
			estadosMuestra.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_POSITIVO));
			estadosMuestra.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_DEBIL));
			estadosMuestra.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_REPETIR));

			return estadosMuestra;
		}
				
		@ModelAttribute("beanListaPosiblesResultadosMuestra")
		public  List<BeanResultado> rellenaListaPosiblesResultadosMuestra() {
			List<BeanResultado> listaPosiblesResultadosMuestra =  BeanResultado.resultadosMuestra();
			return listaPosiblesResultadosMuestra;
		}
		
		
		
		
		@RequestMapping(value="/", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public ModelAndView buscadorMuestras(HttpSession session) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestraListadoAnalisis");
		
			BeanBusquedaMuestraAnalisis beanBusqueda = new BeanBusquedaMuestraAnalisis();
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);
			
			return vista;
		}
		
		
		@RequestMapping(value="/list", method=RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public ModelAndView buscarMuestras(HttpSession session, @ModelAttribute BeanBusquedaMuestraAnalisis beanBusqueda) throws Exception {
			ModelAndView vista = new ModelAndView("VistaMuestraListadoAnalisis");
			
			//solo mostraremos al jefe las muestras de placas que ha cogido bajo su responsabilidad, (que solo podran ser del centro del jefe, ya que solo le hemos dejado coger placas de su centro)
			beanBusqueda.setIdJefePlaca(sesionServicio.getUsuario().getId()); //id del usuario logado (el jefe)			
			Page<BeanListadoMuestraAnalisis> muestrasPage = muestraServicio.findMuestraByParam(beanBusqueda, PageRequest.of(0, Integer.MAX_VALUE, ORDENACION));
						
			
			vista.addObject("beanBusquedaMuestra", beanBusqueda);			
			vista.addObject("listadoMuestras", muestrasPage);
			return vista;
		}
		
		//metodos de asignacion de muestras a analistas
		
		@RequestMapping(value="/asignar", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
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
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
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
		//fin metodos de asignacion de muestras a analistas
		
		//muestra pantalla al jefe para que resuelva la muestra
		@RequestMapping(value="/revisar", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
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
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
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
		
		//añadido Diana, findByClaves recibe una cadena de codnum's de muestras y devuelve la lista tipo BeanListadoMuestraAnalisis de los registros con esos codnums		
		public List<BeanListadoMuestraAnalisis> findByClaves(String claves) {
			List<BeanListadoMuestraAnalisis> listaBeanListadoMuestraAnalisis = new ArrayList<BeanListadoMuestraAnalisis>();
			
			String[] parts = claves.split(",");
			for(String codnumMuestra: parts) {
				Integer codnumLeeInt = Integer.parseInt(codnumMuestra);
				System.out.println("el codnum vale: " + codnumLeeInt);
				BeanListadoMuestraAnalisis beanElementList = this.getBean(codnumLeeInt);
				listaBeanListadoMuestraAnalisis.add(beanElementList);
				
			}
			return listaBeanListadoMuestraAnalisis;
		}
		
		
		//metodo que recibe la lista de codnums de muestras que se han marcado  para cerrar
		//busca sus datos y los muestra en el modal
		@PostMapping("/buscarMuestrasACerrar")
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public String buscarMuestrasACerrar(@ModelAttribute("beanBusquedaMuestra") BeanBusquedaMuestraAnalisis beanBusquedaMuestra,			
				BindingResult result, HttpServletRequest req, Model model, Locale locale) {
			
			//metodo que recibe los codnum de muestras en los que se ha marcado el checkbox para cerrarlas
			//busca esos resgistros, y presenta sus datos en una lista
			System.out.println("Diana- postMapping buscarMuestrasACerrar");				
			
			String claves = req.getParameter("claves");				
			System.out.println("lista de claves que tienen check activado: " + claves);
			
			//ESTOY AKIIIII
			List<BeanListadoMuestraAnalisis> beanListadoMuestraAnalisis = this.findByClaves(claves);
			
			System.out.println("beanListadoMuestraAnalisis tiene: " + beanListadoMuestraAnalisis.size());
				
			model.addAttribute("listadoMuestras", beanListadoMuestraAnalisis);
			
			return "VistaMuestrasMarcadasParaCerrar";
		}
		
		
		
		@PostMapping("/ejecutarCierreMuestras")
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public ModelAndView ejecutarCierreMuestras(@ModelAttribute("beanBusquedaMuestra") BeanBusquedaMuestraAnalisis beanBusquedaMuestra,			
				BindingResult result, HttpServletRequest req, Model model, Locale locale, HttpSession session) throws Exception {
			
			//metodo que recibe los codnum de muestras en los que se ha marcado el checkbox para cerrar
			//busca esos resgistros, los actualiza y los guarda
			
			System.out.println("Diana post mapping ejecutarCierreMuestras ");

			/*
			if (result.hasErrors()) {
				System.out.println("errores de validacion del formulario al ejecutar llamamiento");
				return ListaEsperaController.VIEW_LIST_FORM;			
			} else {
			*/					
				
				String claves = req.getParameter("claves");
				StringTokenizer codnums = new StringTokenizer(claves, ",");			
				System.out.println("lista de claves que tienen check activado: " + claves);
				
				//TODO cambiar el estado de las muesetras a resueltas con fecha de resolucion, (cerradas por el jefe)
				//y recalcular aciertos y posibles
						
				
				//recorremos las muestras para los que han marcado el check
				/*
				while (codnums.hasMoreTokens()) {
					Long codnum = Long.parseLong(codnums.nextToken());
					try {					
						//solo si NO tiene ya una L en el llamamiento (si no ha sido llamado previamente)
						//guardamos la L de llamado en el registro de lista espera entry y volvemos a ejecutar la busqueda
						
						ListaEsperaEntry lee = listaEsperaEntryService.findByCodnum(codnum);
						
						//rellenamos el resto de datos del wrapper para poder guardar todos los datos en las entidades interesllamamiento y llamamiento
						//LlamamientoWrapper llamamientoWrapper = new LlamamientoWrapper();
						
						UsuarioBis autorLlamamiento = (UsuarioBis) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						llamamientoWrapper.setAutorLlamamiento(autorLlamamiento);
						llamamientoWrapper.setFechaHoraLlamamiento(new Date());
						llamamientoWrapper.setEstadoLlamamiento(AdmdumConstantes.LLAMADO_VALUE);
						//llamamientoWrapper.setEstudio(lee.getEstudio());
						//llamamientoWrapper.setPeriodoPreinscripcion(lee.getListaEspera().getPeriodoPreinscripcion());
						llamamientoWrapper.setUsuarioPreinscripcion(lee.getUsuarioPreinscripcion());
						llamamientoWrapper.setListaEspera(lee.getListaEspera());
											
						//guardamos el estado llamamiento (Llamado, fecha y autor) en el registro de interesllamamiento con su llamamiento
						//creamos un nuevo recurso si no tiene un recurso positivo
						//y volvemos a ejecutar la busqueda
						if (interesLlamamientoService.saveLlamamiento(llamamientoWrapper)==true) {
							//System.out.println("guardado el llamamiento del registo de lee con codnum: "+codnum);
							//recurso
							//si hemos guardado correctamente el llamamiento, y su estado de recurso no es admitido, damos de alta su recurso de admision positiva
							// Consultar el estado de un recurso
							
							UsuarioBis usuarioRecurso = lee.getUsuarioPreinscripcion();
							PeriodoPreinscripcion periodoPreinscripcionRecurso = findCommand.getPeriodoPreinscripcion();
							EstudioBis estudioRecurso = this.estudioService.findByCodnum(findCommand.getEstudio().getCodnum());
													
							// Damos de alta un recurso de admisión positiva si no existía ya un recurso positivo
							PreinscripcionRecurso nuevoRecurso =  this.preinscripcionRecursoService.createRecursoLlamamientoListaEspera(usuarioRecurso, periodoPreinscripcionRecurso, estudioRecurso);
							
						}
						else {
							System.out.println("error al guardar el llamamiento del registo de lee con codnum: "+codnum);
							model.addAttribute("mensaje",
									this.messageSource.getMessage("gestionlistaEspera.lista.actualizar.error", null, this.locale));
						}					
						
					} catch (Exception e) {
						model.addAttribute("mensaje",
								this.messageSource.getMessage("gestionlistaEspera.lista.actualizar.error", null, this.locale));
					}
				}*/
				
				//return ejecutaBusqueda(findCommand, model); //REVISAAAAR
				
				return buscarMuestras(session, beanBusquedaMuestra);
			
			
		}
		
		//FUNCIONALIDAD JEFE: COGER PLACAS Y ASIGNAR ANALISTAS A PLACAS
		
		//gestionar lotes (en realidad es gestionar placas)		
		@RequestMapping(value = "/cogerPlacas", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public ModelAndView buscarPlacasinAsignarYBajoResponsabilidadGET(HttpSession session, HttpServletRequest request, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

			ModelAndView vista = new ModelAndView("ListadoPlacasSinAsignarYBajoResponsabilidad");
			
			String mensajeCoger = null;
			String mensajeDevolver = null;
			// Comprobamos si hay mensajes enviados desde los posts
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			if (inputFlashMap != null) {
				mensajeCoger = (String) inputFlashMap.get("mensajeCoger");
				System.out.println("mensajeCoger vale: " + mensajeCoger);
				mensajeDevolver = (String) inputFlashMap.get("mensajeDevolver");
				System.out.println("mensajeDevolver vale: " + mensajeDevolver);
			}
			vista.addObject("mensajeCoger", mensajeCoger);
			vista.addObject("mensajeDevolver", mensajeDevolver);
			
			//recupero el usuario logado			
			Usuario user = sesionServicio.getUsuario();
			System.out.println("usuario logado: " + user.getNombre() + " del idLaboratorioCentro: " + user.getIdLaboratorioCentro());

			// Buscamos las placas con estado 'Lista para análisis' (ya han salido de la maquina, tienen cargado un resultado pcr y estan listas para analizar)
			// del idLaboratorioCentro del usuario jefe logado
			BusquedaPlacaLaboratorioJefeBean criteriosBusquedaPlacaListaParaAnalisis = new BusquedaPlacaLaboratorioJefeBean();			
			criteriosBusquedaPlacaListaParaAnalisis.setIdEstadoPlaca(BeanEstado.Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum());
			criteriosBusquedaPlacaListaParaAnalisis.setIdLaboratorioCentro(user.getIdLaboratorioCentro());
			List<PlacaLaboratorioCentroBean> listaPlacasListasParaAnalisis = laboratorioCentroServicio.buscarPlacas(criteriosBusquedaPlacaListaParaAnalisis, pageable).getContent();
			System.out.println("listaPlacasListasParaAnalisis tiene: "+ listaPlacasListasParaAnalisis.size());
			
			// Buscamos las placas con estado 'Asignada para análisis' (un jefe ya las ha puesto bajo su responsabilidad)
			// del centro del usuario jefe logado 
			// y que esten asignadas al jefe logado
			BusquedaPlacaLaboratorioJefeBean criteriosBusquedaPlacaAsignadaParaAnalisis = new BusquedaPlacaLaboratorioJefeBean();			
			criteriosBusquedaPlacaAsignadaParaAnalisis.setIdEstadoPlaca(BeanEstado.Estado.PLACA_ASIGNADA_PARA_ANALISIS.getCodNum());
			criteriosBusquedaPlacaListaParaAnalisis.setIdLaboratorioCentro(user.getIdLaboratorioCentro());
			criteriosBusquedaPlacaAsignadaParaAnalisis.setIdJefe(user.getId());
			List<PlacaLaboratorioCentroBean> listaPlacasAsignadasParaAnalisis = laboratorioCentroServicio.buscarPlacas(criteriosBusquedaPlacaAsignadaParaAnalisis, pageable).getContent();
			System.out.println("listaPlacasAsignadasParaAnalisis tiene: "+ listaPlacasAsignadasParaAnalisis.size());			
			
			GuardarCogerYDevolverPlacasBean guardarCogerYDevolverPlacasBean = new GuardarCogerYDevolverPlacasBean();
			
			vista.addObject("listaPlacasListasParaAnalisis", listaPlacasListasParaAnalisis);
			vista.addObject("listaPlacasAsignadasParaAnalisis", listaPlacasAsignadasParaAnalisis);
			vista.addObject("guardarCogerYDevolverPlacasBean", guardarCogerYDevolverPlacasBean);			
			
			
			return vista;

		}
		
		//boton "reclamar los seleccionados"
		@RequestMapping(value = "/cogerPlacas", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public RedirectView cogerPlacasSeleccionadas(HttpSession session, @ModelAttribute GuardarCogerYDevolverPlacasBean guardarCogerYDevolverPlacasBean, 
				@PageableDefault(page = 0, value = 20) Pageable pageable, RedirectAttributes redirectAttributes) throws Exception {

			//recupero el usuario logado			
			Usuario user = sesionServicio.getUsuario();
			System.out.println("usuario logado: " + user.getNombre() + " del idLaboratorioCentro: " + user.getIdLaboratorioCentro());
						
			//recogemos las placas marcadas para coger
			List<Integer> listaIdsPlacasSeleccionadosParaCoger = guardarCogerYDevolverPlacasBean.getListaIdsPlacasSeleccionadosParaCoger();
			System.out.println("listaIdsPlacasSeleccionadosParaCoger: " + listaIdsPlacasSeleccionadosParaCoger.toString());
			
			//cogemos esas placas, les asignamos el jefe logado, fecha de asignacion, cambiamos estado a PLACA_ASIGNADA_PARA_ANALISIS y las guardamos
			//TODO coger las muestras de esa placa y ponerles estado pendente de analizar
			for(Integer idPlacaSeleccionada : listaIdsPlacasSeleccionadosParaCoger) {
				laboratorioCentroServicio.guardarCogerODevolverPlaca(idPlacaSeleccionada, user.getId(), "coger");
			}						
			
			redirectAttributes.addFlashAttribute("mensajeCoger", "Placas reclamadas");
			return new RedirectView("/analisis/cogerPlacas", true);			
			
		}
		
		//boton "devolver los seleccionados"
		@RequestMapping(value = "/devolverPlacas", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public RedirectView devolverPlacasSeleccionadas(HttpSession session, @ModelAttribute GuardarCogerYDevolverPlacasBean guardarCogerYDevolverPlacasBean, 
				@PageableDefault(page = 0, value = 20) Pageable pageable, RedirectAttributes redirectAttributes) throws Exception {

			//recogemos las placas marcadas para devolver
			List<Integer> listaIdsPlacasSeleccionadosParaDevolver = guardarCogerYDevolverPlacasBean.getListaIdsPlacasSeleccionadosParaDevolver();
			System.out.println("listaIdsPlacasSeleccionadosParaDevolver: " + listaIdsPlacasSeleccionadosParaDevolver.toString());
			
			//devolver esas placas, quitarles el jefe asignado, fecha de asignacion, cambiar estado a placa a PLACA_LISTA_PARA_ANALISIS y guardarlas
			//TODO ¿que hacemos con las muestras?, las ponemos en el estado anterior "enviada centro analisis"			
			for(Integer idPlacaSeleccionada : listaIdsPlacasSeleccionadosParaDevolver) {
				laboratorioCentroServicio.guardarCogerODevolverPlaca(idPlacaSeleccionada, null, "devolver");
			}	
						
			
			redirectAttributes.addFlashAttribute("mensajeDevolver", "Placas devueltas");
			return new RedirectView("/analisis/cogerPlacas", true);			
			
		}
		
		//metodos de asignacion de analistas a placas
		
		@RequestMapping(value="/asignarPlaca", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public ModelAndView asignarPlaca(HttpSession session, HttpServletRequest request, @RequestParam("idPlaca") Integer idPlaca) throws Exception {
			ModelAndView vista = new ModelAndView("VistaAsignarAnalistasAPlaca");
						
			String mensaje = null;
			// Comprobamos si hay mensaje enviado desde guardarAsignacion.
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			if (inputFlashMap != null) {
				mensaje = (String) inputFlashMap.get("mensaje");
				System.out.println("mensaje vale: " + mensaje);
			}
			vista.addObject("mensaje", mensaje);

			PlacaLaboratorioCentroAsignacionesBean placaLaboratorioCentroAsignacionesBean=laboratorioCentroServicio.buscarPlacaAsignaciones(idPlaca); 
			

			//para recoger los analistas y voluntarios seleccionados
			GuardarAsignacionPlacaLaboratorioCentroBean formBeanGuardarAsignacionPlacaLaboratorioCentro = new GuardarAsignacionPlacaLaboratorioCentroBean();
			formBeanGuardarAsignacionPlacaLaboratorioCentro.setIdPlaca(idPlaca);
								
			//obtenemos los listados de analistas del laboratorio, voluntarios del laboratorio y voluntarios sin laboratorio
			
			//recupero el usuario logado			
			Usuario user = sesionServicio.getUsuario();
			System.out.println("usuario logado: " + user.getNombre() + " del idLaboratorioCentro: " + user.getIdLaboratorioCentro());
			
			//List<BeanUsuario> beanListadoAnalistaLab =getBeanListadoAnalistasLaboratorio();
			List<BeanUsuario> beanListadoAnalistaLab = usuarioServicio.listaUsuariosAnalistasDeLaboratorioCentro(user.getIdLaboratorioCentro()) ;
			List<BeanUsuario> beanListadoAnalistaVol = usuarioServicio.listaUsuariosVoluntariosDeLaboratorioCentro(user.getIdLaboratorioCentro());	
			List<BeanUsuario> beanListadoVoluntariosSinLaboratorioCentro = usuarioServicio.listaUsuariosVoluntariosSinLaboratorioCentro();	
			
			//de los listados totales quitamos los analistaslab y analistasvol que ya tiene asignados la placa para no mostrarlos como posibles a asignar en el desplegable
			//listaAnalistasLab
			List<BeanAsignacion> beanListadoAnalistaLabAsignados = placaLaboratorioCentroAsignacionesBean.getBeanAnalisis().getBeanListaAsignaciones().getListaAnalistasLab();
			List<BeanUsuario> beanListadoAnalistaLabABorrar = new ArrayList<BeanUsuario>();
			//convierto la lista BeanAsignacion en lista BeanUsuario
			for(BeanAsignacion beanAsig: beanListadoAnalistaLabAsignados) {
				beanListadoAnalistaLabABorrar.add(beanAsig.getBeanUsuario());				
			}
			System.out.println("beanListadoAnalistaLabABorrar tiene: " + beanListadoAnalistaLabABorrar.size());
			//borro de la lista todal de analistaslab los asignados
			beanListadoAnalistaLab.removeAll(beanListadoAnalistaLabABorrar);
			
			//listaAnalistasVol			
			List<BeanAsignacion> beanListadoAnalistaVolAsignados = placaLaboratorioCentroAsignacionesBean.getBeanAnalisis().getBeanListaAsignaciones().getListaAnalistasVol();
			List<BeanUsuario> beanListadoAnalistaVolABorrar = new ArrayList<BeanUsuario>();
			//convierto la lista BeanAsignacion en lista BeanUsuario
			for(BeanAsignacion beanAsig: beanListadoAnalistaVolAsignados) {
				beanListadoAnalistaVolABorrar.add(beanAsig.getBeanUsuario());				
			}
			System.out.println("beanListadoAnalistaVolABorrar tiene: " + beanListadoAnalistaVolABorrar.size());
			//borro de la lista todal de analistaslab los asignados
			beanListadoAnalistaVol.removeAll(beanListadoAnalistaVolABorrar);
			
			
			//listaAnalistasVolSinCentro			
			List<BeanAsignacion> beanListadoAnalistaVolSinLabCentroAsignados = placaLaboratorioCentroAsignacionesBean.getBeanAnalisis().getBeanListaAsignaciones().getListaAnalistasVolSinLabCentro();
			List<BeanUsuario> beanListadoAnalistaVolSinCentroABorrar = new ArrayList<BeanUsuario>();
			//convierto la lista BeanAsignacion en lista BeanUsuario
			for(BeanAsignacion beanAsig: beanListadoAnalistaVolSinLabCentroAsignados) {
				beanListadoAnalistaVolSinCentroABorrar.add(beanAsig.getBeanUsuario());				
			}
			System.out.println("beanListadoAnalistaVolSinCentroABorrar tiene: " + beanListadoAnalistaVolSinCentroABorrar.size());
			//borro de la lista todal de analistas los asignados
			beanListadoVoluntariosSinLaboratorioCentro.removeAll(beanListadoAnalistaVolSinCentroABorrar);
			
			//no se puede asignar nuevos analistas a la placa si la placa tiene ya todas sus muestras con resultado definitivo
			Boolean noSePuedeAsignarNuevosAnalistasAPlaca = laboratorioCentroServicio.tienenResultadoDefinitivoLasMuestrasDeLaPlaca(idPlaca);
			
			
			vista.addObject("placaLaboratorioCentroAsignacionesBean", placaLaboratorioCentroAsignacionesBean);
			vista.addObject("formBeanGuardarAsignacionPlaca", formBeanGuardarAsignacionPlacaLaboratorioCentro);
			vista.addObject("beanListadoAnalistaLab", beanListadoAnalistaLab);			
			vista.addObject("beanListadoAnalistaVol", beanListadoAnalistaVol);
			vista.addObject("beanListadoVoluntariosSinLaboratorioCentro", beanListadoVoluntariosSinLaboratorioCentro);
			vista.addObject("noSePuedeAsignarNuevosAnalistasAPlaca", noSePuedeAsignarNuevosAnalistasAPlaca);
			return vista;
		}
		
		
		@RequestMapping(value = "/guardarAsignacionPlaca", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public RedirectView guardarAsignacionPlaca(@ModelAttribute("formBeanGuardarAsignacionPlaca") GuardarAsignacionPlacaLaboratorioCentroBean formBeanGuardarAsignacionPlaca,
				HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
			
			System.out.println("placa id: " + formBeanGuardarAsignacionPlaca.getIdPlaca());
			System.out.println("analistas de labCentro seleccionados para asignar: " + formBeanGuardarAsignacionPlaca.getListaIdsAnalistasLabSeleccionados().toString());
			System.out.println("voluntarios de labCentro seleccionados para asignar: " + formBeanGuardarAsignacionPlaca.getListaIdsAnalistasVolSeleccionados().toString());
			System.out.println("voluntarios sin labCentro seleccionados para asignar: " + formBeanGuardarAsignacionPlaca.getListaIdsVolSinLabCentroSeleccionados().toString());
			
			//llamamos a metodo de servicio que a partir de formBeanGuardarAsignacionPlaca recupere la placa y le asigne a todas sus muestras los nuevos analistas lab, analistas vol y otros vol
			//y cambie el estado de las muestras a asignada analista			
			laboratorioCentroServicio.guardarAsignacionesAnalistasYVoluntariosAPlacaYmuestras(formBeanGuardarAsignacionPlaca);
			
						
			//vuelvo al formulario de asignacion de la placa
			String idPlaca = String.valueOf(formBeanGuardarAsignacionPlaca.getIdPlaca());
			redirectAttributes.addFlashAttribute("mensaje", "Asignaciones de placa guardadas");
			return new RedirectView("/analisis/asignarPlaca?idPlaca="+idPlaca, true);
		}
		

		
		//fin metodos de asignacion de analistas a placas

		
		
		//FIN FUNCIONALIDAD JEFE: COGER PLACAS Y ASIGNAR ANALISTAS A PLACAS
		
		
		//ANALISTA O VOLUNTARIOS (SOLO ANALIZAN)
		
		
		//ANALISTAS- REVISAR PLACA		
		
		@RequestMapping(value = "/listarPlacasAnalista", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','ANALISTALABORATORIO')")
		public ModelAndView listarPlacasAsignadasAnalistaGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

			ModelAndView vista = new ModelAndView("ListadoPlacasARevisarAnalista");
			
			System.out.println("estoy en listarPlacasAsignadasAnalistaGET");
			
			//recupero el usuario logado			
			Usuario user = sesionServicio.getUsuario();
			System.out.println("usuario logado: " + user.getNombre() + " del idLaboratorioCentro: " + user.getIdLaboratorioCentro());
			
			//buscamos las placas pendientes de revisar por el analista
			//serán aquellas placas cuyas muestras hayan sido asignadas al usuario logado y cuyas muestras no tengan ya valoración por el analista
			//son las que están asignadas al analista para que las revise subiendo el excel

			/*
			// Buscamos las placas con estado 'Lista para análisis' (ya han salido de la maquina, tienen cargado un resultado pcr y estan listas para analizar)
			// del centro del usuario jefe logado
			BusquedaPlacaLaboratorioJefeBean criteriosBusquedaPlacaListaParaAnalisis = new BusquedaPlacaLaboratorioJefeBean();			
			criteriosBusquedaPlacaListaParaAnalisis.setIdEstadoPlaca(BeanEstado.Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum());
			criteriosBusquedaPlacaListaParaAnalisis.setIdLaboratorioCentro(user.getIdLaboratorioCentro());
			List<PlacaLaboratorioCentroBean> listaPlacasListasParaAnalisis = laboratorioCentroServicio.buscarPlacas(criteriosBusquedaPlacaListaParaAnalisis, pageable).getContent();
			System.out.println("listaPlacasListasParaAnalisis tiene: "+ listaPlacasListasParaAnalisis.size());
			*/
			
			// Buscamos las placas cuyas muestras esten asignadas al usuario logado con estado 'Asignada analissta'
			//y que no tengan aun valoracion por el analista			 
			
			BusquedaPlacaLaboratorioAnalistaBean criteriosBusquedaPlacaAsignadaParaRevision = new BusquedaPlacaLaboratorioAnalistaBean();			
			criteriosBusquedaPlacaAsignadaParaRevision.setIdEstadoPlaca(BeanEstado.Estado.PLACA_ASIGNADA_PARA_ANALISIS.getCodNum());
			criteriosBusquedaPlacaAsignadaParaRevision.setIdLaboratorioCentro(user.getIdLaboratorioCentro());
			criteriosBusquedaPlacaAsignadaParaRevision.setIdAnalistaMuestras(user.getId());
			criteriosBusquedaPlacaAsignadaParaRevision.setIdEstadoMuestras(BeanEstado.Estado.MUESTRA_ASIGNADA_ANALISTA.getCodNum());
			criteriosBusquedaPlacaAsignadaParaRevision.setValoracion(null);
			List<PlacaLaboratorioCentroBean> listaPlacasAsignadasParaRevision = laboratorioCentroServicio.buscarPlacas(criteriosBusquedaPlacaAsignadaParaRevision, pageable).getContent();
			System.out.println("listaPlacasAsignadasParaRevision tiene: "+ listaPlacasAsignadasParaRevision.size());			
			
			
			//vista.addObject("listaPlacasListasParaAnalisis", listaPlacasListasParaAnalisis);
			vista.addObject("listaPlacasAsignadasParaRevision", listaPlacasAsignadasParaRevision);
			
			return vista;

		}
		//FIN ANALISTAS-REVISAR PLACA
		
		//ANALISTAS- REVISAR MUESTRA		
		@RequestMapping(value = "/listarMuestrasAnalista", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','ANALISTALABORATORIO')")
		public ModelAndView listarMuestrasAsignadasAnalistaGET(HttpSession session, @PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

			ModelAndView vista = new ModelAndView("ListadoMuestrasARevisarAnalista");
			
			System.out.println("estoy en listarMuestrasAsignadasAnalistaGET");
			//recupero el usuario logado
			PcrUserDetails pcrUserDetails = (PcrUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Usuario user = usuarioServicio.findByEmail(pcrUserDetails.getUser().getUsuario().getEmail());
			System.out.println("usuario logado: " + user.getNombre() + " del idLaboratorioCentro: " + user.getIdLaboratorioCentro());

			/*
			// Buscamos las placas con estado 'Lista para análisis' (ya han salido de la maquina, tienen cargado un resultado pcr y estan listas para analizar)
			// del centro del usuario jefe logado
			BusquedaPlacaLaboratorioJefeBean criteriosBusquedaPlacaListaParaAnalisis = new BusquedaPlacaLaboratorioJefeBean();			
			criteriosBusquedaPlacaListaParaAnalisis.setIdEstadoPlaca(BeanEstado.Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum());
			criteriosBusquedaPlacaListaParaAnalisis.setIdLaboratorioCentro(user.getIdLaboratorioCentro());
			List<PlacaLaboratorioCentroBean> listaPlacasListasParaAnalisis = laboratorioCentroServicio.buscarPlacas(criteriosBusquedaPlacaListaParaAnalisis, pageable).getContent();
			System.out.println("listaPlacasListasParaAnalisis tiene: "+ listaPlacasListasParaAnalisis.size());
			
			// Buscamos las placas con estado 'Asignada para análisis' (un jefe ya las ha puesto bajo su responsabilidad)
			// del centro del usuario jefe logado 
			// y que esten asignadas al jefe logado
			BusquedaPlacaLaboratorioJefeBean criteriosBusquedaPlacaAsignadaParaAnalisis = new BusquedaPlacaLaboratorioJefeBean();			
			criteriosBusquedaPlacaAsignadaParaAnalisis.setIdEstadoPlaca(BeanEstado.Estado.PLACA_ASIGNADA_PARA_ANALISIS.getCodNum());
			criteriosBusquedaPlacaListaParaAnalisis.setIdLaboratorioCentro(user.getIdLaboratorioCentro());
			criteriosBusquedaPlacaAsignadaParaAnalisis.setIdJefe(user.getId());
			List<PlacaLaboratorioCentroBean> listaPlacasAsignadasParaAnalisis = laboratorioCentroServicio.buscarPlacas(criteriosBusquedaPlacaAsignadaParaAnalisis, pageable).getContent();
			System.out.println("listaPlacasAsignadasParaAnalisis tiene: "+ listaPlacasAsignadasParaAnalisis.size());			
			
			//this.agregarListasDesplegables(vista);
			//vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
			vista.addObject("listaPlacasListasParaAnalisis", listaPlacasListasParaAnalisis);
			vista.addObject("listaPlacasAsignadasParaAnalisis", listaPlacasAsignadasParaAnalisis);
			*/
			return vista;

		}
		
		
		
		
		//muestra pantalla al analista para que resuelva la muestra
		@RequestMapping(value="/revisarAnalista", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','ANALISTALABORATORIO')")
		public ModelAndView revisarMuestraAnalista(HttpSession session, HttpServletRequest request, @RequestParam("idMuestra") Integer idMuestra) throws Exception {
			ModelAndView vista = new ModelAndView("VistaRevisarMuestraAnalista");
						
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
		
		
		@RequestMapping(value = "/guardarRevisionAnalista", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','JEFESERVICIO')")
		public RedirectView guardarRevisionAnalista(@ModelAttribute("beanMuestra") MuestraBean beanMuestra,
				HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
			
			System.out.println("muestra id: " + beanMuestra.getId());
			System.out.println("resultado seleccionado por el analista: " + beanMuestra.getResultado());			
			
			//TODO llamar a metodo de servicio que a partir de beanMuestra recupere la muestra y le asigne el resultado escogido por el jefe
			
			//la muestra se cierra desde la pagina principal (creo) alli habrá que 
			//cambiar el estado de la muestra a resuelta (cerrada) con su fecha de resolucion
			//TODO actualizar estadisticas de aciertos a los asignados a la muestra
			
			//muestraSevicio.guardarResultado (beanMuestra);
						
			//vuelvo al formulario de asignacion de la muestra
			String idMuestra = String.valueOf(beanMuestra.getId());
			redirectAttributes.addFlashAttribute("mensaje", "Resultado de muestra guardado");
			return new RedirectView("/analisis/revisarAnalista?idMuestra="+idMuestra, true);
		}
		//ANALISTAS- REVISAR MUESTRA	
		
		//FIN ANALISTA O VOLUNTARIOS (SOLO ANALIZAN)
		
		
		
		
		
		
		/*
		@RequestMapping(value = "/gestionPlacas/resultados", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('RESPONSANBLEPCR','ADMIN')")
		public ModelAndView buscarPlacasEsperandoResultadosPOST(HttpSession session, 
				@ModelAttribute BusquedaPlacaLaboratorioBean criteriosBusqueda, 
				@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {

			ModelAndView vista = new ModelAndView("ListadoPlacasEsperandoResultadosPCR");
			List<PlacaLaboratorioCentroBean> listaPlacas = laboratorioCentroServicio.buscarPlacas(criteriosBusqueda, pageable).getContent();

			vista.addObject("busquedaPlacaLaboratorioBean", criteriosBusqueda);
			vista.addObject("listaPlacas", listaPlacas);
			return vista;
		}
		*/
		
		
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
			//bean.setCodNumLote("codLote-" + i);
			
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
				ana.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO));
				beanAsigAna.setBeanUsuario(ana);				
				beanAsigAna.setFechaAsignacion(date);
				beanAsigAna.setValoracion("P");
				beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);
				
				BeanAsignacion beanAsigVol = new BeanAsignacion();
				BeanUsuario vol = new BeanUsuario();
				vol.setId(j);
				vol.setNom("voluntario-" + j);
				vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO));
				beanAsigVol.setBeanUsuario(vol);				
				beanAsigVol.setFechaAsignacion(date);
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
				ana.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO));
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
				vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO));
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
				ana.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO));
				beanAsigAna.setBeanUsuario(ana);				
				beanAsigAna.setFechaAsignacion(date);
				beanAsigAna.setValoracion("P");
				beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);
				
				BeanAsignacion beanAsigVol = new BeanAsignacion();
				BeanUsuario vol = new BeanUsuario();
				vol.setId(j);
				vol.setNom("voluntario-" + j);
				vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO));
				beanAsigVol.setBeanUsuario(vol);				
				beanAsigVol.setFechaAsignacion(date);
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
