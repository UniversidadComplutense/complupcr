package es.ucm.pcr.controladores;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.LogMuestraListadoBean;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.ServicioLog;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.validadores.LogMuestraValidador;

@Controller
@RequestMapping(value = "/gestor")
public class LogMuestraControlador {
	
	// TODO - INCLUIR EL ROL DEL CENTRO
	// TODO - LOG, TRAZAR SERVICIOS
	// TODO - LOTE - LABORATORIO
	// TODO - LOTE - MUESTRAS
	// TODO - LOTE - ACCION, ORDENACION, PAGINACION
	
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "muestra.id", "fechaCambio");
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Autowired
	private ServicioLog servicioLog;
	
	@Autowired
	private LogMuestraValidador logValidadorMuestra;
	
	@InitBinder("beanBusqueda")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(logValidadorMuestra);
	}
	
	@ModelAttribute("listaLotes")
	public List<LoteListadoBean> lotes() {
		if (sesionServicio.getCentro()!=null)
		 return loteServicio.findLoteByParam(new LoteBusquedaBean(sesionServicio.getCentro().getId()));
		else 
			return loteServicio.findLoteByParam(new LoteBusquedaBean());
	}
	
	@RequestMapping(value="/log", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR', 'AUDITOR')")
	public ModelAndView buscador(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLogMuestraListado");
	
		LogMuestraBusquedaBean beanBusqueda = new LogMuestraBusquedaBean();	
		
		vista.addObject("beanBusqueda", beanBusqueda);
		return vista;
	}
	
	@RequestMapping(value = "/log/list", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR', 'AUDITOR')")
	public ModelAndView buscar(@Valid @ModelAttribute("beanBusqueda") LogMuestraBusquedaBean beanBusqueda, BindingResult result, HttpSession session) throws Exception {
		
		ModelAndView vista = new ModelAndView("VistaLogMuestraListado");
		if (result.hasErrors()) {
			vista.addObject("beanBusqueda", beanBusqueda);
			
		} else {		
			session.setAttribute("beanBusqueda", beanBusqueda);
			session.setAttribute("paginaActual", 1);
			buscarMuestras(beanBusqueda, vista, session);
		}
		return vista;
	}

	@RequestMapping(value = "/log/list", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR', 'AUDITOR')")
	public ModelAndView buscar(HttpSession session, @RequestParam("pagina") Optional<Integer> page) throws Exception {
		Integer currentPage = page.orElse(null);
		currentPage = currentPage == null ? (session.getAttribute("paginaActual") != null ? (Integer)session.getAttribute("paginaActual") : 1) : page.get();
		
		ModelAndView vista = new ModelAndView("VistaLogMuestraListado");

		LogMuestraBusquedaBean beanBusqueda = (LogMuestraBusquedaBean) session.getAttribute("beanBusqueda");
		beanBusqueda = beanBusqueda != null ? beanBusqueda : new LogMuestraBusquedaBean();
		session.setAttribute("paginaActual", currentPage);

		buscarMuestras(beanBusqueda, vista, session);
		return vista;
	}

	private void buscarMuestras(LogMuestraBusquedaBean beanBusqueda, ModelAndView vista, HttpSession session) {
		Integer currentPage = (Integer)session.getAttribute("paginaActual");
		
		Page<LogMuestraListadoBean> muestrasPage = servicioLog.findLogMuestraByParam(beanBusqueda,
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION));
		vista.addObject("beanBusqueda", beanBusqueda);
		vista.addObject("listadoLogMuestras", muestrasPage.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(muestrasPage.getTotalPages(), currentPage, muestrasPage.getTotalElements(), "/gestor/log/list");		
		vista.addObject("paginadorBean", paginadorBean);
	}
	
}
