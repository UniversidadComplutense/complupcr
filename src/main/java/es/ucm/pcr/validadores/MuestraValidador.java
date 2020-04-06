package es.ucm.pcr.validadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.LoteCentroBean;
import es.ucm.pcr.beans.MuestraBusquedaBean;
import es.ucm.pcr.beans.MuestraCentroBean;
import es.ucm.pcr.beans.MuestraListadoBean;
import es.ucm.pcr.servicios.LoteServicio;
import es.ucm.pcr.servicios.MuestraServicio;
import es.ucm.pcr.servicios.SesionServicio;

@Component
public class MuestraValidador implements Validator {
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Autowired
	private MuestraServicio muestraServicio;
	
	@Autowired
	private LoteServicio loteServicio;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MuestraCentroBean.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {
		MuestraCentroBean muestra = (MuestraCentroBean) target;
		
		// valida el numero historial clinico, no se puede repetir por centro
		validateNHC(muestra, errors);
		
		// si se ha rellenado el lote valida que no sobrepase la capacidad del lote
		validateLote(muestra, errors);
		
		// recoger datos notificar
		validateDatosNotificar(muestra, errors);
		
		// avisos automaticos
		validateNotificarAuto(muestra, errors);
		
		// avisos sms
		validateAvisoSMS(muestra, errors);
		
	}
	
	/**
	 * Validacion de numero historial clinico muestra
	 * @param muestra
	 * @param errors
	 */
	private void validateNHC(MuestraCentroBean muestra, Errors errors) {
		MuestraBusquedaBean beanBusqueda = new MuestraBusquedaBean(muestra.getNhc(), sesionServicio.getCentro().getId());
		List<MuestraListadoBean> muestras = muestraServicio.findMuestraByParam(beanBusqueda);
		if (!CollectionUtils.isEmpty(muestras)) {
			if (muestra.getId() == null) {
				errors.rejectValue("nhc", "campo.invalid", "Ya existe una muestra con el mismo número historial clínico");
			} else {
				MuestraListadoBean muestraBean = muestras.stream().filter(muestrasList -> muestra.getId().equals(muestrasList.getId())).findAny().orElse(null);
				if (muestraBean == null) {
					errors.rejectValue("nhc", "campo.invalid", "Ya existe una muestra con el mismo número historial clínico");
				}
			}
		}
	}
	
	/**
	 * Validacion de lote, no puede sobrepasar la capacidad del lote
	 * @param muestra
	 * @param errors
	 */
	private void validateLote(MuestraCentroBean muestra, Errors errors) {
		if (muestra.getIdLote() != null) {
			LoteCentroBean loteBean = loteServicio.findById(muestra.getIdLote());
			
			if (loteBean.getNumeroMuestras().intValue() == loteBean.getCapacidad().intValue()) {
				errors.rejectValue("idLote", "campo.invalid", "El lote ya esta completo");
			} 
		}
	}
	
	/**
	 * Recoger datos para notificar
	 * SI: obligatorio el correo o el telefono
	 * NO: no obligatorio correo y telefono
	 * @param muestra
	 * @param errors
	 */
	private void validateDatosNotificar(MuestraCentroBean muestra, Errors errors) {
		if (muestra.isRecogerDatosNotif()) {
			if (StringUtils.isEmpty(muestra.getCorreo()) && StringUtils.isEmpty(muestra.getTelefono())) {
				errors.rejectValue("recogerDatosNotif", "campo.invalid", "Debe rellenar el email y/o teléfono");
			}
		}
	}
	
	/**
	 * Avisos automaticos
	 * SI: obligatorio el correo
	 * NO: obligatorio correo o telefono
	 * @param muestra
	 * @param errors
	 */
	private void validateNotificarAuto(MuestraCentroBean muestra, Errors errors) {
		if (muestra.isAvisosAuto()) {
			if (StringUtils.isEmpty(muestra.getCorreo())) {
				errors.rejectValue("avisosAuto", "campo.invalid", "Debe rellenar el email");
			}
		} else {
			if (StringUtils.isEmpty(muestra.getCorreo()) && StringUtils.isEmpty(muestra.getTelefono())) {
				errors.rejectValue("avisosAuto", "campo.invalid", "Debe rellenar el email y/o teléfono");
			}
		}
	}
	
	/**
	 * Avisos sms
	 * SI: obligatorio el telefono
	 * NO: 
	 * @param muestra
	 * @param errors
	 */
	private void validateAvisoSMS(MuestraCentroBean muestra, Errors errors) {
		if (muestra.isAvisoSms()) {
			if (StringUtils.isEmpty(muestra.getTelefono())) {
				errors.rejectValue("avisoSms", "campo.invalid", "Debe rellenar el teléfono");
			}
		} 
	}
		
}
