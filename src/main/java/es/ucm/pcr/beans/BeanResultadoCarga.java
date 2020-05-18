/*************************************
COMPLUPCR implements a process for obtaining PCR results from a sample
until the final report of the analysis results. It allows to combine
multiple laboratories and coordinate the work of analysists working
remotely, besides tracing the whole process.

Copyright (C) 2020  Universidad Complutense de Madrid

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
**************************************/

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