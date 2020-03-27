package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;

public class BeanListadoMuestraAnalisis extends BeanBusquedaMuestraAnalisis {
	
	private Integer id;
	private String codNumLote;

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

	
		

}
