package es.ucm.pcr.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.Equipo;

public interface EquipoRepositorio extends JpaRepository<Equipo, Integer> {

}
