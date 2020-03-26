package es.ucm.pcr.servicios;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImp.class);

	@Autowired
	UsuarioRepositorio usurep;

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
}
