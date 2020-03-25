package es.ucm.pcr.bean;

public class BeanCentro {
	
	public Integer idCentro;
	public String codCentro;
	public String desCentro;
	public String telefonoCentro;
	public String responsableCentro;
	public String telefonoResponsableCentro;
	
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
	public String getTelefonoCentro() {
		return telefonoCentro;
	}
	public void setTelefonoCentro(String telefonoCentro) {
		this.telefonoCentro = telefonoCentro;
	}
	public String getResponsableCentro() {
		return responsableCentro;
	}
	public void setResponsableCentro(String responsableCentro) {
		this.responsableCentro = responsableCentro;
	}
	public String getTelefonoResponsableCentro() {
		return telefonoResponsableCentro;
	}
	public void setTelefonoResponsableCentro(String telefonoResponsableCentro) {
		this.telefonoResponsableCentro = telefonoResponsableCentro;
	}
	@Override
	public String toString() {
		return "BeanCentro [idCentro=" + idCentro + ", codCentro=" + codCentro + ", desCentro=" + desCentro
				+ ", telefonoCentro=" + telefonoCentro + ", responsableCentro=" + responsableCentro
				+ ", telefonoResponsableCentro=" + telefonoResponsableCentro + "]";
	}

}
