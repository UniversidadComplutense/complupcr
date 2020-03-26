package es.ucm.pcr.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BeanBusquedaLotes;
import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanLote;
@Service
public class ServicioLotesImpl implements ServicioLotes{
	public List<BeanElemento> buscarEstadosLotes(){
		
		// para probar posteriormente hay que sacarlo de bbdd
		List<BeanElemento> listaEstados= new ArrayList();
		BeanElemento beanEstado= new BeanElemento();
		
		beanEstado.setCodigo(1);
		beanEstado.setDescripcion("Enviado");
		listaEstados.add(beanEstado);
		BeanElemento beanEstado2= new BeanElemento();
        beanEstado.setCodigo(2);
		beanEstado.setDescripcion("Pendiente Envio");
		
		listaEstados.add(beanEstado2);
		BeanElemento beanEstado3= new BeanElemento();
        beanEstado.setCodigo(3);
		beanEstado.setDescripcion("Recibido");
		
		
		listaEstados.add(beanEstado3);
		System.out.println(listaEstados.size());
		return listaEstados;
	}

}
