package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.HashMap;

public enum RepositorioHashtags {
	INSTANCE;
	
	private final HashMap<String, Hashtag> hashtags;

	private RepositorioHashtags() {
		hashtags = new HashMap<String, Hashtag>();
	}
	
	/**
	 * Crea un hashtag con la cadena indicada y lo guarda para evitar 
	 * objetos similares repetidos. Es la funcion que se debe usar cada vez
	 * que se quiera recuperar un hashtag. 
	 * 
	 * @param contenido la cadena con la que formar el hashtag. Debe ajustarse
	 * a la forma de un hashtag: no mas de 15 letras mayusculas o minusculas,
	 * precedidas opcionalmente del simbolo '#'
	 * 
	 * @return Un hashtag a partir de la cadena parametro
	 * 
	 * @throws IllegalArgumentException
	 */
	public Hashtag getHashtag(String contenido) throws IllegalArgumentException {
		if (hashtags.containsKey(contenido)) {
			return hashtags.get(contenido);
		} else {
			Hashtag nuevo = new Hashtag(contenido);
			hashtags.put(contenido, nuevo);
			return nuevo;
		}
	}

}
