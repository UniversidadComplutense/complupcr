package es.ucm.pcr.beans;

public class BeanResultadoCarga {

		private String muestra;
		private String resultado;
		
		public BeanResultadoCarga() {
			super();
			
		}
		public BeanResultadoCarga(String muestra, String resultado) {
			super();
			this.muestra = muestra;
			this.resultado = resultado;
		}
		/**
		 * @return the muestra
		 */
		public String getMuestra() {
			return muestra;
		}
		/**
		 * @param muestra the muestra to set
		 */
		public void setMuestra(String muestra) {
			this.muestra = muestra;
		}
		/**
		 * @return the resultado
		 */
		public String getResultado() {
			return resultado;
		}
		/**
		 * @param resultado the resultado to set
		 */
		public void setResultado(String resultado) {
			this.resultado = resultado;
		}

		


}