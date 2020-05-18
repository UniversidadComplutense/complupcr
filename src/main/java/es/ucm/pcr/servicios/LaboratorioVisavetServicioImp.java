/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

package es.ucm.pcr.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanLaboratorioVisavet;
import es.ucm.pcr.beans.BusquedaRecepcionPlacasVisavetBean;
import es.ucm.pcr.beans.PlacaLaboratorioVisavetBean;
import es.ucm.pcr.modelo.orm.EstadoPlacaVisavet;
import es.ucm.pcr.modelo.orm.LaboratorioVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.repositorio.LaboratorioVisavetRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
import es.ucm.pcr.utilidades.Utilidades;

@Service
public class LaboratorioVisavetServicioImp implements LaboratorioVisavetServicio{
	
	@Autowired
	LaboratorioVisavetRepositorio laboratorioVisavetRepositorio;
	
	@Autowired
	PlacaVisavetRepositorio placaVisavetRepositorio;
	
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
	
	public List<BeanLaboratorioVisavet> listaLaboratoriosVisavetOrdenada() throws Exception{
		// cargo todos los laboratorioVisavets de BBDD
		List<BeanLaboratorioVisavet> listaLaboratorioVisavet = new ArrayList<BeanLaboratorioVisavet>();
		for (LaboratorioVisavet laboratorioVisavet: laboratorioVisavetRepositorio.findAll())
		{
			BeanLaboratorioVisavet beanLaboratorioVisavet = new BeanLaboratorioVisavet();
			beanLaboratorioVisavet = mapeoEntidadBeanLaboratorioVisavet(laboratorioVisavet);
			listaLaboratorioVisavet.add(beanLaboratorioVisavet);
		}
		//	Ordeno por ap1, ap2, nombre
		Collections.sort(listaLaboratorioVisavet);
		return listaLaboratorioVisavet;

	}
	
	public Map<Integer,String> mapaLaboratoriosVisavet (List<BeanLaboratorioVisavet> laboratoriosVisavet) throws Exception{
		Map<Integer, String> mapalaboratorioVisavet = new HashMap<Integer, String>();
		for (BeanLaboratorioVisavet laboratorioVisavet :laboratoriosVisavet)
		{
			mapalaboratorioVisavet.put(laboratorioVisavet.getId(), laboratorioVisavet.getNombre());
		}
		return mapalaboratorioVisavet;
	}
	
	public LaboratorioVisavet save (LaboratorioVisavet laboratorioVisavet) throws Exception{
		return laboratorioVisavetRepositorio.save(laboratorioVisavet);
	}
	
	public void deleteById (Integer idLaboratorioVisavet) throws Exception
	{
		laboratorioVisavetRepositorio.deleteById(idLaboratorioVisavet);
	}
	
	public Optional <LaboratorioVisavet> findById (Integer idLaboratorioVisavet) throws Exception{
			return laboratorioVisavetRepositorio.findById(idLaboratorioVisavet);
	}
	
