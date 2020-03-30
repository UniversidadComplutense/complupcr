package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 12:25:35 by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Paciente generated by hbm2java
 */
@Entity
@Table(name = "paciente", uniqueConstraints = @UniqueConstraint(columnNames = "idMuestra"))
public class Paciente implements java.io.Serializable {

	private Integer id;
	private Muestra muestra;
	private String nombrePaciente;
	private String apellido1paciente;
	private String apellido2paciente;
	private String notificarAutomato;
	private String notificar;
	private String nhc;
	private String email;
	private String telefono;

	public Paciente() {
	}

	public Paciente(Muestra muestra) {
		this.muestra = muestra;
	}

	public Paciente(Muestra muestra, String nombrePaciente, String apellido1paciente, String apellido2paciente,
			String notificarAutomato, String notificar, String nhc, String email, String telefono) {
		this.muestra = muestra;
		this.nombrePaciente = nombrePaciente;
		this.apellido1paciente = apellido1paciente;
		this.apellido2paciente = apellido2paciente;
		this.notificarAutomato = notificarAutomato;
		this.notificar = notificar;
		this.nhc = nhc;
		this.email = email;
		this.telefono = telefono;
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
	@JoinColumn(name = "idMuestra", unique = true, nullable = false)
	public Muestra getMuestra() {
		return this.muestra;
	}

	public void setMuestra(Muestra muestra) {
		this.muestra = muestra;
	}

	@Column(name = "nombrePaciente", length = 45)
	public String getNombrePaciente() {
		return this.nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	@Column(name = "apellido1Paciente", length = 45)
	public String getApellido1paciente() {
		return this.apellido1paciente;
	}

	public void setApellido1paciente(String apellido1paciente) {
		this.apellido1paciente = apellido1paciente;
	}

	@Column(name = "apellido2Paciente", length = 45)
	public String getApellido2paciente() {
		return this.apellido2paciente;
	}

	public void setApellido2paciente(String apellido2paciente) {
		this.apellido2paciente = apellido2paciente;
	}

	@Column(name = "NotificarAutomato", length = 1)
	public String getNotificarAutomato() {
		return this.notificarAutomato;
	}

	public void setNotificarAutomato(String notificarAutomato) {
		this.notificarAutomato = notificarAutomato;
	}

	@Column(name = "Notificar", length = 1)
	public String getNotificar() {
		return this.notificar;
	}

	public void setNotificar(String notificar) {
		this.notificar = notificar;
	}

	@Column(name = "NHC", length = 45)
	public String getNhc() {
		return this.nhc;
	}

	public void setNhc(String nhc) {
		this.nhc = nhc;
	}

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefono", length = 9)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
