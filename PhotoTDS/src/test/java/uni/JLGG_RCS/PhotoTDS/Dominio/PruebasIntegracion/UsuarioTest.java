package uni.JLGG_RCS.PhotoTDS.Dominio.PruebasIntegracion;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Hashtag;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public class UsuarioTest {
	
	private Usuario principal = new Usuario("Principal", "P1", new Date(), "email", "password");
	private Usuario sec1 = new Usuario("Secundario 1", "S1", new Date(), "email", "password");
	private Usuario sec2 = new Usuario("Secundario 2", "S2", new Date(), "email", "password");
	private Usuario sec3 = new Usuario("Secundario 3", "S3", new Date(), "email", "password");

	private Foto fotoPerfil = new Foto("FotoPerfil", "Foto de perfil", "/home/ruben/Firefox_wallpaper.png");
	private Foto foto = new Foto("Mi foto normal", "Mi primera foto #Prueba #Test", "/home/ruben/Firefox_wallpaper.png");
	private Album album = new Album("Mi primer album", "Este es un album de #Ejemplo");
	private Foto f1 = new Foto("Mi primera foto", "Mi primera foto de album #Prueba #Test", "/home/ruben/Firefox_wallpaper.png");
	private Foto f2 = new Foto("Mi segunda foto", "Otra foto mas", "/home/ruben/Firefox_wallpaper.png");
	
	
	@Before
	public void setup() {
		principal.addSeguidores(Arrays.asList(sec1, sec2, sec3));
		principal.setFotoPerfil(foto);
	}
	
	@Test
	public void testNotificaciones() {
		List<Notificacion> l = sec1.getNotificaciones();
		
		if (l.size() > 0)
			fail("Todavia no se ha publicado nada!");
		
		principal.publicarFoto(foto);
		
		l = sec1.getNotificaciones();
		if (l.size() != 1) {
			fail("No ha llegado la notificacion de foto");
		}
		
		principal.publicarAlbum(album);
		album.setFotos(Arrays.asList(f1,f2));
		
		l = sec1.getNotificaciones();
		if (l.size() != 2)
			fail("No ha llegado la notificacion de album");
		
		List<Notificacion> l2 = sec2.getNotificaciones();
		List<Notificacion> l3 = sec3.getNotificaciones();
		
		if ((!l.equals(l2)) || (!l.equals(l3))) {
			fail("Las tres listas deben ser iguales");
		}
	}
	
	@Test
	public void testUnicidadHashtags() {
		List<Hashtag> l1 = foto.getHashtags();
		List<Hashtag> l2 = f1.getHashtags();

		Hashtag h1 = l1.get(0);
		Hashtag h2 = l2.get(0);
		
		if (h1 != h2)
			fail("Los hashtags deben ser el mismo objeto");
		
		h1 = l1.get(1);
		h2 = l2.get(1);
		
		if (h1 != h2)
			fail("Los hashtags deben ser el mismo objeto");
	}

}