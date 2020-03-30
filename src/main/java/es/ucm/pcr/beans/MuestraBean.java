package es.ucm.pcr.beans;

import java.util.Calendar;


public class MuestraBean{
	private int id;
	private String etiqueta;
	private String tipoMuestra;
	private Calendar fecha;
	private String referenciaInterna;
	private BeanEstado estado;
	
	private BeanAnalisis beanAnalisis; //proceso de analizar la muestra (asignación de analistas a las muestras por parte del jefe de servicio y su resolucion)  F6y F7
	private String resultado; //valoracion final de la muestra despues de analizarla los analistas o dar su veredicto el jefe de servicio
	
	
	public MuestraBean() {		
		
	}
	
	public MuestraBean(MuestraBean muestraBean) {
		super();
		this.id = muestraBean.getId();
		this.etiqueta = muestraBean.getEtiqueta();
		this.tipoMuestra = muestraBean.getTipoMuestra();
		this.fecha = muestraBean.getFecha();
		this.referenciaInterna = muestraBean.getReferenciaInterna();
		this.estado = muestraBean.getEstado();
		this.beanAnalisis = muestraBean.getBeanAnalisis();
		this.resultado = muestraBean.getResultado();
	}
	
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
	
	
	
	
}
