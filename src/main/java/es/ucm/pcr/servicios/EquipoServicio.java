package es.ucm.pcr.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import es.ucm.pcr.beans.BeanEquipo;
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;

public interface EquipoServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad Equipo
	 * 
	 * @param BeanEquipo
	 * @return Equipo
	 */
	public Equipo mapeoBeanEntidadEquipo(BeanEquipo beanEquipo) throws Exception;
	
	/**
	 * Hago el mapeo de la entidad al bean Equipo
	 * 
	 * @param Equipo
	 * @return BeanEquipo
	 */
	public BeanEquipo mapeoEntidadBeanEquipo(Equipo equipo) throws Exception;

	/**
	 * Lista de equipos ordenada
	 * 
	 * @param 
	 * @return List<BeanEquipo>
	 */
	public List<BeanEquipo> listaEquiposOrdenada() throws Exception;
	
	/**
	 * Mapa de Equipos
	 * 
	 * @param List<BeanEquipo>
	 * @return Map<String,String>
	 */
	public Map<Integer,String> mapaEquipos(List<BeanEquipo> equipos) throws Exception;
	
	/**
	 * Guardar Equipo
	 * 
	 * @param Equipo
	 * @return Equipo >
	 */
	public Equipo save(Equipo equipo) throws Exception;
	
	/**
	 * Borrar Equipo
	 * 
	 * @param Integer idEquipo
	 * @return void findById
	 */
	public void deleteById(Integer idEquipo) throws Exception;

	/**
	 * Buscar Equipo por id
	 * 
	 * @param Integer idEquipo
	 * @return Optional<Equipo>
	 */
	public Optional<Equipo> findById(Integer idEquipo) throws Exception;
		
	/**
	 * Buscar Equipo por laboratorioCentro
	 * 
	 * @param LaboratorioCentro laboratorioCentro
	 * @return Optional<Equipo>
	 */
	public List<Equipo> findByLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception;
	


}
