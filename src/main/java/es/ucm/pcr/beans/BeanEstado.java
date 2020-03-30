package es.ucm.pcr.beans;


public class BeanEstado {
	
	
	public enum TipoEstado {EstadoLote}; // estan solo los de los lotes habria que incluir los estados del resto
	
	public enum Estado {INICIADO (1, "Iniciado"), ASIGNADO_CENTRO_ANALISIS(2, "Asignado centro análisis"), 
							ENVIADO_CENTRO_ANALISIS (3, "Enviado centro análisis"), RECIBIDO_CENTRO_ANALISIS (4, "Recibido centro análisis"), PROCESADO_CENTRO_ANALISIS(5, "Procesado centro análisis");

		private int codNum;		
		public String descripcion;
		
		
		private Estado(){
			
		}
		
		private Estado (int codNum, String descripcion) {
			this.codNum = codNum;
			this.descripcion = descripcion;
		}
		
		
	
		
		public int getCodNum() {
			return codNum;
		}

		
		public String getDescripcion() {
			return descripcion;
		}
	
	}

	private TipoEstado tipoEstado;
	private Estado estado;

	
	public BeanEstado() {

	}

	public BeanEstado(TipoEstado tipoEstado, Estado estado) {
		this.tipoEstado = tipoEstado;
		this.estado = estado;
	}


	public TipoEstado getTipoEstado() {
		return tipoEstado;
	}


	public void setTipoEstado(TipoEstado tipoEstado) {
		this.tipoEstado = tipoEstado;
	}


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
	
	public BeanEstado asignarTipoEstadoYCodNum(TipoEstado tipoEstado, int codNumEstado){		
		switch (tipoEstado) {
			case EstadoLote: {
				this.setTipoEstado(TipoEstado.EstadoLote);
				switch (codNumEstado) {				
					case 1: {
						this.setEstado(Estado.INICIADO);
						break;
					}
					case 2: {
						this.setEstado(Estado.ASIGNADO_CENTRO_ANALISIS);
						break;
					}
					case 3: {
						this.setEstado(Estado.ENVIADO_CENTRO_ANALISIS);
						break;
					}
					case 4: {
						this.setEstado(Estado.RECIBIDO_CENTRO_ANALISIS);
						break;
					}
					case 5: {
						this.setEstado(Estado.PROCESADO_CENTRO_ANALISIS);
						break;
					}
				}
			break;
			}			
		}			
		
			
		return this;
	}

	

	@Override
	public String toString() {
		return "BeanEstado [tipoEstado=" + tipoEstado + ", estado=" + estado + "]";
	}


	
	
	

}