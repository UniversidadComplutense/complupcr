package es.ucm.pcr.beans;

public class BeanElemento {
	private Integer codigo;
	private String codigoString;
	private String descripcion;

	public BeanElemento() {

	}

	

	public BeanElemento(Integer codigo, String codigoString, String descripcion) {
		super();
		this.codigo = codigo;
		this.codigoString = codigoString;
		this.descripcion = descripcion;
	}



	
	
	
	public Integer getCodigo() {
		return codigo;
	}



	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}



	public String getCodigoString() {
		return codigoString;
	}



	public void setCodigoString(String codigoString) {
		this.codigoString = codigoString;
	}



	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	@Override
	public String toString() {
		return "BeanElemento [codigo=" + codigo + ", codigoString=" + codigoString + ", descripcion=" + descripcion
				+ "]";
	}

}

