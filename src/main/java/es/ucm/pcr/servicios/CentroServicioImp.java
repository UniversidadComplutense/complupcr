package es.ucm.pcr.servicios;

import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.modelo.orm.Centro;

@Service
public class CentroServicioImp implements CentroServicio{
	
	public Centro mapeoBeanEntidadCentro(BeanCentro beanCentro) throws Exception{
		Centro centro = new Centro();
		
		centro.setId(beanCentro.getId());
		centro.setNombre(beanCentro.getNombre());	
		centro.setDocumentos(beanCentro.getDocumentos());
		centro.setCodCentro(beanCentro.getCodCentro());
		centro.setDireccion(beanCentro.getDireccion());
		centro.setEmail(beanCentro.getEmail());
		centro.setLotes(beanCentro.getLotes());
		centro.setMuestras(beanCentro.getMuestras());
		centro.setUsuarios(beanCentro.getUsuarios());
		centro.setTelefono(beanCentro.getTelefono());
	
		return centro;
	}

	public BeanCentro mapeoEntidadBeanCentro(Centro centro) throws Exception{
		BeanCentro beanCentro = new BeanCentro();
		
		beanCentro.setId(centro.getId());
		beanCentro.setNombre(centro.getNombre());
		beanCentro.setDocumentos(centro.getDocumentos());
		beanCentro.setCodCentro(centro.getCodCentro());
		beanCentro.setDireccion(centro.getDireccion());
		beanCentro.setDocumentos(centro.getDocumentos());
		beanCentro.setEmail(centro.getEmail());
		beanCentro.setLotes(centro.getLotes());
		beanCentro.setMuestras(centro.getMuestras());
		beanCentro.setTelefono(centro.getTelefono());
		
		
		
		return beanCentro;	
	}
	
}
