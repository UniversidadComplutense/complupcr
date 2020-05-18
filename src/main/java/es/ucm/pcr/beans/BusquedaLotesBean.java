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
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;



public class BusquedaLotesBean {
private String idLaboratorio;
private String nombreLaboratorio;
private  String numLote;
private Date fechaFinEntrada;
private Date fechaInicioEntrada;
private String muestra;
private List<BeanEstado> listaBeanEstado;
private Integer codNumEstadoSeleccionado;
private List<BeanCentro> listaCentros;
private Integer idCentro;
private String urlPaginada="/laboratorioUni/buscarLotesGet";
private String orden;
private String sentidoOrden;
private Boolean mostrarProcesar;
private String rolURL;
private Boolean mostrarGuardarRef;

public String getIdLaboratorio() {
	return idLaboratorio;
}
public void setIdLaboratorio(String idLaboratorio) {
	this.idLaboratorio = idLaboratorio;
}
public String getNombreLaboratorio() {
	return nombreLaboratorio;
}
public void setNombreLaboratorio(String nombreLaboratorio) {
	this.nombreLaboratorio = nombreLaboratorio;
}
public String getNumLote() {
	return numLote;
}
public void setNumLote(String numLote) {
	this.numLote = numLote;
}
public Date getFechaInicioEntrada() {
	return fechaInicioEntrada;
}
public void setFechaInicioEntrada(Date fechaInicioEntrada) {
	this.fechaInicioEntrada = fechaInicioEntrada;
}
public Date getFechaFinEntrada() {
	return fechaFinEntrada;
}
public void setFechaFinEntrada(Date fechaFinEntrada) {
	this.fechaFinEntrada = fechaFinEntrada;
}
public String getMuestra() {
	return muestra;
}
public void setMuestra(String muestra) {
	this.muestra = muestra;
}
public List<BeanEstado> getListaBeanEstado() {
	return listaBeanEstado;
}
public void setListaBeanEstado(List<BeanEstado> listaBeanEstado) {
	this.listaBeanEstado = listaBeanEstado;
}
public Integer getCodNumEstadoSeleccionado() {
	return codNumEstadoSeleccionado;
}
public void setCodNumEstadoSeleccionado(Integer codNumEstadoSeleccionado) {
	this.codNumEstadoSeleccionado = codNumEstadoSeleccionado;
}
public List<BeanCentro> getListaCentros() {
	return listaCentros;
}
public void setListaCentros(List<BeanCentro> list) {
	this.listaCentros = list;
}
public Integer getIdCentro() {
	return idCentro;
}
public void setIdCentro(Integer idCentro) {
	this.idCentro = idCentro;
}
public String getUrlPaginada() {
	return urlPaginada;
}
public void setUrlPaginada(String urlPaginada) {
	this.urlPaginada = urlPaginada;
}
public String getOrden() {
	return orden;
}
public void setOrden(String orden) {
	this.orden = orden;
}
public String getSentidoOrden() {
	return sentidoOrden;
}
public void setSentidoOrden(String sentidoOrden) {
	this.sentidoOrden = sentidoOrden;
}


public Boolean getMostrarProcesar() {
	return mostrarProcesar;
}
public void setMostrarProcesar(Boolean mostrarProcesar) {
	this.mostrarProcesar = mostrarProcesar;
}
public String getCriterioNumLote() {
	return StringUtils.isBlank(numLote) ? null : "%" + numLote + "%";
}
public String getRolURL() {
	return rolURL;
}
public void setRolURL(String rolURL) {
	this.rolURL = rolURL;
}
public Boolean getMostrarGuardarRef() {
	return mostrarGuardarRef;
}
public void setMostrarGuardarRef(Boolean mostrarGuardarRef) {
	this.mostrarGuardarRef = mostrarGuardarRef;
}

}
