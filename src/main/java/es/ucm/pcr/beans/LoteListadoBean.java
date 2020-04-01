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
	private String descLaboratorio;
	private String descEstado;
	private Date fechaEnvio;
	private Integer capacidad;

	private List<MuestraListadoBean> muestras;
	
	private CentroBean centroBean;
	private BeanEstado beanEstado;

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

	public void setCentroBean(CentroBean centroBean) {
		this.centroBean = centroBean;
	}
	
	public BeanEstado getBeanEstado() {
		return beanEstado;
	}

	public void setBeanEstado(BeanEstado beanEstado) {
		this.beanEstado = beanEstado;
	}

	public static LoteListadoBean modelToBean(Lote lote) {
		// TODO - LABORATORIO, FECHA ENVIO		
		// TODO - MUESTRAS
		
		LoteListadoBean bean = new LoteListadoBean();
		bean.setId(lote.getId());
		bean.setNumLote(lote.getNumeroLote());
		bean.setCapacidad(lote.getCapacidad());
		//bean.setFechaEnvio(lote.getFechaEnvio());
		bean.setDescEstado(lote.getEstadoLote().getDescripcion());
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
		
		return bean;
	}

}
