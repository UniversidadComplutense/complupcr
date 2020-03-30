package es.ucm.pcr.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;

@Controller
public class LaboratorioCentroControlador {

	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	//	Muestra una lista ordenada ap1, ap2,nombre con los laboratorioCentros
	// Punto de entrada a la gestión de laboratorioCentros
	@RequestMapping(value="/gestor/listaLaboratorioCentro", method=RequestMethod.GET)
	public ModelAndView GestionLaboratorioCentro(HttpSession session) throws Exception {
		ModelAndView vista = new ModelAndView("VistaGestionLaboratorioCentro");
		
		System.out.println("estro lb ucm");
	
		// cargo todos los laboratorioCentros de BBDD
		List<BeanLaboratorioCentro> listaLaboratorioCentro = new ArrayList<BeanLaboratorioCentro>();
		for (LaboratorioCentro laboratorioCentro: laboratorioCentroRepositorio.findAll())
		{
			listaLaboratorioCentro.add(new BeanLaboratorioCentro(laboratorioCentro.getId(), laboratorioCentro.getNombre(), "L"));
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaLaboratorioCentro);
		vista.addObject("listaLaboratorioCentro", listaLaboratorioCentro);
	
		return vista;
	}	

	// da de alta un nuevo rol
	@RequestMapping(value="/gestor/altaLaboratorioCentro", method=RequestMethod.GET)
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
		public ModelAndView grabarAltaLaboratorioCentro ( @ModelAttribute("formBeanLaboratorioCentro") BeanLaboratorioCentro beanLaboratorioCentro, HttpSession session) throws Exception {

			// Damos de alta nuevo laboratorioCentro
			if (beanLaboratorioCentro.getAccion().equals("A"))
			{
				LaboratorioCentro laboratorioCentro = new LaboratorioCentro();
				laboratorioCentro.setNombre(beanLaboratorioCentro.getNombre());
				laboratorioCentroRepositorio.save(laboratorioCentro);
			}
			// Modificamos laboratorioCentro existente, menos mail
			if (beanLaboratorioCentro.getAccion().equals("M"))
			{	
				// Buscamos el laboratorioCentro a modificar, y volcamos los datos recogidos por el formulario
				Optional<LaboratorioCentro> laboratorioCentro = laboratorioCentroRepositorio.findById(beanLaboratorioCentro.getId());
				// añadimos campos del formulario
				laboratorioCentro.get().setNombre(beanLaboratorioCentro.getNombre());
				laboratorioCentroRepositorio.save(laboratorioCentro.get());
			}

			// Volvemos a grabar mas laboratorioCentro
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioCentro",true));	
			return vista;		
		}	
		
		// Modificamos un laboratorioCentro
		@RequestMapping(value = "/gestor/editarLaboratorioCentro", method = RequestMethod.GET)
		public ModelAndView editarLaboratorioCentro(@RequestParam("idLaboratorioCentro") Integer idLaboratorioCentro) throws Exception {

			ModelAndView vista = new ModelAndView("VistaLaboratorioCentro");
			
			// Busco el laboratorioCentro a modificar
			Optional<LaboratorioCentro> laboratorioCentro = laboratorioCentroRepositorio.findById(idLaboratorioCentro);
			// cargo el beanLaboratorioCentro con lo datos del laboratorioCentro a modificar
			BeanLaboratorioCentro beanLaboratorioCentro = new BeanLaboratorioCentro();
			beanLaboratorioCentro.setId(laboratorioCentro.get().getId());
			beanLaboratorioCentro .setNombre(laboratorioCentro.get().getNombre());
			
			// le indicamos la acción a relizar: M modificación de un laboratorioCentro
			beanLaboratorioCentro.setAccion("M");
			
			vista.addObject("formBeanLaboratorioCentro", beanLaboratorioCentro);
		
			return vista;
		}	

		@RequestMapping(value = "/gestor/borrarLaboratorioCentro", method = RequestMethod.GET)
		public ModelAndView borrarLaboratorioCentro(@RequestParam("idLaboratorioCentro") Integer idLaboratorioCentro) throws Exception {
			
			laboratorioCentroRepositorio.deleteById(idLaboratorioCentro);
			
			// Volvemos a grabar mas centros
			ModelAndView vista = new ModelAndView(new RedirectView("/gestor/listaLaboratorioCentro",true));	
			return vista;
		}
		
}
