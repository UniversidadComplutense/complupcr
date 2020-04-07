package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.repositorio.LoteRepositorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
@Service
public class ServicioLaboratorioVisavetUCMImpl implements ServicioLaboratorioVisavetUCM{
	
	@Autowired
    LoteServicio loteServicio;
	@Autowired
	LoteRepositorio loteRepositorio1;
	
	@Autowired
	PlacaVisavetRepositorio placaVisavetRepositorio;
	
	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	@Autowired
	SesionServicio sesionServicio;
	@Autowired
	private LoteRepositorio loteRepositorio;
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable){
		LoteBusquedaBean loteBusquedaBean= new LoteBusquedaBean();
		loteBusquedaBean.setIdCentro(busquedaLotes.getIdCentro());
	// se refiere a fecha recepcion
		loteBusquedaBean.setFechaEnvioFin(busquedaLotes.getFechaFinEntrada());
		loteBusquedaBean.setFechaEnvioIni(busquedaLotes.getFechaInicioEntrada());
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
				lotePlaca.setFechaEntrada(unResultado.getFechaEnvio());
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
	
	
	public LoteBeanPlacaVisavet buscarLote(Integer id) {
		Lote lote=loteServicio.findByIdLote(id);
		if (lote != null) {
			return LoteBeanPlacaVisavet.modelToBean(lote);
		}
		return null;
	}
	

	public Page<BeanPlacaVisavetUCM> buscarPlacas(BusquedaPlacasVisavetBean busqueda, Pageable pageable){
		
	busqueda.setIdLaboratorioVisavet(sesionServicio.getLaboratorioVisavet().getId());	
	busqueda.setMuestra(null);
	busqueda.setIdPlaca(null);
	busqueda.setNumLote(null);
	busqueda.setIdLaboratorioCentro(null);
	List<BeanPlacaVisavetUCM> listPlacaBean = new ArrayList<BeanPlacaVisavetUCM>();
	//List<PlacaVisavet> placasList =placaVisavetRepositorio.findByParams(busqueda); 
	Page<PlacaVisavet> placasPage =placaVisavetRepositorio.findByParams(busqueda, pageable); 
	//	Page<PlacaVisavet> placasPage = laboratorioVisavetRepositorio.findByParams(busqueda, pageable); 
//	Page<PlacaVisavet> placasPage = null;
		for (PlacaVisavet l : placasPage.getContent()) {
			listPlacaBean.add(BeanPlacaVisavetUCM.modelToBean(l));
		}
		
		Page<BeanPlacaVisavetUCM> paginasPlacas = new PageImpl<>(listPlacaBean, pageable, placasPage.getTotalElements());
		
		return paginasPlacas;
	}
	
	public BeanPlacaVisavetUCM guardar(BeanPlacaVisavetUCM beanPlacaVisavetUCM) {
	
		PlacaVisavet placa = BeanPlacaVisavetUCM.beanToModel(beanPlacaVisavetUCM);						
 
		// Placa nueva
		if (beanPlacaVisavetUCM.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_INICIADA.getCodNum()));
			
		}
		placa.setLaboratorioVisavet(new LaboratorioVisavet(sesionServicio.getUsuario().getIdLaboratorioVisavet()));
		placa = placaVisavetRepositorio.save(placa);
		return BeanPlacaVisavetUCM.modelToBean(placa);

	}
	
	
	public BeanPlacaVisavetUCM guardarConLote(BeanPlacaVisavetUCM beanPlacaVisavetUCM) {
		
		PlacaVisavet placa = BeanPlacaVisavetUCM.beanToModel(beanPlacaVisavetUCM);						
 
		// Placa nueva
		if (beanPlacaVisavetUCM.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_INICIADA.getCodNum()));
			
		}
		placa.setLaboratorioVisavet(new LaboratorioVisavet(sesionServicio.getUsuario().getIdLaboratorioVisavet()));
	
		
		
		
	/*	if (placa.getId()!= null) {
			for (Lote l: placa.getLotes()) {
				Lote lbbdd = loteRepositorio1.findById(l.getId()).get();
				lbbdd.setPlacaVisavet(placa);
				lbbdd =loteRepositorio1.save(lbbdd);
				
			 // aqui tengo que asignarle a placa los lotes con el nuevo id
			}
		} */
		placa = placaVisavetRepositorio.save(placa);
		// por cada placa tengo que guardar el lote
		if (placa.getId()!=null) {
			for (LoteBeanPlacaVisavet loteB: beanPlacaVisavetUCM.getListaLotes()) {
				Lote l=LoteBeanPlacaVisavet.beanToModel(loteB);
				l.setPlacaVisavet(placa);
				loteRepositorio.save(l);
			}
		}
		return BeanPlacaVisavetUCM.modelToBean(placa);

	}
	
	public BeanPlacaVisavetUCM buscarPlacaById(Integer id) {
		Optional<PlacaVisavet> placa=placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) 
			return 	BeanPlacaVisavetUCM.modelToBean(placa.get());
		else return null;
	}
	public BeanPlacaVisavetUCM guardarPlacaConLaboratorio(BeanPlacaVisavetUCM placaVisavet, Integer idLaboratorio) {
		Optional<PlacaVisavet> placaOp=placaVisavetRepositorio.findById(placaVisavet.getId());
		Optional<LaboratorioCentro> laboratorioOp= laboratorioCentroRepositorio.findById(idLaboratorio);
		PlacaVisavet placa=null;
		if (placaOp.isPresent()) {
			placa=placaOp.get();
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(placaVisavet.getEstado().getEstado().getCodNum()));
				if (laboratorioOp.isPresent())
					placa.setLaboratorioCentro(laboratorioOp.get());
		placa=placaVisavetRepositorio.save(placa);
		}
	return	BeanPlacaVisavetUCM.modelToBean(placa);
	
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
