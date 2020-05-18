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

import es.ucm.pcr.utilidades.Utilidades;

public class BeanRol implements Comparable<BeanRol>{
	
	private Integer id;
	private String nombre;
	private boolean seleccionado;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanRol() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanRol(Integer id, String nombre, boolean seleccionado, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.seleccionado = seleccionado;
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



	public boolean isSeleccionado() {
		return seleccionado;
	}


	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}


	@Override
	public String toString() {
		return "BeanRol [id=" + id + ", nombre=" + nombre + ", seleccionado=" + seleccionado + ", accion=" + accion
				+ "]";
	}



	@Override
    public int compareTo(BeanRol o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }		
}
