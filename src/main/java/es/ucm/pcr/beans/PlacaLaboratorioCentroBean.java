package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.ucm.pcr.modelo.orm.Documento;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;

public class PlacaLaboratorioCentroBean {

	private String id;
	private String numeroMuestras;
	private BeanEstado beanEstado;
	private LaboratorioCentroBean laboratorioCentro;
	private List<PlacaLaboratorioVisavetBean> placasVisavet;
	private List<DocumentoBean> documentos;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	
	public static PlacaLaboratorioCentroBean modelToBean(PlacaLaboratorio placaLaboratorio) {

		PlacaLaboratorioCentroBean bean = new PlacaLaboratorioCentroBean();

		bean.setId(String.valueOf(placaLaboratorio.getId()));

		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLabCentro,
				placaLaboratorio.getEstadoPlacaLaboratorio().getId());
		bean.setBeanEstado(beanEstado);

		LaboratorioCentroBean laboratorioCentroBean = new LaboratorioCentroBean();
		// TODO rellenar laboratorioCentroBean
		// laboratorioCentroBean.setId(String.valueOf((placaLaboratorio.getLaboratorioCentro().getId())));
		laboratorioCentroBean.setNombre(placaLaboratorio.getLaboratorioCentro().getNombre());
		// laboratorioCentroBean.setCapacidad(loteBean.getCapacidad() != null ?
		// loteBean.getCapacidad() : 0);
		bean.setLaboratorioCentro(laboratorioCentroBean);
		bean.setNumeroMuestras(placaLaboratorio.getNumeromuestras());

		List<PlacaLaboratorioVisavetBean> placasVisavet = new ArrayList<PlacaLaboratorioVisavetBean>();
		// TODO rellenar placasVisavet
		bean.setPlacasVisavet(placasVisavet);

		List<DocumentoBean> documentos = new ArrayList<DocumentoBean>();
		// TODO rellenar Documento
		bean.setDocumentos(documentos);

		return bean;

	}

	public static PlacaLaboratorio beanToModel(PlacaLaboratorioCentroBean placaLaboratorioCentroBean) {

		PlacaLaboratorio placa = new PlacaLaboratorio();

		placa.setId(Integer.valueOf(placaLaboratorioCentroBean.getId()));

		EstadoPlacaLaboratorio estadoPlacaLaboratorio = new EstadoPlacaLaboratorio();
		estadoPlacaLaboratorio.setId(placaLaboratorioCentroBean.getBeanEstado().getEstado().getCodNum());
		estadoPlacaLaboratorio.setDescripcion(placaLaboratorioCentroBean.getBeanEstado().getEstado().getDescripcion());
		placa.setEstadoPlacaLaboratorio(estadoPlacaLaboratorio);

		// TODO rellenar LaboratorioCentro
		// placa.setLaboratorioCentro();

		// TODO rellenar documentos
		Set<Documento> documentos = new HashSet<Documento>();
		placa.setDocumentos(documentos);

		placa.setNumeromuestras(placaLaboratorioCentroBean.getNumeroMuestras());

		// TODO rellenar placas Visavet
		Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios = new HashSet<PlacaVisavetPlacaLaboratorio>();
		placa.setPlacaVisavetPlacaLaboratorios(placaVisavetPlacaLaboratorios);

		return placa;
	}

}
