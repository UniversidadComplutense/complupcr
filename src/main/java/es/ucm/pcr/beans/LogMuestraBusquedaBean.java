package es.ucm.pcr.beans;

import org.apache.commons.lang.StringUtils;

public class LogMuestraBusquedaBean {

	private Integer idMuestra;
	private String etiquetaMuestra;
	private String nhcPaciente;
	private Integer idCentro;
	private Integer idLote;
	private Integer idPlacaLaboratorio;
	private Integer idPlacaVisavet;

	public LogMuestraBusquedaBean() {
	}

	public Integer getIdMuestra() {
		return idMuestra;
	}

	public void setIdMuestra(Integer idMuestra) {
		this.idMuestra = idMuestra;
	}

	public String getEtiquetaMuestra() {
		if (etiquetaMuestra != null) {
			etiquetaMuestra = StringUtils.trim(etiquetaMuestra);			
		}
		return etiquetaMuestra;
	}

	public void setEtiquetaMuestra(String etiquetaMuestra) {
		this.etiquetaMuestra = etiquetaMuestra;
	}

	public String getNhcPaciente() {
		if (nhcPaciente != null) {
			nhcPaciente = StringUtils.trim(nhcPaciente);
		}
		return nhcPaciente;
	}

	public void setNhcPaciente(String nhcPaciente) {
		this.nhcPaciente = nhcPaciente;
	}

	public Integer getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}

	public Integer getIdLote() {
		return idLote;
	}

	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}

	public Integer getIdPlacaLaboratorio() {
		return idPlacaLaboratorio;
	}

	public void setIdPlacaLaboratorio(Integer idPlacaLaboratorio) {
		this.idPlacaLaboratorio = idPlacaLaboratorio;
	}

	public Integer getIdPlacaVisavet() {
		return idPlacaVisavet;
	}

	public void setIdPlacaVisavet(Integer idPlacaVisavet) {
		this.idPlacaVisavet = idPlacaVisavet;
	}

}
