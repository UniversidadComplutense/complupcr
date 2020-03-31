package es.ucm.pcr.beans;

import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class LoteBusquedaBean {

	private Integer id;
	private String numLote;
	private String idLaboratorio;
	private Integer idEstado;
	private Date fechaEnvioIni;
	private Date fechaEnvioFin;
	private Integer idCentro;

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

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
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

	public Integer getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}
	
	public String getCriterioNumLote() {
		return StringUtils.isBlank(numLote) ? null : "%" + numLote + "%";
	}
	
}
