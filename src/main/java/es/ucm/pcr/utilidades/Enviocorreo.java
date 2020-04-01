package es.ucm.pcr.utilidades;
import java.util.List;

import es.ucm.pcr.beans.BeanAdjunto;
import es.ucm.pcr.beans.BeanCorreo;
//import es.ucm.titulos.preinscripcion.beans.BeanReporter;
//import es.ucm.titulos.preinscripcion.beans.BeanSolicitud;
//import net.sf.jasperreports.engine.JasperPrint;
public interface Enviocorreo {


		public void send(String to, String subject, String texto, List<BeanAdjunto> listaAdjuntos, String cabecera, String pie, String convocatoria);

	    
	    public boolean enviarCorreoGenericoConAdjuntos(BeanCorreo beanCorreo) throws Exception;
}
