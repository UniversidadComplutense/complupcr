package es.ucm.pcr.servicios;

import java.util.List;

import es.ucm.pcr.beans.MenuBean;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Usuario;

/**
 * Servicio de acceso a los datos de sesión del usuario actual
 * 
 * @author desarrollo
 *
 */
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
	
	/**
	 * Obtener menú del usuario actual
	 */
	public List<MenuBean> getMenu();

}
