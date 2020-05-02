package es.ucm.pcr.beans;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.sun.net.httpserver.Authenticator.Result;

import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;

public class MuestraListadoBean extends MuestraBusquedaBean {

	private Integer id;
	private String codNumLote;
	private boolean notificado;
	private Date fechaEntrada;
    
	public MuestraListadoBean() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodNumLote() {
		return codNumLote;
	}

	public void setCodNumLote(String codNumLote) {
		this.codNumLote = codNumLote;
	}

	public boolean isNotificado() {
		return notificado;
	}

	public void setNotificado(boolean notificado) {
		this.notificado = notificado;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	

	
	public static MuestraListadoBean modelToBean(Muestra muestra) {
		Paciente paciente = muestra.getPaciente();
		
		MuestraListadoBean bean = new MuestraListadoBean();
		bean.setId(muestra.getId());
		bean.setNombrePaciente(nombreCompletoPaciente(muestra.getPaciente()));
		bean.setEmailPaciente(muestra.getPaciente().getEmail());
		bean.setNhcPaciente(paciente.getNhc());
		bean.setEtiquetaMuestra(muestra.getEtiqueta());
		bean.setRefInternaMuestra(muestra.getRefInternaVisavet());
		bean.setFechaEntrada(muestra.getFechaEntrada());
		bean.setFechaEnvioMuestraIni(muestra.getFechaEnvio());
		bean.setFechaResultadoMuestraIni(muestra.getFechaResultado());
		bean.setCodNumLote(muestra.getLote() != null ? muestra.getLote().getNumeroLote() : "");
		bean.setNotificado(muestra.getFechaNotificacion() != null);
		bean.setTipoMuestra(muestra.getTipoMuestra());
		BeanResultado beanResultado = new BeanResultado();
		bean.setEstadoMuestra(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());		
		
		return bean;
	}
	
	public static String nombreCompletoPaciente(Paciente paciente) {
		String nombreCompleto = "";
		if (paciente != null) {
			nombreCompleto = paciente.getNombrePaciente();
			if (StringUtils.isNotEmpty(paciente.getApellido1paciente())) {
				nombreCompleto = nombreCompleto.concat(" ").concat(paciente.getApellido1paciente());
			}
			if (StringUtils.isNotEmpty(paciente.getApellido2paciente())) {
				nombreCompleto = nombreCompleto.concat(" ").concat(paciente.getApellido2paciente());
			}
		}
		return nombreCompleto;
	}

	@Override
	public String toString() {
		return "MuestraListadoBean [id=" + id + ", etiqueta="+getEtiquetaMuestra()+", codNumLote=" + codNumLote + ", notificado=" + notificado
				+ ", fechaEntrada=" + fechaEntrada + "]";
	}
	
	

}
