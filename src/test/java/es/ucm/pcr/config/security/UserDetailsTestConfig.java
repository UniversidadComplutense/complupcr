package es.ucm.pcr.config.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import es.ucm.pcr.servicios.CentroServicio;

@TestConfiguration
public class UserDetailsTestConfig {
	
	@Autowired
	CentroServicio centroServicio;
	
	@Bean
    @Primary
    public UserDetailsService userDetailsService() throws Exception {
		
        Set<GrantedAuthority> setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_CENTROSALUD"));
        User ucsUserDetails = new User("centrosalud@ucm.es", "PWD", setAuth);
        
        
        setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_RECEPCIONLABORATORIO"));
        User urviUserDetails = new User("recepcionvisavet@ucm.es", "PWD", setAuth);
        
        setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_TECNICOLABORATORIO"));
        User utviUserDetails = new User("tecnicovisavet@ucm.es", "PWD", setAuth);
        
        return new InMemoryUserDetailsManager(Arrays.asList(ucsUserDetails, urviUserDetails, utviUserDetails));
    }
}
