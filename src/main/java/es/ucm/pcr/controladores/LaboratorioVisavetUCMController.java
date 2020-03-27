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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanListadoMuestra;
import es.ucm.pcr.beans.BeanBusquedaLotes;
import es.ucm.pcr.beans.BeanBusquedaMuestra;
import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanLote;
import es.ucm.pcr.beans.BeanMuestra;
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
	public BeanLote getBean(int i) {
		BeanLote bean = new BeanLote();
		bean.setId(""+i);
		bean.setNumLote("33454d");
		BeanEstado estado= new BeanEstado();
		estado.setTipoEstado(TipoEstado.EstadoLote);
		estado.setEstado(Estado.Enviado);
		
		Date date= new Date();
		bean.setCentroProcedencia("Centro de Salud de Getafe");
		bean.setFechaEntrada(Calendar.getInstance().getTime());
		bean.setEstado(estado);
		List<BeanMuestra>  listaMuestras = new ArrayList();
		for (int j=0;j<10;j++) {
			BeanMuestra e=new BeanMuestra();
			e.setId(j);
			e.setEtiqueta("498979");
			listaMuestras.add(e);
		}
		bean.setListaMuestras(listaMuestras);
		
		
		return bean;
	}
	
	public ModelAndView buscarLotes(BeanBusquedaLotes busquedaLotes, HttpServletRequest request, HttpSession session, Pageable pageable) {
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		// invocar al servicio que dado id De laboratorio se obtiene la entidad laboratorioUni
		Page<BeanLote> paginaLotes = null;
		paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
		List<BeanLote> list= new ArrayList();
		for (int i = 0; i<10; i++) {
			list.add(getBean(i));
		}		
		paginaLotes = new PageImpl<BeanLote>(list, pageable,20);
		vista.addObject("busquedaLotes", busquedaLotes);
		vista.addObject("paginaLotes", paginaLotes);
		return vista;
	}
	// presenta la pagina con unos criterios de busqueda iniciales
	@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.GET)
	public ModelAndView buscarLotesGet(Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20) Pageable pageable) throws Exception {
         // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		BeanBusquedaLotes busquedaLotes= new BeanBusquedaLotes();
		// inicializamos a enviado para filtrar por estos
		busquedaLotes.setCodNumEstadoSeleccionado("2");
		busquedaLotes.setListaBeanEstado(this.rellenaListaEstados());
		return this.buscarLotes(busquedaLotes, request, session, pageable);
		// mas adelante necesito obtener Centros de un servicioCentros
		
		//busquedaLotes.setListaBeanCentro(servicioCentros.buscarCentros());
		
		
		
	}
	// buscar lotes segun los criterios de busqueda 
	@RequestMapping(value = "/laboratorioUni/buscarLotes", method = RequestMethod.POST)
	public ModelAndView buscarLotesPost(Model model, HttpServletRequest request, HttpSession session,@ModelAttribute BeanBusquedaLotes busquedaLotes,@PageableDefault(page = 0, value = 20) Pageable pageable) {
        // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		
		
	return this.buscarLotes(busquedaLotes, request, session, pageable);
	
	
	}
	// buscar placas segun los criterios de busqueda 
		@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.GET)
		public ModelAndView buscarPlacasGet(Model model, HttpServletRequest request, HttpSession session) {
	        // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
			ModelAndView vista = new ModelAndView("VistaBuscarPlacas");
			return vista;
		}
		// buscar lotes segun los criterios de busqueda 
		@RequestMapping(value = "/laboratorioUni/buscarPlacas", method = RequestMethod.POST)
		public ModelAndView buscarPlacasPost(Model model, HttpServletRequest request, HttpSession session) {
		    // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
			ModelAndView vista = new ModelAndView("VistaBuscarPlacas");
			return vista;
		}
		public BeanLote getBean2(int i) {
			BeanLote bean = new BeanLote();
			bean.setId(""+i);
			bean.setNumLote("44444");
			BeanEstado estado= new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoLote);
			estado.setEstado(Estado.Recibido);
			
			Date date= new Date();
			bean.setCentroProcedencia("Centro de Salud de Getafe");
			bean.setFechaEntrada(Calendar.getInstance().getTime());
			bean.setEstado(estado);
			List<BeanMuestra>  listaMuestras = new ArrayList();
			for (int j=0;j<10;j++) {
				BeanMuestra e=new BeanMuestra();
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
			Page<BeanLote> paginaLotes = null;
			BeanBusquedaLotes busquedaLotes=(BeanBusquedaLotes) model.getAttribute("busquedaLotes");
			paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
			List<BeanLote> list= new ArrayList();
			for (int i = 0; i<10; i++) {
				list.add(getBean2(i));
			}		
			paginaLotes = new PageImpl<BeanLote>(list, pageable,20);
			model.addAttribute("paginaLotes", paginaLotes);
			
			return "VistaListadoRecepcionLotes :: #trGroup";
		}
		
}
