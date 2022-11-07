package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Album extends Publicacion {

	private List<Foto> fotos;
	
	public Album(String titulo, Date fecha, String descripcion, Usuario usuario) {
		super(titulo, fecha, descripcion, usuario);
		this.fotos = new ArrayList<Foto>();
	}
	
	public void addFoto(Foto f) {
		this.fotos.add(f);
	}
	
	public List<Foto> getFotos() {
		return new ArrayList<Foto>(fotos);
	}

}
