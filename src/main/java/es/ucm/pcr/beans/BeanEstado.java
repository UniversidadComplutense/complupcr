package es.ucm.pcr.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanEstado {

	public enum TipoEstado {
		EstadoLote, EstadoMuestra
	};

	public enum Estado {

		// ESTADOS LOTE
		LOTE_INICIADO(1, "Iniciado"), LOTE_ASIGNADO_CENTRO_ANALISIS(2, "Asignado centro análisis"),
		LOTE_ENVIADO_CENTRO_ANALISIS(3, "Enviado centro análisis"),
		LOTE_RECIBIDO_CENTRO_ANALISIS(4, "Recibido centro análisis"),
		LOTE_PROCESADO_CENTRO_ANALISIS(5, "Procesado centro análisis"),

		// ESTADOS MUESTRA
		MUESTRA_INICIADA(1, "Iniciada"), MUESTRA_ASIGNADA_LOTE(2, "Asignada lote"),
		MUESTRA_ENVIADA_CENTRO_ANALISIS(3, "Enviada centro análisis"),
		MUESTRA_PENDIENTE_ANALIZAR(4, "Pendiente analizar"), MUESTRA_ASIGNADA_ANALISTA(5, "Asignada analista"),
		MUESTRA_RESUELTA(6, "Resuelta");
				
		private int codNum;
		public String descripcion;

		private Estado() {

		}

		private Estado(int codNum, String descripcion) {
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

	public BeanEstado asignarTipoEstadoYCodNum(TipoEstado tipoEstado, int codNumEstado) {
		switch (tipoEstado) {
			case EstadoLote: {
				this.setTipoEstado(TipoEstado.EstadoLote);
				switch (codNumEstado) {
					case 1: {
						this.setEstado(Estado.LOTE_INICIADO);
						break;
					}
					case 2: {
						this.setEstado(Estado.LOTE_ASIGNADO_CENTRO_ANALISIS);
						break;
					}
					case 3: {
						this.setEstado(Estado.LOTE_ENVIADO_CENTRO_ANALISIS);
						break;
					}
					case 4: {
						this.setEstado(Estado.LOTE_RECIBIDO_CENTRO_ANALISIS);
						break;
					}
					case 5: {
						this.setEstado(Estado.LOTE_PROCESADO_CENTRO_ANALISIS);
						break;
					}
				}
				break;
			}
			case EstadoMuestra: {
				this.setTipoEstado(TipoEstado.EstadoMuestra);
				switch (codNumEstado) {
					case 1: {
						this.setEstado(Estado.MUESTRA_INICIADA);
						break;
					}
					case 2: {
						this.setEstado(Estado.MUESTRA_ASIGNADA_LOTE);
						break;
					}
					case 3: {
						this.setEstado(Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS);
						break;
					}
					case 4: {
						this.setEstado(Estado.MUESTRA_PENDIENTE_ANALIZAR);
						break;
					}
					case 5: {
						this.setEstado(Estado.MUESTRA_ASIGNADA_ANALISTA);
						break;
					}
					case 6: {
						this.setEstado(Estado.MUESTRA_RESUELTA);
						break;
					}
				}
				break;				
			}
		}

		return this;
	}

	/**
	 * Estados del lote
	 * @return
	 */
	public static List<BeanEstado> estadosLote() {
		// estados del lote
		List<BeanEstado> estadosLote = new ArrayList<>();
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_INICIADO));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_ASIGNADO_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_ENVIADO_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_RECIBIDO_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoLote, Estado.LOTE_PROCESADO_CENTRO_ANALISIS));
		
		return estadosLote;
	}
	
	/**
	 * Estados del lote
	 * @return
	 */
	public static List<BeanEstado> estadosMuestra() {
		// estados del lote
		List<BeanEstado> estadosLote = new ArrayList<>();
		estadosLote.add(new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_INICIADA));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_ASIGNADA_LOTE));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_ENVIADA_CENTRO_ANALISIS));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_PENDIENTE_ANALIZAR));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_ASIGNADA_ANALISTA));
		estadosLote.add(new BeanEstado(TipoEstado.EstadoMuestra, Estado.MUESTRA_RESUELTA));
		
		return estadosLote;
	}
	
	/**
	 * Para los centros, solo se pueden asignar muestras a lotes iniciados o asignado centro analisis (laboratorio)
	 * @return
	 */
	public static List<Integer> getEstadosLotesDisponiblesCentro() {
		return Arrays.asList(new Integer[] {Estado.LOTE_INICIADO.getCodNum(), Estado.LOTE_ASIGNADO_CENTRO_ANALISIS.getCodNum()});
	}

	@Override
	public String toString() {
		return "BeanEstado [tipoEstado=" + tipoEstado + ", estado=" + estado + "]";
	}

}