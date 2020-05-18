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
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;

public class BeanLaboratorioCentro implements Comparable <BeanLaboratorioCentro>{

	private Integer id;
	private String nombre;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<PlacaLaboratorio> placaLaboratorios = new HashSet<PlacaLaboratorio>(0);
	//private Set<PlacaLaboratorio> placaLaboratorios = new HashSet<PlacaLaboratorio>(0);
	private Set<Equipo> equipos = new HashSet<Equipo>(0);
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	private Integer placasVisavetaLaEspera;
	
	
	public BeanLaboratorioCentro() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BeanLaboratorioCentro(Integer id, String nombre, Set<Documento> documentos,
			Set<PlacaLaboratorio> placaLaboratorios, Set<Equipo> equipos, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.documentos = documentos;
		this.placaLaboratorios = placaLaboratorios;
		this.equipos = equipos;
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


	public Set<PlacaLaboratorio> getPlacaLaboratorios() {
		return placaLaboratorios;
	}


	public void setPlacaLaboratorios(Set<PlacaLaboratorio> placaLaboratorios) {
		this.placaLaboratorios = placaLaboratorios;
	}


	public Set<Equipo> getEquipos() {
		return equipos;
	}


	public void setEquipos(Set<Equipo> equipos) {
		this.equipos = equipos;
	}

	

	public Integer getPlacasVisavetaLaEspera() {
		return placasVisavetaLaEspera;
	}


	public void setPlacasVisavetaLaEspera(Integer placasVisavetaLaEspera) {
		this.placasVisavetaLaEspera = placasVisavetaLaEspera;
	}


	@Override
	public String toString() {
		return "BeanLaboratorioCentro [id=" + id + ", nombre=" + nombre + ", documentos=" + documentos
				+ ", placaLaboratorios=" + placaLaboratorios + ", equipos=" + equipos + ", accion=" + accion + "]";
	}


	@Override
    public int compareTo(BeanLaboratorioCentro o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }	
}
