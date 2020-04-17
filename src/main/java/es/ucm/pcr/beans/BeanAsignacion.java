package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;

public class BeanAsignacion {
	
	//asignación del usuario a la muestra
	private BeanUsuario beanUsuario; //los usuarios con rol analista, voluntario o jefe de servicio serán asignados para valorar la muestra
	private Date fechaAsignacion; //fecha de asignacion del usuario a la muestra
	private String valoracion; //Valoración final de la muestra (N,P,R,A)
	private Date fechaValoracion; //fecha valoracion
	//una asignacion será reemplazable si el usuario no ha valorado aun la muestra (podremos reemplazar el analista por otro)
	private Boolean esReemplazable = true; //podremos reemplazar al analista por otro si aun no hay valoración ni fecha de valoración (por defecto son reemplazables)
	
	
	public BeanAsignacion() {
		super();
		// TODO Auto-generated constructor stub
	}


	public BeanUsuario getBeanUsuario() {
		return beanUsuario;
	}


	public void setBeanUsuario(BeanUsuario beanUsuario) {
		this.beanUsuario = beanUsuario;
	}


	

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}


	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}



	public String getValoracion() {
		return valoracion;
	}


	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}


	public Date getFechaValoracion() {
		return fechaValoracion;
	}

	public void setFechaValoracion(Date fechaValoracion) {
		this.fechaValoracion = fechaValoracion;
	}

	
	public Boolean getEsReemplazable() {
		return esReemplazable;
	}


	public void setEsReemplazable(Boolean esReemplazable) {
		this.esReemplazable = esReemplazable;
	}


	@Override
	public String toString() {
		return "BeanAsignacion [beanUsuario=" + beanUsuario + ", fechaAsignacion=" + fechaAsignacion + ", valoracion="
				+ valoracion + ", fechaValoracion=" + fechaValoracion + "]";
	}

	

	
		

}
