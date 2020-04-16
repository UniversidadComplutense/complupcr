package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ElementoDocumentacionBean {

	public static Integer TIPO_ELEMENTO_MUESTRA = 1;
	public static Integer TIPO_ELEMENTO_PLACA_LABORATORIO = 2;
	public static Integer TIPO_ELEMENTO_PLACA_VISAVET = 3;

	private Integer id;
	private String descripcion;
	private Integer tipoElemento;
	private List<DocumentoBean> documentos;
	private MultipartFile file;
	private String hoja; //en la carga de resultados del analista (excel) identificamos la hoja y la columna del excel del analista
	private String columna;
	private String columnaRef;
	private String tipo; //el tipo sera "RES" si es un excel de resultados
	private Integer codiUrl;
	private String urlVolver;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(Integer tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<DocumentoBean> getDocumentos() {
		if (documentos == null) {
			documentos = new ArrayList<DocumentoBean>();
		}
		return documentos;
	}

	public void setDocumentos(List<DocumentoBean> documentos) {
		this.documentos = documentos;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
		
	public String getHoja() {
		return hoja;
	}

	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	public String getColumna() {
		return columna;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}
	
	/**
	 * @return the columnaRef
	 */
	public String getColumnaRef() {
		return columnaRef;
	}

	/**
	 * @param columnaRef the columnaRef to set
	 */
	public void setColumnaRef(String columnaRef) {
		this.columnaRef = columnaRef;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCodiUrl() {
		return codiUrl;
	}

	public void setCodiUrl(Integer codiUrl) {
		this.codiUrl = codiUrl;
	}

	public String getUrlVolver() {
		return urlVolver;
	}

	public void setUrlVolver(String urlVolver) {
		this.urlVolver = urlVolver;
	}

}
