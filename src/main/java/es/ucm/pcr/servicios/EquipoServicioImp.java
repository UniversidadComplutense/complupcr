package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEquipo;
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.repositorio.EquipoRepositorio;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;

@Service
public class EquipoServicioImp implements EquipoServicio{

	@Autowired
	EquipoRepositorio equipoRepositorio;
	
	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	public Equipo mapeoBeanEntidadEquipo(BeanEquipo beanEquipo) throws Exception{
		Equipo equipo = new Equipo();
		
		equipo.setId(beanEquipo.getId());
		equipo.setNombre(beanEquipo.getNombre());	
		equipo.setCapacidad(beanEquipo.getCapacidad());
//		equipo.setLaboratorioCentro(beanEquipo.getLaboratorioCentro());
		// Laboratorio Ucm  seleccionado
		if (beanEquipo.getLabUcmSeleccionado() != null && laboratorioCentroRepositorio.existsById(beanEquipo.getLabUcmSeleccionado()))
		{
				Optional<LaboratorioCentro> laboratorioGuardar = laboratorioCentroRepositorio.findById(beanEquipo.getLabUcmSeleccionado());
				equipo.setLaboratorioCentro(laboratorioGuardar.get());				
		} else {
			equipo.setLaboratorioCentro(null);
		}
	
		return equipo;
	}

	public BeanEquipo mapeoEntidadBeanEquipo(Equipo equipo) throws Exception{
		BeanEquipo beanEquipo = new BeanEquipo();
		
		beanEquipo.setId(equipo.getId());
		beanEquipo.setNombre(equipo.getNombre());
		beanEquipo.setCapacidad(equipo.getCapacidad());
		beanEquipo.setLaboratorioCentro(equipo.getLaboratorioCentro());
		beanEquipo.setLabUcmSeleccionado(laboratorioUcmSeleccionadoEquipo(equipo));

		return beanEquipo;	
	}
	
	public List<BeanEquipo> listaEquiposOrdenada() throws Exception{
		// cargo todos los rols de BBDD
		List<BeanEquipo> listaEquipos = new ArrayList<BeanEquipo>();
		for (Equipo equipo: equipoRepositorio.findAll())
		{
			BeanEquipo beanEquipo = new BeanEquipo();
			beanEquipo = mapeoEntidadBeanEquipo(equipo);
			listaEquipos.add(beanEquipo);
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaEquipos);
		return listaEquipos;
	}
	
	public Map<Integer,String> mapaEquipos (List<BeanEquipo> equipos) throws Exception
	{
		Map<Integer, String> mapaEquipos = new HashMap<Integer, String>();
		for (BeanEquipo equipo : equipos)
		{
			mapaEquipos.put(equipo.getId(), equipo.getNombre());
		}
		return mapaEquipos;
	}
	
	public Equipo save (Equipo equipo) throws Exception{
		return equipoRepositorio.save(equipo);
	}
	
	public void deleteById (Integer idEquipo) throws Exception{
		equipoRepositorio.deleteById(idEquipo);
	}
	
	public Optional<Equipo> findById (Integer idEquipo) throws Exception{
		return equipoRepositorio.findById(idEquipo);
	}
	
	// Un equipo puede no estar asociado a ningún laboratorio UCM,
	// por lo que puede ser null,o el id de dicho laboratorio UCM.
	// Esta función devuelve el valos de ese IdLaboratorioUCM es caso de que exista
	public Integer laboratorioUcmSeleccionadoEquipo (Equipo equipo) throws Exception
	{
		Integer labUcmSeleccionado;
		if (equipo.getLaboratorioCentro() != null)
		{
			labUcmSeleccionado = equipo.getLaboratorioCentro().getId();
		}
		else
		{
			labUcmSeleccionado = null;
		}
		return labUcmSeleccionado;
	}
	
	
}
