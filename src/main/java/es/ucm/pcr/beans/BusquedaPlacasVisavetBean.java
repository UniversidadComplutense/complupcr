package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class BusquedaPlacasVisavetBean {
private String idPlaca;
private  String numLote;
private String fechaCreacion;
private String muestra;
private List<BeanElemento> listaBeanEstado;
private String codNumEstadoSeleccionado;
private List<CentroBean> listaLaboratorioCentro;
private String idLaboratorioCentro;
public String getIdPlaca() {
	return idPlaca;
}
public void setIdPlaca(String idPlaca) {
	this.idPlaca = idPlaca;
}
public String getNumLote() {
	return numLote;
}
public void setNumLote(String numLote) {
	this.numLote = numLote;
}
public String getFechaCreacion() {
	return fechaCreacion;
}
public void setFechaCreacion(String fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
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
public List<CentroBean> getListaLaboratorioCentro() {
	return listaLaboratorioCentro;
}
public void setListaLaboratorioCentro(List<CentroBean> listaLaboratorioCentro) {
	this.listaLaboratorioCentro = listaLaboratorioCentro;
}
public String getIdLaboratorioCentro() {
	return idLaboratorioCentro;
}
public void setIdLaboratorioCentro(String idLaboratorioCentro) {
	this.idLaboratorioCentro = idLaboratorioCentro;
}


}
