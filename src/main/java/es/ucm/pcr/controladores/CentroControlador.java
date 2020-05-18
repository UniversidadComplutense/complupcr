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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.servicios.CentroServicio;


@Controller
public class CentroControlador {
	
	@Autowired
	CentroServicio centroServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los centros
	// Punto de entrada a la gestión de centros
	@RequestMapping(value="/gestor/listaCentros", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView GestionCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionCentros");
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			session.removeAttribute("mensajeError");
		}
		// cargo todos los centros de BBDD
		List<BeanCentro> listaCentros = new ArrayList<BeanCentro>();
		listaCentros = centroServicio.listaCentrosOrdenada();
		vista.addObject("listaCentros", listaCentros);
		return vista;
	}	
	
	// da de alta un nuevo centro
	@RequestMapping(value="/gestor/altaCentro", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView AltaCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaCentro");
		BeanCentro beanCentro = new BeanCentro();
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			beanCentro = (BeanCentro) session.getAttribute("beanCentro");
			session.removeAttribute("mensajeError");
			session.removeAttribute("beanCentro");
		}
		// le indicamos la acción a relizar: A alta de un centro
		beanCentro.setAccion("A");
		vista.addObject("formBeanCentro", beanCentro);

		return vista;
	}
	
   // Alta/modificación de centro 
	@RequestMapping(value="/gestor/altaCentro", method=RequestMethod.POST)	
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView grabarAltaCentro ( @ModelAttribute("formBeanCentro") BeanCentro beanCentro, HttpSession session) throws Exception {

		String mensaje = null;
		// Damos de alta nuevo centro
		if (beanCentro.getAccion().equals("A"))
		{
			try {
			centroServicio.save(centroServicio.mapeoBeanEntidadCentro(beanCentro));
			} catch (DataIntegrityViolationException e) {
				mensaje = "Ya existe un centro con ese código.";
				session.setAttribute("mensajeError", mensaje);
				session.setAttribute("beanCentro", beanCentro);
				ModelAndView vista = new ModelAndView(new RedirectView("/gestor/altaCentro",true));
				return vista;
			}
		}
		// Modificamos centro existente
		if (beanCentro.getAccion().equals("M"))
		{
			// Buscamos el centro a modificar, y volcamos los datos recogidos por el formulario
			Optional<Centro> centro = centroServicio.findById(beanCentro.getId());
			// añadimos campos del formulario
			centroServicio.save(centroServicio.mapeoBeanEntidadCentro(beanCentro));
		}

		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaCentros",true));	
		return vista;		
	}	
	
	// Modificamos un centro
	@RequestMapping(value = "/gestor/editarCentro", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView editarCentro(@RequestParam("idCentro") Integer idCentro) throws Exception {

		ModelAndView vista = new ModelAndView("VistaCentro");
		
		// Busco el centro a modificar
		Optional<Centro> centro = centroServicio.findById(idCentro);
		// cargo el beanCentro con lo datos del centro a modificar
		BeanCentro beanCentro = centroServicio.mapeoEntidadBeanCentro(centro.get());
	
		// le indicamos la acción a relizar: M modificación de un centro
		beanCentro.setAccion("M");
		
		vista.addObject("formBeanCentro", beanCentro);
	
		return vista;
	}	
	
	@RequestMapping(value = "/gestor/borrarCentro", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView borrarCentro(@RequestParam("idCentro") Integer idCentro, HttpSession session) throws Exception {
		try {
		centroServicio.deleteById(idCentro);
		} catch (DataIntegrityViolationException e) {
			
			String mensaje = "No se puede borrar el centro " + centroServicio.findById(idCentro).get().getNombre() + " porque tiene información asociada.";
			session.setAttribute("mensajeError", mensaje);
		}
		
		// Volvemos a grabar mas centros
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaCentros",true));	
		return vista;
	}	

}
