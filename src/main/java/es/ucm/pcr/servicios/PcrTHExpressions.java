package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ucm.pcr.beans.MenuBean;
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
	public String getPieDerecho() {
		//Organizamos esto por roles
		if (sesionServicio.tieneRol("ADMIN")) {
			return "Administrador";
		}
		if (sesionServicio.tieneRol("GESTOR")) {
			return "Gestor";
		}
		if (sesionServicio.tieneRol("CENTROSALUD")) {
			if (sesionServicio.getCentro() == null) {
				return "Centro salud: Sin centro asignado";
			}
			return "Centro salud: " + sesionServicio.getCentro().getNombre();
		}
		if (sesionServicio.tieneRol("RECEPCIONLABORATORIO") || sesionServicio.tieneRol("TECNICOLABORATORIO")) {
			if (sesionServicio.getLaboratorioVisavet() == null) {
				return "Laboratorio: Sin laboratorio asignado";
			}
			return "Laboratorio: " + sesionServicio.getLaboratorioVisavet().getNombre();
		}
		if (sesionServicio.tieneRol("RESPONSABLEPCR") || sesionServicio.tieneRol("JEFESERVICIO") || sesionServicio.tieneRol("ANALISTALABORATORIO")) {
			if (sesionServicio.getLaboratorioCentro() == null) {
				return "Laboratorio: Sin laboratorio asignado";
			}
			return "Laboratorio: " + sesionServicio.getLaboratorioCentro().getNombre();
		}
		if (sesionServicio.tieneRol("VOLUNTARIO")) {
			return "Voluntario";
		}
		if (sesionServicio.tieneRol("AUDITOR")) {
			return "Auditor";
		}
		return "";
	}
	
	/**
	 * Método que devuelve el menu del usuario.
	 */
	public List<MenuBean> getMenu() {
		List<MenuBean> menu = sesionServicio.getMenu();
		return menu;
	}
	
	

}
