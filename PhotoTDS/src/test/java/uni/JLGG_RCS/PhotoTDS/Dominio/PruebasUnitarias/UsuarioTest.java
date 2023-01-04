package uni.JLGG_RCS.PhotoTDS.Dominio.PruebasUnitarias;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public class UsuarioTest {

	private Usuario u = new Usuario("Pepe Simon", "pepito32", new Date(), "pepe@gmail.com", "contra");

	@Test 
	public void testConstructor() {
		Usuario prueba = null;
		
		// Comprobamos que el constructor no admite nulls en los parametros
		// Ni cadenas vacias
		
		// Nombre completo
		try {
			prueba = new Usuario(null, "prueba23", new Date(), "prueba@prueba.com", "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		try {
			prueba = new Usuario("", "prueba23", new Date(), "prueba@prueba.com", "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		
		// Nombre de usuario
		try {
			prueba = new Usuario("Prueba", null, new Date(), "prueba@prueba.com", "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		try {
			prueba = new Usuario("Prueba", "", new Date(), "prueba@prueba.com", "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		
		// Fecha
		try {
			prueba = new Usuario("Prueba", "prueba23", null, "prueba@prueba.com", "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		
		// Email
		try {
			prueba = new Usuario("Prueba", "prueba23", new Date(), null, "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		try {
			prueba = new Usuario("Prueba", "prueba23", new Date(), "", "asdf");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		
		// Password
		try {
			prueba = new Usuario("Prueba", "prueba23", new Date(), "prueba@prueba.com", null);
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
		try {
			prueba = new Usuario("Prueba", "prueba23", new Date(), "prueba@prueba.com", "");
		} catch (IllegalArgumentException e) {
			// no pasa nada, debe ser asi
		}
		assert(prueba == null);
	}
	
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
			System.out.print(u.getPresentacion());
			System.out.print(u.getPresentacion().length());
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
		
		// Probamos a incluirse a si mismo como seguidor
		boolean sePuede = false;
		try {
			u.addSeguidor(u);
			sePuede = true;
		} catch (IllegalArgumentException e) {
			// No pasa nada, esto DEBE ocurrir
		}
		// No se debe poder
		assert(!sePuede);
		
		Usuario u0 = new Usuario("Rita", "Rita11", new Date(), "rita@rita.rita", "rita");
		u.addSeguidor(u0);
		
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
	
	@Test
	public void testPersistente() {
		u.setId((Integer) null);
		System.out.println(u.getId());
		
		u.setId(12);
		System.out.println(u.getId());
		
		Integer i = 14;
		u.setId(i);
		System.out.println(u.getId());
	}
}
