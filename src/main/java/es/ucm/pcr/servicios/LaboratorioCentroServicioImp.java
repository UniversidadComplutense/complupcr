package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;

@Service
public class LaboratorioCentroServicioImp implements LaboratorioCentroServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LaboratorioCentroServicioImp.class);

	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	
	
	@Override
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroBean> listaPlacasLaboratorioCentroBean = new ArrayList<PlacaLaboratorioCentroBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = laboratorioCentroRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroBean.add(PlacaLaboratorioCentroBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}

	
	@Override
	public PlacaLaboratorioCentroBean guardarPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean) {
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);
		placa = laboratorioCentroRepositorio.save(placa);
		return PlacaLaboratorioCentroBean.modelToBean(placa);

	}
	
	
	@Override
	public PlacaLaboratorioCentroBean buscarPlaca(Integer id) {
		Optional<PlacaLaboratorio> placa = laboratorioCentroRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioCentroBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioCentroBean();
	}
}
