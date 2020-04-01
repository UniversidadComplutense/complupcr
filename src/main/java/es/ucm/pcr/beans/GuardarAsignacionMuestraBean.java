package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;


public class GuardarAsignacionMuestraBean extends MuestraBean {

	private String fecAsignacion;	
	private List<Integer> listaIdsAnalistasLabSeleccionados;
	private List<Integer> listaIdsAnalistasVolSeleccionados;	

	public GuardarAsignacionMuestraBean() {
		super();
		listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
		listaIdsAnalistasVolSeleccionados = new ArrayList<Integer>();

	}
	
	public GuardarAsignacionMuestraBean(MuestraBean muestrabean) {
		super(muestrabean);
		listaIdsAnalistasLabSeleccionados = new ArrayList<Integer>();
		listaIdsAnalistasVolSeleccionados = new ArrayList<Integer>();

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

	
	

}
