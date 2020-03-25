package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.List;



public class BusquedaLotesBean {
private String idLaboratorio;
private String nombreLaboratorio;
private  String numLote;
private Calendar fechaEntrada;
private String muestra;
private List<BeanEstado> listaBeanEstado;
private String codNumEstadoSeleccionado;
private List<CentroBean> listaCentros;
private int idCentro;
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
public Calendar getFechaEntrada() {
	return fechaEntrada;
}
public void setFechaEntrada(Calendar fechaEntrada) {
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

}
