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
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.BeanEstado;
import es.ucm.pcr.beans.BeanEstado.Estado;
import es.ucm.pcr.beans.BeanEstado.TipoEstado;
import es.ucm.pcr.beans.BusquedaLotesBean;
import es.ucm.pcr.beans.BeanElemento;
import es.ucm.pcr.beans.LoteBeanPlacaVisavet;
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
