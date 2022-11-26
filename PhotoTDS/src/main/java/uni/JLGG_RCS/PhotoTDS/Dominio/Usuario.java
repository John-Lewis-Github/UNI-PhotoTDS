package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Usuario implements Persistente {
	
	private int id;
	private final String nombreCompleto;
	private final String nombreUsuario;
	private final Date fechaNacimiento;
	private final String email;
	
	private String password;
	private String presentacion;
	private Foto fotoPerfil;
	
	private List<Usuario> seguidores;
	private List<Foto> fotos;
	private List<Album> albumes;
	private List<Notificacion> notificaciones;
	
	public Usuario(String nombreCompleto, String nombreUsuario, Date fechaNacimiento, String email) {
		this.nombreCompleto = nombreCompleto;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
				
		this.seguidores = new ArrayList<Usuario>();
		this.fotos = new ArrayList<Foto>();
		this.albumes = new ArrayList<Album>();
		this.notificaciones = new ArrayList<Notificacion>();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPresentacion() {
		return presentacion;
	}

	// Ojo: sólo se toman los 200 primeros caracteres
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion.substring(0, 200);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Foto getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(Foto foto) {
		this.fotoPerfil = foto;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public List<Usuario> getSeguidores() {
		return new ArrayList<Usuario>(seguidores);
	}
	
	public void addSeguidor(Usuario seguidor) {
		seguidores.add(seguidor);
	}

	public void addSeguidores(Collection<Usuario> seguidores) {
		seguidores.addAll(seguidores);
	}

	public List<Foto> getFotos() {
		return new ArrayList<Foto>(fotos);
	}
	
	public void addFoto(Foto foto) {
		fotos.add(foto);
	}

	public void addFotos(Collection<Foto> fotos) {
		this.fotos.addAll(fotos);
	}
	
	public List<Album> getAlbumes() {
		return new ArrayList<Album>(albumes);
	}
	
	public void addAlbum(Album album) {
		albumes.add(album);
	}

	public void addAlbumes(Collection<Album> albumes) {
		this.albumes.addAll(albumes);
	}
	
	public List<Notificacion> getNotificaciones() {
		return new ArrayList<Notificacion>(notificaciones);
	}
	
	public void addNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
	}

	public void addNotificaciones(Collection<Notificacion> notifiaciones) {
		this.notificaciones.addAll(notifiaciones);
	}
	
	public int getMeGustaTotales() {
		int total = 0;
		fotos.stream().map(f -> f.getMeGusta()).reduce(total, (i1,i2) -> i1+i2);
		albumes.stream().map(f -> f.getMeGusta()).reduce(total, (i1,i2) -> i1+i2);
		return total;
	}

}
