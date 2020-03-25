package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findByEmail(String email);
}
