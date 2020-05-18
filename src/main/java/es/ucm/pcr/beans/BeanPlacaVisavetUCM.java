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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public class BeanPlacaVisavetUCM {
// aunque es List suponemos que una placa solo contiene un lote
private List<LoteBeanPlacaVisavet> listaLotes;
private Integer id;
private String tamano;
private Date fechaCreacion;
private Date fechaAsignadaLaboratorio;
private Date fechaEnviadaLaboratorio;
private BeanEstado estado;
private Integer idLaboratorioCentro;
private BeanLaboratorioCentro laboratorioCentro;
private List<MuestraBeanLaboratorioVisavet>  listaMuestras;
private String nombrePlacaVisavet;

public List<MuestraBeanLaboratorioVisavet> getListaMuestras() {
	return listaMuestras;
}
public void setListaMuestras(List<MuestraBeanLaboratorioVisavet> listaMuestras) {
	this.listaMuestras = listaMuestras;
}
public List<LoteBeanPlacaVisavet> getListaLotes() {
	return listaLotes;
}
public void setListaLotes(List<LoteBeanPlacaVisavet> listaLotes) {
	this.listaLotes = listaLotes;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
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

public BeanLaboratorioCentro getLaboratorioCentro() {
	return laboratorioCentro;
}
public void setLaboratorioCentro(BeanLaboratorioCentro laboratorioCentro) {
	this.laboratorioCentro = laboratorioCentro;
}


public Date getFechaAsignadaLaboratorio() {
	return fechaAsignadaLaboratorio;
}
public void setFechaAsignadaLaboratorio(Date fechaAsignadaLaboratorio) {
	this.fechaAsignadaLaboratorio = fechaAsignadaLaboratorio;
}




public Date getFechaEnviadaLaboratorio() {
	return fechaEnviadaLaboratorio;
}
public void setFechaEnviadaLaboratorio(Date fechaEnviadaLaboratorio) {
	this.fechaEnviadaLaboratorio = fechaEnviadaLaboratorio;
}

public String getNombrePlacaVisavet() {
	return nombrePlacaVisavet;
}
public void setNombrePlacaVisavet(String nombrePlacaVisavet) {
	this.nombrePlacaVisavet = nombrePlacaVisavet;
}
public static BeanPlacaVisavetUCM modelToBean(PlacaVisavet placaVisavet) {

	BeanPlacaVisavetUCM bean = new BeanPlacaVisavetUCM();
//
	
	bean.setId(placaVisavet.getId());
//	bean.setTamano(placaVisavet.getTamano());
	bean.setFechaCreacion(placaVisavet.getFechaCreacion());
	bean.setFechaAsignadaLaboratorio(placaVisavet.getFechaAsignadaLaboratorioCentro());
	BeanEstado beanEstado = new BeanEstado();
	beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLaboratorioVisavet,
			placaVisavet.getEstadoPlacaVisavet().getId());
	bean.setEstado(beanEstado);
    bean.setFechaEnviadaLaboratorio(placaVisavet.getFechaEnviadaLaboratorioCentro());
	LoteBeanPlacaVisavet lote = new LoteBeanPlacaVisavet();
	List<LoteBeanPlacaVisavet> listaLotes = new ArrayList();
	List<MuestraBeanLaboratorioVisavet> listaMuestras = new ArrayList();
	for (Lote l:placaVisavet.getLotes()) {
	lote=LoteBeanPlacaVisavet.modelToBean(l);
	listaLotes.add(lote);
	
   
	
	for (Muestra m:l.getMuestras()) {
	
	listaMuestras.add(MuestraBeanLaboratorioVisavet.modelToBean(m));
	}
	
	}
	bean.setListaMuestras(listaMuestras);
	if (placaVisavet.getLaboratorioCentro()!= null) {
		BeanLaboratorioCentro laboratorioCentro= new BeanLaboratorioCentro();
		//LaboratorioCentro laboratorioCentro = new BeanLaboratorioCentro();
		laboratorioCentro.setId(placaVisavet.getLaboratorioCentro().getId());
		laboratorioCentro.setNombre(placaVisavet.getLaboratorioCentro().getNombre());
	    bean.setLaboratorioCentro(laboratorioCentro);
	    bean.setIdLaboratorioCentro(placaVisavet.getLaboratorioCentro().getId());
	}
	
	/*List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
	Set<Muestra> muestras = placaVisavet.getMuestras();
	
	bean.setMuestras(listadoMuestras);
	*/
	bean.setNombrePlacaVisavet(placaVisavet.getNombrePlacaVisavet());
	bean.setListaLotes(listaLotes);
	return bean;

}
public static PlacaVisavet beanToModel(BeanPlacaVisavetUCM bean) {

	PlacaVisavet placaVisavet = new PlacaVisavet();
//
	
	placaVisavet.setId(bean.getId());
//	bean.setTamano(placaVisavet.getTamano());
	placaVisavet.setFechaCreacion(bean.getFechaCreacion());
	placaVisavet.setFechaAsignadaLaboratorioCentro(bean.getFechaAsignadaLaboratorio());
    placaVisavet.setEstadoPlacaVisavet(new EstadoPlacaVisavet(bean.getEstado().getEstado().getCodNum()));
    placaVisavet.setFechaEnviadaLaboratorioCentro(bean.getFechaEnviadaLaboratorio());
    /* YOLI  
     */Set<Lote> listaLotes =  new HashSet();
   if (bean.getListaLotes() !=null) {
    for (LoteBeanPlacaVisavet l:bean.getListaLotes()) {
    	listaLotes.add(LoteBeanPlacaVisavet.beanToModel(l));
    }
    placaVisavet.setLotes(listaLotes);
    
   }
    /*LoteBeanPlacaVisavet lote = new LoteBeanPlacaVisavet();
	List<LoteBeanPlacaVisavet> listaLotes = new ArrayList();
	
	for (Lote l:placaVisavet.getLotes()) {
		LoteBeanPlacaVisavet.modelToBean(l);
	listaLotes.add(lote);
	}
	*/
	/*List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
	Set<Muestra> muestras = placaVisavet.getMuestras();
	
	bean.setMuestras(listadoMuestras);
	*/
   placaVisavet.setNombrePlacaVisavet(bean.getNombrePlacaVisavet());
	return placaVisavet;

}
@Override
public String toString() {
	return "BeanPlacaVisavetUCM [listaLotes=" + listaLotes + ", id=" + id + ", tamano=" + tamano + ", fechaCreacion="
			+ fechaCreacion + ", fechaAsignadaLaboratorio=" + fechaAsignadaLaboratorio + ", fechaEnviadaLaboratorio="
			+ fechaEnviadaLaboratorio + ", estado=" + estado + ", idLaboratorioCentro=" + idLaboratorioCentro
			+ ", laboratorioCentro=" + laboratorioCentro + ", listaMuestras=" + listaMuestras + ", nombrePlacaVisavet="
			+ nombrePlacaVisavet + "]";
}



}
