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

import java.util.ArrayList;
import java.util.List;

public class BeanResultado {

	public enum ResultadoMuestra {

		// RESULTADO MUESTRA
		RESULTADO_MUESTRA_PENDIENTE("PENDIENTE", "Pendiente"), RESULTADO_MUESTRA_POSITIVO("POSITIVO", "Positivo"),
		RESULTADO_MUESTRA_NEGATIVO("NEGATIVO", "Negativo"), RESULTADO_MUESTRA_DEBIL("DEBIL", "Débil"),
		RESULTADO_MUESTRA_REPETIR("REPETIR", "Repetir");

		private String cod;
		public String descripcion;

		private ResultadoMuestra() {

		}

		private ResultadoMuestra(String cod, String descripcion) {
			this.cod = cod;
			this.descripcion = descripcion;
		}

		public String getCod() {
			return cod;
		}

		public String getDescripcion() {
			return descripcion;
		}

	}

	private ResultadoMuestra resultadoMuestra;

	public BeanResultado() {

	}
	
	public BeanResultado(ResultadoMuestra resultadoMuestra) {
		super();
		this.resultadoMuestra = resultadoMuestra;
	}

	public ResultadoMuestra getResultadoMuestra() {
		return resultadoMuestra;
	}

	public void setResultadoMuestra(ResultadoMuestra resultadoMuestra) {
		this.resultadoMuestra = resultadoMuestra;
	}

	public BeanResultado asignarTipoEstadoYCodNum(String cod) {

		switch (cod) {
			case "PENDIENTE": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE);
				break;
			}
			case "NEGATIVO": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_NEGATIVO);
				break;
			}
			case "POSITIVO": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_POSITIVO);
				break;
			}
			case "DEBIL": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_DEBIL);
				break;
			}
			case "REPETIR": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_REPETIR);
				break;
			}
		}

		return this;

	}
	
	public BeanResultado asignarTipoEstadoDescripcion(String descripcion) {

		switch (descripcion) {
			case "Pendiente": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE);
				break;
			}
			case "Negativo": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_NEGATIVO);
				break;
			}
			case "Positivo": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_POSITIVO);
				break;
			}
			case "Débil": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_DEBIL);
				break;
			}
			case "Repetir": {
				this.setResultadoMuestra(ResultadoMuestra.RESULTADO_MUESTRA_REPETIR);
				break;
			}
		}

		return this;

	}

	/**
	 * Resultados de la muestra
	 * 
	 * @return
	 */
	public static List<BeanResultado> resultadosMuestra() {
		// estados del lote
		List<BeanResultado> estadosLote = new ArrayList<>();
		estadosLote.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_PENDIENTE));
		estadosLote.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_NEGATIVO));
		estadosLote.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_POSITIVO));
		estadosLote.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_DEBIL));
		estadosLote.add(new BeanResultado(ResultadoMuestra.RESULTADO_MUESTRA_REPETIR));

		return estadosLote;
	}

}