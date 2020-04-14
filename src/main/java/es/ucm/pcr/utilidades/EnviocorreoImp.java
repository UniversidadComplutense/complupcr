package es.ucm.pcr.utilidades;

import java.io.ByteArrayOutputStream;
import java.util.List;

/* Incluir en el pom.xml
 *  <!-- ========== Mail ========== -->  
		<dependency>
			<groupId>javax.mail</groupId>
			<artifactId>mail</artifactId>
			<version>1.4.7</version>
		</dependency>
		
		
		<dependency>
			<groupId>org.springframework.integration</groupId>
			<artifactId>spring-integration-mail</artifactId>			
		</dependency>
 
 */
/* Incluir en el contexto
/* <!--#Configuración del servicio de Spring: MailSernder -->  
28.    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">  
29.      <property name="host" value="${mail.host}" ucsmtp.ucm.es />  
30.      <property name="port" value="${mail.port}"/>  
31.      <property name="username" value="${mail.username}"/>  
32.      <property name="password" value="${mail.password}"/>  
33.      <property name="defaultEncoding" value="UTF-8"/>  
34.    </bean>  
35.  
36.    <!--#Configuración de nuestro servicio:MailService -->  
37.    <bean id="mailService" class="com.autentia.training.spring.mail.MailServiceImpl">  
38.      <property name="active" value="true"/>  
39.      <property name="mailSender" ref="mailSender"/>  
40.      <property name="from" value="default@unknown.com"/>  
41.    </bean>  

 * 
 * 
 * 
 * 
 * 
 */
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.ucm.pcr.beans.BeanAdjunto;
import es.ucm.pcr.beans.BeanCorreo;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.servicios.UsuarioServicio;
//import es.ucm.titulos.preinscripcion.servicio.ServicioSolicitud;
import net.sf.jasperreports.engine.JasperExportManager;

/**
 * Servicio de envío de emails
 */

@Component
public class EnviocorreoImp implements Enviocorreo {

//	@Autowired
//	private ServicioSolicitud servicioSolicitud;
	@Autowired
	private UsuarioServicio usuarioServicio;
	@Autowired
	private Environment env;

	// funcionalidad de envío de correo genérico cogiendo los datos de el beanCorreo
	// rellenado previamente en funcion del tipo de correo
	@Override
	public boolean enviarCorreoGenericoConAdjuntos(BeanCorreo beanCorreo) throws Exception {
		// llamaremos al metodo send y registraremos en el log los datos del envio de
		// correo

		// EnvioMail logEnvioMail = new EnvioMail();

		// System.out.println("aquí estamos enviando el email a: " +
		// beanCorreo.getPara());
		EnviocorreoImp correoresguardosc = new EnviocorreoImp();
		System.out.println("ENVIO CORRREO DE PRUEBAS-enviarCorreoGenericoConAdjuntos");
		correoresguardosc.send(beanCorreo.getPara(), beanCorreo.getAsunto(), beanCorreo.getTexto(),
				beanCorreo.getListaAdjuntos(), beanCorreo.getCabecera(), beanCorreo.getPie(),
				beanCorreo.getConvocatoria());

		return true;

	}

