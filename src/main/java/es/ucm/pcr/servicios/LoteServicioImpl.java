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

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.repositorio.LoteRepositorio;

@Service
public class LoteServicioImpl implements LoteServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoteServicioImpl.class);
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
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
	public LoteCentroBean guardar(LoteCentroBean loteBean) {
		Lote lote = null;
		
		// Lote nuevo
		if (loteBean.getId() == null) {
			// TODO - COMPLETAR MUESTRAS
			loteBean.setEstado(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_INICIADO));
			if (loteBean.getIdLaboratorio() != null) {
				loteBean.setEstado(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_ASIGNADO_CENTRO_ANALISIS));
			}
			lote = LoteCentroBean.beanToModel(loteBean);
		} else {
			// Existe lote
			lote = findByIdLote(loteBean.getId());
			if (lote != null) {
				lote.setNumeroLote(loteBean.getNumLote());
				lote.setCapacidad(loteBean.getCapacidad());				
				// TODO - puede cambiar laboratorio
			}
		}
		
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
	public void actualizarEstadoLote(Integer id, BeanEstado estadoActualizar) {
		Lote lote = findByIdLote(id);
		if (lote != null) {
			
			// si actualizamos el lote a envidado ademas rellenamos la fecha de envio
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum()) {
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum()));
				lote.setFechaEnvio(new Date());
			}
			
			if (estadoActualizar.getEstado().getCodNum() == Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()) {
				// TODO - ACTUALIZAR ESTADO LOTE RECIBIDO
				lote.setEstadoLote(new EstadoLote(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()));
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
	private Lote findByIdLote(Integer id) {
		Optional<Lote> loteOptional = loteRepositorio.findById(id);
		if (loteOptional.isPresent()) {
			return loteOptional.get();
		}
		return null;
	}
}
