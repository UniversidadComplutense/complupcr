package es.ucm.pcr.beans;

import java.util.Calendar;

public class MuestraBean{
	private int id;
	private String etiqueta;
	private String tipoMuestra;
	private Calendar fecha;
	private String referenciaInterna;
	private BeanEstado estado;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getTipoMuestra() {
		return tipoMuestra;
	}
	public void setTipoMuestra(String tipoMuestra) {
		this.tipoMuestra = tipoMuestra;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public String getReferenciaInterna() {
		return referenciaInterna;
	}
	public void setReferenciaInterna(String referenciaInterna) {
		this.referenciaInterna = referenciaInterna;
	}
	public BeanEstado getEstado() {
		return estado;
	}
	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}
	
	
}
