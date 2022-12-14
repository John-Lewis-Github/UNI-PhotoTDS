package uni.jlgg_rcs.phototds.dominio;

import java.util.Date;

public class Comentario implements Persistente {
	
	private int id;
	private final String texto;
	private final Date fecha;
	private Usuario comentador;
	
	public Comentario(String texto, Date fecha) {
		this.texto = texto;
		this.fecha = fecha;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public Usuario getComentador() {
		return comentador;
	}
	
	public void setComentador(Usuario comentador) {
		this.comentador = comentador;
	}
}
