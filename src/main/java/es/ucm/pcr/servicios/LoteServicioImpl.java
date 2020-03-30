package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
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
		Lote lote = LoteCentroBean.beanToModel(loteBean);
		lote = loteRepositorio.save(lote);
		return LoteCentroBean.modelToBean(lote);
	}
	
	@Override
	public LoteCentroBean findById(Integer id) {
		Optional<Lote> lote = loteRepositorio.findById(id);
		if (lote.isPresent()) {
			return LoteCentroBean.modelToBean(lote.get());
		}
		return new LoteCentroBean();
	}
}
