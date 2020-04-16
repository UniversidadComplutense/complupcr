package es.ucm.pcr.beans;

import org.apache.commons.lang.StringUtils;

public class BeanBusquedaUsuario {

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;

	public BeanBusquedaUsuario() {
		super();
		// TODO Auto-generated constructor stub
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
	
	public String getCriterioNombre() {
		return StringUtils.isBlank(nombre) ? null : "%" + nombre + "%";
	}

	public String getCriterioApellido1() {
		return StringUtils.isBlank(apellido1) ? null : "%" + apellido1 + "%";
	}
	
	public String getCriterioApellido2() {
		return StringUtils.isBlank(apellido2) ? null : "%" + apellido2 + "%";
	}
	
	public String getCriterioEmail() {
		return StringUtils.isBlank(email) ? null : "%" + email + "%";
	}
}
