package es.ucm.pcr.servicio;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicioImp implements UsuarioServicio {

	private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImp.class);

	@Autowired
	UsuarioRepositorio usurep;

	@Override
	public Usuario getUsuarioActual() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return this.buscarUsuarioPorEmail(email);
	}

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
}
