package uni.JLGG_RCS.PhotoTDS.Dominio;

public class DescuentoPopularidad implements Descuento {
	
	private static final int MEGUSTA_THRESHOLD_1 = 500;
	private static final int MEGUSTA_THRESHOLD_2 = 3000;	
	private static final double DESCUENTO_1 = 0.1;
	private static final double DESCUENTO_2 = 0.3;
	private Usuario usuario;
	public DescuentoPopularidad(Usuario u) {
		usuario = u;
	}

	@Override
	public double aplicarDescuento() {
		if (usuario.getMeGustaTotales() >= MEGUSTA_THRESHOLD_1)
			return DESCUENTO_1;
		if (usuario.getMeGustaTotales() >= MEGUSTA_THRESHOLD_2)
			return DESCUENTO_2;
		return 0.0;
	}

}
