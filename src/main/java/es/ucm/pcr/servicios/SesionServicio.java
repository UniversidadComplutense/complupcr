/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

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