package es.ucm.pcr.beans;

import java.util.Date;

import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.Lote;

public class LoteCentroBean {
	private Integer id;
	private String numLote;
	private BeanEstado estado;
	private Integer idCentro;
	private Integer idLaboratorio;
	private Integer capacidad;
	private Date fechaEnvio;
	
	public LoteCentroBean() {
		super();	
	}
	
	public LoteCentroBean(BeanEstado estado, Integer idCentro) {
		super();
		this.estado = estado;
		this.idCentro = idCentro;
	}

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

	public BeanEstado getEstado() {
		return estado;
	}

	public void setEstado(BeanEstado estado) {
		this.estado = estado;
	}
	
	public Integer getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(Integer idCentro) {
		this.idCentro = idCentro;
	}

	public Integer getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(Integer idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public static Lote beanToModel(LoteCentroBean loteBean) {
		Lote lote = new Lote();
		lote.setNumeroLote(loteBean.getNumLote());
		lote.setCentro(new Centro(loteBean.getIdCentro()));		
		lote.setEstadoLote(new EstadoLote(loteBean.getEstado().getEstado().getCodNum()));
		lote.setCapacidad(loteBean.getCapacidad() != null ? loteBean.getCapacidad() : 0);
		// TODO - COMPLETAR MUESTRAS
		return lote;
	}
	
	public static LoteCentroBean modelToBean(Lote lote) {
		LoteCentroBean loteBean = new LoteCentroBean();
		loteBean.setId(lote.getId());
		loteBean.setNumLote(lote.getNumeroLote());
		loteBean.setCapacidad(lote.getCapacidad());
		BeanEstado beanEstado = new BeanEstado();
		loteBean.setEstado(beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoLote, lote.getEstadoLote().getId()));
		loteBean.setFechaEnvio(lote.getFechaEnvio());
		// TODO - COMPLETAR MUESTRAS
		return loteBean;
	}
	
}
