package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;

public interface PlacaLaboratorioRepositorio extends JpaRepository<PlacaLaboratorio, Integer> {



	@Query("SELECT placa FROM PlacaLaboratorio placa, LaboratorioCentro laboratorioCentro, EstadoPlacaLaboratorio estadoPlacaLaboratorio "
			+ "JOIN placa.laboratorioCentro laboratorioCentro "
			+ "JOIN placa.estadoPlacaLaboratorio estadoPlacaLaboratorio "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.fechaCreacionInicio} is null or placa.fechaCreacion >= :#{#params.fechaCreacionInicio}) and "
			+ "(:#{#params.fechaCreacionFin} is null or placa.fechaCreacion <= :#{#params.fechaCreacionFin}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaLaboratorio.id = :#{#params.idEstadoPlaca}) and "
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro})")
	public Page<PlacaLaboratorio> findByParams(@Param("params") BusquedaPlacaLaboratorioBean params,
			Pageable pageable);	
	
	
	
	
	@Query("SELECT placa FROM PlacaLaboratorio placa "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaLaboratorio.id = :#{#params.idEstadoPlaca}) and "
			+ "(:#{#params.idJefe} is null or placa.usuario.id = :#{#params.idJefe}) and "
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro})")
	public Page<PlacaLaboratorio> findByParams(@Param("params") BusquedaPlacaLaboratorioJefeBean params,
			Pageable pageable);
	
	
	

	Optional<PlacaLaboratorio> findById(Integer id);
	
	
}
