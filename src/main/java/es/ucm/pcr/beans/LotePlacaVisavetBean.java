/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

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
