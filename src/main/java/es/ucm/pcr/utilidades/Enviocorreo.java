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

	public void scheduleEnvioMailInicio();

	public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Usuario user);

	public SimpleMailMessage constructWelcomeEmail(String contextPath, Usuario user);

	public SimpleMailMessage constructEmail(String subject, String body, Usuario user);

	public String getAppUrl(HttpServletRequest request);

}
