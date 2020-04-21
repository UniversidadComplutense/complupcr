package es.ucm.pcr.beans;

import java.util.Date;
import java.util.List;

public class BusquedaRecepcionPlacasVisavetBean {

	private Integer idPlaca;
	private Integer numeroMuestras;
	private Integer idEstadoPlaca;
	private Date fechaBusquedaInicio;
	private Date fechaBusquedaFin;
	private Date fechaEnviadaInicio;
	private Date fechaEnviadaFin;
	private Date fechaRecepcionInicio;
	private Date fechaRecepcionFin;
	private Integer idLaboratorioCentro;
	private List<Integer> estadosBusqueda;

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

	public Date getFechaBusquedaInicio() {
		return fechaBusquedaInicio;
	}

	public void setFechaBusquedaInicio(Date fechaBusquedaInicio) {
		this.fechaBusquedaInicio = fechaBusquedaInicio;
	}

	public Date getFechaBusquedaFin() {
		return fechaBusquedaFin;
	}

	public void setFechaBusquedaFin(Date fechaBusquedaFin) {
		this.fechaBusquedaFin = fechaBusquedaFin;
	}

	public Date getFechaEnviadaInicio() {
		return fechaEnviadaInicio;
	}

	public void setFechaEnviadaInicio(Date fechaEnviadaInicio) {
		this.fechaEnviadaInicio = fechaEnviadaInicio;
	}

	public Date getFechaEnviadaFin() {
		return fechaEnviadaFin;
	}

	public void setFechaEnviadaFin(Date fechaEnviadaFin) {
		this.fechaEnviadaFin = fechaEnviadaFin;
	}

	public Date getFechaRecepcionInicio() {
		return fechaRecepcionInicio;
	}

	public void setFechaRecepcionInicio(Date fechaRecepcionInicio) {
		this.fechaRecepcionInicio = fechaRecepcionInicio;
	}

	public Date getFechaRecepcionFin() {
		return fechaRecepcionFin;
	}

	public void setFechaRecepcionFin(Date fechaRecepcionFin) {
		this.fechaRecepcionFin = fechaRecepcionFin;
	}

	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}

	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}

	public List<Integer> getEstadosBusqueda() {
		return estadosBusqueda;
	}

	public void setEstadosBusqueda(List<Integer> estadosBusqueda) {
		this.estadosBusqueda = estadosBusqueda;
	}

}
