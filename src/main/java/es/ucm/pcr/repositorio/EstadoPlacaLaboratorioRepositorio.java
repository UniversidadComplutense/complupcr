package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;

public interface EstadoPlacaLaboratorioRepositorio extends JpaRepository<EstadoPlacaLaboratorio, Integer> {
	

	Optional<EstadoPlacaLaboratorio> findById(Integer id);
	
	
}
