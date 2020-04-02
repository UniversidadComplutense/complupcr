package es.ucm.pcr.beans;

import java.sql.Date;

public class BusquedaPlacaLaboratorioJefeBean {

	private Integer idPlaca;
	private Integer numeroMuestras;
	private Integer idEstadoPlaca;
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;
	private Integer idJefe; //jefe que tiene asignada la placa
	private Integer idLaboratorioCentro; //laboratorioCentro en el que está la placa
	
	public Integer getIdPlaca() {
		return idPlaca;
	}
	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}
	public Integer getNumeroMuestras() {
		return numeroMuestras;
	}
	public void setNumeroMuestras(Integer numeroMuestras) {
		this.numeroMuestras = numeroMuestras;
	}
	public Integer getIdEstadoPlaca() {
		return idEstadoPlaca;
	}
	public void setIdEstadoPlaca(Integer idEstadoPlaca) {
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
	public Integer getIdJefe() {
		return idJefe;
	}
	public void setIdJefe(Integer idJefe) {
		this.idJefe = idJefe;
	}
	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}
	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}
	

	
	

}
