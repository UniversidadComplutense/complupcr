package es.ucm.pcr.servicios;

import java.util.List;

import es.ucm.pcr.beans.MenuBean;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Usuario;

/**
 * Servicio de acceso a los datos de sesión del usuario actual
 * 
 * @author pmarrasant
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
	 * 
	 * @return String
	 */
	public String getEmail();
	
	/**
	 * Obtener los roles que tiene asignado el usuario actual
	 * 
	 * @return List<String>
	 */
	public List<String> getRoles();
	
	/**
	 * Comprueba si el usuario tiene un rol determinado

	 * @param rol
	 * @return Boolean
	 */
	public Boolean tieneRol(String rol);
	/**
	 * Obtener Centro del usuario actual
	 * 
	 * @return Centro or null
	 */
	public Centro getCentro();
	
	/**
	 * Obtener el laboratorio Visavet del usuario actual
	 * 
	 * @return LaboratorioVisavet or null
	 */
	public LaboratorioVisavet getLaboratorioVisavet();
	
	/**
	 * Obtener el laboratorioCentro del usuario actual
	 * 
	 * @return LaboratorioCentro or null
	 */
	public LaboratorioCentro getLaboratorioCentro();
	
	/**
	 * Obtener menú del usuario actual
	 * 
	 * @return List<MenuBean>
	 */
	public List<MenuBean> getMenu();
}