package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Publicacion implements Persistente {
	static final int ME_GUSTA_INICIAL = 0;
	
	private int id;
	private String titulo;
	private Date fecha;
	private String descripcion;
	private int megusta;
	private List<Hashtag> hashtags;
	private List<Comentario> comentarios;
	private Usuario usuario;
	
	private final RepositorioHashtags repHashtags = RepositorioHashtags.INSTANCE;
	
	public Publicacion(String titulo, String descripcion) {
		this.titulo = titulo;
		this.fecha = new Date();
		this.descripcion = descripcion;
		this.megusta = ME_GUSTA_INICIAL;
		this.hashtags = new LinkedList<Hashtag>();
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
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		addHashtags(descripcion);
	}

	public List<Hashtag> getHashtags() {
		return new LinkedList<Hashtag>(hashtags);
	}

	// Los hashtags sólo se introducen como parte de un comentario OJO
	private void addHashtags(String texto) {
		List<String> palabras = Arrays.asList(texto.split(" "));
		palabras.stream()
				.filter(s -> (s.charAt(0) == '#'))
				.forEach(s -> this.hashtags.add(repHashtags.getHashtag(s)));
	}

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
		// addHashtags(comment);
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

	
}
