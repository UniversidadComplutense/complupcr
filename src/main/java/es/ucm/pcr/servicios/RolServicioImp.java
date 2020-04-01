package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanRol;
import es.ucm.pcr.modelo.orm.Rol;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.RolRepositorio;

@Service
public class RolServicioImp implements RolServicio{
	
	@Autowired
	RolRepositorio rolRepositorio;
	
	public List<BeanRol> generarListaRoles() throws Exception{
		// cargo todos los rols de BBDD
		List<BeanRol> listaRoles = new ArrayList<BeanRol>();
		for (Rol rol: rolRepositorio.findAll())
		{
			listaRoles.add(new BeanRol(rol.getId(), rol.getNombre(), false, "L"));
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaRoles);
		return listaRoles;
	}

	public List<BeanRol> generarListaRolesUsuario(Usuario usuario) throws Exception{
		// cargo todos los rols de BBDD
		List<BeanRol> listaRoles = new ArrayList<BeanRol>();
		for (Rol rol: rolRepositorio.findAll())
		{
			if(usuario.getRols().contains(rol))
			{
				listaRoles.add(new BeanRol(rol.getId(), rol.getNombre(), true, "L"));
			}
			else
			{
				listaRoles.add(new BeanRol(rol.getId(), rol.getNombre(), false, "L"));
			}
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaRoles);
		return listaRoles;
	}

}
	

