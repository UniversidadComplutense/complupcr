package es.ucm.pcr.beans;

public class BeanRol {
	
	public Integer id;
	public String rol;
	
	public BeanRol() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "BeanRol [id=" + id + ", rol=" + rol + "]";
	}
	
	
	
	

}
