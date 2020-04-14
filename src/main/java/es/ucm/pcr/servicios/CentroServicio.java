package es.ucm.pcr.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.modelo.orm.Centro;

public interface CentroServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad Centro
	 * 
	 * @param BeanCentro
	 * @return Centro
	 */
	public Centro mapeoBeanEntidadCentro(BeanCentro beanCentro) throws Exception;
	
	/**
	 * Hago el mapeo de la entidad al bean Centro
	 * 
	 * @param Centro
	 * @return BeanCentro
	 */
	public BeanCentro mapeoEntidadBeanCentro(Centro centro) throws Exception;

	/**
	 * Lista de centros ordenada
	 * 
	 * @param 
	 * @return List<BeanCentro>
	 */
	public List<BeanCentro> listaCentrosOrdenada() throws Exception;
	
	/**
	 * Mapa de Centros
	 * 
	 * @param List<BeanCentro>
	 * @return Map<String,String>
	 */
	public Map<Integer,String> mapaCentros(List<BeanCentro> centros) throws Exception;
	
	/**
	 * Guardar Centro
	 * 
	 * @param Centro
	 * @return Centro >
	 */
	public Centro save(Centro centro) throws Exception;
	
	/**
	 * Borrar Centro
	 * 
	 * @param Integer idCentro
	 * @return void findById
	 */
	public void deleteById(Integer idCentro) throws Exception;

	/**
	 * Buscar Centro por id
	 * 
	 * @param Integer idCentro
	 * @return Optional<Centro>
	 */
	public Optional<Centro> findById(Integer idCentro) throws Exception;

	/**
	 * Buscar por codCentro
	 * @param codCentro
	 * @return Optional<Centro>
	 */
	public Optional<Centro> findByCodCentro(String codCentro);


}
