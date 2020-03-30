package es.ucm.pcr.modelo.orm;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "laboratorioVisavet")
public class LaboratorioVisavet implements java.io.Serializable{
	
	private Integer id;
	private String nombre;
	private Integer capacidad;
	private Integer ocupacion;
	


	public LaboratorioVisavet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LaboratorioVisavet(Integer id, String nombre, Integer capacidad, Integer ocupacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.ocupacion = ocupacion;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "capacidad")
	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	@Column(name = "ocupacion")
	public Integer getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Integer ocupacion) {
		this.ocupacion = ocupacion;
	}

	@Override
	public String toString() {
		return "LaboratorioVisavet [id=" + id + ", nombre=" + nombre + ", capacidad=" + capacidad + ", ocupacion="
				+ ocupacion + "]";
	}
	
	
	
	
}
