package es.ucm.pcr.beans;

import java.util.HashSet;
import java.util.Set;

import es.ucm.pcr.utilidades.Utilidades;
import es.ucm.pcr.modelo.orm.Documento;
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;

public class BeanLaboratorioCentro implements Comparable <BeanLaboratorioCentro>{

	private Integer id;
	private String nombre;
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<PlacaLaboratorio> placaLaboratorios = new HashSet<PlacaLaboratorio>(0);
	private Set<Equipo> equipos = new HashSet<Equipo>(0);
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanLaboratorioCentro() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BeanLaboratorioCentro(Integer id, String nombre, Set<Documento> documentos,
			Set<PlacaLaboratorio> placaLaboratorios, Set<Equipo> equipos, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.documentos = documentos;
		this.placaLaboratorios = placaLaboratorios;
		this.equipos = equipos;
		this.accion = accion;
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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}


	
	public Set<Documento> getDocumentos() {
		return documentos;
	}


	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}


	public Set<PlacaLaboratorio> getPlacaLaboratorios() {
		return placaLaboratorios;
	}


	public void setPlacaLaboratorios(Set<PlacaLaboratorio> placaLaboratorios) {
		this.placaLaboratorios = placaLaboratorios;
	}


	public Set<Equipo> getEquipos() {
		return equipos;
	}


	public void setEquipos(Set<Equipo> equipos) {
		this.equipos = equipos;
	}

	

	@Override
	public String toString() {
		return "BeanLaboratorioCentro [id=" + id + ", nombre=" + nombre + ", documentos=" + documentos
				+ ", placaLaboratorios=" + placaLaboratorios + ", equipos=" + equipos + ", accion=" + accion + "]";
	}


	@Override
    public int compareTo(BeanLaboratorioCentro o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }	
}
