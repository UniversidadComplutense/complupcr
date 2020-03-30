package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;

public interface LaboratorioCentroServicio {
	
	List<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda);
	Page<PlacaLaboratorioCentroBean> buscarPlacasPaginable(BusquedaPlacaLaboratorioBean criteriosBusqueda, Pageable pageable);
	PlacaLaboratorioCentroBean buscarPlaca (Integer id);
	void guardarPlaca (Integer id);

}
