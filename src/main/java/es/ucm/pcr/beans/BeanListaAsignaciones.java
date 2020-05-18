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

public class BeanListaAsignaciones {
	private List<BeanAsignacion> listaAnalistasLab; //lista de los analistas de laboratorioCentro asignados
	private List<BeanAsignacion> listaAnalistasVol; //lista de los analistas voluntarios del laboratorioCentro asignados
	private List<BeanAsignacion> listaAnalistasVolSinLabCentro; //lista de los analistas voluntarios de ningun laboratorioCentro asignados
	
	
	
	
	public BeanListaAsignaciones() {
		listaAnalistasLab = new ArrayList<BeanAsignacion>();
		listaAnalistasVol = new ArrayList<BeanAsignacion>();
		listaAnalistasVolSinLabCentro = new ArrayList<BeanAsignacion>();		
	}


	public List<BeanAsignacion> getListaAnalistasLab() {
		return listaAnalistasLab;
	}


	public void setListaAnalistasLab(List<BeanAsignacion> listaAnalistasLab) {
		this.listaAnalistasLab = listaAnalistasLab;
	}


	public List<BeanAsignacion> getListaAnalistasVol() {
		return listaAnalistasVol;
	}


	public void setListaAnalistasVol(List<BeanAsignacion> listaAnalistasVol) {
		this.listaAnalistasVol = listaAnalistasVol;
	}


	public List<BeanAsignacion> getListaAnalistasVolSinLabCentro() {
		return listaAnalistasVolSinLabCentro;
	}


	public void setListaAnalistasVolSinLabCentro(List<BeanAsignacion> listaAnalistasVolSinLabCentro) {
		this.listaAnalistasVolSinLabCentro = listaAnalistasVolSinLabCentro;
	}

	

	
}