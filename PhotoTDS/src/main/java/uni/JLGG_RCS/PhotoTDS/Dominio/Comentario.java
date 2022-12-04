package uni.JLGG_RCS.PhotoTDS.Dominio;

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
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
