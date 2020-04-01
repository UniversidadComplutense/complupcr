package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;

@Service
public class LaboratorioCentroServicioImp implements LaboratorioCentroServicio{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LaboratorioCentroServicioImp.class);

	@Autowired
	PlacaLaboratorioRepositorio laboratorioCentroRepositorio;
	
	@Autowired
	SesionServicio sesionServicio;
	
	
	public LaboratorioCentro mapeoBeanEntidadLaboratorioCentro(BeanLaboratorioCentro beanLaboratorioCentro) throws Exception{
		
		LaboratorioCentro laboratorioCentro = new LaboratorioCentro();
		
		laboratorioCentro.setId(beanLaboratorioCentro.getId());
		laboratorioCentro.setNombre(beanLaboratorioCentro.getNombre());	
		laboratorioCentro.setDocumentos(beanLaboratorioCentro.getDocumentos());
		laboratorioCentro.setEquipos(beanLaboratorioCentro.getEquipos());
		laboratorioCentro.setPlacaLaboratorios(beanLaboratorioCentro.getPlacaLaboratorios());
		
		return laboratorioCentro;
		
	}
	
	public BeanLaboratorioCentro mapeoEntidadBeanLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception{
		
		BeanLaboratorioCentro beanLaboratorioCentro = new BeanLaboratorioCentro();
		
		beanLaboratorioCentro.setId(laboratorioCentro.getId());
		beanLaboratorioCentro.setNombre(laboratorioCentro.getNombre());
		beanLaboratorioCentro.setDocumentos(laboratorioCentro.getDocumentos());
		beanLaboratorioCentro.setEquipos(laboratorioCentro.getEquipos());
		beanLaboratorioCentro.setPlacaLaboratorios(laboratorioCentro.getPlacaLaboratorios());
		
		return beanLaboratorioCentro;
		
	}
	
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

		// Placa nueva
		if (placaLaboratorioCentroBean.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_INICIADA.getCodNum()));
			placa.setLaboratorioCentro(new LaboratorioCentro(sesionServicio.getUsuario().getIdLaboratorioCentro()));
		}
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
