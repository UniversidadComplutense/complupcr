package es.ucm.pcr.beans;

import java.util.Date;
import java.util.List;

public class BeanPlacaVisavetUCM {
// aunque es List suponemos que una placa solo contiene un lote
private List<LoteBeanPlacaVisavet> listaLotes;
private String id;
private String tamano;
private Date fechaCreacion;
private BeanEstado estado;
private Integer idLaboratorioCentro;
public List<LoteBeanPlacaVisavet> getListaLotes() {
	return listaLotes;
}
public void setListaLotes(List<LoteBeanPlacaVisavet> listaLotes) {
	this.listaLotes = listaLotes;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getTamano() {
	return tamano;
}
public void setTamano(String tamano) {
	this.tamano = tamano;
}
public Date getFechaCreacion() {
	return fechaCreacion;
}
public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
}
public BeanEstado getEstado() {
	return estado;
}
public void setEstado(BeanEstado estado) {
	this.estado = estado;
}
public Integer getIdLaboratorioCentro() {
	return idLaboratorioCentro;
}
public void setIdLaboratorioCentro(Integer idLaboratorioCentro) {
	this.idLaboratorioCentro = idLaboratorioCentro;
}

}
