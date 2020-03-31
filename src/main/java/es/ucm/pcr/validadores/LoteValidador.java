package es.ucm.pcr.validadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.SesionServicio;

@Component
public class LoteValidador implements Validator {
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LoteCentroBean.class.isAssignableFrom(clazz);			
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		LoteCentroBean lote = (LoteCentroBean) target;		
		
		// validar codigo de lote
		validateCodigoLote(lote, errors);
	}
	
	/**
	 * No puede haber varios lotes con el mismo codigo
	 * @param lote
	 * @param errors
	 */
	private void validateCodigoLote(LoteCentroBean lote, Errors errors) {
		List<LoteListadoBean> lotes = loteServicio.findLoteByParam(new LoteBusquedaBean(lote.getNumLote(), sesionServicio.getCentro().getId()));
		if (!CollectionUtils.isEmpty(lotes)) {
			if (lote.getId() == null) {
				errors.rejectValue("numLote", "campo.invalid", "Ya existe un lote con el mismo código");
			} else {
				LoteListadoBean loteBean = lotes.stream().filter(loteList -> lote.getId().equals(loteList.getId())).findAny().orElse(null);
				if (loteBean == null) {
					errors.rejectValue("numLote", "campo.invalid", "Ya existe un lote con el mismo código");
				}
			}
		}
	}
}