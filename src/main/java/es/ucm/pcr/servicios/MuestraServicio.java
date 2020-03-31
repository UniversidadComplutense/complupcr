package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;

public interface MuestraServicio {
	
	/**
	 * Recupera todos las muestras en funcion de los parametros de busqueda
	 * @param pageable
	 * @return
	 */
	public Page<MuestraListadoBean> findMuestraByParam(MuestraBusquedaBean params, Pageable pageable);
	
	/**
	 * Guardar muestra
	 * @param muestraBean
	 * @return muestra guardada
	 */
	public MuestraCentroBean guardar(MuestraCentroBean muestraBean);
	
	/**
	 * Busca lote por id
	 * @param id
	 * @return
	 */
	public MuestraCentroBean findById(Integer id);
	
	
	/**
	 * Actualiza el estado de una muestra
	 * @param id identificador estado
	 * @param estadoActualizar estado a actualizar
	 */
	public void actualizarEstadoMuestra(Integer id, BeanEstado estadoActualizar);

}
