package es.ucm.pcr.beans;

import java.util.List;

public class LaboratorioCentroBean {

	private String id;
	private String nombre;
	private String universidad;
	private List<PlacaLaboratorioCentroBean> placas;
	private List<EquipoLaboratorioCentroBean> equipos;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public List<PlacaLaboratorioCentroBean> getPlacas() {
		return placas;
	}

	public void setPlacas(List<PlacaLaboratorioCentroBean> placas) {
		this.placas = placas;
	}

	public List<EquipoLaboratorioCentroBean> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<EquipoLaboratorioCentroBean> equipos) {
		this.equipos = equipos;
	}

}
