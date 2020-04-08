package es.ucm.pcr.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.modelo.orm.LogMuestras;


@Repository
public interface LogMuestrasRepositorio extends JpaRepository<LogMuestras, Integer> {

	@Query("SELECT logMuestra FROM LogMuestras logMuestra "
			+ "WHERE 1=1 "
			+ "and (:#{#params.etiquetaMuestra} is null or :#{#params.etiquetaMuestra} = '' or logMuestra.muestra.etiqueta = :#{#params.etiquetaMuestra}) "			
			+ "and (:#{#params.nhcPaciente} is null or :#{#params.nhcPaciente} = '' or logMuestra.muestra.paciente.nhc = :#{#params.nhcPaciente}) "
			+ "and (:#{#params.idLote} is null or logMuestra.lote.id = :#{#params.idLote}) "
			+ "and (:#{#params.idPlacaLaboratorio} is null or logMuestra.placaLaboratorio.id = :#{#params.idPlacaLaboratorio}) "
			+ "and (:#{#params.idPlacaVisavet} is null or logMuestra.placaVisavet.id = :#{#params.idPlacaVisavet}) ")
	public Page<LogMuestras> findByParams(@Param("params") LogMuestraBusquedaBean params, Pageable pageable);
	
	@Query("SELECT logMuestra FROM LogMuestras logMuestra "
			+ "WHERE 1=1 "
			+ "and (:#{#params.idMuestra} is null or logMuestra.muestra.id = :#{#params.idMuestra}) "
			+ "and (:#{#params.etiquetaMuestra} is null or logMuestra.muestra.etiqueta = :#{#params.etiquetaMuestra}) "
			+ "and (:#{#params.idLote} is null or logMuestra.lote.id = :#{#params.idLote}) "
			+ "and (:#{#params.idPlacaLaboratorio} is null or logMuestra.placaLaboratorio.id = :#{#params.idPlacaLaboratorio}) "
			+ "and (:#{#params.idPlacaVisavet} is null or logMuestra.placaVisavet.id = :#{#params.idPlacaVisavet}) ")
	public List<LogMuestras> findByParams(@Param("params") LogMuestraBusquedaBean params);
	
}
