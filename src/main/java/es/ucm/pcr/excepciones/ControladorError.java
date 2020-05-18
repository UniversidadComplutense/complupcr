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

package es.ucm.pcr.excepciones;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import es.ucm.pcr.utilidades.AppUtil;
//import es.ucm.titulos.preinscripcion.beans.BeanCabecera;

/**
 * Based on the helpful answer at http://stackoverflow.com/q/25356781/56285,
 * with error details in response body added.
 * 
 * @author Joni Karppinen
 * @since 20.2.2015
 * Bueno
 */

@RestController
public class ControladorError implements ErrorController {

	@Autowired
	private ErrorAttributes errorAttributes;
	
	protected Logger log;

	public ControladorError() {
		log = LoggerFactory.getLogger(getClass());
	}
	
	
	@RequestMapping(value ="/error")
	public ModelAndView cargaError(WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
		
		SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		log.error("Controlador de errores: " + errorAttributes.getErrorAttributes(webRequest , false).toString() );
		
        dateParser.setLenient(false);
        Date fecha = (Date) errorAttributes.getErrorAttributes(webRequest , false).get("timestamp");
        String exErrorTimestamptuneado = dateParser.format(fecha);

			
		ModelAndView vista = new ModelAndView();
		vista.setViewName("VistaError");
		vista.addObject("exErrorMessage", " Error genérico en la aplicación");
		vista.addObject("exErrorTimestamptuneado", exErrorTimestamptuneado);
		vista.addObject("exErrorNode", AppUtil.devolverNodo(request.getLocalAddr()));
		vista.addObject("exErrorPeticion", errorAttributes.getErrorAttributes(webRequest , false).get("path"));
	//	vista.addObject("formBeanCabecera", getBeanCabecera(request.getSession()));
	
		
		return vista;
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}

	/*
	 * public BeanCabecera getBeanCabecera(HttpSession session) { BeanCabecera
	 * beanCabecera = (BeanCabecera) session.getAttribute("beanCabecera"); if
	 * (beanCabecera == null) beanCabecera = new BeanCabecera(); return
	 * beanCabecera; }
	 */
}
