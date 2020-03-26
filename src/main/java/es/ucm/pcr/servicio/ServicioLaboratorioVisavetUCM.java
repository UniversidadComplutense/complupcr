package es.ucm.pcr.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanBusquedaLotes;
import es.ucm.pcr.beans.BeanLote;
@Service
public interface ServicioLaboratorioVisavetUCM {
	public Page<BeanLote> buscarLotes(BeanBusquedaLotes busquedaLotes, Pageable pageable);

}
