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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ap1 == null) ? 0 : ap1.hashCode());
		result = prime * result + ((ap2 == null) ? 0 : ap2.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((internoExterno == null) ? 0 : internoExterno.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		result = prime * result + ((telFijo == null) ? 0 : telFijo.hashCode());
		result = prime * result + ((telMovil == null) ? 0 : telMovil.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanUsuario other = (BeanUsuario) obj;
		if (ap1 == null) {
			if (other.ap1 != null)
				return false;
		} else if (!ap1.equals(other.ap1))
			return false;
		if (ap2 == null) {
			if (other.ap2 != null)
				return false;
		} else if (!ap2.equals(other.ap2))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internoExterno == null) {
			if (other.internoExterno != null)
				return false;
		} else if (!internoExterno.equals(other.internoExterno))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		if (telFijo == null) {
			if (other.telFijo != null)
				return false;
		} else if (!telFijo.equals(other.telFijo))
			return false;
		if (telMovil == null) {
			if (other.telMovil != null)
				return false;
		} else if (!telMovil.equals(other.telMovil))
			return false;
		return true;
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
