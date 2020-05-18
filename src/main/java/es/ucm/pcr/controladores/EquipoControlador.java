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
import java.util.Map;
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

import es.ucm.pcr.beans.BeanEquipo;
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.servicios.EquipoServicio;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;

@Controller
public class EquipoControlador {
	
	@Autowired
	EquipoServicio equipoServicio;
	
	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;
	
	//	Muestra una lista ordenada con los equipos
	// Punto de entrada a la gestión de equipos
	@RequestMapping(value="/gestor/listaEquipos", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView GestionEquipo(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionEquipos");
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			session.removeAttribute("mensajeError");
		}
		// cargo todos los equipos de BBDD
		List<BeanEquipo> listaEquipos = new ArrayList<BeanEquipo>();
		listaEquipos = equipoServicio.listaEquiposOrdenada();
		vista.addObject("listaEquipos", listaEquipos);
		return vista;
	}	
	
	// da de alta un nuevo equipo
	@RequestMapping(value="/gestor/altaEquipo", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView AltaEquipo(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaEquipo");
		BeanEquipo beanEquipo = new BeanEquipo();
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			beanEquipo = (BeanEquipo) session.getAttribute("beanEquipo");
			session.removeAttribute("mensajeError");
			session.removeAttribute("beanEquipo");
		}
		// le indicamos la acción a relizar: A alta de un equipo
		beanEquipo.setAccion("A");
		vista.addObject("formBeanEquipo", beanEquipo);
		
		// añadimos los laboratorios Centro
		Map<Integer,String> mapaLaboratoriosCentro = laboratorioCentroServicio.mapaLaboratoriosCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
		vista.addObject("mapaLaboratoriosCentro", mapaLaboratoriosCentro);

		return vista;
	}
	
   // Alta/modificación de equipo 
	@RequestMapping(value="/gestor/altaEquipo", method=RequestMethod.POST)	
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView grabarAltaEquipo ( @ModelAttribute("formBeanEquipo") BeanEquipo beanEquipo, HttpSession session) throws Exception {
		System.out.println("Lab ucm seleccionado: " + beanEquipo.getLabUcmSeleccionado() + "  " + beanEquipo.getAccion());
		String mensaje = null;
		// Damos de alta nuevo equipo
		if (beanEquipo.getAccion().equals("A"))
		{
			try {
			equipoServicio.save(equipoServicio.mapeoBeanEntidadEquipo(beanEquipo));
			} catch (DataIntegrityViolationException e) {
				mensaje = "Ya existe un equipo con ese código.";
				session.setAttribute("mensajeError", mensaje);
				session.setAttribute("beanEquipo", beanEquipo);
				ModelAndView vista = new ModelAndView(new RedirectView("/gestor/altaEquipo",true));
				return vista;
			}
		}
		// Modificamos equipo existente
		if (beanEquipo.getAccion().equals("M"))
		{
			// Buscamos el equipo a modificar, y volcamos los datos recogidos por el formulario
			Optional<Equipo> equipo = equipoServicio.findById(beanEquipo.getId());
			// añadimos campos del formulario
			equipoServicio.save(equipoServicio.mapeoBeanEntidadEquipo(beanEquipo));
		}

		// Volvemos a grabar mas equipos
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaEquipos",true));	
		return vista;		
	}	
	
	// Modificamos un equipo
	@RequestMapping(value = "/gestor/editarEquipo", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView editarEquipo(@RequestParam("idEquipo") Integer idEquipo) throws Exception {

		ModelAndView vista = new ModelAndView("VistaEquipo");
		
		// Busco el equipo a modificar
		Optional<Equipo> equipo = equipoServicio.findById(idEquipo);
		// cargo el beanEquipo con lo datos del equipo a modificar
		BeanEquipo beanEquipo = equipoServicio.mapeoEntidadBeanEquipo(equipo.get());
	
		// le indicamos la acción a relizar: M modificación de un equipo
		beanEquipo.setAccion("M");
		
		vista.addObject("formBeanEquipo", beanEquipo);
		
		// añadimos los laboratorios Centro
		Map<Integer,String> mapaLaboratoriosCentro = laboratorioCentroServicio.mapaLaboratoriosCentro(laboratorioCentroServicio.listaLaboratoriosCentroOrdenada());
		vista.addObject("mapaLaboratoriosCentro", mapaLaboratoriosCentro);

	
		return vista;
	}	
	
	@RequestMapping(value = "/gestor/borrarEquipo", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView borrarEquipo(@RequestParam("idEquipo") Integer idEquipo, HttpSession session) throws Exception {
		try {
		equipoServicio.deleteById(idEquipo);
		} catch (DataIntegrityViolationException e) {
			
			String mensaje = "No se puede borrar el equipo " + equipoServicio.findById(idEquipo).get().getNombre() + " porque tiene información asociada.";
			session.setAttribute("mensajeError", mensaje);
		}
		
		// Volvemos a grabar mas equipos
		ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaEquipos",true));	
		return vista;
	}	
	

}
