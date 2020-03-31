package es.ucm.pcr.servicios;

import java.util.Set;

import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;

public interface UsuarioServicio {

	/**
	 * Buscar un usuario por email
	 * 
	 * @param email
	 * @return Usuario
	 */
	public Usuario buscarUsuarioPorEmail(String email);

	/**
	 * Obtener los roles que tiene asignado un usuario
	 * 
	 * @param Usuario
	 * @return Set<Rol>
	 */
	public Set<Rol> getRoles(Usuario usuario);
	
	/**
	 * Crea un token unico para que el usuario restablezca la contraseña.
	 * 
	 * @param user
	 * @param token
	 */
	public void createPasswordResetTokenForUser(Usuario user, String token);
	
	/**
	 * Modifica la contraseña del usuario.
	 * 
	 * @param user
	 * @param contrasena
	 */
	public void cabiarContrasena(Usuario user, String contrasena);
}
