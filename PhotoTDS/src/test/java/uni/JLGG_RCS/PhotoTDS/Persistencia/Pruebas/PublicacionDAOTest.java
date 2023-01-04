package uni.JLGG_RCS.PhotoTDS.Persistencia.Pruebas;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;
import uni.JLGG_RCS.PhotoTDS.Persistencia.DAOException;
import uni.JLGG_RCS.PhotoTDS.Persistencia.FactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.MySQLFactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.PublicacionDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.UsuarioDAO;

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
			factoria = MySQLFactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
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
