package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.MenuBean;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.LaboratorioCentro;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.LaboratorioCentroRepositorio;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;

@Service
public class SesionServicioImpl implements SesionServicio {

	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;

	@Autowired
	LaboratorioCentroRepositorio laboratorioCentroRepositorio;

	@Autowired
	Environment environment;

	@Override
	public Usuario getUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		User user = (User) authentication.getPrincipal();
		Usuario usu = usuarioServicio.findByEmail(user.getUsername());
		return usu;
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
		String rolSprSec = "ROLE_" + rol;
		return this.getRoles().contains(rolSprSec);
	}

	@Override
	@Transactional
	public Centro getCentro() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioServicio.findByEmail(user.getUsername());
		return usuario.getCentro();
	}

	@Override
	public List<MenuBean> getMenu() {
		List<MenuBean> menuPrincipal = null;
		List<MenuBean> menuSecundario = null;
		MenuBean opcionSecundaria = null;
		MenuBean opcionPrincipal = null;

		menuPrincipal = new ArrayList<MenuBean>();
//	Centro de Salud	
		if (this.tieneRol("ADMIN") || this.tieneRol("CENTROSALUD")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Nueva muestra", "/centroSalud/muestra/nueva", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Búsqueda de muestras", "/centroSalud/muestra", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Nuevo lote", "/centroSalud/lote/nuevo", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Búsqueda de lotes", "/centroSalud/lote", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Centro de salud", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//  Recepcion Laboratorio
		if (this.tieneRol("ADMIN") || this.tieneRol("RECEPCIONLABORATORIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Recepción de Lotes", "/laboratorioUni/buscarLotes?estado=3&rol=R", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Recepción laboratorio", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);

		}
//	Tecnico laboratorio
		if (this.tieneRol("ADMIN") || this.tieneRol("TECNICOLABORATORIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Asignar lotes a placas", "/laboratorioUni/buscarLotes?estado=4&rol=T",
					null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Gestionar placas", "/laboratorioUni/buscarPlacas/list", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Tecnico laboratorio", null, menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		Responsable PCR
		if (this.tieneRol("ADMIN") || this.tieneRol("RESPONSABLEPCR")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Recepción de placas con muestras a analizar",
					"/laboratorioCentro/recepcionPlacas/list", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Gestión de placas PCR", "/laboratorioCentro/gestionPlacas/list", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Responsable PCR", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		Jefe de servicio
		if (this.tieneRol("ADMIN") || this.tieneRol("JEFESERVICIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Revisar muestras", "/analisis/", null);
			menuSecundario.add(opcionSecundaria);
			// opcionSecundaria = new MenuBean("Revisar muestras", "", null);
			// menuSecundario.add(opcionSecundaria);
			// opcionSecundaria = new MenuBean("Estado muestras", "", null);
			// menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Coger y asignar placas", "/analisis/cogerPlacas", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Jefe de servicio", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//			Analista
		if (this.tieneRol("ADMIN") || this.tieneRol("ANALISTALABORATORIO")) {
			menuSecundario = new ArrayList<MenuBean>();
			// opcionSecundaria = new MenuBean("Revisar muestras",
			// "/analisis/listarMuestrasAnalista", null);
			// menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Revisar placas", "/analisis/listarPlacasAnalista", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Analista", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//		AUDITOR
		if (this.tieneRol("ADMIN") || this.tieneRol("AUDITOR")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Consulta log muestras", "/gestor/log", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Auditor", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}
//	GESTOR
		if (this.tieneRol("ADMIN") || this.tieneRol("GESTOR")) {
			menuSecundario = new ArrayList<MenuBean>();
			opcionSecundaria = new MenuBean("Centros de salud", "/gestor/listaCentros", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Laboratorios Centros UCM", "/gestor/listaLaboratorioCentro", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Laboratorios Visavet", "/gestor/listaLaboratorioVisavet", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Equipos PCR", "/gestor/listaEquipos", null);
			menuSecundario.add(opcionSecundaria);
			opcionSecundaria = new MenuBean("Usuarios", "/gestor/lista", null);
			menuSecundario.add(opcionSecundaria);
			opcionPrincipal = new MenuBean("Gestor", "", menuSecundario);
			menuPrincipal.add(opcionPrincipal);
		}

		return menuPrincipal;
	}

	@Override
	public LaboratorioVisavet getLaboratorioVisavet() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioServicio.findByEmail(user.getUsername());
		Integer idLabV = usuario.getIdLaboratorioVisavet();
		Optional<LaboratorioVisavet> labV = laboratorioVisavetRepositorio.findById(idLabV);
		if (labV.isPresent()) {
			return labV.get();
		} else {
			return null;
		}
	}

	@Override
	public LaboratorioCentro getLaboratorioCentro() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Usuario usuario = usuarioServicio.findByEmail(user.getUsername());
		Integer idLabC = usuario.getIdLaboratorioCentro();
		Optional<LaboratorioCentro> labC = laboratorioCentroRepositorio.findById(idLabC);
		if (labC.isPresent()) {
			return labC.get();
		} else {
			return null;
		}
	}
}
