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

import es.ucm.pcr.beans.BeanEquipo;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;

@Controller
public class LaboratorioCentroControlador {
	
	@Autowired
	LaboratorioCentroServicio laboratorioCentroServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los laboratorioCentros
	// Punto de entrada a la gestión de laboratorioCentros
	@RequestMapping(value="/gestor/listaLaboratorioCentro", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView GestionLaboratorioCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionLaboratorioCentro");
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
			vista.addObject("mensajeError", mensajeError);
			session.removeAttribute("mensajeError");
		}
		// cargo todos los laboratorioCentros de BBDD
		List<BeanLaboratorioCentro> listaLaboratorioCentro = new ArrayList<BeanLaboratorioCentro>();
		listaLaboratorioCentro = laboratorioCentroServicio.listaLaboratoriosCentroOrdenada();
		vista.addObject("listaLaboratorioCentro", listaLaboratorioCentro);
		return vista;
	}	

	// da de alta un nuevo rol
	@RequestMapping(value="/gestor/altaLaboratorioCentro", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView AltaLaboratorioCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLaboratorioCentro");
		BeanLaboratorioCentro beanLaboratorioCentro = new BeanLaboratorioCentro();
		
		// le indicamos la acción a relizar: A alta de un rol
		beanLaboratorioCentro.setAccion("A");
		vista.addObject("formBeanLaboratorioCentro", beanLaboratorioCentro);
		
		return vista;
	}
	
	   // Alta/modificación de laboratorioCentro 
		@RequestMapping(value="/gestor/altaLaboratorioCentro", method=RequestMethod.POST)	
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView grabarAltaLaboratorioCentro ( @ModelAttribute("formBeanLaboratorioCentro") BeanLaboratorioCentro beanLaboratorioCentro, HttpSession session) throws Exception {

			// Damos de alta nuevo laboratorioCentro
			if (beanLaboratorioCentro.getAccion().equals("A"))
			{
				laboratorioCentroServicio.save(laboratorioCentroServicio.mapeoBeanEntidadLaboratorioCentro(beanLaboratorioCentro));
			}
			// Modificamos laboratorioCentro existente
			if (beanLaboratorioCentro.getAccion().equals("M"))
			{	
				// Buscamos el laboratorioCentro a modificar, y volcamos los datos recogidos por el formulario
				Optional<LaboratorioCentro> laboratorioCentro = laboratorioCentroServicio.findById(beanLaboratorioCentro.getId());
				// añadimos campos del formulario
				laboratorioCentroServicio.save(laboratorioCentroServicio.mapeoBeanEntidadLaboratorioCentro(beanLaboratorioCentro));
			}

			// Volvemos a grabar mas laboratorioCentro
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioCentro",true));	
			return vista;		
		}	
		
		// Modificamos un laboratorioCentro
		@RequestMapping(value = "/gestor/editarLaboratorioCentro", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView editarLaboratorioCentro(@RequestParam("idLaboratorioCentro") Integer idLaboratorioCentro) throws Exception {

			ModelAndView vista = new ModelAndView("VistaLaboratorioCentro");
			
			// Busco el laboratorioCentro a modificar
			Optional<LaboratorioCentro> laboratorioCentro = laboratorioCentroServicio.findById(idLaboratorioCentro);
			// cargo el beanLaboratorioCentro con lo datos del laboratorioCentro a modificar
			BeanLaboratorioCentro beanLaboratorioCentro = laboratorioCentroServicio.mapeoEntidadBeanLaboratorioCentro(laboratorioCentro.get());
			
			// le indicamos la acción a relizar: M modificación de un laboratorioCentro
			beanLaboratorioCentro.setAccion("M");
			
			vista.addObject("formBeanLaboratorioCentro", beanLaboratorioCentro);
			
			// Añdimos  los equipos asociados al centro
			List<BeanEquipo> listaEquipos = laboratorioCentroServicio.listaEquiposLaboratorioCentro(laboratorioCentro.get());
			if (listaEquipos.isEmpty())
			{
				vista.addObject("listaEquipos", null);
			}
			else 
			{
				vista.addObject("listaEquipos", listaEquipos);
			}
		
			return vista;
		}	

		@RequestMapping(value = "/gestor/borrarLaboratorioCentro", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView borrarLaboratorioCentro(@RequestParam("idLaboratorioCentro") Integer idLaboratorioCentro, HttpSession session) throws Exception {
			try {
			laboratorioCentroServicio.deleteById(idLaboratorioCentro);
			} catch (DataIntegrityViolationException e) {
				String mensaje = "No se puede borrar el centro " + laboratorioCentroServicio.buscarLaboratorioById(idLaboratorioCentro).getNombre() + " porque tiene información asociada.";
				session.setAttribute("mensajeError", mensaje);
			}
			
			// Volvemos a grabar mas centros
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioCentro",true));	
			return vista;
		}
		
}
