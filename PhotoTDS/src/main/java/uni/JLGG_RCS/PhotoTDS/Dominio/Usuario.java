package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
	
	private final String nombreCompleto;
	private final String nombreUsuario;
	private final Date fechaNacimiento;
	private final String email;
	
	private String presentacion;
	private String contraseña;
	private Image foto;
	
	private List<Usuario> seguidores;
	private List<Foto> fotos;
	private List<Album> albumes;
	private List<Notificacion> notificaciones;
	
	public Usuario(String nombreCompleto, String nombreUsuario, String email, String contraseña, Date fechaNacimiento) {
		this.nombreCompleto = nombreCompleto;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contraseña = contraseña;
		this.fechaNacimiento = fechaNacimiento;
				
		this.seguidores = new ArrayList<Usuario>();
		this.fotos = new ArrayList<Foto>();
		this.albumes = new ArrayList<Album>();
		this.notificaciones = new ArrayList<Notificacion>();
	}

	public String getPresentacion() {
		return presentacion;
	}

	// Ojo: sólo se toman los 200 primeros caracteres
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion.substring(0, 200);
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Image getFoto() {
		return foto;
	}

	public void setFoto(Image foto) {
		this.foto = foto;
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

	public List<Foto> getFotos() {
		return new ArrayList<Foto>(fotos);
	}
	
	public List<Album> getAlbumes() {
		return new ArrayList<Album>(albumes);
	}
	
	public int getMeGustaTotales() {
		int total = 0;
		fotos.stream().map(f -> f.getMegusta()).reduce(total, (i1,i2) -> i1+i2);
		albumes.stream().map(f -> f.getMegusta()).reduce(total, (i1,i2) -> i1+i2);
		return total;
	}

}
