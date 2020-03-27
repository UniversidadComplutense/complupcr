package es.ucm.pcr.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.PlacaLaboratorioCentro;

public interface LaboratorioCentroRepositorio extends JpaRepository<PlacaLaboratorioCentro, Integer> {

	Optional<PlacaLaboratorioCentro> findById(Integer id);
}
