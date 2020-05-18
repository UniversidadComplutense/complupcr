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

package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.ucm.pcr.beans.BeanRolUsuario.RolUsuario;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;

public class PlacaLaboratorioCentroAsignacionesBean {
	//datos de la placa, analistas asignados a la placa y a sus respectivas muestras con todas las valoraciones de los analistas asignados
	
	//datos de la placa
	private Integer id;
	//private String numeroMuestras;
	private BeanEstado beanEstado;
	private LaboratorioCentroBean laboratorioCentro;
	private Date fechaCreacion;
	private Date fechaListaAnalisis;
	private Date fechaAsignadaJefe;	
	private List<DocumentoBean> documentos;
	private List<BeanListadoMuestraAnalisis> muestras;	//las muestras tendran sus analista asignados y sus valoraciones
	
	//ya no hereda de PlacaLaboratorioCentroBean, al que le añadimos el beanAnalisis y fechaAsignacionAAnalistaLogado
	
	//analistas de laboratorio y voluntarios que se asignan las placas
	//serán los mismos que se asignen a sus respesctivas muestras (por parte del jefe de servicio)  F6y F7
	private BeanAnalisis beanAnalisisPlaca; 
	
	//una placa será devolvible si sus muestras aun no estan asignadas a analitas (podremos devolver la placa para que la coja otro jefe)
	private Boolean esDevolvible = true; //podremos devolver la placa si no tiene muestras o si no está aun asignada (por defecto es devolvible) 
	
	public PlacaLaboratorioCentroAsignacionesBean() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BeanEstado getBeanEstado() {
		return beanEstado;
	}

	public void setBeanEstado(BeanEstado beanEstado) {
		this.beanEstado = beanEstado;
	}

