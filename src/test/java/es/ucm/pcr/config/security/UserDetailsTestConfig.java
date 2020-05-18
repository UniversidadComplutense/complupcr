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
        
        setAuth = new HashSet<GrantedAuthority>();
        setAuth.add(new SimpleGrantedAuthority("ROLE_RESPONSABLEPCR"));
        User urpcrUserDetails = new User("responsablepcr@ucm.es", "PWD", setAuth);
        
        return new InMemoryUserDetailsManager(Arrays.asList(ucsUserDetails, urviUserDetails, utviUserDetails, urpcrUserDetails));
    }
}
