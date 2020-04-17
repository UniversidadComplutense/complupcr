package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 17:36:56 by Hibernate Tools 5.2.12.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Equipo generated by hbm2java
 */
@Entity
@Table(name = "equipo")
public class Equipo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 153676787451191102L;
	private Integer id;
	private LaboratorioCentro laboratorioCentro;
	private String nombre;
	private Integer  capacidad;

	public Equipo() {
	}
	
	public Equipo(Integer id) {
		this.id = id;
	}

	public Equipo(String nombre, Integer capacidad) {
		this.nombre = nombre;
		this.capacidad = capacidad;
	}

	public Equipo(LaboratorioCentro laboratorioCentro, String nombre, Integer capacidad) {
		this.laboratorioCentro = laboratorioCentro;
		this.nombre = nombre;
		this.capacidad = capacidad;
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
	@JoinColumn(name = "IdLaboratorio")
	public LaboratorioCentro getLaboratorioCentro() {
		return this.laboratorioCentro;
	}

	public void setLaboratorioCentro(LaboratorioCentro laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
	}

	@Column(name = "nombre", nullable = false, length = 200)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "capacidad", nullable = false, length = 45)
	public Integer getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

}
