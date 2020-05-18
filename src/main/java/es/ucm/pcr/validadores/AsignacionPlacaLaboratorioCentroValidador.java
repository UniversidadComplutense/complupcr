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

package es.ucm.pcr.validadores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.beans.GuardarAsignacionPlacaLaboratorioCentroBean;
import es.ucm.pcr.beans.PlacaLaboratorioCentroAsignacionesBean;
import es.ucm.pcr.servicios.LaboratorioCentroServicio;
import es.ucm.pcr.utilidades.Utilidades;


@Component
public class AsignacionPlacaLaboratorioCentroValidador implements Validator {
	
	   	
	
	//usamos el servicio, que pasamos por parametro al constructor del validador 
	private LaboratorioCentroServicio laboratorioCentroServicio; 
	
		
	public AsignacionPlacaLaboratorioCentroValidador(LaboratorioCentroServicio laboratorioCentroServ, HttpSession session) {
		this.laboratorioCentroServicio = laboratorioCentroServ;
		
	}
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return GuardarAsignacionPlacaLaboratorioCentroBean.class.isAssignableFrom(clazz);	
	}
	
	@Override
	public  void validate(Object target, Errors errors) {		
		GuardarAsignacionPlacaLaboratorioCentroBean guardarAsignacionPlacaLaboratorioCentroBean = (GuardarAsignacionPlacaLaboratorioCentroBean)target;
		
		
		// valida el num total de analistas (lo que ya tenía asignados + los marcados para asignar)
		validateNumeroTotalAnalistasAsignados(guardarAsignacionPlacaLaboratorioCentroBean, errors);
	}		
		
	/**
	 * Valida que el numero de analistas asignados y marcados para asignar sea el numAnalistas del properties
	 * @param guardarAsignacionPlacaLaboratorioCentroBean
	 * @param errors
	 */
	private void validateNumeroTotalAnalistasAsignados(GuardarAsignacionPlacaLaboratorioCentroBean guardarAsignacionPlacaLaboratorioCentroBean, Errors errors) {
		Integer numAnalistasPermitidos = guardarAsignacionPlacaLaboratorioCentroBean.getNumAnalistasPermitidos();
		System.out.println("el num analistas total permitidos por la aplicacion es: "+ numAnalistasPermitidos);
		Integer idPlaca = guardarAsignacionPlacaLaboratorioCentroBean.getIdPlaca();
				
		//recuperamos la placa con sus asignaciones
		PlacaLaboratorioCentroAsignacionesBean placaLaboratorioCentroAsignacionesBean=laboratorioCentroServicio.buscarPlacaAsignaciones(idPlaca);
		//calculamos el numero de analistas asignados
		//recorremos todos las listas de los analistas asignados a la placa y los sumamos
		Integer numAnalistasLabAsignados = placaLaboratorioCentroAsignacionesBean.getBeanAnalisisPlaca().getBeanListaAsignaciones().getListaAnalistasLab().size();
		Integer numAnalistasVolAsignados = placaLaboratorioCentroAsignacionesBean.getBeanAnalisisPlaca().getBeanListaAsignaciones().getListaAnalistasVol().size();
		Integer numAnalistasVolSinLabCentroAsignados = placaLaboratorioCentroAsignacionesBean.getBeanAnalisisPlaca().getBeanListaAsignaciones().getListaAnalistasVolSinLabCentro().size();
		Integer numTotalAnalistasAsignados = numAnalistasLabAsignados + numAnalistasVolAsignados + numAnalistasVolSinLabCentroAsignados;
		System.out.println("el num analistas asignados es: "+ numTotalAnalistasAsignados);
		//calculamos el numero de analistas por asignar marcados en el formulario
		Integer numAnalistasLabNuevos = guardarAsignacionPlacaLaboratorioCentroBean.getListaIdsAnalistasLabSeleccionados().size();
		Integer numAnalistasVolNuevos = guardarAsignacionPlacaLaboratorioCentroBean.getListaIdsAnalistasVolSeleccionados().size();
		Integer numAnalistasVolSinLabCentroNuevos = guardarAsignacionPlacaLaboratorioCentroBean.getListaIdsVolSinLabCentroSeleccionados().size();
		Integer numTotalAnalistasNuevosPorAsignar = numAnalistasLabNuevos + numAnalistasVolNuevos + numAnalistasVolSinLabCentroNuevos;
		System.out.println("el num analistas por asignar es: "+ numTotalAnalistasNuevosPorAsignar);
		//si la suma de todos es diferente al numero minimo y maximo de analistas definido en el properties de la aplicacion, mostramos error de validacion y no dejamos continuar
		Integer totalAnalistasAsignadosYAnalistasPorAsignar = numTotalAnalistasAsignados + numTotalAnalistasNuevosPorAsignar;
		System.out.println("el TotalAnalistasAsignadosYAnalistasPorAsignar es: "+ totalAnalistasAsignadosYAnalistasPorAsignar);
		if(!totalAnalistasAsignadosYAnalistasPorAsignar.equals(numAnalistasPermitidos)) {
			errors.rejectValue("numAnalistasPermitidos", "field.numAnalistasPermitidos.invalid", "El numero total de analistas debe ser " + numAnalistasPermitidos);
		
			//errors.rejectValue("idPlaca", "campo.invalid", "MAAAAALL");
			//errors.rejectValue("idPlaca", "field.idPlaca.invalid", "El numero total de analistas asignados y analistas por asignar debe ser " + numAnalistasPermitidos);		
		}
	}
}
