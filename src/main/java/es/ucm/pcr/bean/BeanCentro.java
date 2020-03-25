package es.ucm.pcr.bean;

public class BeanCentro {
	
	public Integer idCentro;
	public String codCentro;
	public String desCentro;
	
	public BeanCentro() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}
	public String getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(String codCentro) {
		this.codCentro = codCentro;
	}
	public String getDesCentro() {
		return desCentro;
	}
	public void setDesCentro(String desCentro) {
		this.desCentro = desCentro;
	}
	@Override
	public String toString() {
		return "BeanCentro [idCentro=" + idCentro + ", codCentro=" + codCentro + ", desCentro=" + desCentro + "]";
	}

}
