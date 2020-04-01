package es.ucm.pcr.servicios;

import java.util.Set;

import es.ucm.pcr.beans.BeanUsuarioGestion;
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
	 * Realiza el mapeo de la entidad al bean Usuario
	 * 
	 * @param Usuario
	 * @return BeanUsuario
	 */	
	public BeanUsuarioGestion mapeoEntidadBeanUsuario (Usuario usuario) throws Exception;
	
	/**
	 * Realiza el mapeo del bean a la entidad Usuario
	 * 
	 * @param BeanUsuarioGestion
	 * @return Usuario
	 */	
	public Usuario mapeoBeanEntidadUsuarioAlta (BeanUsuarioGestion beanUsuario, int[] roles) throws Exception;
	
	/**
	 * Realiza el mapeo del bean a la entidad Usuario para modificar
	 * 
	 * @param BeanUsuario, Usuario
	 * @return Usuario
	 */	
	public Usuario mapeoBeanEntidadUsuarioModificar (BeanUsuarioGestion beanUsuario, Usuario usuario, int[] roles) throws Exception;
	
	
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
	public void cambiarContrasena(Usuario user, String contrasena);
}
