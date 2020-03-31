package es.ucm.pcr.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.LaboratorioCentroBean;

@Component
public class LaboratorioCentroValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return LaboratorioCentroBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LaboratorioCentroBean LabCentroBean = (LaboratorioCentroBean) target;

		// TODO - VALIDADOR

	}
}
