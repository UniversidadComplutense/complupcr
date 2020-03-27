package es.ucm.pcr.beans;


public class EstadoBean {
	
	
	public enum TipoEstado {EstadoLote, EstadoPlacaLabCentro}; 
	
	public enum Estado {PendienteEnvio (1, "PendienteEnvio"), Enviado (2, "Enviado"), Recibido (3, "Recibido"),  		// ESTADOS LOTES
						
						IniciadaConMuestras (1, "Iniciada con muestras"), PreparadaParaPCR (2, "Preparada para PCR"), 	// ESTADOS PLACA LABORATORIO CENTRO					
						FinalizadoPCR (3, "Finalizado PCR"), ListaParaAnalizar (4, "Lista para analizar"), 
						AsignadaJefeAnalistas (5, "Asignada a jefe de analistas");								
		
		
		
		

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

	
	public EstadoBean() {

	}

	public EstadoBean(TipoEstado tipoEstado, Estado estado) {
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
	
	
	
	public EstadoBean asignarTipoEstadoYCodNum(TipoEstado tipoEstado, int codNumEstado){		
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
			
			case EstadoPlacaLabCentro: {
				this.setTipoEstado(TipoEstado.EstadoPlacaLabCentro);
				switch (codNumEstado) {				
					case 1: {
						this.setEstado(Estado.IniciadaConMuestras);
						break;
					}
					case 2: {
						this.setEstado(Estado.PreparadaParaPCR);
						break;
					}
					case 3: {
						this.setEstado(Estado.FinalizadoPCR);
						break;
					}
					case 4: {
						this.setEstado(Estado.ListaParaAnalizar);
						break;
					}
					case 5: {
						this.setEstado(Estado.AsignadaJefeAnalistas);
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