package es.ucm.pcr.utilidades;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 
 * @author pmarrasant
 * 
 * Clase con algunas utilidades generales para utilizarlas en la aplicación
 *
 */

public class AppUtil {
	
	@Autowired
	static Environment entorno;
	
	/** Mapa con las IP'S del entorno de producción */
	public static final Map<String, String> mapIPNodo_PRO = Stream.of(new String[][] {
		{"10.150.1.43", "Nodo 1"},{"10.150.1.44", "Nodo 2"},{"10.150.1.70", "Nodo 3"},{"10.150.1.71", "Nodo 4"}
	}).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));
		
	
	/**
	 * Comprueba si una collection está vacía.
	 *
	 * @param collection La collection
	 * @return true, si la collection está vacía
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Comoprueba si un objeto está vacío
	 *
	 * @param object el objeto
	 * @return true, si el objeto está vacío
	 */
	public static boolean isObjectEmpty(Object object) {
		if(object == null) return true;
		else if(object instanceof String) {
			if (((String)object).trim().length() == 0) {
				return true;
			}
		} else if(object instanceof Collection) {
			return isCollectionEmpty((Collection<?>)object);
		}
		return false;
	}
	
	/**
	 * Obtener entorno de ejecución
	 *
	 * 
	 * @return Entorno de desarrollo, integración o producción
	 */	
	public static   String devolverEntorno()
	{
		String entorno = "";
		String perfilesEjecucionActivos = System.getProperty("spring.profiles.active") != null ? System.getProperty("spring.profiles.active") : "";

		if (perfilesEjecucionActivos.contains("desarrollo"))
		{
			entorno = "DESARROLLO";
		}		
		if (perfilesEjecucionActivos.contains("integracion"))
		{
			entorno = "INTEGRACIÓN";
		}	
		if (perfilesEjecucionActivos.contains("produccion"))
		{
			entorno = "PRODUCCIÓN";
		}		
		return entorno;
//		if (Arrays.asList(entorno.getActiveProfiles()).contains("produccion")){
//			return "produccion";
//		} else if (Arrays.asList(entorno.getActiveProfiles()).contains("integracion")){
//			return "integracion";
//		} else {
//			return "desarrollo";
//		}
	}

	/**
	 * Devuelve un identificador de nodo en base a la ip
	 * 
	 * @param localeAddr ip
	 * @return identificador nodo
	 */
	public static String devolverNodo(String localeAddr) {
		String nodo = "Nodo 1";
		if (devolverEntorno().equals("PRODUCCIÓN")) {
			nodo = mapIPNodo_PRO.get(localeAddr);
		}
		return nodo;
	}
	
	public static String cursoActual(String anyoUrl) throws Exception {		
		return anyoUrl + "/" + Integer.toString(Integer.parseInt(anyoUrl) + 1);
	}
	
	public static String cursoActualGuion(String anyoUrl) throws Exception {
		return anyoUrl + "-" + Integer.toString(Integer.parseInt(anyoUrl.substring(2, 4)) + 1);
	}

}
