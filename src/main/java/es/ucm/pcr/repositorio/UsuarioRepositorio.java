package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.ucm.pcr.modelo.orm.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findById(Integer id);
	
	Optional<Usuario> findByEmail(String email);
	
	@Query("select u from Usuario u left join fetch u.rols where u.email = :email")
	Optional<Usuario> findByEmailWithRoles(String email);
	
	List<Usuario> findByHabilitadoOrderById(String habilitado);
}
