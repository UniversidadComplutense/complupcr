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

package es.ucm.pcr.utilidades;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.SimpleMailMessage;

import es.ucm.pcr.beans.BeanAdjunto;
import es.ucm.pcr.beans.BeanCorreo;
import es.ucm.pcr.modelo.orm.Usuario;

//import es.ucm.titulos.preinscripcion.beans.BeanReporter;
//import es.ucm.titulos.preinscripcion.beans.BeanSolicitud;
//import net.sf.jasperreports.engine.JasperPrint;
public interface Enviocorreo {

	public void send(String to, String subject, String texto, List<BeanAdjunto> listaAdjuntos, String cabecera,
			String pie, String convocatoria);

	public boolean enviarCorreoGenericoConAdjuntos(BeanCorreo beanCorreo) throws Exception;

	public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Usuario user);

	public SimpleMailMessage constructWelcomeEmail(String contextPath, Usuario user);

	public SimpleMailMessage constructEmail(String subject, String body, Usuario user);

	public String getAppUrl(HttpServletRequest request);
	
	public void correoBienVenidaUsuario(Usuario usuario);

}
