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

package es.ucm.pcr.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ucm.pcr.beans.MenuBean;

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
	
	/** devuelve el rol para hacer consulta en thymeleaf **/
	public String getRol() {
		if (sesionServicio.tieneRol("TECNICOLABORATORIO")) return "TECNICOLABORATORIO";
		return null;
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
		if (sesionServicio.tieneRol("RESPONSABLEPCR") || sesionServicio.tieneRol("ANALISTALABORATORIO")) {
			if (sesionServicio.getLaboratorioCentro() == null) {
				return "Laboratorio: Sin laboratorio asignado";
			}
			return "Laboratorio: " + sesionServicio.getLaboratorioCentro().getNombre();
		}
		if (sesionServicio.tieneRol("VOLUNTARIO")) {
			return "Voluntario";
		}
		if ( sesionServicio.tieneRol("JEFESERVICIO")) {
			return "Jefe de Servicio";
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
