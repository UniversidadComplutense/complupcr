package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public interface UsuarioMuestraRepositorio extends JpaRepository<UsuarioMuestra, Integer> {
	
	Optional<UsuarioMuestra> findByIdUsuarioMuestra(Integer idUsuarioMuestra);
	
	
	
}
