package es.ucm.pcr.beans;

public class BeanElemento {
	private int codigo;
	private String codigoString;
	private String descripcion;

	public BeanElemento() {

	}

	

	public BeanElemento(int codigo, String codigoString, String descripcion) {
		super();
		this.codigo = codigo;
		this.codigoString = codigoString;
		this.descripcion = descripcion;
	}



	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
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

