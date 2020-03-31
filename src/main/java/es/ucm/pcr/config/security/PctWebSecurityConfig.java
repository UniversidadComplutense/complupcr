package es.ucm.pcr.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true, prePostEnabled = true)
public class PctWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	PcrUserDetailsService pcrUserDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(pcrUserDetailsService).passwordEncoder(passwordEncoder()).and()
				.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(pcrUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().authorizeRequests().antMatchers("/acceso").permitAll()			
			.and().authorizeRequests().antMatchers("/regenerarContrasena*").permitAll()
			.and().authorizeRequests().antMatchers("/css/**").permitAll()
			.and().authorizeRequests().antMatchers("/img/**").permitAll()
			.and().authorizeRequests().antMatchers("/fonts/**").permitAll()
			.and().authorizeRequests().antMatchers("/js/**").permitAll()
			.and().authorizeRequests().antMatchers("/modificarContrasena*").permitAll()
			.and().authorizeRequests().antMatchers("/**").authenticated()
			.and().formLogin().loginPage("/acceso").usernameParameter("email")
				.passwordParameter("password").failureUrl("/acceso?error")				
				.permitAll().and().logout()
				.logoutUrl("/cerrarSesion").deleteCookies("JSESSIONID").permitAll();
	}
}
