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

package es.ucm.pcr.beans;

import java.util.List;

public class LaboratorioCentroBean {

	private String id;
	private String nombre;
	private String universidad;
	private List<PlacaLaboratorioCentroBean> placas;
	private List<EquipoLaboratorioCentroBean> equipos;
	private Integer placasVisavetaLaEspera;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public List<PlacaLaboratorioCentroBean> getPlacas() {
		return placas;
	}

	public void setPlacas(List<PlacaLaboratorioCentroBean> placas) {
		this.placas = placas;
	}

	public List<EquipoLaboratorioCentroBean> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<EquipoLaboratorioCentroBean> equipos) {
		this.equipos = equipos;
	}
	public Integer getPlacasVisavetaLaEspera() {
		return placasVisavetaLaEspera;
	}


	public void setPlacasVisavetaLaEspera(Integer placasVisavetaLaEspera) {
		this.placasVisavetaLaEspera = placasVisavetaLaEspera;
	}

}
