package es.ucm.pcr.modelo.orm;

import javax.persistence.Entity;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

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
