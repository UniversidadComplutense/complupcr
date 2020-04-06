package es.ucm.pcr.servicios;

import java.util.List;
import java.util.Map;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.beans.BeanLaboratorioVisavet;
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
	public Map<Integer,String> mapaCentros (List<BeanCentro> centros) throws Exception;
	
	/**
	 * Guardar Centro
	 * 
	 * @param Centro
	 * @return void >
	 */
	public void guardarCentro (Centro centro) throws Exception;
	
	/**
	 * Borrar Centro
	 * 
	 * @param Integer idCentro
	 * @return void >
	 */
	public void BorrarCentro (Integer idCentro) throws Exception;


}
