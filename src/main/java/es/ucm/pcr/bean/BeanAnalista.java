package es.ucm.pcr.bean;

public class BeanAnalista {
	
	public Integer idAnalista;
	public String nom;
	public String ap1;
	public String ap2;
	public String telFijo;
	public String telMovil;
	public String mail;
	public String internoExterno;
	public String rol;
	
	public BeanAnalista() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdAnalista() {
		return idAnalista;
	}

	public void setIdAnalista(Integer idAnalista) {
		this.idAnalista = idAnalista;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAp1() {
		return ap1;
	}

	public void setAp1(String ap1) {
		this.ap1 = ap1;
	}

	public String getAp2() {
		return ap2;
	}

	public void setAp2(String ap2) {
		this.ap2 = ap2;
	}

	public String getTelFijo() {
		return telFijo;
	}

	public void setTelFijo(String telFijo) {
		this.telFijo = telFijo;
	}

	public String getTelMovil() {
		return telMovil;
	}

	public void setTelMovil(String telMovil) {
		this.telMovil = telMovil;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getInternoExterno() {
		return internoExterno;
	}

	public void setInternoExterno(String internoExterno) {
		this.internoExterno = internoExterno;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "BeanAnalista [idAnalista=" + idAnalista + ", nom=" + nom + ", ap1=" + ap1 + ", ap2=" + ap2
				+ ", telFijo=" + telFijo + ", telMovil=" + telMovil + ", mail=" + mail + ", internoExterno="
				+ internoExterno + ", rol=" + rol + "]";
	}
	
	
	
	

}
