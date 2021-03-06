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
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanBusquedaUsuario;
import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;

public interface UsuarioServicio {

	/**
	 * Buscar un usuario por email
	 * 
	 * @param email
	 * @return Usuario
	 */
	public Usuario findByEmail(String email);

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
	 * Lista ordenada de  BeanUsuarioGestion
	 * 
	 * @param 
	 * @return List<BeanUsuarioGestion>
	 */
	public Page<BeanUsuarioGestion> listaUsuariosOrdenada(Pageable pageable) throws Exception;
	
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
	public void cambiarContrasena(String email, String contrasena);
	
	/**
	 * Obtener usarios inhabilitados.
	 * 
	 * @return
	 */
	public List<Usuario> buscarUsuarioInhabilitados();
	
	/**
	 * Guarda el usuario
	 * 
	 * @return
	 */
	public Usuario save(Usuario usuario);
	
	

	/**
	 * Borrar Usuario
	 * 
	 * @param Integer idUsuario
	 * @return void 
	 */
	public void deleteById(Integer idUsuario) throws Exception;
	
	/**
	 * Buscar Usuario por Id
	 * 
	 * @param Integer idUsuario
	 * @return void Optional<Usuario>
	 */
	public Optional<Usuario> findById(Integer idUsuario) throws Exception;
	
	
	
	/**
	 * Obtiene usuarios analistas de un laboratorioCentro
	 * 
	 * @param idLaboratorioCentro
	 * @return List<BeanUsuario>
	 */
	public List<BeanUsuario> listaUsuariosAnalistasDeLaboratorioCentro(Integer idLaboratorioCentro);
	
	/**
	 * Obtiene usuarios voluntarios de un laboratorioCentro
	 * 
	 * @param idLaboratorioCentro
	 * @return List<BeanUsuario>
	 */
	public List<BeanUsuario> listaUsuariosVoluntariosDeLaboratorioCentro(Integer idLaboratorioCentro);
	
	
	/**
	 * Obtiene usuarios voluntarios sin asignar a ningun laboratorioCentro
	 *	 
	 * @return List<BeanUsuario>
	 */
	public List<BeanUsuario> listaUsuariosVoluntariosSinLaboratorioCentro();

	/**
	 * Obtiene el centro del usuario
	 * 
	 * @param usuario
	 * @return Optional<Usuario>
	 */
	public Optional<Centro> getCentro(Usuario usuario);

	/**
	 * Obtiene el laboratorio visavet del usuario
	 * 
	 * @param usug1
	 * @return Optional<LaboratorioVisavet>
	 */
	public Optional<LaboratorioVisavet> getLaboratorioVisavet(Usuario usug1); 
	
	/**
	 * Lista ordenada de  BeanUsuarioGestion por criterios de busqueda
	 * 
	 * @param String busqueda: mail o apellido1
	 * @return List<BeanUsuarioGestion>
	 */
	public List<BeanUsuarioGestion> listaUsuariosOrdenadaLikeEmailApellido1(String busqueda) throws Exception;
	
	/**
	 * Lista paginada de usuarios
	 * @param params criterios de busqueda
	 * @param pageable paginacion
	 * @return Page<BeanUsuarioGestion>
	 */
	public Page<BeanUsuarioGestion> findUsuarioByParam(BeanBusquedaUsuario params, Pageable pageable) throws Exception;
	
}
