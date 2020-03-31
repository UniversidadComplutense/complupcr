package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;

import es.ucm.pcr.beans.BusquedaLotesBean;

import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.BeanPlacaVisavetUCM;
import es.ucm.pcr.beans.LotePlacaVisavetBean;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCM;
import es.ucm.pcr.servicios.ServicioLotes;




@Controller
public class LaboratorioVisavetUCMController {
	@Autowired
	ServicioLaboratorioVisavetUCM servicioLaboratorioUni;
	@Autowired
	ServicioLotes servicioLotes;
	
	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(LaboratorioVisavetUCMController.class);
	private List<BeanElemento> listaEstados = null;
	private List<BeanElemento> rellenaListaEstados() throws Exception {
		if (listaEstados == null) {
			listaEstados = servicioLotes.buscarEstadosLotes();
			BeanElemento estado = new BeanElemento();

			/*estado.setCodigo(00);
			estado.setDescripcion("- Seleccione estado preinscripción - ");
			listaEstados.add(0, estado);*/
		}
		return listaEstados;
	}
	// para probar
	public LoteBeanPlacaVisavet getBean(int i) {
		LoteBeanPlacaVisavet bean = new LoteBeanPlacaVisavet();
		bean.setId(""+i);
		bean.setNumLote("33454d");
		BeanEstado estado= new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoLote);
		estado.setEstado(Estado.Enviado);
		
