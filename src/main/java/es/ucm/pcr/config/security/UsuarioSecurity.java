package es.ucm.pcr.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import es.ucm.pcr.modelo.orm.Centro;

/**
 * Clase de UsuarioSecurity. Extiende la clase User de Spring Security y nos permite añadirle 
 * los atributos que necesitamos. 
 * 
 * @author pmarrasant
 *
 */
public class UsuarioSecurity extends User {

	private static final long serialVersionUID = -7451641204353137229L;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private Centro centro;

	public UsuarioSecurity(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String nombre, String apellido1, String apellido2, String email) {
		super(username, password, authorities);
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}
}