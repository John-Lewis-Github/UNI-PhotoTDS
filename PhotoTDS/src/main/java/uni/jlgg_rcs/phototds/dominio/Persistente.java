package uni.jlgg_rcs.phototds.dominio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Persistente {
	
	/**
	 * Obtiene el identificador de un objeto persistente.
	 * El identificador debe ser null por defecto, y modificarse 
	 * con el metodo setId()
	 * 
	 * @return el identificador de un objeto persistente
	 */
	public Integer getId();
	
	/**
	 * Modifica el identificador de un objeto persistente. 
	 * 
	 * @param id el nuevo identificador del objeto persistente
	 */
	public void setId(Integer id);
	
	/**
	 * Construye una cadena con los identificadores de una lista de 
	 * elementos persistentes.
	 * 
	 * @param l la lista con elementos persistentes
	 * @return una cadena con los identificadores separados por espacios
	 */
	public static <T extends Persistente> String idList2String(List<T> l) {		
		return l.stream()
				.map(e -> e.getId())
				.map(id -> Integer.toString(id))
				.reduce("", (s1, s2) -> s1+" "+s2)
				.trim();
	}
	
	/**
	 * Devuelve una lista de identificadores enteros a partir de una
	 * cadena que los contiene separados por espacios.
	 * 
	 * @param s la cadena con los identificadores
	 * @return una lista de enteros
	 */
	public static List<Integer> string2IdList(String s) {
		if (s.equals(""))
			return new ArrayList<Integer>();
		
		return Arrays.asList(s.split(" ")).stream()
				.map(e -> Integer.parseInt(e))
				.toList();
	}
}
