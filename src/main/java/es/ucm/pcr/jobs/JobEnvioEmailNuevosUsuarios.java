package es.ucm.pcr.jobs;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import es.ucm.pcr.config.QuartzConfig;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.servicios.UsuarioServicio;
import es.ucm.pcr.utilidades.Enviocorreo;


/**
 * Esta Clase de tipo Runnable se encargará de enviar los emails a los usuarios que se carguen por BD
 *
 * 
 * @author pmarrasan
 *
 */
@Component
@DisallowConcurrentExecution
public class JobEnvioEmailNuevosUsuarios implements InterruptableJob {
	private static final Logger logger = LoggerFactory.getLogger(JobEnvioEmailNuevosUsuarios.class);
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	@Autowired
	private Enviocorreo envioCorreo;
	@Autowired
	private Environment env;

	@Value("${cron.expression}")
	private String frequency;
	
		
	@Bean(name = "jobJobEnvioEmail")
	public JobDetailFactoryBean altaAvisosIndividualesJob() {
		return QuartzConfig.createJobDetail(this.getClass());
	}

	@Bean(name = "triggerJobEnvioEmail")
	@DependsOn("jobJobEnvioEmail")
	public CronTriggerFactoryBean altaAvisosIndividualesTrigger(@Qualifier("jobJobEnvioEmail") JobDetailFactoryBean jdfb) {
		return QuartzConfig.createCronTrigger(jdfb.getObject(), frequency);
	}

	@Override
	public void execute(JobExecutionContext context) {
		List<Usuario> userList = usuarioServicio.buscarUsuarioInhabilitados();
		for (Usuario user : userList) {
			SimpleMailMessage simpleMailMessage = envioCorreo.constructWelcomeEmail(env.getProperty("app.url"), user);
			envioCorreo.send(user.getEmail(), simpleMailMessage.getSubject(), simpleMailMessage.getText(), null, "",
					"<p><strong>Este es un correo automático enviado por la aplicación COVID-19.</strong></p>"
							+ "<p><strong>No responda a este mensaje.</strong></p>",
					"");
			user.setHabilitado("E");
			usuarioServicio.save(user);
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub

	}
}
