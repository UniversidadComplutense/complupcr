package es.ucm.pcr.config.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.ucm.pcr.modelo.orm.PasswordResetToken;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.PasswordTokenRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

/**
 * Servicio PcrUserDetailsService. Nos permite interceptar el login de Spring Security 
 * para inicializar nuestra PcrUserDetails.
 * 
 * @author pmarrasant
 *
 */
@Service
public class PcrUserDetailsService implements UserDetailsService {

	@Autowired
	UsuarioRepositorio usuarioRepository;

	@Autowired
	private PasswordTokenRepositorio passwordTokenRepositorio;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Autenticación incorrecta.");
		}
		Usuario miUser = usuario.get();

		return new User(miUser.getEmail(), miUser.getPassword(), getAuthorities(miUser));

	}

	private static Set<GrantedAuthority> getAuthorities(Usuario usuario) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Rol rol : usuario.getRols()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
		}
		return authorities;
	}

	public String validarPasswordResetToken(long id, String token) {
		final Optional<PasswordResetToken> passToken = passwordTokenRepositorio.findByToken(token);
		if ((!passToken.isPresent()) || (passToken.get().getUsuario().getId() != id)) {
			return "invalidToken";
		}

		final Usuario usuario = passToken.get().getUsuario();
		final User user = new User(usuario.getEmail(), usuario.getPassword(), getAuthorities(usuario));
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
				Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}
}