package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BeanPlacaVisavetUCM;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BusquedaPlacasVisavetBean;
import es.ucm.pcr.beans.CentroBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.LoteBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.repositorio.CentroRepositorio;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.repositorio.LoteRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
@Service

public class ServicioLaboratorioVisavetUCMImpl implements ServicioLaboratorioVisavetUCM{

	@Autowired
	LoteServicio loteServicio;
	@Autowired
	LoteRepositorio loteRepositorio1;

	@Autowired
	PlacaVisavetRepositorio placaVisavetRepositorio;

	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;

	@Autowired
	SesionServicio sesionServicio;

	@Autowired
	MuestraRepositorio muestraRepositorio;

	@Autowired
	private ServicioLog servicioLog;
	@Autowired
	private LoteRepositorio loteRepositorio;
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable){
		LoteBusquedaBean loteBusquedaBean= new LoteBusquedaBean();
		loteBusquedaBean.setIdCentro(busquedaLotes.getIdCentro());
		// se refiere a fecha recepcion
		loteBusquedaBean.setFechaEnvioFin(busquedaLotes.getFechaFinEntrada());
		loteBusquedaBean.setFechaEnvioIni(busquedaLotes.getFechaInicioEntrada());
		loteBusquedaBean.setNumLote(busquedaLotes.getNumLote());
		// por muestra aun no esta
		loteBusquedaBean.setIdEstado(busquedaLotes.getCodNumEstadoSeleccionado());
		//loteBusquedaBean.setIdLaboratorio("");

		//Pedro: Añado laborarioVisavet del usuario en la sesión a los parámetros de búsqueda
		loteBusquedaBean.setLaboratorioVisavet(sesionServicio.getLaboratorioVisavet());

		Page <LoteListadoBean> pageResultados=loteServicio.findLoteByParam(loteBusquedaBean,pageable);

		List<LoteBeanPlacaVisavet> listaBeans = new ArrayList<LoteBeanPlacaVisavet>();

		for (LoteListadoBean unResultado: pageResultados.getContent()) {
			LoteBeanPlacaVisavet lotePlaca= new LoteBeanPlacaVisavet();
			lotePlaca.setId(unResultado.getId());
			lotePlaca.setNumLote(unResultado.getNumLote());
			lotePlaca.setFechaEntrada(unResultado.getFechaEnvio());
			lotePlaca.setCentroProcedencia(unResultado.getCentroBean().getNombre());
			// necesito el BeanEstado
			lotePlaca.setEstado(unResultado.getBeanEstado());
			//lotePlaca.setEstado(unResultado.);
			List<MuestraBeanLaboratorioVisavet>  listaMuestrasVisavet= new ArrayList<MuestraBeanLaboratorioVisavet>();
			for(MuestraListadoBean muestra : unResultado.getMuestras()) {

				MuestraBeanLaboratorioVisavet muestraBeanLaboratorioVisavet = new MuestraBeanLaboratorioVisavet();
				muestraBeanLaboratorioVisavet.setId(muestra.getId());
				muestraBeanLaboratorioVisavet.setEtiqueta(muestra.getEtiquetaMuestra());
				muestraBeanLaboratorioVisavet.setTipoMuestra(muestra.getTipoMuestra());
				muestraBeanLaboratorioVisavet.setReferenciaInterna(muestra.getRefInternaMuestra());
				//if (listaMuestrasVisavet == null) new ArrayList<MuestraBeanLaboratorioVisavet>();
				listaMuestrasVisavet.add(muestraBeanLaboratorioVisavet);
			}
			lotePlaca.setListaMuestras(listaMuestrasVisavet);
			lotePlaca.setReferenciaInternaLote(unResultado.getReferenciaInternaLote());
			listaBeans.add(lotePlaca);
		}



		Page<LoteBeanPlacaVisavet> pageLote=new PageImpl<LoteBeanPlacaVisavet>(listaBeans, pageable,
				pageResultados.getTotalElements());
		return pageLote;
	}


	public LoteBeanPlacaVisavet buscarLote(Integer id) {
		Lote lote=loteServicio.findByIdLote(id);
		if (lote != null) {
			return LoteBeanPlacaVisavet.modelToBean(lote);
		}
		return null;
	}

	@Transactional
	public Page<BeanPlacaVisavetUCM> buscarPlacas(BusquedaPlacasVisavetBean busqueda, Pageable pageable){

		busqueda.setIdLaboratorioVisavet(sesionServicio.getLaboratorioVisavet().getId());	
		if (busqueda.getNombrePlacaVisavet()!= null && busqueda.getNombrePlacaVisavet().equals("")) busqueda.setNombrePlacaVisavet(null);
		//	if (busqueda.getIdLaboratorioCentro().equals("")) busqueda.setIdLaboratorioCentro(null);
		List<BeanPlacaVisavetUCM> listPlacaBean = new ArrayList<BeanPlacaVisavetUCM>();
		//List<PlacaVisavet> placasList =placaVisavetRepositorio.findByParams(busqueda); 
		Page<PlacaVisavet> placasPage =placaVisavetRepositorio.findByParams(busqueda, pageable); 
		//	Page<PlacaVisavet> placasPage = laboratorioVisavetRepositorio.findByParams(busqueda, pageable); 
		//	Page<PlacaVisavet> placasPage = null;
		for (PlacaVisavet l : placasPage.getContent()) {
			listPlacaBean.add(BeanPlacaVisavetUCM.modelToBean(l));
		}

		Page<BeanPlacaVisavetUCM> paginasPlacas = new PageImpl<>(listPlacaBean, pageable, placasPage.getTotalElements());

		return paginasPlacas;
	}
	@Transactional
	public BeanPlacaVisavetUCM guardar(BeanPlacaVisavetUCM beanPlacaVisavetUCM) {

		PlacaVisavet placa = BeanPlacaVisavetUCM.beanToModel(beanPlacaVisavetUCM);						

		if (beanPlacaVisavetUCM.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_INICIADA.getCodNum()));

		}
		else {
			Optional<PlacaVisavet> placaBBDDOpt=placaVisavetRepositorio.findById(beanPlacaVisavetUCM.getId());
			if (placaBBDDOpt.get()!=null) {
				/*placa.setDocumentos(placaBBDDOpt.getDocumentos());
	            placa.(placaBBDDOpt.getDocumentos());
				 */
				placa=placaBBDDOpt.get();
				placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(beanPlacaVisavetUCM.getEstado().getEstado().getCodNum()));
				if (beanPlacaVisavetUCM.getFechaEnviadaLaboratorio()!= null)placa.setFechaEnviadaLaboratorioCentro(beanPlacaVisavetUCM.getFechaEnviadaLaboratorio());

				// Placa nueva
			}
		}
		placa.setLaboratorioVisavet(new LaboratorioVisavet(sesionServicio.getUsuario().getIdLaboratorioVisavet()));
		placa = placaVisavetRepositorio.save(placa);
		// si el estado de la placa es FINALIZADA grabamos en el log
		if (placa.getEstadoPlacaVisavet().getId() == Estado.PLACAVISAVET_FINALIZADA.getCodNum()) {

			//guardo en el log
			BeanEstado estado=new BeanEstado();
			estado.setEstado(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS);
			estado.setTipoEstado(TipoEstado.EstadoMuestra);
			servicioLog.actualizarEstadoMuestraPorPlacaVisavet(placa.getId(), estado);
		}
		return BeanPlacaVisavetUCM.modelToBean(placa);

	}

	@Transactional
	public BeanPlacaVisavetUCM guardarConLote(BeanPlacaVisavetUCM beanPlacaVisavetUCM) {

		//PlacaVisavet placa = BeanPlacaVisavetUCM.beanToModel(beanPlacaVisavetUCM);						
		PlacaVisavet placa = new PlacaVisavet();
		placa.setLaboratorioVisavet(new LaboratorioVisavet(sesionServicio.getUsuario().getIdLaboratorioVisavet()));

		if (beanPlacaVisavetUCM.getId() == null) {
			placa.setFechaCreacion(new Date());
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_INICIADA.getCodNum()));
			placa.setNumeromuestras(Integer.parseInt(beanPlacaVisavetUCM.getTamano()));
		}
		else {
			Optional<PlacaVisavet> placaOpt=placaVisavetRepositorio.findById(beanPlacaVisavetUCM.getId());
			if (placaOpt.isPresent()) {
				placa=placaOpt.get();
				placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(beanPlacaVisavetUCM.getEstado().getEstado().getCodNum()));

			}

		}

		if (beanPlacaVisavetUCM.getTamano()!=null)placa.setNumeromuestras(Integer.parseInt(beanPlacaVisavetUCM.getTamano()));
		// Placa nueva

		if (beanPlacaVisavetUCM.getNombrePlacaVisavet()!= null && !beanPlacaVisavetUCM.getNombrePlacaVisavet().equals("")) {
			placa.setNombrePlacaVisavet(beanPlacaVisavetUCM.getNombrePlacaVisavet());
		}
		/*	if (placa.getId()!= null) {
			for (Lote l: placa.getLotes()) {
				Lote lbbdd = loteRepositorio1.findById(l.getId()).get();
				lbbdd.setPlacaVisavet(placa);
				lbbdd =loteRepositorio1.save(lbbdd);

			 // aqui tengo que asignarle a placa los lotes con el nuevo id
			}
		} */
		placa = placaVisavetRepositorio.save(placa);
		// por cada placa tengo que guardar el lote
		if (placa.getId()!=null) {
			Set listaLotes= new HashSet();
			Integer numeroMuestras=0;
			for (LoteBeanPlacaVisavet loteB: beanPlacaVisavetUCM.getListaLotes()) {
				Lote lbbdd = loteRepositorio1.findById(loteB.getId()).get();
				//	Lote l=LoteBeanPlacaVisavet.beanToModel(loteB);
				lbbdd.setPlacaVisavet(placa);
				lbbdd.setEstadoLote(new EstadoLote(loteB.getEstado().getEstado().getCodNum()));
				//	numeroMuestras+=lbbdd.getMuestras().size();
				lbbdd=loteRepositorio.save(lbbdd);
				listaLotes.add(lbbdd);
			}
			Optional<PlacaVisavet>  placaOpt=placaVisavetRepositorio.findById(beanPlacaVisavetUCM.getId());
			if (placaOpt.isPresent()) {
				placa=placaOpt.get();
				placa.setLotes(listaLotes);
				//placa.setNumeromuestras(numeroMuestras);
				placa = placaVisavetRepositorio.save(placa);
			}
		}
		return BeanPlacaVisavetUCM.modelToBean(placa);

	}
	@Transactional
	public BeanPlacaVisavetUCM eliminarLotedePlaca(int idLote) {
		int idPlaca = 0;


		Optional<Lote> loteOptional=loteRepositorio.findById(idLote);
		if (loteOptional.isPresent()) {
			idPlaca=loteOptional.get().getPlacaVisavet().getId();
			loteOptional.get().setPlacaVisavet(null);

			loteOptional.get().setFechaRecibido(new Date());
			loteOptional.get().setEstadoLote(new EstadoLote(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS.getCodNum()));

			loteRepositorio.save(loteOptional.get());
		}
		// quitamos el lote
		// busco la placa
		BeanPlacaVisavetUCM placa = this.buscarPlacaById(idPlaca); 
		List<LoteBeanPlacaVisavet> listaLotes= new ArrayList();
		for (LoteBeanPlacaVisavet lote: placa.getListaLotes()) {
			if (lote.getId()!= idLote)
				listaLotes.add(lote);
		}
		placa.setListaLotes(listaLotes);

		return this.guardarConLote(placa);

	}
	@Transactional
	public void eliminarPlaca(int idPlaca) {
		placaVisavetRepositorio.deleteById(idPlaca);
	}

	@Transactional
	public void eliminarPlacayLotes(int idPlaca) {
		Optional<PlacaVisavet> placaOpt=placaVisavetRepositorio.findById(idPlaca);
		if (placaOpt.isPresent()) {
			PlacaVisavet placa=placaOpt.get();
			HashSet<Lote> lotes=new HashSet<Lote> (placa.getLotes());
			for (Lote lote: lotes) {
				this.eliminarLotedePlaca(lote.getId());
			}
			this.eliminarPlaca(idPlaca);
		}


	}


	@Transactional
	public BeanPlacaVisavetUCM buscarPlacaById(Integer id) {
		Optional<PlacaVisavet> placa=placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) 
			return 	BeanPlacaVisavetUCM.modelToBean(placa.get());
		else return null;
	}
	@Transactional
	public BeanPlacaVisavetUCM guardarPlacaConLaboratorio(BeanPlacaVisavetUCM placaVisavet, Integer idLaboratorio) {
		Optional<PlacaVisavet> placaOp=placaVisavetRepositorio.findById(placaVisavet.getId());
		Optional<LaboratorioCentro> laboratorioOp= laboratorioCentroRepositorio.findById(idLaboratorio);
		PlacaVisavet placa=null;
		if (placaOp.isPresent()) {
			placa=placaOp.get();
			placa.setEstadoPlacaVisavet(new EstadoPlacaVisavet(placaVisavet.getEstado().getEstado().getCodNum()));
			if (laboratorioOp.isPresent()) {
				placa.setLaboratorioCentro(laboratorioOp.get());
				placa.setFechaAsignadaLaboratorioCentro(new Date());
			}
			placa=placaVisavetRepositorio.save(placa);
		}
		return	BeanPlacaVisavetUCM.modelToBean(placa);

	}
















	// A VER SI DEJANDO TODOS ESTOS ESPACIOS EVITAMOS MERGES COMPLICADOS :D
	@Override
	public List<BeanLaboratorioVisavet> findAll() {
		List<BeanLaboratorioVisavet> laboratoriosBean = new ArrayList<BeanLaboratorioVisavet>();
		List<LaboratorioVisavet> laboratorios = laboratorioVisavetRepositorio.findAll();

		if (!CollectionUtils.isEmpty(laboratorios)) {
			for (LaboratorioVisavet l : laboratorios) {
				laboratoriosBean.add(new BeanLaboratorioVisavet(l.getId(), l.getNombre(), l.getCapacidad(), l.getOcupacion(), null, null, null));
			}
		}

		return laboratoriosBean;
	}






	@Override
	@Transactional
	public void guardarReferenciasMuestraPlaca(ElementoDocumentacionBean bean) throws Exception {
		Integer idPlaca = bean.getId();
		Optional<PlacaVisavet> placa = placaVisavetRepositorio.findById(idPlaca);
		// la placa no está almacenando las muestras, hay que ir al lote
		if (placa.isPresent()) {			
			HashMap<String, String> resultados = obtenerReferenciasExcel(bean);
			for (Lote l:placa.get().getLotes())
				for (Muestra m : l.getMuestras()) {
					//for (Muestra m : placa.get().getMuestras()) {
					String referenciaMuestra = resultados.get(m.getEtiqueta());
					m.setRefInternaVisavet(referenciaMuestra);
					muestraRepositorio.save(m);

				}
		}

	}


	private HashMap<String, String> obtenerReferenciasExcel(ElementoDocumentacionBean bean) throws Exception {
		int cols = 0;
		Integer colMuestra = null;
		Integer colReferencia = null;
		String cellValue;
		String cellValueMuestra;
		String cellValueReferencia;
		HashMap<String, String> resultados = new HashMap<String, String>();

		Workbook workbook = WorkbookFactory.create(bean.getFile().getInputStream());
		Sheet sheet = workbook.getSheet(bean.getHoja());
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
												if (r == 0 && cellValue.compareTo(bean.getColumna()) == 0) {
													colMuestra = c;
												}
												if (r == 0 && cellValue.compareTo(bean.getColumnaRef()) == 0) {
													colReferencia = c;
												}
					}
				} else if (r > 0 && colMuestra != null && colReferencia != null) {
					cellValueMuestra = extractValue(colMuestra, row);

					cellValueReferencia = extractValue(colReferencia, row);

					resultados.put(cellValueMuestra, cellValueReferencia);

				}

			}
		}

		return resultados;
	}


	@Autowired
	CentroServicio centroServicio;
	@Autowired
	MuestraServicio muestraServicio;
	@Autowired
	CentroRepositorio centroRepositorio;



	@Transactional
	public void procesarExcel(ElementoDocumentacionBean edb, Integer idCentro, Integer tamanoPlaca) throws Exception {
		AnalisisExcelMuestras aem=verificarExcel(edb,tamanoPlaca);		
		Hashtable<String,Integer> loteId=new Hashtable<String,Integer>();
		// crea lotes
		for (String lote:aem.getLoteRemitente().keySet()) {
			String remitente=aem.getLoteRemitente().get(lote);
			Optional<Centro> centro = centroRepositorio.findByNombre(remitente);	

			LoteCentroBean loteBean=new LoteCentroBean();
			if (centro.isPresent()) {
				loteBean.setCapacidad(aem.getLotesMuestras().size());
				loteBean.setReferenciaInternaLote(lote);
				loteBean.setIdCentro(centro.get().getId());				
				loteBean.setNumLote(lote);
				loteBean.setIdLaboratorio(idCentro);
				LoteCentroBean loteCreado = loteServicio.guardar(loteBean);				
				for (String muestraid:aem.getLotesMuestras().get(lote)) {
					MuestraCentroBean muestraBean=new MuestraCentroBean();					
					muestraBean.setEtiqueta(muestraid);					
					muestraBean.setRefInterna(aem.getMuestraYRefVisavet().get(muestraid));
					muestraBean.setCentro(centro.get().getId());
					muestraBean.setNhc(muestraid);			
					muestraBean.setNombre("Fake"+muestraid);	
					muestraBean.setPrimerApellido("Fake"+muestraid);		
					muestraBean.setIdLote(loteCreado.getId());
					switch (aem.getMuestraTipo().get(muestraid).toLowerCase()) {
					case "medio de cultivo":muestraBean.setTipoMuestra("MC");break;
					case "otros":muestraBean.setTipoMuestra("OT");break;
					case "hisopo nasofaríngeo":muestraBean.setTipoMuestra("N");break;
					case "esputo":muestraBean.setTipoMuestra("E");break;
					case "hisopo nasofaríngeo y orofaríngeo":muestraBean.setTipoMuestra("NB");break;
					case "hisopo orofaríngeo":muestraBean.setTipoMuestra("B");break;
					}
					muestraServicio.guardar(muestraBean);					
				}


				loteCreado=loteServicio.findById(loteCreado.getId());

				BeanEstado beanEstado = new BeanEstado();
				beanEstado.asignarTipoEstadoYCodNum(TipoEstado.EstadoMuestra, Estado.LOTE_ENVIADO_CENTRO_ANALISIS.getCodNum());				
				loteServicio.actualizarEstadoLote(loteCreado, beanEstado);	

				LoteCentroBean beanLote = loteServicio.findById(loteCreado.getId());
				beanLote.setFechaRecibido(new Date());
				BeanEstado estado = new BeanEstado();
				estado.setTipoEstado(TipoEstado.EstadoLote);
				estado.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);

				loteServicio.actualizarEstadoLote(beanLote, estado);				
			}

		}	
		Hashtable<String, List<String>> placasYLotes = aem.getPlacaYSusLotes();
		for (String placa:placasYLotes.keySet()) {

			List<LoteBeanPlacaVisavet> listaLotesPlaca=new ArrayList<LoteBeanPlacaVisavet>(); 
			int nmuestras=0;
			for (String lote:placasYLotes.get(placa)) {
				LoteBusquedaBean lbb=new LoteBusquedaBean();
				lbb.setNumLote(lote);
				List<LoteListadoBean> beanLote = loteServicio.findLoteByParam(lbb);
				LoteBeanPlacaVisavet lbp = this.buscarLote(beanLote.get(0).getId());
				listaLotesPlaca.add(lbp);
				nmuestras=nmuestras+lbp.getListaMuestras().size();
			}

			BeanPlacaVisavetUCM placaVisavet = new BeanPlacaVisavetUCM();
			BeanEstado estado = new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estado.setEstado(Estado.PLACA_INICIADA);

			placaVisavet.setEstado(estado);
			placaVisavet = this.guardar(placaVisavet);

			BeanPlacaVisavetUCM nplacaVisavet = new BeanPlacaVisavetUCM();
			nplacaVisavet.setId(placaVisavet.getId());
			nplacaVisavet.setNombrePlacaVisavet(placa);
			nplacaVisavet.setTamano(""+tamanoPlaca);
			nplacaVisavet.setListaLotes(listaLotesPlaca);
			estado = new BeanEstado();
			estado.setTipoEstado(TipoEstado.EstadoPlacaLaboratorioVisavet);
			estado.setEstado(Estado.PLACAVISAVET_INICIADA);
			nplacaVisavet.setEstado(estado);
			placaVisavet.setFechaCreacion(new Date());

			this.guardarConLote(nplacaVisavet);
		}


	}

	public AnalisisExcelMuestras verificarExcel(ElementoDocumentacionBean bean, Integer tamanoPlaca) throws Exception{
		int cols = 0;
		Integer colMuestra = null;
		Integer colReferencia = null;
		Integer colRemitente = null;
		Integer colCliente = null;
		Integer colPlaca = null;
		Integer colLote = null;
		Integer colTipoMuestra = null;
		String cellValue;
		String cellValueMuestra;
		String cellValueReferencia;
		String cellValueRemitente;
		String cellValueCliente;
		String cellValuePlaca;
		String cellValueLote;
		String cellValueTipoMuestra;

		HashMap<String, String> muestraYRefVisavet = new HashMap<String, String>();

		Workbook workbook = WorkbookFactory.create(bean.getFile().getInputStream());
		Sheet sheet = workbook.getSheet(bean.getHoja());
		if (sheet==null) {
			String sheetNames="";
			for (int k=0;k<workbook.getNumberOfSheets();k++)
				sheetNames="'"+workbook.getSheetName(k)+"',";
			throw new Exception("La hoja "+bean.getHoja()+" no existe. Las hojas existentes son "+sheetNames);

		}
		Row row;
		int rows = sheet.getLastRowNum();

		Hashtable<String,List<String>> lotesMuestras=new Hashtable<String,List<String>>();
		Hashtable<String,String> lotesPlaca=new Hashtable<String,String>();
		Hashtable<String,String> loteRemitente=new Hashtable<String,String>();
		Hashtable<String,String> muestraCliente=new Hashtable<String,String>();
		Hashtable<String,String> muestraTipo=new  Hashtable<String,String>();

		List<String> filasIncompletas=new ArrayList<String>();

		AnalisisExcelMuestras resultado=new AnalisisExcelMuestras(filasIncompletas, muestraTipo, lotesMuestras,lotesPlaca,loteRemitente, muestraCliente, muestraYRefVisavet);

		for (int r = 0; r <= rows; r++) {
			row = sheet.getRow(r);
			if (row == null) {
				break;
			} else {
				cols = row.getLastCellNum();
				if (r == 0) {
					for (int c = 0; c < cols; c++) {
						cellValue = extractValue(c, row);
						if (cellValue.compareTo(bean.getColumna()) == 0) {
							colMuestra = c;
						} else
							if ( cellValue.compareTo(bean.getColumnaCliente()) == 0) {
								colCliente = c;
							}else
								if ( cellValue.compareTo(bean.getColumnaLote()) == 0) {
									colLote=c;
								}else
									if ( cellValue.compareTo(bean.getColumnaPlaca()) == 0) {
										colPlaca=c;
									}else
										if ( cellValue.compareTo(bean.getColumnaRef()) == 0) {
											colReferencia = c;
										} else 
											if (cellValue.compareTo(bean.getColumnaRemitente()) == 0) {
												colRemitente = c;
											} else
												if (cellValue.compareTo(bean.getColumnaTipoMuestra()) == 0) {
													colTipoMuestra = c;
												}
					}
				} else 
					if (colMuestra != null && colReferencia != null && 
					colRemitente!=null &&colCliente!=null && 
					colPlaca!=null && colLote!=null & colTipoMuestra!=null) {

						cellValueMuestra = extractValue(colMuestra, row);
						cellValueReferencia = extractValue(colReferencia, row);
						cellValueRemitente= extractValue(colRemitente, row);
						cellValueCliente= extractValue(colCliente, row);
						cellValuePlaca= extractValue(colPlaca, row);
						cellValueLote= extractValue(colLote, row);
						cellValueTipoMuestra= extractValue(colTipoMuestra, row);

						if (cellValueReferencia.equals("") ||
								cellValuePlaca.equals("") ||
								cellValueReferencia.equals("") ||
								cellValueLote.equals("")||
								cellValueRemitente.equals("") ||
								cellValueTipoMuestra.equals("")||
								cellValueMuestra.equals("")) {
							String fila="fila:"+r+" muestra="+cellValueMuestra+
									", placa="+cellValuePlaca+
									", referencia="+cellValueReferencia+
									", lote="+cellValueLote+
									", centro emisor="+cellValueRemitente+
									", tipo="+cellValueTipoMuestra;
							filasIncompletas.add(fila);
						} else {
							Optional<Centro> centro = centroRepositorio.findByNombre(cellValueRemitente);
							if (!centro.isPresent()) {
								String fila="fila:"+r+" muestra="+cellValueMuestra+
										", placa="+cellValuePlaca+
										", referencia="+cellValueReferencia+
										", lote="+cellValueLote+
										", centro emisor="+cellValueRemitente+" [centro inexistente] "+
										", tipo="+cellValueTipoMuestra;
								filasIncompletas.add(fila);
							} else 
								if (!(cellValueTipoMuestra.toLowerCase().equals("medio de cultivo")||
										cellValueTipoMuestra.toLowerCase().equals("otros") ||
										cellValueTipoMuestra.toLowerCase().equals("hisopo nasofaríngeo") ||
										cellValueTipoMuestra.toLowerCase().equals("esputo") ||
										cellValueTipoMuestra.toLowerCase().equals("hisopo nasofaríngeo y orofaríngeo") ||
										cellValueTipoMuestra.toLowerCase().equals("hisopo orofaríngeo"))) {
									String fila="fila:"+r+" muestra="+cellValueMuestra+
											", placa="+cellValuePlaca+
											", referencia="+cellValueReferencia+
											", lote="+cellValueLote+
											", centro emisor="+cellValueRemitente+
											", tipo="+cellValueTipoMuestra+ " [tipo desconocido]";
									filasIncompletas.add(fila);
								} else {


									MuestraBusquedaBean params=new MuestraBusquedaBean();
									params.setRefInternaMuestra(cellValueReferencia);
									List<MuestraListadoBean> res = muestraServicio.findMuestraByParam(params);
									if (!res.isEmpty()) {
										String fila="fila:"+r+" muestra="+cellValueMuestra+
												", placa="+cellValuePlaca+
												", referencia="+cellValueReferencia+ "[Ref duplicada]"+
												", lote="+cellValueLote+
												", centro emisor="+cellValueRemitente+
												", tipo="+cellValueTipoMuestra;
										filasIncompletas.add(fila);
									} else {
										params=new MuestraBusquedaBean();
										params.setEtiquetaMuestra(cellValueMuestra);
										params.setIdCentro(centro.get().getId());
										res = muestraServicio.findMuestraByParam(params);
										if (!res.isEmpty()) {
											String fila="fila:"+r+" muestra="+cellValueMuestra+ "[ya existe esa etiqueta en este centro]"+
													", placa="+cellValuePlaca+
													", referencia="+cellValueReferencia+ 
													", lote="+cellValueLote+
													", centro emisor="+cellValueRemitente+
													", tipo="+cellValueTipoMuestra;
											filasIncompletas.add(fila);
										} else {
											if (resultado.muestrasPorPlaca(cellValuePlaca).size()>tamanoPlaca){
												String fila="fila:"+r+" muestra="+cellValueMuestra+ "[ya existe esa etiqueta en este centro]"+
														", placa="+cellValuePlaca+ "[tamano maximo de placa "+tamanoPlaca+" excedido]"+
														", referencia="+cellValueReferencia+ 
														", lote="+cellValueLote+
														", centro emisor="+cellValueRemitente+
														", tipo="+cellValueTipoMuestra;
												filasIncompletas.add(fila);
											} else {
												muestraYRefVisavet.put(cellValueMuestra, cellValueReferencia);
												if (!lotesMuestras.containsKey(cellValueLote))
													lotesMuestras.put(cellValueLote, new ArrayList<String>());
												lotesMuestras.get(cellValueLote).add(cellValueMuestra);											
												lotesPlaca.put(cellValueLote, cellValuePlaca);
												loteRemitente.put(cellValueLote,cellValueRemitente);
												muestraCliente.put(cellValueMuestra,cellValueCliente);
												muestraTipo.put(cellValueMuestra, cellValueTipoMuestra);
											}

										}
									}
								}
						}
					}
			}
		}
		return resultado;
	}

	class AnalisisExcelMuestras {
		Hashtable<String,List<String>> lotesMuestras=new Hashtable<String,List<String>>();
		Hashtable<String,String> muestraTipo=new  Hashtable<String,String>();

		public Hashtable<String, String> getMuestraTipo() {
			return muestraTipo;
		}

		public void setMuestraTipo(Hashtable<String, String> muestraTipo) {
			this.muestraTipo = muestraTipo;
		}

		Hashtable<String,String> lotesPlaca=new Hashtable<String,String>();
		Hashtable<String,String> loteRemitente=new Hashtable<String,String>();
		Hashtable<String,String> muestraCliente=new Hashtable<String,String>();
		HashMap<String, String> muestraYRefVisavet = new HashMap<String, String>();
		List<String> filasIncompletas=new ArrayList<String>();

		public AnalisisExcelMuestras(List<String> filasIncompletas,Hashtable<String,String> muestraTipo, Hashtable<String, List<String>> lotesMuestras,
				Hashtable<String, String> lotesPlaca, Hashtable<String, String> loteRemitente,
				Hashtable<String, String> muestraCliente, HashMap<String, String> muestraYRefVisavet) {
			super();
			this.muestraTipo=muestraTipo;
			this.filasIncompletas=filasIncompletas;
			this.lotesMuestras = lotesMuestras;
			this.lotesPlaca = lotesPlaca;
			this.loteRemitente = loteRemitente;
			this.muestraCliente = muestraCliente;
			this.muestraYRefVisavet = muestraYRefVisavet;
		}
		public List<String> getFilasIncompletas() {
			return filasIncompletas;
		}
		public void setFilasIncompletas(List<String> filasIncompletas) {
			this.filasIncompletas = filasIncompletas;
		}
		public Hashtable<String, List<String>> getLotesMuestras() {
			return lotesMuestras;
		}
		public void setLotesMuestras(Hashtable<String, List<String>> lotesMuestras) {
			this.lotesMuestras = lotesMuestras;
		}
		public Hashtable<String, String> getLotesPlaca() {
			return lotesPlaca;
		}
		public void setLotesPlaca(Hashtable<String, String> lotesPlaca) {
			this.lotesPlaca = lotesPlaca;
		}
		public Hashtable<String, String> getLoteRemitente() {
			return loteRemitente;
		}
		public void setLoteRemitente(Hashtable<String, String> loteRemitente) {
			this.loteRemitente = loteRemitente;
		}
		public Hashtable<String, String> getMuestraCliente() {
			return muestraCliente;
		}
		public void setMuestraCliente(Hashtable<String, String> muestraCliente) {
			this.muestraCliente = muestraCliente;
		}
		public HashMap<String, String> getMuestraYRefVisavet() {
			return muestraYRefVisavet;
		}
		public void setMuestraYRefVisavet(HashMap<String, String> muestraYRefVisavet) {
			this.muestraYRefVisavet = muestraYRefVisavet;
		}

		public Hashtable<String, List<String>> getPlacaYSusLotes(){
			Hashtable<String, List<String>> resultado= new Hashtable<String, List<String>>();
			HashSet<String> placas = new HashSet(this.getLotesPlaca().values());
			for (String placa:placas) {
				List<String> listaLotes=new ArrayList<String>();
				for(String lote:getLotesPlaca().keySet()) {
					if (getLotesPlaca().get(lote).equals(placa)) {
						listaLotes.add(lote);
					}
				}
				resultado.put(placa, listaLotes);
			}

			return resultado;
		}

		public List<String> muestrasPorPlaca(String cellValuePlaca) {
			List<String> muestras=new ArrayList<String>();
			Hashtable<String, List<String>> placas = getPlacaYSusLotes();
			if (placas.containsKey(cellValuePlaca))
				for (String lote:placas.get(cellValuePlaca)) {
					muestras.addAll(this.getLotesMuestras().get(lote));
				}
			return muestras;
		}

	}

	private String extractValue(Integer colMuestra, Row row) {
		return row.getCell(colMuestra) == null ? ""
				: (row.getCell(colMuestra).getCellType() == Cell.CELL_TYPE_STRING)
				? row.getCell(colMuestra).getStringCellValue()
						: (row.getCell(colMuestra).getCellType() == Cell.CELL_TYPE_NUMERIC)
						? "" + (String.format ("%.0f", row.getCell(colMuestra).getNumericCellValue())) // para que no devuelva números
								: (row.getCell(colMuestra).getCellType() == Cell.CELL_TYPE_BOOLEAN)
								? "" + row.getCell(colMuestra).getBooleanCellValue()
										: (row.getCell(colMuestra)
												.getCellType() == Cell.CELL_TYPE_BLANK)
										? "BLANK"
												: (row.getCell(colMuestra)
														.getCellType() == Cell.CELL_TYPE_FORMULA)
												? "FORMULA"
														: (row.getCell(colMuestra)
																.getCellType() == Cell.CELL_TYPE_ERROR)
														? "ERROR"
																: "";
	}


}
