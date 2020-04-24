package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class BeanBusquedaMuestraAnalisis {
	//para buscar por datos de paciente
	private String nombrePaciente;
	private String primerApellido;
	private String segundoApellido;
	private String emailPaciente;
	private String nhcPaciente;
	
	private String etiquetaMuestra;
	private String refInternaMuestra;	
	private Date fechaResultadoMuestraIni;
	private Date fechaResultadoMuestraFin;
	
	private String estadoMuestra;
	private Integer idEstado;
	private Integer estadoResueltaNoticado;
	private Boolean estaNotificada;
	
	private Integer idPlacaLaboratorio;
	private Integer idJefePlaca; //será el jefe que ha cogido la placa bajo su responsabilidad
	
	private Date fechaReclamadaPlacaIni;
	private Date fechaReclamadaPlacaFin;
	
		
	public BeanBusquedaMuestraAnalisis() {
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
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public Integer getEstadoResueltaNoticado() {
		return estadoResueltaNoticado;
	}
	public void setEstadoResueltaNoticado(Integer estadoResueltaNoticado) {
		this.estadoResueltaNoticado = estadoResueltaNoticado;
	}
	public Boolean getEstaNotificada() {
		return estaNotificada;
	}
	public void setEstaNotificada(Boolean estaNotificada) {
		this.estaNotificada = estaNotificada;
	}
	public Integer getIdPlacaLaboratorio() {
		return idPlacaLaboratorio;
	}
	public void setIdPlacaLaboratorio(Integer idPlacaLaboratorio) {
		this.idPlacaLaboratorio = idPlacaLaboratorio;
	}
	public Integer getIdJefePlaca() {
		return idJefePlaca;
	}
	public void setIdJefePlaca(Integer idJefePlaca) {
		this.idJefePlaca = idJefePlaca;
	}	
	public Date getFechaReclamadaPlacaIni() {
		return fechaReclamadaPlacaIni;
	}
	public void setFechaReclamadaPlacaIni(Date fechaReclamadaPlacaIni) {
		this.fechaReclamadaPlacaIni = fechaReclamadaPlacaIni;
	}
	public Date getFechaReclamadaPlacaFin() {
		return fechaReclamadaPlacaFin;
	}
	public void setFechaReclamadaPlacaFin(Date fechaReclamadaPlacaFin) {
		this.fechaReclamadaPlacaFin = fechaReclamadaPlacaFin;
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
