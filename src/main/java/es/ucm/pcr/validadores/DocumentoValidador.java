package es.ucm.pcr.validadores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.modelo.orm.Lote;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCM;
import es.ucm.pcr.servicios.ServicioLaboratorioVisavetUCMImpl.AnalisisExcelMuestras;
import es.ucm.pcr.servicios.SesionServicio;
import es.ucm.pcr.utilidades.Utilidades;

@Component
public class DocumentoValidador implements Validator {

	@Autowired
	PlacaLaboratorioRepositorio placaLaboratorioRepositorio;

	@Autowired
	PlacaVisavetRepositorio placaVisavetRepositorio;

	@Autowired
	ServicioLaboratorioVisavetUCM servicioVisavet;

	@Autowired
	SesionServicio sesionServicio;

	@Override
	public boolean supports(Class<?> clazz) {
		return ElementoDocumentacionBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ElementoDocumentacionBean elementoDocBean = (ElementoDocumentacionBean) target;

		// valida tamanio fichero
		validateTamanioFichero(elementoDocBean, errors);
		if (elementoDocBean.getTipo().compareTo("RES") == 0) {
			validarExcelResutadosAnalista(elementoDocBean, errors);
		}
		if (elementoDocBean.getTipo().compareTo("REF") == 0) {
			validarExcelReferenciasLaboratorio(elementoDocBean, errors);
		}
		if (elementoDocBean.getTipo().compareTo("SDP") == 0) {
			validarExcelSinDatosPrevios(elementoDocBean, errors);
		}
	}

	/**
	 * Valida que el fichero no exceda de tamanio
	 * 
	 * @param elementoDocBean
	 * @param errors
	 */
	private void validateTamanioFichero(ElementoDocumentacionBean elementoDocBean, Errors errors) {
		if (Utilidades.excedeTamanioFichero(elementoDocBean.getFile().getSize())) {
			errors.rejectValue("file", "campo.invalid", "El tamaño del documento no puede exceder los 5MB");
		}
	}

	/**
	 * Valida que la excel de resultados es valida
	 * 
	 * @param elementoDocBean
	 * @param errors
	 */
	private void validarExcelResutadosAnalista(ElementoDocumentacionBean elementoDocBean, Errors errors) {

		try {
			Workbook workbook = WorkbookFactory.create(elementoDocBean.getFile().getInputStream());
			Sheet sheet = workbook.getSheet(elementoDocBean.getHoja());
			if (sheet == null) {
				errors.rejectValue("hoja", "campo.invalid", "No existe la hoja indicada en el libro subido");
			} else {
				Row row;
				int rows = sheet.getLastRowNum();
				int cols = 0;
				Integer colResultado = null;
				String cellValue;
				String cellValueResultado;
				String cellValueReferencia;
				List<String> listaMuestrasExcel = new ArrayList<String>();
				List<String> listaMuestrasLaboratorio = new ArrayList<String>();
				List<String> listaResultados = new ArrayList<String>(
						Arrays.asList("POSITIVO", "NEGATIVO", "CI NEG O BAJO", "DUDOSO"));
				List<String> listaMuestrasExcelResultado = new ArrayList<String>();
				// Validar muestras placa
				Optional<PlacaLaboratorio> placaLaboratorio = placaLaboratorioRepositorio
						.findById(elementoDocBean.getId());
				if (placaLaboratorio.isPresent()) {
					// Set<Muestra> muestras = placaLaboratorio.get().getMuestras(); //modificado
					// Diana- Las muestras se obtienen a traves de la placa visavet y el lote

					// obtencion de las muestras de la placa a través de sus placas visavet y los
					// lotes
					Set<Muestra> muestras = new HashSet<Muestra>();
					Set<PlacaVisavet> placasVisavetDeLaPlacaLaboratorio = new HashSet<PlacaVisavet>();
					for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio : placaLaboratorio.get()
							.getPlacaVisavetPlacaLaboratorios()) {
						placasVisavetDeLaPlacaLaboratorio.add(placaVisavetPlacaLaboratorio.getPlacaVisavet());
					}
					for (PlacaVisavet placaVisavet : placasVisavetDeLaPlacaLaboratorio) {
						// Recuperamos las muestras de la placa Visavet desde el lote
						Set<Muestra> muestrasVisavet = new HashSet<Muestra>();
						Set<Lote> lotes = placaVisavet.getLotes();
						for (Lote lote : lotes) {
							muestrasVisavet.addAll(lote.getMuestras());
							muestras.addAll(muestrasVisavet);
						}
					}
					// fin obtencion cjto de muestras

					for (Muestra muestra : muestras) {
						listaMuestrasLaboratorio.add(muestra.getRefInternaVisavet());
					}
				}
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
													: (row.getCell(colResultado)
															.getCellType() == Cell.CELL_TYPE_BOOLEAN) ? ""
																	+ row.getCell(colResultado).getBooleanCellValue()
																	: (row.getCell(colResultado)
																			.getCellType() == Cell.CELL_TYPE_BLANK)
																					? "BLANK"
																					: (row.getCell(colResultado)
																							.getCellType() == Cell.CELL_TYPE_FORMULA)
																									? "FORMULA"
																									: (row.getCell(
																											colResultado)
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

							listaMuestrasExcel.add(cellValueReferencia);
							if (listaMuestrasLaboratorio.contains(cellValueReferencia)
									&& !listaResultados.contains(cellValueResultado.toUpperCase())) {
								listaMuestrasExcelResultado.add(cellValueReferencia);
							}

						}
						// Validar nombre coluna
						if (r == 1 && colResultado == null) {
							errors.rejectValue("columna", "campo.invalid", "No existe la columna indicada");
						}

					}
				}

