package es.ucm.pcr.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.modelo.orm.Lote;


@Repository
public interface LoteRepositorio extends PagingAndSortingRepository<Lote, Integer> {

	
@Query("SELECT lote FROM Lote lote "
			+ "JOIN lote.centro centro "
			+ "JOIN lote.estadoLote estadoLote "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idCentro} is null or centro.id = :#{#params.idCentro}) and "
			+ "(:#{#params.criterioNumLote} is null or lote.numeroLote like :#{#params.criterioNumLote}) and "
			+ "(:#{#params.fechaEnvioIni} is null or lote.fechaEnvio >= :#{#params.fechaEnvioIni}) and "
			+ "(:#{#params.fechaEnvioFin} is null or lote.fechaEnvio <= :#{#params.fechaEnvioFin}) and "
			+ "(:#{#params.idEstado} is null or estadoLote.id = :#{#params.idEstado}) and"
			+ "(:#{#params.laboratorioVisavet} is null or lote.laboratorioVisavet = :#{#params.laboratorioVisavet}) ")
/*
	@Query("SELECT lote FROM Lote lote "
			+ "JOIN lote.centro centro "
			+ "JOIN lote.estadoLote estadoLote "
			+ "LEFT JOIN lote.placaVisavet placaVisavet "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idCentro} is null or centro.id = :#{#params.idCentro}) and "
			+ "(:#{#params.criterioNumLote} is null or lote.numeroLote like :#{#params.criterioNumLote}) and "
			+ "(:#{#params.fechaEnvioIni} is null or lote.fechaEnvio >= :#{#params.fechaEnvioIni}) and "
			+ "(:#{#params.fechaEnvioFin} is null or lote.fechaEnvio <= :#{#params.fechaEnvioFin}) and "
			+ "(:#{#params.idEstado} is null or estadoLote.id = :#{#params.idEstado}) ")
*/
	public Page<Lote> findByParams(@Param("params") LoteBusquedaBean params,
			Pageable pageable);	
	
	@Query("SELECT lote FROM Lote lote "
			+ "JOIN lote.centro centro "
			+ "JOIN lote.estadoLote estadoLote "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idCentro} is null or centro.id = :#{#params.idCentro}) and "
			+ "(:#{#params.criterioNumLote} is null or lote.numeroLote like :#{#params.criterioNumLote}) and "
			+ "(:#{#params.fechaEnvioIni} is null or lote.fechaEnvio >= :#{#params.fechaEnvioIni}) and "
			+ "(:#{#params.fechaEnvioFin} is null or lote.fechaEnvio <= :#{#params.fechaEnvioFin}) and "
			+ "(:#{#params.idEstado} is null or estadoLote.id = :#{#params.idEstado}) ")
	public List<Lote> findByParams(@Param("params") LoteBusquedaBean params);
	
	@Query("SELECT lote FROM Lote lote "
			+ "JOIN lote.centro centro "
			+ "JOIN lote.estadoLote estadoLote "
			+ "WHERE 1=1 and "
			+ "(:idCentro is null or centro.id = :idCentro) and "
			+ "(estadoLote.id IN (:idsEstado)) ")	
	public List<Lote> findByEstado(@Param("idCentro") Integer idCentro, @Param("idsEstado")List<Integer> idsEstado);
	
}
