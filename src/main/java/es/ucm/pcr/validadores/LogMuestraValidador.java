package es.ucm.pcr.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import es.ucm.pcr.beans.LogMuestraBusquedaBean;

@Component
public class LogMuestraValidador implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LogMuestraBusquedaBean.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		/*LogMuestraBusquedaBean logMuestra = (LogMuestraBusquedaBean) target;
		
		String etiquetaMuestra = StringUtils.trim(logMuestra.getEtiquetaMuestra());
		String nhcPaciente = StringUtils.trim(logMuestra.getNhcPaciente());
		
		if (StringUtils.isEmpty(etiquetaMuestra) && StringUtils.isEmpty(nhcPaciente)) {
			errors.rejectValue("etiquetaMuestra", "campo.invalid", "Debe informar la etiqueta y/o el NHC");
			errors.rejectValue("nhcPaciente", "campo.invalid", "Debe informar NHC y/o la etiqueta");
		}*/
	}	
		
}
