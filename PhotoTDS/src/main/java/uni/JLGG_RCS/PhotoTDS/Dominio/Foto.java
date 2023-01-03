package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

// Por terminar
public class Foto extends Publicacion {
	
	private String path;
	private Image imagen;
	
	public Foto(String titulo, String descripcion) {
		super(titulo, descripcion);
		this.path = null;
		this.imagen = null;
	}
	
	public Foto(String titulo, String descripcion, String path) {
		super(titulo, descripcion);
		this.path = path;
		this.imagen = null;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Image getImagen () {
		// Se retrasa la creacion de la imagen hasta el ultimo momento
		if (imagen == null)
			try {
				this.imagen = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return imagen;
	}

	@Override
	public Image getImagenPrincipal() {
		return getImagen();
	}

}
