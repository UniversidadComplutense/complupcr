package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

public interface LaboratorioVisavetRepositorio extends JpaRepository<LaboratorioVisavet, Integer>{

	Optional<LaboratorioVisavet> findByNombre(String nombre);
	
	

	
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
