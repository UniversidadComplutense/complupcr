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
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;

import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public class LoteBeanPlacaVisavet{
	private Integer id;
	private String numLote;
	private String centroProcedencia;
	private CentroBean centroBean;
	private int capacidad;
	private int ocupacion;
	private BeanEstado estado;
	private Date fechaEntrada;
	private List<MuestraBeanLaboratorioVisavet>  listaMuestras;
	private String funcionEjecutar;
	private String test;
	private Integer idPlacaVisavet; 
	private Integer tamanoPlaca;
	private String nombrePlaca;
	private String referenciaInternaLote;
	private String errorReferenciaLote;
	//private BeanPlacaVisavetUCM beanPlacaVisavetUCM;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumLote() {
		return numLote;
	}
	public void setNumLote(String numLote) {
		this.numLote = numLote;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public int getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(int ocupacion) {
		this.ocupacion = ocupacion;
	}
	public BeanEstado getEstado() {
		return estado;
	}
	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}
	
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public List<MuestraBeanLaboratorioVisavet> getListaMuestras() {
		return listaMuestras;
	}
	public void setListaMuestras(List<MuestraBeanLaboratorioVisavet> listaMuestras) {
		this.listaMuestras = listaMuestras;
	}
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getCentroProcedencia() {
		return centroProcedencia;
	}
	public void setCentroProcedencia(String centroProcedencia) {
		this.centroProcedencia = centroProcedencia;
	}
	public String getFuncionEjecutar() {
		return funcionEjecutar;
	}
	public void setFuncionEjecutar(String funcionEjecutar) {
		this.funcionEjecutar = funcionEjecutar;
	}
	public Integer getIdPlacaVisavet() {
		return idPlacaVisavet;
	}
	public void setIdPlacaVisavet(Integer idPlacaVisavet) {
		this.idPlacaVisavet = idPlacaVisavet;
	}
	
	public CentroBean getCentroBean() {
		return centroBean;
	}
	public void setCentroBean(CentroBean centroBean) {
		this.centroBean = centroBean;
	}
	
public String getReferenciaInternaLote() {
		return referenciaInternaLote;
	}
	public void setReferenciaInternaLote(String referenciaInternaLote) {
		this.referenciaInternaLote = referenciaInternaLote;
	}
	/*	public BeanPlacaVisavetUCM getBeanPlacaVisavetUCM() {
		return beanPlacaVisavetUCM;
	}
	public void setBeanPlacaVisavetUCM(BeanPlacaVisavetUCM beanPlacaVisavetUCM) {
		this.beanPlacaVisavetUCM = beanPlacaVisavetUCM;
	}
	*/
	
	public static LoteBeanPlacaVisavet modelToBean(Lote lote) {
		LoteBeanPlacaVisavet lotePlaca= new LoteBeanPlacaVisavet();
		lotePlaca.setId(lote.getId());
		lotePlaca.setNumLote(lote.getNumeroLote());
		lotePlaca.setCentroProcedencia(lote.getCentro().getNombre());
		lotePlaca.setFechaEntrada(lote.getFechaEnvio());
		// necesito el BeanEstado
		 BeanEstado estado = new BeanEstado();
		 estado.asignarTipoEstadoYCodNum(TipoEstado.EstadoLote, lote.getEstadoLote().getId());
		 lotePlaca.setEstado(estado);
		//lotePlaca.setEstado(unResultado.);
		List<MuestraBeanLaboratorioVisavet>  listaMuestrasVisavet= new ArrayList<MuestraBeanLaboratorioVisavet>();

		// Si el lote tiene muestras
		if (!CollectionUtils.isEmpty(lote.getMuestras())) {
			for (Muestra m : lote.getMuestras()) {
				listaMuestrasVisavet.add(MuestraBeanLaboratorioVisavet.modelToBean(m));
			}
		}
		lotePlaca.setListaMuestras(listaMuestrasVisavet);
		
		lotePlaca.setCentroBean(CentroBean.modelToBean(lote.getCentro()));
		if (lote.getPlacaVisavet()!= null) {
		lotePlaca.setIdPlacaVisavet(lote.getPlacaVisavet().getId());

		lotePlaca.setNombrePlaca(lote.getPlacaVisavet().getNombrePlacaVisavet());
		lotePlaca.setTamanoPlaca(lote.getPlacaVisavet().getNumeromuestras());
		/*if (lote.getPlacaVisavet()!=null)
		lotePlaca.setBeanPlacaVisavetUCM(BeanPlacaVisavetUCM.modelToBean(lote.getPlacaVisavet()));
		*/
		}
		lotePlaca.setReferenciaInternaLote(lote.getReferenciaInternaLote());
		return lotePlaca;
	}
	public String getNombrePlaca() {
		return nombrePlaca;
	}
	public void setNombrePlaca(String nombrePlaca) {
		this.nombrePlaca = nombrePlaca;
	}
	public String getErrorReferenciaLote() {
		return errorReferenciaLote;
	}
	public void setErrorReferenciaLote(String errorReferenciaLote) {
		this.errorReferenciaLote = errorReferenciaLote;
	}
	
	public Integer getTamanoPlaca() {
		return tamanoPlaca;
	}
	public void setTamanoPlaca(Integer tamanoPlaca) {
		this.tamanoPlaca = tamanoPlaca;
	}
	public static Lote beanToModel(LoteBeanPlacaVisavet lotePlaca) {
		Lote lote= new Lote();
		lote.setId(lotePlaca.getId());
		lote.setNumeroLote(lotePlaca.getNumLote());
		
		if(lotePlaca.getCentroBean()!= null)
		lote.setCentro(CentroBean.beanToModel(lotePlaca.getCentroBean()));
		if (lotePlaca.getEstado()!= null)
		lote.setEstadoLote(new EstadoLote(lotePlaca.getEstado().getEstado().getCodNum()));
	//	lote.setPlacaVisavet(BeanPlacaVisavetUCM.beanToModel(lotePlaca.getBeanPlacaVisavetUCM()));
		Set<Muestra>  listaMuestras=  new HashSet();

		// Si el lote tiene muestras
		if (!CollectionUtils.isEmpty(lotePlaca.getListaMuestras())) {
			for (MuestraBeanLaboratorioVisavet m : lotePlaca.getListaMuestras()) {
				listaMuestras.add(MuestraBeanLaboratorioVisavet.beanToModel(m));
			}
		}
		lote.setMuestras(listaMuestras);
		lote.setReferenciaInternaLote(lotePlaca.getReferenciaInternaLote());
		return lote;
	}
}
