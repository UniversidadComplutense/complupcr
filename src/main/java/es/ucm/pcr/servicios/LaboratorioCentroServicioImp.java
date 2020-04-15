package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioAnalistaBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.GuardarAsignacionPlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesAnalistaBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;
import es.ucm.pcr.repositorio.EstadoPlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.EstadoPlacaVisavetRepositorio;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetPlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
import es.ucm.pcr.repositorio.UsuarioMuestraRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
public class LaboratorioCentroServicioImp implements LaboratorioCentroServicio{
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LaboratorioCentroServicioImp.class);

	@Autowired
	PlacaLaboratorioRepositorio placaLaboratorioRepositorio;
	
	@Autowired
	PlacaVisavetRepositorio placaVisavetRepositorio;
	
	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;
	
	@Autowired
	MuestraRepositorio muestraRepositorio;
	
	@Autowired
	SesionServicio sesionServicio;
		
	@Autowired
	UsuarioRepositorio usuarioRepositorio;	
	
	@Autowired
	UsuarioMuestraRepositorio usuarioMuestraRepositorio;	
	
	@Autowired
	EstadoPlacaVisavetRepositorio estadoPlacaVisavetRepositorio;
	
	@Autowired
	EstadoPlacaLaboratorioRepositorio estadoPlacaLaboratorioRepositorio;
	
	@Autowired
	PlacaVisavetPlacaLaboratorioRepositorio placaVisavetPlacaLaboratorioRepositorio;
	
	@Autowired
	private ServicioLog servicioLog;
	
	
	@Autowired
	MuestraServicio muestraServicio;
	
	public LaboratorioCentro mapeoBeanEntidadLaboratorioCentro(BeanLaboratorioCentro beanLaboratorioCentro) throws Exception{
		
		LaboratorioCentro laboratorioCentro = new LaboratorioCentro();
		
		laboratorioCentro.setId(beanLaboratorioCentro.getId());
		laboratorioCentro.setNombre(beanLaboratorioCentro.getNombre());	
		laboratorioCentro.setDocumentos(beanLaboratorioCentro.getDocumentos());
		laboratorioCentro.setEquipos(beanLaboratorioCentro.getEquipos());
		laboratorioCentro.setPlacaLaboratorios(beanLaboratorioCentro.getPlacaLaboratorios());
		
		return laboratorioCentro;
		
	}
	
	public BeanLaboratorioCentro mapeoEntidadBeanLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception{
		
		BeanLaboratorioCentro beanLaboratorioCentro = new BeanLaboratorioCentro();
		
		beanLaboratorioCentro.setId(laboratorioCentro.getId());
		beanLaboratorioCentro.setNombre(laboratorioCentro.getNombre());
		beanLaboratorioCentro.setDocumentos(laboratorioCentro.getDocumentos());
		beanLaboratorioCentro.setEquipos(laboratorioCentro.getEquipos());
		beanLaboratorioCentro.setPlacaLaboratorios(laboratorioCentro.getPlacaLaboratorios());
		
		return beanLaboratorioCentro;
		
	}
	
	public List<BeanLaboratorioCentro> listaLaboratoriosCentroOrdenada() throws Exception{
		List<BeanLaboratorioCentro> listaLaboratorioCentro = new ArrayList<BeanLaboratorioCentro>();
		for (LaboratorioCentro laboratorioCentro: laboratorioCentroRepositorio.findAll())
		{
			BeanLaboratorioCentro beanLaboratorioCentro = new BeanLaboratorioCentro();
			beanLaboratorioCentro = mapeoEntidadBeanLaboratorioCentro(laboratorioCentro);
			listaLaboratorioCentro.add(beanLaboratorioCentro);
			
//			listaLaboratorioCentro.add(new BeanLaboratorioCentro(
//					laboratorioCentro.getId(), 
//					laboratorioCentro.getNombre(),
//					laboratorioCentro.getDocumentos(),
//					laboratorioCentro.getPlacaLaboratorios(),
//					laboratorioCentro.getEquipos(),
//					"L"));
		}
		//	Ordeno por nombre
		Collections.sort(listaLaboratorioCentro);
		return listaLaboratorioCentro;
	}
	
	public Map<Integer,String> mapaLaboratoriosCentro (List<BeanLaboratorioCentro> laboratoriosCentro) throws Exception{
		Map<Integer, String> mapalaboratorioCentro = new HashMap<Integer, String>();
		for (BeanLaboratorioCentro laboratorioCentro :laboratoriosCentro)
		{
			mapalaboratorioCentro.put(laboratorioCentro.getId(), laboratorioCentro.getNombre());
		}
		return mapalaboratorioCentro;
	}
	
	public LaboratorioCentro save (LaboratorioCentro laboratorioCentro) throws Exception{
		return laboratorioCentroRepositorio.save(laboratorioCentro);
	}
	
	public void deleteById (Integer idLaboratorioCentro) throws Exception{
		laboratorioCentroRepositorio.deleteById(idLaboratorioCentro);
	}
	
	@Transactional
	public Optional <LaboratorioCentro> findById (Integer idLaboratorioCentro) throws Exception{
		return laboratorioCentroRepositorio.findById(idLaboratorioCentro);
	}
	
	// JAVI
	@Override
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroBean> listaPlacasLaboratorioCentroBean = new ArrayList<PlacaLaboratorioCentroBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroBean.add(PlacaLaboratorioCentroBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}

	
	//JAVI
	@Override
	public PlacaLaboratorioCentroBean crearPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean) {
		
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);

		placa.setFechaCreacion(new Date());
		placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_INICIADA.getCodNum()));
		placa.setLaboratorioCentro(new LaboratorioCentro(sesionServicio.getUsuario().getIdLaboratorioCentro()));

		placa = placaLaboratorioRepositorio.save(placa);
		
		// Transformamos en un Array el String que viene de la vista con los IDs de las placas Visavet seleccionadas
		String[] listaIDsPlacasVisavetSeleccinadas = placaLaboratorioCentroBean.getPlacasVisavetSeleccionadas().split(":");
		
		// Asignamos a la placa de laboratorio las placas Visavet seleccionadas
		if (listaIDsPlacasVisavetSeleccinadas.length > 0) {
			
			Set<PlacaVisavetPlacaLaboratorio> placaVisavetPlacaLaboratorios = new HashSet<PlacaVisavetPlacaLaboratorio>();
			
			for (String idPlacaVisavet : listaIDsPlacasVisavetSeleccinadas) {

				PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio = new PlacaVisavetPlacaLaboratorio();
				PlacaLaboratorio placaLaboratorio = placaLaboratorioRepositorio.getOne(placa.getId());
				PlacaVisavet placaVisavet = placaVisavetRepositorio.getOne(Integer.valueOf(idPlacaVisavet));
				
				// Cambiamos el estado de la placa Visavet a traspasada
				placaVisavet.setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_TRANSPASADA.getCodNum()));
				placaVisavet = placaVisavetRepositorio.save(placaVisavet);				
				placaVisavetPlacaLaboratorio.setPlacaLaboratorio(placaLaboratorio);
				placaVisavetPlacaLaboratorio.setPlacaVisavet(placaVisavet);
				
				placaVisavetPlacaLaboratorioRepositorio.save(placaVisavetPlacaLaboratorio);
				
				placaVisavetPlacaLaboratorios.add(placaVisavetPlacaLaboratorio);
			}
			placa.setPlacaVisavetPlacaLaboratorios(placaVisavetPlacaLaboratorios);
			placa = placaLaboratorioRepositorio.save(placa);
		}		
		return PlacaLaboratorioCentroBean.modelToBean(placa);

	}

	
	//JAVI
	@Override
	public PlacaLaboratorioCentroBean guardarPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean) {
		
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);
		
		// Placa nueva
		if (placaLaboratorioCentroBean.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_INICIADA.getCodNum()));
			placa.setLaboratorioCentro(new LaboratorioCentro(sesionServicio.getUsuario().getIdLaboratorioCentro()));
		}
		placa = placaLaboratorioRepositorio.save(placa);
		return PlacaLaboratorioCentroBean.modelToBean(placa);

	}
	
	// JAVI
	@Override
	public PlacaLaboratorioCentroBean buscarPlaca(Integer id) {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioCentroBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioCentroBean();
	}

	// JAVI
	@Override
	public void finalizarPCR(Integer id) {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_PREPARADA_PARA_PCR.getCodNum()) {
				placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_FINALIZADA_PCR.getCodNum()));
				placaLaboratorioRepositorio.save(placa.get());
			}			
		}
	}
	
	// JAVI
	@Override
	public void asignarEquipoPCR(Integer id) {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_INICIADA.getCodNum()) {
				
				//TODO asignar a un equipo del laboratorio si así nos lo piden finalmente
				
				placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_PREPARADA_PARA_PCR.getCodNum()));
				placaLaboratorioRepositorio.save(placa.get());
			}			
		}
	}

	
	//Diana- metodos para jefe de servicio (replica de metodos de Javi con mi bean)
	
	@Override
	public PlacaLaboratorioCentroAsignacionesBean buscarPlacaAsignaciones(Integer id) {
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioCentroAsignacionesBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioCentroAsignacionesBean();
	}
	
	
	
	@Override
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroBean> listaPlacasLaboratorioCentroBean = new ArrayList<PlacaLaboratorioCentroBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroBean.add(PlacaLaboratorioCentroBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}
	
	@Override
	@Transactional
	public PlacaLaboratorioCentroBean guardarCogerODevolverPlaca(Integer idPlaca, Integer idUsuario, String accion) {
	//metodo que recibe el idPlaca, y el id de usuario que la quiere coger
	
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);		
				
		if(accion.equals("coger")) {
			//asocia la placa al usuario, le cambia el estado de la placa a PLACA_ASIGNADA_PARA_ANALISIS y pone a todas sus muestras a estado pendente de analizar
			Usuario usuario = usuarioRepositorio.getOne(idUsuario);
			placa.setUsuario(usuario);
			EstadoPlacaLaboratorio estadoPlacaLab = estadoPlacaLaboratorioRepositorio.getOne(BeanEstado.Estado.PLACA_ASIGNADA_PARA_ANALISIS.getCodNum());
			System.out.println("el estado que le vamos a asignar a la placa es: " + estadoPlacaLab.getDescripcion());
			placa.setEstadoPlacaLaboratorio(estadoPlacaLab);
			//recorremos todas las muestras de esa placa y les ponemos el estado pendente de analizar
			for(Muestra m: placa.getMuestras()) {
				m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_PENDIENTE_ANALIZAR.getCodNum()));
				muestraRepositorio.save(m);
				//guardamos en el log el cambio de estado de la muestra
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
			}			
		}else if(accion.equals("devolver")) {
			//desasocia la placa del usuario, le cambia el estado de la placa a PLACA_LISTA_PARA_ANALISIS (estado anterior) y ¿Qué hacemos con las muestras?
			placa.setUsuario(null);
			EstadoPlacaLaboratorio estadoPlacaLab = estadoPlacaLaboratorioRepositorio.getOne(BeanEstado.Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum());			
			System.out.println("el estado que le vamos a asignar a la placa es: " + estadoPlacaLab.getDescripcion());
			placa.setEstadoPlacaLaboratorio(estadoPlacaLab);
			//TODO preguntar que hacemos con las muestras cuando devolvermos la placa?
			//recorremos todas las muestras de esa placa y les ponemos en el estado que tenian al llegar MUESTRA_ENVIADA_CENTRO_ANALISIS?
			for(Muestra m: placa.getMuestras()) {
				m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum()));
				muestraRepositorio.save(m);
				//guardamos en el log el cambio de estado de la muestra
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
			}	
		}
		placa = placaLaboratorioRepositorio.save(placa);
		//TODO guardamos en el log el cambio de estado de la placa (no tenemos tabla de log de placas)
		
		return PlacaLaboratorioCentroBean.modelToBean(placa);

	}
	
	
	@Override
	public List<PlacaLaboratorioCentroBean> buscarPlacasAsignadasAJefe(Usuario usuario) {	
		//busca la lissta de placas que se ha asignado el Jefe (que estan bajo su responsabilidad)
		List<PlacaLaboratorioCentroBean> listaBeanPlacasLaboratorioDeJefe = new ArrayList<PlacaLaboratorioCentroBean>();
		List<PlacaLaboratorio> lisaPlacasJefe =  placaLaboratorioRepositorio.findByUsuario(usuario);
		for(PlacaLaboratorio placa: lisaPlacasJefe) {
			PlacaLaboratorioCentroBean placaBean = PlacaLaboratorioCentroBean.modelToBean(placa);
			listaBeanPlacasLaboratorioDeJefe.add(placaBean);			
		}		
		return listaBeanPlacasLaboratorioDeJefe;
	}
	
	@Override
	public List<BeanElemento> buscarPlacasBeanElementoAsignadasAJefe(Usuario usuario) {	
		//busca la lissta de placas que se ha asignado el Jefe (que estan bajo su responsabilidad)
		List<BeanElemento> listaBeanPlacasLaboratorioDeJefe = new ArrayList<BeanElemento>();
		List<PlacaLaboratorio> lisaPlacasJefe =  placaLaboratorioRepositorio.findByUsuario(usuario);		
		for(PlacaLaboratorio placa: lisaPlacasJefe) {
			BeanElemento beanElementoPlaca = new BeanElemento();
			beanElementoPlaca.setCodigo(placa.getId());
			beanElementoPlaca.setDescripcion("Placa " + placa.getId() + ", muestras: " + placa.getMuestras().size());
			listaBeanPlacasLaboratorioDeJefe.add(beanElementoPlaca);			
		}		
		//añado el elemento seleccione al principio
		BeanElemento seleccione = new BeanElemento();
		seleccione.setCodigo(null);
		seleccione.setDescripcion("Seleccione");
		listaBeanPlacasLaboratorioDeJefe.add(0,seleccione);
		return listaBeanPlacasLaboratorioDeJefe;
	}
	
	//metodo que guarda las nuevas asignaciones de analistas de labCentro, voluntarios de labCentro y voluntarios sin centro a la placa 
	//se guardan en realidad las asignaciones a las muestras de la placa
	@Override
	@Transactional
	public void guardarAsignacionesAnalistasYVoluntariosAPlacaYmuestras(GuardarAsignacionPlacaLaboratorioCentroBean formBeanGuardarAsignacionPlaca) {
	
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(formBeanGuardarAsignacionPlaca.getIdPlaca());
		
		//si se ha marcado algun analistalab, analistavol u otros vol 
		if(formBeanGuardarAsignacionPlaca.getListaIdsAnalistasLabSeleccionados().size()>0 ||
			formBeanGuardarAsignacionPlaca.getListaIdsAnalistasVolSeleccionados().size()>0 ||
			formBeanGuardarAsignacionPlaca.getListaIdsVolSinLabCentroSeleccionados().size()>0) {
			
			Date fechaAsignacion = new Date();
			
			//recorremos todas las muestras de esa placa, les asignamos los nuevos analistas, aumentamos el contador de analistas asignados
			//y les ponemos el estado asignada analista
			for(Muestra muestra: placa.getMuestras()) {
				Integer numerodeAnalistasAsignadosMuestra = (muestra.getNumerodeAnalistasAsignados()==null) ? 0 : muestra.getNumerodeAnalistasAsignados(); 
				//creamos nuevos usuarioMuestras por cada nueva asignacion de cada una de las muestras
				//recorremos los analistas de labaratorio marcados para asignar
				for(Integer idAnalistaLabSeleccionado: formBeanGuardarAsignacionPlaca.getListaIdsAnalistasLabSeleccionados()) {
					Usuario analistaLab = usuarioRepositorio.getOne(idAnalistaLabSeleccionado);
					UsuarioMuestra nuevoUsuarioMuestra = new UsuarioMuestra(muestra,analistaLab);
					nuevoUsuarioMuestra.setFechaAsignacion(fechaAsignacion);
					usuarioMuestraRepositorio.save(nuevoUsuarioMuestra);
					numerodeAnalistasAsignadosMuestra = numerodeAnalistasAsignadosMuestra +1;
				}
				//recorremos los analistas voluntarios de labaratorio marcados para asignar
				for(Integer idAnalistaVolSeleccionado: formBeanGuardarAsignacionPlaca.getListaIdsAnalistasVolSeleccionados()) {
					Usuario analistaVol = usuarioRepositorio.getOne(idAnalistaVolSeleccionado);
					UsuarioMuestra nuevoUsuarioMuestra = new UsuarioMuestra(muestra,analistaVol);
					nuevoUsuarioMuestra.setFechaAsignacion(fechaAsignacion);
					usuarioMuestraRepositorio.save(nuevoUsuarioMuestra);
					numerodeAnalistasAsignadosMuestra = numerodeAnalistasAsignadosMuestra +1;
				}
				//recorremos los otros voluntarios sin labaratorioCentro marcados para asignar
				for(Integer idOtroVolSeleccionado: formBeanGuardarAsignacionPlaca.getListaIdsVolSinLabCentroSeleccionados()) {
					Usuario otroVol = usuarioRepositorio.getOne(idOtroVolSeleccionado);
					UsuarioMuestra nuevoUsuarioMuestra = new UsuarioMuestra(muestra,otroVol);
					nuevoUsuarioMuestra.setFechaAsignacion(fechaAsignacion);
					usuarioMuestraRepositorio.save(nuevoUsuarioMuestra);
					numerodeAnalistasAsignadosMuestra = numerodeAnalistasAsignadosMuestra +1;
				}
				//cambiamos el estado de la muestra a asignada a analista							
				muestra.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ASIGNADA_ANALISTA.getCodNum()));
				muestra.setNumerodeAnalistasAsignados(numerodeAnalistasAsignadosMuestra);
				muestraRepositorio.save(muestra);
				//guardamos en el log el cambio de estado de la muestra
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, muestra.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(muestra.getId(), estadoMuestra);				
			}
		}
		else {
			//si no se ha marcado ningun nuevo analista no hacemos nada
			System.out.println("no se ha escogido ningun analista nuevo, no hacemos asignaciones nuevas ni cambiamos el estado a sus muestras");			
		}
				
		//a la placa no hay que cambiarle el estado, solo cambiamos el estado de sus muestras		

	}
	
	//metodo que nos dice si una muestra está resuelta o no
	private Boolean muestraResuelta(Muestra m) {
		Boolean resuelta = false;		
		//una muestra está resuelta si tiene estado resuelta, tiene resultado y fecha de resultado		
		if(m.getEstadoMuestra().getId() == Estado.MUESTRA_RESUELTA.getCodNum() &&
		   m.getResultado()!=null && !m.getResultado().isEmpty() && m.getFechaResultado() !=null) {
			resuelta = true;
		}
		return resuelta;
	}
	
	
	//metodo que nos dirá si las muestras de la placa pasada por parámetro tiene un resultado definitivo 
	@Override
	public Boolean tienenResultadoDefinitivoLasMuestrasDeLaPlaca(Integer idPlaca) {		
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);
		//si la placa no tiene muestras devolvemos false
		if(placa.getMuestras().size()==0) {
			return false;
		}else {			
			//recorremos las muestras de la placa y si alguna no está resuelta
			// es decir	no tiene el estado resuelta, no tiene resultado o no tiene fecha de resultado) devolvemos false
			for(Muestra m : placa.getMuestras()) {
				if(this.muestraResuelta(m)==false) {
					return false;
				}
			}
		}		
		return true;
	}
	
	//Fin Diana- metodos para jefe de servicio
	
	//Diana- metodos para los analistas de placas
	@Override
	public Page<PlacaLaboratorioCentroAsignacionesAnalistaBean> buscarPlacas(BusquedaPlacaLaboratorioAnalistaBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroAsignacionesAnalistaBean> listaPlacasLaboratorioCentroAsignacionesBean = new ArrayList<PlacaLaboratorioCentroAsignacionesAnalistaBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParamsValoradasAndNotValoradas(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			System.out.println("Placa vale: "+ placa.toString());
			PlacaLaboratorioCentroAsignacionesAnalistaBean placaLaboratorioCentroAsignacionesAnalistaBean = PlacaLaboratorioCentroAsignacionesAnalistaBean.modelToBean(placa, criteriosBusqueda.getIdAnalistaMuestras());			
			listaPlacasLaboratorioCentroAsignacionesBean.add(placaLaboratorioCentroAsignacionesAnalistaBean);
		}		
		Page<PlacaLaboratorioCentroAsignacionesAnalistaBean> placasLaboratorioCentroAsignacionesAnalista = new PageImpl<>(listaPlacasLaboratorioCentroAsignacionesBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentroAsignacionesAnalista;
	}
	
	
	private Integer calculaCuantasValoracionesTieneLaMuestra(Muestra m) {
		//metodo que nos dice cuantas valoraciones han dado los analistas asignados a la muestra
		//de una muestra recuperamos todos sus analistas asignados y contamos cuantos han puesto una valoracion
		Integer cont=0;
		for(UsuarioMuestra um: m.getUsuarioMuestras()) {
			if(um.getValoracion()!=null) {
				cont = cont + 1;
			}
		}
		return cont;
	}
	
	@Override
	@Transactional
	//metodo que recibe el ElementoDocumentacionBean (excel con valoraciones) y carga las valoraciones que ha metido el analista en todas las muestras de la placa 
	public void guardarResultadosPlacaLaboratorio(ElementoDocumentacionBean bean, Integer numAnalistas) {
		//buscamos todas los registros usuarioMuestra de las muestras de la placa
		Integer idPlaca = bean.getId(); //aqui solo puede llegar el id de una placa del analista logado que tenga sus muestras sin valorar
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);
		Integer idUsuarioLogado = sesionServicio.getUsuario().getId(); 
		Date fechaActual = new Date();
		//por cada muestra de la placa
		for(Muestra m: placa.getMuestras()) {
			String valoracionExcelParaMuestra = "POSITIVO"; //TODO, aquí habría que recoger la valoracion del excel para la muestra, esto es un ejemplo
			//recuperamos el usuarioMuestra asociado al usuario logado (analista que está valorando las muestras de la placa)
			Optional<UsuarioMuestra> optusumu = usuarioMuestraRepositorio.findByIdUsuarioAndIdMuestra(idUsuarioLogado, m.getId());
			if (optusumu.isPresent()) {
				UsuarioMuestra usumu = optusumu.get();
				//aqui tendriamos que asociarle la valoracion que viene en el excel para esa muestra 
				usumu.setValoracion(valoracionExcelParaMuestra);
				usumu.setFechaValoracion(fechaActual);
				UsuarioMuestra usumuGuardado = usuarioMuestraRepositorio.save(usumu);
			}
			else {
				log.error("No se ha encontrado el registro de usuario muestra para asignarle la valoracion del analista");
			}
			
			//por cada muestra calculamos si ya se han dado las valoraciones de todos los analistas
			//en ese caso el resultado sería definitivo y se guardara el resultado en la muestra y se enviará la notificacion 
			Integer numValoracionesMuestra = this.calculaCuantasValoracionesTieneLaMuestra(m);
			System.out.println("el numero de valoraciones actuales de la muestra con id: "+ m.getId()+" es: " + numValoracionesMuestra);
			if(numValoracionesMuestra.equals(numAnalistas)) {
				//guardamos como resultado definitivo este ultimo resultado que está dando el analista que viene del excel
				m.setResultado(valoracionExcelParaMuestra);
				m.setFechaResultado(fechaActual);				
				//cambiamos el estado de la muestra a resuelta y la guardamos							
				m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_RESUELTA.getCodNum()));				
				muestraRepositorio.save(m);
				//anotamos en el log el cambio de estado de la muestra				
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
				//Envio de notificacion al pacientes si tiene activada la notificacion automatica
				//recupero el paciente de la muestra
				Paciente paciente = m.getPaciente();
				System.out.println("el paciente con id: " + paciente.getId()+" tiene la notificacion automato a: " + paciente.getNotificarAutomato());
				if(paciente.getNotificarAutomato().equals("S")) {
					muestraServicio.actualizarNotificacionMuestra(m.getId(), true); //actualiza fecha de notificación y envia correo
				}
				
			}
			
		}			
	}
	
	
	
	
	
	
	
	
	//fin Diana- metodos para los analistas de placas
	
	
	public BeanLaboratorioCentro buscarLaboratorioById(Integer id) {
		Optional<LaboratorioCentro> laboratorioOp= laboratorioCentroRepositorio.findById(id);
		
		if (laboratorioOp.isPresent()) {
			LaboratorioCentro laboratorioCentro=laboratorioOp.get();
			return new BeanLaboratorioCentro(
					laboratorioCentro.getId(), 
					laboratorioCentro.getNombre(),
					laboratorioCentro.getDocumentos(),
					laboratorioCentro.getPlacaLaboratorios(),
					laboratorioCentro.getEquipos(),
					"L");
		}
		else return null;
	}
	
	
}