		Date date= new Date();
		bean.setCentroProcedencia("Centro de Salud de Getafe");
		bean.setFechaEntrada(Calendar.getInstance().getTime());
		bean.setEstado(estado);
		List<MuestraBeanLaboratorioVisavet>  listaMuestras = new ArrayList();
		for (int j=0;j<30;j++) {
			MuestraBeanLaboratorioVisavet e=new MuestraBeanLaboratorioVisavet();
			e.setId(j);
			e.setEtiqueta("498979");
			
			e.setTipoMuestra("nanofaringe");
		
			listaMuestras.add(e);
		}
		bean.setFuncionEjecutar("loadConfirmarEnvio("+bean.getId()+")");
		bean.setListaMuestras(listaMuestras);
		
		
		return bean;
	}
	
	public ModelAndView buscarLotes(BusquedaLotesBean busquedaLotes, HttpServletRequest request, HttpSession session, Pageable pageable) {
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
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
		return vista;
	}
	// presenta la pagina con unos criterios de busqueda iniciales
	@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.GET)
	public ModelAndView buscarLotesGet(Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {
         // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		System.out.println("numero get "+pageable.getPageNumber());
		BusquedaLotesBean busquedaLotes= new BusquedaLotesBean();
		// inicializamos a enviado para filtrar por estos
		busquedaLotes.setCodNumEstadoSeleccionado("2");
		busquedaLotes.setListaBeanEstado(this.rellenaListaEstados());
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		// invocar al servicio que dado id De laboratorio se obtiene la entidad laboratorioUni
		List<LoteBeanPlacaVisavet> list= new ArrayList();
		Page<LoteBeanPlacaVisavet> paginaLotes =new PageImpl<LoteBeanPlacaVisavet>(list, pageable,20);
		
		model.addAttribute("paginaLotes", paginaLotes);;
		vista.addObject("busquedaLotes", busquedaLotes);
		vista.addObject("paginaLotes", paginaLotes);
		return vista;
		// return this.buscarLotes(busquedaLotes, request, session, pageable);
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
				if (lista.get(i).getEstado().getEstado()== Estado.Recibido) {
			 	tabla+="<input type=\"checkbox\" id=\"seleccionado"+i+"\" value=\""+lista.get(i).getId()+"\"/></td>";
				}
				else {
					tabla+="<input type=\"checkbox\" disabled id=\"seleccionado"+i+"\" value=\""+lista.get(i).getId()+"\"/></td>";		
				}
				tabla+="<td width=\"1%\"><span>"+lista.get(i).getNumLote()+"</span></td>";
				tabla+="<td width=\"10%\"><span>"+lista.get(i).getCentroProcedencia()+"</span></td>";
				tabla+="<td width=\"1%\"><span>"+lista.get(i).getFechaEntrada()+"</span></td>";
			    tabla+="<td id=\"mensajes_log\" style=\"text-align: center;\" width=\"1%\"> <a  data-toggle=\"modal\" href=\"#modalConsultarMuestras\" onClick=\"consultarMuestras('"+lista.get(i).getNumLote()+"','"+lista.get(i).getCentroProcedencia()+"','"+lista.get(i).getId()+"')\" >"; 
			    tabla+="<i class=\"fa fa-table\" aria-hidden=\"true\" 	style=\"font-size: 1.2em;\"></i></a></td>";
			 	tabla+="<td id=\"mensajes_log\" width=\"1%\"><span>Covid19</span></td>";
			 	tabla+="<td width=\"16%\"><span>"+lista.get(i).getEstado().getEstado().getDescripcion()+"</span>&nbsp;";
			 	if (lista.get(i).getEstado().getEstado()== Estado.Enviado)
			 	tabla+="<a   href=\"#modalConfirmRecibido\" data-toggle=\"modal\" onclick=\"loadConfirmarEnvio('"+lista.get(i).getId()+"','"+lista.get(i).getNumLote()+"', '"+lista.get(i).getCentroProcedencia()+"')\">Confirmar recibido</a> ";
			 	tabla+="</td>";
				tabla+="</tr>";
	   }
			
	tabla +="</table>";
	return tabla;
	}
	
	// buscar lotes segun los criterios de busqueda 
	
		@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.POST)
		public ModelAndView buscarLotesPost(@ModelAttribute("busquedaLotes") BusquedaLotesBean busquedaLotes, Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) {
	        // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
			Page<LoteBeanPlacaVisavet> paginaLotes = null;
			//BeanBusquedaLotes busquedaLotes=(BeanBusquedaLotes) model.getAttribute("busquedaLotes");
			ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
			
			paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
			// para probar
			List<LoteBeanPlacaVisavet> list= new ArrayList();
			for (int i = 0; i<20; i++) {
				list.add(getBean(i));
			}		
			paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable,pageable.getPageSize());
			
			// fin para probar 
			model.addAttribute("paginaLotes", paginaLotes);
			vista.addObject("paginaLotes", paginaLotes);
			vista.addObject("busquedaLotes",busquedaLotes);
			// guardo los criterios de busqueda de lotes
			session.setAttribute("busquedaLotes", busquedaLotes);
		    return vista;
		
		}
	
	
	//@ResponseBody
	@RequestMapping(value = "/laboratorioUni/confirmarReciboLote", method = RequestMethod.GET)
	public ModelAndView confirmarReciboLote(@RequestParam("id") String id,Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) {
	// llamar al servicio lotes y cambiar el estado de id a Recibido
		// para probar
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
				List<LoteBeanPlacaVisavet> list=new ArrayList();
					for (int i = 0; i<20; i++) {
						list.add(getBean(i));
						if (list.get(i).getId().equals(id)) {
							// cambiamos estado
							BeanEstado estado= new BeanEstado();
							estado.setTipoEstado(TipoEstado.EstadoLote);
							estado.setEstado(Estado.Recibido);
							list.get(i).setEstado(estado);
						}
						//para que tenga mas de dos lotes 
						if (list.get(i).getId().equals("3")) {
							// cambiamos estado
							BeanEstado estado= new BeanEstado();
							estado.setTipoEstado(TipoEstado.EstadoLote);
							estado.setEstado(Estado.Recibido);
							list.get(i).setEstado(estado);
						}
					}	
					
					Page<LoteBeanPlacaVisavet> paginaLotes = null;
					paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable,pageable.getPageSize());
					// fin para probar 
					model.addAttribute("paginaLotes", paginaLotes);
					model.addAttribute("busquedaLotes", session.getAttribute("busquedaLotes"));
					vista.addObject("paginaLotes", paginaLotes);
					vista.addObject("busquedaLotes", session.getAttribute("busquedaLotes"));
				    return vista;	
	}
	
		public LoteBeanPlacaVisavet getBean2(int i) {
			LoteBeanPlacaVisavet bean = new LoteBeanPlacaVisavet();
			bean.setId(""+i);
			bean.setNumLote("44444");
			BeanEstado estado= new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoLote);
			estado.setEstado(Estado.Recibido);
			
			Date date= new Date();
			bean.setCentroProcedencia("Centro de Salud de Getafe");
			bean.setFechaEntrada(Calendar.getInstance().getTime());
			bean.setEstado(estado);
			List<MuestraBeanLaboratorioVisavet>  listaMuestras = new ArrayList();
			for (int j=0;j<10;j++) {
				MuestraBeanLaboratorioVisavet e=new MuestraBeanLaboratorioVisavet();
				e.setId(j);
				e.setEtiqueta("498979");
				listaMuestras.add(e);
			}
			bean.setListaMuestras(listaMuestras);
			
			
			return bean;
		}
		// refrescar  datos con ajax
		@RequestMapping(value = "/laboratorioUni/actualizarLote", method = RequestMethod.POST)
		public String buscarPlacasPost(@RequestParam("id") String id, Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20) Pageable pageable) {
			Page<LoteBeanPlacaVisavet> paginaLotes = null;
			BusquedaLotesBean busquedaLotes=(BusquedaLotesBean) model.getAttribute("busquedaLotes");
			paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
			List<LoteBeanPlacaVisavet> list= new ArrayList();
			for (int i = 0; i<10; i++) {
				list.add(getBean2(i));
			}		
			paginaLotes = new PageImpl<LoteBeanPlacaVisavet>(list, pageable,20);
			model.addAttribute("paginaLotes", paginaLotes);
			
			return "VistaListadoRecepcionLotes :: #trGroup";
		}
