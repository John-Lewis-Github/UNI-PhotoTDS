package uni.jlgg_rcs.phototds.persistencia.pruebas;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uni.jlgg_rcs.phototds.dominio.Album;
import uni.jlgg_rcs.phototds.dominio.Foto;
import uni.jlgg_rcs.phototds.dominio.Usuario;
import uni.jlgg_rcs.phototds.persistencia.DAOException;
import uni.jlgg_rcs.phototds.persistencia.FactoriaDAO;
import uni.jlgg_rcs.phototds.persistencia.H2FactoriaDAO;
import uni.jlgg_rcs.phototds.persistencia.PublicacionDAO;

public class PublicacionDAOTest {

	private FactoriaDAO factoria;
	private PublicacionDAO<Foto> fotoDAO;
	private PublicacionDAO<Album> albumDAO;

	private Usuario u1;
	private Usuario u2;
	private Foto foto;
	private Album album;
	
	@Before
	public void setFactoria() {
		try {
			factoria = H2FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
			fail("Excepción al obtener la factoria DAO");
		}
		
		fotoDAO = factoria.getFotoDAO();
		albumDAO = factoria.getAlbumDAO();
		
		u1 = new Usuario("Pepito", "Pepito1", new Date(), "letongue56@hotmail.es", "aaaa");
		u2 = new Usuario("Paquita", "PacaAlpaca", new Date(), "alpaca56@hotmail.es", "aaaa");
		foto = new Foto("Titulo", "Descripcion", "/home/ruben/Firefox_wallpaper.png");
		album = new Album("Titulo album", "Nuevo album");
		u1.publicarFoto(foto);
		u2.publicarAlbum(album);
	}
	
	@Test
	public void testFotoCreate() {
		
		if (foto.getId() != null)
			fail("La foto no deberia estar guardada");
		
		fotoDAO.create(foto);

		if (foto.getId() == null)
			fail("La foto deberia estar guardada");
		
		Foto foto2 = fotoDAO.get(foto.getId());
		
		if (foto2 == null)
			fail("No se ha creado bien la foto en la base de datos");
		
		if (foto2.getUsuario() == null)
			fail("No se ha guardado bien el usuario");
	}
	
	@Test
	public void testAlbumCreate() {
		
		if (album.getId() != null)
			fail("La foto no deberia estar guardada");
		
		albumDAO.create(album);

		if (album.getId() == null)
			fail("La foto deberia estar guardada");
		
		Album album2 = albumDAO.get(album.getId());
		
		if (album2 == null)
			fail("No se ha creado bien la foto en la base de datos");
		
		if (album2.getUsuario() == null)
			fail("No se ha guardado bien el usuario");
	}


}
