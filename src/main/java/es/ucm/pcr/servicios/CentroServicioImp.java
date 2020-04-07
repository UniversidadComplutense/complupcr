package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanCentro;
import es.ucm.pcr.modelo.orm.Centro;
import es.ucm.pcr.repositorio.CentroRepositorio;

@Service
public class CentroServicioImp implements CentroServicio{
	
	@Autowired
	CentroRepositorio centroRepositorio;
	
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
	
	public List<BeanCentro> listaCentrosOrdenada() throws Exception{
		// cargo todos los rols de BBDD
		List<BeanCentro> listaCentros = new ArrayList<BeanCentro>();
		for (Centro centro: centroRepositorio.findAll())
		{
			BeanCentro beanCentro = new BeanCentro();
			beanCentro = mapeoEntidadBeanCentro(centro);
			listaCentros.add(beanCentro);
//			
//			
//			listaCentros.add( 
//					new BeanCentro(
//							centro.getId(), 
//							centro.getNombre(), 
//							centro.getCodCentro(), 
//							centro.getTelefono(), 
//							centro.getEmail(), 
//							centro.getDireccion(),
//							centro.getUsuarios(),
//							centro.getMuestras(),
//							centro.getDocumentos(),
//							centro.getLotes(),
//							"L")
//			);
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaCentros);
		return listaCentros;
	}
	
	public Map<Integer,String> mapaCentros (List<BeanCentro> centros) throws Exception
	{
		Map<Integer, String> mapaCentros = new HashMap<Integer, String>();
		for (BeanCentro centro : centros)
		{
			mapaCentros.put(centro.getId(), centro.getNombre());
		}
		return mapaCentros;
	}
	
	public void guardarCentro (Centro centro) throws Exception{
		centroRepositorio.save(centro);
	}
	
	public void BorrarCentro (Integer idCentro) throws Exception{
		centroRepositorio.deleteById(idCentro);
	}
	
	public Optional<Centro> buscarCentroPorId (Integer idCentro) throws Exception{
		return centroRepositorio.findById(idCentro);
	}
	
}
