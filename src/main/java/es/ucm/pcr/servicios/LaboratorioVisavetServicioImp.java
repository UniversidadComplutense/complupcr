package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;

@Service
public class LaboratorioVisavetServicioImp implements LaboratorioVisavetServicio{
	
	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	
	@Autowired
	PlacaVisavetRepositorio placaVisavetRepositorio;
	
	public LaboratorioVisavet mapeoBeanEntidadLaboratorioVisavet(BeanLaboratorioVisavet beanLaboratorioVisavet) throws Exception {
		LaboratorioVisavet laboratorioVisavet = new LaboratorioVisavet();
		
		laboratorioVisavet.setId(beanLaboratorioVisavet.getId());
		laboratorioVisavet.setNombre(beanLaboratorioVisavet.getNombre());	
		laboratorioVisavet.setCapacidad(beanLaboratorioVisavet.getCapacidad());
		laboratorioVisavet.setDocumentos(beanLaboratorioVisavet.getDocumentos());
		laboratorioVisavet.setPlacaVisavets(beanLaboratorioVisavet.getPlacaVisavets());
		
		return laboratorioVisavet;
	}

	public BeanLaboratorioVisavet mapeoEntidadBeanLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) throws Exception{
		BeanLaboratorioVisavet beanLaboratorioVisavet = new BeanLaboratorioVisavet();
		
		beanLaboratorioVisavet.setId(laboratorioVisavet.getId());
		beanLaboratorioVisavet.setNombre(laboratorioVisavet.getNombre());
		beanLaboratorioVisavet.setCapacidad(laboratorioVisavet.getCapacidad());
		beanLaboratorioVisavet.setDocumentos(laboratorioVisavet.getDocumentos());
		beanLaboratorioVisavet.setOcupacion(laboratorioVisavet.getOcupacion());
		beanLaboratorioVisavet.setPlacaVisavets(laboratorioVisavet.getPlacaVisavets());
		
		return beanLaboratorioVisavet;		
	}
	
	public List<BeanLaboratorioVisavet> listaLaboratoriosVisavetOrdenada() throws Exception{
		// cargo todos los laboratorioVisavets de BBDD
		List<BeanLaboratorioVisavet> listaLaboratorioVisavet = new ArrayList<BeanLaboratorioVisavet>();
		for (LaboratorioVisavet laboratorioVisavet: laboratorioVisavetRepositorio.findAll())
		{
			listaLaboratorioVisavet.add(
					new BeanLaboratorioVisavet( laboratorioVisavet.getId(), 
												laboratorioVisavet.getNombre(), 
												laboratorioVisavet.getCapacidad(),
												laboratorioVisavet.getOcupacion(),
												laboratorioVisavet.getDocumentos(),
												laboratorioVisavet.getPlacaVisavets(),
												"L"));
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaLaboratorioVisavet);
		return listaLaboratorioVisavet;

	}
	
	public Map<Integer,String> mapaLaboratoriosVisavet (List<BeanLaboratorioVisavet> laboratoriosVisavet) throws Exception{
		Map<Integer, String> mapalaboratorioVisavet = new HashMap<Integer, String>();
		for (BeanLaboratorioVisavet laboratorioVisavet :laboratoriosVisavet)
		{
			mapalaboratorioVisavet.put(laboratorioVisavet.getId(), laboratorioVisavet.getNombre());
		}
		return mapalaboratorioVisavet;
	}
	
	// JAVI para buscar placas Visavet e incorporarlas a 'BusquedaPlacasVisavetBean'
	@Override
	public Page<PlacaLaboratorioVisavetBean> buscarPlacas(BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioVisavetBean> listaPlacasVisavetBean = new ArrayList<PlacaLaboratorioVisavetBean>();		
		Page<PlacaVisavet> PagePlacasVisavet = placaVisavetRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaVisavet placa : PagePlacasVisavet.getContent()) {
			listaPlacasVisavetBean.add(PlacaLaboratorioVisavetBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioVisavetBean> placasVisavet = new PageImpl<>(listaPlacasVisavetBean, pageable, PagePlacasVisavet.getTotalElements());		
		return placasVisavet;
	}
	
	
	// JAVI para buscar una placa Visavet e incorporarla a 'BusquedaPlacasVisavetBean'
	@Override
	public PlacaLaboratorioVisavetBean buscarPlaca(Integer id) {
		Optional<PlacaVisavet> placa = placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioVisavetBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioVisavetBean();
	}
	
	
	// JAVI para recepcionar una placa Visavet
	@Override
	public void recepcionarPlaca(Integer id) {				
		Optional<PlacaVisavet> placa = placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) {
			placa.get().setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_RECIBIDA.getCodNum()));
			placaVisavetRepositorio.save(placa.get());
		}
	}

}
