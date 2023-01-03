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
	
	/**
	 * Constructor principal de fotos. Toma el titulo de la foto,
	 * su descripcion y la ruta a un fichero del sistema que representa
	 * una imagen.
	 * 
	 * @param titulo el titulo de la foto
	 * @param descripcion la descripcion de la foto
	 * @param la ruta a la imagen
	 */
	public Foto(String titulo, String descripcion, String path) {
		super(titulo, descripcion);
		this.path = path;
		this.imagen = null;
	}
	
	/**
	 * Constructor auxiliar que no establece ruta
	 * 
	 * @param titulo el titulo de la foto
	 * @param descripcion la descripcion de la foto
	 */
	public Foto(String titulo, String descripcion) {
		this(titulo, descripcion, "");
	}
	
	
	/**
	 * Getter de la ruta
	 * @return la ruta de la imagen
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Setter de la ruta
	 * @param path la nueva ruta a la imagen
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Getter de la imagen de la foto
	 * @return la imagen asociada a la foto
	 */
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
