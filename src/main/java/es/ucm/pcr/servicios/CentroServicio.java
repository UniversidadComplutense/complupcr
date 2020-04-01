package es.ucm.pcr.servicios;

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


}
