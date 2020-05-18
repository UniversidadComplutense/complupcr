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
import java.util.List;

import org.springframework.util.CollectionUtils;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;

public class LoteListadoBean {

	private Integer id;
	private String numLote;
	private Integer idLaboratorio;
	private String descLaboratorio;
	private String descEstado;
	private Date fechaEnvio;
	private Integer capacidad;

	private List<MuestraListadoBean> muestras;
	
	private CentroBean centroBean;
	private BeanEstado beanEstado;
	private String referenciaInternaLote;
	
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
	
	public Integer getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(Integer idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public String getDescLaboratorio() {
		return descLaboratorio;
	}

	public void setDescLaboratorio(String descLaboratorio) {
		this.descLaboratorio = descLaboratorio;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public List<MuestraListadoBean> getMuestras() {
		if (muestras == null) {
			muestras = new ArrayList<MuestraListadoBean>();
		}
		return muestras;
	}

	public void setMuestras(List<MuestraListadoBean> muestras) {
		this.muestras = muestras;
	}
	
	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	
	
	
	public CentroBean getCentroBean() {
		return centroBean;
	}

	public void setBeanEstado(BeanEstado beanEstado) {
		this.beanEstado = beanEstado;
	}

	public void setCentroBean(CentroBean centroBean) {
		this.centroBean = centroBean;
	}
	
	public BeanEstado getBeanEstado() {
		return beanEstado;
	}


	public String getReferenciaInternaLote() {
		return referenciaInternaLote;
	}

	public void setReferenciaInternaLote(String referenciaInternaLote) {
		this.referenciaInternaLote = referenciaInternaLote;
	}

	public static LoteListadoBean modelToBean(Lote lote) {
		// TODO - LABORATORIO, FECHA ENVIO		
		// TODO - MUESTRAS
		
		LoteListadoBean bean = new LoteListadoBean();
		bean.setId(lote.getId());
		bean.setNumLote(lote.getNumeroLote());
		bean.setCapacidad(lote.getCapacidad());
		bean.setFechaEnvio(lote.getFechaEnvio());
		bean.setDescEstado(lote.getEstadoLote().getDescripcion());
		if (lote.getLaboratorioVisavet() != null) {
			bean.setDescLaboratorio(lote.getLaboratorioVisavet().getNombre());
			bean.setIdLaboratorio(lote.getLaboratorioVisavet().getId());
		}
		//añadido por yoli
	    bean.setCentroBean(CentroBean.modelToBean(lote.getCentro()));
	    BeanEstado estado = new BeanEstado();
	    estado.asignarTipoEstadoYCodNum(TipoEstado.EstadoLote, lote.getEstadoLote().getId());
	    bean.setBeanEstado(estado);
		
		// Si el lote tiene muestras
		if (!CollectionUtils.isEmpty(lote.getMuestras())) {
			for (Muestra m : lote.getMuestras()) {
				bean.getMuestras().add(MuestraListadoBean.modelToBean(m));
			}
		}
		
		
		bean.setReferenciaInternaLote(lote.getReferenciaInternaLote());
		return bean;
	}

}
