package es.ucm.pcr.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public interface LaboratorioVisavetRepositorio extends JpaRepository<LaboratorioVisavet, Integer>{
	
	

	
	/* @Query("SELECT placaVisavet FROM PlacaVisavet placaVisavet "
			+ "JOIN placaVisavet.laboratorioVisavet laboratorioV "
			+ "JOIN PlacaVisavet.estadoPlacaVisavet estadoPlaca "
			+ "JOIN placaVisavet.placaVisavetPlacaLaboratorios placaVisavetPlacaLaboratorios "
			+ "JOIN PlacaVisavet.lotes lotes "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placaVisavet.id = :#{#params.idPlaca})")
			and "
			+ "(:#{#params.criterioNumLote} is null or lotes.numeroLote like :#{#params.criterioNumLote}) and "
			+ "(:#{#params.fechaCreacion} is null or placaVisavet.fechaCreacion = :#{#params.fechaCreacion}) and "
			+ "(:#{#params.idEstado} is null or estadoPlaca.id = :#{#params.codNumEstadoSeleccionado}) ")
	
	
	@Query("SELECT placaVisavet FROM PlacaVisavet placaVisavet JOIN PlacaVisavet.estadoPlacaVisavet estadoPlaca "+
	"where 1=1 and "
	//+ "(:#{#params.codNumEstadoSeleccionado} is null or estadoPlaca.id = :#{#params.codNumEstadoSeleccionado}) and "
			+ "(:#{#params.idPlaca} is null or placaVisavet.id = :#{#params.idPlaca})")
	*/
	
}
