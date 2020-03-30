package es.ucm.pcr.beans;

import es.uc.pcr.utilidades.Utilidades;

public class BeanLaboratorioCentro implements Comparable <BeanLaboratorioCentro>{

	private Integer id;
	private String nombre;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanLaboratorioCentro() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanLaboratorioCentro(Integer id, String nombre, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "BeanLaboratorioCentro [id=" + id + ", nombre=" + nombre + ", accion=" + accion + "]";
	}
	
	@Override
    public int compareTo(BeanLaboratorioCentro o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }	
}
