package es.ucm.pcr.servicios;

import java.util.Optional;

import es.ucm.pcr.beans.BeanLaboratorioCentro;
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
}	
