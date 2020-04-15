package es.ucm.pcr.utilidades;

import java.text.DecimalFormat;
import java.text.Normalizer;

public class Utilidades {
	
	public static DecimalFormat df2 = new DecimalFormat("#.##");
	// 5MB
	public static Integer MAX_FILE_SIZE = 5242880;
	
	public static Integer NUMERO_PAGINACION = 5;
	
	public static String limpiarStringParaOrdenacion(String cadena) {
	    String limpio =null;
	    if (cadena !=null) {
	        String valor = cadena;
	        valor = valor.toUpperCase();
	        // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
	        limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
	        // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
	        limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
	        // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
	        limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
	    }
	    return limpio;
	}
	
	
	/**
	 * Formatea a string el tamanio del fichero
	 * @param size
	 * @return
	 */
	public static String fileSizeFormat(Integer size) {
		double sized = 0;
		
		sized = (double) size/(1024*1024);
		if (sized > 1) {
			return df2.format(sized) + " MB";
		} else {
			sized = (double) size/(1024);
			if (sized > 1) {
				return df2.format(sized) + " kB";
			} else {
				return size + " bytes";
			}
		}	
	}	 
	
	/**
	 * Comprueba si el tamanio del fichero excede lo permitido
	 * @param size
	 * @return
	 */
	public static boolean excedeTamanioFichero(long size) {
		return (size > MAX_FILE_SIZE);		
	}

}
