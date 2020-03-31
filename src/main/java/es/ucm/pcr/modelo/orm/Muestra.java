package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 17:36:56 by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Muestra generated by hbm2java
 */
@Entity
@Table(name = "muestra")
public class Muestra implements java.io.Serializable {

	private Integer id;
	private Centro centro;
	private EstadoMuestra estadoMuestra;
	private Lote lote;
	private String etiqueta;
	private String tipoMuestra;
	private Date fechaEntrada;
	private Date fechaEnvio;
	private Date fechaResultado;
	private String refInternaVisavet;
	private String resultado;
	private Date fechaAsignada;
	private Date fechaNotificacion;
	private Set<UsuarioMuestra> usuarioMuestras = new HashSet<UsuarioMuestra>(0);
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<Paciente> pacientes = new HashSet<Paciente>(0);

	public Muestra() {
	}

	public Muestra(Centro centro, EstadoMuestra estadoMuestra, String etiqueta, String tipoMuestra) {
		this.centro = centro;
		this.estadoMuestra = estadoMuestra;
		this.etiqueta = etiqueta;
		this.tipoMuestra = tipoMuestra;
	}

	public Muestra(Centro centro, EstadoMuestra estadoMuestra, Lote lote, String etiqueta, String tipoMuestra,
			Date fechaEntrada, Date fechaEnvio, Date fechaResultado, String refInternaVisavet, String resultado,
			Date fechaAsignada, Date fechaNotificacion, Set<UsuarioMuestra> usuarioMuestras,
			Set<Documento> documentos, Set<Paciente> pacientes) {
		this.centro = centro;
		this.estadoMuestra = estadoMuestra;
		this.lote = lote;
		this.etiqueta = etiqueta;
		this.tipoMuestra = tipoMuestra;
		this.fechaEntrada = fechaEntrada;
		this.fechaEnvio = fechaEnvio;
		this.fechaResultado = fechaResultado;
		this.refInternaVisavet = refInternaVisavet;
		this.resultado = resultado;
		this.fechaAsignada = fechaAsignada;
		this.fechaNotificacion = fechaNotificacion;
		this.usuarioMuestras = usuarioMuestras;
		this.documentos = documentos;
		this.pacientes = pacientes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "idCentro", nullable = false)
	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	@ManyToOne
	@JoinColumn(name = "idEstadoMuestra", nullable = false)
	public EstadoMuestra getEstadoMuestra() {
		return this.estadoMuestra;
	}

	public void setEstadoMuestra(EstadoMuestra estadoMuestra) {
		this.estadoMuestra = estadoMuestra;
	}

	@ManyToOne
	@JoinColumn(name = "idLote")
	public Lote getLote() {
		return this.lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	@Column(name = "etiqueta", nullable = false, length = 100)
	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	@Column(name = "tipoMuestra", nullable = false, length = 2)
	public String getTipoMuestra() {
		return this.tipoMuestra;
	}

	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaEntrada", length = 19)
	public Date getFechaEntrada() {
		return this.fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaEnvio", length = 19)
	public Date getFechaEnvio() {
		return this.fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaResultado", length = 19)
	public Date getFechaResultado() {
		return this.fechaResultado;
	}

	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}

	@Column(name = "refInternaVisavet", length = 100)
	public String getRefInternaVisavet() {
		return this.refInternaVisavet;
	}

	public void setRefInternaVisavet(String refInternaVisavet) {
		this.refInternaVisavet = refInternaVisavet;
	}

	@Column(name = "resultado", length = 25)
	public String getResultado() {
		return this.resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaAsignada", length = 19)	
	public Date getFechaAsignada() {
		return this.fechaAsignada;
	}

	public void setFechaAsignada(Date fechaAsignada) {
		this.fechaAsignada = fechaAsignada;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaNotificacion", length = 19)
	public Date getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "muestra")
	public Set<UsuarioMuestra> getUsuarioMuestras() {
		return this.usuarioMuestras;
	}

	public void setUsuarioMuestras(Set<UsuarioMuestra> usuarioMuestras) {
		this.usuarioMuestras = usuarioMuestras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "muestra")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "muestra")
	public Set<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
