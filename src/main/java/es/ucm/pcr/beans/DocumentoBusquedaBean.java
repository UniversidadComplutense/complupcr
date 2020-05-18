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

public class DocumentoBusquedaBean {

	private Integer id;
	private Integer idCentro;
	private Integer idLaboratorioCentro;
	private Integer idLaboratorioVisavet;
	private Integer idMuestra;
	private Integer idPlacaLaboratorio;
	private Integer idPlacaVisavet;
	private String tipo;
	private List<Integer> placasVisavet;

	public DocumentoBusquedaBean() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}

	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}

	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}

	public Integer getIdLaboratorioVisavet() {
		return idLaboratorioVisavet;
	}

	public void setIdLaboratorioVisavet(Integer idLaboratorioVisavet) {
		this.idLaboratorioVisavet = idLaboratorioVisavet;
	}

	public Integer getIdMuestra() {
		return idMuestra;
	}

	public void setIdMuestra(Integer idMuestra) {
		this.idMuestra = idMuestra;
	}

	public Integer getIdPlacaLaboratorio() {
		return idPlacaLaboratorio;
	}

	public void setIdPlacaLaboratorio(Integer idPlacaLaboratorio) {
		this.idPlacaLaboratorio = idPlacaLaboratorio;
	}

	public Integer getIdPlacaVisavet() {
		return idPlacaVisavet;
	}

	public void setIdPlacaVisavet(Integer idPlacaVisavet) {
		this.idPlacaVisavet = idPlacaVisavet;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Integer> getPlacasVisavet() {
		return placasVisavet;
	}

	public void setPlacasVisavet(List<Integer> placasVisavet) {
		this.placasVisavet = placasVisavet;
	}
	
	

}
