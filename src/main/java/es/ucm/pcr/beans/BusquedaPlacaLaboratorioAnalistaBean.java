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

import java.sql.Date;

public class BusquedaPlacaLaboratorioAnalistaBean {

	private Integer idPlaca;
	private Integer numeroMuestras;
	private Integer idEstadoPlaca;
	private Integer idEstadoMuestras; //estado de las muestras de la placa
	private String valoracion; //valoracion de la muestra por parte del analista
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;
	private Integer idAnalistaMuestras; //analista que tiene asignadas las muestras de la placa (para resolver la placa subiendo el excel de las muestras)
	private Integer idLaboratorioCentro; //laboratorioCentro en el que está la placa
	
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
	public Integer getIdEstadoMuestras() {
		return idEstadoMuestras;
	}
	public void setIdEstadoMuestras(Integer idEstadoMuestras) {
		this.idEstadoMuestras = idEstadoMuestras;
	}	
	public String getValoracion() {
		return valoracion;
	}
	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
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
	public Integer getIdAnalistaMuestras() {
		return idAnalistaMuestras;
	}
	public void setIdAnalistaMuestras(Integer idAnalistaMuestras) {
		this.idAnalistaMuestras = idAnalistaMuestras;
	}
	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}
	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}
	

	
	

}
