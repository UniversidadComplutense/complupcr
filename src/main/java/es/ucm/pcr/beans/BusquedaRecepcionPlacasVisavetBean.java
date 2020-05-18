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

public class BusquedaRecepcionPlacasVisavetBean {

	private Integer idPlaca;
	private Integer numeroMuestras;
	private Integer idEstadoPlaca;
	private Date fechaBusquedaInicio;
	private Date fechaBusquedaFin;
	private Date fechaEnviadaInicio;
	private Date fechaEnviadaFin;
	private Date fechaRecepcionInicio;
	private Date fechaRecepcionFin;
	private Integer idLaboratorioCentro;
	private List<Integer> estadosBusqueda;

	public Integer getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}

	public Integer getNumeroMuestras() {
		return numeroMuestras;
	}

	public void setNumeroMuestras(Integer numeroMuestras) {
		this.numeroMuestras = numeroMuestras;
	}

	public Integer getIdEstadoPlaca() {
		return idEstadoPlaca;
	}

	public void setIdEstadoPlaca(Integer idEstadoPlaca) {
		this.idEstadoPlaca = idEstadoPlaca;
	}

	public Date getFechaBusquedaInicio() {
		return fechaBusquedaInicio;
	}

	public void setFechaBusquedaInicio(Date fechaBusquedaInicio) {
		this.fechaBusquedaInicio = fechaBusquedaInicio;
	}

	public Date getFechaBusquedaFin() {
		return fechaBusquedaFin;
	}

	public void setFechaBusquedaFin(Date fechaBusquedaFin) {
		this.fechaBusquedaFin = fechaBusquedaFin;
	}

	public Date getFechaEnviadaInicio() {
		return fechaEnviadaInicio;
	}

	public void setFechaEnviadaInicio(Date fechaEnviadaInicio) {
		this.fechaEnviadaInicio = fechaEnviadaInicio;
	}

	public Date getFechaEnviadaFin() {
		return fechaEnviadaFin;
	}

	public void setFechaEnviadaFin(Date fechaEnviadaFin) {
		this.fechaEnviadaFin = fechaEnviadaFin;
	}

	public Date getFechaRecepcionInicio() {
		return fechaRecepcionInicio;
	}

	public void setFechaRecepcionInicio(Date fechaRecepcionInicio) {
		this.fechaRecepcionInicio = fechaRecepcionInicio;
	}

	public Date getFechaRecepcionFin() {
		return fechaRecepcionFin;
	}

	public void setFechaRecepcionFin(Date fechaRecepcionFin) {
		this.fechaRecepcionFin = fechaRecepcionFin;
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
