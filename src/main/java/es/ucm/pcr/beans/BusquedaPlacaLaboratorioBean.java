package es.ucm.pcr.beans;

import java.util.Date;
import java.util.List;

/**
 * @author desarrollo
 *
 */
public class BusquedaPlacaLaboratorioBean {

	private Integer idPlaca;
	private String numeroMuestras;
	private Integer idEstadoPlaca;
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;
	private Integer idLaboratorioCentro;
	private List<Integer> estadosBusqueda;

	
	public Integer getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}

	public String getNumeroMuestras() {
		return numeroMuestras;
	}

	public void setNumeroMuestras(String numeroMuestras) {
		this.numeroMuestras = numeroMuestras;
	}

	public Integer getIdEstadoPlaca() {
		return idEstadoPlaca;
	}

	public void setIdEstadoPlaca(Integer idEstadoPlaca) {
		this.idEstadoPlaca = idEstadoPlaca;
	}

	public Date getFechaCreacionInicio() {
		return fechaCreacionInicio;
	}

	public void setFechaCreacionInicio(Date fechaCreacionInicio) {
		this.fechaCreacionInicio = fechaCreacionInicio;
	}

	public Date getFechaCreacionFin() {
		return fechaCreacionFin;
	}

	public void setFechaCreacionFin(Date fechaCreacionFin) {
		this.fechaCreacionFin = fechaCreacionFin;
	}

	public Integer getIdLaboratorioCentro() {
		return idLaboratorioCentro;
	}

	public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
		this.idLaboratorioCentro = idLaboratorioCentro;
	}

	public List<Integer> getEstadosBusqueda() {
		return estadosBusqueda;
	}

	public void setEstadosBusqueda(List<Integer> estadosBusqueda) {
		this.estadosBusqueda = estadosBusqueda;
	}

}
