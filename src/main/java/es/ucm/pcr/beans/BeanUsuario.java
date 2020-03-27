package es.ucm.pcr.beans;

import es.ucm.pcr.utilidades.Utilidades;

public class BeanUsuario implements Comparable<BeanUsuario>{

	public Integer id;
	public String nom;
	public String ap1;
	public String ap2;
	public String telFijo;
	public String telMovil;
	public String mail;
	public String internoExterno;
	public String rol;
	
	
	public BeanUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}



	public BeanUsuario(Integer id, String nom, String ap1, String ap2, String telFijo, String telMovil, String mail,
			String internoExterno, String rol) {
		super();
		this.id = id;
		this.nom = nom;
		this.ap1 = ap1;
		this.ap2 = ap2;
		this.telFijo = telFijo;
		this.telMovil = telMovil;
		this.mail = mail;
		this.internoExterno = internoExterno;
		this.rol = rol;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
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
		return "BeanUsuario [id=" + id + ", nom=" + nom + ", ap1=" + ap1 + ", ap2=" + ap2 + ", telFijo=" + telFijo
				+ ", telMovil=" + telMovil + ", mail=" + mail + ", internoExterno=" + internoExterno + ", rol=" + rol
				+ "]";
	}

    @Override
    public int compareTo(BeanUsuario o) {
		String p1 = o.getAp1() + o.getAp2() + o.getNom();
		String p2 = getAp1() + getAp2() + getNom();
        String l1 = Utilidades.limpiarStringParaOrdenacion(p1);
        String l2 = Utilidades.limpiarStringParaOrdenacion(p2);
		return l2.compareTo(l1);
    }		

}
