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


public class GuardarAsignacionPlacaLaboratorioCentroBean {
	
	//datos de la placa
	private PlacaLaboratorioCentroAsignacionesBean placaLaboratorioCentroAsignacionesBean;
	
	//listados de analistas de centro, voluntarios de centro y voluntarios sin centro que puede escoger (serán todos menos los que ya tiene asignados)
	private List<BeanUsuario> beanListadoAnalistaLab;
	private List<BeanUsuario> beanListadoAnalistaVol;
	private List<BeanUsuario> beanListadoVoluntariosSinLaboratorioCentro;
	
	private Boolean noSePuedeAsignarNuevosAnalistasAPlaca;

	
	//datos que recogeremos del formulario
	private Integer idPlaca;
	private String fecAsignacion;	
	private List<Integer> listaIdsAnalistasLabSeleccionados;
	private List<Integer> listaIdsAnalistasVolSeleccionados;	
	private List<Integer> listaIdsVolSinLabCentroSeleccionados;	
	private Integer numAnalistasPermitidos;


	public GuardarAsignacionPlacaLaboratorioCentroBean() {
		super();
		listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
		listaIdsAnalistasVolSeleccionados = new ArrayList<Integer>();
		listaIdsVolSinLabCentroSeleccionados = new ArrayList<Integer>();
	}
	
	
	
	public PlacaLaboratorioCentroAsignacionesBean getPlacaLaboratorioCentroAsignacionesBean() {
		return placaLaboratorioCentroAsignacionesBean;
	}

	public void setPlacaLaboratorioCentroAsignacionesBean(
			PlacaLaboratorioCentroAsignacionesBean placaLaboratorioCentroAsignacionesBean) {
		this.placaLaboratorioCentroAsignacionesBean = placaLaboratorioCentroAsignacionesBean;
	}






	public List<BeanUsuario> getBeanListadoAnalistaLab() {
		return beanListadoAnalistaLab;
	}






	public void setBeanListadoAnalistaLab(List<BeanUsuario> beanListadoAnalistaLab) {
		this.beanListadoAnalistaLab = beanListadoAnalistaLab;
	}






	public List<BeanUsuario> getBeanListadoAnalistaVol() {
		return beanListadoAnalistaVol;
	}






	public void setBeanListadoAnalistaVol(List<BeanUsuario> beanListadoAnalistaVol) {
		this.beanListadoAnalistaVol = beanListadoAnalistaVol;
	}






	public List<BeanUsuario> getBeanListadoVoluntariosSinLaboratorioCentro() {
		return beanListadoVoluntariosSinLaboratorioCentro;
	}






	public void setBeanListadoVoluntariosSinLaboratorioCentro(
			List<BeanUsuario> beanListadoVoluntariosSinLaboratorioCentro) {
		this.beanListadoVoluntariosSinLaboratorioCentro = beanListadoVoluntariosSinLaboratorioCentro;
	}






	public Boolean getNoSePuedeAsignarNuevosAnalistasAPlaca() {
		return noSePuedeAsignarNuevosAnalistasAPlaca;
	}






	public void setNoSePuedeAsignarNuevosAnalistasAPlaca(Boolean noSePuedeAsignarNuevosAnalistasAPlaca) {
		this.noSePuedeAsignarNuevosAnalistasAPlaca = noSePuedeAsignarNuevosAnalistasAPlaca;
	}






	public Integer getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}



	public String getFecAsignacion() {
		return fecAsignacion;
	}

	public void setFecAsignacion(String fecAsignacion) {
		this.fecAsignacion = fecAsignacion;
	}

	public List<Integer> getListaIdsAnalistasLabSeleccionados() {
		return listaIdsAnalistasLabSeleccionados;
	}

	public void setListaIdsAnalistasLabSeleccionados(List<Integer> listaIdsAnalistasLabSeleccionados) {
		this.listaIdsAnalistasLabSeleccionados = listaIdsAnalistasLabSeleccionados;
	}

	public List<Integer> getListaIdsAnalistasVolSeleccionados() {
		return listaIdsAnalistasVolSeleccionados;
	}

	public void setListaIdsAnalistasVolSeleccionados(List<Integer> listaIdsAnalistasVolSeleccionados) {
		this.listaIdsAnalistasVolSeleccionados = listaIdsAnalistasVolSeleccionados;
	}

	public List<Integer> getListaIdsVolSinLabCentroSeleccionados() {
		return listaIdsVolSinLabCentroSeleccionados;
	}

	public void setListaIdsVolSinLabCentroSeleccionados(List<Integer> listaIdsVolSinLabCentroSeleccionados) {
		this.listaIdsVolSinLabCentroSeleccionados = listaIdsVolSinLabCentroSeleccionados;
	}



	public Integer getNumAnalistasPermitidos() {
		return numAnalistasPermitidos;
	}



	public void setNumAnalistasPermitidos(Integer numAnalistasPermitidos) {
		this.numAnalistasPermitidos = numAnalistasPermitidos;
	}

	
	

}
