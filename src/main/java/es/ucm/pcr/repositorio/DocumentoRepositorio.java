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

package es.ucm.pcr.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.beans.DocumentoBusquedaBean;
import es.ucm.pcr.modelo.orm.Documento;

@Repository
public interface DocumentoRepositorio extends PagingAndSortingRepository<Documento, Integer> {

	@Query("SELECT documento FROM Documento documento "
			+ "WHERE 1=1 "
			+ "and (:#{#params.id} is null or documento.id = :#{#params.id}) "
			+ "and (:#{#params.idCentro} is null or documento.centro.id = :#{#params.idCentro}) "
			+ "and (:#{#params.idLaboratorioCentro} is null or documento.laboratorioCentro.id = :#{#params.idLaboratorioCentro}) "
			+ "and (:#{#params.idLaboratorioVisavet} is null or documento.laboratorioVisavet.id = :#{#params.idLaboratorioVisavet}) "
			+ "and (:#{#params.idMuestra} is null or documento.muestra.id = :#{#params.idMuestra}) "
			+ "and (:#{#params.idPlacaLaboratorio} is null or documento.placaLaboratorio.id = :#{#params.idPlacaLaboratorio}) "
			+ "and (:#{#params.idPlacaVisavet} is null or documento.placaVisavet.id = :#{#params.idPlacaVisavet}) "
			+ "and (:#{#params.tipo} is null or documento.tipo = :#{#params.tipo}) ")
	public List<Documento> findByParams(@Param("params") DocumentoBusquedaBean params);
	
	
	
	@Query("SELECT documento FROM Documento documento "
			+ "WHERE 1=1 "
			+ "and (:#{#params.id} is null or documento.id = :#{#params.id}) "
			+ "and (:#{#params.idCentro} is null or documento.centro.id = :#{#params.idCentro}) "
			+ "and (:#{#params.idLaboratorioCentro} is null or documento.laboratorioCentro.id = :#{#params.idLaboratorioCentro}) "
			+ "and (:#{#params.idLaboratorioVisavet} is null or documento.laboratorioVisavet.id = :#{#params.idLaboratorioVisavet}) "
			+ "and (:#{#params.idMuestra} is null or documento.muestra.id = :#{#params.idMuestra}) "
			+ "and (:#{#params.idPlacaLaboratorio} is null or documento.placaLaboratorio.id = :#{#params.idPlacaLaboratorio}) "
			+ "or  (documento.placaVisavet.id in (:#{#params.placasVisavet})) "
			+ "and (:#{#params.idPlacaVisavet} is null or documento.placaVisavet.id = :#{#params.idPlacaVisavet}) "
			+ "and (:#{#params.tipo} is null or documento.tipo = :#{#params.tipo}) ")
	public List<Documento> findDocuPlacaLabYPlacaVis(@Param("params") DocumentoBusquedaBean params);
	
	
}
