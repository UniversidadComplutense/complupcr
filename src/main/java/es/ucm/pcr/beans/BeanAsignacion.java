package es.ucm.pcr.beans;

import java.util.Calendar;

public class BeanAsignacion {
	
	//asignación del usuario a la muestra
	private BeanUsuarioGestion beanUsuario; //los usuarios con rol analista, voluntario o jefe de servicio serán asignados para valorar la muestra
	private Calendar fechaAsignacion; //fecha de asignacion del usuario a la muestra
	private String valoracion; //Valoración final de la muestra (N,P,R,A)
	private Calendar fechaValoracion; //fecha valoracion
	
	
	public BeanAsignacion() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BeanUsuarioGestion getBeanUsuario() {
		return beanUsuario;
	}


	public void setBeanUsuario(BeanUsuarioGestion beanUsuario) {
		this.beanUsuario = beanUsuario;
	}


	public Calendar getFechaAsignacion() {
		return fechaAsignacion;
	}


	public void setFechaAsignacion(Calendar fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}


	public String getValoracion() {
		return valoracion;
	}


	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}


	public Calendar getFechaValoracion() {
		return fechaValoracion;
	}


	public void setFechaValoracion(Calendar fechaValoracion) {
		this.fechaValoracion = fechaValoracion;
	}


	@Override
	public String toString() {
		return "BeanAsignacion [beanUsuario=" + beanUsuario + ", fechaAsignacion=" + fechaAsignacion + ", valoracion="
				+ valoracion + ", fechaValoracion=" + fechaValoracion + "]";
	}

	
		

}
