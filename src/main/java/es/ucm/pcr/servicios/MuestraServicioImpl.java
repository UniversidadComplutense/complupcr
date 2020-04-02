package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
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
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.repositorio.LoteRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PacienteRepositorio;
import es.ucm.pcr.utilidades.Enviocorreo;

@Service
public class MuestraServicioImpl implements MuestraServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MuestraServicioImpl.class);
	
	@Autowired
	private MuestraRepositorio muestraRepositorio;
	
	@Autowired
	private PacienteRepositorio pacienteRepositorio;
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Autowired
	private Enviocorreo envioCorreoImp;	
	
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
		
		muestra = MuestraCentroBean.beanToModel(muestraBean);

		// Lote nuevo, resultado pendiente
		if (muestraBean.getId() == null) {
			muestra.setResultado(BeanResultado.ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE.getCod());		
		}
		
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
		return MuestraCentroBean.modelToBean(findByIdMuestra(muestra.getId()));
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
	
	@Override
	public void actualizarNotificacionMuestra(Integer id, boolean enviarMail) {
		Muestra muestra = findByIdMuestra(id);
		if (muestra != null) {
			muestra.setFechaNotificacion(new Date());
			muestraRepositorio.save(muestra);
			
			if (enviarMail) {
				envioCorreoImp.send(muestra.getPaciente().getEmail(), "Notificación", mensajeEmail(muestra), null, "",pieEmail(muestra.getCentro()),""); 
			}			
		}		
	}
	
	/**
	 * Recupera el lote
	 * @param id
	 * @return
	 */
	private Muestra findByIdMuestra(Integer id) {
		Muestra muestra = null;
		Optional<Muestra> loteOptional = muestraRepositorio.findById(id);
		if (loteOptional.isPresent()) {
			muestra = loteOptional.get();
			
			// TODO - algo pasa con el lazy que no me trae el estado del lote...¿?¿?	
			if (muestra.getLote() != null) {
				Lote l = loteRepositorio.findById(muestra.getLote().getId()).get();
				muestra.setLote(l);
			}
		}
		
		// TODO - algo pasa con el lazy que no me trae el estado del lote...
		/*muestra.getLote().getId();
		muestra.getLote().getEstadoLote().getId();*/
		return muestra;
	}
	
	private String mensajeEmail(Muestra muestra) {
		
		StringBuffer mensaje = new StringBuffer("<div style='font-size: 12px !important;text-align:justify !important;'>");
		
		if (muestra != null) {

			BeanResultado resultado = new BeanResultado();
			resultado.asignarTipoEstadoYCodNum(muestra.getResultado());
			
			mensaje.append("El análisis de la muestra del paciente <b>").append(muestra.getPaciente().getNombrePaciente()).append("</b>");
			if (StringUtils.isNotEmpty(muestra.getPaciente().getApellido1paciente())) {
				mensaje.append(" <b>").append(muestra.getPaciente().getApellido1paciente()).append("</b>");
			}
			if (StringUtils.isNotEmpty(muestra.getPaciente().getApellido2paciente())) {
				mensaje.append(" <b>").append(muestra.getPaciente().getApellido2paciente()).append("</b>");
			}
			mensaje.append(", con número de historial clínico <b>").append(muestra.getPaciente().getNhc()).append("</b>");
			mensaje.append(" ha resultado <b>").append(resultado.getResultadoMuestra().getDescripcion()).append("</b>");
			mensaje.append("<br><br> Si tiene cualquier consulta no dude en contactarnos");
		}
		mensaje.append("</div>");
		return mensaje.toString();
	}
	
	private String pieEmail(Centro centro) {
		
		
		StringBuffer mensaje = new StringBuffer("<div style='font-size: 12px !important;text-align:justify !important;margin-top:20px'>");
		if (centro != null) {
			mensaje.append("Centro de salud <b>").append(centro.getNombre()).append("</b><br>");
			mensaje.append("&nbsp;Dirección <b>").append(centro.getDireccion()).append("</b><br>");
			mensaje.append("&nbsp;Email <b>").append(centro.getEmail()).append("</b><br>");
			mensaje.append("&nbsp;Teléfono <b>").append(centro.getTelefono()).append("</b><br>");
		}
		mensaje.append("</div>");
		return mensaje.toString();
	}
}
