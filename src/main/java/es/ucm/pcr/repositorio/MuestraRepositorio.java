package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;


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
			+ "(:#{#params.fechaResultadoMuestraFin} is null or muestra.fechaResultado <= :#{#params.fechaResultadoMuestraFin}) and "
			+ "(:#{#params.estadoMuestra} is null or :#{#params.estadoMuestra} ='' or muestra.resultado = :#{#params.estadoMuestra}) and "
			+ "(:#{#params.idEstado} is null or estadoMuestra.id = :#{#params.idEstado}) and "
			+ "(:#{#params.estaNotificada} is null or (:#{#params.estaNotificada} = TRUE and muestra.fechaNotificacion is not null) or (:#{#params.estaNotificada} = FALSE and muestra.fechaNotificacion is null)) ")
	public Page<Muestra> findByParams(@Param("params") MuestraBusquedaBean params,
			Pageable pageable);
	
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
			+ "(:#{#params.etiquetaMuestra} is null or muestra.etiqueta = :#{#params.etiquetaMuestra}) and "
			+ "(:#{#params.criterioRefInterna} is null or muestra.refInternaVisavet like :#{#params.criterioRefInterna}) and "
			+ "(:#{#params.fechaEnvioMuestraIni} is null or muestra.fechaEnvio >= :#{#params.fechaEnvioMuestraIni}) and "
			+ "(:#{#params.fechaEnvioMuestraFin} is null or muestra.fechaEnvio <= :#{#params.fechaEnvioMuestraFin}) and "
			+ "(:#{#params.fechaResultadoMuestraIni} is null or muestra.fechaResultado >= :#{#params.fechaResultadoMuestraIni}) and "
			+ "(:#{#params.fechaResultadoMuestraFin} is null or muestra.fechaResultado <= :#{#params.fechaResultadoMuestraFin}) and "
			+ "(:#{#params.estadoMuestra} is null or :#{#params.estadoMuestra} ='' or muestra.resultado = :#{#params.estadoMuestra}) and "
			+ "(:#{#params.idEstado} is null or estadoMuestra.id = :#{#params.idEstado}) and "
			+ "(:#{#params.estaNotificada} is null or (:#{#params.estaNotificada} = TRUE and muestra.fechaNotificacion is not null) or (:#{#params.estaNotificada} = FALSE and muestra.fechaNotificacion is null)) ")
	public List<Muestra> findByParams(@Param("params") MuestraBusquedaBean params);
	
	
	
	
	
	@Query("SELECT muestra FROM Muestra muestra "
			+ "JOIN muestra.centro centro "
			+ "JOIN muestra.estadoMuestra estadoMuestra "
			+ "JOIN muestra.paciente pacienteMuestra "
			+ "JOIN muestra.lote loteMuestra "	
			+ "JOIN loteMuestra.placaVisavet placaVisavetMuestra "	
			+ "JOIN placaVisavetMuestra.placaVisavetPlacaLaboratorios placaVisavetPlacaLaboratorioMuestra "
			+ "JOIN placaVisavetPlacaLaboratorioMuestra.placaLaboratorio placaLaboratorioMuestra "			
			+ "JOIN placaLaboratorioMuestra.usuario usuarioPlacaLaboratorioMuestra "	
			+ "WHERE 1=1 and "			
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
			+ "(:#{#params.fechaResultadoMuestraFin} is null or muestra.fechaResultado <= :#{#params.fechaResultadoMuestraFin}) and "
			+ "(:#{#params.estadoMuestra} is null or :#{#params.estadoMuestra} ='' or muestra.resultado = :#{#params.estadoMuestra}) and "
			+ "(:#{#params.idEstado} is null or estadoMuestra.id = :#{#params.idEstado}) and "
			+ "(:#{#params.idPlacaLaboratorio} is null or placaLaboratorioMuestra.id = :#{#params.idPlacaLaboratorio}) and "			
			+ "(:#{#params.idJefePlaca} is null or usuarioPlacaLaboratorioMuestra.id = :#{#params.idJefePlaca}) and "			
			+ "(:#{#params.estaNotificada} is null or (:#{#params.estaNotificada} = TRUE and muestra.fechaNotificacion is not null) or (:#{#params.estaNotificada} = FALSE and muestra.fechaNotificacion is null)) ")
	public Page<Muestra> findByParams(@Param("params") BeanBusquedaMuestraAnalisis params,
			Pageable pageable);
	
	
	@Query("SELECT distinct muestra FROM Muestra muestra "
			+ "JOIN muestra.paciente pacienteMuestra "
			+ "LEFT JOIN muestra.lote loteMuestra "	
			+ "LEFT JOIN loteMuestra.placaVisavet placaVisavetMuestra "	
			+ "LEFT JOIN placaVisavetMuestra.placaVisavetPlacaLaboratorios placaVisavetPlacaLaboratorioMuestra "
			+ "LEFT JOIN placaVisavetPlacaLaboratorioMuestra.placaLaboratorio placaLaboratorioMuestra "	
			+ "WHERE 1=1 and "
			+ "(:#{#params.criterioNHC} is null or pacienteMuestra.nhc like :#{#params.criterioNHC}) and "
			+ "(:#{#params.criterioEtiqueta} is null or muestra.etiqueta like :#{#params.criterioEtiqueta}) and "
			+ "(:#{#params.criterioRefInternaMuestra} is null or muestra.refInternaVisavet like :#{#params.criterioRefInternaMuestra}) and "
			+ "(:#{#params.criterioCodNumLote} is null or loteMuestra.numeroLote like :#{#params.criterioCodNumLote}) and "
			+ "(:#{#params.criterioRefInternaLote} is null or loteMuestra.referenciaInternaLote like :#{#params.criterioRefInternaLote}) and "
			+ "(:#{#params.idPlacaVisavet} is null or placaVisavetMuestra.id = :#{#params.idPlacaVisavet}) and "
			+ "(:#{#params.criterioNombrePlacaVisavet} is null or placaVisavetMuestra.nombrePlacaVisavet like :#{#params.criterioNombrePlacaVisavet}) and "
			+ "(:#{#params.idPlacaLaboratorio} is null or placaLaboratorioMuestra.id = :#{#params.idPlacaLaboratorio}) ")
	public Page<Muestra> findByParams(@Param("params") LogMuestraBusquedaBean params,
			Pageable pageable);
	
	
	Optional<Muestra> findById(Integer id);
	
	Optional<Muestra> findByRefInternaVisavet(String refInternaVisavet);
	
}
