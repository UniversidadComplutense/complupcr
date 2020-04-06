package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public class BeanListadoMuestraAnalisis extends BeanBusquedaMuestraAnalisis {
	
	private Integer id;
	//private String codNumLote;
	private boolean notificado;
	
	private BeanAnalisis beanAnalisis; //proceso de analizar la muestra (asignación de analistas a las muestras por parte del jefe de servicio y su resolucion)  F6y F7
	private String resultado; //valoracion final de la muestra despues de analizarla los analistas o dar su veredicto el jefe de servicio
	private Boolean esCerrable = false; //por defecto la muestra no se puede cerrar hasta que el jefe dé su veredicto o los analistas estén deacuerdo
	
	
	
	public BeanListadoMuestraAnalisis() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public boolean isNotificado() {
		return notificado;
	}

	public void setNotificado(boolean notificado) {
		this.notificado = notificado;
	}

	public BeanAnalisis getBeanAnalisis() {
		return beanAnalisis;
	}

	public void setBeanAnalisis(BeanAnalisis beanAnalisis) {
		this.beanAnalisis = beanAnalisis;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Boolean getEsCerrable() {
		return esCerrable;
	}

	public void setEsCerrable(Boolean esCerrable) {
		this.esCerrable = esCerrable;
	}

	/*
	public BeanListadoMuestraAnalisis getBean(int i) {
		BeanListadoMuestraAnalisis bean = new BeanListadoMuestraAnalisis();
		bean.setId(i);
		bean.setNombrePaciente("Paciente " + i);
		bean.setNhcPaciente("nhc-" + i);
		bean.setEtiquetaMuestra("etiquetaM-" + i);
		bean.setRefInternaMuestra("refInternaM-" + i);
		bean.setFechaEnvioMuestraIni(new Date());
		bean.setFechaResultadoMuestraIni(new Date());
		bean.setEstadoMuestra("Pendiente");
		if (i > 2) {
			bean.setEstadoMuestra("Negativo");
		}
		//bean.setCodNumLote("codLote-" + i);
		
		Date date = new Date(); // your date
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
		BeanAnalisis beanAnalisis  = new BeanAnalisis();
		BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
		//usuarios que analizan, 2 analistas y 2 voluntario
		for(int j=0;j<2; j++) {
			BeanAsignacion beanAsigAna = new BeanAsignacion();
			BeanUsuario ana = new BeanUsuario();
			ana.setId(j);
			ana.setNom("analista-" + j);
			ana.setRol("ANALISTA_LAB");
			beanAsigAna.setBeanUsuario(ana);				
			beanAsigAna.setFechaAsignacion(cal);
			beanAsigAna.setValoracion("P");
			beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);
			
			BeanAsignacion beanAsigVol = new BeanAsignacion();
			BeanUsuario vol = new BeanUsuario();
			vol.setId(j);
			vol.setNom("voluntario-" + j);
			vol.setRol("ANALISTA_VOLUNTARIO");
			beanAsigVol.setBeanUsuario(vol);				
			beanAsigVol.setFechaAsignacion(cal);
			beanAsigVol.setValoracion("N");
			beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
		}
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		bean.setBeanAnalisis(beanAnalisis);		
		bean.setEsCerrable(true); //lo pongo a true para que esté habilitado el check
		
		
		return bean;
	}
	*/
	
	

	public static BeanListadoMuestraAnalisis modelToBean(Muestra muestra) {
		Paciente paciente = muestra.getPaciente();
		
		BeanListadoMuestraAnalisis bean = new BeanListadoMuestraAnalisis();
		bean.setId(muestra.getId());
		bean.setNombrePaciente(nombreCompletoPaciente(muestra.getPaciente()));
		bean.setNhcPaciente(paciente.getNhc());
		bean.setEtiquetaMuestra(muestra.getEtiqueta());
		bean.setRefInternaMuestra(muestra.getRefInternaVisavet());
		//bean.setFechaEntrada(muestra.getFechaEntrada());
		bean.setFechaEnvioMuestraIni(muestra.getFechaEnvio());
		bean.setFechaResultadoMuestraIni(muestra.getFechaResultado());
		//bean.setCodNumLote(muestra.getLote() != null ? muestra.getLote().getNumeroLote() : "");
		bean.setNotificado(muestra.getFechaNotificacion() != null);
		
		BeanResultado beanResultado = new BeanResultado();
		bean.setEstadoMuestra(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());
		
		if(muestra.getPlacaLaboratorio()!=null) {
			bean.setIdPlacaLaboratorio(muestra.getPlacaLaboratorio().getId()); //aqui solo se mostrarán muestras que ya esten en una placa de laboratorio
			bean.setIdJefePlaca(muestra.getPlacaLaboratorio().getUsuario().getId()); //todas las muestras que mostremos en esta vista serán aquellas cuya placa se haya asignado el jefe
		}
		
		Date date = new Date(); // your date
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
		BeanAnalisis beanAnalisis  = new BeanAnalisis();
		BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
		
		Set<UsuarioMuestra> usuarioMuestras = muestra.getUsuarioMuestras(); //conjunto de usuarios asignados a la muestra (analistas, voluntarios y jefes)
		for(UsuarioMuestra usuMu: usuarioMuestras) {
			Usuario usu = usuMu.getUsuario();
			Set<Rol> rolesUsu = usu.getRols();			
			for(Rol rol: rolesUsu) {
				//si el usuario tiene el rol analista
				if(rol.getId()==8) {
					BeanAsignacion beanAsigAna = new BeanAsignacion();
					BeanUsuario ana = new BeanUsuario();
					ana.setId(usu.getId());
					ana.setNom(nombreCompletoUsuario(usu));
					ana.setRol("ANALISTA_LAB"); //TODO mirar como poner estoo
					beanAsigAna.setBeanUsuario(ana);				
					beanAsigAna.setFechaAsignacion(usuMu.getFechaAsignacion());
					beanAsigAna.setValoracion(usuMu.getValoracion());
					beanAsigAna.setFechaValoracion(usuMu.getFechaValoracion());
					beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);					
				}
				//si el usuario tiene el rol voluntario
				if(rol.getId()==14) {
					BeanAsignacion beanAsigVol = new BeanAsignacion();
					BeanUsuario vol = new BeanUsuario();
					vol.setId(usu.getId());
					vol.setNom(nombreCompletoUsuario(usu));
					vol.setRol("ANALISTA_VOLUNTARIO"); //TODO mirar como poner estoo
					beanAsigVol.setBeanUsuario(vol);				
					beanAsigVol.setFechaAsignacion(usuMu.getFechaAsignacion());					
					beanAsigVol.setValoracion(usuMu.getValoracion());
					beanAsigVol.setFechaValoracion(usuMu.getFechaValoracion());
					beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
				}
			}
		}
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		bean.setBeanAnalisis(beanAnalisis);		
		bean.setEsCerrable(true); //TODO calculo de cuándo es cerrable la muestra. lo pongo a true para que esté habilitado el check	
		
		return bean;
	}
	
	
	private static String nombreCompletoPaciente(Paciente paciente) {
		String nombreCompleto = paciente.getNombrePaciente();
		if (StringUtils.isNotEmpty(paciente.getApellido1paciente())) {
			nombreCompleto = nombreCompleto.concat(" ").concat(paciente.getApellido1paciente());
		}
		if (StringUtils.isNotEmpty(paciente.getApellido2paciente())) {
			nombreCompleto = nombreCompleto.concat(" ").concat(paciente.getApellido2paciente());
		}
		return nombreCompleto;
	}
	
	private static String nombreCompletoUsuario(Usuario usu) {
		String nombreCompleto = usu.getNombre();
		if (StringUtils.isNotEmpty(usu.getApellido1())) {
			nombreCompleto = nombreCompleto.concat(" ").concat(usu.getApellido1());
		}
		if (StringUtils.isNotEmpty(usu.getApellido2())) {
			nombreCompleto = nombreCompleto.concat(" ").concat(usu.getApellido2());
		}
		return nombreCompleto;
	}
		

}
