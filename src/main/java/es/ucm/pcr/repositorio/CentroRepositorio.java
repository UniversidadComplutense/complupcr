package es.ucm.pcr.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.Centro;

public interface CentroRepositorio extends JpaRepository<Centro, Integer>{

}
