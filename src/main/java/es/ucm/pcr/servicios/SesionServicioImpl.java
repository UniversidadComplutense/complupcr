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
		List<MenuBean> menuPrincipal = null;
		List<MenuBean> menuSecundario = null;
		MenuBean opcionSecundaria = null;
		MenuBean opcionPrincipal = null;

		menuPrincipal = new ArrayList<MenuBean>();
//		Administrador
			if (this.tieneRol("ROLE_ADMIN")) {
				menuSecundario = new ArrayList<MenuBean>();
				opcionSecundaria = new MenuBean("Gestionar Usuarios", "/gestor/usuario/gestion", null);
				menuSecundario.add(opcionSecundaria);
				opcionSecundaria = new MenuBean("Gestionar Roles", "/gestor/rol/alta", null);
				menuSecundario.add(opcionSecundaria);
				opcionSecundaria = new MenuBean("Gestionar Centros de Salud", "/gestor/centro/alta", null);
				menuSecundario.add(opcionSecundaria);
				opcionSecundaria = new MenuBean("Gestionar Laboratorios", "/gestor/laboratorio/alta", null);
				menuSecundario.add(opcionSecundaria);
				opcionPrincipal = new MenuBean("Administrador", null, menuSecundario);
				menuPrincipal.add(opcionPrincipal);
			}
//	Centro de Salud	
		if (this.tieneRol("ROLE_ADMIN") || this.tieneRol("CENTROSALUD")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Gestionar muestras", "/centroSalud/muestra", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Gestionar lotes", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Centro de salud", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//	Laboratorio Visavet
			if (this.tieneRol("ROLE_ADMIN") || this.tieneRol("TECNICO")) {
				menuSecundario = new ArrayList<MenuBean>();
				opcionSecundaria = new MenuBean("Gestionar lotes centros de salud", "/laboratorioUni/buscarLotes", null);
				menuSecundario.add(opcionSecundaria);
				opcionSecundaria = new MenuBean("Gestionar placas VISAVET", "/laboratorioUni/buscarPlacas", null);
				menuSecundario.add(opcionSecundaria);
				opcionPrincipal = new MenuBean("Laboratorio VISAVET", "", menuSecundario);
				menuPrincipal.add(opcionPrincipal);
			}
//	Tecnico laboratorio
		if (this.tieneRol("ROLE_ADMIN") || this.tieneRol("TECNICO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Gestionar entregas", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Preparar", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Placas en preparacion", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Placas listas para laboratorio", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Laboratorios", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Tecnico laboratorio", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		Responsable PCR
		if (this.tieneRol("ROLE_ADMIN")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Placas recepcionads", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Placas listas para PCR", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Placas esperando resultado PCR", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Responsable PCR", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		Jefe de servicio
		if (this.tieneRol("ROLE_ADMIN")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Asignar muestras", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Revisar  muestras", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Estado  muestras", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Coger  lotes", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Jefe de servicio", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//			Analista
		if (this.tieneRol("ROLE_ADMIN")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Revisar miestra", "", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Analista", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
		return menuPrincipal;
	}

}
