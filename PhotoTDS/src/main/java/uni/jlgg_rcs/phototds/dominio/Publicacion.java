package uni.jlgg_rcs.phototds.dominio;

import java.awt.Image;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class Publicacion implements Persistente {
	static final int ME_GUSTA_INICIAL = 0;
	
	private Integer id;
	
	private String titulo;
	private Date fecha;
	private String descripcion;
	
	private int megusta;
	
	private Set<Hashtag> hashtags;
	private List<Comentario> comentarios;
	private Usuario usuario;
	
	private final RepositorioHashtags repHashtags = RepositorioHashtags.INSTANCE;
	
	/**
	 * Constructor de publicaciones. Toma el titulo y la descripcion,
	 * mientras que el resto de atributos se inicializan directamente.
	 * Su usuario queda inicializado a null.
	 * 
	 * Los parametros que toma deben ser distintos de null y de ""
	 * 
	 * @param titulo el titulo de la publicacion
	 * @param descripcion la descripcion
	 */
	protected Publicacion(String titulo, String descripcion) {
		id = null;
		
		if ((titulo == null) || (titulo.equals("")))
			throw new IllegalArgumentException();
		this.titulo = titulo;
		
		this.fecha = new Date();
		
		if (descripcion == null)
			throw new IllegalArgumentException();
		this.descripcion = descripcion;
		
		this.megusta = ME_GUSTA_INICIAL;
		
		this.hashtags = new HashSet<Hashtag>();
		addHashtags(descripcion);
		
		this.comentarios = new LinkedList<Comentario>();
		
		this.usuario = null;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Getter del titulo
	 * @return el titulo de la publicacion
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Setter del titulo
	 * @param titulo el nuevo titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Getter de la fecha
	 * @return la fecha de la publicacion
	 */
	public Date getFecha() {
		return fecha;
	}
	
	/**
	 * Setter de la fecha
	 * @param fecha la nueva fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * Getter de la descripcion
	 * @return la descripcion de la publicacion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Setter de la descripcion
	 * @param descripcion la nueva descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		addHashtags(descripcion);
	}

	/**
	 * Getter de los hashtags
	 * @return los hashtags de la publicacion
	 */
	public List<Hashtag> getHashtags() {
		return new LinkedList<Hashtag>(hashtags);
	}

	/**
	 * Parsea un texto e incluye los hashtags que contiene en el conjunto
	 * de hashtags de la publicacion
	 * 
	 * @param texto el texto a parsear
	 */
	private void addHashtags(String texto) {
		if ((texto == null) || texto.equals(""))
			return;
		
		List<String> palabras = Arrays.asList(texto.split(" "));
		palabras.stream()
				.filter(s -> (s.charAt(0) == '#'))
				.forEach(s -> this.hashtags.add(repHashtags.getHashtag(s)));
	}
	
	/** 
	 * Incluye una coleccion de hashtags para la publicacion
	 * Solo incluye los hashtags nuevos
	 * 
	 * @param hashtags los nuevos hashtags
	 */
	public void addHashtags(Collection<Hashtag> hashtags) {
		String nuevosHashtags = hashtags.stream()
			.filter(h -> this.hashtags.add(h))
			.map(h -> h.getContenido())
			.reduce("", (s1, s2) -> s1 + " " + s2);
		
		this.descripcion += nuevosHashtags;
	}

	/**
	 * Getter del numero de meGusta
	 * @return los meGusta
	 */
	public int getMeGusta() {
		return megusta;
	}
	
	public void setMeGusta(int megusta) {
		this.megusta = megusta;
	}
	
	public void darMeGusta() {
		megusta += 1;
	}
	
	public List<Comentario> getComentarios() {
		return new LinkedList<Comentario>(comentarios);
	}
	
	public void addComentario(Comentario comment) {
		this.comentarios.add(comment);
	}
	
	public void addComentarios(Collection<Comentario> comentarios) {
		this.comentarios.addAll(comentarios);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Devuelve una imagen que represente a la publicacion
	 * 
	 * @return una imagen para la publicacion
	 */
	public abstract Image getImagenPrincipal();

	
}
