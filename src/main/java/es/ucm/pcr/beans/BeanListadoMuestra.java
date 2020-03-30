package es.ucm.pcr.beans;

public class BeanListadoMuestra extends BeanBusquedaMuestra {

	private Integer id;
	private String codNumLote;

	public BeanListadoMuestra() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodNumLote() {
		return codNumLote;
	}

	public void setCodNumLote(String codNumLote) {
		this.codNumLote = codNumLote;
	}

}
