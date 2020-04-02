
package es.ucm.pcr.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableAutoConfiguration
@Profile({ "produccion" })
/**
 * Clase de configuración del conector tomcat en producción para ponerlo detrás de F5 con certificado SSL y que las redirecciones
 * las haga a https.
 * 
 * @author pmarrasant
 *
 */
public class ConfiguracionTomcatProduccion {
	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addConnectorCustomizers(tomcatConnectorCustomizers());
	  
		return tomcat;
	}

	private TomcatConnectorCustomizer tomcatConnectorCustomizers() {
		TomcatConnectorCustomizer tcc = new TomcatConnectorCustomizer() {
			
			@Override
			public void customize(Connector connector) {
				connector.setScheme("https");
				connector.setSecure(true);
				connector.setURIEncoding("UTF-8");		
				connector.setProxyPort(443); // el de F5
				connector.setProperty ("disableUploadTimeout", "false");
				connector.setProperty("connectionUploadTimeout", "120000");
		        connector.setProperty("relaxedQueryChars", "|{}[]@\"áéíóú.?&=\\' ");  				
			}
		};
		return tcc;
	}

}