package es.ucm.pcr.beans;

import java.util.List;
// lo voy a usar para empezar a asignar lotes a placas visavet pero sin haber confirmado aún
public class LotePlacaVisavetBean {
private List<Integer> listaTamanosDisponibles;
private List<BeanLote> listaLotesDisponibles;
private BeanPlacaVisavetUCM placa;
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

public List<BeanLote> getListaLotesDisponibles() {
	return listaLotesDisponibles;
}

public void setListaLotesDisponibles(List<BeanLote> listaLotesDisponibles) {
	this.listaLotesDisponibles = listaLotesDisponibles;
}


}
