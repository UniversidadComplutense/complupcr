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

import java.util.HashSet;
import java.util.Set;

import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.modelo.orm.Documento;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public class BeanLaboratorioVisavet implements Comparable <BeanLaboratorioVisavet>{

	private Integer id;
	private String nombre;
	private Integer capacidad;
	private Integer ocupacion;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<PlacaVisavet> placaVisavets = new HashSet<PlacaVisavet>(0);
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanLaboratorioVisavet() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public BeanLaboratorioVisavet(Integer id, String nombre, Integer capacidad, Integer ocupacion,
			Set<Documento> documentos, Set<PlacaVisavet> placaVisavets, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.ocupacion = ocupacion;
		this.documentos = documentos;
		this.placaVisavets = placaVisavets;
		this.accion = accion;
	}




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Integer ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	
	
	public Set<Documento> getDocumentos() {
		return documentos;
	}


	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}


	public Set<PlacaVisavet> getPlacaVisavets() {
		return placaVisavets;
	}


	public void setPlacaVisavets(Set<PlacaVisavet> placaVisavets) {
		this.placaVisavets = placaVisavets;
	}

	

	@Override
	public String toString() {
		return "BeanLaboratorioVisavet [id=" + id + ", nombre=" + nombre + ", capacidad=" + capacidad + ", ocupacion="
				+ ocupacion + ", documentos=" + documentos + ", placaVisavets=" + placaVisavets + ", accion=" + accion
				+ "]";
	}


	@Override
    public int compareTo(BeanLaboratorioVisavet o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }	
	
}
