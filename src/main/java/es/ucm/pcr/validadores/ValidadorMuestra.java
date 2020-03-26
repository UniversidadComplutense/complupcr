package es.ucm.pcr.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.BeanMuestraCentro;

@Component
public class ValidadorMuestra implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return BeanMuestraCentro.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		BeanMuestraCentro muestra = (BeanMuestraCentro) target;
		
		// TODO - comprobar que no existe una muestra con la misma etiqueta
		// errors.rejectValue("etiqueta", "campo.invalid", "Ya existe una muestra con la misma etiqueta");
		
		// TODO - validacion fecha entrada?
		
	}
}
