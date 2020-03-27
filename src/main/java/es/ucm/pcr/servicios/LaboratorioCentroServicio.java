package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;

public interface LaboratorioCentroServicio {
	
	Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda, Pageable pageable);
	PlacaLaboratorioCentroBean buscarPlaca (Integer id);
	void guardarPlaca (Integer id);

}
