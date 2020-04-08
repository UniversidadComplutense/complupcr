package es.ucm.pcr.beans;

import java.util.Date;

public class LogMuestraListadoBean {

	private Integer id;
	private String descMuestra;
	private String descLote;
	private String descCentroSalud;
	private String descPaciente;
	private String nhcPaciente;
	private String descEstadoMuestra;
	private Date fechaCambio;
	private String nombreAutorCambio;

	public LogMuestraListadoBean() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescMuestra() {
		return descMuestra;
	}

	public void setDescMuestra(String descMuestra) {
		this.descMuestra = descMuestra;
	}

	public String getDescLote() {
		return descLote;
	}

	public void setDescLote(String descLote) {
		this.descLote = descLote;
	}

	public String getDescCentroSalud() {
		return descCentroSalud;
	}

	public void setDescCentroSalud(String descCentroSalud) {
		this.descCentroSalud = descCentroSalud;
	}

	public String getDescPaciente() {
		return descPaciente;
	}

	public void setDescPaciente(String descPaciente) {
		this.descPaciente = descPaciente;
	}

	public String getNhcPaciente() {
		return nhcPaciente;
	}

	public void setNhcPaciente(String nhcPaciente) {
		this.nhcPaciente = nhcPaciente;
	}

	public String getDescEstadoMuestra() {
		return descEstadoMuestra;
	}

	public void setDescEstadoMuestra(String descEstadoMuestra) {
		this.descEstadoMuestra = descEstadoMuestra;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getNombreAutorCambio() {
		return nombreAutorCambio;
	}

	public void setNombreAutorCambio(String nombreAutorCambio) {
		this.nombreAutorCambio = nombreAutorCambio;
	}

}
