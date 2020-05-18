/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/


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
	
	public static String traducirTipoMuestra(String tipoMuestra) {
		switch (tipoMuestra.toUpperCase()) {
		case "MC":return "Medio de Cultivo";
		case "OT": return "Otros";
		case "N": return "Hisopo nasofaríngeo";
		case "E": return "Esputo";
		case "NB": return "Hisopo Nasofaríngeo y Orofaríngeo";
		case "B": return "Hisopo Orofaríngeo";		
		}
		return "Desconocido";
	}
	
	
	
	
}
