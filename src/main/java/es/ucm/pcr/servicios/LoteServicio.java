package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

}
