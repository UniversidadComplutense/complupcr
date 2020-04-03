package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BeanPlacaVisavetUCM;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.repositorio.LoteRepositorio;
@Service
public class ServicioLaboratorioVisavetUCMImpl implements ServicioLaboratorioVisavetUCM{
	
	@Autowired
    LoteServicio loteServicio;
	
	@Autowired
	private LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;

	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable){
		LoteBusquedaBean loteBusquedaBean= new LoteBusquedaBean();
		loteBusquedaBean.setIdCentro(busquedaLotes.getIdCentro());
	// se refiere a fecha recepcion
		loteBusquedaBean.setFechaEnvioFin(busquedaLotes.getFechaEntrada());
		loteBusquedaBean.setNumLote(busquedaLotes.getNumLote());
		// por muestra aun no esta
		loteBusquedaBean.setIdEstado(busquedaLotes.getCodNumEstadoSeleccionado());
		//loteBusquedaBean.setIdLaboratorio("");
	
		
		Page <LoteListadoBean> pageResultados=loteServicio.findLoteByParam(loteBusquedaBean,pageable);
		
		List<LoteBeanPlacaVisavet> listaBeans = new ArrayList<LoteBeanPlacaVisavet>();
			
			for (LoteListadoBean unResultado: pageResultados.getContent()) {
				LoteBeanPlacaVisavet lotePlaca= new LoteBeanPlacaVisavet();
				lotePlaca.setId(unResultado.getId());
				lotePlaca.setNumLote(unResultado.getNumLote());
				lotePlaca.setCentroProcedencia(unResultado.getCentroBean().getNombre());
				// necesito el BeanEstado
				lotePlaca.setEstado(unResultado.getBeanEstado());
				//lotePlaca.setEstado(unResultado.);
				List<MuestraBeanLaboratorioVisavet>  listaMuestrasVisavet= new ArrayList<MuestraBeanLaboratorioVisavet>();
				for(MuestraListadoBean muestra : unResultado.getMuestras()) {
			 
			     MuestraBeanLaboratorioVisavet muestraBeanLaboratorioVisavet = new MuestraBeanLaboratorioVisavet();
			     muestraBeanLaboratorioVisavet.setId(muestra.getId());
			     muestraBeanLaboratorioVisavet.setEtiqueta(muestra.getEtiquetaMuestra());
			     muestraBeanLaboratorioVisavet.setTipoMuestra(muestra.getTipoMuestra());
			     muestraBeanLaboratorioVisavet.setReferenciaInterna(muestra.getRefInternaMuestra());
			     //if (listaMuestrasVisavet == null) new ArrayList<MuestraBeanLaboratorioVisavet>();
			     listaMuestrasVisavet.add(muestraBeanLaboratorioVisavet);
				}
				lotePlaca.setListaMuestras(listaMuestrasVisavet);
				listaBeans.add(lotePlaca);
			}
		
			
		
		Page<LoteBeanPlacaVisavet> pageLote=new PageImpl<LoteBeanPlacaVisavet>(listaBeans, pageable,
				pageResultados.getTotalElements());
		return pageLote;
	}

	public Page<BeanPlacaVisavetUCM> buscarPlacas(BusquedaPlacasVisavetBean busqueda, Pageable pageable){
		
		
	List<BeanPlacaVisavetUCM> listPlacaBean = new ArrayList<BeanPlacaVisavetUCM>();
		
	//	Page<PlacaVisavet> placasPage = laboratorioVisavetRepositorio.findByParams(busqueda, pageable); 
	Page<PlacaVisavet> placasPage = null;
		for (PlacaVisavet l : placasPage.getContent()) {
			//listPlacaBean.add(BeanPlacaVisavetUCM.modelToBean(l));
		}
		
		Page<BeanPlacaVisavetUCM> paginasPlacas = new PageImpl<>(listPlacaBean, pageable, placasPage.getTotalElements());
		
		return paginasPlacas;
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
