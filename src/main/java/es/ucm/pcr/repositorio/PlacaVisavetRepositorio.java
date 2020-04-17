package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public interface PlacaVisavetRepositorio extends JpaRepository<PlacaVisavet, Integer> {


	@Query("SELECT placa FROM PlacaVisavet placa "
			+ "JOIN placa.laboratorioVisavet LaboratorioVisavet "
			+ "JOIN placa.estadoPlacaVisavet EstadoPlacaVisavet "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.numeroMuestras} is null or placa.numeromuestras = :#{#params.numeroMuestras}) and "
			+ "(:#{#params.fechaAsignadaInicio} is null or placa.fechaAsignadaLaboratorioCentro >= :#{#params.fechaAsignadaInicio}) and "
			+ "(:#{#params.fechaAsignadaFin} is null or placa.fechaAsignadaLaboratorioCentro <= :#{#params.fechaAsignadaInicio}) and "
			+ "(:#{#params.fechaEnviadaInicio} is null or placa.fechaEnviadaLaboratorioCentro >= :#{#params.fechaEnviadaInicio}) and "
			+ "(:#{#params.fechaEnviadaFin} is null or placa.fechaEnviadaLaboratorioCentro <= :#{#params.fechaEnviadaFin}) and "
			+ "(:#{#params.fechaRecepcionInicio} is null or placa.fechaRecepcionLaboratorioCentro >= :#{#params.fechaRecepcionInicio}) and "
			+ "(:#{#params.fechaRecepcionFin} is null or placa.fechaRecepcionLaboratorioCentro <= :#{#params.fechaRecepcionFin}) and "
			+ "(:#{#params.idEstadoPlaca} is null or placa.estadoPlacaVisavet.id in (:#{#params.estadosBusqueda})) and " 
			+ "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro})")
	public Page<PlacaVisavet> findByParams(@Param("params") BusquedaRecepcionPlacasVisavetBean params,
			Pageable pageable);
	
	
	@Query("SELECT placa FROM PlacaVisavet placa, PlacaVisavetPlacaLaboratorio pvisplab  "
			+ "WHERE placa = pvisplab.placaVisavet and pvisplab.placaLaboratorio.id = :idPlacaLaboratorio ")
	public Set<PlacaVisavet> findByIdPlacaLaboratorio(@Param("idPlacaLaboratorio") Integer idPlacaLaboratorio);	
		
	

	//Optional<PlacaVisavet> findById(Integer id);	
	
	public void save(Optional<PlacaVisavet> placa);
	
	// yoli esta query no da resultados probar LEFT JOIN placa.placaVisavetPlacaLaboratorios placaVLaboratorio "
	@Query("SELECT distinct placa FROM PlacaVisavet placa "
			+ "JOIN placa.laboratorioVisavet laboratorioVisavet "
			+ "JOIN placa.estadoPlacaVisavet estadoPlacaVisavet "
			//+ "LEFT JOIN placa.lotes lotes "
			//+ "LEFT JOIN placa.muestras muestras "
		//	+ "JOIN placa.laboratorioCentro laboratorioCentro "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca}) and "
			+ "(:#{#params.codNumEstadoSeleccionado} is null or placa.estadoPlacaVisavet.id = :#{#params.codNumEstadoSeleccionado}) and "
			+ "(:#{#params.fechaCreacionInicio} is null or placa.fechaCreacion >= :#{#params.fechaCreacionInicio}) and "
			+ "(:#{#params.fechaCreacionFin} is null or placa.fechaCreacion <= :#{#params.fechaCreacionFin}) and "
			//+ "(:#{#params.numLote} is null or lotes.numeroLote = :#{#params.numLote}) and "
			//+ "(:#{#params.muestra} is null or muestras.etiqueta = :#{#params.muestra}) and "
		    + "(:#{#params.idLaboratorioCentro} is null or placa.laboratorioCentro.id = :#{#params.idLaboratorioCentro}) and "
			+ "(:#{#params.idLaboratorioVisavet} is null or placa.laboratorioVisavet.id = :#{#params.idLaboratorioVisavet})")
       public Page<PlacaVisavet> findByParams(@Param("params") BusquedaPlacasVisavetBean params,
			Pageable pageable);	
	
	@Query("SELECT placa FROM PlacaVisavet placa "
			//+ "JOIN placa.laboratorioVisavet laboratorioVisavet "
			//+ "JOIN placa.estadoPlacaVisavet estadoPlacaVisavet "
			// + "WHERE 1=1 and "
			+" WHERE "
			+ "(:#{#params.idPlaca} is null or placa.id = :#{#params.idPlaca})  ")
	// + "(:#{#params.codNumEstadoSeleccionado} is null or placa.estadoPlacaVisavet.id = :#{#params.codNumEstadoSeleccionado}) and  "
			//+ "(:#{#params.fechaCreacionInicio} is null or placa.fechaCreacion >= :#{#params.fechaCreacionInicio}) and "
		//	+ "(:#{#params.fechaCreacionFin} is null or placa.fechaCreacion <= :#{#params.fechaCreacionFin}) and "
		//	+ "(:#{#params.numLote} is null or lotes.numeroLote = :#{#params.numLote}) and "
		//	+ "(:#{#params.muestra} is null or muestras.etiqueta = :#{#params.muestra}) and "
			// + "(:#{#params.idLaboratorioCentro} is null or placaVLaboratorio.placaLaboratorio.laboratorioCentro.id = :#{#params.idLaboratorioCentro}) and "
		//	+ "(:#{#params.idLaboratorioVisavet} is null or placa.laboratorioVisavet.id = :#{#params.idLaboratorioVisavet})")
	public List<PlacaVisavet> findByParams(@Param("params") BusquedaPlacasVisavetBean params);

	
	
}