// obtiene las muestras de un lote
		
		
		@RequestMapping(value = "/laboratorioUni/mostrarMuestras", method = RequestMethod.GET)
		public String consultarMuestrasLote(@RequestParam("id") String id,Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20,sort = "lote", direction =Sort.Direction.DESC) Pageable pageable) {
		// ir al servicio lotes y llamar al metodo que me liste las muestra a partir del id de lote
		// para probar
			
			List<MuestraBeanLaboratorioVisavet> muestras=new ArrayList();
			List<LoteBeanPlacaVisavet> list= new ArrayList();
			for (int i = 0; i<10; i++) {
				list.add(getBean(i));
				if (list.get(i).getId().equals(id)) {
					muestras= list.get(i).getListaMuestras();
				}
			}	
	    // fin de prueba		
           model.addAttribute("muestras", muestras);
			
			return "VistaListadoRecepcionLotes :: #trMuestra";
		}
		
// request PLACAS
		//este metodo obtiene los lotes q estan listos para ser procesados en placas visavet y muestran en las placas
		@RequestMapping(value = "/laboratorioUni/procesarLotes", method = RequestMethod.GET)
		public ModelAndView buscarPlacasGet(@RequestParam("lotes") String lotes,Model model, HttpServletRequest request, HttpSession session) {
			ModelAndView vista = new ModelAndView("VistaLotesPlacasVisavet");
			LotePlacaVisavetBean lotePlacaVisavetBean= new LotePlacaVisavetBean();
			// obtenemos los lotes con sus muestras
			String[] idsLotes=lotes.split(":");
			List<LoteBeanPlacaVisavet> listaLotes=new ArrayList();
			System.out.println("size "+idsLotes.length);
			for (int i=0; i<idsLotes.length;i++) {
				// cuando ya este el servicio BeanLote lote=servicioLotes.obtenerLote(idsLotes[i]);
				// para probar
				System.out.println("size "+i);
				listaLotes.add(getBean(i));
				lotePlacaVisavetBean.setTotalMuestras(getBean(i).getListaMuestras().size()+lotePlacaVisavetBean.getTotalMuestras());
			} 
			
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
			//vista.addObject("listaLotes",listaLotes);
			// los mostramos en la vista
			return vista;
		}
		@ResponseBody
		@RequestMapping(value = "/laboratorioUni/altaPlacaVisavet", method = RequestMethod.GET)
		public String altaPlacasGet(Model model, HttpServletRequest request, HttpSession session) {
			// para obtener un numero de Placa voy a crear una placa vacia
			// la creo vacia pq quiero obtener solo el num de placa
			// cuando tenga servicio BeanPlacasVisavetUCM placaVisavet = servicioVisavet.crearPlaca();
			// para probar
			BeanPlacaVisavetUCM placaVisavet = new BeanPlacaVisavetUCM();
			placaVisavet.setId("1");
			/*List<Integer> tamanoLista= new ArrayList();
			tamanoLista.add(20);
			tamanoLista.add(96);
			placaVisavet.setListaTamanosDisponibles(tamanoLista);
			*/
			// los mostramos en la vista
			return placaVisavet.getId();
		}
		
		
		// buscar placas segun los criterios de busqueda 
		@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.GET)
		public ModelAndView buscarPlacasGet(Model model, HttpServletRequest request, HttpSession session) {
	     // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		ModelAndView vista = new ModelAndView("VistaBuscarPlacas");
		
		//
		
		List<LoteBeanPlacaVisavet> list=new ArrayList();
			for (int i = 0; i<20; i++) {
				list.add(getBean(i));
				if (list.get(i).getId().equals('1')) {
					// cambiamos estado
					BeanEstado estado= new BeanEstado();
					estado.setTipoEstado(TipoEstado.EstadoLote);
					estado.setEstado(Estado.Recibido);
					list.get(i).setEstado(estado);
				}
			}	
			
		/*	Page<BeanLote> paginaLotes = null;
			paginaLotes = new PageImpl<BeanLote>(list, pageable,pageable.getPageSize());
			// fin para probar 
			model.addAttribute("paginaLotes", paginaLotes);
			model.addAttribute("busquedaLotes", session.getAttribute("busquedaLotes"));
			vista.addObject("paginaLotes", paginaLotes);
			vista.addObject("busquedaLotes", session.getAttribute("busquedaLotes"));
		  */
		    return vista;	
		
		
		//			
		
		}
				// buscar lotes segun los criterios de busqueda 
				@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.POST)
				public ModelAndView buscarPlacasPost(@ModelAttribute("lotePlacaVisavetBean") LotePlacaVisavetBean lotePlacaVisavetBean, Model model, HttpServletRequest request, HttpSession session) {
				    // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
					ModelAndView vista = new ModelAndView("VistaBuscarPlacas");
				 //tengo que obtener de la variable de sesion el bean lotePlacaVisavetBean para actualizarlo con los datos que vengan del formulario
				  if (lotePlacaVisavetBean.getAccion().equals("grabaryFinalizar")) {
					  // tengo que transicionar de estado a FINALIZADA
				  }
					return vista;
				}
				@RequestMapping(value = "/laboratorioUni/asignarPlaca", method = RequestMethod.GET)
				public String asignarPlacasGet(@RequestParam("idPlaca") int idPlaca,  Model model, HttpServletRequest request, HttpSession session) {
				// grabar en el servicio la placa junto con el que tenga lote que venga del modelo
					LotePlacaVisavetBean lotePlacaVisavetBean = (LotePlacaVisavetBean )session.getAttribute("lotePlacaVisavetBean");
						// ModelAndView vista = new ModelAndView("VistaLotesPlacasVisavet");
					//añado a la placa 1 a todos los lotes que tenga en la placa
					// para jugar
					for (int i=0;i<lotePlacaVisavetBean.getPlaca().getListaLotes().size();i++) {
					 lotePlacaVisavetBean.getPlaca().getListaLotes().get(i).setIdPlacaVisavet(idPlaca);
					 lotePlacaVisavetBean.getPlaca().setListaLotes(lotePlacaVisavetBean.getPlaca().getListaLotes());
					 
					}
					//vista.addObject("lotePlacaVisavetBean",lotePlacaVisavetBean);
					model.addAttribute("lotePlacaVisavetBean",lotePlacaVisavetBean);
					return "VistaLotesPlacasVisavet :: #trGroup";
					
				}
				
				
}




