package es.ucm.pcr.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ucm.pcr.servicio.UsuarioServicio;

/**
 * Componente para utilizar en thymeleaf con la notación @pcrTHExpressions.metodo(...) dentro de una expresión Thymeleaf ${...}
 * 
 * @author desarrollo
 *
 */
@Component
public class PcrTHExpressions {
	
	@Autowired
	UsuarioServicio usuServ;
	
	/**
	 * Método que devuelve el eMail del usuario para ponerlo en el pie de página.
	 * 
	 * @return String
	 */
	public String getEmailUsuario() {
		return usuServ.getUsuarioActual().getEmail();
	}
	
	/**
	 * Método que devuelve el centro del usuario para ponerlo en el pie de página.
	 */
	public String getCentro() {
		//TODO: Coger el centro o el rol si es administrador o similar y devolverlo aquí.
		return "TODO: Poner el centro";
	}

}
