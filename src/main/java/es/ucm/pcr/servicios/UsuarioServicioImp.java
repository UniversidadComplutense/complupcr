package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanRolUsuario;
import es.ucm.pcr.beans.BeanRolUsuario.RolUsuario;
import es.ucm.pcr.beans.BeanUsuario;
import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.PasswordResetToken;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.CentroRepositorio;
import es.ucm.pcr.repositorio.PasswordTokenRepositorio;
import es.ucm.pcr.repositorio.RolRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;
import es.ucm.pcr.utilidades.EnviocorreoImp;

@Service
@Transactional
public class UsuarioServicioImp implements UsuarioServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImp.class);

	@Autowired
	UsuarioRepositorio usurep;
	
	@Autowired
	CentroRepositorio centroRepositorio;
	
	@Autowired
	RolRepositorio rolRepositorio;
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	PasswordTokenRepositorio passwordTokenRepositorio;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Environment env;
	
	@Autowired
	EnviocorreoImp enviocorreoImp;
	
	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		email = email.trim().toLowerCase();
		Optional<Usuario> usuario = usurep.findByEmail(email);
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			return null;
		}
	}

	@Override
	public Set<Rol> getRoles(Usuario usuario) {
		return usuario.getRols();
	}
	
	// Un usuario puede no estar asociado a ningún centro,
	// estar asociado a un centro de salud, a un laboratorio UCM
	// o a un laboratotrio Visavet.
	// Para laboratorio Ucm y Visavet el bean guarda su Id, pero 
	// para centros de salud guarda una entidad Centro, que puede
	// ser null,o el id de dicho centro.
	// Esta función devuelve el valos de ese IdCentro es caso de que exista
	public Integer centroSaludSeleccionadoUsuario (Usuario usuario) throws Exception
	{
		Integer centroSeleccionado;
		if (usuario.getCentro() != null)
		{
			centroSeleccionado = usuario.getCentro().getId();
		}
		else
		{
			centroSeleccionado = null;
		}
		return centroSeleccionado;
	}
	
	
	
	// Nos dice si un usuario tiene asignado un Centro de salud, un laboratorio UCM,
	// Un laboratorio Visavet o ninguno de ellos.
