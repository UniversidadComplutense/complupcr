package es.ucm.pcr.servicios;

import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;

@Service
public class LaboratorioVisavetServicioImp implements LaboratorioVisavetServicio{
	
	
	public LaboratorioVisavet mapeoBeanEntidadLaboratorioVisavet(BeanLaboratorioVisavet beanLaboratorioVisavet) throws Exception {
		LaboratorioVisavet laboratorioVisavet = new LaboratorioVisavet();
		
		laboratorioVisavet.setId(beanLaboratorioVisavet.getId());
		laboratorioVisavet.setNombre(beanLaboratorioVisavet.getNombre());	
		laboratorioVisavet.setCapacidad(beanLaboratorioVisavet.getCapacidad());
		laboratorioVisavet.setDocumentos(beanLaboratorioVisavet.getDocumentos());
		laboratorioVisavet.setPlacaVisavets(beanLaboratorioVisavet.getPlacaVisavets());
		
		return laboratorioVisavet;
	}

	public BeanLaboratorioVisavet mapeoEntidadBeanLaboratorioVisavet(LaboratorioVisavet laboratorioVisavet) throws Exception{
		BeanLaboratorioVisavet beanLaboratorioVisavet = new BeanLaboratorioVisavet();
		
		beanLaboratorioVisavet.setId(laboratorioVisavet.getId());
		beanLaboratorioVisavet.setNombre(laboratorioVisavet.getNombre());
		beanLaboratorioVisavet.setCapacidad(laboratorioVisavet.getCapacidad());
		beanLaboratorioVisavet.setDocumentos(laboratorioVisavet.getDocumentos());
		beanLaboratorioVisavet.setOcupacion(laboratorioVisavet.getOcupacion());
		beanLaboratorioVisavet.setPlacaVisavets(laboratorioVisavet.getPlacaVisavets());
		
		return beanLaboratorioVisavet;		
	}
	

}
