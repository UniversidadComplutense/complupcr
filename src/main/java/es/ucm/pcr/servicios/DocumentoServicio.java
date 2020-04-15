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

}
