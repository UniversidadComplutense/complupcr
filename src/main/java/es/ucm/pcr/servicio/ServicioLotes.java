package es.ucm.pcr.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanBusquedaLotes;
import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.BeanLote;
@Service
public interface ServicioLotes {
	public List<BeanElemento> buscarEstadosLotes();

}
