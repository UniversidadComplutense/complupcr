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

import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public interface UsuarioMuestraRepositorio extends JpaRepository<UsuarioMuestra, Integer> {
	
	Optional<UsuarioMuestra> findByIdUsuarioMuestra(Integer idUsuarioMuestra);
	
	
	@Query("SELECT usuariomuestra FROM UsuarioMuestra usuariomuestra "
			+ "JOIN usuariomuestra.usuario usuario "
			+ "JOIN usuariomuestra.muestra muestra "
			+ "WHERE usuario.id = :idUsuario and muestra.id = :idMuestra")
	public Optional<UsuarioMuestra> findByIdUsuarioAndIdMuestra(@Param("idUsuario") Integer idUsuario, @Param("idMuestra") Integer idMuestra);			

	
}
