package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;

public interface LaboratorioCentroRepositorio extends JpaRepository<PlacaLaboratorio, Integer> {


	@Query("SELECT placa FROM PlacaLaboratorio placa "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras})")
	public Page<PlacaLaboratorio> findByParams(@Param("params") BusquedaPlacaLaboratorioBean params,
			Pageable pageable);	
	

	Optional<PlacaLaboratorio> findById(Integer id);
	
	
}