	// JAVI para buscar placas Visavet e incorporarlas a 'BusquedaPlacasVisavetBean'
	@Override
	public Page<PlacaLaboratorioVisavetBean> buscarPlacas(BusquedaRecepcionPlacasVisavetBean criteriosBusqueda,
			Pageable pageable) throws Exception {
		
		List<PlacaLaboratorioVisavetBean> listaPlacasVisavetBean = new ArrayList<PlacaLaboratorioVisavetBean>();

		if (criteriosBusqueda.getFechaBusquedaInicio() != null) {
			
			if (criteriosBusqueda.getIdEstadoPlaca() == Estado.PLACAVISAVET_ENVIADA.getCodNum()) {
				criteriosBusqueda.setFechaEnviadaInicio(criteriosBusqueda.getFechaBusquedaInicio());
			} else {
				if (criteriosBusqueda.getIdEstadoPlaca() == Estado.PLACAVISAVET_RECIBIDA.getCodNum()) {
					criteriosBusqueda.setFechaRecepcionInicio(criteriosBusqueda.getFechaBusquedaInicio());
				}
			}
		}
		
		if (criteriosBusqueda.getFechaBusquedaFin() != null) {
			
			if (criteriosBusqueda.getIdEstadoPlaca() == Estado.PLACAVISAVET_ENVIADA.getCodNum()) {
				criteriosBusqueda.setFechaEnviadaFin(Utilidades.fechafinBuscador(criteriosBusqueda.getFechaBusquedaFin()));
			} else {
				if (criteriosBusqueda.getIdEstadoPlaca() == Estado.PLACAVISAVET_RECIBIDA.getCodNum()) {
					criteriosBusqueda.setFechaRecepcionFin(Utilidades.fechafinBuscador(criteriosBusqueda.getFechaBusquedaFin()));
				}
			}
		}
		
		// Si no se ha seleccionado el estado de la placa en la búsqueda, buscamos por los estados PLACAVISAVET_ENVIADA ó PLACAVISAVET_RECIBIDA
		if (criteriosBusqueda.getIdEstadoPlaca() == 0) {
			criteriosBusqueda.setEstadosBusqueda(Arrays.asList(Estado.PLACAVISAVET_ENVIADA.getCodNum(), Estado.PLACAVISAVET_RECIBIDA.getCodNum()));
		} else {
			criteriosBusqueda.setEstadosBusqueda(Arrays.asList(criteriosBusqueda.getIdEstadoPlaca()));
		}

		Page<PlacaVisavet> PagePlacasVisavet = placaVisavetRepositorio.findByParams(criteriosBusqueda, pageable); 

		for (PlacaVisavet placa : PagePlacasVisavet.getContent()) {
			listaPlacasVisavetBean.add(PlacaLaboratorioVisavetBean.modelToBean(placa));
		}		
		Page<PlacaLaboratorioVisavetBean> placasVisavet = new PageImpl<>(listaPlacasVisavetBean, pageable, PagePlacasVisavet.getTotalElements());		
		return placasVisavet;
	}
	
	
	// JAVI para buscar una placa Visavet e incorporarla a 'BusquedaPlacasVisavetBean'
	@Override
	@Transactional
	public PlacaLaboratorioVisavetBean buscarPlaca(Integer id) throws Exception {
		Optional<PlacaVisavet> placa = placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) {
			return PlacaLaboratorioVisavetBean.modelToBean(placa.get());
		}
		return new PlacaLaboratorioVisavetBean();
	}
	
	
	// JAVI para recepcionar una placa Visavet
	@Override
	@Transactional
	public boolean recepcionarPlaca(Integer id) throws Exception {				
		Optional<PlacaVisavet> placa = placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaVisavet().getId() == Estado.PLACAVISAVET_ENVIADA.getCodNum()) {
				placa.get().setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_RECIBIDA.getCodNum()));
				placa.get().setFechaRecepcionLaboratorioCentro(new Date());
				placaVisavetRepositorio.save(placa.get());
				
				// TODO Registrar en LOG placaVisavet recibida
				return true;
			}			
		}
		return false;
	}
	
	
	// JAVI para anular la recepción de una placa Visavet
	@Override
	@Transactional
	public boolean anularRecepcionarPlaca(Integer id) throws Exception {				
		Optional<PlacaVisavet> placa = placaVisavetRepositorio.findById(id);
		if (placa.isPresent()) {
			if (placa.get().getEstadoPlacaVisavet().getId() == Estado.PLACAVISAVET_RECIBIDA.getCodNum()) {
				placa.get().setEstadoPlacaVisavet(new EstadoPlacaVisavet(Estado.PLACAVISAVET_ENVIADA.getCodNum()));
				placa.get().setFechaRecepcionLaboratorioCentro(null);
				placaVisavetRepositorio.save(placa.get());
				
				// TODO Registrar en LOG placaVisavet anulada recepción 
				return true;
			}			
		}
		return false;
	}

	
	// JAVI para saber las placas Visavet combinadas en una placa de laboratorio
	@Override
	public List<PlacaLaboratorioVisavetBean> buscarPlacasPorIdPlacaLaboratorio(Integer idPlacaLaboratorio) throws Exception {
		
		Set<PlacaVisavet> placas = placaVisavetRepositorio.findByIdPlacaLaboratorio(idPlacaLaboratorio);		
		List<PlacaLaboratorioVisavetBean> placasVisavet = new ArrayList<PlacaLaboratorioVisavetBean>();
		for (PlacaVisavet placa : placas) {
			PlacaLaboratorioVisavetBean bean = new PlacaLaboratorioVisavetBean();
			bean = PlacaLaboratorioVisavetBean.modelToBean(placa);
			placasVisavet.add(bean);
		}		
		return placasVisavet;
	}
	
	@Override
	public Optional<LaboratorioVisavet> findByNombre(String nombre) {
		return laboratorioVisavetRepositorio.findByNombre(nombre);
	}
	
}
