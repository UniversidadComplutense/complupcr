package es.ucm.pcr.servicios;


import es.ucm.pcr.beans.BeanLaboratorioVisavet;

import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

public interface LaboratorioVisavetServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad LaboratorioVisavet
	 * 
	 * @param BeanLaboratorioVisavet
	 * @return LaboratorioVisavet
	 */
	public LaboratorioVisavet mapeoBeanEntidadLaboratorioVisavet(BeanLaboratorioVisavet beanLaboratorioVisavet) throws Exception;
	
	/**
	 * Hago el mapeo de la entidad al bean LaboratorioVisavet
	 * 
	 * @param LaboratorioVisavet
	 * @return BeanLaboratorioVisavet
	 */
	public BeanLaboratorioVisavet mapeoEntidadBeanLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) throws Exception;
	

}
