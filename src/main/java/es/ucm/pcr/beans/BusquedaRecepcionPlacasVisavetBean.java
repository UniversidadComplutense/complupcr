package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.List;

public class BusquedaRecepcionPlacasVisavetBean {

	private String idPlacaVisavet;
	private String capacidad;
	private List<EstadoBean> estadosPlacaVisavet;
	private String estadoSeleccionado;
	private Calendar fechaEnvio;
	private Calendar fechaRecepcion;
	private Calendar fechaTraspaso;

	
	public String getIdPlacaVisavet() {
		return idPlacaVisavet;
	}

	public void setIdPlacaVisavet(String idPlacaVisavet) {
		this.idPlacaVisavet = idPlacaVisavet;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public List<EstadoBean> getEstadosPlacaVisavet() {
		return estadosPlacaVisavet;
	}

	public void setEstadosPlacaVisavet(List<EstadoBean> estadosPlacaVisavet) {
		this.estadosPlacaVisavet = estadosPlacaVisavet;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public Calendar getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Calendar fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Calendar getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Calendar fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Calendar getFechaTraspaso() {
		return fechaTraspaso;
	}

	public void setFechaTraspaso(Calendar fechaTraspaso) {
		this.fechaTraspaso = fechaTraspaso;
	}

}
