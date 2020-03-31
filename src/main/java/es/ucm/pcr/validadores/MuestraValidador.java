package es.ucm.pcr.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.MuestraCentroBean;

@Component
public class MuestraValidador implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MuestraCentroBean.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		MuestraCentroBean muestra = (MuestraCentroBean) target;
		
		// TODO - comprobar que no existe una muestra con la misma etiqueta
		// errors.rejectValue("etiqueta", "campo.invalid", "Ya existe una muestra con la misma etiqueta");
		
		// TODO - validacion fecha entrada?
		
	}
}
