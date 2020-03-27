package es.ucm.pcr.beans;

public class DocumentoBean {

	private Integer id;
	private String tipoDocumento;
	private String nombreDocumento;
	private String tamanioDocumento;
	private String tipoContenido;
	private byte[] elDocumento;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getTamanioDocumento() {
		return tamanioDocumento;
	}

	public void setTamanioDocumento(String tamanioDocumento) {
		this.tamanioDocumento = tamanioDocumento;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public byte[] getElDocumento() {
		return elDocumento;
	}

	public void setElDocumento(byte[] elDocumento) {
		this.elDocumento = elDocumento;
	}

}
