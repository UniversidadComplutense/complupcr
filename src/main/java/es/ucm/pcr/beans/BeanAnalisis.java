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

public class BeanAnalisis {
	
	//bean en el que se recoge el proceso de analizar los resultados por parte de analistas (personal ucm o voluntarios) y jefes de servicio
	
	private String resultadoAnalisis; //Valoración final de la muestra (N,P,R,A)
	private Date fechaResultadoAnalisis;
	private BeanListaAsignaciones beanListaAsignaciones; //lista de asignaciones que tiene la muestra; el jefe de servicio asigna analistas a las muestras para que las valoren
	private Integer numTotalAnalistasAsignados;
	
	private BeanAsignacion asignacionUsuarioLogado; //datos de valoración que ha puesto el usuario logado a la muestra
	
	
	public BeanAnalisis() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getResultadoAnalisis() {
		return resultadoAnalisis;
	}


	public void setResultadoAnalisis(String resultadoAnalisis) {
		this.resultadoAnalisis = resultadoAnalisis;
	}



	public Date getFechaResultadoAnalisis() {
		return fechaResultadoAnalisis;
	}


	public void setFechaResultadoAnalisis(Date fechaResultadoAnalisis) {
		this.fechaResultadoAnalisis = fechaResultadoAnalisis;
	}


	public BeanListaAsignaciones getBeanListaAsignaciones() {
		return beanListaAsignaciones;
	}


	public void setBeanListaAsignaciones(BeanListaAsignaciones beanListaAsignaciones) {
		this.beanListaAsignaciones = beanListaAsignaciones;
	}


	public Integer getNumTotalAnalistasAsignados() {
		return numTotalAnalistasAsignados;
	}


	public void setNumTotalAnalistasAsignados(Integer numTotalAnalistasAsignados) {
		this.numTotalAnalistasAsignados = numTotalAnalistasAsignados;
	}


	public BeanAsignacion getAsignacionUsuarioLogado() {
		return asignacionUsuarioLogado;
	}


	public void setAsignacionUsuarioLogado(BeanAsignacion asignacionUsuarioLogado) {
		this.asignacionUsuarioLogado = asignacionUsuarioLogado;
	}


	@Override
	public String toString() {
		return "BeanAnalisis [resultadoAnalisis=" + resultadoAnalisis + ", fechaResultadoAnalisis="
				+ fechaResultadoAnalisis + ", beanListaAsignaciones=" + beanListaAsignaciones
				+ ", asignacionUsuarioLogado=" + asignacionUsuarioLogado + "]";
	}


	

	

	
		

}