	public LaboratorioCentroBean getLaboratorioCentro() {
		return laboratorioCentro;
	}
	public void setLaboratorioCentro(LaboratorioCentroBean laboratorioCentro) {
		this.laboratorioCentro = laboratorioCentro;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaListaAnalisis() {
		return fechaListaAnalisis;
	}

	public void setFechaListaAnalisis(Date fechaListaAnalisis) {
		this.fechaListaAnalisis = fechaListaAnalisis;
	}

	public Date getFechaAsignadaJefe() {
		return fechaAsignadaJefe;
	}

	public void setFechaAsignadaJefe(Date fechaAsignadaJefe) {
		this.fechaAsignadaJefe = fechaAsignadaJefe;
	}

	public List<DocumentoBean> getDocumentos() {
		return documentos;
	}



	public void setDocumentos(List<DocumentoBean> documentos) {
		this.documentos = documentos;
	}



	public List<BeanListadoMuestraAnalisis> getMuestras() {
		return muestras;
	}



	public void setMuestras(List<BeanListadoMuestraAnalisis> muestras) {
		this.muestras = muestras;
	}



	public BeanAnalisis getBeanAnalisisPlaca() {
		return beanAnalisisPlaca;
	}



	public void setBeanAnalisisPlaca(BeanAnalisis beanAnalisisPlaca) {
		this.beanAnalisisPlaca = beanAnalisisPlaca;
	}


	public Boolean getEsDevolvible() {
		return esDevolvible;
	}

	public void setEsDevolvible(Boolean esDevolvible) {
		this.esDevolvible = esDevolvible;
	}

	@Override
	public String toString() {
		return "PlacaLaboratorioCentroAsignacionesBean [id=" + id + ", beanEstado=" + beanEstado
				+ ", laboratorioCentro=" + laboratorioCentro + ", fechaCreacion=" + fechaCreacion + ", documentos="
				+ documentos + ", muestras=" + muestras + ", beanAnalisisPlaca=" + beanAnalisisPlaca + "]";
	}

	//rellena los datos de la placa y todos los analistas y voluntarios asignados a la placa y a las muestras de la placa
	public static PlacaLaboratorioCentroAsignacionesBean modelToBean(PlacaLaboratorio placaLaboratorio) {
		
		//PlacaLaboratorioCentroBean.modelToBean(placaLaboratorio); //llamo al metodo de la clase padre
		
		//replica codigo javi en clase padre
		PlacaLaboratorioCentroAsignacionesBean bean = new PlacaLaboratorioCentroAsignacionesBean();

		bean.setId(placaLaboratorio.getId());

		BeanEstado beanEstado = new BeanEstado();
		beanEstado.asignarTipoEstadoYCodNum(BeanEstado.TipoEstado.EstadoPlacaLabCentro,
				placaLaboratorio.getEstadoPlacaLaboratorio().getId());
		bean.setBeanEstado(beanEstado);		
		bean.setFechaCreacion(placaLaboratorio.getFechaCreacion());
		bean.setFechaListaAnalisis(placaLaboratorio.getFechaListaAnalisis());
		bean.setFechaAsignadaJefe(placaLaboratorio.getFechaAsignadaJefe());
		
		LaboratorioCentroBean laboratorioCentroBean = new LaboratorioCentroBean();	
		laboratorioCentroBean.setId(String.valueOf((placaLaboratorio.getLaboratorioCentro().getId())));
		laboratorioCentroBean.setNombre(placaLaboratorio.getLaboratorioCentro().getNombre());
		bean.setLaboratorioCentro(laboratorioCentroBean);
		
		
		/*Muestras: antes recuperaba las muestras de la placa de laboratorio, ahora salen a traves de la placa visavet y de los lotes
		Set<Muestra> muestras = placaLaboratorio.getMuestras();
		for (Muestra muestra : muestras) {
			BeanListadoMuestraAnalisis muestraBeanAnalisis = BeanListadoMuestraAnalisis.modelToBean(muestra);			
			listadoMuestras.add(muestraBeanAnalisis);
		}
		bean.setMuestras(listadoMuestras);
		*/
		
		Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio
		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();
		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placaLaboratorio.getPlacaVisavetPlacaLaboratorios()) {
			placasVisavetDeLaPlacaLaboratorio.add(placaVisavetPlacaLaboratorio.getPlacaVisavet());
		}
		
		for (PlacaVisavet placaVisavet: placasVisavetDeLaPlacaLaboratorio) {			
			// Recuperamos las muestras de la placa Visavet desde el lote
			Set<Muestra> muestrasVisavet = new HashSet<Muestra>();
			Set<Lote> lotes = placaVisavet.getLotes();
			for (Lote lote : lotes) {
				muestrasVisavet.addAll(lote.getMuestras());
				cjtoMuestrasPlacaLaboratorio.addAll(muestrasVisavet);
			}
		}
		//fin recuperar muestras
		
		List<BeanListadoMuestraAnalisis> listadoMuestras = new ArrayList<BeanListadoMuestraAnalisis>();
		for (Muestra muestra : cjtoMuestrasPlacaLaboratorio) {
			BeanListadoMuestraAnalisis muestraBeanAnalisis = BeanListadoMuestraAnalisis.modelToBean(muestra);			
			listadoMuestras.add(muestraBeanAnalisis);
		}
		bean.setMuestras(listadoMuestras);
		
				

		List<DocumentoBean> documentos = new ArrayList<DocumentoBean>();
		// TODO rellenar Documentos
		bean.setDocumentos(documentos);
		
				
		//analistas de la placa
		//beanAnalisis
		BeanAnalisis beanAnalisis  = new BeanAnalisis();
		BeanListaAsignaciones beanListaAsignaciones = new BeanListaAsignaciones();
		//analistas de la muestra(serán los mismos analistas que la placa), 
		//rellenamos los analistas de la placa a partir de los analistas de la primera muestra de la placa (porque son los mismos para todas las muestras)
		//Set<Muestra> setMuestras = placaLaboratorio.getMuestras();		
		for (Muestra muestra : cjtoMuestrasPlacaLaboratorio) {
			for(UsuarioMuestra usuMuestra: muestra.getUsuarioMuestras()) {
				Usuario usu = usuMuestra.getUsuario();
							
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
		beanAnalisis.setBeanListaAsignaciones(beanListaAsignaciones);
		//el total de analistas asignados será la suma de los analistas lab, analistas vol y analistas vol sin centro
		beanAnalisis.setNumTotalAnalistasAsignados(beanListaAsignaciones.getListaAnalistasLab().size() + beanListaAsignaciones.getListaAnalistasVol().size() + beanListaAsignaciones.getListaAnalistasVolSinLabCentro().size());
		bean.setBeanAnalisisPlaca(beanAnalisis);
		
		//la placa será devolvible si no tiene analistas asignados
		if(beanAnalisis.getNumTotalAnalistasAsignados()==0) {
			bean.setEsDevolvible(true);
		}else if (beanAnalisis.getNumTotalAnalistasAsignados()>0){
			bean.setEsDevolvible(false);
		}


		return bean;

	}
	
	
	

}
