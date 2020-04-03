package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public interface PlacaVisavetRepositorio extends JpaRepository<PlacaVisavet, Integer> {

	// TODO Terminar query para que busque por idLaboratorioCentro

	@Query("SELECT placa FROM PlacaVisavet placa "
			+ "JOIN placa.laboratorioVisavet LaboratorioVisavet "
			+ "JOIN placa.estadoPlacaVisavet EstadoPlacaVisavet "
			+ "JOIN placa.placaVisavetPlacaLaboratorios PlacaVisavetPlacaLaboratorio "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.fechaCreacionInicio} is null or placa.fechaCreacion >= :#{#params.fechaCreacionInicio}) and "
			+ "(:#{#params.fechaCreacionFin} is null or placa.fechaCreacion <= :#{#params.fechaCreacionFin}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaVisavet.id = :#{#params.idEstadoPlaca})")
	//		+ "(:#{#params.idLaboratorioCentro} is null or placa.placaVisavetPlacaLaboratorios.placaLaboratorio.laboratorioCentro.id = :#{#params.idLaboratorioCentro})")
	public Page<PlacaVisavet> findByParams(@Param("params") BusquedaRecepcionPlacasVisavetBean params,
			Pageable pageable);	
	

	Optional<PlacaVisavet> findById(Integer id);
	
	
}
