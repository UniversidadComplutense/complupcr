package es.ucm.pcr.beans;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MuestraBusquedaBean {
	private String nombrePaciente;
	private String emailPaciente;
	private String nhcPaciente;
	private String etiquetaMuestra;
	private String refInternaMuestra;
	private Date fechaEnvioMuestraIni;
	private Date fechaEnvioMuestraFin;
	private Date fechaResultadoMuestraIni;
	private Date fechaResultadoMuestraFin;
	
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
}
