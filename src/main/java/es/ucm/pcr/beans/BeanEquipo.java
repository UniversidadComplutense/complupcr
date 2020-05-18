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

import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.utilidades.Utilidades;

public class BeanEquipo implements Comparable<BeanEquipo> {
	
	private Integer id;
	private LaboratorioCentro laboratorioCentro;
	private String nombre;
	private Integer  capacidad;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	private Integer labUcmSeleccionado;
	


	public BeanEquipo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public BeanEquipo(Integer id, LaboratorioCentro laboratorioCentro, String nombre, Integer capacidad, String accion,
			Integer labUcmSeleccionado) {
		super();
		this.id = id;
		this.laboratorioCentro = laboratorioCentro;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.accion = accion;
		this.labUcmSeleccionado = labUcmSeleccionado;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public LaboratorioCentro getLaboratorioCentro() {
		return laboratorioCentro;
	}



	public void setLaboratorioCentro(LaboratorioCentro laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
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



	public String getAccion() {
		return accion;
	}



	public void setAccion(String accion) {
		this.accion = accion;
	}



	public Integer getLabUcmSeleccionado() {
		return labUcmSeleccionado;
	}



	public void setLabUcmSeleccionado(Integer labUcmSeleccionado) {
		this.labUcmSeleccionado = labUcmSeleccionado;
	}



	@Override
	public String toString() {
		return "BeanEquipo [id=" + id + ", laboratorioCentro=" + laboratorioCentro + ", nombre=" + nombre
				+ ", capacidad=" + capacidad + ", accion=" + accion + ", labUcmSeleccionado=" + labUcmSeleccionado
				+ "]";
	}



	@Override
    public int compareTo(BeanEquipo o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }		

	
}
