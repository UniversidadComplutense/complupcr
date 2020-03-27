package es.ucm.pcr.servicios;

import java.util.Set;

import es.ucm.pcr.beans.BeanUsuario;
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
	
//	/**
//	 * Realiza el mapeo de la entidad al bean Usuario
//	 * 
//	 * @param Usuario
//	 * @return BeanUsuario
//	 */	
//	public BeanUsuario mapeoEntidadBeanUsuario (Usuario usuario) throws Exception;
//	
//	/**
//	 * Realiza el mapeo del bean a la entidad Usuario
//	 * 
//	 * @param BeanUsuario
//	 * @return Usuario
//	 */	
//	public Usuario mapeoEntidadBeanUsuario (BeanUsuario beanUsuario) throws Exception;
}
