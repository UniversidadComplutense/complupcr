package es.ucm.pcr.servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.pcr.beans.DocumentoBean;
import es.ucm.pcr.beans.DocumentoBusquedaBean;
import es.ucm.pcr.beans.ElementoDocumentacionBean;
import es.ucm.pcr.modelo.orm.Documento;
import es.ucm.pcr.modelo.orm.Muestra;
import es.ucm.pcr.modelo.orm.PlacaLaboratorio;
import es.ucm.pcr.modelo.orm.PlacaVisavet;
import es.ucm.pcr.modelo.orm.PlacaVisavetPlacaLaboratorio;
import es.ucm.pcr.modelo.orm.Usuario;
import es.ucm.pcr.repositorio.DocumentoRepositorio;
import es.ucm.pcr.repositorio.MuestraRepositorio;
import es.ucm.pcr.repositorio.PlacaLaboratorioRepositorio;
import es.ucm.pcr.repositorio.PlacaVisavetRepositorio;
import es.ucm.pcr.utilidades.Utilidades;

@Service
public class DocumentoServicioImpl implements DocumentoServicio {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DocumentoServicioImpl.class);

	@Autowired
	private DocumentoRepositorio documentoRepositorio;
	
	@Autowired
	private MuestraRepositorio muestraRepositorio;
	
	@Autowired
	private PlacaVisavetRepositorio placaVisavetRepositorio;
	
	@Autowired
	private PlacaLaboratorioRepositorio placaLaboratorioRepositorio;
	
	@Autowired
	private SesionServicio sesionServicio;
	
	@Override
	public List<DocumentoBean> findByParams(DocumentoBusquedaBean params) {
		List<DocumentoBean> documentoBeanList = new ArrayList<DocumentoBean>();
		List<Documento> documentoList = documentoRepositorio.findByParams(params);
		DocumentoBean docBean = new DocumentoBean();
		for (Documento d : documentoList) {
			docBean = new DocumentoBean();
			docBean.setId(d.getId());
			docBean.setNombreDocumento(d.getNombre());
			docBean.setFichero(d.getFichero());
			docBean.setTamanioDocumento(d.getTamanoDocumento()!= null ? Utilidades.fileSizeFormat(d.getTamanoDocumento()) : "0 MB");
			Usuario usuario = d.getUsuario();
			docBean.setDescripcionUsuario(usuario.getNombre() + " " + usuario.getApellido1() + (usuario.getApellido2() != null ? usuario.getApellido2() : ""));
			docBean.setIdUsuario(d.getUsuario().getId());
			documentoBeanList.add(docBean);
		}
		return documentoBeanList;
	}
	
	@Override
	public List<DocumentoBean> findDocumentosPlacaLaboratorioYPlacasVisavet(DocumentoBusquedaBean params) {
		List<DocumentoBean> documentoBeanList = new ArrayList<DocumentoBean>();
		List<Documento> documentoList = documentoRepositorio.findDocuPlacaLabYPlacaVis(params);
		DocumentoBean docBean = new DocumentoBean();
		for (Documento d : documentoList) {
			docBean = new DocumentoBean();
			docBean.setId(d.getId());
			docBean.setNombreDocumento(d.getNombre());
			docBean.setFichero(d.getFichero());
			docBean.setTamanioDocumento(d.getTamanoDocumento()!= null ? Utilidades.fileSizeFormat(d.getTamanoDocumento()) : "0 MB");
			Usuario usuario = d.getUsuario();
			docBean.setDescripcionUsuario(usuario.getNombre() + " " + usuario.getApellido1() + (usuario.getApellido2() != null ? usuario.getApellido2() : ""));
			docBean.setIdUsuario(d.getUsuario().getId());
			documentoBeanList.add(docBean);
		}
		return documentoBeanList;
	}
	
	@Override
	public ElementoDocumentacionBean obtenerDocumentosMuestra(Integer idMuestra) {
		ElementoDocumentacionBean elDoc = new ElementoDocumentacionBean();
		Muestra muestra = Optional.of(muestraRepositorio.findById(idMuestra).get()).orElse(null);
		elDoc.setId(muestra.getId());
		elDoc.setDescripcion(muestra.getEtiqueta() + " " + muestra.getPaciente().getNombrePaciente());
		elDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_MUESTRA);
		
		DocumentoBusquedaBean docBusquedaBean = new DocumentoBusquedaBean();
		docBusquedaBean.setIdMuestra(idMuestra);
		List<DocumentoBean> docsMuestra = findByParams(docBusquedaBean);
		elDoc.setDocumentos(docsMuestra);
		
		return elDoc;
	}
	
	@Override
	public ElementoDocumentacionBean obtenerDocumentosPlacaVisavet(Integer idPlacaVisavet) {
		ElementoDocumentacionBean elDoc = new ElementoDocumentacionBean();
		PlacaVisavet placaVisavet = Optional.of(placaVisavetRepositorio.findById(idPlacaVisavet).get()).orElse(null);
		elDoc.setId(placaVisavet.getId());
		// TODO - ESTABLECER DESCRIPCION
		elDoc.setDescripcion(placaVisavet.getId().toString());
		elDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_VISAVET);
		
		DocumentoBusquedaBean docBusquedaBean = new DocumentoBusquedaBean();
		docBusquedaBean.setIdPlacaVisavet(idPlacaVisavet);
		List<DocumentoBean> docsMuestra = findByParams(docBusquedaBean);
		elDoc.setDocumentos(docsMuestra);
		
		return elDoc;
	}
	
	@Override
	public ElementoDocumentacionBean obtenerDocumentosPlacaVisavetConTipo(Integer idPlacaVisavet, String tipo) {
		ElementoDocumentacionBean elDoc = new ElementoDocumentacionBean();
		PlacaVisavet placaVisavet = Optional.of(placaVisavetRepositorio.findById(idPlacaVisavet).get()).orElse(null);
		elDoc.setId(placaVisavet.getId());
		// TODO - ESTABLECER DESCRIPCION (pongo el id de la placa de momento, podemos poner lo que querais)
		elDoc.setDescripcion("Placa "+placaVisavet.getId());
		elDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_VISAVET);
		elDoc.setTipo(tipo); //el tipo del doc que guardareemos será del mismo tipo que los doc que estamos buscando
		
		DocumentoBusquedaBean docBusquedaBean = new DocumentoBusquedaBean();
		docBusquedaBean.setIdPlacaVisavet(idPlacaVisavet);
		docBusquedaBean.setTipo(tipo); //buscamos los documentos de ese tipo
		List<DocumentoBean> docsMuestra = findByParams(docBusquedaBean);
		elDoc.setDocumentos(docsMuestra);
		
		return elDoc;
	}
	
	@Override
	public ElementoDocumentacionBean obtenerDocumentosPlacaLaboratorio(Integer idPlacaLaboratorio) {
		ElementoDocumentacionBean elDoc = new ElementoDocumentacionBean();
		PlacaLaboratorio placaLaboratorio = Optional.of(placaLaboratorioRepositorio.findById(idPlacaLaboratorio).get()).orElse(null);		
		elDoc.setId(placaLaboratorio.getId());
		// TODO - ESTABLECER DESCRIPCION (Diana: pongo el id de la placa de momento, podemos poner lo que querais)
		elDoc.setDescripcion("Placa " + placaLaboratorio.getId());
		elDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_LABORATORIO);
		
		DocumentoBusquedaBean docBusquedaBean = new DocumentoBusquedaBean();
		
		docBusquedaBean.setIdPlacaLaboratorio(idPlacaLaboratorio);

		List<DocumentoBean> docsMuestra = findByParams(docBusquedaBean);
		elDoc.setDocumentos(docsMuestra);
		
		return elDoc;
	}
	
	@Override
	public ElementoDocumentacionBean obtenerDocumentosPlacaLaboratorioYPlacasVisavet(Integer idPlacaLaboratorio) {
		ElementoDocumentacionBean elDoc = new ElementoDocumentacionBean();
		PlacaLaboratorio placaLaboratorio = Optional.of(placaLaboratorioRepositorio.findById(idPlacaLaboratorio).get()).orElse(null);		
		elDoc.setId(placaLaboratorio.getId());
		// TODO - ESTABLECER DESCRIPCION (Diana: pongo el id de la placa de momento, podemos poner lo que querais)
		elDoc.setDescripcion("Placa " + placaLaboratorio.getId());
		elDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_LABORATORIO);
		
		DocumentoBusquedaBean docBusquedaBean = new DocumentoBusquedaBean();
		
		docBusquedaBean.setIdPlacaLaboratorio(idPlacaLaboratorio);
		
		// Documento(s) asociado(s) a las placa(s) Visavet que forman parte de la placa de laboratorio
		List<Integer> idsPlacasVisavet = new ArrayList<Integer>();
		for (PlacaVisavetPlacaLaboratorio placaVisavetPlacaLaboratorio : placaLaboratorio.getPlacaVisavetPlacaLaboratorios()){
			idsPlacasVisavet.add(placaVisavetPlacaLaboratorio.getPlacaVisavet().getId());
		}
		docBusquedaBean.setPlacasVisavet(idsPlacasVisavet);		
		//
		List<DocumentoBean> docsMuestra = findDocumentosPlacaLaboratorioYPlacasVisavet(docBusquedaBean);
		elDoc.setDocumentos(docsMuestra);
		
		return elDoc;
	}
	
	@Override
	public ElementoDocumentacionBean obtenerDocumentosPlacaLaboratorioConTipo(Integer idPlacaLaboratorio, String tipo) {
		ElementoDocumentacionBean elDoc = new ElementoDocumentacionBean();
		PlacaLaboratorio placaLaboratorio = Optional.of(placaLaboratorioRepositorio.findById(idPlacaLaboratorio).get()).orElse(null);		
		elDoc.setId(placaLaboratorio.getId());
		// TODO - ESTABLECER DESCRIPCION (Diana: pongo el id de la placa de momento, podemos poner lo que querais)
		elDoc.setDescripcion("Placa " + placaLaboratorio.getId());
		elDoc.setTipoElemento(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_LABORATORIO);
		elDoc.setTipo(tipo); //el tipo del doc que guardareemos será del mismo tipo que los doc que estamos buscando
		
		DocumentoBusquedaBean docBusquedaBean = new DocumentoBusquedaBean();
		docBusquedaBean.setIdPlacaLaboratorio(idPlacaLaboratorio);
		docBusquedaBean.setTipo(tipo); //buscamos los documentos de ese tipo
		List<DocumentoBean> docsMuestra = findByParams(docBusquedaBean);
		elDoc.setDocumentos(docsMuestra);
		
		return elDoc;
	}
	
	@Override
	public void guardar(ElementoDocumentacionBean elementoDocumentoBean) {
		
		try {
			Documento documento = new Documento();
			documento.setFichero(elementoDocumentoBean.getFile().getBytes());
			documento.setFechaDocumento(new Date());
			documento.setNombre(elementoDocumentoBean.getFile().getOriginalFilename());
			documento.setTamanoDocumento(Long.valueOf(elementoDocumentoBean.getFile().getSize()).intValue());
			documento.setTipo(elementoDocumentoBean.getTipo());
			documento.setUsuario(sesionServicio.getUsuario());
			
			// se establece la muestra al documento
			if (elementoDocumentoBean.getTipoElemento().equals(ElementoDocumentacionBean.TIPO_ELEMENTO_MUESTRA)) {
				documento.setMuestra(new Muestra(elementoDocumentoBean.getId()));
			}
			
			// se establece la placa laboratorio al documento
			if (elementoDocumentoBean.getTipoElemento().equals(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_LABORATORIO)) {
				documento.setPlacaLaboratorio(new PlacaLaboratorio(elementoDocumentoBean.getId()));
			}
			
			// se establece la placa visavet al documento
			if (elementoDocumentoBean.getTipoElemento().equals(ElementoDocumentacionBean.TIPO_ELEMENTO_PLACA_VISAVET)) {
				documento.setPlacaVisavet(new PlacaVisavet(elementoDocumentoBean.getId()));
			}							
			documentoRepositorio.save(documento);						
		} catch (IOException e) {
			log.error("ERROR:: guardar " + e);
		}		
	}

	@Override
	public boolean borrar(Integer id) {
		try {
			documentoRepositorio.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error("ERROR:: borrar " + e);
			return false;
		}
	}
	
	
}
