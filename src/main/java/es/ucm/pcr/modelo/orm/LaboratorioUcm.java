package es.ucm.pcr.modelo.orm;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "laboratorioCentroUcm")
public class LaboratorioUcm implements java.io.Serializable {
	
	private Integer id;
	private String nombre;
	
	public LaboratorioUcm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LaboratorioUcm(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	@Override
	public String toString() {
		return "LaboratorioUcm [id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
