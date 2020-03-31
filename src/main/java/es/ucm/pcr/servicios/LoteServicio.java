package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;

public interface LoteServicio {
	
	/**
	 * Recupera todos los lotes en funcion de los parametros de busqueda
	 * @param pageable
	 * @return
	 */
	public Page<LoteListadoBean> findLoteByParam(LoteBusquedaBean params, Pageable pageable);
	
	/**
	 * Busca lotes en funcion de los parametros de busqueda
	 * @param params
	 * @return
	 */
	public List<LoteListadoBean> findLoteByParam(LoteBusquedaBean params);
	
	/**
	 * Guardar lote
	 * @param loteBean
	 * @return lote guardado
	 */
	public LoteCentroBean guardar(LoteCentroBean loteBean);
	
	/**
	 * Busca lote por id
	 * @param id
	 * @return
	 */
	public LoteCentroBean findById(Integer id);
	
	
	/**
	 * Actualiza el estado de un lote
	 * @param id identificador estado
	 * @param estadoActualizar estado a actualizar
	 */
	public void actualizarEstadoLote(Integer id, BeanEstado estadoActualizar);

}
