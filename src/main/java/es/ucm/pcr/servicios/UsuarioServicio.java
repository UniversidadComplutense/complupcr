package es.ucm.pcr.servicios;

import es.ucm.pcr.modelo.orm.Usuario;


public interface UsuarioServicio {

	/**
	 * Método que me devuelve el usuario actual de la sesión
	 * 
	 * @return Usuario
	 */
	public  Usuario getUsuarioActual();
	
	/**
	 * Buscar un usuario por email
	 * 
	 * @param email
	 * @return Usuario
	 */
	public Usuario buscarUsuarioPorEmail(String email);
}
