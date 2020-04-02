package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
@Service
public interface ServicioLaboratorioVisavetUCM {
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable);

	
	
	
	
	
	
	
	/**
	 * Recupera laboratorios con datos simples, id, nombre, capacida y ocupacion
	 * @return
	 */
	public List<BeanLaboratorioVisavet> findAll();
}
