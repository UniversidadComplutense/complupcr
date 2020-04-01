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
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PacienteRepositorio;

@Service
public class MuestraServicioImpl implements MuestraServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MuestraServicioImpl.class);
	
	@Autowired
	private MuestraRepositorio muestraRepositorio;
	
	@Autowired
	private PacienteRepositorio pacienteRepositorio;
	
	@Override
	public Page<MuestraListadoBean> findMuestraByParam(MuestraBusquedaBean params, Pageable pageable) {
		List<MuestraListadoBean> listLotesBean = new ArrayList<MuestraListadoBean>();
		
		Page<Muestra> muestrasPage = muestraRepositorio.findByParams(params, pageable); 
		
		for (Muestra m : muestrasPage.getContent()) {
			listLotesBean.add(MuestraListadoBean.modelToBean(m));
		}
		
		Page<MuestraListadoBean> lotesCentro = new PageImpl<>(listLotesBean, pageable, muestrasPage.getTotalElements());
		
		return lotesCentro;
	}

	@Override
	public MuestraCentroBean guardar(MuestraCentroBean muestraBean) {
		Muestra muestra = null;						

		// Lote nuevo, resultado pendiente
		if (muestraBean.getId() == null) {
			muestraBean.setResultado(BeanResultado.ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE.getCod());		
		} 
		
		muestra = MuestraCentroBean.beanToModel(muestraBean);
		
		muestra.setLote(null);
		muestra.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_INICIADA.getCodNum()));
		if (muestraBean.getIdLote() != null) {
			muestra.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ASIGNADA_LOTE.getCodNum()));
			muestra.setLote(new Lote(muestraBean.getIdLote()));
		} 
		
		Paciente paciente = muestra.getPaciente();
		muestra = muestraRepositorio.save(muestra);
		paciente.setMuestra(muestra);
		pacienteRepositorio.save(paciente);
		return MuestraCentroBean.modelToBean(muestra);
	}
	
	@Override
	public MuestraCentroBean findById(Integer id) {
		Muestra muestra = findByIdMuestra(id);
		if (muestra != null) {
			return MuestraCentroBean.modelToBean(muestra);
		}
		return new MuestraCentroBean();
	}

	@Override
	public void actualizarEstadoMuestra(Integer id, BeanEstado estadoActualizar) {
		Muestra muestra = findByIdMuestra(id);
		if (muestra != null) {
			
			// si actualizamos el lote a envidado ademas rellenamos la fecha de envio
			if (estadoActualizar.getEstado().getCodNum() == Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum()) {
				muestra.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum()));
				muestra.setFechaEnvio(new Date());
			}
			
			if (estadoActualizar.getEstado().getCodNum() == Estado.MUESTRA_PENDIENTE_ANALIZAR.getCodNum()) {
				// TODO - ACCIONES ACTUALIZAR ESTADO MUESTRA				
			}
			if (estadoActualizar.getEstado().getCodNum() == Estado.MUESTRA_ASIGNADA_ANALISTA.getCodNum()) {
				// TODO - ACCIONES ACTUALIZAR ESTADO MUESTRA				
			}
			if (estadoActualizar.getEstado().getCodNum() == Estado.MUESTRA_RESUELTA.getCodNum()) {
				// TODO - ACCIONES ACTUALIZAR ESTADO MUESTRA				
			}
		}	
		muestraRepositorio.save(muestra);
	}
	
	/**
	 * Recupera el lote
	 * @param id
	 * @return
	 */
	private Muestra findByIdMuestra(Integer id) {
		Optional<Muestra> loteOptional = muestraRepositorio.findById(id);
		if (loteOptional.isPresent()) {
			return loteOptional.get();
		}
		return null;
	}
}
