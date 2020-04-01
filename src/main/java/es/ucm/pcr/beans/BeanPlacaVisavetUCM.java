package es.ucm.pcr.beans;

import java.util.List;

public class BeanPlacaVisavetUCM {
// aunque es List suponemos que una placa solo contiene un lote
private List<LoteBeanPlacaVisavet> listaLotes;
private String id;
private String tamano;
//private Date fechaCreacion;
//private BeanEstado 
//private List<Integer> listaTamanosDisponibles;
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

}
