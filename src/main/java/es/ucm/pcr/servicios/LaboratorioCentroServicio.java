package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;

public interface LaboratorioCentroServicio {
	
	/**
	 * Hago el mapeo del bean a la entidad LaboratorioCentro
	 * 
	 * @param BeanLaboratorioCentro
	 * @return LaboratorioCentro
	 */
	public LaboratorioCentro mapeoBeanEntidadLaboratorioCentro(BeanLaboratorioCentro beanLaboratorioCentro) throws Exception;
	
	/**
	 * Hago el mapeo de la entidad al bean LaboratorioCentro
	 * 
	 * @param LaboratorioCentro
	 * @return BeanLaboratorioCentro
	 */
	public BeanLaboratorioCentro mapeoEntidadBeanLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception;

	Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda, Pageable pageable);
	PlacaLaboratorioCentroBean buscarPlaca (Integer id);
	PlacaLaboratorioCentroBean guardarPlaca (PlacaLaboratorioCentroBean placaLaboratorioCentroBean);	
	
	
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda, Pageable pageable); 
	
	public PlacaLaboratorioCentroBean guardarCogerODevolverPlaca(Integer idPlaca, Integer idUsuario, String accion);
}	
