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
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCMImpl.AnalisisExcelMuestras;
@Service
public interface ServicioLaboratorioVisavetUCM {
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable);
	public Page<BeanPlacaVisavetUCM> buscarPlacas(BusquedaPlacasVisavetBean busqueda, Pageable pageable);
	public BeanPlacaVisavetUCM guardar(BeanPlacaVisavetUCM beanPlacaVisavetUCM);
	public BeanPlacaVisavetUCM guardarConLote(BeanPlacaVisavetUCM beanPlacaVisavetUCM);
	public LoteBeanPlacaVisavet buscarLote(Integer id);
	public BeanPlacaVisavetUCM buscarPlacaById(Integer id);
	
	public BeanPlacaVisavetUCM guardarPlacaConLaboratorio(BeanPlacaVisavetUCM placaVisavet, Integer laboratorio);
	/**
	 * Recupera laboratorios con datos simples, id, nombre, capacida y ocupacion
	 * @return
	 */
	public List<BeanLaboratorioVisavet> findAll();
	
	public void guardarReferenciasMuestraPlaca(ElementoDocumentacionBean bean)throws Exception ;
	public BeanPlacaVisavetUCM eliminarLotedePlaca(int idLote);
	public void eliminarPlaca(int idPlaca);
	public void eliminarPlacayLotes(int idPlaca);
	public void procesarExcel(ElementoDocumentacionBean edb, Integer idCentro, Integer tamanoPlaca) throws Exception;
	public AnalisisExcelMuestras verificarExcel(ElementoDocumentacionBean edb, Integer tamanoPlaca) throws Exception;
}
