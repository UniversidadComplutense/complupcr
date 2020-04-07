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

}
