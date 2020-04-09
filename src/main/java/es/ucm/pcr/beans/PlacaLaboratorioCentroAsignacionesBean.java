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

public class PlacaLaboratorioCentroAsignacionesBean extends PlacaLaboratorioCentroBean{
	
	//es PlacaLaboratorioCentroBean al que le añadimos el beanAnalisis y fechaAsignacionAAnalistaLogado
	
	private BeanAnalisis beanAnalisis; //analistas de laboratorio y voluntarios que se asignan las placas (y a sus respesctivas muestras) por parte del jefe de servicio)  F6y F7
	
	//será la fecha en la que se asignó la placa (y todas sus muestras) al analista que ha iniciado sesion, 
	//solo se rellena al recuperar las placas asignadas al analista que inicia sesion
	private Date fechaAsignacionAAnalistaLogado;   
	
	public PlacaLaboratorioCentroAsignacionesBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BeanAnalisis getBeanAnalisis() {
		return beanAnalisis;
	}

	public void setBeanAnalisis(BeanAnalisis beanAnalisis) {
		this.beanAnalisis = beanAnalisis;
	}
	public Date getFechaAsignacionAAnalistaLogado() {
		return fechaAsignacionAAnalistaLogado;
	}

	public void setFechaAsignacionAAnalistaLogado(Date fechaAsignacionAAnalistaLogado) {
		this.fechaAsignacionAAnalistaLogado = fechaAsignacionAAnalistaLogado;
	}

	public static PlacaLaboratorioCentroAsignacionesBean modelToBean(PlacaLaboratorio placaLaboratorio) {

		//PlacaLaboratorioCentroBean.modelToBean(placaLaboratorio); //llamo al metodo de la clase padre
		
		//replica codigo javi en clase padre
		PlacaLaboratorioCentroAsignacionesBean bean = new PlacaLaboratorioCentroAsignacionesBean();

		bean.setId(placaLaboratorio.getId());

		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLabCentro,
				placaLaboratorio.getEstadoPlacaLaboratorio().getId());
		bean.setBeanEstado(beanEstado);
		bean.setNumeroMuestras(placaLaboratorio.getNumeromuestras());
		bean.setFechaCreacion(placaLaboratorio.getFechaCreacion());
		
		LaboratorioCentroBean laboratorioCentroBean = new LaboratorioCentroBean();	
		laboratorioCentroBean.setId(String.valueOf((placaLaboratorio.getLaboratorioCentro().getId())));
		laboratorioCentroBean.setNombre(placaLaboratorio.getLaboratorioCentro().getNombre());
		bean.setLaboratorioCentro(laboratorioCentroBean);
		
		List<MuestraListadoPlacasLaboratorioBean> listadoMuestras = new ArrayList<MuestraListadoPlacasLaboratorioBean>();
		Set<Muestra> muestras = placaLaboratorio.getMuestras();
		for (Muestra muestra : muestras) {
			MuestraListadoPlacasLaboratorioBean muestraBean = new MuestraListadoPlacasLaboratorioBean();
			muestraBean.setId(muestra.getId());
			if(muestra.getPlacaVisavet()!=null) //Diana- añado esta condicion para que no falle porque en los datos de prueba tenemos casi todas las muestras sin placaVisavet
				muestraBean.setIdPlacaVisavet(muestra.getPlacaVisavet().getId());
			muestraBean.setEstado(muestra.getEstadoMuestra().getDescripcion());
			muestraBean.setEtiqueta(muestra.getEtiqueta());
			muestraBean.setRefInterna(muestra.getRefInternaVisavet());
			listadoMuestras.add(muestraBean);
		}
		bean.setMuestras(listadoMuestras);

		List<PlacaLaboratorioVisavetBean> placasVisavet = new ArrayList<PlacaLaboratorioVisavetBean>();
		// TODO rellenar placasVisavet
		bean.setPlacasVisavet(placasVisavet);

		List<DocumentoBean> documentos = new ArrayList<DocumentoBean>();
		// TODO rellenar Documentos
		bean.setDocumentos(documentos);
		
		//fin replica codigo clase padre
				
		
		//beanAnalisis
		BeanAnalisis beanAnalisis  = new BeanAnalisis();
		BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
		//analistas de la muestra(serán los mismos analistas que la placa), 
		//rellenamos los analistas de la placa a partir de los analistas de la primera muestra de la placa (porque son los mismos para todas las muestras)
		Set<Muestra> setMuestras = placaLaboratorio.getMuestras();
		for (Muestra muestra : setMuestras) {
			for(UsuarioMuestra usuMuestra: muestra.getUsuarioMuestras()) {
				Usuario usu = usuMuestra.getUsuario();
							
				for(Rol rol: usu.getRols()) {
					//si el usuario tiene el rol analista
					if(rol.getId()== BeanRolUsuario.RolUsuario.ROL_USUARIO_ANALISTALABORATORIO.getId()) {
						//si tiene rol ANALISTALABORATORIO
						BeanAsignacion beanAsigAna = new BeanAsignacion();
						BeanUsuario beanUsuAnalista = BeanUsuario.modelToBean(usu);
						beanUsuAnalista.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO)); //TODO mirar como poner estoo						
						beanAsigAna.setBeanUsuario(beanUsuAnalista);				
						beanAsigAna.setFechaAsignacion(usuMuestra.getFechaAsignacion());
						//beanAsigAna.setValoracion("P"); //la valoración no tiene sentido a nivel de placa (se valoran las muestras no las placas)
						beanListaAsignaciones.getListaAnalistasLab().add(beanAsigAna);
					}
					//si el usuario tiene el rol voluntario y tiene idLaboratorioCentro
					if((rol.getId()== BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()!= null)) {
						//si tiene rol VOLUNTARIO
						BeanAsignacion beanAsigVol = new BeanAsignacion();
						BeanUsuario beanUsuVol = BeanUsuario.modelToBean(usu);
						beanUsuVol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo
						beanAsigVol.setBeanUsuario(beanUsuVol);				
						beanAsigVol.setFechaAsignacion(usuMuestra.getFechaAsignacion());
						//beanAsigVol.setValoracion("P"); //la valoración no tiene sentido a nivel de placa (se valoran las muestras no las placas)
						beanListaAsignaciones.getListaAnalistasVol().add(beanAsigVol);
					}
					//si el usuario tiene el rol voluntario y no tiene idLaboratorioCentro
					if((rol.getId()== BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId()) && (usu.getIdLaboratorioCentro()== null)) {
						//si tiene rol VOLUNTARIO y no está asignado a ningun laboratorioCentro
						BeanAsignacion beanAsigVolSinCentro = new BeanAsignacion();
						BeanUsuario beanUsuVolSinCentro = BeanUsuario.modelToBean(usu);
						beanUsuVolSinCentro.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); //TODO mirar como poner estoo
						beanAsigVolSinCentro.setBeanUsuario(beanUsuVolSinCentro);				
						beanAsigVolSinCentro.setFechaAsignacion(usuMuestra.getFechaAsignacion());
						//beanAsigVol.setValoracion("P"); //la valoración no tiene sentido a nivel de placa (se valoran las muestras no las placas)
						beanListaAsignaciones.getListaAnalistasVolSinLabCentro().add(beanAsigVolSinCentro);
					}
				}				
			}
			//en cuanto obtengamos los datos de los analistas de la primera muestra nos salimos del bucle
			break;
			
		}
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		bean.setBeanAnalisis(beanAnalisis);	


		return bean;

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
