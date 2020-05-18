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
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

public interface LaboratorioVisavetServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad LaboratorioVisavet
	 * 
	 * @param BeanLaboratorioVisavet
	 * @return LaboratorioVisavet
	 */
	public LaboratorioVisavet mapeoBeanEntidadLaboratorioVisavet(BeanLaboratorioVisavet beanLaboratorioVisavet) throws Exception;
		
	/**
	 * Hago el mapeo de la entidad al bean LaboratorioVisavet
	 * 
	 * @param LaboratorioVisavet
	 * @return BeanLaboratorioVisavet
	 */
	public BeanLaboratorioVisavet mapeoEntidadBeanLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) throws Exception;
	
	/**
	 * Lista ordenada de bean LaboratorioVisavet
	 * 
	 * @param 
	 * @return BeanLaboratorioVisavet
	 */
	public List<BeanLaboratorioVisavet> listaLaboratoriosVisavetOrdenada() throws Exception;	

	/**
	 * Mapa de  LaboratorioVisavet
	 * 
	 * @param List<BeanLaboratorioVisavet>
	 * @return Map<String,String>
	 */
	public Map<Integer,String> mapaLaboratoriosVisavet (List<BeanLaboratorioVisavet> laboratoriosVisavet) throws Exception;

	/**
	 * Guardar Laboratorio Visavet
	 * 
	 * @param LaboratorioVisavet
	 * @return void >
	 */
	public LaboratorioVisavet save(LaboratorioVisavet laboratorioVisavet) throws Exception;
	
	/**
	 * Borrar Laboratorio Visavet
	 * 
	 * @param Integer idLaboratorioVisavet
	 * @return void >
	 */
	public void deleteById(Integer idLaboratorioVisavet) throws Exception;	

	/**
	 * Buscar Laboratorio por Id
	 * 
	 * @param Integer idLaboratorioVisavet
	 * @return void Optional <LaboratorioVisavet>
	 */
	public Optional <LaboratorioVisavet> findById(Integer idLaboratorioVisavet) throws Exception;

	
	// JAVI
	public Page<PlacaLaboratorioVisavetBean> buscarPlacas(BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			Pageable pageable) throws Exception;
	// JAVI
	public PlacaLaboratorioVisavetBean buscarPlaca(Integer id) throws Exception;

	// JAVI
	public boolean recepcionarPlaca(Integer id) throws Exception;
	
	// JAVI
	public List<PlacaLaboratorioVisavetBean> buscarPlacasPorIdPlacaLaboratorio(Integer idPlacaLaboratorio) throws Exception;
	
	// JAVI
		public boolean anularRecepcionarPlaca(Integer id) throws Exception;
	
	/**
	 * Buscar laboratorio visavet por nombre
	 * 
	 * @param nombre
	 * @return Optional<LaboratorioVisavet>
	 */
	public Optional<LaboratorioVisavet> findByNombre(String nombre);

	
	
	
}
