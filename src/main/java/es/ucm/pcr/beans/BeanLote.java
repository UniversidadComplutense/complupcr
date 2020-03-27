package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class BeanLote{
	private String id;
	private String numLote;
	private String centroProcedencia;
	private int capacidad;
	private int ocupacion;
	private BeanEstado estado;
	private Date fechaEntrada;
	private List<BeanMuestra>  listaMuestras;
	
	private String test;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumLote() {
		return numLote;
	}
	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public int getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(int ocupacion) {
		this.ocupacion = ocupacion;
	}
	public BeanEstado getEstado() {
		return estado;
	}
	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}
	
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public List<BeanMuestra> getListaMuestras() {
		return listaMuestras;
	}
	public void setListaMuestras(List<BeanMuestra> listaMuestras) {
		this.listaMuestras = listaMuestras;
	}
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getCentroProcedencia() {
		return centroProcedencia;
	}
	public void setCentroProcedencia(String centroProcedencia) {
		this.centroProcedencia = centroProcedencia;
	}
	
}
