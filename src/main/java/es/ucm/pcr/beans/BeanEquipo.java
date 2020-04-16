package es.ucm.pcr.beans;

import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.utilidades.Utilidades;

public class BeanEquipo implements Comparable<BeanEquipo> {
	
	private Integer id;
	private LaboratorioCentro laboratorioCentro;
	private String nombre;
	private Integer  capacidad;
	private String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	private Integer LabUcmSeleccionado;
	

	
	public BeanEquipo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanEquipo(Integer id, LaboratorioCentro laboratorioCentro, String nombre, Integer capacidad, String accion,
			Integer labUcmSeleccionado) {
		super();
		this.id = id;
		this.laboratorioCentro = laboratorioCentro;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.accion = accion;
		LabUcmSeleccionado = labUcmSeleccionado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LaboratorioCentro getLaboratorioCentro() {
		return laboratorioCentro;
	}

	public void setLaboratorioCentro(LaboratorioCentro laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Integer getLabUcmSeleccionado() {
		return LabUcmSeleccionado;
	}

	public void setLabUcmSeleccionado(Integer labUcmSeleccionado) {
		LabUcmSeleccionado = labUcmSeleccionado;
	}	
	
	@Override
	public String toString() {
		return "BeanEquipo [id=" + id + ", laboratorioCentro=" + laboratorioCentro + ", nombre=" + nombre
				+ ", capacidad=" + capacidad + ", accion=" + accion + ", LabUcmSeleccionado=" + LabUcmSeleccionado
				+ "]";
	}

	@Override
    public int compareTo(BeanEquipo o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getNombre());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getNombre());
		return p2.compareTo(p1);
    }		

	
}
