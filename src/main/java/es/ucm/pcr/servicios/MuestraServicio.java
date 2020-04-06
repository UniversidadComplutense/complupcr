package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanListadoMuestraAnalisis;
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
	 * Recupera todas las muestras en funcion de los parametros de busqueda
	 * @param params
	 * @return
	 */
	public List<MuestraListadoBean> findMuestraByParam(MuestraBusquedaBean params);
	
	
	
	
	/**
	 * Recupera todos las muestras en funcion de los parametros de busqueda
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<BeanListadoMuestraAnalisis> findMuestraByParam(BeanBusquedaMuestraAnalisis params, Pageable pageable);
	
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
	
	/**
	 * Actualiza la muestra rellenando la fecha de notificacion de la muestra
	 * si enviarMail, envio de mail
	 * @param id idenficador muestra
	 * @param enviarMail envio mail si true, no envio en caso contrario
	 */
	public void actualizarNotificacionMuestra(Integer id, boolean enviarMail);
	
	/**
	 * Valida si la muestra se puede borrar
	 * @param id
	 * @return
	 */
	public boolean validateBorrar(Integer id);
	
	/**
	 * Borrar muestra
	 * @param id identificador de la muestra a borrar
	 * @return
	 */
	public boolean borrar(Integer id);

}
