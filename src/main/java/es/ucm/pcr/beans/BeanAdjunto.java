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

import net.sf.jasperreports.engine.JasperPrint;

public class BeanAdjunto {	
	
	//clase que contendrá el jasper y el nombre del fichero
		
		private JasperPrint reporte;
		private String nombre;
		
		public JasperPrint getReporte() {
			return reporte;
		}
		public void setReporte(JasperPrint reporte) {
			this.reporte = reporte;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		@Override
		public String toString() {
			return "BeanAdjunto [reporte=" + reporte + ", nombre=" + nombre + "]";
		}
		
		
				
		
		

}
