package es.ucm.pcr.validadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.utilidades.Utilidades;

@Component
public class DocumentoValidador implements Validator {

	@Autowired
	PlacaLaboratorioRepositorio placaLaboratorioRepositorio;

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
			XSSFWorkbook workbook = new XSSFWorkbook(elementoDocBean.getFile().getInputStream());
			XSSFSheet xssfSheet = workbook.getSheet(elementoDocBean.getHoja());
			if (xssfSheet == null) {
				errors.rejectValue("hoja", "campo.invalid", "No existe la hoja indicada en el libro subido");
			} else {
				XSSFRow xssfRow;
				int rows = xssfSheet.getLastRowNum();
				int cols = 0;
				Integer colResultado = null;
				String cellValue;
				List<String> listaMuestrasExcel = new ArrayList<String>();
				List<String> listaMuestrasLaboratorio = new ArrayList<String>();
				for (int r = 0; r < rows; r++) {
					xssfRow = xssfSheet.getRow(r);
					if (xssfRow == null) {
						break;
					} else {
						cols = xssfRow.getLastCellNum();
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
							if (r == 0 && cellValue.compareTo(elementoDocBean.getColumna()) == 0) {
								colResultado = c;
							}
							if (r > 0 && c == 0) {
								listaMuestrasExcel.add(cellValue);
							}
						}
						// Validar nombre coluna
						if (r == 1 && colResultado == null) {
							errors.rejectValue("columna", "campo.invalid", "No existe la columna indicada");
						}

					}
				}

				// Validar muestras placa
				Optional<PlacaLaboratorio> placaLaboratorio= placaLaboratorioRepositorio
						.findById(elementoDocBean.getId());

			
				Set<Muestra> muestras =  placaLaboratorio.get().getMuestras();
				for (Muestra muestra : muestras) {
					listaMuestrasLaboratorio.add(muestra.getRefInternaVisavet());
				}

				if (!listaMuestrasExcel.containsAll(listaMuestrasLaboratorio)) {
					errors.rejectValue("file", "campo.invalid",
							"Las muestras de la excel no coinciden con las de la placa, revise los datos subidos");
				}

			}
		} catch (IOException e) {
			errors.rejectValue("file", "campo.invalid", "El fichero de resultado no es un fichero valido");

		}

	}
}
