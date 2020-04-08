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
import org.springframework.util.CollectionUtils;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.repositorio.LoteRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;

@Service
public class LoteServicioImpl implements LoteServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoteServicioImpl.class);
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Autowired
	private MuestraRepositorio muestraRepositorio;
	
	@Autowired
	private ServicioLog servicioLog;
	
	@Override
	public Page<LoteListadoBean> findLoteByParam(LoteBusquedaBean params, Pageable pageable) {
		List<LoteListadoBean> listLotesBean = new ArrayList<LoteListadoBean>();
		
		Page<Lote> lotesPage = loteRepositorio.findByParams(params, pageable); 
		
		for (Lote l : lotesPage.getContent()) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		Page<LoteListadoBean> lotesCentro = new PageImpl<>(listLotesBean, pageable, lotesPage.getTotalElements());
		
		return lotesCentro;
	}
	
	@Override
	public List<LoteListadoBean> findLoteByParam(LoteBusquedaBean params) {
		List<LoteListadoBean> listLotesBean = new ArrayList<LoteListadoBean>();
		
		List<Lote> lotes = loteRepositorio.findByParams(params); 
		
		for (Lote l : lotes) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		return listLotesBean;
	}
	
	@Override
	public List<LoteListadoBean> findLoteByEstados(Integer idCentro, List<Integer> idsEstado) {
		List<LoteListadoBean> listLotesBean = new ArrayList<LoteListadoBean>();
		
		List<Lote> lotes = loteRepositorio.findByEstado(idCentro, idsEstado); 
		
		for (Lote l : lotes) {
			listLotesBean.add(LoteListadoBean.modelToBean(l));
		}
		
		return listLotesBean;
	}

	@Override
	public LoteCentroBean guardar(LoteCentroBean loteBean) {
		Lote lote = null;
		
		lote = LoteCentroBean.beanToModel(loteBean);
				
		lote.setEstadoLote(new EstadoLote(Estado.LOTE_INICIADO.getCodNum()));
		if (loteBean.getIdLaboratorio() != null) {
			lote.setEstadoLote(new EstadoLote(Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum()));
		}
		
		lote = loteRepositorio.save(lote);
		return LoteCentroBean.modelToBean(lote);
	}
	
	@Override
	public LoteCentroBean findById(Integer id) {
		Lote lote = findByIdLote(id);
		if (lote != null) {
			return LoteCentroBean.modelToBean(lote);
		}
		return new LoteCentroBean();
	}

	@Override
	public void actualizarEstadoLote(LoteCentroBean loteBean, BeanEstado estadoActualizar) {
		Lote lote = findByIdLote(loteBean.getId());
		if (lote != null) {
			
			// si actualizamos el lote a envidado ademas rellenamos la fecha de envio
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum()) {
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum()));
				lote.setFechaEnvio(new Date());

				// se actualiza el estado y la fecha de cada muestra del lote
				if (!CollectionUtils.isEmpty(lote.getMuestras())) {
					for (Muestra m : lote.getMuestras()) {
						m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum()));
						m.setFechaEnvio(new Date());
						
						BeanEstado estadoMuestra = new BeanEstado();
						estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
						servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
					}
					muestraRepositorio.saveAll(lote.getMuestras());
				}
				lote.setLaboratorioVisavet(new LaboratorioVisavet(loteBean.getIdLaboratorio()));
			}
			
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()) {
				// TODO - ACTUALIZAR ESTADO LOTE RECIBIDO
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()));
				lote.setFechaRecibido(new Date());
			}
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_PROCESADO_CENTRO_ANALISIS.getCodNum()) {
				// TODO - ACTUALIZAR ESTADO LOTE PROCESADO
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_PROCESADO_CENTRO_ANALISIS.getCodNum()));
			}
			
		}	
		loteRepositorio.save(lote);
	}	
	
	/**
	 * Recupera el lote
	 * @param id
	 * @return
	 */
	@Override
	public Lote findByIdLote(Integer id) {
		Optional<Lote> loteOptional = loteRepositorio.findById(id);
		if (loteOptional.isPresent()) {
			return loteOptional.get();
		}
		return null;
	}

	@Override
	public boolean borrar(Integer id) {
		try {
			loteRepositorio.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error("ERROR:: borrar " + e);
			return false;
		}
	}
	
	
	// yoli
	@Override
	public LoteBeanPlacaVisavet findByIdByPlacas(Integer id) {
		Lote lote = findByIdLote(id);
		if (lote != null) {
			return LoteBeanPlacaVisavet.modelToBean(lote);
		}
		return new LoteBeanPlacaVisavet();
	}
	
}
