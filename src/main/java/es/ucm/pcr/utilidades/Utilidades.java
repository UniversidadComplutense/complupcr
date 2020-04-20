package es.ucm.pcr.utilidades;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;


public class Utilidades {

	public static DecimalFormat df2 = new DecimalFormat("#.##");
	// 5MB
	public static Integer MAX_FILE_SIZE = 5242880;

	public static Integer NUMERO_PAGINACION = 20;
	public static Integer NUMERO_PAGINACION_USUARIO = 20;

	
	public static Date fechafinBuscador(Date fechafin) {
		if (fechafin != null){
		    Calendar calendar = Calendar.getInstance();
	        calendar.setTime(fechafin); // Configuramos la fecha que se recibe
	        calendar.add(Calendar.HOUR, 23);  // numero de horas a añadir, o restar en caso de horas<0
	        calendar.add(Calendar.MINUTE, 59);
	        fechafin= calendar.getTime();
		}	
	         return fechafin ;
			}
	     
	
		
	
	public static String limpiarStringParaOrdenacion(String cadena) {
		String limpio = null;
		if (cadena != null) {
			String valor = cadena;
			valor = valor.toUpperCase();
			// Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
			limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
			// Quitar caracteres no ASCII excepto la enie, interrogacion que abre,
			// exclamacion que abre, grados, U con dieresis.
			limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
			// Regresar a la forma compuesta, para poder comparar la enie con la tabla de
			// valores
			limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
		}
		return limpio;
	}

	/**
	 * Formatea a string el tamanio del fichero
	 * 
	 * @param size
	 * @return
	 */
	public static String fileSizeFormat(Integer size) {
		double sized = 0;

		sized = (double) size / (1024 * 1024);
		if (sized > 1) {
			return df2.format(sized) + " MB";
		} else {
			sized = (double) size / (1024);
			if (sized > 1) {
				return df2.format(sized) + " kB";
			} else {
				return size + " bytes";
			}
		}
	}

	/**
	 * Comprueba si el tamanio del fichero excede lo permitido
	 * 
	 * @param size
	 * @return
	 */
	public static boolean excedeTamanioFichero(long size) {
		return (size > MAX_FILE_SIZE);
	}

	public static String resultado(String res) {
		String respuesta = "";
		res=res.toUpperCase();
		switch (res) {
		case "POSITIVO":
			respuesta="POSITIVO";
			break;
		case "NEGATIVO":
			respuesta="NEGATIVO";
			break;
		case "CI NEG O BAJO":
			respuesta="DEBIL";
			break;
		case "DUDOSO":
			respuesta="REPETIR";
			break;
		}
		return respuesta;
	}

}
