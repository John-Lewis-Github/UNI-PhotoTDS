package uni.JLGG_RCS.PhotoTDS.Dominio.PruebasIntegracion;

import java.util.Date;

import org.junit.Test;

import uni.jlgg_rcs.phototds.dominio.GeneradorPDF;
import uni.jlgg_rcs.phototds.dominio.Usuario;

public class GeneradorPDFTest {

	@Test
	public void test() {
		GeneradorPDF gen = GeneradorPDF.INSTANCE;
		
		Usuario u = new Usuario("Pepe premium", "Premium44", new Date(), "pepe@premium.com", "aaaa");
		Usuario u1 = new Usuario("Josefa", "josefa23", new Date(), "pepe@premium.com", "aaaa");
		Usuario u2 = new Usuario("MariCarmen", "mari", new Date(), "pepe@premium.com", "aaaa");
		Usuario u3 = new Usuario("Pepe", "otropepe", new Date(), "pepe@premium.com", "aaaa");
		
		u1.setPresentacion("Hola, soy Josefa");
		u2.setPresentacion("El siguiente no tiene presentacion");
		
		u.addSeguidor(u1);
		u.addSeguidor(u2);
		u.addSeguidor(u3);
		
		try {
			gen.generarPDF(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
