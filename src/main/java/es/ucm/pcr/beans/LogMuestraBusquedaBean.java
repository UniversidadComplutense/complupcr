package es.ucm.pcr.beans;

import org.apache.commons.lang.StringUtils;

public class LogMuestraBusquedaBean {

	private Integer idMuestra;
	private String etiquetaMuestra;
	private String refInternaVisavetMuestra;
	private String nhcPaciente;
	private Integer idCentro;
	private Integer idLote;
	private String codNumLote;
	private String referenciaInternaLote;
	private Integer idPlacaLaboratorio;
	private Integer idPlacaVisavet;
	private String nombrePlacaVisavet;

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

	public String getRefInternaVisavetMuestra() {
		return refInternaVisavetMuestra;
	}

	public void setRefInternaVisavetMuestra(String refInternaVisavetMuestra) {
		this.refInternaVisavetMuestra = refInternaVisavetMuestra;
	}

	public String getCodNumLote() {
		return codNumLote;
	}

	public void setCodNumLote(String codNumLote) {
		this.codNumLote = codNumLote;
	}

	public String getReferenciaInternaLote() {
		return referenciaInternaLote;
	}

	public void setReferenciaInternaLote(String referenciaInternaLote) {
		this.referenciaInternaLote = referenciaInternaLote;
	}

	public String getNombrePlacaVisavet() {
		return nombrePlacaVisavet;
	}

	public void setNombrePlacaVisavet(String nombrePlacaVisavet) {
		this.nombrePlacaVisavet = nombrePlacaVisavet;
	}
	
	public String getCriterioNHC() {
		return StringUtils.isBlank(nhcPaciente) ? null : "%" + nhcPaciente + "%";
	}
	
	public String getCriterioEtiqueta() {
		return StringUtils.isBlank(etiquetaMuestra) ? null : "%" + etiquetaMuestra + "%";
	}
	
	public String getCriterioRefInternaMuestra() {
		return StringUtils.isBlank(refInternaVisavetMuestra) ? null : "%" + refInternaVisavetMuestra + "%";
	}
	
	public String getCriterioRefInternaLote() {
		return StringUtils.isBlank(referenciaInternaLote) ? null : "%" + referenciaInternaLote + "%";
	}
	
	public String getCriterioNombrePlacaVisavet() {
		return StringUtils.isBlank(nombrePlacaVisavet) ? null : "%" + nombrePlacaVisavet + "%";
	}
	
	public String getCriterioCodNumLote() {
		return StringUtils.isBlank(codNumLote) ? null : "%" + codNumLote + "%";
	}
	
}
