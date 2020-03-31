package es.ucm.pcr.beans;

import java.util.List;
// lo voy a usar para empezar a asignar lotes a placas visavet pero sin haber confirmado aún
public class LotePlacaVisavetBean {
private List<Integer> listaTamanosDisponibles;
private List<LoteBeanPlacaVisavet> listaLotesDisponibles;
private int totalMuestras;
private BeanPlacaVisavetUCM placa;
private String accion;
public List<Integer> getListaTamanosDisponibles() {
	return listaTamanosDisponibles;
}

public void setListaTamanosDisponibles(List<Integer> listaTamanosDisponibles) {
	this.listaTamanosDisponibles = listaTamanosDisponibles;
}

public BeanPlacaVisavetUCM getPlaca() {
	return placa;
}

public void setPlaca(BeanPlacaVisavetUCM listaPlacas) {
	this.placa = listaPlacas;
}

public List<LoteBeanPlacaVisavet> getListaLotesDisponibles() {
	return listaLotesDisponibles;
}

public void setListaLotesDisponibles(List<LoteBeanPlacaVisavet> listaLotesDisponibles) {
	this.listaLotesDisponibles = listaLotesDisponibles;
}

public int getTotalMuestras() {
	return totalMuestras;
}

public void setTotalMuestras(int totalMuestras) {
	this.totalMuestras = totalMuestras;
}

public String getAccion() {
	return accion;
}

public void setAccion(String accion) {
	this.accion = accion;
}


}
