package es.ucm.pcr.beans;

import java.util.Date;

public class LoteBusquedaBean {

	private Integer id;
	private String numLote;
	private String idLaboratorio;
	private String idEstado;
	private Date fechaEnvioIni;
	private Date fechaEnvioFin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}

	public String getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(String idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	public Date getFechaEnvioIni() {
		return fechaEnvioIni;
	}

	public void setFechaEnvioIni(Date fechaEnvioIni) {
		this.fechaEnvioIni = fechaEnvioIni;
	}

	public Date getFechaEnvioFin() {
		return fechaEnvioFin;
	}

	public void setFechaEnvioFin(Date fechaEnvioFin) {
		this.fechaEnvioFin = fechaEnvioFin;
	}

}
