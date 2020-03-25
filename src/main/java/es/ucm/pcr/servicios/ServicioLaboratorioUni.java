package es.ucm.pcr.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.LoteBean;
@Service
public interface ServicioLaboratorioUni {
	public Page<LoteBean> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable);

}