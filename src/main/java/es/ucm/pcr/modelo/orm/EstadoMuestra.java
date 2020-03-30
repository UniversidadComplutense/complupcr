package es.ucm.pcr.modelo.orm;
// Generated 30 mar. 2020 12:25:35 by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * EstadoMuestra generated by hbm2java
 */
@Entity
@Table(name = "estadoMuestra")
public class EstadoMuestra implements java.io.Serializable {

	private Integer id;
	private String descripcion;
	private Set<Muestra> muestras = new HashSet<Muestra>(0);

	public EstadoMuestra() {
	}

	public EstadoMuestra(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoMuestra(String descripcion, Set<Muestra> muestras) {
		this.descripcion = descripcion;
		this.muestras = muestras;
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

	@Column(name = "descripcion", nullable = false, length = 45)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoMuestra")
	public Set<Muestra> getMuestras() {
		return this.muestras;
	}

	public void setMuestras(Set<Muestra> muestras) {
		this.muestras = muestras;
	}

}
