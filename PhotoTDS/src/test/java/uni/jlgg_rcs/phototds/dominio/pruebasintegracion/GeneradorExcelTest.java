package uni.jlgg_rcs.phototds.dominio.pruebasintegracion;

import java.util.Date;

import org.junit.Test;

import uni.jlgg_rcs.phototds.dominio.GeneradorExcel;
import uni.jlgg_rcs.phototds.dominio.Usuario;

public class GeneradorExcelTest {

	@Test
	public void test() {
		GeneradorExcel gen = GeneradorExcel.INSTANCE;
		
		Usuario u = new Usuario("Pepe premium", "Premium44", new Date(), "pepe@premium.com", "aaaa");
		Usuario u1 = new Usuario("Josefa", "josefa23", new Date(), "pepe@premium.com", "aaaa");
		Usuario u2 = new Usuario("MariCarmen", "mari", new Date(), "pepe@premium.com", "aaaa");
		Usuario u3 = new Usuario("Pepe", "otropepe", new Date(), "pepe@premium.com", "aaaa");
		
		u1.setPresentacion("Hola, soy Josefa");
		u2.setPresentacion("El siguiente no tiene presentacion");
		
		u.addSeguidor(u1);
		u.addSeguidor(u2);
		u.addSeguidor(u3);
		
		gen.generarExcel(u);
	}

}
