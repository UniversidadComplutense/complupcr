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

/**
 * Clase de configuración general de Spring Security.
 * 
 * Definimos nuestro passwordEncoder, PcrUserDetailsService y configuramos el elemento HttpSecurity
 * con nuestras url's, roles, página de login, etc.
 * 
 * @author pmarrasant
 *
 */
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
			.and().authorizeRequests().antMatchers("/centroSalud/**").hasAnyRole("ADMIN","CENTROSALUD")
			.and().authorizeRequests().antMatchers("/gestor/**").hasAnyRole("ADMIN","GESTOR")
			.and().authorizeRequests().antMatchers("/gestor/log/**").hasAnyRole("ADMIN","GESTOR","AUDITOR")
			.and().formLogin().loginPage("/acceso").usernameParameter("email")
				.passwordParameter("password").failureUrl("/acceso?error")				
				.permitAll().and().logout()
				.logoutUrl("/cerrarSesion").deleteCookies("JSESSIONID").permitAll();
	}
}
