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

import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public class PlacaLaboratorioVisavetBean {

	private Integer id;
	private String numeroMuestras;
	private BeanEstado beanEstado;
	private Date fechaAsignacion;
	private Date fechaEnvio;
	private Date fechaRecepcion;
	
	private List<MuestraListadoPlacasLaboratorioBean> muestras;

	
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNumeroMuestras() {
		return numeroMuestras;
	}


	public void setNumeroMuestras(String numeroMuestras) {
		this.numeroMuestras = numeroMuestras;
	}


	public BeanEstado getBeanEstado() {
		return beanEstado;
	}


	public void setBeanEstado(BeanEstado beanEstado) {
		this.beanEstado = beanEstado;
	}


	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}


	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}


	public Date getFechaEnvio() {
		return fechaEnvio;
	}


	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}


	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}


	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}


	public List<MuestraListadoPlacasLaboratorioBean> getMuestras() {
		return muestras;
	}


	public void setMuestras(List<MuestraListadoPlacasLaboratorioBean> muestras) {
		this.muestras = muestras;
	}


	public static PlacaLaboratorioVisavetBean modelToBean(PlacaVisavet placaVisavet) {

		PlacaLaboratorioVisavetBean bean = new PlacaLaboratorioVisavetBean();

		bean.setId(placaVisavet.getId());

		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLaboratorioVisavet,
				placaVisavet.getEstadoPlacaVisavet().getId());
		bean.setBeanEstado(beanEstado);
		if (placaVisavet.getNumeromuestras() == null) {
			bean.setNumeroMuestras("0");
		} else {
			bean.setNumeroMuestras("" + placaVisavet.getNumeromuestras());
		}
		bean.setFechaAsignacion(placaVisavet.getFechaAsignadaLaboratorioCentro());
		bean.setFechaEnvio(placaVisavet.getFechaEnviadaLaboratorioCentro());
		bean.setFechaRecepcion(placaVisavet.getFechaRecepcionLaboratorioCentro());
		
		List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();

		// Recuperamos las muestras de la placa Visavet desde el lote
		
		Set<Muestra> muestras = new HashSet<Muestra>();
		
		Set<Lote> lotes = placaVisavet.getLotes();
		for (Lote lote : lotes) {
			muestras.addAll(lote.getMuestras());
		}
		
		for (Muestra muestra : muestras) {
			MuestraListadoPlacasLaboratorioBean muestraBean = new MuestraListadoPlacasLaboratorioBean();
			muestraBean.setId(muestra.getId());
			muestraBean.setEstado(muestra.getEstadoMuestra().getDescripcion());
			muestraBean.setEtiqueta(muestra.getEtiqueta());
			muestraBean.setRefInterna(muestra.getRefInternaVisavet());
			muestraBean.setIdPlacaVisavet(placaVisavet.getId());
			listadoMuestras.add(muestraBean);
		}
		bean.setMuestras(listadoMuestras);
		return bean;

	}
	

	public static PlacaVisavet beanToModel(PlacaLaboratorioVisavetBean placaLaboratorioVisavetBean) {

		PlacaVisavet placa = new PlacaVisavet();

		if (placaLaboratorioVisavetBean.getId() != null) {
			placa.setId(placaLaboratorioVisavetBean.getId());
		}
		
		if (placaLaboratorioVisavetBean.getFechaAsignacion() != null) {
			placa.setFechaAsignadaLaboratorioCentro(placaLaboratorioVisavetBean.getFechaAsignacion());
		}
		
		if (placaLaboratorioVisavetBean.getFechaEnvio() != null) {
			placa.setFechaEnviadaLaboratorioCentro(placaLaboratorioVisavetBean.getFechaEnvio());
		}
		
		if (placaLaboratorioVisavetBean.getFechaRecepcion() != null) {
			placa.setFechaRecepcionLaboratorioCentro(placaLaboratorioVisavetBean.getFechaRecepcion());
		}
		
		if (placaLaboratorioVisavetBean.getBeanEstado() != null) {
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(placaLaboratorioVisavetBean.getBeanEstado().getEstado().getCodNum()));
		}
		
		if (placaLaboratorioVisavetBean.getNumeroMuestras() != null) {
			placa.setNumeromuestras(Integer.valueOf(placaLaboratorioVisavetBean.getNumeroMuestras()));
		}

		return placa;
	}

}
