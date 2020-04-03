package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class BusquedaPlacasVisavetBean {
private Integer idPlaca;
private  String numLote;
private Date fechaCreacion;
private String muestra;
private List<BeanEstado> listaBeanEstado;
private Integer codNumEstadoSeleccionado;
private List<BeanLaboratorioCentro> listaLaboratorioCentro;
private Integer idLaboratorioCentro;
private Integer idLaboratorioVisavet;
public Integer getIdPlaca() {
	return idPlaca;
}
public void setIdPlaca(Integer idPlaca) {
	this.idPlaca = idPlaca;
}
public String getNumLote() {
	return numLote;
}
public void setNumLote(String numLote) {
	this.numLote = numLote;
}
public Date getFechaCreacion() {
	return fechaCreacion;
}
public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
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
public List<BeanLaboratorioCentro> getListaLaboratorioCentro() {
	return listaLaboratorioCentro;
}
public void setListaLaboratorioCentro(List<BeanLaboratorioCentro> listaLaboratorioCentro) {
	this.listaLaboratorioCentro = listaLaboratorioCentro;
}
public Integer getIdLaboratorioCentro() {
	return idLaboratorioCentro;
}
public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
	this.idLaboratorioCentro = idLaboratorioCentro;
}
public Integer getIdLaboratorioVisavet() {
	return idLaboratorioVisavet;
}
public void setIdLaboratorioVisavet(Integer idLaboratorioVisavet) {
	this.idLaboratorioVisavet = idLaboratorioVisavet;
}


}
