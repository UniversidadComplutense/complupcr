package es.ucm.pcr.beans;

import java.util.List;

public class PlacaLaboratorioVisavetBean {

	private String id;
	private String capacidad;
	private BeanEstado BeanEstado;
	private List<LoteBean> lotes;
	private List<DocumentoBean> documentos;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public BeanEstado getBeanEstado() {
		return BeanEstado;
	}

	public void setBeanEstado(BeanEstado beanEstado) {
		BeanEstado = beanEstado;
	}

	public List<LoteBean> getLotes() {
		return lotes;
	}

	public void setLotes(List<LoteBean> lotes) {
		this.lotes = lotes;
	}

	public List<DocumentoBean> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoBean> documentos) {
		this.documentos = documentos;
	}

}
