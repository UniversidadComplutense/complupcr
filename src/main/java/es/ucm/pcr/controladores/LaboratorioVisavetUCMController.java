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

private  BusquedaLotesBean rellenarBusquedaLotes(BusquedaLotesBean busquedaLotes) throws Exception {

	busquedaLotes.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
	busquedaLotes.setListaCentros(centroServicio.listaCentrosOrdenada());

	return busquedaLotes;
}
	public ModelAndView buscarLotes(BusquedaLotesBean busquedaLotes, String rolURL, HttpServletRequest request, HttpSession session, Pageable pageable) throws Exception {
		
		Page<LoteBeanPlacaVisavet> paginaLotes = null;
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		 busquedaLotes.setMostrarProcesar(false);
		
		for (String rol:sesionServicio.getRoles()){
			if ((rol.equals("ROLE_TECNICOLABORATORIO")|| rol.equals("ROLE_ADMIN")) && rolURL.equals("T")) {
				 busquedaLotes.setMostrarProcesar(true);
				 break;
			}
		}
		busquedaLotes.setListaBeanEstado(BeanEstado.estadosLoteLaboratorioVisavet());
		busquedaLotes.setListaCentros(centroServicio.listaCentrosOrdenada());

		busquedaLotes.setRolURL(rolURL);
		paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), ORDENACION));
		//model.addAttribute("paginaLotes", paginaLotes);
		vista.addObject("paginaLotes", paginaLotes);
		vista.addObject("busquedaLotes",busquedaLotes);
		
		// guardo los criterios de busqueda de lotes
		session.setAttribute("busquedaLotes", busquedaLotes);
	    return vista;
	}
	// presenta la pagina con unos criterios de busqueda iniciales
	@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public ModelAndView buscarLotesGet(@RequestParam("estado") Integer estado,@RequestParam(required = false) String  rol,  Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 500000000) Pageable pageable) throws Exception {
         // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		BusquedaLotesBean  busquedaLotes= new BusquedaLotesBean();
		
		/*if (estado!= null) busquedaLotes.setCodNumEstadoSeleccionado(estado);
		else
		busquedaLotes.setCodNumEstadoSeleccionado(3);
		*/
		if (session.getAttribute("busquedaLotes") != null) {
			busquedaLotes=(BusquedaLotesBean) session.getAttribute("busquedaLotes");
		// inicializamos a enviado para filtrar por estos
		// tengo que obtener los centros de los que puedo recibir muestras
		//busquedaLotes.setIdCentro(0);	
	}
		if (estado!= null) { 
			if (estado==0)busquedaLotes.setCodNumEstadoSeleccionado(null);
			else
			busquedaLotes.setCodNumEstadoSeleccionado(estado);
		}
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		// invocar al servicio que dado id De laboratorio se obtiene la entidad laboratorioUni
		List<LoteBeanPlacaVisavet> list= new ArrayList();
		Page<LoteBeanPlacaVisavet> paginaLotes =new PageImpl<LoteBeanPlacaVisavet>(list, pageable,50000000);
		
		model.addAttribute("paginaLotes", paginaLotes);;
		vista.addObject("busquedaLotes", busquedaLotes);
		vista.addObject("paginaLotes", paginaLotes);
		//return vista;
		 return this.buscarLotes(busquedaLotes, rol, request, session, pageable);
		// mas adelante necesito obtener Centros de un servicioCentros
		
		//busquedaLotes.setListaBeanCentro(servicioCentros.buscarCentros());
		
		
		
	}
	// buscar lotes segun los criterios de busqueda 
	/*@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.POST)
	public String buscarLotesPost(Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) {
        // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		Page<BeanLote> paginaLotes = null;
		BeanBusquedaLotes busquedaLotes=(BeanBusquedaLotes) model.getAttribute("busquedaLotes");
	
		paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
		// para probar
		List<BeanLote> list= new ArrayList();
		for (int i = 0; i<20; i++) {
			list.add(getBean(i));
		}		
		paginaLotes = new PageImpl<BeanLote>(list, pageable,pageable.getPageSize());
		// fin para probar 
		model.addAttribute("paginaLotes", paginaLotes);
		
		return "VistaListadoRecepcionLotes :: #trGroup";
	//return "VistaListadoRecepcionLotes :: #trGroup";
	
	}
	*/
	private String mostrarResultadosLotes(List<LoteBeanPlacaVisavet> lista) {
		String tabla="<table class=\"col-xs-12 table\" id=\"tablaResultadosCabecera\">";
		 tabla+="<thead class=\"thead-light\"><tr><th  style=\"text-align: left;\"></th><th  style=\"text-align: left;\">#Lotes</th><th style=\"text-align: left;\">Centro</th><th  style=\"text-align: left;\">F.Entrada</th>";
				tabla+="<th  style=\"text-align: left;\">#Muestras</th><th  style=\"text-align: left;\">Test</th><th  style=\"text-align: left;\">Estado</th>	</tr></thead></table>";
				
				tabla+="<table class=\"col-xs-12 table\" id=\"tablaResultadosLotes\">";
				for (int i=0; i<lista.size();i++) {
			 	tabla+="<tr id=\"trGroup"+i+"\" >";
			 	tabla+="<td width=\"1%\">";
				/*if (lista.get(i)).getEstado().getEstado()== Estado.LOTE_RECIBIDO_CENTRO_ANALISIS) {
			 	tabla+="<input type=\"checkbox\" id=\"seleccionado"+i+"\" value=\""+lista.get(i).getId()+"\"/></td>";
				}
				else {
					tabla+="<input type=\"checkbox\" disabled id=\"seleccionado"+i+"\" value=\""+lista.get(i).getId()+"\"/></td>";		
				}*/
				tabla+="<td width=\"1%\"><span>"+lista.get(i).getNumLote()+"</span></td>";
				tabla+="<td width=\"10%\"><span>"+lista.get(i).getCentroProcedencia()+"</span></td>";
				tabla+="<td width=\"1%\"><span>"+lista.get(i).getFechaEntrada()+"</span></td>";
			    tabla+="<td id=\"mensajes_log\" style=\"text-align: center;\" width=\"1%\"> <a  data-toggle=\"modal\" href=\"#modalConsultarMuestras\" onClick=\"consultarMuestras('"+lista.get(i).getNumLote()+"','"+lista.get(i).getCentroProcedencia()+"','"+lista.get(i).getId()+"')\" >"; 
			    tabla+="<i class=\"fa fa-table\" aria-hidden=\"true\" 	style=\"font-size: 1.2em;\"></i></a></td>";
			 	tabla+="<td id=\"mensajes_log\" width=\"1%\"><span>Covid19</span></td>";
			 	tabla+="<td width=\"16%\"><span>"+lista.get(i).getEstado().getEstado().getDescripcion()+"</span>&nbsp;";
			 	if (lista.get(i).getEstado().getEstado()== Estado.LOTE_ENVIADO_CENTRO_ANALISIS)
			 	tabla+="<a   href=\"#modalConfirmRecibido\" data-toggle=\"modal\" onclick=\"loadConfirmarEnvio('"+lista.get(i).getId()+"','"+lista.get(i).getNumLote()+"', '"+lista.get(i).getCentroProcedencia()+"')\">Confirmar recibido</a> ";
			 	tabla+="</td>";
				tabla+="</tr>";
	   }
			
	tabla +="</table>";
	return tabla;
	}
	
	// buscar lotes segun los criterios de busqueda 
	
		@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
		public String buscarLotesPost(@ModelAttribute("busquedaLotes") BusquedaLotesBean busquedaLotes, Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 50000000,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) throws Exception {
	        // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		// funciona return  this.buscarLotes(busquedaLotes, request, session, pageable);
			session.setAttribute("busquedaLotes", busquedaLotes);
			if (busquedaLotes.getCodNumEstadoSeleccionado() ==null) busquedaLotes.setCodNumEstadoSeleccionado(0);
			if (busquedaLotes.getRolURL() == null) busquedaLotes.setRolURL("R");
			return "redirect:/laboratorioUni/buscarLotes?estado="+busquedaLotes.getCodNumEstadoSeleccionado()+"&rol="+busquedaLotes.getRolURL();
		
		}
	
	
	//@ResponseBody
	@RequestMapping(value = "/laboratorioUni/confirmarReciboLote", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
	public ModelAndView confirmarReciboLote(@RequestParam("id") Integer id,Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 50000000,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) throws Exception {
	// llamar al servicio lotes y cambiar el estado de id a Recibido
		// para probar
		
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		LoteCentroBean beanLote = loteServicio.findById(id);
		beanLote.setFechaRecibido(new Date());
		BeanEstado estado= new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoLote);
		estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);
		/*		beanLote.setEstado(estado);
		System.out.println(beanLote.getIdCentro());
		
			List<LoteBeanPlacaVisavet> list=new ArrayList();
					for (int i = 0; i<20; i++) {
						list.add(getBean(i));
						if (list.get(i).getId().equals(0)) {
							// cambiamos estado
							
							list.get(i).setEstado(estado);
						}
						//para que tenga mas de dos lotes 
						if (list.get(i).getId().equals(3)) {
							// cambiamos estado
							BeanEstado estado= new BeanEstado();
							estado.setTipoEstado(TipoEstado.EstadoLote);
							estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);
							list.get(i).setEstado(estado);
						}
					}	
				*/	
		loteServicio.actualizarEstadoLote(beanLote, estado);
		//LoteCentroBean lote = loteServicio.guardarLotePlavaVisavet(beanLote,estado);
		BusquedaLotesBean busqueda=(BusquedaLotesBean) session.getAttribute("busquedaLotes");
		if (busqueda ==null) busqueda= new BusquedaLotesBean();
		if (busqueda.getRolURL()==null) busqueda.setRolURL("R");
		return  this.buscarLotes(busqueda,busqueda.getRolURL(), request, session, pageable);
				/*	Page<LoteBeanPlacaVisavet> paginaLotes = null;
					paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable,pageable.getPageSize());
					// fin para probar 
					model.addAttribute("paginaLotes", paginaLotes);
					model.addAttribute("busquedaLotes", session.getAttribute("busquedaLotes"));
					vista.addObject("paginaLotes", paginaLotes);
					vista.addObject("busquedaLotes", session.getAttribute("busquedaLotes"));
				
				    return vista;	*/
	}
	@RequestMapping(value = "/laboratorioUni/confirmarEnviadaPlaca", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	public String confirmarEnviadaPlaca(@RequestParam("id") Integer id,Model model, HttpServletRequest request, HttpSession session) throws Exception {
		BeanPlacaVisavetUCM placa= servicioLaboratorioUni.buscarPlacaById(id);
		//BeanLaboratorioCentro laboratorioBean =laboratorioCentroServicio.buscarLaboratorioById(laboratorio);
		BeanEstado estado= new BeanEstado();
		estado.setEstado(Estado.PLACAVISAVET_ENVIADA);
		estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
		placa.setEstado(estado);
		placa.setFechaEnviadaLaboratorio(new Date());
	   BeanPlacaVisavetUCM placab= servicioLaboratorioUni.guardar(placa); 
	   /*BusquedaPlacasVisavetBean busqueda=(BusquedaPlacasVisavetBean) session.getAttribute("busqueda");
	  if (busqueda ==null) busqueda= new BusquedaPlacasVisavetBean();
		
	    return this.buscarPlacas(busqueda, request, session, pageable);*/
	   return "redirect:/laboratorioUni/buscarPlacas";
	}
	
		// refrescar  datos con ajax
		@RequestMapping(value = "/laboratorioUni/actualizarLote", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
		public String buscarPlacasPost(@RequestParam("id") String id, Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 50000000) Pageable pageable) {
			Page<LoteBeanPlacaVisavet> paginaLotes = null;
			BusquedaLotesBean busquedaLotes=(BusquedaLotesBean) model.getAttribute("busquedaLotes");
			paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
			List<LoteBeanPlacaVisavet> list= new ArrayList();
			for (int i = 0; i<10; i++) {
				//list.add(getBean2(i));
			}		
			paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable,50000000);
			model.addAttribute("paginaLotes", paginaLotes);
			
			return "VistaListadoRecepcionLotes :: #trGroup";
		}
