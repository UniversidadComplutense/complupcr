package es.ucm.pcr.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.LoteCentroBean;

@Component
public class LoteValidador implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LoteCentroBean.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		LoteCentroBean lote = (LoteCentroBean) target;		
		
	}
}
