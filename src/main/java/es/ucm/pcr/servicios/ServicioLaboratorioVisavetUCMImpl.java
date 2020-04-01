package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
@Service
public class ServicioLaboratorioVisavetUCMImpl implements ServicioLaboratorioVisavetUCM{
	
	@Autowired
	private LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable){
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// A VER SI DEJANDO TODOS ESTOS ESPACIOS EVITAMOS MERGES COMPLICADOS :D
	@Override
	public List<BeanLaboratorioVisavet> findAll() {
		List<BeanLaboratorioVisavet> laboratoriosBean = new ArrayList<BeanLaboratorioVisavet>();
		List<LaboratorioVisavet> laboratorios = laboratorioVisavetRepositorio.findAll();
		
		if (!CollectionUtils.isEmpty(laboratorios)) {
			for (LaboratorioVisavet l : laboratorios) {
				laboratoriosBean.add(new BeanLaboratorioVisavet(l.getId(), l.getNombre(), l.getCapacidad(), l.getOcupacion(), null, null, null));
			}
		}
		
		return laboratoriosBean;
	}
	
	
}
