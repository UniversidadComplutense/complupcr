package es.ucm.pcr.servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;

@Service
public class LaboratorioCentroServicioImp implements LaboratorioCentroServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LaboratorioCentroServicioImp.class);

	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	
	@Override
	public List<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<PlacaLaboratorioCentroBean> buscarPlacasPaginable(BusquedaPlacaLaboratorioBean criteriosBusqueda,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlacaLaboratorioCentroBean buscarPlaca(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarPlaca(Integer id) {
		// TODO Auto-generated method stub

	}
}
