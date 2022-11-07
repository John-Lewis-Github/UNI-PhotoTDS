package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Publicacion {
	static final int ME_GUSTA_INICIAL = 0;
	static final int MAX_HASHTAG_CHARS = 16; // Incluye el '#'
	
	private String titulo;
	private final Date fecha;
	private String descripcion;
	private int megusta;
	private List<String> hashtags;
	private List<Comentario> comentarios;
	private final Usuario usuario;
	
	
	public Publicacion(String titulo, Date fecha, String descripcion, Usuario usuario) {
		this.titulo = titulo;
		this.fecha = new Date();
		this.descripcion = descripcion;
		this.megusta = ME_GUSTA_INICIAL;
		this.hashtags = new LinkedList<String>();
		addHashtags(descripcion);
		this.comentarios = new LinkedList<Comentario>();
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		addHashtags(descripcion);
	}

	public List<String> getHashtags() {
		return new LinkedList<String>(hashtags);
	}

	// Los hashtags sólo se introducen como parte de un comentario OJO
	private void addHashtags(String texto) {
		int i = 0;
		while ((i = texto.indexOf("#", i)) != -1) {
			this.hashtags.add(texto.substring(i, MAX_HASHTAG_CHARS));
		}
		
	}

	public Date getFecha() {
		return fecha;
	}

	public int getMegusta() {
		return megusta;
	}
	
	public void darMeGusta() {
		megusta += 1;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public List<Comentario> getComentarios() {
		return new LinkedList<Comentario>(comentarios);
	}
	
	public void addComentario(Comentario comment) {
		this.comentarios.add(comment);
		// addHashtags(comment);
	}
	
}
