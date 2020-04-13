package es.ucm.pcr.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.EstadoLote;

public interface EstadoLoteRepositorio extends JpaRepository<EstadoLote, Integer> {

}
