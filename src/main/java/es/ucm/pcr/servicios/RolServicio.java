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
import java.util.Optional;

import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;

public interface RolServicio {
	
	/**
	 * Obtiene un rol por nombrre
	 * 
	 * @param nombre
	 * @return Optional<Rol>
	 */
	public Optional<Rol> findByNombre(String nombre);
	
	/**
	 * Elimina un rol por id
	 * 
	 * @param id
	 */
	public void deleteById(Integer id);
	/**
	 * Obtiene un rol por id
	 * 
	 * @param id
	 * @return Optional<Rol>
	 */
	public Optional<Rol> findById(Integer id);
	/**
	 * Obtiene todos los roles de la BD
	 * 
	 * @return List<Rol>
	 */
	public List<Rol> findAll();
	
	/**
	 * Guarda un rol en la BD
	 * 
	 * @param rol
	 * @return Rol
	 */
	public Rol save(Rol rol);
	
	/**
	 * Obtiene la lista de BeanRol de la BD
	 * 
	 * @return List<BeanRol>
	 * @throws Exception
	 */
	public List<BeanRol> generarListaRoles() throws Exception;
	
	/**
	 * Obtiene la lista de BeanRol de un usuario
	 * 
	 * @param usuario
	 * @return List<BeanRol>
	 * @throws Exception
	 */
	public List<BeanRol> generarListaRolesUsuario(Usuario usuario) throws Exception;
}