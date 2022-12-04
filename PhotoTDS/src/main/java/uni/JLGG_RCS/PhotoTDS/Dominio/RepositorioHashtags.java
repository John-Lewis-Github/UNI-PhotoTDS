package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.HashMap;

public enum RepositorioHashtags {
	INSTANCE;
	
	private final HashMap<String, Hashtag> hashtags;

	private RepositorioHashtags() {
		hashtags = new HashMap<String, Hashtag>();
	}
	
	public Hashtag getHashtag(String contenido) {
		if (hashtags.containsKey(contenido)) {
			return hashtags.get(contenido);
		} else {
			Hashtag nuevo = new Hashtag(contenido);
			hashtags.put(contenido, nuevo);
			return nuevo;
		}
	}

}
