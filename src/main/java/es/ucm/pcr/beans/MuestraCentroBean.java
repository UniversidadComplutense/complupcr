package es.ucm.pcr.beans;

import java.util.Date;

import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;

public class MuestraCentroBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer idCentro;
	private String etiqueta;
	private String tipoMuestra;
	private Date fechaEntrada;
	private Date fechaAsignada;
	private Date fechaEnvio;
	private Date fechaResultado;
	private String refInterna;
	private String resultado;
	private BeanEstado estado;
	private Integer idLote;
	private String nhc;
	private boolean recogerDatosNotif;
	private boolean avisosAuto;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String correo;
	private String telefono;
	private boolean avisoSms;
	private Date fechaNotificacion;
	private Integer idPaciente;

	public MuestraCentroBean() {
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

	public Date getFechaAsignada() {
		return fechaAsignada;
	}

	public void setFechaAsignada(Date fechaAsignada) {
		this.fechaAsignada = fechaAsignada;
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

	public BeanEstado getEstado() {
		return estado;
	}

	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}

	public Integer getIdLote() {
		return this.idLote;
	}

	public void setIdLote(Integer idLote) {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

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

	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public static Muestra beanToModel(MuestraCentroBean muestraBean) {
		Muestra muestra = new Muestra();
		Paciente paciente = new Paciente();

		muestra.setId(muestraBean.getId());
		muestra.setEtiqueta(muestraBean.getEtiqueta());
		muestra.setTipoMuestra(muestraBean.getTipoMuestra());
		muestra.setFechaEntrada(muestraBean.getFechaEntrada());
		muestra.setFechaAsignada(muestraBean.getFechaAsignada());
		muestra.setEstadoMuestra(new EstadoMuestra(muestraBean.getEstado().getEstado().getCodNum()));
		muestra.setCentro(new Centro(muestraBean.getIdCentro()));	
		
		paciente.setNombrePaciente(muestraBean.getNombre());
		paciente.setApellido1paciente(muestraBean.getPrimerApellido());
		paciente.setApellido2paciente(muestraBean.getSegundoApellido());
		paciente.setNhc(muestraBean.getNhc());
		paciente.setEmail(muestraBean.getCorreo());
		paciente.setTelefono(muestraBean.getTelefono());
		paciente.setNotificar(muestraBean.isAvisoSms() ? "S" : "N");
		paciente.setNotificarAutomato(muestraBean.isAvisosAuto() ? "S" : "N");

		paciente.setMuestra(muestra);
		muestra.getPacientes().add(paciente);
		
		// TODO - COMPLETAR MUESTRAS
		
		return muestra;
	}
	
	public static MuestraCentroBean modelToBean(Muestra muestra) {
		Paciente paciente = muestra.getPacientes().iterator().next();
		MuestraCentroBean muestraBean = new MuestraCentroBean();

		muestraBean.setId(muestra.getId());
		muestraBean.setEtiqueta(muestra.getEtiqueta());
		muestraBean.setTipoMuestra(muestra.getTipoMuestra());
		muestraBean.setFechaEntrada(muestra.getFechaEntrada());
		muestraBean.setFechaAsignada(muestra.getFechaAsignada());
		BeanEstado beanEstado = new BeanEstado();
		muestraBean.setEstado(beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, muestra.getEstadoMuestra().getId()));
		muestraBean.setIdCentro(muestra.getCentro().getId());	
		
		muestraBean.setNombre(paciente.getNombrePaciente());
		muestraBean.setPrimerApellido(paciente.getApellido1paciente());
		muestraBean.setSegundoApellido(paciente.getApellido2paciente());
		muestraBean.setNhc(paciente.getNhc());
		muestraBean.setCorreo(paciente.getEmail());
		muestraBean.setTelefono(paciente.getTelefono());
		muestraBean.setAvisoSms(paciente.getNotificar().equals("S"));
		muestraBean.setAvisosAuto(paciente.getNotificarAutomato().equals("S"));

		// TODO - COMPLETAR
		
		return muestraBean;
	}

}
