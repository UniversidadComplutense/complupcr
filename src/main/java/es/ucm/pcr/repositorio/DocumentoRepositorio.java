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
			+ "and (:#{#params.idPlacaVisavet} is null or documento.placaVisavet.id = :#{#params.idPlacaVisavet}) ")
	public List<Documento> findByParams(@Param("params") DocumentoBusquedaBean params);
}
