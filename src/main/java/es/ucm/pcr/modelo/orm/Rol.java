package es.ucm.pcr.modelo.orm;
// Generated 23 mar. 2020 23:59:07 by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Rol generated by hbm2java
 */
@Entity
@Table(name = "rol", catalog = "covid19")
public class Rol implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5884879270022594310L;
	private int id;
	private String nombre;
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);

	public Rol() {
	}

	public Rol(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Rol(int id, String nombre, Set<Usuario> usuarios) {
		this.id = id;
		this.nombre = nombre;
		this.usuarios = usuarios;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_rol", 
		joinColumns = {@JoinColumn(name = "rol_id", referencedColumnName="id", nullable = false) },
		inverseJoinColumns = {@JoinColumn(name = "usuario_id", referencedColumnName="id", nullable = false) })
	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
