package es.ucm.pcr.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ucm.pcr.modelo.orm.Centro;

/**
 * Componente para utilizar en thymeleaf con la notación @pcrTHExpressions.metodo(...) dentro de una expresión Thymeleaf ${...}
 * 
 * @author desarrollo
 *
 */
@Component
public class PcrTHExpressions {
	
	@Autowired
	SesionServicio sesionServicio;
	
	/**
	 * Método que devuelve el eMail del usuario para ponerlo en el pie de página.
	 * 
	 * @return String
	 */
	public String getEmailUsuario() {
		return sesionServicio.getEmail();
	}
	
	/**
	 * Método que devuelve el centro del usuario para ponerlo en el pie de página.
	 */
	public String getCentro() {
		Centro centro = sesionServicio.getCentro();
		if (centro != null) {
			return centro.getNombre();
		} else {
			return null;
		}
		//TODO: Revisar esto. Habrá roles en los que hay que sacer el rol y roles donde hay que sacer el centro.
	}

}
