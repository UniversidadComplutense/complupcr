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

import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;

public class MuestraBeanLaboratorioVisavet{
	private int id;
	private String etiqueta;
	private String tipoMuestra;
	private String descripcionMuestra;
	private Calendar fecha;
	private String referenciaInterna;
	private BeanEstado estado;
	private BeanCentro centro;
	private BeanAnalisis beanAnalisis; //proceso de analizar la muestra (asignación de analistas a las muestras por parte del jefe de servicio y su resolucion)  F6y F7
	
	
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
	public BeanCentro getCentro() {
		return centro;
	}
	public void setCentro(BeanCentro centro) {
		this.centro = centro;
	}
	
	
	public String getDescripcionMuestra() {
		return descripcionMuestra;
	}
	public void setDescripcionMuestra(String descripcionMuestra) {
		this.descripcionMuestra = descripcionMuestra;
	}
	public static MuestraBeanLaboratorioVisavet modelToBean(Muestra muestra) {
	//
	
	MuestraBeanLaboratorioVisavet muestraBeanLaboratorioVisavet = new MuestraBeanLaboratorioVisavet();
    muestraBeanLaboratorioVisavet.setId(muestra.getId());
    muestraBeanLaboratorioVisavet.setEtiqueta(muestra.getEtiqueta());
    muestraBeanLaboratorioVisavet.setTipoMuestra(muestra.getTipoMuestra());
    muestraBeanLaboratorioVisavet.setReferenciaInterna(muestra.getRefInternaVisavet());
    
    
    return muestraBeanLaboratorioVisavet;
	//
	}
	
	public static Muestra beanToModel(MuestraBeanLaboratorioVisavet muestra) {
		//
		
		Muestra muestraT = new Muestra();
		muestraT.setId(muestra.getId());
		muestraT.setEtiqueta(muestra.getEtiqueta());
		muestraT.setTipoMuestra(muestra.getTipoMuestra());
		muestraT.setRefInternaVisavet(muestra.getReferenciaInterna());
	    return muestraT;
		//
		}
	@Override
	public String toString() {
		return "MuestraBeanLaboratorioVisavet [id=" + id + ", etiqueta=" + etiqueta + ", tipoMuestra=" + tipoMuestra
				+ ", descripcionMuestra=" + descripcionMuestra + ", fecha=" + fecha + ", referenciaInterna="
				+ referenciaInterna + ", estado=" + estado + ", centro=" + centro + ", beanAnalisis=" + beanAnalisis
				+ "]";
	}
	
	
}
