package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
import es.ucm.pcr.beans.LoteBusquedaBean;
import es.ucm.pcr.beans.LoteListadoBean;
import es.ucm.pcr.beans.LotePlacaVisavetBean;
import es.ucm.pcr.beans.MuestraBeanLaboratorioVisavet;
import es.ucm.pcr.beans.MuestraListadoBean;

@Service
public class ServicioLaboratorioVisavetUCMImpl implements ServicioLaboratorioVisavetUCM{
	@Autowired
    LoteServicio loteServicio;
	public Page<LoteBeanPlacaVisavet> buscarLotes(BusquedaLotesBean busquedaLotes, Pageable pageable){
		LoteBusquedaBean loteBusquedaBean= new LoteBusquedaBean();
		loteBusquedaBean.setIdCentro(busquedaLotes.getIdCentro());
	// se refiere a fecha recepcion
		loteBusquedaBean.setFechaEnvioFin(busquedaLotes.getFechaEntrada());
		loteBusquedaBean.setNumLote(busquedaLotes.getNumLote());
		// por muestra aun no esta
		loteBusquedaBean.setIdEstado(busquedaLotes.getCodNumEstadoSeleccionado());
		loteBusquedaBean.setIdLaboratorio("");
		Page <LoteListadoBean> pageResultados=loteServicio.findLoteByParam(loteBusquedaBean,pageable);
		List<LoteBeanPlacaVisavet> listaBeans = new ArrayList<LoteBeanPlacaVisavet>();
		if (pageResultados.getTotalElements() > 0) {
			
			for (LoteListadoBean unResultado: pageResultados.getContent()) {
				LoteBeanPlacaVisavet lotePlaca= new LoteBeanPlacaVisavet();
				lotePlaca.setId(unResultado.getId());
				lotePlaca.setNumLote(unResultado.getNumLote());
				lotePlaca.setCentroProcedencia(unResultado.getCentroBean().getNombre());
				// necesito el BeanEstado
				lotePlaca.setEstado(unResultado.getBeanEstado());
				//lotePlaca.setEstado(unResultado.);
				List<MuestraBeanLaboratorioVisavet>  listaMuestrasVisavet= null;
				for(MuestraListadoBean muestra : unResultado.getMuestras()) {
			     if (listaMuestrasVisavet == null) new ArrayList();
			     MuestraBeanLaboratorioVisavet muestraBeanLaboratorioVisavet = new MuestraBeanLaboratorioVisavet();
			     muestraBeanLaboratorioVisavet.setId(muestra.getId());
			     muestraBeanLaboratorioVisavet.setEtiqueta(muestra.getEtiquetaMuestra());
			     muestraBeanLaboratorioVisavet.setTipoMuestra(muestra.getTipoMuestra());
			     muestraBeanLaboratorioVisavet.setReferenciaInterna(muestra.getRefInternaMuestra());
			     listaMuestrasVisavet.add(muestraBeanLaboratorioVisavet);
				}
				lotePlaca.setListaMuestras(listaMuestrasVisavet);
				listaBeans.add(lotePlaca);
			}
		
			
		}
		Page<LoteBeanPlacaVisavet> pageLote=new PageImpl<LoteBeanPlacaVisavet>(listaBeans, pageable,
				pageResultados.getTotalElements());
		return pageLote;
	}

}
