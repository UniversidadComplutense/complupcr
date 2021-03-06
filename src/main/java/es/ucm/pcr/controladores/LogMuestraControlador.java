/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

package es.ucm.pcr.controladores;

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
import es.ucm.pcr.beans.PaginadorBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.MuestraServicio;
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
	
	//public static final Sort ORDENACION = Sort.by(Direction.ASC, "muestra.id", "fechaCambio");
	public static final Sort ORDENACION = Sort.by(Direction.ASC, "etiqueta");
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Autowired
	private ServicioLog servicioLog;
	
	@Autowired
	private MuestraServicio muestraServicio;
	
	@Autowired
	private LogMuestraValidador logValidadorMuestra;
	
	@InitBinder("beanBusqueda")
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder, HttpSession session)
			throws Exception {
		binder.setValidator(logValidadorMuestra);
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
		
		Page<LogMuestraListadoBean> muestrasPage = muestraServicio.findMuestraByParam(beanBusqueda,
				PageRequest.of(currentPage-1, Utilidades.NUMERO_PAGINACION, ORDENACION));
		vista.addObject("beanBusqueda", beanBusqueda);
		vista.addObject("listadoMuestras", muestrasPage.getContent());
		
		PaginadorBean paginadorBean = new PaginadorBean(muestrasPage.getTotalPages(), currentPage, muestrasPage.getTotalElements(), "/gestor/log/list");		
		vista.addObject("paginadorBean", paginadorBean);
	}
	
}
