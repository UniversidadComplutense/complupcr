package es.ucm.pcr.beans;

import es.uc.pcr.utilidades.Utilidades;

public class BeanLaboratorioVisavet implements Comparable <BeanLaboratorioVisavet>{

	private Integer id;
	private String nombre;
	private Integer capacidad;
	private Integer ocupacion;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanLaboratorioVisavet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanLaboratorioVisavet(Integer id, String nombre, Integer capacidad, Integer ocupacion, String accion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.ocupacion = ocupacion;
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

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Integer ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	@Override
	public String toString() {
		return "BeanLaboratorioVisavet [id=" + id + ", nombre=" + nombre + ", capacidad=" + capacidad + ", ocupacion="
				+ ocupacion + ", accion=" + accion + "]";
	}
	
	
	
	@Override
    public int compareTo(BeanLaboratorioVisavet o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }	
	
}
