package es.ucm.pcr.servicios;

import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;

@Service
public class LaboratorioCentroServicioImp implements LaboratorioCentroServicio{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LaboratorioCentroServicioImp.class);

	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
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
