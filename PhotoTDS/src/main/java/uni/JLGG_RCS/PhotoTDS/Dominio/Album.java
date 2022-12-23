package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album extends Publicacion {

	private List<Foto> fotos;

	public Album(String titulo, String descripcion) {
		super(titulo, descripcion);
		this.fotos = new ArrayList<Foto>();
	}
	
	public void addFoto(Foto f) {
		this.fotos.add(f);
	}
	
	public List<Foto> getFotos() {
		return new ArrayList<Foto>(fotos);
	}
	
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

}
