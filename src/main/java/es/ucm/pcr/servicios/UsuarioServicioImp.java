package es.ucm.pcr.servicios;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanUsuarioGestion;
import es.ucm.pcr.modelo.orm.PasswordResetToken;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.PasswordTokenRepositorio;
import es.ucm.pcr.repositorio.UsuarioRepositorio;

@Service
@Transactional
public class UsuarioServicioImp implements UsuarioServicio {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImp.class);

	@Autowired
	UsuarioRepositorio usurep;
	
	@Autowired
	PasswordTokenRepositorio passwordTokenRepositorio;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
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
		Optional<Usuario> usuWithRoles = usurep.findByEmailWithRoles(usuario.getEmail());
		if (usuWithRoles.isPresent()) {
			return usuWithRoles.get().getRols();
		} else {
			return null;
		}
	}
	
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
		
		return beanUsuario;
	}
	
	@Override
	public Usuario mapeoBeanEntidadUsuarioAlta (BeanUsuarioGestion beanUsuario) throws Exception{
	
		Usuario usuario = new Usuario();
		
		usuario.setAcertadas(beanUsuario.getAcertadas());
		usuario.setApellido1(beanUsuario.getApellido1());
		usuario.setApellido2(beanUsuario.getApellido2());
		usuario.setAsignadas(beanUsuario.getAsignadas());
		usuario.setCentro(beanUsuario.getCentro());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setEmail(beanUsuario.getEmail());
		usuario.setHabilitado(beanUsuario.getHabilitado());
		usuario.setId(beanUsuario.getId());
		usuario.setIdLaboratorioCentro(beanUsuario.getIdLaboratorioCentro());
		usuario.setIdLaboratorioVisavet(beanUsuario.getIdLaboratorioVisavet());
		usuario.setNombre(beanUsuario.getNombre());
		// el Pwd se asigna por otros medios, pero no puede ir vacio
		if (beanUsuario.getPassword() == null)
		{
			usuario.setPassword("PWD");
		}
		else
		{
			usuario.setPassword(beanUsuario.getPassword());
		}
		usuario.setRols(beanUsuario.getRols());
		usuario.setUsuarioMuestras(beanUsuario.getUsuarioMuestras());
		
		return usuario;
		
	}
	
	@Override
	public Usuario mapeoBeanEntidadUsuarioModificar (BeanUsuarioGestion beanUsuario, Usuario usuario) throws Exception {
		
		// No asigno el id del usuario
		usuario.setAcertadas(beanUsuario.getAcertadas());
		usuario.setApellido1(beanUsuario.getApellido1());
		usuario.setApellido2(beanUsuario.getApellido2());
		usuario.setAsignadas(beanUsuario.getAsignadas());
		usuario.setCentro(beanUsuario.getCentro());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		usuario.setDocumentos(beanUsuario.getDocumentos());
		//  El mail es único, y asociado al usuario, no poemos modifciarlo
//		usuario.setEmail(beanUsuario.getEmail());
		usuario.setHabilitado(beanUsuario.getHabilitado());
		usuario.setId(beanUsuario.getId());
		usuario.setIdLaboratorioCentro(beanUsuario.getIdLaboratorioCentro());
		usuario.setIdLaboratorioVisavet(beanUsuario.getIdLaboratorioVisavet());
		usuario.setNombre(beanUsuario.getNombre());
		// el Pwd se asigna por otros medios, no podemos modificarlo
//		usuario.setPassword(beanUsuario.getPassword());
		usuario.setRols(beanUsuario.getRols());
		usuario.setUsuarioMuestras(beanUsuario.getUsuarioMuestras());
		
		return usuario;
	}
	
	public void createPasswordResetTokenForUser(Usuario user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(user,token);
	    user.setHabilitado("E");
		usurep.save(user);
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
	
}