//	public  String tipoCentroSeleccionadoUsuario (Usuario usuario) throws Exception
//	{
//		String tipoCentroAsignado = null;
//        // Es un centro de salud
//        if ( usuario.getCentro() != null && usuario.getIdLaboratorioCentro() == null && usuario.getIdLaboratorioVisavet() == null)
//        {
//        	tipoCentroAsignado = "C";
//        }
//        // Es un laboratorio Centro UCM
//        if ( usuario.getCentro() == null && usuario.getIdLaboratorioCentro() != null && usuario.getIdLaboratorioVisavet() == null)
//        {
//        	tipoCentroAsignado = "L";
//        }  
//        // Es un laboratorio Visavet
//        if (usuario.getCentro() == null && usuario.getIdLaboratorioCentro() == null && usuario.getIdLaboratorioVisavet() != null)
//        {
//        	tipoCentroAsignado = "V";
//        }  
//        // Todos null; A elegir
//        if (usuario.getCentro() == null && usuario.getIdLaboratorioCentro() == null && usuario.getIdLaboratorioVisavet() == null)
//        {
//        	tipoCentroAsignado = null;
//        }  
//        return tipoCentroAsignado;
//	}
	
	@Override
	public BeanUsuarioGestion mapeoEntidadBeanUsuario (Usuario usuario) throws Exception
	{
		BeanUsuarioGestion beanUsuario =  new BeanUsuarioGestion();
		
		beanUsuario.setId(usuario.getId());
		beanUsuario.setAcertadas(usuario.getAcertadas());
		beanUsuario.setApellido1(usuario.getApellido1());
		beanUsuario.setApellido2(usuario.getApellido2());
		beanUsuario.setAsignadas(usuario.getAsignadas());
		beanUsuario.setCentro(usuario.getCentro());
		beanUsuario.setDocumentos(usuario.getDocumentos());
		beanUsuario.setEmail(usuario.getEmail());
		beanUsuario.setHabilitado(usuario.getHabilitado());
		beanUsuario.setId(usuario.getId());
		beanUsuario.setIdLaboratorioCentro(usuario.getIdLaboratorioCentro());
		beanUsuario.setIdLaboratorioVisavet(usuario.getIdLaboratorioVisavet());
		beanUsuario.setNombre(usuario.getNombre());
		beanUsuario.setPassword(usuario.getPassword());
		beanUsuario.setRols(usuario.getRols());
		beanUsuario.setUsuarioMuestras(usuario.getUsuarioMuestras());	
        beanUsuario.setCentroSeleccionado(centroSaludSeleccionadoUsuario(usuario));
//        beanUsuario.setTipoCentroSeleccionado(tipoCentroSeleccionadoUsuario(usuario));
 		
		return beanUsuario;
	}
	
	@Override
	public Usuario mapeoBeanEntidadUsuarioAlta (BeanUsuarioGestion beanUsuario, int[] roles) throws Exception{
		
		Usuario usuario = new Usuario();
		
		usuario.setAcertadas(beanUsuario.getAcertadas());
		usuario.setApellido1(beanUsuario.getApellido1());
		usuario.setApellido2(beanUsuario.getApellido2());
		usuario.setAsignadas(beanUsuario.getAsignadas());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setEmail(beanUsuario.getEmail());
		usuario.setHabilitado(beanUsuario.getHabilitado());
		usuario.setId(beanUsuario.getId());
		usuario.setNombre(beanUsuario.getNombre());
		// el Pwd se asigna por otros medios, pero no puede ir vacio
		usuario.setPassword(passwordEncoder.encode("PWD"));
		// Enviamos mail para cambio de Pwd 
		SimpleMailMessage simpleMailMessage = enviocorreoImp.constructWelcomeEmail(env.getProperty("app.url"), usuario);
		enviocorreoImp.send(usuario.getEmail(), simpleMailMessage.getSubject(), simpleMailMessage.getText(), null, "",
				"<p><strong>Este es un correo automático enviado por la aplicación COVID-19.</strong></p>"
						+ "<p><strong>No responda a este mensaje.</strong></p>",
				"");
		usuario.setHabilitado("E");
		// Añado los roles seleccionado
		Set<Rol> rolesSeleccionados = new HashSet<Rol>(0);
		if(roles != null) 
		{
		    for (int i = 0; i < roles.length; i++)  
		    {
		    	Optional<Rol> rol = rolRepositorio.findById(roles[i]);
		    	rolesSeleccionados.add(rol.get());
		    }
		}
		usuario.setRols(rolesSeleccionados);
		// Centro seleccionado
		if (beanUsuario.getCentroSeleccionado() != null && centroRepositorio.existsById(beanUsuario.getCentroSeleccionado()))
		{
				Optional<Centro> centroGuardar = centroRepositorio.findById(beanUsuario.getCentroSeleccionado());
				usuario.setCentro(centroGuardar.get());
		} else {
			usuario.setCentro(null);
		}
		//Laboratorio Visavet
		usuario.setIdLaboratorioVisavet(beanUsuario.getIdLaboratorioVisavet());
		//Laboratorio Centro UCM
		usuario.setIdLaboratorioCentro(beanUsuario.getIdLaboratorioCentro());

		//Muestras
		usuario.setUsuarioMuestras(beanUsuario.getUsuarioMuestras());
		
		return usuario;
		
	}
	
	@Override
	public Usuario mapeoBeanEntidadUsuarioModificar (BeanUsuarioGestion beanUsuario, Usuario usuario, int[] roles) throws Exception {
		
		// No asigno el id del usuario
		usuario.setAcertadas(beanUsuario.getAcertadas());
		usuario.setApellido1(beanUsuario.getApellido1());
		usuario.setApellido2(beanUsuario.getApellido2());
		usuario.setAsignadas(beanUsuario.getAsignadas());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		//  El mail es único, y asociado al usuario, no poemos modifciarlo
//		usuario.setEmail(beanUsuario.getEmail());
		usuario.setHabilitado(beanUsuario.getHabilitado());
		usuario.setId(beanUsuario.getId());
		usuario.setNombre(beanUsuario.getNombre());
		// el Pwd se asigna por otros medios, no podemos modificarlo
//		usuario.setPassword(beanUsuario.getPassword());
		// Añado los roles seleccionado
		Set<Rol> rolesSeleccionados = new HashSet<Rol>(0);
		if(roles != null) 
		{
		    for (int i = 0; i < roles.length; i++)  
		    {
		    	Optional<Rol> rol = rolRepositorio.findById(roles[i]);
		    	rolesSeleccionados.add(rol.get());
		    }
		}
		usuario.setRols(rolesSeleccionados);
		// Centro seleccionado
		if (beanUsuario.getCentroSeleccionado() != null && centroRepositorio.existsById(beanUsuario.getCentroSeleccionado()))
		{
				Optional<Centro> centroGuardar = centroRepositorio.findById(beanUsuario.getCentroSeleccionado());
				usuario.setCentro(centroGuardar.get());
		} else {
			usuario.setCentro(null);
		}
		// laboratorio centro Ucm
		usuario.setIdLaboratorioCentro(beanUsuario.getIdLaboratorioCentro());
		// Laboratorio Visavet
		usuario.setIdLaboratorioVisavet(beanUsuario.getIdLaboratorioVisavet());
		
		//Muestras
		usuario.setUsuarioMuestras(beanUsuario.getUsuarioMuestras());
		
		return usuario;
	}
	
	public List<BeanUsuarioGestion> listaUsuariosOrdenada() throws Exception {
		
		List<BeanUsuarioGestion> listaUsuarios = new ArrayList<BeanUsuarioGestion>();
		for (Usuario usuario: usuarioRepositorio.findAll())
		{
			BeanUsuarioGestion beanUsuarioGestion = new BeanUsuarioGestion();
			beanUsuarioGestion = mapeoEntidadBeanUsuario (usuario);
			listaUsuarios.add(beanUsuarioGestion);
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaUsuarios);
		return listaUsuarios;

	}
	
	public void createPasswordResetTokenForUser(Usuario user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(user,token);
	    passwordTokenRepositorio.save(myToken);
	}

	@Override
	public void cambiarContrasena(String email, String contrasena) {
		Usuario user = buscarUsuarioPorEmail(email);
		user.setPassword(passwordEncoder.encode(contrasena));
		user.setHabilitado("A");
		usurep.save(user);
		passwordTokenRepositorio.deleteByUsuario(user);
		
	}

	@Override
	public List<Usuario> buscarUsuarioInhabilitados() {
		return usurep.findByHabilitadoOrderById("I");
	}

	@Override 
	public Usuario guardar(Usuario usuario) {
		usurep.save(usuario);
		return usuario;
	}
	
	public void borrarUsuario (Integer idUsuario) throws Exception{
		usurep.deleteById(idUsuario);
	}
	
	public Optional<Usuario> buscarUsuarioPorId (Integer idUsuario) throws Exception{
		return usurep.findById(idUsuario);
	}

	//metodos de obtencion de usuarios analistas de laboratoriocentro, voluntarios de laboratoriocentro y voluntarios sin laboratoriocentro
	@Override 
	public List<BeanUsuario> listaUsuariosAnalistasDeLaboratorioCentro(Integer idLaboratorioCentro) {
		List<BeanUsuario> listaUsuariosBean = new ArrayList<BeanUsuario>();
		Integer idRolAnalistaLaboratorio = BeanRolUsuario.RolUsuario.ROL_USUARIO_ANALISTALABORATORIO.getId(); 
		List<Usuario> listUsuarios = usurep.findByIdLaboratorioCentroAndIdRol(idLaboratorioCentro, idRolAnalistaLaboratorio);
		System.out.println("la lista de analistas del laboratorioCentro tiene: " + listUsuarios.size());
		for (Usuario u : listUsuarios) {
			BeanUsuario ana = BeanUsuario.modelToBean(u);			
			ana.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_ANALISTALABORATORIO));
			listaUsuariosBean.add(ana);
		}
		return listaUsuariosBean;
	}
	
	@Override 
	public List<BeanUsuario> listaUsuariosVoluntariosDeLaboratorioCentro(Integer idLaboratorioCentro) {		
		List<BeanUsuario> listaUsuariosBean = new ArrayList<BeanUsuario>();
		Integer idRolVoluntario = BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId(); 
		List<Usuario> listUsuarios = usurep.findByIdLaboratorioCentroAndIdRol(idLaboratorioCentro, idRolVoluntario);
		System.out.println("la lista de voluntarios del laboratorioCentro tiene: " + listUsuarios.size());
		for (Usuario u : listUsuarios) {
			BeanUsuario vol = BeanUsuario.modelToBean(u);			
			vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); 
			listaUsuariosBean.add(vol);
		}
		return listaUsuariosBean;
	}
	
	@Override 
	public List<BeanUsuario> listaUsuariosVoluntariosSinLaboratorioCentro() {
		List<BeanUsuario> listaUsuariosBean = new ArrayList<BeanUsuario>();
		Integer idRolVoluntario = BeanRolUsuario.RolUsuario.ROL_USUARIO_VOLUNTARIO.getId(); 
		List<Usuario> listUsuarios = usurep.findByIdRolAndNotIdLaboratorioCentro(idRolVoluntario);
		System.out.println("la lista de voluntarios sin asignar a ningun laboratorioCentro tiene: " + listUsuarios.size());
		for (Usuario u : listUsuarios) {
			BeanUsuario vol = BeanUsuario.modelToBean(u);			
			vol.setBeanRolUsuario(new BeanRolUsuario(RolUsuario.ROL_USUARIO_VOLUNTARIO)); 
			listaUsuariosBean.add(vol);
		}
		return listaUsuariosBean;		
	}
	
}
