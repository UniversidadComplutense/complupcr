package es.ucm.pcr.beans;

import java.sql.Date;

public class BusquedaPlacaLaboratorioBean {

	private String idPlaca;
	private String numeroMuestras;
	private String idEstadoPlaca;
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;

	
	public String getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(String idPlaca) {
		this.idPlaca = idPlaca;
	}

	public String getNumeroMuestras() {
		return numeroMuestras;
	}

	public void setNumeroMuestras(String numeroMuestras) {
		this.numeroMuestras = numeroMuestras;
	}

	public String getIdEstadoPlaca() {
		return idEstadoPlaca;
	}

	public void setIdEstadoPlaca(String idEstadoPlaca) {
		this.idEstadoPlaca = idEstadoPlaca;
	}

	public Date getFechaCreacionInicio() {
		return fechaCreacionInicio;
	}

	public void setFechaCreacionInicio(Date fechaCreacionInicio) {
		this.fechaCreacionInicio = fechaCreacionInicio;
	}

	public Date getFechaCreacionFin() {
		return fechaCreacionFin;
	}

	public void setFechaCreacionFin(Date fechaCreacionFin) {
		this.fechaCreacionFin = fechaCreacionFin;
	}

}
