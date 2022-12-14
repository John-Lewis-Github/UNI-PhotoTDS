package uni.jlgg_rcs.phototds.dominio.pruebasunitarias;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.junit.Test;

import uni.jlgg_rcs.phototds.dominio.Foto;
import uni.jlgg_rcs.phototds.dominio.Hashtag;

public class FotoTest {

	private Foto f = new Foto("Mi foto", "Mi primera foto #Prueba #Test", "/home/ruben/Firefox_wallpaper.png");
	private Foto f2 = new Foto("Foto espejo", "La foto de antes #Prueba #Test", "/home/ruben/Firefox_wallpaper.png");
	@Test
	public void testHashtags() {		
		List<Hashtag> l = f.getHashtags();
		
		if (l.size() != 2)
			fail("Debe haber dos Hashtag");
	}

	@Test
	public void testMeGusta() {
		if (f.getMeGusta() != 0)
			fail("Debe haber 0 meGusta al principio");
		
		f.darMeGusta();
		f.darMeGusta();
		f.darMeGusta();
		
		if (f.getMeGusta() != 3)
			fail("Debe haber 3 meGusta tras dar 3 veces");
		
		f.darMeGusta();
		f.darMeGusta();
		f.darMeGusta();
		
		if (f.getMeGusta() != 6)
			fail("Debe haber 6 meGusta tras dar otras 3 veces");
	}
	
	@Test
	public void testUsoIcono() {
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(f.getImagen().getScaledInstance(200, 150, 0)));
		label.setSize(new Dimension(200,200));
		
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		panel.add(label);
		
		frame.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUnicidadHashtags() {
		List<Hashtag> l1 = f.getHashtags();
		List<Hashtag> l2 = f2.getHashtags();

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
