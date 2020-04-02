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
