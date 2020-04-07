package es.ucm.pcr.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.modelo.orm.LogMuestras;


@Repository
public interface LogMuestrasRepositorio extends JpaRepository<LogMuestras, Integer> {

	
	
}
