package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.Date;

public class DescuentoEdad implements Descuento {

	private static final long MILLIS_PER_YEAR = 1000*60*60*24*365;
	private static final int EDAD_DESCUENTO = 18;
	private static final double DESCUENTO = 0.1;
	private Usuario usuario;
	public DescuentoEdad(Usuario u) {
		usuario = u;
	}

	@Override
	public double aplicarDescuento() {
		long millis = usuario.getFechaNacimiento().getTime() - new Date().getTime();
		long years = millis / MILLIS_PER_YEAR;
		if (years < EDAD_DESCUENTO)
			return DESCUENTO;
		return 0.0;
	}

}
