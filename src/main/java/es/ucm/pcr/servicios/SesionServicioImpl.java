package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.MenuBean;
import es.ucm.pcr.config.security.PcrUserDetails;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Usuario;

@Service
public class SesionServicioImpl implements SesionServicio {
	
	@Autowired
	UsuarioServicio usuarioServicio;

	@Override
	public Usuario getUsuario() {
		String email = this.getEmail();
		if (email == null) {
			return null;
		}
		return usuarioServicio.buscarUsuarioPorEmail(email);
	}

	@Override
	public String getEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		String email = authentication.getName();
		return email;
	}
	
	@Override
	public List<String> getRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority auth : authorities) {
			roles.add(auth.getAuthority());
		}
		return roles;
	}
	
	@Override
	public Boolean tieneRol(String rol) {
		return this.getRoles().contains(rol);
	}

	@Override
	public Centro getCentro() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PcrUserDetails ud = (PcrUserDetails) authentication.getPrincipal();
		return ud.getCentro();
	}

	@Override
	public List<MenuBean> getMenu() {
		// TODO Auto-generated method stub -- AARON
		return null;
	}

	
}
