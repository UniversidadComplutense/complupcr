package es.ucm.pcr.beans;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import es.ucm.pcr.beans.BeanEstado.Estado;

public class MuestraBusquedaBean {
	
	private static final Integer RESUELTA_SIN_NOTIFICAR = 1;
	private static final Integer RESUELTA_NOTIFICADA = 2;
	private static final Integer RESUELTA = 3;
	
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
	private String tipoMuestra;
	private String estadoMuestra;
	private Integer idEstado;
	private Integer estadoResueltaNoticado;
	private Boolean estaNotificada;
	
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
	public String getTipoMuestra() {
		return tipoMuestra;
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
	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}
	public Integer getEstadoResueltaNoticado() {
		return estadoResueltaNoticado;
	}
	public void setEstadoResueltaNoticado(Integer estadoResueltaNoticado) {
		this.estadoResueltaNoticado = estadoResueltaNoticado;
	}
	public Integer getIdEstado() {
		if (idEstado != null) {
			return idEstado;
		} else if (estadoResueltaNoticado != null) {
			// si nos llega del buscador que quiere buscar las resueltas
			return Estado.MUESTRA_RESUELTA.getCodNum();
		}
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public Boolean getEstaNotificada() {
		if (estaNotificada != null) {
			return estaNotificada;
		} else if (estadoResueltaNoticado != null) {
			if (estadoResueltaNoticado.equals(RESUELTA_NOTIFICADA)) {
				return Boolean.TRUE;
			}
			if (estadoResueltaNoticado.equals(RESUELTA_SIN_NOTIFICAR)) {
				return Boolean.FALSE;
			}
		}
		return estaNotificada;
	}
	public void setEstaNotificada(Boolean estaNotificada) {
		this.estaNotificada = estaNotificada;
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
