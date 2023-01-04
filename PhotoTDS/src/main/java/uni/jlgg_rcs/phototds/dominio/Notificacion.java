package uni.jlgg_rcs.phototds.dominio;

import java.util.Date;
import java.util.EventObject;

public class Notificacion implements Persistente {

	private static final long serialVersionUID = -751224586408912519L;

	private Integer id;
	
	private final Date fecha;
	private final Publicacion publicacion;	
	
	public Notificacion(Publicacion publicacion, Date fecha) {
		this.publicacion = publicacion;
		this.fecha = fecha;
	}
	
	/**
	 * Constructor de notificaciones que toma unicamente la publicacion
	 * 
	 * @param publicacion la publicacion a notificar
	 */
	public Notificacion(Publicacion publicacion) {
		this(publicacion, new Date());
	}
	
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public Publicacion getPublicacion() {
		return publicacion;
	}
	
}
