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

import java.util.ArrayList;
import java.util.List;


public class GuardarCogerYDevolverPlacasBean {

	private String fecAsignacion;
	private String fecDevolucion;
	private List<Integer> listaIdsPlacasSeleccionadosParaCoger;
	private List<Integer> listaIdsPlacasSeleccionadosParaDevolver;	

	public GuardarCogerYDevolverPlacasBean() {
		super();
		listaIdsPlacasSeleccionadosParaCoger = new ArrayList<Integer>();
		listaIdsPlacasSeleccionadosParaDevolver = new ArrayList<Integer>();

	}	

	public String getFecAsignacion() {
		return fecAsignacion;
	}

	public void setFecAsignacion(String fecAsignacion) {
		this.fecAsignacion = fecAsignacion;
	}

	public String getFecDevolucion() {
		return fecDevolucion;
	}

	public void setFecDevolucion(String fecDevolucion) {
		this.fecDevolucion = fecDevolucion;
	}

	public List<Integer> getListaIdsPlacasSeleccionadosParaCoger() {
		return listaIdsPlacasSeleccionadosParaCoger;
	}

	public void setListaIdsPlacasSeleccionadosParaCoger(List<Integer> listaIdsPlacasSeleccionadosParaCoger) {
		this.listaIdsPlacasSeleccionadosParaCoger = listaIdsPlacasSeleccionadosParaCoger;
	}

	public List<Integer> getListaIdsPlacasSeleccionadosParaDevolver() {
		return listaIdsPlacasSeleccionadosParaDevolver;
	}

	public void setListaIdsPlacasSeleccionadosParaDevolver(List<Integer> listaIdsPlacasSeleccionadosParaDevolver) {
		this.listaIdsPlacasSeleccionadosParaDevolver = listaIdsPlacasSeleccionadosParaDevolver;
	}

	
	
	

}
