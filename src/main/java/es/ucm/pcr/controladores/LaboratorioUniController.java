package es.ucm.pcr.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
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

import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.LoteBean;
import es.ucm.pcr.servicios.ServicioLaboratorioUni;



@Controller
@RequestMapping(value="/laboratorioUni")
public class LaboratorioUniController {
	@Autowired
	ServicioLaboratorioUni servicioLaboratorioUni;
	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(LaboratorioUniController.class);
	
	
	
	// presenta la pagina con unos criterios de busqueda iniciales
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public ModelAndView buscarGet(Model model, HttpServletRequest request, HttpSession session,@PageableDefault(page = 0, value = 20) Pageable pageable) {
         // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		BusquedaLotesBean busquedaLotes= new BusquedaLotesBean();
		// inicializamos a enviado para filtrar por estos
		busquedaLotes.setCodNumEstadoSeleccionado("2");
		
		ModelAndView vista = new ModelAndView("VistaListadoRecepcionLotes");
		// invocar al servicio que dado id De laboratorio se obtiene la entidad laboratorioUni
		Page<LoteBean> paginaLotes = null;
		paginaLotes = servicioLaboratorioUni.buscarLotes(busquedaLotes, pageable);
				
		vista.addObject("busquedaLotes", busquedaLotes);
		vista.addObject("paginaLotes", paginaLotes);
		return vista;
	}
	// buscar lotes segun los criterios de busqueda 
	@RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public ModelAndView buscarPost(Model model, HttpServletRequest request, HttpSession session) {
        // tengo que mirar como a partir del usuario vemos de que laboratorioUni es y le muestro unicamente sus loooooootes
		ModelAndView vista = new ModelAndView("listadoLotes");
		return vista;
	}
	
	
}
