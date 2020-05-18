/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

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
