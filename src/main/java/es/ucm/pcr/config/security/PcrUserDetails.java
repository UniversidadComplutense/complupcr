package es.ucm.pcr.config.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Usuario;

public class PcrUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3134992594168099074L;
	private UsuarioSecurity user;

	public PcrUserDetails(Usuario user, Set<GrantedAuthority> grantedAuthorities, Centro centro) {
		
		this.user = new UsuarioSecurity(user.getEmail(), user.getPassword(), grantedAuthorities, user.getNombre(),
				user.getApellido1(), user.getApellido2(), user.getEmail());
		this.user.setCentro(centro);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.user.getAuthorities();
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.user.isEnabled();
	}
	
	public String getNombre() {
		return this.user.getNombre();
	}

	public String getApellido1() {
		return this.user.getApellido1();
	}

	public String getApellido2() {
		return this.user.getApellido2();
	}

	public String getEmail() {
		return this.user.getEmail();
	}
	
	public Centro getCentro() {
		return this.user.getCentro();
	}

	public UsuarioSecurity getUser() {
		return user;
	}

}
