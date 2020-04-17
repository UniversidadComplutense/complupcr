package es.ucm.pcr.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import es.ucm.pcr.config.security.PcrUserDetails;
import es.ucm.pcr.modelo.orm.Usuario;

@TestConfiguration
public class UserDetailsTestConfig {

	@Bean
    @Primary
    public UserDetailsService userDetailsService() {
        Usuario usuario = new Usuario();
        usuario.setEmail("centrosalud@ucm.es");
        usuario.setPassword("PWD");
        Set<GrantedAuthority> setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_CENTROSALUD"));
        PcrUserDetails ucsUserDetails = new PcrUserDetails(usuario, setAuth);
        
        usuario = new Usuario();
        usuario.setEmail("recepcionvisavet@ucm.es");
        usuario.setPassword("PWD");
        setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_RECEPCIONLABORATORIO"));
        PcrUserDetails urviUserDetails = new PcrUserDetails(usuario, setAuth);
        
        usuario = new Usuario();
        usuario.setEmail("tecnicovisavet@ucm.es");
        usuario.setPassword("PWD");
        setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_TECNICOLABORATORIO"));
        PcrUserDetails utviUserDetails = new PcrUserDetails(usuario, setAuth);
        
        return new InMemoryUserDetailsManager(Arrays.asList(ucsUserDetails, urviUserDetails, utviUserDetails));
    }
}
