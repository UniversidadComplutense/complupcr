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
