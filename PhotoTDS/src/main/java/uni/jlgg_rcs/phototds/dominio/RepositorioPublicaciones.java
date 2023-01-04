package uni.jlgg_rcs.phototds.dominio;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uni.jlgg_rcs.phototds.persistencia.DAOException;
import uni.jlgg_rcs.phototds.persistencia.FactoriaDAO;
import uni.jlgg_rcs.phototds.persistencia.PublicacionDAO;

public enum RepositorioPublicaciones {
	INSTANCE;
	
	private Map<Hashtag, List<Publicacion>> porHashtag;

	private RepositorioPublicaciones() {
		
		// Se indexaran las publicaciones por hashtags
		porHashtag = new HashMap<>();
	}
	
	/**
	 * Incluye una publicacion en el repositorio de publicaciones,
	 * de forma que se pueda buscar
	 * 
	 * @param publicacion la publicacion a incluir
	 */
	public <T extends Publicacion> void  addPublicacion(T publicacion) {
		// La nueva publicacion sera indexada por hashtags
		for (Hashtag h : publicacion.getHashtags()) {
			porHashtag.putIfAbsent(h, new LinkedList<>());
			porHashtag.get(h).add(publicacion);
		}
	}
	
	/**
	 * Incluye un conjunto de publicaciones en el repositorio
	 * 
	 * @param coll una coleccion de publicaciones
	 */
	public <T extends Publicacion> void addPublicaciones(Collection<T> coll) {
		coll.stream().forEach(p -> addPublicacion(p));
	}
	
	/**
	 * Devuelve una lista de publicaciones que contienen el hashtag
	 * indicado
	 * 
	 * @param hashtag el hashtag que deben tener las publicaciones
	 * @return una lista de publicaciones
	 */
	public List<Publicacion> findPublicaciones(Hashtag hashtag) {
		porHashtag.putIfAbsent(hashtag, new LinkedList<>());
		return porHashtag.get(hashtag);
	}

}
