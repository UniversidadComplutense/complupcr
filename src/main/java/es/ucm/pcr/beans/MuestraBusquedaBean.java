package es.ucm.pcr.beans;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class MuestraBusquedaBean {
	private String nombrePaciente;
	private String primerApellido;
	private String segundoApellido;
	private String emailPaciente;
	private String nhcPaciente;
	private String etiquetaMuestra;
	private String refInternaMuestra;
	private Date fechaEnvioMuestraIni;
	private Date fechaEnvioMuestraFin;
	private Date fechaResultadoMuestraIni;
	private Date fechaResultadoMuestraFin;
	private Integer idCentro;
	
	// TODO - ENUMERADO?
	private String estadoMuestra;
	
	public MuestraBusquedaBean() {
		super();
	}
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}	
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getEmailPaciente() {
		return emailPaciente;
	}
	public void setEmailPaciente(String emailPaciente) {
		this.emailPaciente = emailPaciente;
	}
	public String getNhcPaciente() {
		return nhcPaciente;
	}
	public void setNhcPaciente(String nhcPaciente) {
		this.nhcPaciente = nhcPaciente;
	}
	public String getEtiquetaMuestra() {
		return etiquetaMuestra;
	}
	public void setEtiquetaMuestra(String etiquetaMuestra) {
		this.etiquetaMuestra = etiquetaMuestra;
	}
	public String getRefInternaMuestra() {
		return refInternaMuestra;
	}
	public void setRefInternaMuestra(String refInternaMuestra) {
		this.refInternaMuestra = refInternaMuestra;
	}
	public Date getFechaEnvioMuestraIni() {
		return fechaEnvioMuestraIni;
	}
	public void setFechaEnvioMuestraIni(Date fechaEnvioMuestraIni) {
		this.fechaEnvioMuestraIni = fechaEnvioMuestraIni;
	}
	public Date getFechaEnvioMuestraFin() {
		return fechaEnvioMuestraFin;
	}
	public void setFechaEnvioMuestraFin(Date fechaEnvioMuestraFin) {
		this.fechaEnvioMuestraFin = fechaEnvioMuestraFin;
	}
	public Date getFechaResultadoMuestraIni() {
		return fechaResultadoMuestraIni;
	}
	public void setFechaResultadoMuestraIni(Date fechaResultadoMuestraIni) {
		this.fechaResultadoMuestraIni = fechaResultadoMuestraIni;
	}
	public Date getFechaResultadoMuestraFin() {
		return fechaResultadoMuestraFin;
	}
	public void setFechaResultadoMuestraFin(Date fechaResultadoMuestraFin) {
		this.fechaResultadoMuestraFin = fechaResultadoMuestraFin;
	}
	public String getEstadoMuestra() {
		return estadoMuestra;
	}
	public void setEstadoMuestra(String estadoMuestra) {
		this.estadoMuestra = estadoMuestra;
	}
	public Integer getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}
	
	public String getCriterioNombre() {
		return StringUtils.isBlank(nombrePaciente) ? null : "%" + nombrePaciente + "%";
	}
	
	public String getCriterioPrimerApellido() {
		return StringUtils.isBlank(primerApellido) ? null : "%" + primerApellido + "%";
	}
	
	public String getCriterioSegundoApellido() {
		return StringUtils.isBlank(segundoApellido) ? null : "%" + segundoApellido + "%";
	}
	
	public String getCriterioCorreo() {
		return StringUtils.isBlank(emailPaciente) ? null : "%" + emailPaciente + "%";
	}
	
	public String getCriterioNHC() {
		return StringUtils.isBlank(nhcPaciente) ? null : "%" + nhcPaciente + "%";
	}
	
	public String getCriterioEtiqueta() {
		return StringUtils.isBlank(etiquetaMuestra) ? null : "%" + etiquetaMuestra + "%";
	}
	
	public String getCriterioRefInterna() {
		return StringUtils.isBlank(refInternaMuestra) ? null : "%" + refInternaMuestra + "%";
	}
}
