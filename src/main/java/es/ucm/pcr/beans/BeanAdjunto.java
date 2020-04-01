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
