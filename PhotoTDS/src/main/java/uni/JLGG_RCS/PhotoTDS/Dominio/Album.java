package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album extends Publicacion {

	private List<Foto> fotos;

	/**
	 * Constructor de albumes. Inicialmente no tiene fotos
	 * 
	 * @param titulo el titulo del album
	 * @param descripcion la descripcion de la publicacion
	 */
	public Album(String titulo, String descripcion) {
		super(titulo, descripcion);
		this.fotos = new ArrayList<Foto>();
	}
	
	/**
	 * Incluye una foto en el album
	 * @param foto la nueva foto del album
	 */
	public void addFoto(Foto foto) {
		this.fotos.add(foto);
	}
	
	/**
	 * Getter de las fotos
	 * @return la lista de fotos del album
	 */
	public List<Foto> getFotos() {
		return new ArrayList<Foto>(fotos);
	}
	
	/**
	 * Setter de las fotos
	 * @param fotos la lista de fotos del album
	 */
	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	
	/**
	 * Un "Me gusta" para el album indica un "Me gusta" para cada publicacion
	 * del album
	 */
	@Override
	public void darMeGusta() {
		fotos.stream().forEach(f -> f.darMeGusta());
	}
	
	/**
	 * Se devuelven los "Me gusta" de todas las publicaciones del album
	 */
	@Override
	public int getMeGusta() {
		return fotos.stream()
				.map(f -> f.getMeGusta())
				.reduce(0, (x,y)->x+y);
	}

	@Override
	public Image getImagenPrincipal() {
		// Se toma la primera foto
		return fotos.get(0).getImagenPrincipal();
	}

}
