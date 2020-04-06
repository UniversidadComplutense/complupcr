package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BeanPlacaVisavetUCM;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteCentroBean;
@Service
public interface ServicioLaboratorioVisavetUCM {
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable);
	public Page<BeanPlacaVisavetUCM> buscarPlacas(BusquedaPlacasVisavetBean busqueda, Pageable pageable);
	public BeanPlacaVisavetUCM guardar(BeanPlacaVisavetUCM beanPlacaVisavetUCM);
	public BeanPlacaVisavetUCM guardarConLote(BeanPlacaVisavetUCM beanPlacaVisavetUCM);
	public LoteBeanPlacaVisavet buscarLote(Integer id);
	
	
	
	/**
	 * Recupera laboratorios con datos simples, id, nombre, capacida y ocupacion
	 * @return
	 */
	public List<BeanLaboratorioVisavet> findAll();
}
