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