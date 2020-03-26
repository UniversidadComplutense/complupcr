package es.ucm.pcr.config;

import java.sql.Driver;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@EnableSpringDataWebSupport
@Configuration
@Profile({ "desarrollo" ,"integracion"})
//@ImportResource("/configuracion/appServlet-servlet.xml")
@EnableTransactionManagement
@EnableJpaRepositories
(basePackages="es.ucm.titulos.preinscripcion.repositorio.uxxiac", 
entityManagerFactoryRef="entityManagerFactoryUXXIAC", transactionManagerRef = "transactionManagerUXXIAC")


public class ConfiguracionDesarrolloPCR {


	/*@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    CharacterEncodingFilter filter = new CharacterEncodingFilter();
	    filter.setEncoding("UTF-8");
        filter.isForceRequestEncoding();
        filter.isForceResponseEncoding();
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(filter);
	    registrationBean.addUrlPatterns("/*");
	    return registrationBean;
	}
	*/
	
	
	
	 @Bean(name="TransactionUXXIAC")
	    public PlatformTransactionManager transactionManagerUXXIAC(){
				return new JpaTransactionManager(entityManagerFactoryUXXIAC().getObject());
	     
	    }
	
	
	  @Bean(name="entityManagerFactoryUXXIAC")
	  public	  LocalContainerEntityManagerFactoryBean entityManagerFactoryUXXIAC() {
	
	  LocalContainerEntityManagerFactoryBean em = new    	  LocalContainerEntityManagerFactoryBean(); 
	  em.setDataSource(dataSourceUXXIAC());
	  em.setPackagesToScan(new String[] { "es.ucm.titulos.preinscripcion.modelo.uxxiac" });
	  JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
	  em.setJpaProperties(additionalProperties());
	  em.setPersistenceUnitName("TransactionUXXIAC");
	  return em; }
	  

	  
	
	  
	
	
	  @Bean(name="dataSourceUXXIAC") public DataSource dataSourceUXXIAC(){
	  
	  DriverManagerDataSource dataSourceUXXIAC = new DriverManagerDataSource();
	  dataSourceUXXIAC.setDriverClassName("oracle.jdbc.OracleDriver"); //Oracle 12
	  // balanceador //10.147.138.3:1521/uxxiacp"); //Oracle 12 1 //
	  //hin:@//10.147.138.5:1521/uxxiacp"); //Oracle 12 2 // //
	//  thin:@//10.147.138.6:1521/uxxiacp"); // NODO 1 //
	  dataSourceUXXIAC.setUrl("jdbc:oracle:thin:@//10.147.138.3:1521/uxxiacp");
	  
	  
	  
	  dataSourceUXXIAC.setUsername( "UXXIAC" ); dataSourceUXXIAC.setPassword(
	  "2014integ" ); return dataSourceUXXIAC;
	  
	  }
	 
	
	  
	
	/*
	 * // produccion
	 * 
	 * @Bean(name="dataSourceUXXIAC") public DataSource dataSourceUXXIAC(){
	 * 
	 * DriverManagerDataSource dataSourceUXXIAC = new DriverManagerDataSource();
	 * 
	 * dataSourceUXXIAC.setDriverClassName("oracle.jdbc.OracleDriver"); //
	 * 
	 * dataSourceUXXIAC.setUrl("jdbc:oracle:thin:@//orac.adm.ucm.es:1521/UXXIAC" );
	 * dataSourceUXXIAC.setUsername( "UXXIAC" ); dataSourceUXXIAC.setPassword(
	 * "ucm2009" ); return dataSourceUXXIAC;
	 * 
	 * }
	 * 
	 */
	  
	  
	Properties additionalProperties() {
		Properties properties = new Properties();
		//"org.hibernate.dialect.Oracle10gDialect"
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
		properties.setProperty("hibernate.cache.use_second_level_cache", "false");
		properties.setProperty("hibernate.connection.useUnicode", "true");
		properties.setProperty("hibernate.connection.characterEncoding","UTF-8"); 
		properties.setProperty("hibernate.connection.charSet", "UTF-8"); 
	//	properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}


}
