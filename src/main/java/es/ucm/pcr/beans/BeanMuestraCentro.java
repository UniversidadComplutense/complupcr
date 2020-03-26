package es.ucm.pcr.beans;

import java.util.Date;

import javax.persistence.Column;

public class BeanMuestraCentro implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer idCentro;
	private String etiqueta;
	private String tipoMuestra;
	private Date fechaEntrada;
	private Date fechaEnvio;
	private Date fechaResultado;
	private String refInterna;
	private String resultado;
	private Integer idEstado;
	private int idLote;
	private String nhc;
	private boolean recogerDatosNotif;
	private boolean avisosAuto;
	private String nombreApellidos;
	private String correo;
	private String telefono;
	private boolean avisoSms;
	private Date fechaNotificacion;

	public BeanMuestraCentro() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCentro() {
		return this.idCentro;
	}

	public void setCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getTipoMuestra() {
		return this.tipoMuestra;
	}

	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}

	public Date getFechaEntrada() {
		return this.fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaEnvio() {
		return this.fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaResultado() {
		return this.fechaResultado;
	}

	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}

	public String getRefInterna() {
		return this.refInterna;
	}

	public void setRefInterna(String refInterna) {
		this.refInterna = refInterna;
	}

	public String getResultado() {
		return this.resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Integer getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public int getIdLote() {
		return this.idLote;
	}

	public void setIdLote(int idLote) {
		this.idLote = idLote;
	}

	public String getNhc() {
		return this.nhc;
	}

	public void setNhc(String nhc) {
		this.nhc = nhc;
	}

	public boolean isRecogerDatosNotif() {
		return this.recogerDatosNotif;
	}

	public void setRecogerDatosNotif(boolean recogerDatosNotif) {
		this.recogerDatosNotif = recogerDatosNotif;
	}

	public boolean isAvisosAuto() {
		return this.avisosAuto;
	}

	public void setAvisosAuto(boolean avisosAuto) {
		this.avisosAuto = avisosAuto;
	}

	public String getNombreApellidos() {
		return this.nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Column(name = "telefono", length = 25)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isAvisoSms() {
		return this.avisoSms;
	}

	public void setAvisoSms(boolean avisoSms) {
		this.avisoSms = avisoSms;
	}

	public Date getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

}
