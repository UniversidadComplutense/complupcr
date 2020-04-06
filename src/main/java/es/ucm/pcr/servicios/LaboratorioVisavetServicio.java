package es.ucm.pcr.servicios;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
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
	
	/**
	 * Lista ordenada de bean LaboratorioVisavet
	 * 
	 * @param 
	 * @return BeanLaboratorioVisavet
	 */
	public List<BeanLaboratorioVisavet> listaLaboratoriosVisavetOrdenada() throws Exception;	

	/**
	 * Mapa de  LaboratorioVisavet
	 * 
	 * @param List<BeanLaboratorioVisavet>
	 * @return Map<String,String>
	 */
	public Map<Integer,String> mapaLaboratoriosVisavet (List<BeanLaboratorioVisavet> laboratoriosVisavet) throws Exception;


	
	// JAVI
	public Page<PlacaLaboratorioVisavetBean> buscarPlacas(BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			Pageable pageable);
	// JAVI
	public PlacaLaboratorioVisavetBean buscarPlaca(Integer id);

	// JAVI
	public void recepcionarPlaca(Integer id);
	
	
	
	
}
