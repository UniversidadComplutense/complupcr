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

package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 17:36:56 by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PlacaVisavetPlacaLaboratorio generated by hbm2java
 */
@Entity
@Table(name = "placaVisavet_placaLaboratorio")
public class PlacaVisavetPlacaLaboratorio implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4341094805740396522L;
	private Integer id;
	private PlacaLaboratorio placaLaboratorio;
	private PlacaVisavet placaVisavet;

	public PlacaVisavetPlacaLaboratorio() {
	}

	public PlacaVisavetPlacaLaboratorio(PlacaLaboratorio placaLaboratorio, PlacaVisavet placaVisavet) {
		this.placaLaboratorio = placaLaboratorio;
		this.placaVisavet = placaVisavet;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idplacaLaboratorio", nullable = false)
	public PlacaLaboratorio getPlacaLaboratorio() {
		return this.placaLaboratorio;
	}

	public void setPlacaLaboratorio(PlacaLaboratorio placaLaboratorio) {
		this.placaLaboratorio = placaLaboratorio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idplacaVisavet", nullable = false)
	public PlacaVisavet getPlacaVisavet() {
		return this.placaVisavet;
	}

	public void setPlacaVisavet(PlacaVisavet placaVisavet) {
		this.placaVisavet = placaVisavet;
	}

}
