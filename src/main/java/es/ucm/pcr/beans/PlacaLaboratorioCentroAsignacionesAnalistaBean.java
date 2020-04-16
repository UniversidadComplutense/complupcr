package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import es.ucm.pcr.beans.BeanRolUsuario.RolUsuario;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public class PlacaLaboratorioCentroAsignacionesAnalistaBean extends PlacaLaboratorioCentroAsignacionesBean{
	
	//es PlacaLaboratorioCentroAsignacionesBean pero al cargar sus muestras cargaremos tambien el atributo asignacionUsuario logado
	//que tendra solo los datos de la valoracion del analista logado
	
	private Integer idAnalistaLogado; 
	//será la fecha en la que se asignó la placa (y todas sus muestras) al analista que ha iniciado sesion, 
	//solo se rellena al recuperar las placas asignadas al analista que inicia sesion
	private Date fechaAsignacionAAnalistaLogado;   
	
	public PlacaLaboratorioCentroAsignacionesAnalistaBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getIdAnalistaLogado() {
		return idAnalistaLogado;
	}
	public void setIdAnalistaLogado(Integer idAnalistaLogado) {
		this.idAnalistaLogado = idAnalistaLogado;
	}


	public Date getFechaAsignacionAAnalistaLogado() {
		return fechaAsignacionAAnalistaLogado;
	}

	public void setFechaAsignacionAAnalistaLogado(Date fechaAsignacionAAnalistaLogado) {
		this.fechaAsignacionAAnalistaLogado = fechaAsignacionAAnalistaLogado;
	}

	//rellena los datos de la placa y los datos de las muestras de la placa y le añade los datos que corresponden al usuario analista logado
	public static PlacaLaboratorioCentroAsignacionesAnalistaBean modelToBean(PlacaLaboratorio placaLaboratorio, Integer idUsuarioAnalistaLogado) {
				
		PlacaLaboratorioCentroAsignacionesAnalistaBean bean = new PlacaLaboratorioCentroAsignacionesAnalistaBean();
		
		bean.setIdAnalistaLogado(idUsuarioAnalistaLogado);
		

		bean.setId(placaLaboratorio.getId());

		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLabCentro,
				placaLaboratorio.getEstadoPlacaLaboratorio().getId());
		bean.setBeanEstado(beanEstado);		
		bean.setFechaCreacion(placaLaboratorio.getFechaCreacion());
		
		LaboratorioCentroBean laboratorioCentroBean = new LaboratorioCentroBean();	
		laboratorioCentroBean.setId(String.valueOf((placaLaboratorio.getLaboratorioCentro().getId())));
		laboratorioCentroBean.setNombre(placaLaboratorio.getLaboratorioCentro().getNombre());
		bean.setLaboratorioCentro(laboratorioCentroBean);
		
		List<BeanListadoMuestraAnalisis> listadoMuestras = new ArrayList<BeanListadoMuestraAnalisis>();
		Set<Muestra> muestras = placaLaboratorio.getMuestras();
		for (Muestra muestra : muestras) {
			//llamamos al metodo modelToBeanAnalistas que cargara tambien el atributo asignacionUsuarioLogado 	
			BeanListadoMuestraAnalisis muestraBeanAnalisis = BeanListadoMuestraAnalisis.modelToBeanAnalista(muestra, idUsuarioAnalistaLogado); 		
			listadoMuestras.add(muestraBeanAnalisis);
		}
		bean.setMuestras(listadoMuestras);

		List<DocumentoBean> documentos = new ArrayList<DocumentoBean>();
		// TODO rellenar Documentos
		bean.setDocumentos(documentos);
		
				
		//analistas de la placa
		//beanAnalisis
		BeanAnalisis beanAnalisis  = new BeanAnalisis();
		BeanAsignacion beanAsignacionUsuarioLogado = new BeanAsignacion();
		BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
		//analistas de la muestra(serán los mismos analistas que la placa), 
		//rellenamos los analistas de la placa a partir de los analistas de la primera muestra de la placa (porque son los mismos para todas las muestras)
		Set<Muestra> setMuestras = placaLaboratorio.getMuestras();
		for (Muestra muestra : setMuestras) {
			for(UsuarioMuestra usuMuestra: muestra.getUsuarioMuestras()) {
				Usuario usu = usuMuestra.getUsuario();
				//si el usuario es el usuario logado rellenamos el bean				
				if(usu.getId().equals(idUsuarioAnalistaLogado)) {						
					BeanUsuario beanUsuLogado = BeanUsuario.modelToBean(usu);
					//no puedo asociarle el rol porque puede tener varios
					//BeanRolUsuario beanRolUsuario = new BeanRolUsuario();				
					//beanUsuLogado.setBeanRolUsuario(beanRolUsuario.asignarRolUsuarioPorCodNum(usuarioLogado.get));
					beanAsignacionUsuarioLogado.setBeanUsuario(beanUsuLogado);
					beanAsignacionUsuarioLogado.setFechaAsignacion(usuMuestra.getFechaAsignacion());
					beanAsignacionUsuarioLogado.setValoracion(usuMuestra.getValoracion());
					beanAsignacionUsuarioLogado.setFechaValoracion(usuMuestra.getFechaValoracion());
					//el usuario será reemplazable si no ha valorado la muestra
					beanAsignacionUsuarioLogado.setEsReemplazable(usuMuestra.getFechaValoracion()==null && usuMuestra.getValoracion()==null);
					//la fecha de asignacion de analista logado sera la misma que la fecha de asignacion del analista logado a las muestras
					bean.setFechaAsignacionAAnalistaLogado(usuMuestra.getFechaAsignacion());
				}
				
				
				for(Rol rol: usu.getRols()) {
					//si el usuario tiene el rol analista
					if(rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_ANALISTALABORATORIO.getId())) {
						//si tiene rol ANALISTALABORATORIO
						BeanAsignacion beanAsigAna = new BeanAsignacion();
						BeanUsuario beanUsuAnalista = BeanUsuario.modelToBean(usu);
						beanUsuAnalista.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO)); //TODO mirar como poner estoo						
						beanAsigAna.setBeanUsuario(beanUsuAnalista);				
						beanAsigAna.setFechaAsignacion(usuMuestra.getFechaAsignacion());
						//beanAsigAna.setValoracion("P"); //la valoración no tiene sentido a nivel de placa (se valoran las muestras no las placas)
						//el usuario será reemplazable si no ha valorado la muestra
						beanAsigAna.setEsReemplazable(usuMuestra.getFechaValoracion()==null && usuMuestra.getValoracion()==null);
						beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);
					}
					//si el usuario tiene el rol voluntario y tiene idLaboratorioCentro
					if((rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()!= null))) {
						//si tiene rol VOLUNTARIO
						BeanAsignacion beanAsigVol = new BeanAsignacion();
						BeanUsuario beanUsuVol = BeanUsuario.modelToBean(usu);
						beanUsuVol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo
						beanAsigVol.setBeanUsuario(beanUsuVol);				
						beanAsigVol.setFechaAsignacion(usuMuestra.getFechaAsignacion());
						//beanAsigVol.setValoracion("P"); //la valoración no tiene sentido a nivel de placa (se valoran las muestras no las placas)
						//el usuario será reemplazable si no ha valorado la muestra
						beanAsigVol.setEsReemplazable(usuMuestra.getFechaValoracion()==null && usuMuestra.getValoracion()==null);
						beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
					}
					//si el usuario tiene el rol voluntario y no tiene idLaboratorioCentro
					if((rol.getId().equals(BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()== null))) {
						//si tiene rol VOLUNTARIO y no está asignado a ningun laboratorioCentro
						BeanAsignacion beanAsigVolSinCentro = new BeanAsignacion();
						BeanUsuario beanUsuVolSinCentro = BeanUsuario.modelToBean(usu);
						beanUsuVolSinCentro.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo
						beanAsigVolSinCentro.setBeanUsuario(beanUsuVolSinCentro);				
						beanAsigVolSinCentro.setFechaAsignacion(usuMuestra.getFechaAsignacion());
						//beanAsigVol.setValoracion("P"); //la valoración no tiene sentido a nivel de placa (se valoran las muestras no las placas)
						//el usuario será reemplazable si no ha valorado la muestra
						beanAsigVolSinCentro.setEsReemplazable(usuMuestra.getFechaValoracion()==null && usuMuestra.getValoracion()==null);
						beanListaAsignaciones.getListaAnalistasVolSinLabCentro().add(beanAsigVolSinCentro);
					}
				}				
			}
			//en cuanto obtengamos los datos de los analistas de la primera muestra nos salimos del bucle
			break;
			
		}
		beanAnalisis.setAsignacionUsuarioLogado(beanAsignacionUsuarioLogado);
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		//el total de analistas asignados será la suma de los analistas lab, analistas vol y analistas vol sin centro
		beanAnalisis.setNumTotalAnalistasAsignados(beanListaAsignaciones.getListaAnalistasLab().size() + beanListaAsignaciones.getListaAnalistasVol().size() + beanListaAsignaciones.getListaAnalistasVolSinLabCentro().size());
		bean.setBeanAnalisisPlaca(beanAnalisis);	


		return bean;

	

	}
	@Override
	public String toString() {
		return "PlacaLaboratorioCentroAsignacionesAnalistaBean [idAnalistaLogado=" + idAnalistaLogado
				+ ", fechaAsignacionAAnalistaLogado=" + fechaAsignacionAAnalistaLogado + "]";
	}
		
		
	

	/*
	public static PlacaLaboratorio beanToModel(PlacaLaboratorioCentroAsignacionesBean placaLaboratorioCentroBean) {

		PlacaLaboratorio placa = new PlacaLaboratorio();

		if (placaLaboratorioCentroBean.getId() != null) {
			placa.setId(placaLaboratorioCentroBean.getId());
		}
		
		if (placaLaboratorioCentroBean.getFechaCreacion() != null) {
			placa.setFechaCreacion(placaLaboratorioCentroBean.getFechaCreacion());
		}

		if (placaLaboratorioCentroBean.getBeanEstado() != null) {
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(placaLaboratorioCentroBean.getBeanEstado().getEstado().getCodNum()));
		}
		
		if (placaLaboratorioCentroBean.getNumeroMuestras() != null) {
			placa.setNumeromuestras(placaLaboratorioCentroBean.getNumeroMuestras());
		}
		

		// TODO rellenar LaboratorioCentro
		// placa.setLaboratorioCentro();

		// TODO rellenar documentos
		//Set<Documento> documentos = new HashSet<Documento>();
		//placa.setDocumentos(documentos);

		

		// TODO rellenar placas Visavet
		//Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios = new HashSet<PlacaVisavetPlacaLaboratorio>();
		//placa.setPlacaVisavetPlacaLaboratorios(placaVisavetPlacaLaboratorios);

		return placa;
	}

*/
	
	
	
	
	
}
