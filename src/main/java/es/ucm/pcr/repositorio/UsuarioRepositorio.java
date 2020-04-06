package es.ucm.pcr.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
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
	
}
