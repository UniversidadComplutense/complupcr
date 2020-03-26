package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanBusquedaLotes;
import es.ucm.pcr.beans.BeanLote;
@Service
public class ServicioLaboratorioVisavetUCMImpl implements ServicioLaboratorioVisavetUCM{
	public Page<BeanLote> buscarLotes(BeanBusquedaLotes busquedaLotes, Pageable pageable){
		return null;
	}

}
