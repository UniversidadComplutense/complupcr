package es.ucm.pcr.modelo.orm;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "laboratorioVisavetUcm")
public class LaboratorioVisavet {
	
	private Integer id;
	private String nombre;
	private String capacidad;
	private String ocupacion;
	
	public LaboratorioVisavet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LaboratorioVisavet(Integer id, String nombre, String capacidad, String ocupacion) {
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
	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	@Column(name = "ocupacion")
	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	@Override
	public String toString() {
		return "LaboratorioVisavet [id=" + id + ", nombre=" + nombre + ", capacidad=" + capacidad + ", ocupacion="
				+ ocupacion + "]";
	}
	
	
	
	
}
