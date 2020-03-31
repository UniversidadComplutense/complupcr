package es.ucm.pcr.servicios;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.ucm.pcr.modelo.orm.PasswordResetToken;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.PasswordTokenRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicioImp implements UsuarioServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImp.class);

	@Autowired
	UsuarioRepositorio usurep;
	
	@Autowired
	PasswordTokenRepositorio passwordTokenRepositorio;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		email = email.trim().toLowerCase();
		Optional<Usuario> usuario = usurep.findByEmail(email);
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			return null;
		}
	}

	@Override
	public Set<Rol> getRoles(Usuario usuario) {
		Optional<Usuario> usuWithRoles = usurep.findByEmailWithRoles(usuario.getEmail());
		if (usuWithRoles.isPresent()) {
			return usuWithRoles.get().getRols();
		} else {
			return null;
		}
	}
	
	public void createPasswordResetTokenForUser(Usuario user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(user,token);
	    passwordTokenRepositorio.save(myToken);
	}

	@Override
	public void cambiarContrasena(Usuario user, String contrasena) {
		 user.setPassword(passwordEncoder.encode(contrasena));
		 usurep.save(user);
		 passwordTokenRepositorio.deleteByUsuario(user);
		
	}
	
}
