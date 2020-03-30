package es.ucm.pcr.beans;

import java.util.List;

public class BusquedaPlacaLaboratorioBean {

	private String idPlaca;
	private String capacidad;
	private List<EstadoBean> estadoPlaca;
	private String estadoSeleccionado;
	
	

	public String getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(String idPlaca) {
		this.idPlaca = idPlaca;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public List<EstadoBean> getEstadoPlaca() {
		return estadoPlaca;
	}

	public void setEstadoPlaca(List<EstadoBean> estadoPlaca) {
		this.estadoPlaca = estadoPlaca;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

}
