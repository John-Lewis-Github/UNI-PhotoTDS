package uni.JLGG_RCS.PhotoTDS.Dominio;

public class Comentario {
	
	private final String texto;
	private final Usuario comentador;
	
	public Comentario(String texto, Usuario comentador) {
		this.texto = texto;
		this.comentador = comentador;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public Usuario getComentador() {
		return comentador;
	}
}
