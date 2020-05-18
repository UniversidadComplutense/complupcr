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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;


import es.ucm.pcr.utilidades.AppUtil;

@ControllerAdvice
public class GlobalExceptionHandling {

	protected Logger log;

	public GlobalExceptionHandling() {
		log = LoggerFactory.getLogger(getClass());
	}
	
	@Autowired
	private ErrorAttributes errorAttributes;

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, WebRequest webRequest, Exception e) throws Exception {

		if (printErrorLog(e)) {
			log.error("Petición:  ha lanzado la excepción " + e, e);
		}
		
		SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		dateParser.setLenient(false);
		
		Date fecha = (Date) errorAttributes.getErrorAttributes(webRequest, false).get("timestamp");
		String exErrorTimestamptuneado = dateParser.format(fecha);

		ModelAndView vista = new ModelAndView("VistaError");
		vista.addObject("exErrorMessage", getErrorMessage(e));
		vista.addObject("exErrorTimestamptuneado", exErrorTimestamptuneado);
		vista.addObject("exErrorNode", AppUtil.devolverNodo(request.getLocalAddr()));
		vista.addObject("exErrorPeticion", request.getRequestURI());
	//	vista.addObject("formBeanCabecera", getBeanCabecera(request.getSession()));
		
		return vista;

	}
	
	/**
	 * Mensaje de error por excepcion
	 * @param e excepcion
	 * @return mensaje de error
	 */
	private String getErrorMessage(Exception e) {
		String msg = " Error genérico en la aplicación";
		if (e instanceof MaxUploadSizeExceededException) {
			msg = " Ha superado el tamaño máximo permitido de 5MB para la subida de documentos";
		}
		return msg;
	}
	
	/**
	 * Metodo para descartar que excepciones se pintan en el log
	 * @param e excepcion
	 * @return true no excluye excepcion, false se excluyen las excepciones del if
	 */
	private boolean printErrorLog(Exception e) {
		
		if (e instanceof MultipartException) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Recupera los datos del bean de cabecera
	 * @param session sesion
	 * @return datos usuario para la cebecera
	 */
	/*
	 * public BeanCabecera getBeanCabecera(HttpSession session) { BeanCabecera
	 * beanCabecera = (BeanCabecera) session.getAttribute("beanCabecera"); if
	 * (beanCabecera == null) beanCabecera = new BeanCabecera(); return
	 * beanCabecera; }
	 */
	
}