package es.ucm.pcr.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;

public interface EquipoRepositorio extends JpaRepository<Equipo, Integer> {
	
	List<Equipo> findByLaboratorioCentro (LaboratorioCentro laboratorioCentro);

	List<Equipo> findByLaboratorioCentro(LaboratorioCentro laboratorioCentro);

}
