package es.ucm.pcr.beans;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import es.ucm.pcr.beans.BeanRolUsuario.RolUsuario;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public class BeanListadoMuestraAnalisis extends BeanBusquedaMuestraAnalisis {
	
	private Integer id;
	//private String codNumLote;
	private boolean notificado;
	
	private BeanAnalisis beanAnalisis; //proceso de analizar la muestra (asignación de analistas a las muestras por parte del jefe de servicio y su resolucion)  F6y F7
	private Date fechaReclamadaPlaca; //fecha en que el jefe reclamó la placa (se la asignó)
	private String resultado; //valoracion final de la muestra despues de analizarla los analistas o dar su veredicto el jefe de servicio
	private Date fechaResultado; //fecha en la que se asignó un resultado final a la muestra
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


	public Date getFechaReclamadaPlaca() {
		return fechaReclamadaPlaca;
	}

	public void setFechaReclamadaPlaca(Date fechaReclamadaPlaca) {
		this.fechaReclamadaPlaca = fechaReclamadaPlaca;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Date getFechaResultado() {
		return fechaResultado;
	}

	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}

	public Boolean getEsCerrable() {
		return esCerrable;
	}

	public void setEsCerrable(Boolean esCerrable) {
		this.esCerrable = esCerrable;
	}

	

	public static BeanListadoMuestraAnalisis modelToBean(Muestra muestra) {
		Paciente paciente = muestra.getPaciente();
		
		BeanListadoMuestraAnalisis bean = new BeanListadoMuestraAnalisis();
		bean.setId(muestra.getId());
		bean.setNombrePaciente(nombreCompletoPaciente(muestra.getPaciente()));
		bean.setNhcPaciente(paciente.getNhc());
		bean.setEtiquetaMuestra(muestra.getEtiqueta());
		bean.setRefInternaMuestra(muestra.getRefInternaVisavet());
		//bean.setFechaEntrada(muestra.getFechaEntrada());
		//bean.setFechaEnvioMuestraIni(muestra.getFechaEnvio());
		//bean.setFechaResultadoMuestraIni(muestra.getFechaResultado());
		//bean.setCodNumLote(muestra.getLote() != null ? muestra.getLote().getNumeroLote() : "");
		bean.setNotificado(muestra.getFechaNotificacion() != null);
		
		//bean.setEstadoMuestra(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());
		bean.setEstadoMuestra(muestra.getEstadoMuestra().getDescripcion());
		
		
		//TODO: REVISAR ESTO CON SARA- UNA MUESTRA PUEDE ESTAR EN VARIAS PLACAS DE LABORATORIO??		
		/*
		if(muestra.getPlacaLaboratorio()!=null) {
			bean.setIdPlacaLaboratorio(muestra.getPlacaLaboratorio().getId()); //aqui solo se mostrarán muestras que ya esten en una placa de laboratorio
			if(muestra.getPlacaLaboratorio().getUsuario()!=null)
				bean.setIdJefePlaca(muestra.getPlacaLaboratorio().getUsuario().getId()); //todas las muestras que mostremos en esta vista serán aquellas cuya placa se haya asignado el jefe
		}
		*/
		//de la muestra recuperamos la placa a traves del lote y placa visavet
		Set<PlacaVisavetPlacaLaboratorio> setPlacaLabPlacaVis= muestra.getLote().getPlacaVisavet().getPlacaVisavetPlacaLaboratorios();
		for(PlacaVisavetPlacaLaboratorio placaVisPlacaLab: setPlacaLabPlacaVis ) {
			PlacaLaboratorio placaLab = placaVisPlacaLab.getPlacaLaboratorio();
			if(placaLab!=null) {// && placaLab.getUsuario().getId().equals(obj)) {
				bean.setIdPlacaLaboratorio(placaLab.getId()); //aqui solo se mostrarán muestras que ya esten en una placa de laboratorio
				//bean.setFechaReclamadaPlacaIni(placaLab.getFechaAsignadaJefe());
				bean.setFechaReclamadaPlaca(placaLab.getFechaAsignadaJefe());
				if(placaLab.getUsuario()!=null)
					bean.setIdJefePlaca(placaLab.getUsuario().getId()); //todas las muestras que mostremos en esta vista serán aquellas cuya placa se haya asignado el jefe
			}
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
				if(rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_ANALISTALABORATORIO.getId())) {
					BeanAsignacion beanAsigAna = new BeanAsignacion();
					BeanUsuario ana = BeanUsuario.modelToBean(usu);					
					ana.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO)); //TODO mirar como poner estoo
					beanAsigAna.setBeanUsuario(ana);				
					beanAsigAna.setFechaAsignacion(usuMu.getFechaAsignacion());
					beanAsigAna.setValoracion(usuMu.getValoracion());
					beanAsigAna.setFechaValoracion(usuMu.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsigAna.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
					beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);					
				}
				//si el usuario tiene el rol voluntario y tiene idLaboratorioCentro
				if((rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()!= null))) {
					BeanAsignacion beanAsigVol = new BeanAsignacion();
					BeanUsuario vol = BeanUsuario.modelToBean(usu);					
					vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo					
					beanAsigVol.setBeanUsuario(vol);				
					beanAsigVol.setFechaAsignacion(usuMu.getFechaAsignacion());					
					beanAsigVol.setValoracion(usuMu.getValoracion());
					beanAsigVol.setFechaValoracion(usuMu.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsigVol.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
					beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
				}				
				//si el usuario tiene el rol voluntario y no tiene idLaboratorioCentro
				if((rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()== null))) {
					//si tiene rol VOLUNTARIO y no está asignado a ningun laboratorioCentro
					BeanAsignacion beanAsigVolSinCentro = new BeanAsignacion();
					BeanUsuario beanUsuVolSinCentro = BeanUsuario.modelToBean(usu);
					beanUsuVolSinCentro.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo					
					beanAsigVolSinCentro.setBeanUsuario(beanUsuVolSinCentro);
					beanAsigVolSinCentro.setFechaAsignacion(usuMu.getFechaAsignacion());					
					beanAsigVolSinCentro.setValoracion(usuMu.getValoracion());
					beanAsigVolSinCentro.setFechaValoracion(usuMu.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsigVolSinCentro.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
					beanListaAsignaciones.getListaAnalistasVolSinLabCentro().add(beanAsigVolSinCentro);
				}
				
			}
		}
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		//el total de analistas asignados será la suma de los analistas lab, analistas vol y analistas vol sin centro
		beanAnalisis.setNumTotalAnalistasAsignados(beanListaAsignaciones.getListaAnalistasLab().size() + beanListaAsignaciones.getListaAnalistasVol().size() + beanListaAsignaciones.getListaAnalistasVolSinLabCentro().size());
		bean.setBeanAnalisis(beanAnalisis);
		BeanResultado beanResultado = new BeanResultado();
		bean.setResultado(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());
		bean.setFechaResultado(muestra.getFechaResultado());
		bean.setEsCerrable(true); //TODO calculo de cuándo es cerrable la muestra. lo pongo a true para que esté habilitado el check	
		
		return bean;
	}
	
	public static BeanListadoMuestraAnalisis modelToBeanAnalista(Muestra muestra, Integer idUsuarioLogado) {
		//cargamos todos los datos de BeanListadoMuestraAnalisis y ademas le añadimos a cada muestra el beanAnalisis asociado al usaurio logado
		//BeanListadoMuestraAnalisis bean = 	BeanListadoMuestraAnalisis.modelToBean(muestra);
		
		Paciente paciente = muestra.getPaciente();
		
		BeanListadoMuestraAnalisis bean = new BeanListadoMuestraAnalisis();
		bean.setId(muestra.getId());
		bean.setNombrePaciente(nombreCompletoPaciente(muestra.getPaciente()));
		bean.setNhcPaciente(paciente.getNhc());
		bean.setEtiquetaMuestra(muestra.getEtiqueta());
		bean.setRefInternaMuestra(muestra.getRefInternaVisavet());
		//bean.setFechaEntrada(muestra.getFechaEntrada());
		//bean.setFechaEnvioMuestraIni(muestra.getFechaEnvio());
		//bean.setFechaResultadoMuestraIni(muestra.getFechaResultado());
		//bean.setCodNumLote(muestra.getLote() != null ? muestra.getLote().getNumeroLote() : "");
		bean.setNotificado(muestra.getFechaNotificacion() != null);
				
		//bean.setEstadoMuestra(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());
		bean.setEstadoMuestra(muestra.getEstadoMuestra().getDescripcion());
		
		//TODO: REVISAR ESTO CON SARA- UNA MUESTRA PUEDE ESTAR EN VARIAS PLACAS DE LABORATORIO??		
		/*
		if(muestra.getPlacaLaboratorio()!=null) {
			bean.setIdPlacaLaboratorio(muestra.getPlacaLaboratorio().getId()); //aqui solo se mostrarán muestras que ya esten en una placa de laboratorio
			if(muestra.getPlacaLaboratorio().getUsuario()!=null)
				bean.setIdJefePlaca(muestra.getPlacaLaboratorio().getUsuario().getId()); //todas las muestras que mostremos en esta vista serán aquellas cuya placa se haya asignado el jefe
		}
		*/
		//de la muestra recuperamos la placa a traves del lote y placa visavet
		Set<PlacaVisavetPlacaLaboratorio> setPlacaLabPlacaVis= muestra.getLote().getPlacaVisavet().getPlacaVisavetPlacaLaboratorios();
		for(PlacaVisavetPlacaLaboratorio placaVisPlacaLab: setPlacaLabPlacaVis ) {
			PlacaLaboratorio placaLab = placaVisPlacaLab.getPlacaLaboratorio();
			if(placaLab!=null) {// && placaLab.getUsuario().getId().equals(obj)) {
				bean.setIdPlacaLaboratorio(placaLab.getId()); //aqui solo se mostrarán muestras que ya esten en una placa de laboratorio
				//bean.setFechaReclamadaPlacaIni(placaLab.getFechaAsignadaJefe());
				bean.setFechaReclamadaPlaca(placaLab.getFechaAsignadaJefe());
				if(placaLab.getUsuario()!=null)
					bean.setIdJefePlaca(placaLab.getUsuario().getId()); //todas las muestras que mostremos en esta vista serán aquellas cuya placa se haya asignado el jefe
			}
		}
		
		Date date = new Date(); // your date
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
		BeanAnalisis beanAnalisis  = new BeanAnalisis();
		BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
		
		BeanAsignacion beanAsignacionUsuarioLogado = new BeanAsignacion();
		Set<UsuarioMuestra> usuarioMuestras = muestra.getUsuarioMuestras(); //conjunto de usuarios asignados a la muestra (analistas, voluntarios y jefes)
		for(UsuarioMuestra usuMu: usuarioMuestras) {
			Usuario usu = usuMu.getUsuario();			
			//si el usumuestra es el mismo que el usuario logado rellenamos el beanAsignacionUsuarioLogado			
			Integer idUsu = usu.getId();
			//System.out.println("id usuarioMuestra vale: " + usuMu.getIdUsuarioMuestra());
			//System.out.println("id usu vale: " + idUsu);
			//System.out.println("is usuario logado vale: " + idUsuarioLogado);
			if(idUsu.equals(idUsuarioLogado)) {				
				BeanUsuario beanUsuLogado = BeanUsuario.modelToBean(usu);
				//no puedo asociarle el rol porque puede tener varios
				//BeanRolUsuario beanRolUsuario = new BeanRolUsuario();				
				//beanUsuLogado.setBeanRolUsuario(beanRolUsuario.asignarRolUsuarioPorCodNum(usuarioLogado.get));
				beanAsignacionUsuarioLogado.setBeanUsuario(beanUsuLogado);
				beanAsignacionUsuarioLogado.setFechaAsignacion(usuMu.getFechaAsignacion());
				beanAsignacionUsuarioLogado.setValoracion(usuMu.getValoracion());
				//el usuario será reemplazable si no ha valorado la muestra
				beanAsignacionUsuarioLogado.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
				beanAsignacionUsuarioLogado.setFechaValoracion(usuMu.getFechaValoracion());
			}			
			
			Set<Rol> rolesUsu = usu.getRols();			
			for(Rol rol: rolesUsu) {
				//si el usuario tiene el rol analista
				if(rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_ANALISTALABORATORIO.getId())) {
					BeanAsignacion beanAsigAna = new BeanAsignacion();
					BeanUsuario ana = BeanUsuario.modelToBean(usu);					
					ana.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO)); //TODO mirar como poner estoo
					beanAsigAna.setBeanUsuario(ana);				
					beanAsigAna.setFechaAsignacion(usuMu.getFechaAsignacion());
					beanAsigAna.setValoracion(usuMu.getValoracion());
					beanAsigAna.setFechaValoracion(usuMu.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsigAna.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
					beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);					
				}
				//si el usuario tiene el rol voluntario y tiene idLaboratorioCentro
				if((rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()!= null))) {
					BeanAsignacion beanAsigVol = new BeanAsignacion();
					BeanUsuario vol = BeanUsuario.modelToBean(usu);					
					vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo					
					beanAsigVol.setBeanUsuario(vol);				
					beanAsigVol.setFechaAsignacion(usuMu.getFechaAsignacion());					
					beanAsigVol.setValoracion(usuMu.getValoracion());
					beanAsigVol.setFechaValoracion(usuMu.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsigVol.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
					beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
				}				
				//si el usuario tiene el rol voluntario y no tiene idLaboratorioCentro
				if((rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()== null))) {
					//si tiene rol VOLUNTARIO y no está asignado a ningun laboratorioCentro
					BeanAsignacion beanAsigVolSinCentro = new BeanAsignacion();
					BeanUsuario beanUsuVolSinCentro = BeanUsuario.modelToBean(usu);
					beanUsuVolSinCentro.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo					
					beanAsigVolSinCentro.setBeanUsuario(beanUsuVolSinCentro);
					beanAsigVolSinCentro.setFechaAsignacion(usuMu.getFechaAsignacion());					
					beanAsigVolSinCentro.setValoracion(usuMu.getValoracion());
					beanAsigVolSinCentro.setFechaValoracion(usuMu.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsigVolSinCentro.setEsReemplazable(usuMu.getFechaValoracion()==null && usuMu.getValoracion()==null);
					beanListaAsignaciones.getListaAnalistasVolSinLabCentro().add(beanAsigVolSinCentro);
				}
				
			}
		}
		beanAnalisis.setAsignacionUsuarioLogado(beanAsignacionUsuarioLogado);
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		//el total de analistas asignados será la suma de los analistas lab, analistas vol y analistas vol sin centro
		beanAnalisis.setNumTotalAnalistasAsignados(beanListaAsignaciones.getListaAnalistasLab().size() + beanListaAsignaciones.getListaAnalistasVol().size() + beanListaAsignaciones.getListaAnalistasVolSinLabCentro().size());
		//System.out.println("beanAnalisis vale: " + beanAnalisis.toString());
		bean.setBeanAnalisis(beanAnalisis);
		BeanResultado beanResultado = new BeanResultado();
		bean.setResultado(beanResultado.asignarTipoEstadoYCodNum(muestra.getResultado()).getResultadoMuestra().getDescripcion());
		bean.setFechaResultado(muestra.getFechaResultado());
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
		
		

}
