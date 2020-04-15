package es.ucm.pcr.servicios;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

	/**
	 * Guardar Laboratorio Visavet
	 * 
	 * @param LaboratorioVisavet
	 * @return void >
	 */
	public LaboratorioVisavet save(LaboratorioVisavet laboratorioVisavet) throws Exception;
	
	/**
	 * Borrar Laboratorio Visavet
	 * 
	 * @param Integer idLaboratorioVisavet
	 * @return void >
	 */
	public void deleteById(Integer idLaboratorioVisavet) throws Exception;	

	/**
	 * Buscar Laboratorio por Id
	 * 
	 * @param Integer idLaboratorioVisavet
	 * @return void Optional <LaboratorioVisavet>
	 */
	public Optional <LaboratorioVisavet> findById(Integer idLaboratorioVisavet) throws Exception;

	
	// JAVI
	public Page<PlacaLaboratorioVisavetBean> buscarPlacas(BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			Pageable pageable);
	// JAVI
	public PlacaLaboratorioVisavetBean buscarPlaca(Integer id);

	// JAVI
	public void recepcionarPlaca(Integer id);
	
	// JAVI
	public List<PlacaLaboratorioVisavetBean> buscarPlacasPorIdPlacaLaboratorio(Integer idPlacaLaboratorio);
	
	/**
	 * Buscar laboratorio visavet por nombre
	 * 
	 * @param nombre
	 * @return Optional<LaboratorioVisavet>
	 */
	public Optional<LaboratorioVisavet> findByNombre(String nombre);
	
	
}
