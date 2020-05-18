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




@Controller
@RequestMapping(value="/laboratorioUni")
public class LaboratorioUniController {
/*	@Autowired
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
	*/
	
}
