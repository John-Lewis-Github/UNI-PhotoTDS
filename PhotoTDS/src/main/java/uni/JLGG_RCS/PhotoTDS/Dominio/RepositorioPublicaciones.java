package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uni.JLGG_RCS.PhotoTDS.Persistencia.DAOException;
import uni.JLGG_RCS.PhotoTDS.Persistencia.FactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.PublicacionDAO;

public enum RepositorioPublicaciones {
	INSTANCE;
	
	private FactoriaDAO fact;

	private PublicacionDAO<Foto> fotoDAO;
	private PublicacionDAO<Album> albumDAO;
	private List<Foto> fotos;
	private List<Album> albumes;
	private Map<Hashtag, List<Publicacion>> porHashtag;

	private RepositorioPublicaciones() {
		
		// Se establece el tipo de factoria de DAOs
		try {
			fact = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		// Una vez hecho, se obtienen los DAOs y se recuperan las publicaciones
		fotoDAO = fact.getFotoDAO();
		albumDAO = fact.getAlbumDAO();
		
		fotos = fotoDAO.getAll();
		albumes = albumDAO.getAll();
		
		// Se indexaran las publicaciones por hashtags
		porHashtag = new HashMap<>();
		
		// Para cada foto, la incluyo en las listas de sus hashtags
		for (Foto f : fotos) {
			for (Hashtag h : f.getHashtags()) {
				porHashtag.putIfAbsent(h, new LinkedList<>());
				porHashtag.get(h).add(f);
			}
		}
		
		// Para cada album, lo incluyo en las listas de sus hashtags
		for (Album a : albumes) {
			for (Hashtag h : a.getHashtags()) {
				porHashtag.putIfAbsent(h, new LinkedList<>());
				porHashtag.get(h).add(a);
			}
		}
		
	}
	
	public void addPublicacion(Publicacion publicacion) {
		// Es necesario tratar el tipo de publicacion
		if (publicacion instanceof Foto) {
			fotoDAO.create((Foto) publicacion);
			fotos.add((Foto) publicacion);
		} else if (publicacion instanceof Album) {
			albumDAO.create((Album) publicacion);
			albumes.add((Album) publicacion);
		}
		
		// La nueva publicacion sera indexada por hashtags
		for (Hashtag h : publicacion.getHashtags()) {
			porHashtag.putIfAbsent(h, new LinkedList<>());
			porHashtag.get(h).add(publicacion);
		}
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
