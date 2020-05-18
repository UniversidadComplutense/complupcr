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

import org.apache.commons.lang.StringUtils;

import es.ucm.pcr.modelo.orm.LaboratorioVisavet;


public class LoteBusquedaBean {

	private Integer id;
	private String numLote;
	private String idLaboratorio;
	private Integer idEstado;
	private Date fechaEnvioIni;
	private Date fechaEnvioFin;
	private Integer idCentro;
	private LaboratorioVisavet laboratorioVisavet;
	
	public LoteBusquedaBean() {
		super();		
	}
	
	public LoteBusquedaBean(String numLote, Integer idCentro) {
		super();
		this.numLote = numLote;
		this.idCentro = idCentro;
	}
	
	public LoteBusquedaBean(Integer idCentro) {
		super();
		this.idCentro = idCentro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}

	public String getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(String idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFechaEnvioIni() {
		return fechaEnvioIni;
	}

	public void setFechaEnvioIni(Date fechaEnvioIni) {
		this.fechaEnvioIni = fechaEnvioIni;
	}

	public Date getFechaEnvioFin() {
		return fechaEnvioFin;
	}

	public void setFechaEnvioFin(Date fechaEnvioFin) {
		this.fechaEnvioFin = fechaEnvioFin;
	}

	public Integer getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}
	
	public String getCriterioNumLote() {
		return StringUtils.isBlank(numLote) ? null : "%" + numLote + "%";
	}

	public LaboratorioVisavet getLaboratorioVisavet() {
		return laboratorioVisavet;
	}

	public void setLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) {
		this.laboratorioVisavet = laboratorioVisavet;
	}

	
}
