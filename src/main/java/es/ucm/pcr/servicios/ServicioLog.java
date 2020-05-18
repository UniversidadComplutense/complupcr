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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.LogMuestraListadoBean;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public interface ServicioLog {

	/**
	 * Actualiza el estado del log de una muestra
	 * 
	 * @param idMuestra
	 * @param estado
	 */
	public void actualizarEstadoMuestra(Integer idMuestra, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de un lote
	 * 
	 * @param idLote
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorLote(Integer idLote, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de una Placa visavet
	 * 
	 * @param idPlacaVisavet
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorPlacaVisavet(Integer idPlacaVisavet, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de una Placa de laboratorio
	 * 
	 * @param idPlacaLaboratorio
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorPlacaLaboratorio(Integer idPlacaLaboratorio, BeanEstado estado);
	
	/**
	 * Busqueda de logs por parametros
	 * 
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<LogMuestraListadoBean> findLogMuestraByParam(LogMuestraBusquedaBean params, Pageable pageable);
	
	/**
	 * Borra estados asociados a una muestra
	 * @param idMuestra
	 */
	public void borrarEstadosMuestra(Integer idMuestra);
	
	/**
	 * Actualizar estado muestra
	 * @param muestra
	 * @param lote
	 * @param placaVisavet
	 * @param placaLaboratorio
	 * @param estadoActualizar
	 */
	public void actualizarEstadoMuestra(Muestra muestra,Lote lote, PlacaVisavet placaVisavet,
			PlacaLaboratorio placaLaboratorio, BeanEstado estadoActualizar);

}
