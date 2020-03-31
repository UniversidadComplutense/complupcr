package es.ucm.pcr.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.modelo.orm.Muestra;


@Repository
public interface MuestraRepositorio extends PagingAndSortingRepository<Muestra, Integer> {

	
	@Query("SELECT muestra FROM Muestra muestra "
			+ "JOIN muestra.centro centro "
			+ "JOIN muestra.estadoMuestra estadoMuestra "
			+ "JOIN muestra.paciente pacienteMuestra "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idCentro} is null or centro.id = :#{#params.idCentro}) and "
			+ "(:#{#params.criterioNombre} is null or pacienteMuestra.nombrePaciente like :#{#params.criterioNombre}) and "
			+ "(:#{#params.criterioPrimerApellido} is null or pacienteMuestra.apellido1paciente like :#{#params.criterioPrimerApellido}) and "
			+ "(:#{#params.criterioSegundoApellido} is null or pacienteMuestra.apellido2paciente like :#{#params.criterioSegundoApellido}) and "
			+ "(:#{#params.criterioNHC} is null or pacienteMuestra.nhc like :#{#params.criterioNHC}) and "
			+ "(:#{#params.criterioCorreo} is null or pacienteMuestra.email like :#{#params.criterioCorreo}) and "
			+ "(:#{#params.criterioEtiqueta} is null or muestra.etiqueta like :#{#params.criterioEtiqueta}) and "
			+ "(:#{#params.criterioRefInterna} is null or muestra.refInternaVisavet like :#{#params.criterioRefInterna}) and "
			+ "(:#{#params.fechaEnvioMuestraIni} is null or muestra.fechaEnvio >= :#{#params.fechaEnvioMuestraIni}) and "
			+ "(:#{#params.fechaEnvioMuestraFin} is null or muestra.fechaEnvio <= :#{#params.fechaEnvioMuestraFin}) and "
			+ "(:#{#params.fechaResultadoMuestraIni} is null or muestra.fechaResultado >= :#{#params.fechaResultadoMuestraIni}) and "
			+ "(:#{#params.fechaResultadoMuestraFin} is null or muestra.fechaResultado <= :#{#params.fechaResultadoMuestraFin}) ")
	public Page<Muestra> findByParams(@Param("params") MuestraBusquedaBean params,
			Pageable pageable);	
	
}