package uni.JLGG_RCS.PhotoTDS.Dominio.PruebasUnitarias;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Hashtag;

public class AlbumTest {

	private Album album = new Album("Mi primer album", "Este es un album de #Ejemplo");
	private Foto f1 = new Foto("Mi foto", "Mi primera foto #Prueba #Test", "/home/ruben/Firefox_wallpaper.png");
	private Foto f2 = new Foto("Mi segunda foto", "Otra foto mas", "/home/ruben/Firefox_wallpaper.png");
	
	@Before
	public void setup() {
		album.addFoto(f1);
		album.addFoto(f2);
	}
	
	@Test
	public void testHashtags() {		
		List<Hashtag> l = album.getHashtags();
		
		if (l.size() != 1)
			fail("Debe haber un Hashtag");
	}

	@Test
	public void testMeGusta() {
		if (album.getMeGusta() != 0)
			fail("Debe haber 0 meGusta al principio");
		
		album.darMeGusta();
		album.darMeGusta();
		
		if (album.getMeGusta() != 4)
			fail("Debe haber 4 meGusta tras dar 2 veces");
		
		f1.darMeGusta();
		f2.darMeGusta();
		f2.darMeGusta();
		
		if (album.getMeGusta() != 7)
			fail("Debe haber 7 meGusta tras dar otras 3 veces a fotos individuales");
	}

}
