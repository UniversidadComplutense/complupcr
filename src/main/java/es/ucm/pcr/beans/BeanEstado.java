package es.ucm.pcr.beans;


public class BeanEstado {
	
	
	public enum TipoEstado {EstadoLote}; // estan solo los de los lotes habria que incluir los estados del resto
	
	public enum Estado {PendienteEnvio (1, "PendienteEnvio"), Enviado (2, "Enviado"), Recibido (3, "Recibido");

		public int codNum;		
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
						this.setEstado(Estado.PendienteEnvio);
						break;
					}
					case 2: {
						this.setEstado(Estado.Enviado);
						break;
					}
					case 3: {
						this.setEstado(Estado.Recibido);
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