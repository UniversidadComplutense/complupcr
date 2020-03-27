package es.ucm.pcr.beans;

import es.uc.pcr.utilidades.Utilidades;

public class BeanCentro implements Comparable<BeanCentro> {
	
	public Integer idCentro;
	public String codCentro;
	public String desCentro;
	public String telefonoCentro;
	public String responsableCentro;
	public String telefonoResponsableCentro;
	public String mailCentro;
	public String accion; // A: ALTA, M: MODIFICAR, L: EN LA LISTA
	
	public BeanCentro() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public BeanCentro(Integer idCentro, String codCentro, String desCentro, String telefonoCentro,
			String responsableCentro, String telefonoResponsableCentro, String mailCentro, String accion) {
		super();
		this.idCentro = idCentro;
		this.codCentro = codCentro;
		this.desCentro = desCentro;
		this.telefonoCentro = telefonoCentro;
		this.responsableCentro = responsableCentro;
		this.telefonoResponsableCentro = telefonoResponsableCentro;
		this.mailCentro = mailCentro;
		this.accion = accion;
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
	public String getMailCentro() {
		return mailCentro;
	}
	public void setMailCentro(String mailCentro) {
		this.mailCentro = mailCentro;
	}



	public String getAccion() {
		return accion;
	}



	public void setAccion(String accion) {
		this.accion = accion;
	}



	@Override
	public String toString() {
		return "BeanCentro [idCentro=" + idCentro + ", codCentro=" + codCentro + ", desCentro=" + desCentro
				+ ", telefonoCentro=" + telefonoCentro + ", responsableCentro=" + responsableCentro
				+ ", telefonoResponsableCentro=" + telefonoResponsableCentro + ", mailCentro=" + mailCentro
				+ ", accion=" + accion + "]";
	}
	@Override
    public int compareTo(BeanCentro o) {
		String p1 = Utilidades.limpiarStringParaOrdenacion(o.getDesCentro());
		String p2 = Utilidades.limpiarStringParaOrdenacion(getDesCentro());
		return p2.compareTo(p1);
    }		

}
