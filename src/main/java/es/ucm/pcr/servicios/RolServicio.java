package es.ucm.pcr.servicios;

import java.util.List;

import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.modelo.orm.Usuario;

public interface RolServicio {
	
	public List<BeanRol> generarListaRoles() throws Exception;
	
	public List<BeanRol> generarListaRolesUsuario(Usuario usuario) throws Exception;

}
