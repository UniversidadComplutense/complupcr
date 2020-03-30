package es.ucm.pcr.repositorio;

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
			+ "WHERE 1=1 and "
			+ "(:#{#params.idCentro} is null or centro.id = :#{#params.idCentro}) and "
			+ "(:#{#params.criterioNumLote} is null or lote.numeroLote like :#{#params.criterioNumLote})")
	public Page<Lote> findByParams(@Param("params") LoteBusquedaBean params,
			Pageable pageable);	
	
}