	// añado otra funcion send pero con lista de adjuntos (el beanAdjunto trae el
	// fichero jasperPrint y su nombre) y la convocatoria
	public void send(String to, String subject, String texto, List<BeanAdjunto> listaAdjuntos, String cabecera,
			String pie, String convocatoria) {

		// Comprobamos si el parámetro "to" contiene más de una dirección de correo
		// separadas por comas y sin espacios. En ese caso las almacenamos
		// en un array de tipo String que pasaremos como parámetro al
		// "MimeMessageHelper".

		String[] destinatarios = null;
		if (to.contains(",")) {
			destinatarios = to.split("\\,");
		}

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		MimeMessage msg = mailSender.createMimeMessage();

		try {

			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			helper = new MimeMessageHelper(msg, true);
			helper.setFrom("no-reply@ucm.es");
			if (destinatarios == null) {
				helper.setTo(to);
			} else {
				helper.setTo(destinatarios);
			}
			String perfilesEjecucionActivos = System.getProperty("spring.profiles.active");
//			if (perfilesEjecucionActivos.equals("desarrollo") || perfilesEjecucionActivos.equals("integracion"))
			if (perfilesEjecucionActivos.contains("desarrollo")) {
				helper.setTo("dades@ucm.es");
			}

			helper.setSubject(MimeUtility.encodeText("string", "UTF-8", "Q"));

			helper.setSubject(subject);

			String quote = "\"";

			String paginahtml =
					// Pongo texto de correo para Titulos propios
					"<html><head> <title>PCR Covid-19</title>" + "<meta http-equiv=" + quote + "Content-Type" + quote
							+ " content=" + quote + "text/html" + quote + "; charset=utf-8" + quote + "/>"
							+ "</head> <body> <h2 style=\"background-color: orange;color: white;font-size: 3em;font-family: Verdana;font-size: 32px;padding: 0.2em 1em;text-align: center;margin: 10;\">PCR Covid-19</h2>"
							+ "<div align=\"center\">" + " <p> " + cabecera + " </p>	" + " <p> " + texto
							+ " </p>	" + " <p> " + pie + " </p>	" + "</div>" + "</body> </html>";

			helper.setText(paginahtml, true);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ByteArrayDataSource aAttachment = null;

			if (listaAdjuntos != null) {
				// enviamos tantos atachment como beanAdjuntos (reportes con su nombre) vengan
				// por cabecera
				for (BeanAdjunto beanAdjunto : listaAdjuntos) {
					JasperExportManager.exportReportToPdfStream(beanAdjunto.getReporte(), baos);
					aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
					String nombreaAttachment = beanAdjunto.getNombre();
					if ((aAttachment != null) && (nombreaAttachment != null)) {

						helper.addAttachment(nombreaAttachment, aAttachment);
					}
				}
			}

		}

		catch (MessagingException e) {
			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		mailSender.setHost("ucsmtp.ucm.es");
		mailSender.send(msg);

	}

	@Scheduled(cron = "${cron.expression}")
	public void scheduleEnvioMailInicio() {
		List<Usuario> userList = usuarioServicio.buscarUsuarioInhabilitados();
		for (Usuario user : userList) {
			SimpleMailMessage simpleMailMessage = constructWelcomeEmail(env.getProperty("app.url"), user);
			send(user.getEmail(), simpleMailMessage.getSubject(), simpleMailMessage.getText(), null, "",
					"<p><strong>Este es un correo automático enviado por la aplicación COVID-19.</strong></p>"
							+ "<p><strong>No responda a este mensaje.</strong></p>",
					"");
			user.setHabilitado("E");
			usuarioServicio.save(user);
		}

	}

	public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Usuario user) {
		String url = contextPath + "/modificarContrasena?id=" + user.getId() + "&token=" + token;
		String message = "<p>Hola " + user.getNombre()
				+ ",</p><p>Puede realizar el cambio de contraseña a través del siguente enlace:</p>";
		return constructEmail("Restablecer contraseña PCR Covid-19",
				message + " <p><a href=\"" + url + "\">Cambio de contraseña<a> </p><p> Un cordial saludo.</p>", user);
	}

	public SimpleMailMessage constructWelcomeEmail(String contextPath, Usuario user) {
		String url = contextPath + "/regenerarContrasena";
		String message = "<p>Bienvenido " + user.getNombre()
				+ ",</p><p>Ha sido dado de alta en la aplicación de gesión y seguimiento de tests PCR Covid-19.</p>"
				+ "<p>Para poder acceder debe solicitar el cambio de contraseña, indicando su e-mail ("
				+ user.getEmail() + ") a través del siguente enlace:</p>";
		return constructEmail("Sistema de gestión y seguimiento de tests PCR Covid-19",
				message + " <p><a href=\"" + url + "\">Cambio de contraseña<a> </p><p> Un cordial saludo.</p>", user);
	}

	public SimpleMailMessage constructEmail(String subject, String body, Usuario user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom("no-reply@ucm.es");
		return email;
	}

	public String getAppUrl(HttpServletRequest request) {
		return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@Override
	public void correoBienVenidaUsuario(Usuario user) {
		SimpleMailMessage simpleMailMessage = constructWelcomeEmail(env.getProperty("app.url"), user);
		send(user.getEmail(), simpleMailMessage.getSubject(), simpleMailMessage.getText(), null, "",
				"<p><strong>Este es un correo automático enviado por la aplicación COVID-19.</strong></p>"
						+ "<p><strong>No responda a este mensaje.</strong></p>",
				"");
		user.setHabilitado("E");
		usuarioServicio.save(user);
		
	}

}
