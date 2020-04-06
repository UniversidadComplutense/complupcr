package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;


public class GuardarAsignacionPlacaLaboratorioCentroBean {

	private Integer idPlaca;
	private String fecAsignacion;	
	private List<Integer> listaIdsAnalistasLabSeleccionados;
	private List<Integer> listaIdsAnalistasVolSeleccionados;	
	private List<Integer> listaIdsVolSinLabCentroSeleccionados;	


	public GuardarAsignacionPlacaLaboratorioCentroBean() {
		super();
		listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
		listaIdsAnalistasVolSeleccionados = new ArrayList<Integer>();
		listaIdsVolSinLabCentroSeleccionados = new ArrayList<Integer>();
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

	
	

}
