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

import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;

public class PlacaLaboratorioCentroBean {

	private Integer id;
	private String numeroMuestras;
	private BeanEstado beanEstado;
	private LaboratorioCentroBean laboratorioCentro;
	private Date fechaCreacion;
	private List<PlacaLaboratorioVisavetBean> placasVisavet;
	private List<DocumentoBean> documentos;
	private List<MuestraListadoPlacasLaboratorioBean> muestras;
	private List<PlacaLaboratorioVisavetBean> placasVisavetParaCombinar;
	private String placasVisavetSeleccionadas;
	private Integer idEquipo;
	private boolean tieneDocumentos;
	private Date fechaListaAnalisis;

	
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

	public LaboratorioCentroBean getLaboratorioCentro() {
		return laboratorioCentro;
	}

	public void setLaboratorioCentro(LaboratorioCentroBean laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<PlacaLaboratorioVisavetBean> getPlacasVisavet() {
		return placasVisavet;
	}

	public void setPlacasVisavet(List<PlacaLaboratorioVisavetBean> placasVisavet) {
		this.placasVisavet = placasVisavet;
	}

	public List<DocumentoBean> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoBean> documentos) {
		this.documentos = documentos;
	}
	
	public List<MuestraListadoPlacasLaboratorioBean> getMuestras() {
		return muestras;
	}

	public void setMuestras(List<MuestraListadoPlacasLaboratorioBean> muestras) {
		this.muestras = muestras;
	}

	public List<PlacaLaboratorioVisavetBean> getPlacasVisavetParaCombinar() {
		return placasVisavetParaCombinar;
	}

	public void setPlacasVisavetParaCombinar(List<PlacaLaboratorioVisavetBean> placasVisavetParaCombinar) {
		this.placasVisavetParaCombinar = placasVisavetParaCombinar;
	}
	
	public String getPlacasVisavetSeleccionadas() {
		return placasVisavetSeleccionadas;
	}

	public void setPlacasVisavetSeleccionadas(String placasVisavetSeleccionadas) {
		this.placasVisavetSeleccionadas = placasVisavetSeleccionadas;
	}

