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

public class BeanLaboratorio {

	private Integer idLaboratorio;
	private String codLaboratorio;
	private String desLaboratorio;
	private String telefonoLaboratorio;
	private String responsableLaboratorio;
	private String telefonoResponsableLaboratorio;
	
	public BeanLaboratorio() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(Integer idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public String getCodLaboratorio() {
		return codLaboratorio;
	}

	public void setCodLaboratorio(String codLaboratorio) {
		this.codLaboratorio = codLaboratorio;
	}

	public String getDesLaboratorio() {
		return desLaboratorio;
	}

	public void setDesLaboratorio(String desLaboratorio) {
		this.desLaboratorio = desLaboratorio;
	}

	public String getTelefonoLaboratorio() {
		return telefonoLaboratorio;
	}

	public void setTelefonoLaboratorio(String telefonoLaboratorio) {
		this.telefonoLaboratorio = telefonoLaboratorio;
	}

	public String getResponsableLaboratorio() {
		return responsableLaboratorio;
	}

	public void setResponsableLaboratorio(String responsableLaboratorio) {
		this.responsableLaboratorio = responsableLaboratorio;
	}

	public String getTelefonoResponsableLaboratorio() {
		return telefonoResponsableLaboratorio;
	}

	public void setTelefonoResponsableLaboratorio(String telefonoResponsableLaboratorio) {
		this.telefonoResponsableLaboratorio = telefonoResponsableLaboratorio;
	}

	@Override
	public String toString() {
		return "BeanLaboratorio [idLaboratorio=" + idLaboratorio + ", codLaboratorio=" + codLaboratorio
				+ ", desLaboratorio=" + desLaboratorio + ", telefonoLaboratorio=" + telefonoLaboratorio
				+ ", responsableLaboratorio=" + responsableLaboratorio + ", telefonoResponsableLaboratorio="
				+ telefonoResponsableLaboratorio + "]";
	}
	
	
	
}
