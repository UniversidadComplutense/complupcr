package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;

public class LoteListadoBean {

	private Integer id;
	private String numLote;
	private String descLaboratorio;
	private String descEstado;

	private List<MuestraListadoBean> muestras;

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

	public List<MuestraListadoBean> getMuestras() {
		if (muestras == null) {
			muestras = new ArrayList<MuestraListadoBean>();
		}
		return muestras;
	}

	public void setMuestras(List<MuestraListadoBean> muestras) {
		this.muestras = muestras;
	}

}