	public Integer getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}

	public boolean isTieneDocumentos() {
		return tieneDocumentos;
	}

	public void setTieneDocumentos(boolean tieneDocumentos) {
		this.tieneDocumentos = tieneDocumentos;
	}	

	public Date getFechaListaAnalisis() {
		return fechaListaAnalisis;
	}

	public void setFechaListaAnalisis(Date fechaListaAnalisis) {
		this.fechaListaAnalisis = fechaListaAnalisis;
	}

	public static PlacaLaboratorioCentroBean modelToBean(PlacaLaboratorio placaLaboratorio) {

		PlacaLaboratorioCentroBean bean = new PlacaLaboratorioCentroBean();

		bean.setId(placaLaboratorio.getId());

		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLabCentro,
				placaLaboratorio.getEstadoPlacaLaboratorio().getId());
		bean.setBeanEstado(beanEstado);
		bean.setNumeroMuestras(placaLaboratorio.getNumeromuestras());
		bean.setFechaCreacion(placaLaboratorio.getFechaCreacion());
		bean.setFechaListaAnalisis(placaLaboratorio.getFechaListaAnalisis());
		
		LaboratorioCentroBean laboratorioCentroBean = new LaboratorioCentroBean();	
		laboratorioCentroBean.setId(String.valueOf((placaLaboratorio.getLaboratorioCentro().getId())));
		laboratorioCentroBean.setNombre(placaLaboratorio.getLaboratorioCentro().getNombre());
		bean.setLaboratorioCentro(laboratorioCentroBean);

		
		List<PlacaLaboratorioVisavetBean> listadoPlacasVisavet = new ArrayList<PlacaLaboratorioVisavetBean>();
		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();
		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placaLaboratorio.getPlacaVisavetPlacaLaboratorios()) {
			placasVisavetDeLaPlacaLaboratorio.add(placaVisavetPlacaLaboratorio.getPlacaVisavet());
		}
		
		for (PlacaVisavet placaVisavet: placasVisavetDeLaPlacaLaboratorio) {
			PlacaLaboratorioVisavetBean placaVisavetBean = new PlacaLaboratorioVisavetBean();
			
			BeanEstado beanEstadoPlacaVisavet = new BeanEstado();
			beanEstadoPlacaVisavet.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLaboratorioVisavet,
					placaVisavet.getEstadoPlacaVisavet().getId());
			placaVisavetBean.setBeanEstado(beanEstadoPlacaVisavet);
			
			placaVisavetBean.setId(placaVisavet.getId());
			placaVisavetBean.setFechaAsignacion(placaVisavet.getFechaAsignadaLaboratorioCentro());
			placaVisavetBean.setFechaEnvio(placaVisavet.getFechaEnviadaLaboratorioCentro());
			placaVisavetBean.setFechaRecepcion(placaVisavet.getFechaRecepcionLaboratorioCentro());
			placaVisavetBean.setNumeroMuestras("" + placaVisavet.getNumeromuestras());
			
			
			List<MuestraListadoPlacasLaboratorioBean> listadoMuestrasVisavet = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
			// Recuperamos las muestras de la placa Visavet desde el lote
			Set<Muestra> muestrasVisavet = new HashSet<Muestra>();
			Set<Lote> lotes = placaVisavet.getLotes();
			for (Lote lote : lotes) {
				muestrasVisavet.addAll(lote.getMuestras());
			}

			for (Muestra muestra : muestrasVisavet) {
				MuestraListadoPlacasLaboratorioBean muestraBean = new MuestraListadoPlacasLaboratorioBean();
				muestraBean.setId(muestra.getId());
				muestraBean.setEstado(muestra.getEstadoMuestra().getDescripcion());
				muestraBean.setEtiqueta(muestra.getEtiqueta());
				muestraBean.setRefInterna(muestra.getRefInternaVisavet());
				muestraBean.setIdPlacaVisavet(placaVisavet.getId());
				listadoMuestrasVisavet.add(muestraBean);
			}
			placaVisavetBean.setMuestras(listadoMuestrasVisavet);
			
			
			listadoPlacasVisavet.add(placaVisavetBean);
		}
		bean.setPlacasVisavet(listadoPlacasVisavet);
		
		// La muestras de la placa de laboratorio serán la suma de las muestras de las placas Visavet que contiene
		
		List<MuestraListadoPlacasLaboratorioBean> listadoMuestrasPlacaLaboratorio = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
		for (PlacaLaboratorioVisavetBean placaVisavetBean : listadoPlacasVisavet) {
			listadoMuestrasPlacaLaboratorio.addAll(placaVisavetBean.getMuestras());
		}
		
		bean.setMuestras(listadoMuestrasPlacaLaboratorio);

		if (placaLaboratorio.getEquipo() != null) {
			bean.setIdEquipo(placaLaboratorio.getEquipo().getId());
		}		
		
		List<DocumentoBean> documentos = new ArrayList<DocumentoBean>();
		// TODO rellenar Documentos
		bean.setDocumentos(documentos);
		
		if (placaLaboratorio.getDocumentos()!= null && !placaLaboratorio.getDocumentos().isEmpty()) {
			bean.setTieneDocumentos(true);
		} else {
			bean.setTieneDocumentos(false);
		}

		return bean;

	}

	public static PlacaLaboratorio beanToModel(PlacaLaboratorioCentroBean placaLaboratorioCentroBean) {

		PlacaLaboratorio placa = new PlacaLaboratorio();

		if (placaLaboratorioCentroBean.getId() != null) {
			placa.setId(placaLaboratorioCentroBean.getId());
		}
		
		if (placaLaboratorioCentroBean.getFechaCreacion() != null) {
			placa.setFechaCreacion(placaLaboratorioCentroBean.getFechaCreacion());
		}

		if (placaLaboratorioCentroBean.getBeanEstado() != null) {
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(placaLaboratorioCentroBean.getBeanEstado().getEstado().getCodNum()));
		}
		
		if (placaLaboratorioCentroBean.getNumeroMuestras() != null) {
			placa.setNumeromuestras(placaLaboratorioCentroBean.getNumeroMuestras());
		}
		
		

		// TODO rellenar LaboratorioCentro
		// placa.setLaboratorioCentro();

		// TODO rellenar documentos
		//Set<Documento> documentos = new HashSet<Documento>();
		//placa.setDocumentos(documentos);

		

		// TODO rellenar placas Visavet
		//Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios = new HashSet<PlacaVisavetPlacaLaboratorio>();
		//placa.setPlacaVisavetPlacaLaboratorios(placaVisavetPlacaLaboratorios);

		return placa;
	}

}
