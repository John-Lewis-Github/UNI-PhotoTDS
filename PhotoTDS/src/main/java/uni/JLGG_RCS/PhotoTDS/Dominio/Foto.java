package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.awt.Image;
import java.util.Date;

// Por terminar
public class Foto extends Publicacion {
	
	private String path;
	private Image imagen;
	
	public Foto(String titulo, Date fecha, String descripcion, Usuario usuario, String path, Image imagen) {
		super(titulo, fecha, descripcion, usuario);
		this.path = path;
		this.imagen = imagen;
	}

}
