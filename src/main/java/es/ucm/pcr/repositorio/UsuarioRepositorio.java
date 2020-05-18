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

package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BeanBusquedaUsuario;
import es.ucm.pcr.modelo.orm.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findById(Integer id);
	
	Optional<Usuario> findByEmail(String email);
	
//	@Query("select u from Usuario u left join fetch u.rols where u.email = :email")
//	Optional<Usuario> findByEmailWithRoles(String email);
	
	List<Usuario> findByHabilitadoOrderById(String habilitado);
	
			
	@Query("SELECT usuario FROM Usuario usuario "
			+ "JOIN usuario.rols rol "				
			+ "WHERE 1=1 and "			
			+ "(usuario.idLaboratorioCentro = :idLaboratorioCentro) and "		
			+ "(rol.id = :idRol)")			
	public List<Usuario> findByIdLaboratorioCentroAndIdRol(@Param("idLaboratorioCentro") Integer idLaboratorioCentro, @Param("idRol") Integer idRol);
	
	
	@Query("SELECT usuario FROM Usuario usuario "
			+ "JOIN usuario.rols rol "				
			+ "WHERE 1=1 and "
			+ "(usuario.idLaboratorioCentro is null) and"
			+ "(rol.id = :idRol)")			
	public List<Usuario> findByIdRolAndNotIdLaboratorioCentro(@Param("idRol") Integer idRol);
	
	@Query("SELECT usuario FROM Usuario usuario "				
			+ "WHERE "			
			+ "(UPPER(usuario.email) like CONCAT('%',UPPER(:#{#busqueda}),'%')) or "		
			+ "(UPPER(usuario.apellido1) like CONCAT('%',UPPER(:#{#busqueda}),'%'))")	
	public List<Usuario> findLikeEmailApellido1 (@Param("busqueda") String busqueda);
	
	@Query("SELECT usuario FROM Usuario usuario "
			+ "WHERE 1=1 and "
			+ "(:#{#params.criterioNombre} is null or usuario.nombre like :#{#params.criterioNombre}) and "
			+ "(:#{#params.criterioApellido1} is null or usuario.apellido1 like :#{#params.criterioApellido1}) and "
			+ "(:#{#params.criterioApellido2} is null or usuario.apellido2 like :#{#params.criterioApellido2}) and "
			+ "(:#{#params.criterioEmail} is null or usuario.email like :#{#params.criterioEmail})")
	public Page<Usuario> findByParams(@Param("params") BeanBusquedaUsuario params,
			Pageable pageable);

}
