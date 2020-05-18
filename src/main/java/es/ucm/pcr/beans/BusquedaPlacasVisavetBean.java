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

import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class BusquedaPlacasVisavetBean {
private Integer idPlaca;
private  String numLote;
private Date fechaCreacionInicio;
private Date fechaCreacionFin;
private String muestra;
private List<BeanEstado> listaBeanEstado;
private Integer codNumEstadoSeleccionado;
private List<BeanLaboratorioCentro> listaLaboratorioCentro;
private Integer idLaboratorioCentro;
private Integer idLaboratorioVisavet;
private String nombrePlacaVisavet;

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

public Date getFechaCreacionInicio() {
	return fechaCreacionInicio;
}
public void setFechaCreacionInicio(Date fechaCreacionInicio) {
	this.fechaCreacionInicio = fechaCreacionInicio;
}
public Date getFechaCreacionFin() {
	return fechaCreacionFin;
}
public void setFechaCreacionFin(Date fechaCreacionFin) {
	this.fechaCreacionFin = fechaCreacionFin;
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
public String getNombrePlacaVisavet() {
	return nombrePlacaVisavet;
}
public void setNombrePlacaVisavet(String nombrePlacaVisavet) {
	this.nombrePlacaVisavet = nombrePlacaVisavet;
}


}