				if (!listaMuestrasExcel.isEmpty() && !listaMuestrasExcel.containsAll(listaMuestrasLaboratorio)) {
					listaMuestrasLaboratorio.removeAll(listaMuestrasExcel);
					errors.rejectValue("file", "campo.invalid",
							"Las muestras de la excel no coinciden con las de la placa, revise los datos subidos. Muestras de la placa que no estan en el fichero: "
									+ listaMuestrasLaboratorio.toString());
				}
				if (!listaMuestrasExcelResultado.isEmpty()) {
					errors.rejectValue("file", "campo.invalid",
							"El resultado para las muestras: " + listaMuestrasExcelResultado.toString()
									+ " No es valido, debe ser uno de estos: " + listaResultados.toString());
				}

			}
		} catch (Exception e) {
			errors.rejectValue("file", "campo.invalid", "El fichero de resultado no es un fichero valido");

		}

	}

	/**
	 * Valida que la excel de resultados es valida
	 * 
	 * @param elementoDocBean
	 * @param errors
	 */
	private void validarExcelReferenciasLaboratorio(ElementoDocumentacionBean elementoDocBean, Errors errors) {

		try {
			Workbook workbook = WorkbookFactory.create(elementoDocBean.getFile().getInputStream());
			Sheet sheet = workbook.getSheet(elementoDocBean.getHoja());
			if (sheet == null) {
				errors.rejectValue("hoja", "campo.invalid", "No existe la hoja indicada en el libro subido");
			} else {
				Row row;
				int rows = sheet.getLastRowNum();
				int cols = 0;
				Integer colMuestra = null;
				Integer colReferencia = null;
				String cellValue;
				List<String> listaMuestrasExcel = new ArrayList<String>();
				List<String> listaMuestrasVisavet = new ArrayList<String>();
				for (int r = 0; r <= rows; r++) {
					row = sheet.getRow(r);
					if (row == null) {
						break;
					} else {
						cols = row.getLastCellNum();
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
							if (r == 0 && cellValue.compareTo(elementoDocBean.getColumna()) == 0) {
								colMuestra = c;
							}
							if (r == 0 && cellValue.compareTo(elementoDocBean.getColumnaRef()) == 0) {
								colReferencia = c;
							}
							if (colMuestra != null && r > 0 && c == colMuestra) {
								listaMuestrasExcel.add(cellValue);
							}
						}
						// Validar nombre coluna
						if (r == 1 && colMuestra == null) {
							errors.rejectValue("columna", "campo.invalid", "No existe la columna indicada");
						}
						if (r == 1 && colReferencia == null) {
							errors.rejectValue("columnaRef", "campo.invalid", "No existe la columna indicada");
						}

					}
				}

				// Validar muestras placa
				Optional<PlacaVisavet> placaVisavet = placaVisavetRepositorio.findById(elementoDocBean.getId());

				if (placaVisavet.isPresent()) {
					// Set<Muestra> muestras = placaVisavet.get().getMuestras(); //modificado Diana-
					// Las muestras se obtienen a traves de los lotes de la placa visavet

					// obtencion de las muestras de la placa visavet a través de lotes
					Set<Muestra> muestras = new HashSet<Muestra>();

					// Recuperamos las muestras de la placa Visavet desde el lote
					Set<Muestra> muestrasVisavet = new HashSet<Muestra>();
					Set<Lote> lotes = placaVisavet.get().getLotes();
					for (Lote lote : lotes) {
						muestrasVisavet.addAll(lote.getMuestras());
						muestras.addAll(muestrasVisavet);
					}
					// fin obtencion cjto de muestras

					for (Muestra muestra : muestras) {
						listaMuestrasVisavet.add(muestra.getEtiqueta());
					}
				}
				if (!listaMuestrasExcel.isEmpty() && !listaMuestrasExcel.containsAll(listaMuestrasVisavet)) {
					listaMuestrasVisavet.removeAll(listaMuestrasExcel);
					errors.rejectValue("file", "campo.invalid",
							"Las muestras de la excel no coinciden con las de la placa, revise los datos subidos. Muestras de la placa que no estan en el fichero: "
									+ listaMuestrasVisavet.toString());
				}
			}
		} catch (Exception e) {
			errors.rejectValue("file", "campo.invalid", "El fichero de resultado no es un fichero valido");

		}

	}

	private void validarExcelSinDatosPrevios(ElementoDocumentacionBean elementoDocBean, Errors errors) {
		AnalisisExcelMuestras analisisExcelMuestras = null;
		try {
			analisisExcelMuestras = servicioVisavet.verificarExcel(elementoDocBean, elementoDocBean.getTamanio());
		} catch (Exception e) {
			errors.rejectValue("file", "campo.invalid", e.getMessage());
		}
		if (analisisExcelMuestras != null && !analisisExcelMuestras.getFilasIncompletas().isEmpty()) {
			errors.rejectValue("file", "campo.invalid", "El fichero contiene filas erroneas no es un fichero valido: ");
		}
	}
}
