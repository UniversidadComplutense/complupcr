package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;



public class BusquedaLotesBean {
private String idLaboratorio;
private String nombreLaboratorio;
private  String numLote;
private Date fechaEntrada;
private String muestra;
private List<BeanEstado> listaBeanEstado;
private Integer codNumEstadoSeleccionado;
private List<BeanCentro> listaCentros;
private Integer idCentro;
private String urlPaginada="/laboratorioUni/buscarLotesGet";
private String orden;
private String sentidoOrden;

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
public Date getFechaEntrada() {
	return fechaEntrada;
}
public void setFechaEntrada(Date fechaEntrada) {
	this.fechaEntrada = fechaEntrada;
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
public String getCriterioNumLote() {
	return StringUtils.isBlank(numLote) ? null : "%" + numLote + "%";
}
}
