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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import es.ucm.pcr.modelo.orm.Lote;
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

		// Si no se ha seleccionado el estado de la placa en la búsqueda, buscamos por los estados: PLACA_INICIADA ó PLACA_PREPARADA_PARA_PCR
		// ó PLACA_FINALIZADA_PCR ó PLACA_LISTA_PARA_ANALISIS
		if (criteriosBusqueda.getIdEstadoPlaca() == 0) {
			criteriosBusqueda.setEstadosBusqueda(Arrays.asList(1,2,3,4));
		} else {
			criteriosBusqueda.setEstadosBusqueda(Arrays.asList(criteriosBusqueda.getIdEstadoPlaca()));
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
	public PlacaLaboratorioCentroBean buscarPlaca(Integer id) {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioCentroBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioCentroBean();
	}

	// JAVI
	@Override
	@Transactional
	public boolean finalizarPCR(Integer id) {
		
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
	public boolean asignarEquipoPCR(Integer id) {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_INICIADA.getCodNum()) {
				
				//TODO asignar a un equipo del laboratorio si así nos lo piden finalmente
				
				placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_PREPARADA_PARA_PCR.getCodNum()));
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
	public boolean placaListaParaAnalizar(Integer id) {
		
		Optional<PlacaLaboratorio> placa = placaLaboratorioRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaLaboratorio().getId() == Estado.PLACA_FINALIZADA_PCR.getCodNum()) {
				ElementoDocumentacionBean documentosPlaca = documentoServicio.obtenerDocumentosPlacaLaboratorio(id);
				if (documentosPlaca != null && documentosPlaca.getDocumentos() != null && documentosPlaca.getDocumentos().size() >0) {
					placa.get().setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_LISTA_PARA_ANALISIS.getCodNum()));
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
	public boolean esEditable(Integer id) {		

		return id == null;		
	}
	
	// JAVI
	@Override
	public Integer espacioLibreParaMuestras(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) {		
		 
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
	// Si la placa es de nueva creación, pasamos por parámetro la capacidad que tendrá la misma, y en caso contrario pasamos un cero.
	@Override
	@Transactional
	public PlacaLaboratorioCentroBean rellenarPlaca(PlacaLaboratorioCentroBean placaLaboratorioCentroBean, Integer capacidadNuevaPlaca) {		
		
		PlacaLaboratorio placa = PlacaLaboratorioCentroBean.beanToModel(placaLaboratorioCentroBean);

		placa.setLaboratorioCentro(new LaboratorioCentro(sesionServicio.getUsuario().getIdLaboratorioCentro()));
		
		// Es una placa nueva
		if (capacidadNuevaPlaca != 0) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaLaboratorio(new EstadoPlacaLaboratorio(Estado.PLACA_INICIADA.getCodNum()));			
			placa = placaLaboratorioRepositorio.save(placa);
		}
		
		
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
		
		// Asignamos las placas Visavet si hay espacio en la placa de laboratorio
		if (espacioLibreParaMuestras >= sumaMuestrasPlacasVisavetSeleccionadas) {
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
	public void guardarResultadosPlacaLaboratorio(ElementoDocumentacionBean bean, Integer numAnalistas)throws Exception {
		//buscamos todas los registros usuarioMuestra de las muestras de la placa
		Integer idPlaca = bean.getId(); //aqui solo puede llegar el id de una placa del analista logado que tenga sus muestras sin valorar
		PlacaLaboratorio placa = placaLaboratorioRepositorio.getOne(idPlaca);
		Integer idUsuarioLogado = sesionServicio.getUsuario().getId(); 
		Date fechaActual = new Date();
		// por cada muestra de la placa
				HashMap<Integer, String> resultados = obtenerResultadosExcel(bean);
				for (Muestra m : placa.getMuestras()) {
					String valoracionExcelParaMuestra = resultados.get(m.getId());  //TODO, aquí habría que recoger la valoracion del excel para la muestra, esto es un ejemplo
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
	
	
	private HashMap<Integer, String> obtenerResultadosExcel(ElementoDocumentacionBean elementoDocBean) throws Exception {

		int cols = 0;
		Integer colResultado = null;
		String cellValue;
		String cellValueResultado;
		String cellValueReferencia;
		HashMap<Integer, String> resultados = new HashMap<Integer, String>();

		XSSFWorkbook workbook = new XSSFWorkbook(elementoDocBean.getFile().getInputStream());
		XSSFSheet xssfSheet = workbook.getSheet(elementoDocBean.getHoja());
		XSSFRow xssfRow;
		int rows = xssfSheet.getLastRowNum();
		for (int r = 0; r < rows; r++) {
			xssfRow = xssfSheet.getRow(r);
			if (xssfRow == null) {
				break;
			} else {
				cols = xssfRow.getLastCellNum();
				if (r == 0) {
					for (int c = 0; c < cols; c++) {

						cellValue = xssfRow.getCell(c) == null ? ""
								: (xssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_STRING)
										? xssfRow.getCell(c).getStringCellValue()
										: (xssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_NUMERIC)
												? "" + xssfRow.getCell(c).getNumericCellValue()
												: (xssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BOOLEAN)
														? "" + xssfRow.getCell(c).getBooleanCellValue()
														: (xssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BLANK)
																? "BLANK"
																: (xssfRow.getCell(c)
																		.getCellType() == Cell.CELL_TYPE_FORMULA)
																				? "FORMULA"
																				: (xssfRow.getCell(c)
																						.getCellType() == Cell.CELL_TYPE_ERROR)
																								? "ERROR"
																								: "";
						if (cellValue.compareTo(elementoDocBean.getColumna()) == 0) {
							colResultado = c;
						}
					}
				} else if (r > 0 && colResultado != null) {
					cellValueResultado = xssfRow.getCell(colResultado) == null ? ""
							: (xssfRow.getCell(colResultado).getCellType() == Cell.CELL_TYPE_STRING)
									? xssfRow.getCell(colResultado).getStringCellValue()
									: (xssfRow.getCell(colResultado).getCellType() == Cell.CELL_TYPE_NUMERIC)
											? "" + xssfRow.getCell(colResultado).getNumericCellValue()
											: (xssfRow.getCell(colResultado).getCellType() == Cell.CELL_TYPE_BOOLEAN)
													? "" + xssfRow.getCell(colResultado).getBooleanCellValue()
													: (xssfRow.getCell(colResultado)
															.getCellType() == Cell.CELL_TYPE_BLANK)
																	? "BLANK"
																	: (xssfRow.getCell(colResultado)
																			.getCellType() == Cell.CELL_TYPE_FORMULA)
																					? "FORMULA"
																					: (xssfRow.getCell(colResultado)
																							.getCellType() == Cell.CELL_TYPE_ERROR)
																									? "ERROR"
																									: "";

					cellValueReferencia = xssfRow.getCell(0) == null ? ""
							: (xssfRow.getCell(0).getCellType() == Cell.CELL_TYPE_STRING)
									? xssfRow.getCell(0).getStringCellValue()
									: (xssfRow.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC)
											? "" + xssfRow.getCell(0).getNumericCellValue()
											: (xssfRow.getCell(0).getCellType() == Cell.CELL_TYPE_BOOLEAN)
													? "" + xssfRow.getCell(0).getBooleanCellValue()
													: (xssfRow.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK)
															? "BLANK"
															: (xssfRow.getCell(0)
																	.getCellType() == Cell.CELL_TYPE_FORMULA)
																			? "FORMULA"
																			: (xssfRow.getCell(0)
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
	
	
}
