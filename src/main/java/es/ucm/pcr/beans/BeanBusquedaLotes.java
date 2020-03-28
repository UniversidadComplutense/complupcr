package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class BeanBusquedaLotes {
private String idLaboratorio;
private String nombreLaboratorio;
private  String numLote;
private String fechaEntrada;
private String muestra;
private List<BeanElemento> listaBeanEstado;
private String codNumEstadoSeleccionado;
private List<CentroBean> listaCentros;
private int idCentro;
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
public String getFechaEntrada() {
	return fechaEntrada;
}
public void setFechaEntrada(String fechaEntrada) {
	this.fechaEntrada = fechaEntrada;
}
public String getMuestra() {
	return muestra;
}
public void setMuestra(String muestra) {
	this.muestra = muestra;
}
public List<BeanElemento> getListaBeanEstado() {
	return listaBeanEstado;
}
public void setListaBeanEstado(List<BeanElemento> listaBeanEstado) {
	this.listaBeanEstado = listaBeanEstado;
}
public String getCodNumEstadoSeleccionado() {
	return codNumEstadoSeleccionado;
}
public void setCodNumEstadoSeleccionado(String codNumEstadoSeleccionado) {
	this.codNumEstadoSeleccionado = codNumEstadoSeleccionado;
}
public List<CentroBean> getListaCentros() {
	return listaCentros;
}
public void setListaCentros(List<CentroBean> listaCentros) {
	this.listaCentros = listaCentros;
}
public int getIdCentro() {
	return idCentro;
}
public void setIdCentro(int idCentro) {
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

}
