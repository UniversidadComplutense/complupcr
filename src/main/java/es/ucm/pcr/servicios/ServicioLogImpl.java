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
import es.ucm.pcr.beans.LogMuestraBusquedaBean;
import es.ucm.pcr.beans.LogMuestraListadoBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.LogMuestras;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.LogMuestrasRepositorio;
import es.ucm.pcr.repositorio.LoteRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;

@Service
public class ServicioLogImpl implements ServicioLog {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ServicioLogImpl.class);

	@Autowired
	private LogMuestrasRepositorio logMuestrasRepositorio;

	@Autowired
	private MuestraRepositorio muestraRepositorio;

	@Autowired
	private LoteRepositorio loteRepositorio;

	@Autowired
	private PlacaVisavetRepositorio placaVisavetRepositorio;

	@Autowired
	private PlacaLaboratorioRepositorio placaLaboratorioRepositorio;

	@Autowired
	private SesionServicio sesionServicio;

	@Override
	public void actualizarEstadoMuestra(Integer idMuestra, BeanEstado estado) {
		Optional<Muestra> muestraOp = muestraRepositorio.findById(idMuestra);
		if (muestraOp.isPresent()) {
			Muestra muestra = muestraOp.get();
			actualizarEstadoMuestra(muestra, estado);
		}
	}

	@Override
	public void actualizarEstadoMuestraPorLote(Integer idLote, BeanEstado estado) {
		Optional<Lote> loteOp = loteRepositorio.findById(idLote);
		if (loteOp.isPresent()) {
			Lote lote = loteOp.get();
			for (Muestra muestra : lote.getMuestras()) {
				actualizarEstadoMuestra(muestra, estado);
			}
		}
	}

	@Override
	public void actualizarEstadoMuestraPorPlacaVisavet(Integer idPlacaVisavet, BeanEstado estado) {
		Optional<PlacaVisavet> placaVisavetOp = placaVisavetRepositorio.findById(idPlacaVisavet);
		if (placaVisavetOp.isPresent()) {
			PlacaVisavet placaVisavet = placaVisavetOp.get();
			for (Lote lote : placaVisavet.getLotes()) {
				for (Muestra muestra : lote.getMuestras()) {
					muestra.setPlacaVisavet(placaVisavet);
				actualizarEstadoMuestra(muestra, estado);
				}
			}
		}
	}

	@Override
	public void actualizarEstadoMuestraPorPlacaLaboratorio(Integer idPlacaLaboratorio, BeanEstado estado) {
		Optional<PlacaLaboratorio> placaLaboratorioOp = placaLaboratorioRepositorio.findById(idPlacaLaboratorio);
		if (placaLaboratorioOp.isPresent()) {
			PlacaLaboratorio placaLaboratorio = placaLaboratorioOp.get();
			for (Muestra muestra : placaLaboratorio.getMuestras()) {
				actualizarEstadoMuestra(muestra, estado);
			}
		}

	}

	private void actualizarEstadoMuestra(Muestra muestra, BeanEstado estadoActualizar) {

		LogMuestras logMuestras = new LogMuestras(muestra, muestra.getLote(), muestra.getPlacaVisavet(),
				muestra.getPlacaLaboratorio(), new EstadoMuestra(estadoActualizar.getEstado().getCodNum()), new Date(),
				sesionServicio.getUsuario());
		logMuestrasRepositorio.save(logMuestras);
	}
	
	@Override
	public Page<LogMuestraListadoBean> findLogMuestraByParam(LogMuestraBusquedaBean params, Pageable pageable) {
		List<LogMuestraListadoBean> listMuestrasBean = new ArrayList<LogMuestraListadoBean>();
		
		Page<LogMuestras> muestrasPage = logMuestrasRepositorio.findByParams(params, pageable); 
		
		LogMuestraListadoBean logMuestra = new LogMuestraListadoBean();
		for (LogMuestras l : muestrasPage.getContent()) {
			logMuestra = new LogMuestraListadoBean();
			logMuestra.setId(l.getId());
			logMuestra.setDescLote(l.getLote() != null ? l.getLote().getNumeroLote() : "");
			logMuestra.setDescCentroSalud(l.getMuestra().getCentro().getNombre());
			logMuestra.setDescMuestra(l.getMuestra().getEtiqueta());
			Paciente paciente = l.getMuestra().getPaciente();
			logMuestra.setDescPaciente(MuestraListadoBean.nombreCompletoPaciente(paciente));
			logMuestra.setNhcPaciente(paciente != null ? paciente.getNhc() : "");
			logMuestra.setDescEstadoMuestra(l.getEstadoMuestra().getDescripcion());
			logMuestra.setFechaCambio(l.getFechaCambio());
			Usuario usuario = l.getAutorCambio();
			logMuestra.setNombreAutorCambio(usuario.getNombre() + " " + usuario.getApellido1() + (usuario.getApellido2() != null ? usuario.getApellido2() : ""));
			listMuestrasBean.add(logMuestra);
		}
		
		Page<LogMuestraListadoBean> listMuestrasBeanPage = new PageImpl<>(listMuestrasBean, pageable, muestrasPage.getTotalElements());
		
		return listMuestrasBeanPage;
	}
	
	@Override
	public void borrarEstadosMuestra(Integer idMuestra) {
		LogMuestraBusquedaBean logBusqueda = new LogMuestraBusquedaBean();
		logBusqueda.setIdMuestra(idMuestra);
		List<LogMuestras> logMuestras = logMuestrasRepositorio.findByParams(logBusqueda);
		logMuestrasRepositorio.deleteAll(logMuestras);
	}

}
