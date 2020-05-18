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
