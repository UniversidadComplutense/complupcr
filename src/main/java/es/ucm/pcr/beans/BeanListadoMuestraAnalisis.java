package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;

public class BeanListadoMuestraAnalisis extends BeanBusquedaMuestraAnalisis {
	
	private Integer id;
	private String codNumLote;
	
	private BeanAnalisis beanAnalisis; //proceso de analizar la muestra (asignación de analistas a las muestras por parte del jefe de servicio y su resolucion)  F6y F7
	private String resultado; //valoracion final de la muestra despues de analizarla los analistas o dar su veredicto el jefe de servicio
	private Boolean esCerrable = false; //por defecto la muestra no se puede cerrar hasta que el jefe dé su veredicto o los analistas estén deacuerdo
	
	
	
	public BeanListadoMuestraAnalisis() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodNumLote() {
		return codNumLote;
	}

	public void setCodNumLote(String codNumLote) {
		this.codNumLote = codNumLote;
	}

	public BeanAnalisis getBeanAnalisis() {
		return beanAnalisis;
	}

	public void setBeanAnalisis(BeanAnalisis beanAnalisis) {
		this.beanAnalisis = beanAnalisis;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Boolean getEsCerrable() {
		return esCerrable;
	}

	public void setEsCerrable(Boolean esCerrable) {
		this.esCerrable = esCerrable;
	}

	
		

}
