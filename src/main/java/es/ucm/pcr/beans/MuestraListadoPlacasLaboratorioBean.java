package es.ucm.pcr.beans;

public class MuestraListadoPlacasLaboratorioBean {

	private Integer id;
	private String etiqueta;
	private String refInterna;
	private String estado;
	private Integer idPlacaVisavet;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getRefInterna() {
		return refInterna;
	}

	public void setRefInterna(String refInterna) {
		this.refInterna = refInterna;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getIdPlacaVisavet() {
		return idPlacaVisavet;
	}

	public void setIdPlacaVisavet(Integer idPlacaVisavet) {
		this.idPlacaVisavet = idPlacaVisavet;
	}

}
