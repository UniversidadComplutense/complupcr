package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.modelo.orm.EstadoLote;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
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
		    estado.setEstado(Estado.MUESTRA_ASIGNADA_LOTE);
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
				numeroMuestras+=lbbdd.getMuestras().size();
				lbbdd=loteRepositorio.save(lbbdd);
				listaLotes.add(lbbdd);
			}
			Optional<PlacaVisavet>  placaOpt=placaVisavetRepositorio.findById(beanPlacaVisavetUCM.getId());
			if (placaOpt.isPresent()) {
				placa=placaOpt.get();
				placa.setLotes(listaLotes);
				placa.setNumeromuestras(numeroMuestras);
				placa = placaVisavetRepositorio.save(placa);
			}
		}
		return BeanPlacaVisavetUCM.modelToBean(placa);

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
		if (placa.isPresent()) {
			HashMap<String, String> resultados = obtenerReferenciasExcel(bean);
			for (Muestra m : placa.get().getMuestras()) {
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
		for (int r = 0; r < rows; r++) {
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
					cellValueMuestra = row.getCell(colMuestra) == null ? ""
							: (row.getCell(colMuestra).getCellType() == Cell.CELL_TYPE_STRING)
									? row.getCell(colMuestra).getStringCellValue()
									: (row.getCell(colMuestra).getCellType() == Cell.CELL_TYPE_NUMERIC)
											? "" + row.getCell(colMuestra).getNumericCellValue()
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

					cellValueReferencia = row.getCell(colReferencia) == null ? ""
							: (row.getCell(colReferencia).getCellType() == Cell.CELL_TYPE_STRING)
									? row.getCell(colReferencia).getStringCellValue()
									: (row.getCell(colReferencia).getCellType() == Cell.CELL_TYPE_NUMERIC)
											? "" + row.getCell(colReferencia).getNumericCellValue()
											: (row.getCell(colReferencia).getCellType() == Cell.CELL_TYPE_BOOLEAN)
													? "" + row.getCell(colReferencia).getBooleanCellValue()
													: (row.getCell(colReferencia).getCellType() == Cell.CELL_TYPE_BLANK)
															? "BLANK"
															: (row.getCell(colReferencia)
																	.getCellType() == Cell.CELL_TYPE_FORMULA)
																			? "FORMULA"
																			: (row.getCell(colReferencia)
																					.getCellType() == Cell.CELL_TYPE_ERROR)
																							? "ERROR"
																							: "";
					
						resultados.put(cellValueMuestra, cellValueReferencia);
					
				}

			}
		}

		return resultados;
	}
	
	
}
