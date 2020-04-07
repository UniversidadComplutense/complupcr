package es.ucm.pcr.servicios;

import es.ucm.pcr.beans.BeanEstado;

public interface ServicioLog {

	/**
	 * Actualiza el estado del log de una muestra
	 * 
	 * @param idMuestra
	 * @param estado
	 */
	public void actualizarEstadoMuestra(Integer idMuestra, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de un lote
	 * 
	 * @param idLote
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorLote(Integer idLote, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de una Placa visavet
	 * 
	 * @param idPlacaVisavet
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorPlacaVisavet(Integer idPlacaVisavet, BeanEstado estado);

	/**
	 * Actualiza el estado del log de todas las muestras de una Placa de laboratorio
	 * 
	 * @param idPlacaLaboratorio
	 * @param estado
	 */
	public void actualizarEstadoMuestraPorPlacaLaboratorio(Integer idPlacaLaboratorio, BeanEstado estado);

}
