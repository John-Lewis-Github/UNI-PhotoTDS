package uni.JLGG_RCS.PhotoTDS.Dominio.PruebasUnitarias;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public class UsuarioTest {

	private Usuario u = new Usuario("Pepe Simon", "pepito32", Date.from(Instant.now()), "pepe@gmail.com", "contra");

	// Prueba de getter/setter
	@Test
	public void testPassword() {
		if (u.getPassword() != "contra")
			fail("La contraseña debería ser contra");
		
		u.setPassword("ab12cd34");

		if (u.getPassword() != "ab12cd34")
			fail("La contraseña debería ser ab12cd34");
	}
	
	// Prueba de presentacion
	@Test
	public void testPresentacion() {
		if (u.getPresentacion() != "")
			fail("La presentacion debería ser '' si no se ha definido");
		
		u.setPresentacion("Hola que tal");

		if (u.getPresentacion() != "Hola que tal")
			fail("La presentacion debería ser Hola que tal");
		
		String presentacionLarga = "";
		
		for (int i = 0; i < 50; i++) {
			presentacionLarga += "0123456789";
		}
		
		u.setPresentacion(presentacionLarga);
		
		if (u.getPresentacion().length() > 200) {
			fail("La presentacion debería tener 200 caracteres o menos");
		}
	}
	
	// Prueba de meGusta
	@Test
	public void testMeGusta() {
		if (u.getMeGustaTotales() != 0)
			fail("El usuario debería tener 0 meGusta");		
	}

	// Prueba de descuento (OJO: no sirve si se tocan esas clases)
	@Test
	public void testDescuento() {
		u.setPremium(true);
		
		double total = 2000;
		if (u.calcularPrecioPremium(total) != total*0.8)
			fail("Mal descuento");
	}
	
	// Prueba con la lista de seguidores
	@Test
	public void testSeguidores() {
		List<Usuario> l  = u.getSeguidores();
		
		if (l.size() > 0)
			fail("La lista debería estar vacía");
		
		// Probamos a añadir un seguidor (nos vale el mismo)
		u.addSeguidor(u);
		
		l  = u.getSeguidores();
		
		if (l.size() != 1)
			fail("La lista debería tener un único elemento");
		
		List<Usuario> l2 = new LinkedList<>();
		
		Usuario u1 = new Usuario("Ramon", "Ramon23", new Date(), "ramon@ramon.ramon", "ramon");
		Usuario u2 = new Usuario("Roman", "Roman23", new Date(), "roman@roman.roman", "roman");
		Usuario u3 = new Usuario("Romeo", "Romeo23", new Date(), "romeo@romeo.romeo", "romeo");
		
		l2.add(u1);
		l2.add(u2);
		l2.add(u3);
		
		u.addSeguidores(l2);
		
		l  = u.getSeguidores();
		
		if (l.size() != 4) {
			System.out.println(l.size());
			fail("La lista debería tener cuatro elementos");
		}
		
		
	}
}
