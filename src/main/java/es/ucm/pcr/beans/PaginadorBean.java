package es.ucm.pcr.beans;

public class PaginadorBean {

	private Integer numeroPaginas;
	private Integer paginaActual;
	private Long totalElementos;
	private String urlPaginacion;

	public PaginadorBean() {
		super();
	}

	public PaginadorBean(Integer numeroPaginas, Integer paginaActual, Long totalElementos, String urlPaginacion) {
		super();
		this.numeroPaginas = numeroPaginas;
		this.paginaActual = paginaActual;
		this.totalElementos = totalElementos;
		this.urlPaginacion = urlPaginacion;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public Integer getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(Integer paginaActual) {
		this.paginaActual = paginaActual;
	}

	public Long getTotalElementos() {
		return totalElementos;
	}

	public void setTotalElementos(Long totalElementos) {
		this.totalElementos = totalElementos;
	}

	public String getUrlPaginacion() {
		return urlPaginacion;
	}

	public void setUrlPaginacion(String urlPaginacion) {
		this.urlPaginacion = urlPaginacion;
	}

}
