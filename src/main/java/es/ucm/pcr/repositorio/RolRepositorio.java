package es.ucm.pcr.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Integer>{

}
