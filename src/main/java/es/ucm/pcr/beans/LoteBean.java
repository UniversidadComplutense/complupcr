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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoteBean{
	private String id;
	private String numLote;
	private int capacidad;
	private int ocupacion;
	private BeanEstado estado;
	private Calendar fechaEntrada;
	private List<MuestraBean>  listaMuestras;
	private CentroBean centroProcedencia;
	private String test;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumLote() {
		return numLote;
	}
	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public int getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(int ocupacion) {
		this.ocupacion = ocupacion;
	}
	public BeanEstado getEstado() {
		return estado;
	}
	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}
	public Calendar getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public List<MuestraBean> getListaMuestras() {
		return listaMuestras;
	}
	public void setListaMuestras(List<MuestraBean> listaMuestras) {
		this.listaMuestras = listaMuestras;
	}
	public CentroBean getCentroProcedencia() {
		return centroProcedencia;
	}
	public void setCentroProcedencia(CentroBean centroProcedencia) {
		this.centroProcedencia = centroProcedencia;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	
}
