package es.ucm.pcr.beans;

public class LoteCentroBean {
	private Integer id;
	private String numLote;
	private BeanEstado estado;
	private CentroBean centroProcedencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}

	public BeanEstado getEstado() {
		return estado;
	}

	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}

	public CentroBean getCentroProcedencia() {
		return centroProcedencia;
	}

	public void setCentroProcedencia(CentroBean centroProcedencia) {
		this.centroProcedencia = centroProcedencia;
	}

}
