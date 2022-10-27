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
	
	public Usuario(String nombreCompleto, String nombreUsuario, String email, String contraseña, Date fechaNacimiento) {
		this.nombreCompleto = nombreCompleto;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contraseña = contraseña;
		this.fechaNacimiento = fechaNacimiento;
				
		this.seguidores = new ArrayList<Usuario>();
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
	
	
	
	
	
	
	

}
