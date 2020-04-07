package es.ucm.pcr.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.utilidades.Utilidades;

@Component
public class DocumentoValidador implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ElementoDocumentacionBean.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		ElementoDocumentacionBean elementoDocBean = (ElementoDocumentacionBean)target;
		
		
		// valida tamanio fichero
		validateTamanioFichero(elementoDocBean, errors);
	}		
		
	/**
	 * Valida que el fichero no exceda de tamanio
	 * @param elementoDocBean
	 * @param errors
	 */
	private void validateTamanioFichero(ElementoDocumentacionBean elementoDocBean, Errors errors) {
		if (Utilidades.excedeTamanioFichero(elementoDocBean.getFile().getSize())) {
			errors.rejectValue("file", "campo.invalid", "El tamaño del documento no puede exceder los 5MB");
		}
	}
}
