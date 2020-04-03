package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;

public class PlacaLaboratorioVisavetBean {

	private Integer id;
	private String numeroMuestras;
	private BeanEstado beanEstado;
	private Date fechaCreacion;
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


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
		bean.setNumeroMuestras("" + placaVisavet.getNumeromuestras());
		bean.setFechaCreacion(placaVisavet.getFechaCreacion());
		
		List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
		Set<Muestra> muestras = placaVisavet.getMuestras();
		for (Muestra muestra : muestras) {
			MuestraListadoPlacasLaboratorioBean muestraBean = new MuestraListadoPlacasLaboratorioBean();
			muestraBean.setId(muestra.getId());
			muestraBean.setIdPlacaVisavet(muestra.getPlacaVisavet().getId());
			muestraBean.setEstado(muestra.getEstadoMuestra().getDescripcion());
			muestraBean.setEtiqueta(muestra.getEtiqueta());
			muestraBean.setRefInterna(muestra.getRefInternaVisavet());
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
		
		if (placaLaboratorioVisavetBean.getFechaCreacion() != null) {
			placa.setFechaCreacion(placaLaboratorioVisavetBean.getFechaCreacion());
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
