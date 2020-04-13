package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Integer>{
	
	public Optional<Rol> findByNombre(String nombre);

}
