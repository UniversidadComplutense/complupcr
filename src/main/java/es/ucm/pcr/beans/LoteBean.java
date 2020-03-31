package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoteBean{
	private String id;
	private String numLote;
	private int capacidad;
	private int ocupacion;
	private BeanEstado estado;
	private Calendar fechaEntrada;
	private List<MuestraBean>  listaMuestras;
	private CentroBean centroProcedencia;
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
	public Calendar getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public List<MuestraBean> getListaMuestras() {
		return listaMuestras;
	}
	public void setListaMuestras(List<MuestraBean> listaMuestras) {
		this.listaMuestras = listaMuestras;
	}
	public CentroBean getCentroProcedencia() {
		return centroProcedencia;
	}
	public void setCentroProcedencia(CentroBean centroProcedencia) {
		this.centroProcedencia = centroProcedencia;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	
}
