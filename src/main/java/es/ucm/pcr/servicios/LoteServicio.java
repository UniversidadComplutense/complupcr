package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.modelo.orm.Lote;

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
	 * Busca los lotes de un centro en un determinado estado
	 * @param idCentro id centro
	 * @param idsEstado estados lote
	 * @return
	 */
	public List<LoteListadoBean> findLoteByEstados(Integer idCentro, List<Integer> idsEstado);
	
	/**
	 * Busca los lotes que cumplan una referencia interna de lote
	 * @param referenciaInterna 
	
	 * @return
	 */
	public List<LoteListadoBean> findLoteByReferenciaExterna(Integer referenciaExterna);
	
	
	
	
	/**
	 * Guardar lote 
	 * @param loteBean
	 * @return lote guardado
	 */
	public LoteCentroBean guardar(LoteCentroBean loteBean);
	
	/**
	 * Guardar todo el lote 
	 * @param loteBean
	 * @return lote guardado
	 */
	public LoteCentroBean guardarLote(LoteCentroBean loteBean);
	/**
	 * Busca loteCentroBean por id
	 * @param id
	 * @return LoteCentroBean
	 */
	public LoteCentroBean findById(Integer id);
	
	/**
	 * Busca lote por id
	 * @param id
	 * @return Lote
	 */
	public Lote findByIdLote(Integer id);
	
	/**
	 * Actualiza el estado de un lote
	 * @param id identificador estado
	 * @param estadoActualizar estado a actualizar
	 */
	public void actualizarEstadoLote(LoteCentroBean loteBean, BeanEstado estadoActualizar);
	
	
	
	
	public boolean borrar(Integer id);
	
	
	
	public LoteBeanPlacaVisavet findByIdByPlacas(Integer id);
	

}
