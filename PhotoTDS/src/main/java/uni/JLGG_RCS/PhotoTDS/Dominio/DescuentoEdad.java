package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.Date;

public class DescuentoEdad implements Descuento {

	private static final long MILLIS_PER_YEAR = 1000*60*60*24*365;
	private Usuario usuario;
	public DescuentoEdad(Usuario u) {
		usuario = u;
	}

	@Override
	public double aplicarDescuento() {
		long millis = usuario.getFechaNacimiento().getTime() - new Date().getTime();
		long years = millis / MILLIS_PER_YEAR;
		if (years < 18)
			return 0.2;
		return 0.0;
	}

}
