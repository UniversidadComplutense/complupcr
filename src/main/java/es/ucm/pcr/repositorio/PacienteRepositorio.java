package es.ucm.pcr.repositorio;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.ucm.pcr.modelo.orm.Paciente;


@Repository
public interface PacienteRepositorio extends PagingAndSortingRepository<Paciente, Integer> {
	
	
}
