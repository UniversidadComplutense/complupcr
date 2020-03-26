package es.ucm.pcr.config;

	import java.sql.Driver;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableSpringDataWebSupport
@EnableAutoConfiguration
@ComponentScan(basePackages = {"es.ucm.titulos"})
@Profile({ "desarrollo", "integracion" })



@EnableTransactionManagement
@EnableJpaRepositories(basePackages="es.ucm.titulos.preinscripcion.repositorio.egea", 
entityManagerFactoryRef="entityManagerFactoryEGEA",  transactionManagerRef = "transactionManagerEGEA")



public class ConfiguracionIntegracionPCR {
	
	
	@Bean(name="TransactionEGEA")
	 public PlatformTransactionManager transactionManagerEGEA(){
	//	 LocalContainerEntityManagerFactoryBean ementra =  entityManagerFactoryEGEA();
	 	 
		 /*	System.out.println("el entire EGEA" + ementra.toString() + "el objeto que asigno ahora mismo " + entityManagerFactoryEGEA().getObject() +
		   			ementra.getDataSource().toString()   + 	 ementra.getEntityManagerInterface().getCanonicalName().toString());
		 	*/
		 	
		return new JpaTransactionManager(entityManagerFactoryEGEA().getObject());
	  
	    }
	
	  @Primary
	  
	  @Bean(name ="entityManagerFactoryEGEA") public
	  LocalContainerEntityManagerFactoryBean entityManagerFactoryEGEA() {
	  LocalContainerEntityManagerFactoryBean em = new
	  LocalContainerEntityManagerFactoryBean(); em.setDataSource(dataSourceEGEA());
	  em.setPackagesToScan(new String[] {
	  "es.ucm.titulos.preinscripcion.modelo.egea" }); JpaVendorAdapter
	  vendorAdapter = new HibernateJpaVendorAdapter();
	  em.setJpaVendorAdapter(vendorAdapter);
	  em.setJpaProperties(additionalProperties());
	  em.setPersistenceUnitName("TransactionEGEA"); return em; }
	 
	
	
	
	
	  @Primary
	  
	  @Bean(name="dataSourceEGEA") public DataSource dataSourceEGEA() {
	  DriverManagerDataSource dataSourceEGEA = new DriverManagerDataSource();
	  dataSourceEGEA.setDriverClassName("oracle.jdbc.OracleDriver");
	  //dataSourceEGEA.setUrl("jdbc:oracle:thin:@racoraint.adm.ucm.es:1521/EGEA");
	  dataSourceEGEA.setUrl("jdbc:oracle:thin:@scan-clustint.adm.ucm.es:1521/IEGEA"
	  );
	  
	  dataSourceEGEA.setUsername("egea2"); dataSourceEGEA.setPassword("123egea");
	  Properties connectionProperties = new Properties();
	  connectionProperties.setProperty("useUnicode", "true");
	  connectionProperties.setProperty("characterEncoding", "UTF-8");
	  dataSourceEGEA.setConnectionProperties(connectionProperties);
	  
	  return dataSourceEGEA; }
	 

	  
	
	/*
	 * @Primary //PRODUCCION
	 * 
	 * @Bean(name="dataSourceEGEA") public DataSource dataSourceEGEA() {
	 * DriverManagerDataSource dataSourceEGEA = new DriverManagerDataSource();
	 * dataSourceEGEA.setDriverClassName("oracle.jdbc.OracleDriver");
	 * dataSourceEGEA.setUrl("jdbc:oracle:thin:@racora.adm.ucm.es:1521/egea");
	 * dataSourceEGEA.setUsername("egea"); dataSourceEGEA.setPassword("123egea");
	 * Properties connectionProperties = new Properties();
	 * connectionProperties.setProperty("useUnicode", "true");
	 * connectionProperties.setProperty("characterEncoding", "UTF-8");
	 * dataSourceEGEA.setConnectionProperties(connectionProperties);
	 * 
	 * return dataSourceEGEA; }
	 * 
	 */
	  
	  
	
	Properties additionalProperties() {
		Properties properties = new Properties();
		//org.hibernate.dialect.Oracle12cDialect
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
		properties.setProperty("hibernate.cache.use_second_level_cache", "false");
		//properties.setProperty("hibernate.show_sql", "true"); 
		properties.setProperty("hibernate.format_sql", "true"); 
		properties.setProperty("hibernate.connection.useUnicode", "true");
		properties.setProperty("hibernate.connection.characterEncoding", "UTF-8"); 
		properties.setProperty("hibernate.connection.charSet", "UTF-8"); 
	
		return properties;
	}

	

}
