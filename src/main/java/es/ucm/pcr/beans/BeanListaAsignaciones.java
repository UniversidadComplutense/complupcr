package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;

public class BeanListaAsignaciones {
	private List<BeanAsignacion> listaAnalistasLab; //lista de los analistas de laboratorio ucm asignados
	private List<BeanAsignacion> listaAnalistasVol; //lista de los analistas voluntarios asignados
	
	
	
	
	public BeanListaAsignaciones() {
		listaAnalistasLab = new ArrayList<BeanAsignacion>();
		listaAnalistasVol = new ArrayList<BeanAsignacion>();
		
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

	

	
}