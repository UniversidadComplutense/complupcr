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

import java.util.List;

import org.springframework.data.repository.query.Param;

import es.ucm.pcr.beans.DocumentoBean;
import es.ucm.pcr.beans.DocumentoBusquedaBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;

public interface DocumentoServicio {
	
	/**
	 * Elemento y documentos muestra
	 * @param idMuestra
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosMuestra(Integer idMuestra);
	
	/**
	 * Elemento y documentos placa visavet
	 * @param idPlacaVisavet
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosPlacaVisavet(Integer idPlacaVisavet);
	
	/**
	 * Elemento y documentos placa laboratorio que son de un tipo
	 * @param idPlacaLaboratorio
	 * @param tipo
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosPlacaVisavetConTipo(Integer idPlacaLaboratorio, String tipo);
	
	/**
	 * Elemento y documentos placa laboratorio
	 * @param idPlacaLaboratorio
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosPlacaLaboratorio(Integer idPlacaLaboratorio);
	
	
	/**
	 * Elemento y documentos placa laboratorio que son de un tipo
	 * @param idPlacaLaboratorio
	 * @param tipo
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosPlacaLaboratorioConTipo(Integer idPlacaLaboratorio, String tipo);
	
	/**
	 * Busqueda de documentos por criterios
	 * @param params
	 * @return
	 */
	public List<DocumentoBean> findByParams(@Param("params") DocumentoBusquedaBean params);
		
	/**
	 * Guarda un documento
	 * @param documento
	 */
	public void guardar(ElementoDocumentacionBean documento);
	
	/**
	 * Borrar documento por identificador de documento
	 * @param id identificador de documento
	 * @return true si lo ha borrado, false en caso contrario
	 */
	public boolean borrar(Integer id);

	
	/**
	 * Elemento y documentos placa laboratorio y placas Visavet
	 * @param idPlacaLaboratorio
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosPlacaLaboratorioYPlacasVisavet(Integer idPlacaLaboratorio);

	/**
	 * Busqueda de documentos de una placa de laboratorio y sus placas Visavet por criterios
	 * @param params
	 * @return
	 */
	public List<DocumentoBean> findDocumentosPlacaLaboratorioYPlacasVisavet(DocumentoBusquedaBean params);
	
	
	/**
	 * Elemento y documentos subidos por el usuario que son de un tipo
	 * @param idUsuario
	 * @param tipo
	 * @return
	 */
	public ElementoDocumentacionBean obtenerDocumentosUsuarioConTipo(Integer idUsuario, String tipo); 

}
