package uni.jlgg_rcs.phototds.dominio;

import java.util.HashMap;

public class Hashtag {

	public static final int MAX_HASHTAG_CHARS = 16; // Incluye el '#'
	private static final String ERROR_LONGITUD = 
			"Error: cada hashtag puede tener hasta 16 caracteres contando el simbolo '#'.";
	private static final String ERROR_CARACTERES = 
			"Error: solo se permiten caracteres en los rangos [a-z] y [A-Z].";
	
	private static final String PATRON_CARACTERES = "[a-zA-Z]+";
	
	private final String contenido;
	
	/**
	 * Metodo estatico que determina si una cadena cumple los requisitos
	 * necesarios para representar un hashtag
	 * @param contenido
	 * @return
	 */
	public static boolean verificaHashtag(String contenido) {
		if (contenido.charAt(0) != '#') 
			contenido = "#" + contenido;
		
		// Primero se verifica si la longitud es correcta
		boolean longitudOk = contenido.length() <= MAX_HASHTAG_CHARS;
		if (!longitudOk)
			return false;
		
		// Si el paso anterior lo permite, vemos si se usan caracteres correctos
		boolean caracteresOk = contenido.substring(1).matches(PATRON_CARACTERES);
		if (!caracteresOk)
			return false;
		
		// Si ambos criterios se cumplen, la cadena es válida
		return true;
	}
	
	
	/**
	 * Constructor de hashtags. Por la forma de los hashtags, es necesario
	 * que se cumplan ciertas condiciones en la cadena que lo genera:
	 * 
	 * La cadena no debe sobrepasar los 16 caracteres contando el simbolo '#',
	 * que es opcional
	 * 
	 * Por otra parte, solo puede contener letras ASCII mayusculas o minusculas
	 *  
	 * @param contenido la cadena con la que formar el hashtag
	 * 
	 * @throws IllegalArgumentException
	 */
	public Hashtag(String contenido) throws IllegalArgumentException {
		if (contenido.charAt(0) != '#') 
			contenido = "#" + contenido;
		
		contenido = contenido.toLowerCase();
		if (contenido.length() > MAX_HASHTAG_CHARS) {
			throw new IllegalArgumentException(ERROR_LONGITUD);
		}
		if (!contenido.substring(1).matches(PATRON_CARACTERES)) {
			throw new IllegalArgumentException(ERROR_CARACTERES + "Hashtag: " + contenido.substring(1));
		}
		this.contenido = contenido;
	}
	
	public String getContenido() {
		return contenido;
	}

}
