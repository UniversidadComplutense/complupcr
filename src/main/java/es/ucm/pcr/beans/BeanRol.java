package es.ucm.pcr.beans;

import es.ucm.pcr.utilidades.Utilidades;

public class BeanRol implements Comparable<BeanRol>{
	
	private Integer id;
	private String nombre;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanRol() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		

	public BeanRol(Integer id, String nombre, String accion) {
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
		return "BeanRol [id=" + id + ", nombre=" + nombre + ", accion=" + accion + "]";
	}



	@Override
    public int compareTo(BeanRol o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }		
}
