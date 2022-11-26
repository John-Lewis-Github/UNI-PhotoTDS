package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.Date;

public class Notificacion implements Persistente {

	private int id;
	private final Date fecha = new Date();
	private final Publicacion novedad;
	
	public Notificacion(Publicacion p) {
		this.novedad = p;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public Publicacion getPublicacion() {
		return novedad;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
