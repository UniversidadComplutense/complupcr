package es.ucm.pcr.beans;

import java.util.List;

public class EquipoLaboratorioCentroBean {

	private String id;
	private String nombre;
	private String universidad;
	private List<PlacaLaboratorioCentroBean> placasInternas;
	private List<PlacaLaboratorioVisavetBean> placasRecibidas;
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

	public List<PlacaLaboratorioCentroBean> getPlacasInternas() {
		return placasInternas;
	}

	public void setPlacasInternas(List<PlacaLaboratorioCentroBean> placasInternas) {
		this.placasInternas = placasInternas;
	}

	public List<PlacaLaboratorioVisavetBean> getPlacasRecibidas() {
		return placasRecibidas;
	}

	public void setPlacasRecibidas(List<PlacaLaboratorioVisavetBean> placasRecibidas) {
		this.placasRecibidas = placasRecibidas;
	}

	public List<EquipoLaboratorioCentroBean> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<EquipoLaboratorioCentroBean> equipos) {
		this.equipos = equipos;
	}

}
