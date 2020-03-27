package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;

public class BeanListaAsignaciones {
	private List<BeanAsignacion> listaAsignaciones;
	
	
	public BeanListaAsignaciones() {
		listaAsignaciones = new ArrayList<BeanAsignacion>();
		
	}


	public List<BeanAsignacion> getListaAsignaciones() {
		return listaAsignaciones;
	}


	public void setListaAsignaciones(List<BeanAsignacion> listaAsignaciones) {
		this.listaAsignaciones = listaAsignaciones;
	}

	
}