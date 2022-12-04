package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.HashMap;

public class Hashtag {

	public static final int MAX_HASHTAG_CHARS = 16; // Incluye el '#'
	private static final String ERROR_LONGITUD = 
			"Error: cada hashtag puede tener hasta 16 caracteres contando el simbolo '#'.";
	private static final String ERROR_CARACTERES = 
			"Error: solo se permiten caracteres en los rangos [a-z] y [A-Z].";
	
	private final String contenido;
	
	public Hashtag(String contenido) throws IllegalArgumentException {
		if (contenido.charAt(0) != '#') {
			contenido = "#" + contenido;
		}
		contenido = contenido.toLowerCase();
		if (contenido.length() > MAX_HASHTAG_CHARS) {
			throw new IllegalArgumentException(ERROR_LONGITUD);
		}
		if (!contenido.substring(1).matches("[a-zA-Z]")) {
			throw new IllegalArgumentException(ERROR_CARACTERES);
		}
		this.contenido = contenido;
	}
	
	public String getContenido() {
		return contenido;
	}

}
