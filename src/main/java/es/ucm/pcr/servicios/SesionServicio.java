package es.ucm.pcr.servicios;

import java.util.List;

import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Usuario;

public interface SesionServicio {
	
	/**
	 * Método que devuelve el usuario actual de la sesión
	 * 
	 * @return Usuario
	 */
	public  Usuario getUsuario();
	
	/**
	 * Método que devuelve el email del usuario actual
	 * @return
	 */
	public String getEmail();
	
	/**
	 * Obtener los roles que tiene asignado el usuario actual
	 * @return
	 */
	public List<String> getRoles();
	
	/**
	 * Obtener Centro del usuario actual
	 */
	public Centro getCentro();

}