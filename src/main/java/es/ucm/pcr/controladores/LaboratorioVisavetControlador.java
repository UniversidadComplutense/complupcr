package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;

import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.servicios.LaboratorioVisavetServicio;

@Controller
public class LaboratorioVisavetControlador {

	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	
	@Autowired
	LaboratorioVisavetServicio laboratorioVisavetServicio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los laboratorioVisavets
	// Punto de entrada a la gestión de laboratorioVisavets
	@RequestMapping(value="/gestor/listaLaboratorioVisavet", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView GestionLaboratorioVisavet(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionLaboratorioVisavet");
	
		// cargo todos los laboratorioVisavets de BBDD
		List<BeanLaboratorioVisavet> listaLaboratorioVisavet = new ArrayList<BeanLaboratorioVisavet>();
		for (LaboratorioVisavet laboratorioVisavet: laboratorioVisavetRepositorio.findAll())
		{
			listaLaboratorioVisavet.add(
					new BeanLaboratorioVisavet( laboratorioVisavet.getId(), 
												laboratorioVisavet.getNombre(), 
												laboratorioVisavet.getCapacidad(),
												laboratorioVisavet.getOcupacion(),
												laboratorioVisavet.getDocumentos(),
												laboratorioVisavet.getPlacaVisavets(),
												"L"));
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaLaboratorioVisavet);
		vista.addObject("listaLaboratorioVisavet", listaLaboratorioVisavet);
	
		return vista;
	}	
	
	// da de alta un nuevo laboratorio Visavet
	@RequestMapping(value="/gestor/altaLaboratorioVisavet", method=RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
	public ModelAndView AltaLaboratorioVisavet(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaLaboratorioVisavet");
	
		BeanLaboratorioVisavet beanLaboratorioVisavet = new BeanLaboratorioVisavet();
		// le indicamos la acción a relizar: A alta de un rol
		beanLaboratorioVisavet.setAccion("A");
		vista.addObject("formBeanLaboratorioVisavet", beanLaboratorioVisavet);

		return vista;
	}
	
	   // Alta/modificación de laboratorioVisavet 
		@RequestMapping(value="/gestor/altaLaboratorioVisavet", method=RequestMethod.POST)	
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView grabarAltaLaboratorioVisavet ( @ModelAttribute("formBeanLaboratorioVisavet") BeanLaboratorioVisavet beanLaboratorioVisavet, HttpSession session) throws Exception {

			// Damos de alta nuevo laboratorioVisavet
			if (beanLaboratorioVisavet.getAccion().equals("A"))
			{
//				LaboratorioVisavet laboratorioVisavet = new LaboratorioVisavet();
//				laboratorioVisavet.setNombre(beanLaboratorioVisavet.getNombre());
//				laboratorioVisavet.setCapacidad(beanLaboratorioVisavet.getCapacidad());
//				laboratorioVisavetRepositorio.save(laboratorioVisavet);
				
//				laboratorioVisavetRepositorio.save(laboratorioVisavetServicio.mapeoBeanEntidadLaboratorioVisavet(beanLaboratorioVisavet));
				laboratorioVisavetServicio.guardarLaboratorioVisavet(laboratorioVisavetServicio.mapeoBeanEntidadLaboratorioVisavet(beanLaboratorioVisavet));
				
			}
			// Modificamos laboratorioVisavet existente, menos mail
			if (beanLaboratorioVisavet.getAccion().equals("M"))
			{	
				// Buscamos el laboratorioVisavet a modificar, y volcamos los datos recogidos por el formulario
				Optional<LaboratorioVisavet> laboratorioVisavet = laboratorioVisavetRepositorio.findById(beanLaboratorioVisavet.getId());
				// añadimos campos del formulario
//				laboratorioVisavet.get().setNombre(beanLaboratorioVisavet.getNombre());
//				laboratorioVisavet.get().setCapacidad(beanLaboratorioVisavet.getCapacidad());
//				laboratorioVisavetRepositorio.save(laboratorioVisavet.get());
				
//				laboratorioVisavetRepositorio.save(laboratorioVisavetServicio.mapeoBeanEntidadLaboratorioVisavet(beanLaboratorioVisavet));
				laboratorioVisavetServicio.guardarLaboratorioVisavet(laboratorioVisavetServicio.mapeoBeanEntidadLaboratorioVisavet(beanLaboratorioVisavet));
			}

			// Volvemos a grabar mas laboratorioVisavet
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioVisavet",true));	
			return vista;		
		}	
		
		// Modificamos un laboratorioVisavet
		@RequestMapping(value = "/gestor/editarLaboratorioVisavet", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView editarLaboratorioVisavet(@RequestParam("idLaboratorioVisavet") Integer idLaboratorioVisavet) throws Exception {

			ModelAndView vista = new ModelAndView("VistaLaboratorioVisavet");
			
			// Busco el laboratorioVisavet a modificar
			Optional<LaboratorioVisavet> laboratorioVisavet = laboratorioVisavetRepositorio.findById(idLaboratorioVisavet);
			// cargo el beanLaboratorioVisavet con lo datos del laboratorioVisavet a modificar
//			BeanLaboratorioVisavet beanLaboratorioVisavet = new BeanLaboratorioVisavet();
//			beanLaboratorioVisavet.setId(laboratorioVisavet.get().getId());
//			beanLaboratorioVisavet.setNombre(laboratorioVisavet.get().getNombre());
//			beanLaboratorioVisavet.setCapacidad(laboratorioVisavet.get().getCapacidad());
			
			BeanLaboratorioVisavet beanLaboratorioVisavet = laboratorioVisavetServicio.mapeoEntidadBeanLaboratorioVisavet(laboratorioVisavet.get());
			
			// le indicamos la acción a relizar: M modificación de un laboratorioVisavet
			beanLaboratorioVisavet.setAccion("M");
			
			vista.addObject("formBeanLaboratorioVisavet", beanLaboratorioVisavet);
		
			return vista;
		}		
		
		@RequestMapping(value = "/gestor/borrarLaboratorioVisavet", method = RequestMethod.GET)
		@PreAuthorize("hasAnyRole('ADMIN','GESTOR')")
		public ModelAndView borrarLaboratorioVisavet(@RequestParam("idLaboratorioVisavet") Integer idLaboratorioVisavet) throws Exception {
			
//			laboratorioVisavetRepositorio.deleteById(idLaboratorioVisavet);
			laboratorioVisavetServicio.borrarLaboratorioVisavet(idLaboratorioVisavet);
			
			// Volvemos a grabar mas visavet
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioVisavet",true));	
			return vista;
		}		
		
}
