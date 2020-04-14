package es.ucm.pcr.beans;

import org.apache.commons.lang.StringUtils;

import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.utilidades.Utilidades;

public class BeanUsuario implements Comparable<BeanUsuario>{

	public Integer id;
	public String nom;
	public String ap1;
	public String ap2;
	public String nombreCompleto;	
	public String mail;	
	
	public BeanRolUsuario beanRolUsuario; //rol con el que está actuando el usuario
	
	
	public BeanUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BeanUsuario(Integer id, String nom, String ap1, String ap2, String nombreCompleto, String mail,
			BeanRolUsuario beanRolUsuario) {
		super();
		this.id = id;
		this.nom = nom;
		this.ap1 = ap1;
		this.ap2 = ap2;
		this.nombreCompleto = nombreCompleto;
		this.mail = mail;
		this.beanRolUsuario = beanRolUsuario;
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

	
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}


	public BeanRolUsuario getBeanRolUsuario() {
		return beanRolUsuario;
	}


	public void setBeanRolUsuario(BeanRolUsuario beanRolUsuario) {
		this.beanRolUsuario = beanRolUsuario;
	}


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ap1 == null) ? 0 : ap1.hashCode());
		result = prime * result + ((ap2 == null) ? 0 : ap2.hashCode());
		result = prime * result + ((beanRolUsuario == null) ? 0 : beanRolUsuario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((nombreCompleto == null) ? 0 : nombreCompleto.hashCode());
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
		if (beanRolUsuario == null) {
			if (other.beanRolUsuario != null)
				return false;
		} else if (!beanRolUsuario.equals(other.beanRolUsuario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (nombreCompleto == null) {
			if (other.nombreCompleto != null)
				return false;
		} else if (!nombreCompleto.equals(other.nombreCompleto))
			return false;
		return true;
	}





	@Override
	public String toString() {
		return "BeanUsuario [id=" + id + ", nom=" + nom + ", ap1=" + ap1 + ", ap2=" + ap2 + ", nombreCompleto="
				+ nombreCompleto + ", mail=" + mail + ", beanRolUsuario=" + beanRolUsuario + "]";
	}


	@Override
    public int compareTo(BeanUsuario o) {
		String p1 = o.getAp1() + o.getAp2() + o.getNom();
		String p2 = getAp1() + getAp2() + getNom();
        String l1 = Utilidades.limpiarStringParaOrdenacion(p1);
        String l2 = Utilidades.limpiarStringParaOrdenacion(p2);
		return l2.compareTo(l1);
    }
	
	private static String nombreCompletoUsuario(Usuario usu) {
		String nombreCompleto = usu.getNombre();
		if (StringUtils.isNotEmpty(usu.getApellido1())) {
			nombreCompleto = nombreCompleto.concat(" ").concat(usu.getApellido1());
		}
		if (StringUtils.isNotEmpty(usu.getApellido2())) {
			nombreCompleto = nombreCompleto.concat(" ").concat(usu.getApellido2());
		}
		return nombreCompleto;
	}
	
	public static BeanUsuario modelToBean(Usuario usu) {
		
		BeanUsuario beanUsuario = new BeanUsuario();
		beanUsuario.setId(usu.getId());
		beanUsuario.setNom(usu.getNombre());
		beanUsuario.setAp1(usu.getApellido1());
		beanUsuario.setAp2(usu.getApellido2());
		beanUsuario.setNombreCompleto(nombreCompletoUsuario(usu));		
		beanUsuario.setMail(usu.getEmail());	
		//beanUsuario.setBeanRolUsuario(beanRolUsuario); //el rol con el que actua depende de dónde lo llamemos, se le asignará despues
		return beanUsuario;		
		
	}
}
