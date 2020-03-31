package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.PasswordResetToken;
import es.ucm.pcr.modelo.orm.Usuario;

public interface PasswordTokenRepositorio extends JpaRepository<PasswordResetToken, Integer> {
	
	Optional<PasswordResetToken> findByToken(String token);
	
	void deleteByUsuario(Usuario usuario);
}
