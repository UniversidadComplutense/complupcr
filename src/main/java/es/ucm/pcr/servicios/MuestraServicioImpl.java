/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanBusquedaMuestraAnalisis;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanListadoMuestraAnalisis;
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.LogMuestraListadoBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
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
	
	@Autowired
	private ServicioLog servicioLog;
	
	@Override
	@Transactional
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
	@Transactional
	public List<MuestraListadoBean> findMuestraByParam(MuestraBusquedaBean params) {
		List<MuestraListadoBean> muestrasListBean = new ArrayList<MuestraListadoBean>();
		
		List<Muestra> muestrasList = muestraRepositorio.findByParams(params); 
		
		for (Muestra m : muestrasList) {
			muestrasListBean.add(MuestraListadoBean.modelToBean(m));
		}
		
		return muestrasListBean;
	}
	
	
	
	//Diana- busqueda de muestras por el jefe de servicio (replica del de Tere con mi bean)
	@Override
	public Page<BeanListadoMuestraAnalisis> findMuestraByParam(BeanBusquedaMuestraAnalisis params, Pageable pageable) {		
		List<BeanListadoMuestraAnalisis> listMuestrasBean = new ArrayList<BeanListadoMuestraAnalisis>();
		
		Page<Muestra> muestrasPage = muestraRepositorio.findByParams(params, pageable); 
		
		for (Muestra m : muestrasPage.getContent()) {
			listMuestrasBean.add(BeanListadoMuestraAnalisis.modelToBean(m));
		}
		
		Page<BeanListadoMuestraAnalisis> pageMuestras = new PageImpl<>(listMuestrasBean, pageable, muestrasPage.getTotalElements());
		
		return pageMuestras;
	}
	
	@Override
	public Page<LogMuestraListadoBean> findMuestraByParam(LogMuestraBusquedaBean params, Pageable pageable) {		
		List<LogMuestraListadoBean> listMuestrasBean = new ArrayList<LogMuestraListadoBean>();
		
		Page<Muestra> muestrasPage = muestraRepositorio.findByParams(params, pageable); 
		
		for (Muestra m : muestrasPage.getContent()) {
			listMuestrasBean.add(LogMuestraListadoBean.modelMuestraToBean(m));
		}
		
		Page<LogMuestraListadoBean> pageMuestras = new PageImpl<>(listMuestrasBean, pageable, muestrasPage.getTotalElements());
		
		return pageMuestras;
	}
	
	@Override
	public BeanListadoMuestraAnalisis buscarMuestra(Integer id) {
		Optional<Muestra> muestra = muestraRepositorio.findById(id);
		if (muestra.isPresent()) {
			return BeanListadoMuestraAnalisis.modelToBean(muestra.get());
		}
		return new BeanListadoMuestraAnalisis();
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
		
		Muestra muestraBBDD = muestra.getId() != null ? muestraRepositorio.findById(muestra.getId()).get() : null;		
		boolean cambioEstado = cambioEstado(muestra, muestraBBDD);
		
		Paciente paciente = muestra.getPaciente();
		muestra = muestraRepositorio.save(muestra);
		paciente.setMuestra(muestra);
		pacienteRepositorio.save(paciente);				
		
		if (cambioEstado) {
			BeanEstado estadoMuestra = new BeanEstado();
			estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, muestra.getEstadoMuestra().getId());
			servicioLog.actualizarEstadoMuestra(muestra, muestra.getLote(), null, null, estadoMuestra);
		}
		
		return MuestraCentroBean.modelToBean(findByIdMuestra(muestra.getId()));
	}
	
	/**
	 * La muestra registra cambio de estado si es nueva o si el estado al que pasa es distinto al que tiene
	 * @param muestraBean
	 * @return
	 */
	private boolean cambioEstado(Muestra muestra, Muestra muestraBBDD) {
		if (muestra.getId() == null || muestraBBDD == null) {
			return true;
		} else {			
			return (muestra.getEstadoMuestra().getId().intValue() != muestraBBDD.getEstadoMuestra().getId().intValue()) ||
					(muestra.getLote().getId().intValue() != muestraBBDD.getLote().getId());
		}
	}
	
	@Override
	@Transactional
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
	
	@Override
	public boolean validateBorrar(Integer id) {
		List<Integer> estadosValidosBorrarMuestra = BeanEstado.getEstadosLotesDisponiblesCentro();
		Muestra muestra = findByIdMuestra(id);
		
		// si el lote esta enviado no se puede borrar la muestra
		if (muestra != null && (muestra.getLote() == null || (muestra.getLote() != null && estadosValidosBorrarMuestra.contains(muestra.getLote().getEstadoLote().getId())))) {
			return true;
		}
		return false;		
	}
	
	@Override
	public boolean borrar(Integer id) {
		try {
			servicioLog.borrarEstadosMuestra(id);
			muestraRepositorio.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error("ERROR:: borrar " + e);
			return false;
		}
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
	
	
	// guardar referencia interna muestra
	@Override
	public MuestraBeanLaboratorioVisavet guardarReferencia(MuestraBeanLaboratorioVisavet muestraBean) {
		Muestra muestra = null;						
		
		//muestra = MuestraBeanLaboratorioVisavet.beanToModel(muestraBean);
        Optional<Muestra> muestraOpt=muestraRepositorio.findById(muestraBean.getId());
        if (muestraOpt.get()!= null) {
        	Muestra muestrabbdd=muestraOpt.get();
        	muestrabbdd.setRefInternaVisavet(muestraBean.getReferenciaInterna());
		// Lote nuevo, resultado pendiente
		/*if (muestraBean.getId() == null) {
			muestra.setResultado(BeanResultado.ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE.getCod());		
		}
		
		muestra.setLote(null);
		muestra.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_INICIADA.getCodNum()));
		if (muestraBean.getIdLote() != null) {
			muestra.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ASIGNADA_LOTE.getCodNum()));
			muestra.setLote(new Lote(muestraBean.getIdLote()));			
		} 
		
		Paciente paciente = muestra.getPaciente();
		*/
		muestra = muestraRepositorio.save(muestrabbdd);
		return MuestraBeanLaboratorioVisavet.modelToBean(muestra);
        }
		return null;
	}
	
}
