package es.ucm.pcr.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import es.ucm.pcr.modelo.orm.Usuario;

/**
 * Clase de UsuarioSecurity. Extiende la clase User de Spring Security y nos permite añadirle 
 * los atributos que necesitamos. 
 * 
 * @author pmarrasant
 *
 */
public class UsuarioSecurity extends User {

	private static final long serialVersionUID = -7451641204353137229L;
	private Usuario usuario;

	public UsuarioSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Usuario usuario) {
		super(username, password, authorities);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}