package es.ucm.pcr.beans;

import java.util.List;

public class BeanPlacaVisavetUCM {
// aunque es List suponemos que una placa solo contiene un lote
private List<BeanLote> listaLotes;
private String id;
private String tamano;
//private List<Integer> listaTamanosDisponibles;
public List<BeanLote> getListaLotes() {
	return listaLotes;
}
public void setListaLotes(List<BeanLote> listaLotes) {
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
