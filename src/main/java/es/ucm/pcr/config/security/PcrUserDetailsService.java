package es.ucm.pcr.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
public class PcrUserDetailsService implements UserDetailsService {

	@Autowired
	UsuarioRepositorio usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario =  usuarioRepository.findByEmail(username);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Autenticación incorrecta.");
		}
		
		return new User(username, usuario.get().getPassword(), getAuthorities(usuario.get()));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Rol rol : usuario.getRols()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+rol.getNombre()));
		}
        return authorities;
    }
}