// obtiene las muestras de un lote
		
		
		@RequestMapping(value = "/laboratorioUni/mostrarMuestras", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONLABORATORIO','TECNICOLABORATORIO')")
		public String consultarMuestrasLote(@RequestParam("id") Integer id,Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 50000000,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) {
		// ir al servicio lotes y llamar al metodo que me liste las muestra a partir del id de lote
		// para probar
			LoteBeanPlacaVisavet loteBeanPlacaVisavet = servicioLaboratorioUni.buscarLote(id);
			List<MuestraBeanLaboratorioVisavet> muestras=loteBeanPlacaVisavet.getListaMuestras();
		/*	List<MuestraBeanLaboratorioVisavet> muestras=new ArrayList();
			List<LoteBeanPlacaVisavet> list= new ArrayList();
			for (int i = 0; i<10; i++) {
				//list.add(getBean(i));
				if (list.get(i).getId().equals(id)) {
					muestras= list.get(i).getListaMuestras();
				}
			}	
	    // fin de prueba		
          */
           model.addAttribute("muestras", muestras);
			
			return "VistaListadoRecepcionLotes :: #trMuestra";
		}
		
// request PLACAS
		private Boolean isLotesConPlacas(List<LoteBeanPlacaVisavet> lotes) {
		 Boolean mostrar=true;
			for (LoteBeanPlacaVisavet lote: lotes) {
			  if (lote.getIdPlacaVisavet()== null) { mostrar =false;
			 break;
			  }
		  }
			return mostrar;
		}
		
		
		//este metodo obtiene los lotes q estan listos para ser procesados en placas visavet y muestran en las placas
		@RequestMapping(value = "/laboratorioUni/procesarLotes", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
		public ModelAndView buscarPlacasGet(@RequestParam("lotes") String lotes,Model model, HttpServletRequest request, HttpSession session) {
			ModelAndView vista = new ModelAndView("VistaLotesPlacasVisavet");
			LotePlacaVisavetBean lotePlacaVisavetBean= new LotePlacaVisavetBean();
			// obtenemos los lotes con sus muestras
			String[] idsLotes=lotes.split(":");
			List<LoteBeanPlacaVisavet> listaLotes=new ArrayList();
			Integer numeroMuestras=0;
			//LoteBeanPlacaVisavet lotePlaca;
			for (int i=0; i<idsLotes.length;i++) {
				// cuando ya este el servicio BeanLote lote=servicioLotes.obtenerLote(idsLotes[i]);
				// para probar
				LoteBeanPlacaVisavet lotePlaca = loteServicio.findByIdByPlacas(Integer.parseInt(idsLotes[i]));
				numeroMuestras+=lotePlaca.getListaMuestras().size();
				listaLotes.add(lotePlaca);
		        //lotePlacaVisavetBean.setTotalMuestras(getBean(i).getListaMuestras().size()+lotePlacaVisavetBean.getTotalMuestras());
			} 
			lotePlacaVisavetBean.setTotalMuestras(numeroMuestras);
			// para probar
			List<Integer> tamanoLista= new ArrayList();
			tamanoLista.add(20);
			tamanoLista.add(96);
			
			lotePlacaVisavetBean.setListaTamanosDisponibles(tamanoLista);
			
			BeanPlacaVisavetUCM placaVisavet= new BeanPlacaVisavetUCM();
			// para modificar tengo que devolver la lista de lotes pero desde el servicio
			
		
			placaVisavet.setListaLotes(listaLotes);
			lotePlacaVisavetBean.setPlaca(placaVisavet);
			lotePlacaVisavetBean.setListaLotesDisponibles(listaLotes);
			session.setAttribute("lotePlacaVisavetBean", lotePlacaVisavetBean);
			//fin probar
			
			vista.addObject("lotePlacaVisavetBean",lotePlacaVisavetBean);
			vista.addObject("botonAltaNoMostrar", this.isLotesConPlacas(listaLotes));
			//vista.addObject("listaLotes",listaLotes);
			// los mostramos en la vista
			return vista;
		}
		@ResponseBody
		@RequestMapping(value = "/laboratorioUni/altaPlacaVisavet", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
		public Integer altaPlacasGet(Model model, HttpServletRequest request, HttpSession session) {
			// para obtener un numero de Placa voy a crear una placa vacia
			// la creo vacia pq quiero obtener solo el num de placa
			// cuando tenga servicio BeanPlacasVisavetUCM placaVisavet = servicioVisavet.crearPlaca();
			// para probar
			BeanPlacaVisavetUCM placaVisavet =new BeanPlacaVisavetUCM();
			BeanEstado estado=new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estado.setEstado(Estado.PLACA_INICIADA);
			
			placaVisavet.setEstado(estado);
			placaVisavet = servicioLaboratorioUni.guardar(placaVisavet);
			// placaVisavet.setId(1);
			/*List<Integer> tamanoLista= new ArrayList();
			tamanoLista.add(20);
			tamanoLista.add(96);
			placaVisavet.setListaTamanosDisponibles(tamanoLista);
			*/
			// los mostramos en la vista
			
			return placaVisavet.getId();
		}
		
		private  BusquedaPlacasVisavetBean rellenarBusquedaPlacas(BusquedaPlacasVisavetBean busquedaPlacasVisavetBean) throws Exception {
			

			busquedaPlacasVisavetBean.setListaBeanEstado(BeanEstado.estadosPlacaVisavet());
			busquedaPlacasVisavetBean.setListaLaboratorioCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
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
		public ModelAndView buscarPlacasGet(Model model, HttpServletRequest request, HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {
			
			Integer currentPage = page.orElse(null);
			currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
			session.setAttribute("paginaActual", currentPage);
			
			// tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
	    BusquedaPlacasVisavetBean busquedaPlacasVisavetBean;
	    ModelAndView vista = new ModelAndView("VistaListadoPlacasVisavet");
		
	    if (session.getAttribute("busquedaPlacasVisavetBean") == null) { 
	    	busquedaPlacasVisavetBean = new BusquedaPlacasVisavetBean();
	    	busquedaPlacasVisavetBean.setIdLaboratorioCentro(0);
			busquedaPlacasVisavetBean=this.rellenarBusquedaPlacas(busquedaPlacasVisavetBean);
			
	    }
	    else busquedaPlacasVisavetBean = (BusquedaPlacasVisavetBean)session.getAttribute("busquedaPlacasVisavetBean");
		//
		// fin para probar 
		vista.addObject("pagina", null);
		vista.addObject("busqueda", busquedaPlacasVisavetBean);
	  
		/*List<LoteBeanPlacaVisavet> list=new ArrayList();
			for (int i = 0; i<20; i++) {
				list.add(getBean(i));
				if (list.get(i).getId().equals('1')) {
					// cambiamos estado
					BeanEstado estado= new BeanEstado();
					estado.setTipoEstado(TipoEstado.EstadoLote);
					estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);
					list.get(i).setEstado(estado);
				}
			}	
			
			Page<BeanLote> paginaLotes = null;
			paginaLotes = new PageImpl<BeanLote>(list, pageable,pageable.getPageSize());
			// fin para probar 
			model.addAttribute("paginaLotes", paginaLotes);
			model.addAttribute("busquedaLotes", session.getAttribute("busquedaLotes"));
			vista.addObject("paginaLotes", paginaLotes);
			vista.addObject("busquedaLotes", session.getAttribute("busquedaLotes"));
		  */
		 return  this.buscarPlacas(busquedaPlacasVisavetBean, request, session, PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION_PLACAS));
			
		   // return vista;	
		
		
		//			
		
		}
		
		public ModelAndView buscarPlacas(BusquedaPlacasVisavetBean busqueda, HttpServletRequest request, HttpSession session, Pageable pageable) throws Exception {
			/* ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
			// invocar al servicio que dado id De laboratorio se obtiene la entidad laboratorioUni
			Page<LoteBeanPlacaVisavet> paginaLotes = null;
			paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
			List<LoteBeanPlacaVisavet> list= new ArrayList();
			for (int i = 0; i<10; i++) {
				list.add(getBean(i));
			}		
			paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable,20);
			vista.addObject("busquedaLotes", busquedaLotes);
			vista.addObject("paginaLotes", paginaLotes);
			return vista; */
			Page<BeanPlacaVisavetUCM> pagina= null;
			ModelAndView vista = new ModelAndView("VistaListadoPlacasVisavet");
		if (busqueda == null) new BusquedaPlacasVisavetBean();
			busqueda= this.rellenarBusquedaPlacas(busqueda);
			pagina = servicioLaboratorioUni.buscarPlacas(busqueda, pageable);
			//model.addAttribute("paginaLotes", paginaLotes);
			vista.addObject("pagina", pagina);
			vista.addObject("busqueda",busqueda);
			// guardo los criterios de busqueda de lotes
			session.setAttribute("busqueda", busqueda);
			
			PaginadorBean paginadorBean = new PaginadorBean(pagina.getTotalPages(), pageable.getPageNumber()+1, pagina.getTotalElements(), "/laboratorioUni/buscarPlacas");		
			vista.addObject("paginadorBean", paginadorBean);
			
		    return vista;
		}
		@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
		public ModelAndView buscarPlacasPost(@ModelAttribute("busquedaPlacasVisavetBean") BusquedaPlacasVisavetBean busquedaPlacasVisavetBean, Model model, HttpServletRequest request, HttpSession session) throws Exception {
			session.setAttribute("busquedaPlacasVisavetBean", busquedaPlacasVisavetBean);
			session.setAttribute("paginaActual", 1);
			//return "redirect:/laboratorioUni/buscarPlacas";
			 return  this.buscarPlacas(busquedaPlacasVisavetBean, request, session, PageRequest.of(0, Utilidades.NUMERO_PAGINACION, ORDENACION_PLACAS));
			
		}
				// buscar placas segun los criterios de busqueda 
				@RequestMapping(value = "/laboratorioUni/buscarPlacasProcesar", method = RequestMethod.POST)
				public String buscarPlacasPost(@ModelAttribute("lotePlacaVisavetBean") LotePlacaVisavetBean lotePlacaVisavetBean, Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 50000000) Pageable pageable) throws Exception {
				    // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
					ModelAndView vista = new ModelAndView("VistaBuscarPlacas");
				 //tengo que obtener de la variable de sesion el bean lotePlacaVisavetBean para actualizarlo con los datos que vengan del formulario
					 BeanEstado estado=new BeanEstado();
					 estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
					if (lotePlacaVisavetBean.getAccion().equals("grabaryFinalizar")) {
					  // tengo que transicionar de estado a FINALIZADA
						
							estado.setEstado(Estado.PLACAVISAVET_FINALIZADA);
						
				  }
				  else {
					  // preparada
					  
						estado.setEstado(Estado.PLACAVISAVET_PREPARADA);
					
					  
				  }
				  
				BeanPlacaVisavetUCM placa= lotePlacaVisavetBean.getPlaca();
				
			
				for (LoteBeanPlacaVisavet lote: lotePlacaVisavetBean.getPlaca().getListaLotes()) {
			    placa.setId(lote.getIdPlacaVisavet());
			    placa.setEstado(estado);
				placa = servicioLaboratorioUni.guardar(placa);
				
				//obtenemos las muestras y guardamos la referencia
				for (MuestraBeanLaboratorioVisavet m: lote.getListaMuestras()) {
					m= muestraServicio.guardarReferencia(m);
				}
				
				
				}
		//creo q no hace falta		lotePlacaVisavetBean.setPlaca(placa);
				BusquedaPlacasVisavetBean busquedaPlacasVisavetBean = new BusquedaPlacasVisavetBean();
				busquedaPlacasVisavetBean.setIdLaboratorioCentro(0);
				busquedaPlacasVisavetBean=this.rellenarBusquedaPlacas(busquedaPlacasVisavetBean);
				
				//return  this.buscarPlacas(busquedaPlacasVisavetBean, request, session, pageable);
				return "redirect:/laboratorioUni/buscarPlacas";
					
				}
				
				@RequestMapping(value = "/laboratorioUni/asignarPlaca", method = RequestMethod.GET)
				
				public String asignarPlacasGet(@RequestParam("idPlaca") int idPlaca,  Model model, HttpServletRequest request, HttpSession session) {
				// grabar en el servicio la placa junto con el que tenga lote que venga del modelo
					LotePlacaVisavetBean lotePlacaVisavetBean = (LotePlacaVisavetBean )session.getAttribute("lotePlacaVisavetBean");
					List<LoteBeanPlacaVisavet> listaLotesDisponibles= new ArrayList();
					if (lotePlacaVisavetBean == null)lotePlacaVisavetBean= new LotePlacaVisavetBean();
						for (LoteBeanPlacaVisavet lote:lotePlacaVisavetBean.getListaLotesDisponibles()) {
				      	
					      lote.setIdPlacaVisavet(idPlaca);
					      BeanEstado estado=new BeanEstado();
						  estado.setTipoEstado(TipoEstado.EstadoLote);
						  estado.setEstado(Estado.LOTE_PROCESADO_CENTRO_ANALISIS);
						   lote.setEstado(estado);
	                      listaLotesDisponibles.add(lote);
					
					} 
					lotePlacaVisavetBean.setListaLotesDisponibles(listaLotesDisponibles);
					BeanPlacaVisavetUCM placaVisavet =new BeanPlacaVisavetUCM();
					placaVisavet.setId(idPlaca);
					placaVisavet.setListaLotes(lotePlacaVisavetBean.getListaLotesDisponibles());
					BeanEstado estado=new BeanEstado();
					estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
					estado.setEstado(Estado.PLACAVISAVET_INICIADA);
					placaVisavet.setEstado(estado);
					placaVisavet.setFechaCreacion(new Date());
					// al guardar no estoy metiendo lotes
					placaVisavet = servicioLaboratorioUni.guardarConLote(placaVisavet);
					
					lotePlacaVisavetBean.setPlaca(placaVisavet);
					//vista.addObject("lotePlacaVisavetBean",lotePlacaVisavetBean);
					model.addAttribute("lotePlacaVisavetBean",lotePlacaVisavetBean);
					return "VistaLotesPlacasVisavet :: trGroup";
				
					
				}
		private Integer calcularPlacasVisavetEspera(BeanLaboratorioCentro laboratorio,@PageableDefault(page = 0, value = 50000000) Pageable pageable)	{
			Integer suma=0;
			BusquedaPlacasVisavetBean busqueda= new BusquedaPlacasVisavetBean();
			busqueda.setIdLaboratorioCentro(laboratorio.getId());
			Page<BeanPlacaVisavetUCM> pagina = servicioLaboratorioUni.buscarPlacas(busqueda, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), ORDENACION_PLACAS));
			List<BeanPlacaVisavetUCM> placas= pagina.getContent();
			//Set<PlacaLaboratorio> placasLaboratorios= laboratorio.getPlacaLaboratorios();
			for (BeanPlacaVisavetUCM placaLaboratorio:placas) {
					if (placaLaboratorio.getEstado().getEstado().getCodNum()== 4 || placaLaboratorio.getEstado().getEstado().getCodNum()==5) 
				     suma++;
				
			}
			
			return suma;
		}
			
		@RequestMapping(value = "/laboratorioUni/consultarOcupacionLaboratorios", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
	   public String asignarPlacasGet(@RequestParam("idPlaca") Integer idPlaca, Model model, HttpServletRequest request, HttpSession session, @PageableDefault(page = 0, value = 50000000) Pageable pageable) throws Exception {
		//	
			List<BeanLaboratorioCentro> laboratorios=laboratorioCentroServicio.listaLaboratoriosCentroOrdenada();
		    
		    for (BeanLaboratorioCentro laboratorio: laboratorios) {
		    	laboratorio.setPlacasVisavetaLaEspera(this.calcularPlacasVisavetEspera(laboratorio,pageable));
		    }
		    model.addAttribute("laboratorios", laboratorios);
			return "VistaListadoPlacasVisavet :: #trLaboratorio";
		}		
		
		
		@RequestMapping(value="/laboratorioUni/asignarLaboratorio", method=RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','TECNICOLABORATORIO')")
		public String asignarPlacasPost(@RequestParam("idPlaca") Integer idPlaca, @RequestParam("laboratorio") Integer laboratorio,Model model,HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 50000000) Pageable pageable) throws Exception {
			//PlacaLaboratorioVisavetBean  placa=laboratorioVisavetServicio.buscarPlaca(idPlaca);
			BeanPlacaVisavetUCM placa= servicioLaboratorioUni.buscarPlacaById(idPlaca);
			//BeanLaboratorioCentro laboratorioBean =laboratorioCentroServicio.buscarLaboratorioById(laboratorio);
			BeanEstado estado= new BeanEstado();
			estado.setEstado(Estado.PLACAVISAVET_ASIGNADA);
			estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			placa.setEstado(estado);
			placa.setFechaAsignadaLaboratorio(new Date());
			
		BeanPlacaVisavetUCM placab= servicioLaboratorioUni.guardarPlacaConLaboratorio(placa, laboratorio);
		/*BusquedaPlacasVisavetBean busqueda= (BusquedaPlacasVisavetBean) session.getAttribute("busqueda");
		if (busqueda ==null) busqueda=new BusquedaPlacasVisavetBean();
		    return this.buscarPlacas(busqueda, request, session, pageable);
		*/
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
			ElementoDocumentacionBean elementoDoc = documentoServicio.obtenerDocumentosPlacaVisavetConTipo(id,"REF");

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
				// guardamos el documento excel asociandolo a la placa
				documentoServicio.guardar(bean);
				// guardamos las valoraciones de las muestras que ha puesto el analista en el
				// excel y si es el ultimo analista de los numAnalistas globales de la
				// aplicacion
				// entonces guardamos el resultado definitivo
				servicioLaboratorioUni.guardarReferenciasMuestraPlaca(bean);
			}

			redirectAttributes.addFlashAttribute("mensaje", "Resultado guardado correctamente");
			return new ModelAndView(new RedirectView(
					"/laboratorioUni/cargaReferencias?id=" + bean.getId() + "&url=" + bean.getCodiUrl(), true));
		}
			

}




