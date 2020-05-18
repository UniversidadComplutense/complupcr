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

import java.util.Date;
import java.util.List;

/**
 * @author desarrollo
 *
 */
public class BusquedaPlacaLaboratorioBean {

	private Integer idPlaca;
	private String numeroMuestras;
	private Integer idEstadoPlaca;
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;
	private Integer idLaboratorioCentro;
	private List<Integer> estadosBusqueda;

	
	public Integer getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}

	public String getNumeroMuestras() {
		return numeroMuestras;
	}

	public void setNumeroMuestras(String numeroMuestras) {
		this.numeroMuestras = numeroMuestras;
	}

	public Integer getIdEstadoPlaca() {
		return idEstadoPlaca;
	}

	public void setIdEstadoPlaca(Integer idEstadoPlaca) {
		this.idEstadoPlaca = idEstadoPlaca;
	}

	public Date getFechaCreacionInicio() {
		return fechaCreacionInicio;
	}

	public void setFechaCreacionInicio(Date fechaCreacionInicio) {
		this.fechaCreacionInicio = fechaCreacionInicio;
	}

	public Date getFechaCreacionFin() {
		return fechaCreacionFin;
	}

	public void setFechaCreacionFin(Date fechaCreacionFin) {
		this.fechaCreacionFin = fechaCreacionFin;
	}

	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}

	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}

	public List<Integer> getEstadosBusqueda() {
		return estadosBusqueda;
	}

	public void setEstadosBusqueda(List<Integer> estadosBusqueda) {
		this.estadosBusqueda = estadosBusqueda;
	}

}
