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

import java.util.Date;
import java.util.List;

public class BeanCorreo {	
	
	//clase que contendrá la información para componer cualquier mail a partir del de, para, asunto y lista de reportes
		
		private String de;
		private String para;
		private String asunto;
		private String texto;
		private List<BeanAdjunto> listaAdjuntos;
		private String tipoMensaje;
		private Date fechaEnvio; //para el log
		private String cabecera;
		private String pie;
		private String convocatoria;
		
		public String getDe() {
			return de;
		}
		public void setDe(String de) {
			this.de = de;
		}
		public String getPara() {
			return para;
		}
		public void setPara(String para) {
			this.para = para;
		}
		public String getAsunto() {
			return asunto;
		}
		public void setAsunto(String asunto) {
			this.asunto = asunto;
		}
		public String getTexto() {
			return texto;
		}
		public void setTexto(String texto) {
			this.texto = texto;
		}
		
		public List<BeanAdjunto> getListaAdjuntos() {
			return listaAdjuntos;
		}
		public void setListaAdjuntos(List<BeanAdjunto> listaAdjuntos) {
			this.listaAdjuntos = listaAdjuntos;
		}
		public String getTipoMensaje() {
			return tipoMensaje;
		}
		public void setTipoMensaje(String tipoMensaje) {
			this.tipoMensaje = tipoMensaje;
		}
		public Date getFechaEnvio() {
			return fechaEnvio;
		}
		public void setFechaEnvio(Date fechaEnvio) {
			this.fechaEnvio = fechaEnvio;
		}
		public String getCabecera() {
			return cabecera;
		}
		public void setCabecera(String cabecera) {
			this.cabecera = cabecera;
		}
		public String getPie() {
			return pie;
		}
		public void setPie(String pie) {
			this.pie = pie;
		}
		public String getConvocatoria() {
			return convocatoria;
		}
		public void setConvocatoria(String convocatoria) {
			this.convocatoria = convocatoria;
		}
		
		
}
