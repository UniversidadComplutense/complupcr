package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanEquipo;
import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanLaboratorioCentro;
import es.ucm.pcr.beans.BeanResultado;
import es.ucm.pcr.beans.BeanResultadoCarga;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioAnalistaBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioBean;
import es.ucm.pcr.beans.BusquedaPlacaLaboratorioJefeBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.GuardarAsignacionPlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesAnalistaBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroBean;
import es.ucm.pcr.modelo.orm.Equipo;
import es.ucm.pcr.modelo.orm.EstadoMuestra;
import es.ucm.pcr.modelo.orm.EstadoPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.Paciente;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.modelo.orm.UsuarioMuestra;
import es.ucm.pcr.repositorio.EquipoRepositorio;
import es.ucm.pcr.repositorio.EstadoPlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.EstadoPlacaVisavetRepositorio;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetPlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
import es.ucm.pcr.repositorio.UsuarioMuestraRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;
import es.ucm.pcr.utilidades.Utilidades;

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
	DocumentoServicio documentoServicio;
	
	@Autowired
	MuestraServicio muestraServicio;
	
	@Autowired
	EquipoRepositorio equipoRepositorio;
	
	@Autowired
	EquipoServicio equipoServicio;
	
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
	@Transactional
	public Page<PlacaLaboratorioCentroBean> buscarPlacas(BusquedaPlacaLaboratorioBean criteriosBusqueda,
			Pageable pageable) throws Exception {
		
		List<PlacaLaboratorioCentroBean> listaPlacasLaboratorioCentroBean = new ArrayList<PlacaLaboratorioCentroBean>();

		// Si no se ha seleccionado el estado de la placa en la búsqueda, buscamos por los estados: PLACA_INICIADA ó PLACA_PREPARADA_PARA_PCR
		// ó PLACA_FINALIZADA_PCR ó PLACA_LISTA_PARA_ANALISIS
		if (criteriosBusqueda.getIdEstadoPlaca() == null || criteriosBusqueda.getIdEstadoPlaca() == 0) {
			criteriosBusqueda.setEstadosBusqueda(Arrays.asList(Estado.PLACA_INICIADA.getCodNum(), Estado.PLACA_PREPARADA_PARA_PCR.getCodNum(), 
																Estado.PLACA_FINALIZADA_PCR.getCodNum(), Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum()));
		} else {
			criteriosBusqueda.setEstadosBusqueda(Arrays.asList(criteriosBusqueda.getIdEstadoPlaca()));
		}
		
		if (criteriosBusqueda.getFechaCreacionFin() != null) {
			criteriosBusqueda.setFechaCreacionFin(Utilidades.fechafinBuscador(criteriosBusqueda.getFechaCreacionFin()));			
		}
		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroBean.add(PlacaLaboratorioCentroBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}

	
	// JAVI
	@Override
	@Transactional
	public PlacaLaboratorioCentroBean buscarPlaca(Integer id) throws Exception {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioCentroBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioCentroBean();
	}

	// JAVI
	@Override
	@Transactional
	public boolean finalizarPCR(Integer id) throws Exception {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_PREPARADA_PARA_PCR.getCodNum()) {
				placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_FINALIZADA_PCR.getCodNum()));
				placaLaboratorioRepositorio.save(placa.get());
				// TODO Registrar en LOG placaLaboratorio ha finalizado PCR
				return true;
			}			
		}
		return false;
	}
	
	// JAVI
	@Override
	@Transactional
	public boolean asignarEquipoPCR(Integer idPlaca, Integer idEquipo) throws Exception {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(idPlaca);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_INICIADA.getCodNum()) {
				
				placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_PREPARADA_PARA_PCR.getCodNum()));
				if (idEquipo != null && idEquipo > 0) {
					placa.get().setEquipo(new Equipo(idEquipo));
				}
				placaLaboratorioRepositorio.save(placa.get());
				// TODO Registrar en LOG placaLaboratorio preparada para PCR
				return true;
			}			
		}
		return false;
	}
	
	// JAVI
	@Override
	@Transactional
	public boolean placaListaParaAnalizar(Integer id) throws Exception {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_FINALIZADA_PCR.getCodNum()) {
				ElementoDocumentacionBean documentosPlaca = documentoServicio.obtenerDocumentosPlacaLaboratorio(id);
				if (documentosPlaca != null && documentosPlaca.getDocumentos() != null && documentosPlaca.getDocumentos().size() >0) {
					placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum()));
					placa.get().setFechaListaAnalisis(new Date());
					placaLaboratorioRepositorio.save(placa.get());
					// No registramos en el log que las muestras de la placa están listas para ser analizadas porque lo hace Diana
					// servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(id, new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_PENDIENTE_ANALIZAR));
					
					// TODO Registrar en LOG placaLaboratorio lista para analizar				
					return true;
				}
			}			
		}
		return false;
	}
	
	// JAVI
	@Override
	@Transactional
	public boolean haySubidoAlgunDocumentoPorParteDelLaboratorio(Integer id) throws Exception {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_FINALIZADA_PCR.getCodNum()) {
				ElementoDocumentacionBean documentosPlaca = documentoServicio.obtenerDocumentosPlacaLaboratorio(id);
				if (documentosPlaca != null && documentosPlaca.getDocumentos() != null && documentosPlaca.getDocumentos().size() >0) {		
					return true;
				}
			}			
		}
		return false;
	}
	
	// JAVI
	@Override
	public boolean esEditable(Integer id) throws Exception {	

		return id == null;		
	}
	
	// JAVI
	@Override
	public Integer espacioLibreParaMuestras(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) throws Exception {		
		 
		Integer espacioLibreParaMuestras = 0;
		
		if (placaLaboratorioCentroBean.getId() != null) {
			Optional<PlacaLaboratorio> placaLaboratorio = placaLaboratorioRepositorio.findById(placaLaboratorioCentroBean.getId());
			if (placaLaboratorio.isPresent()) {	
				Integer numeroMuestrasPlaca = 0;
				// Inicialmente el espacio libre de la placa coincide con su capacidad 
				espacioLibreParaMuestras = Integer.valueOf(placaLaboratorio.get().getNumeromuestras());
				Set<PlacaVisavetPlacaLaboratorio> placasVisavetPlacasLaboratorio = placaLaboratorio.get().getPlacaVisavetPlacaLaboratorios();
				for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placasVisavetPlacasLaboratorio) {
					for (Lote lote : placaVisavetPlacaLaboratorio.getPlacaVisavet().getLotes()) {
						numeroMuestrasPlaca += lote.getMuestras().size();
					}
				}
				return espacioLibreParaMuestras - numeroMuestrasPlaca;	
			}
			return espacioLibreParaMuestras;
		} else {
			// Si la placa es nueva
			return capacidadNuevaPlaca;
		}	
	}
	
	// JAVI
	// Si la placa es de nueva creación, pasamos por parámetro la capacidad que tendrá la misma, y si no es nueva pasamos un cero.
	@Override
	@Transactional
	public boolean esRellenable(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) throws Exception {		
		
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);

		Integer espacioLibreParaMuestras = this.espacioLibreParaMuestras(placaLaboratorioCentroBean, capacidadNuevaPlaca);
		Integer sumaMuestrasPlacasVisavetSeleccionadas = 0;
		
		// Transformamos el String que viene de la vista, en un Array de Strings con los IDs de las placas Visavet seleccionadas
		String[] listaIDsPlacasVisavetSeleccinadas = placaLaboratorioCentroBean.getPlacasVisavetSeleccionadas().split(":");
		
		// Calculamos el número de muestras de la placa(s) Visavet que se pretenden agregar a la placa de laboratorio
		if (listaIDsPlacasVisavetSeleccinadas.length > 0) {			
						
			for (String idPlacaVisavet : listaIDsPlacasVisavetSeleccinadas) {
			
				Set<PlacaVisavetPlacaLaboratorio> placasVisavetPlacasLaboratorio = placa.getPlacaVisavetPlacaLaboratorios();
				for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placasVisavetPlacasLaboratorio) {
					for (Lote lote : placaVisavetPlacaLaboratorio.getPlacaVisavet().getLotes()) {
						sumaMuestrasPlacasVisavetSeleccionadas += lote.getMuestras().size();
					}
				}
			}
		}		
		return espacioLibreParaMuestras >= sumaMuestrasPlacasVisavetSeleccionadas;		
	}
	
	// JAVI
	// Si la placa es de nueva creación, pasamos por parámetro la capacidad que tendrá la misma, y si no es nueva pasamos un cero.
	@Override
	@Transactional
	public PlacaLaboratorioCentroBean rellenarPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) throws Exception {		
		
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);

		placa.setLaboratorioCentro(new LaboratorioCentro(sesionServicio.getUsuario().getIdLaboratorioCentro()));
		
		// Es una placa nueva
		if (capacidadNuevaPlaca != 0) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_INICIADA.getCodNum()));			
			placa = placaLaboratorioRepositorio.save(placa);
		}		
		
		// Asignamos las placas Visavet si hay espacio en la placa de laboratorio
		if (this.esRellenable(placaLaboratorioCentroBean, capacidadNuevaPlaca)) {
			
			// Transformamos el String que viene de la vista, en un Array de Strings con los IDs de las placas Visavet seleccionadas
			String[] listaIDsPlacasVisavetSeleccinadas = placaLaboratorioCentroBean.getPlacasVisavetSeleccionadas().split(":");
			
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
					
					// Registramos en el log que las muestras de la placa se han traspasado a un placa de laboratorio conservando su estado MUESTRA_ENVIADA_CENTRO_ANALISIS
					servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(placa.getId(), new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS));
					
					// TODO Registrar en LOG placaVisavet traspasada
					
					placaVisavetPlacaLaboratorios.add(placaVisavetPlacaLaboratorio);
				}
				placa.setPlacaVisavetPlacaLaboratorios(placaVisavetPlacaLaboratorios);
				placa = placaLaboratorioRepositorio.save(placa);
				
				// Cambio de estado muestras
				//guardo en el log
				BeanEstado estado=new BeanEstado();
			    estado.setEstado(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS);
			    estado.setTipoEstado(TipoEstado.EstadoMuestra);
				servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(placa.getId(), estado);
				
				return PlacaLaboratorioCentroBean.modelToBean(placa);
				
				// TODO Registrar en LOG placaLaboratorio creada
				
			}
		}	
		return null;		
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
	public Page<PlacaLaboratorioCentroAsignacionesBean> buscarPlacas(BusquedaPlacaLaboratorioJefeBean criteriosBusqueda,
			Pageable pageable) {
		
		List<PlacaLaboratorioCentroAsignacionesBean> listaPlacasLaboratorioCentroAsignacionesBean = new ArrayList<PlacaLaboratorioCentroAsignacionesBean>();		
		Page<PlacaLaboratorio> PagePlacasLaboratorioCentro = placaLaboratorioRepositorio.findByParams(criteriosBusqueda, pageable); 		
		for (PlacaLaboratorio placa : PagePlacasLaboratorioCentro.getContent()) {
			listaPlacasLaboratorioCentroAsignacionesBean.add(PlacaLaboratorioCentroAsignacionesBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioCentroAsignacionesBean> placasLaboratorioCentro = new PageImpl<>(listaPlacasLaboratorioCentroAsignacionesBean, pageable, PagePlacasLaboratorioCentro.getTotalElements());		
		return placasLaboratorioCentro;
	}
	
	@Override
	@Transactional
	public PlacaLaboratorioCentroBean guardarCogerODevolverPlaca(Integer idPlaca, Integer idUsuario, String accion) {
	//metodo que recibe el idPlaca, y el id de usuario que la quiere coger o devolver
	
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);
		
		//obtencion de las muestras de la placa a través de a traves de sus placas visavet y los lotes
		Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placa.getPlacaVisavetPlacaLaboratorios()) {
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
		//fin obtencion cjto de muestras
		
				
		if(accion.equals("coger")) {
			//asocia la placa al usuario y fecha asignacion, le cambia el estado de la placa a PLACA_ASIGNADA_PARA_ANALISIS y pone a todas sus muestras a estado MUESTRA_PENDIENTE_ANALIZAR
			Usuario usuario = usuarioRepositorio.getOne(idUsuario);
			placa.setUsuario(usuario);
			placa.setFechaAsignadaJefe(new Date());
			EstadoPlacaLaboratorio estadoPlacaLab = estadoPlacaLaboratorioRepositorio.getOne(BeanEstado.Estado.PLACA_ASIGNADA_PARA_ANALISIS.getCodNum());
			log.info("el estado que le vamos a asignar a la placa es: " + estadoPlacaLab.getDescripcion());
			placa.setEstadoPlacaLaboratorio(estadoPlacaLab);			
			//recorremos todas las muestras de esa placa y les ponemos el estado pendente de analizar
			//las muestras de la placa las obtenemos a traves de sus placas visavet y de los lotes
			//for(Muestra m: placa.getMuestras()) {
			for(Muestra m: cjtoMuestrasPlacaLaboratorio) {
				m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_PENDIENTE_ANALIZAR.getCodNum()));
				muestraRepositorio.save(m);
				/*
				//guardamos en el log el cambio de estado de la muestra
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
				*/
			}
			//guardamos en el log el cambio de estado de las muestras a raiz del cambio de estado de la placa de laboratorio
			BeanEstado estadoMuestra = new BeanEstado();
			estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.MUESTRA_PENDIENTE_ANALIZAR.getCodNum());
			servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(idPlaca, estadoMuestra);
		}else if(accion.equals("devolver")) {
			//desasocia la placa del usuario y le quita la fecha de asignacion, le cambia el estado de la placa a PLACA_LISTA_PARA_ANALISIS (estado anterior) y ponemos a las muestras su estado anterior MUESTRA_ENVIADA_CENTRO_ANALISIS
			placa.setUsuario(null);
			placa.setFechaAsignadaJefe(null);
			EstadoPlacaLaboratorio estadoPlacaLab = estadoPlacaLaboratorioRepositorio.getOne(BeanEstado.Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum());			
			log.info("el estado que le vamos a asignar a la placa es: " + estadoPlacaLab.getDescripcion());
			placa.setEstadoPlacaLaboratorio(estadoPlacaLab);
			//placa.setFechaListaAnalisis(new Date()); la fecha en que transicionó a lista para analisis al llegar a los jefes no la machaco aunque devuelva la placa 
			//TODO preguntar que hacemos con las muestras cuando devolvermos la placa?
			//recorremos todas las muestras de esa placa y les ponemos en el estado que tenian al llegar MUESTRA_ENVIADA_CENTRO_ANALISIS?
			//las muestras de la placa las obtenemos a traves de sus placas visavet y de los lotes
			//for(Muestra m: placa.getMuestras()) {
			for(Muestra m: cjtoMuestrasPlacaLaboratorio) {
				m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum()));
				muestraRepositorio.save(m);
				/*
				//guardamos en el log el cambio de estado de la muestra
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
				*/
			}
			//guardamos en el log el cambio de estado de las muestras a raiz del cambio de estado de la placa de laboratorio
			BeanEstado estadoMuestra = new BeanEstado();
			estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS.getCodNum());
			servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(idPlaca, estadoMuestra);
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
			
			//obtencion de las muestras de la placa a través de a traves de sus placas visavet los lotes
			Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio		
			Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();		
			for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placa.getPlacaVisavetPlacaLaboratorios()) {
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
			//fin obtencion cjto de muestras
			
			//beanElementoPlaca.setDescripcion("Placa " + placa.getId() + ", muestras: " + placa.getMuestras().size());
			beanElementoPlaca.setDescripcion("Placa " + placa.getId() + ", muestras: " + cjtoMuestrasPlacaLaboratorio.size());
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
		
		//obtencion de las muestras de la placa a través de a traves de sus placas visavet los lotes
		Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placa.getPlacaVisavetPlacaLaboratorios()) {
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
		//fin obtencion cjto de muestras
		
		//si se ha marcado algun analistalab, analistavol u otros vol 
		if(formBeanGuardarAsignacionPlaca.getListaIdsAnalistasLabSeleccionados().size()>0 ||
			formBeanGuardarAsignacionPlaca.getListaIdsAnalistasVolSeleccionados().size()>0 ||
			formBeanGuardarAsignacionPlaca.getListaIdsVolSinLabCentroSeleccionados().size()>0) {
			
			Date fechaAsignacion = new Date();
			
			//recorremos todas las muestras de esa placa, les asignamos los nuevos analistas, aumentamos el contador de analistas asignados
			//y les ponemos el estado asignada analista
			//for(Muestra muestra: placa.getMuestras()) { //ahora obtenemos las muestras a partir de la placa visavet y el lote
			for(Muestra muestra: cjtoMuestrasPlacaLaboratorio) {				
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
				/*
				//guardamos en el log el cambio de estado de la muestra
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, muestra.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(muestra.getId(), estadoMuestra);
				*/				
			}
			//guardamos en el log el cambio de estado de las muestras a raiz de asignar la placa de laboratorio
			BeanEstado estadoMuestra = new BeanEstado();
			estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.MUESTRA_ASIGNADA_ANALISTA.getCodNum());
			servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(formBeanGuardarAsignacionPlaca.getIdPlaca(), estadoMuestra);
		}
		else {
			//si no se ha marcado ningun nuevo analista no hacemos nada
			log.info("no se ha escogido ningun analista nuevo, no hacemos asignaciones nuevas ni cambiamos el estado a sus muestras");			
		}
				
		//a la placa no hay que cambiarle el estado, solo cambiamos el estado de sus muestras		

	}
	
	//metodo que reemplaza la asignacion de un analista por otro (siempre que no tenga valoracion en sus muestras) 
	//se reemplazan en realidad las asignaciones a las muestras de la placa
	@Override
	@Transactional
	public void guardarReemplazoAnalistaDePlacaYmuestras(Integer idPlaca, Integer idUsuarioAQuitar, Integer idUsuarioAPoner) {
	
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);
		
		//obtencion de las muestras de la placa a través de a traves de sus placas visavet los lotes
		Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placa.getPlacaVisavetPlacaLaboratorios()) {
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
		//fin obtencion cjto de muestras
		
		//si exiten usuario a quitar y usuario a poner 
		if(idUsuarioAQuitar!=null && idUsuarioAPoner!=null) {
			
			Date fechaAsignacion = new Date();
			
			//recorremos todas las muestras de esa placa, y para cada muestra reemplazamos su analista
			//for(Muestra muestra: placa.getMuestras()) { //ahora obtenemos las muestras a partir de la placa visavet y el lote
			for(Muestra muestra: cjtoMuestrasPlacaLaboratorio) {
				//por el usuario muestra del usuario a quitar
				Optional<UsuarioMuestra> usuMuOpt = usuarioMuestraRepositorio.findByIdUsuarioAndIdMuestra(idUsuarioAQuitar, muestra.getId());
				if(usuMuOpt.isPresent()) {
					//al usuarioMuestra le ponemos el usuarioAPoner, cambiamos fecha de asignacion y guardamos
					UsuarioMuestra usuMu = usuMuOpt.get();
					if(usuMu.getValoracion()==null && usuMu.getFechaValoracion()==null) {
						Usuario usuAPoner = usuarioRepositorio.getOne(idUsuarioAPoner);
						usuMu.setUsuario(usuAPoner);
						usuMu.setFechaAsignacion(fechaAsignacion);
						usuarioMuestraRepositorio.save(usuMu);
						log.info("Se ha reemplazado el usuario: " + idUsuarioAQuitar +  " por el usuario: " +idUsuarioAPoner+  " como analista de la muestra: " + muestra.getId());
					}
					else {
						log.error("El usuario: " + idUsuarioAQuitar + " tiene valoracion en la muestra con id: " + muestra.getId()+", NO podemos reemplazarlo");
					}					
				}
				else {
					log.error("No existe el usuario: " + idUsuarioAQuitar + " como analista de la muestra: " + muestra.getId()+", NO podemos reemplazarlo");
				}
			}
		}
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
		
		//obtencion de las muestras de la placa a través de a traves de sus placas visavet los lotes
		Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placa.getPlacaVisavetPlacaLaboratorios()) {
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
		//fin obtencion cjto de muestras
		
		
		//si la placa no tiene muestras devolvemos false
		//if(placa.getMuestras().size()==0) {
		if(cjtoMuestrasPlacaLaboratorio.size()==0) {
			return false;
		}else {			
			//recorremos las muestras de la placa y si alguna no está resuelta
			// es decir	no tiene el estado resuelta, no tiene resultado o no tiene fecha de resultado) devolvemos false
			//for(Muestra m : placa.getMuestras()) {
			for(Muestra m : cjtoMuestrasPlacaLaboratorio) {
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
			log.info("Placa vale: "+ placa.toString());
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
	public List<BeanResultadoCarga>  guardarResultadosPlacaLaboratorio(ElementoDocumentacionBean bean, Integer numAnalistas)throws Exception {
		//buscamos todas los registros usuarioMuestra de las muestras de la placa
		List<BeanResultadoCarga> carga=new ArrayList<BeanResultadoCarga>();
		Integer idPlaca = bean.getId(); //aqui solo puede llegar el id de una placa del analista logado que tenga sus muestras sin valorar
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);
		
		//obtencion de las muestras de la placa a través de a traves de sus placas visavet los lotes
		Set<Muestra> cjtoMuestrasPlacaLaboratorio = new HashSet<Muestra>(); //ahora no salen directamente de la placa de laboratorio		
		Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();		
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio: placa.getPlacaVisavetPlacaLaboratorios()) {
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
		//fin obtencion cjto de muestras
		
		
		Integer idUsuarioLogado = sesionServicio.getUsuario().getId(); 
		Date fechaActual = new Date();
		Boolean resultadoDefinitivo = false;
		// por cada muestra de la placa
		HashMap<Integer, String> resultados = obtenerResultadosExcel(bean);
		//for (Muestra m : placa.getMuestras()) {
		for (Muestra m : cjtoMuestrasPlacaLaboratorio) {
			String valoracionExcelParaMuestra = resultados.get(m.getId());  //TODO, aquí habría que recoger la valoracion del excel para la muestra, esto es un ejemplo
			//recuperamos el usuarioMuestra asociado al usuario logado (analista que está valorando las muestras de la placa)
			Optional<UsuarioMuestra> optusumu = usuarioMuestraRepositorio.findByIdUsuarioAndIdMuestra(idUsuarioLogado, m.getId());
			if (optusumu.isPresent()) {
				UsuarioMuestra usumu = optusumu.get();
				//aqui tendriamos que asociarle la valoracion que viene en el excel para esa muestra 
				usumu.setValoracion(valoracionExcelParaMuestra);
				usumu.setFechaValoracion(fechaActual);
				UsuarioMuestra usumuGuardado = usuarioMuestraRepositorio.save(usumu);
				carga.add(new BeanResultadoCarga(m.getRefInternaVisavet(),valoracionExcelParaMuestra));
				 
				
			}
			else {
				log.error("No se ha encontrado el registro de usuario muestra para asignarle la valoracion del analista");
			}
			
			//por cada muestra calculamos si ya se han dado las valoraciones de todos los analistas
			//en ese caso el resultado sería definitivo y se guardara el resultado en la muestra y se enviará la notificacion 
			Integer numValoracionesMuestra = this.calculaCuantasValoracionesTieneLaMuestra(m);
			log.info("el numero de valoraciones actuales de la muestra con id: "+ m.getId()+" es: " + numValoracionesMuestra);
			if(numValoracionesMuestra.equals(numAnalistas)) {
				//guardamos como resultado definitivo este ultimo resultado que está dando el analista que viene del excel
				m.setResultado(valoracionExcelParaMuestra);
				m.setFechaResultado(fechaActual);				
				//cambiamos el estado de la muestra a resuelta y la guardamos							
				m.setEstadoMuestra(new EstadoMuestra(Estado.MUESTRA_RESUELTA.getCodNum()));				
				muestraRepositorio.save(m);	
				resultadoDefinitivo= true;
				/*
				//anotamos en el log el cambio de estado de la muestra				
				BeanEstado estadoMuestra = new BeanEstado();
				estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, m.getEstadoMuestra().getId());
				servicioLog.actualizarEstadoMuestra(m.getId(), estadoMuestra);
				*/								
				//Envio de notificacion al pacientes si tiene activada la notificacion automatica
				//recupero el paciente de la muestra
				Paciente paciente = m.getPaciente();
				log.info("el paciente con id: " + paciente.getId()+" tiene la notificacion automato a: " + paciente.getNotificarAutomato());
				if(paciente.getNotificarAutomato().equals("S")) {
					muestraServicio.actualizarNotificacionMuestra(m.getId(), true); //actualiza fecha de notificación y envia correo
				}
				
			}
			
		}
		if(resultadoDefinitivo==true) {
			//anotamos en el log el cambio de estado de las muestras de la placa que se ha resuelto con el ultimo analista				
			BeanEstado estadoMuestra = new BeanEstado();
			estadoMuestra.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.MUESTRA_RESUELTA.getCodNum());
			servicioLog.actualizarEstadoMuestraPorPlacaLaboratorio(idPlaca, estadoMuestra);
		}
		return carga;
	}
	
	
	
	
	
	
	
	
	//fin Diana- metodos para los analistas de placas
	
	
	private HashMap<Integer, String> obtenerResultadosExcel(ElementoDocumentacionBean elementoDocBean) throws Exception {

		int cols = 0;
		Integer colResultado = null;
		String cellValue;
		String cellValueResultado;
		String cellValueReferencia;
		HashMap<Integer, String> resultados = new HashMap<Integer, String>();

		Workbook workbook = WorkbookFactory.create(elementoDocBean.getFile().getInputStream());
		Sheet sheet = workbook.getSheet(elementoDocBean.getHoja());
		Row row;
		int rows = sheet.getLastRowNum();
		for (int r = 0; r <= rows; r++) {
			row = sheet.getRow(r);
			if (row == null) {
				break;
			} else {
				cols = row.getLastCellNum();
				if (r == 0) {
					for (int c = 0; c < cols; c++) {

						cellValue = row.getCell(c) == null ? ""
								: (row.getCell(c).getCellType() == Cell.CELL_TYPE_STRING)
										? row.getCell(c).getStringCellValue()
										: (row.getCell(c).getCellType() == Cell.CELL_TYPE_NUMERIC)
												? "" + row.getCell(c).getNumericCellValue()
												: (row.getCell(c).getCellType() == Cell.CELL_TYPE_BOOLEAN)
														? "" + row.getCell(c).getBooleanCellValue()
														: (row.getCell(c).getCellType() == Cell.CELL_TYPE_BLANK)
																? "BLANK"
																: (row.getCell(c)
																		.getCellType() == Cell.CELL_TYPE_FORMULA)
																				? "FORMULA"
																				: (row.getCell(c)
																						.getCellType() == Cell.CELL_TYPE_ERROR)
																								? "ERROR"
																								: "";
						if (cellValue.compareTo(elementoDocBean.getColumna()) == 0) {
							colResultado = c;
						}
					}
				} else if (r > 0 && colResultado != null) {
					cellValueResultado = row.getCell(colResultado) == null ? ""
							: (row.getCell(colResultado).getCellType() == Cell.CELL_TYPE_STRING)
									? row.getCell(colResultado).getStringCellValue()
									: (row.getCell(colResultado).getCellType() == Cell.CELL_TYPE_NUMERIC)
											? "" + row.getCell(colResultado).getNumericCellValue()
											: (row.getCell(colResultado).getCellType() == Cell.CELL_TYPE_BOOLEAN)
													? "" + row.getCell(colResultado).getBooleanCellValue()
													: (row.getCell(colResultado)
															.getCellType() == Cell.CELL_TYPE_BLANK)
																	? "BLANK"
																	: (row.getCell(colResultado)
																			.getCellType() == Cell.CELL_TYPE_FORMULA)
																					? "FORMULA"
																					: (row.getCell(colResultado)
																							.getCellType() == Cell.CELL_TYPE_ERROR)
																									? "ERROR"
																									: "";

					cellValueReferencia = row.getCell(0) == null ? ""
							: (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING)
									? row.getCell(0).getStringCellValue()
									: (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC)
											? "" + row.getCell(0).getNumericCellValue()
											: (row.getCell(0).getCellType() == Cell.CELL_TYPE_BOOLEAN)
													? "" + row.getCell(0).getBooleanCellValue()
													: (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK)
															? "BLANK"
															: (row.getCell(0)
																	.getCellType() == Cell.CELL_TYPE_FORMULA)
																			? "FORMULA"
																			: (row.getCell(0)
																					.getCellType() == Cell.CELL_TYPE_ERROR)
																							? "ERROR"
																							: "";
					Optional<Muestra> muestra = muestraRepositorio.findByRefInternaVisavet(cellValueReferencia);
					if (muestra.isPresent()) {
						resultados.put(muestra.get().getId(), Utilidades.resultado(cellValueResultado));
					}
				}

			}
		}

		return resultados;
	}

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
	
	public List<BeanEquipo> listaEquiposLaboratorioCentro(LaboratorioCentro laboratorioCentro) throws Exception{
		List<BeanEquipo> listaEquipos = new ArrayList<BeanEquipo>();
		
		for (Equipo equipo: equipoRepositorio.findByLaboratorioCentro(laboratorioCentro))
		{
			listaEquipos.add(equipoServicio.mapeoEntidadBeanEquipo(equipo));
		}
		return listaEquipos;
	}
}
