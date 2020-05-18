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

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

public interface LaboratorioVisavetRepositorio extends JpaRepository<LaboratorioVisavet, Integer>{

	Optional<LaboratorioVisavet> findByNombre(String nombre);
	
	

	
	/* @Query("SELECT placaVisavet FROM PlacaVisavet placaVisavet "
			+ "JOIN placaVisavet.laboratorioVisavet laboratorioV "
			+ "JOIN PlacaVisavet.estadoPlacaVisavet estadoPlaca "
			+ "JOIN placaVisavet.placaVisavetPlacaLaboratorios placaVisavetPlacaLaboratorios "
			+ "JOIN PlacaVisavet.lotes lotes "
			+ "WHERE 1=1 and "
			+ "(:#{#params.idPlaca} is null or placaVisavet.id = :#{#params.idPlaca})")
			and "
			+ "(:#{#params.criterioNumLote} is null or lotes.numeroLote like :#{#params.criterioNumLote}) and "
			+ "(:#{#params.fechaCreacion} is null or placaVisavet.fechaCreacion = :#{#params.fechaCreacion}) and "
			+ "(:#{#params.idEstado} is null or estadoPlaca.id = :#{#params.codNumEstadoSeleccionado}) ")
	
	
	@Query("SELECT placaVisavet FROM PlacaVisavet placaVisavet JOIN PlacaVisavet.estadoPlacaVisavet estadoPlaca "+
	"where 1=1 and "
	//+ "(:#{#params.codNumEstadoSeleccionado} is null or estadoPlaca.id = :#{#params.codNumEstadoSeleccionado}) and "
			+ "(:#{#params.idPlaca} is null or placaVisavet.id = :#{#params.idPlaca})")
	*/
	
}
