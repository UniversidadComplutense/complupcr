package es.ucm.pcr.beans;

public class BeanBusquedaUsuario {
	
	private String busqueda;

	public BeanBusquedaUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanBusquedaUsuario(String busqueda) {
		super();
		this.busqueda = busqueda;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	@Override
	public String toString() {
		return "BeanBusquedaUsuario [busqueda=" + busqueda + "]";
	}
	
	

}
