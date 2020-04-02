package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;

public class BeanAnalisis {
	
	//bean en el que se recoge el proceso de analizar los resultados por parte de analistas (personal ucm o voluntarios) y jefes de servicio
	
	private String resultadoAnalisis; //Valoración final de la muestra (N,P,R,A)
	private Date fechaResultadoAnalisis;
	private BeanListaAsignaciones beanListaAsignaciones; //el jefe de servicio asigna analistas a las muestras para que las valoren
	
	
	public BeanAnalisis() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getResultadoAnalisis() {
		return resultadoAnalisis;
	}


	public void setResultadoAnalisis(String resultadoAnalisis) {
		this.resultadoAnalisis = resultadoAnalisis;
	}



	public Date getFechaResultadoAnalisis() {
		return fechaResultadoAnalisis;
	}


	public void setFechaResultadoAnalisis(Date fechaResultadoAnalisis) {
		this.fechaResultadoAnalisis = fechaResultadoAnalisis;
	}


	public BeanListaAsignaciones getBeanListaAsignaciones() {
		return beanListaAsignaciones;
	}


	public void setBeanListaAsignaciones(BeanListaAsignaciones beanListaAsignaciones) {
		this.beanListaAsignaciones = beanListaAsignaciones;
	}


	@Override
	public String toString() {
		return "BeanAnalisis [resultadoAnalisis=" + resultadoAnalisis + ", fechaResultadoAnalisis="
				+ fechaResultadoAnalisis + ", beanListaAsignaciones=" + beanListaAsignaciones + "]";
	}

	

	
		

}
