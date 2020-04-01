package es.ucm.pcr.beans;

import es.ucm.pcr.utilidades.Utilidades;

public class BeanRol implements Comparable<BeanRol>{
	
	private Integer id;
	private String rol;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanRol() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		
	public BeanRol(Integer id, String rol, String accion) {
		super();
		this.id = id;
		this.rol = rol;
		this.accion = accion;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getAccion() {
		return accion;
	}


	public void setAccion(String accion) {
		this.accion = accion;
	}


	@Override
	public String toString() {
		return "BeanRol [id=" + id + ", rol=" + rol + ", accion=" + accion + "]";
	}

	@Override
    public int compareTo(BeanRol o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getRol());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getRol());
		return p2.compareTo(p1);
    }		
}
