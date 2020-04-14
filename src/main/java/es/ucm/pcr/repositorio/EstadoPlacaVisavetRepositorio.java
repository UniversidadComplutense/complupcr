package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;

public interface EstadoPlacaVisavetRepositorio extends JpaRepository<EstadoPlacaVisavet, Integer> {
	

	Optional<EstadoPlacaVisavet> findById(Integer id);
	
	
}
