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

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.modelo.orm.Centro;

public interface CentroServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad Centro
	 * 
	 * @param BeanCentro
	 * @return Centro
	 */
	public Centro mapeoBeanEntidadCentro(BeanCentro beanCentro) throws Exception;
	
	/**
	 * Hago el mapeo de la entidad al bean Centro
	 * 
	 * @param Centro
	 * @return BeanCentro
	 */
	public BeanCentro mapeoEntidadBeanCentro(Centro centro) throws Exception;

	/**
	 * Lista de centros ordenada
	 * 
	 * @param 
	 * @return List<BeanCentro>
	 */
	public List<BeanCentro> listaCentrosOrdenada() throws Exception;
	
	/**
	 * Mapa de Centros
	 * 
	 * @param List<BeanCentro>
	 * @return Map<String,String>
	 */
	public Map<Integer,String> mapaCentros(List<BeanCentro> centros) throws Exception;
	
	/**
	 * Guardar Centro
	 * 
	 * @param Centro
	 * @return Centro >
	 */
	public Centro save(Centro centro) throws Exception;
	
	/**
	 * Borrar Centro
	 * 
	 * @param Integer idCentro
	 * @return void findById
	 */
	public void deleteById(Integer idCentro) throws Exception;

	/**
	 * Buscar Centro por id
	 * 
	 * @param Integer idCentro
	 * @return Optional<Centro>
	 */
	public Optional<Centro> findById(Integer idCentro) throws Exception;

	/**
	 * Buscar por codCentro
	 * @param codCentro
	 * @return Optional<Centro>
	 */
	public Optional<Centro> findByCodCentro(String codCentro);


}
