package uni.JLGG_RCS.PhotoTDS.Persistencia.Pruebas;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;
import uni.JLGG_RCS.PhotoTDS.Persistencia.DAOException;
import uni.JLGG_RCS.PhotoTDS.Persistencia.FactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.MySQLFactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.MySQLUsuarioDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.NotificacionDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.PublicacionDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.UsuarioDAO;

public class UsuarioDAOTest {
	
	private FactoriaDAO factoria;
	private UsuarioDAO usDAO;

	private Usuario u;
	private Foto fotoPerfil;
	private Album album;
	private Notificacion noti;
	
	@Before
	public void setFactoria() {
		try {
			factoria = MySQLFactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Excepción al obtener la factoria DAO");
		}
		
		usDAO = factoria.getUsuarioDAO();

		u = new Usuario("Pepito", "leTongue", new Date(), "letongue56@hotmail.es", "aaaa");
		fotoPerfil = new Foto("Titulo", "Descripcion", "/home/ruben/Firefox_wallpaper.png");
		fotoPerfil.setUsuario(u);
		u.addFoto(fotoPerfil);
		u.setFotoPerfil(fotoPerfil);
		
		album = new Album("Titulo album", "descripcion");
		album.setUsuario(u);
		
		Usuario u2 = new Usuario("Pepitoasdf", "leTongueasdf", new Date(), "letongue56@hotmail.es", "aaaa");
		
		Foto foto2 = new Foto("asdf", "asdf");
		foto2.setUsuario(u2);
		noti = new Notificacion(foto2);
				
		album.addFoto(fotoPerfil);
		
		u.setFotoPerfil(fotoPerfil);
		
		u.setPresentacion("hola buenas vengo a probar un DAO");
		u.setPremium(true);
		u.addAlbum(album);
		u.addNotificacion(noti);
		
	}
	
	@Test
	public void testUsuarioCreate() {
		
		usDAO.create(u);

		Usuario u2 = usDAO.get(u.getId());
		
		if (u2 == null)
			fail("No se ha creado bien el usuario en la base de datos");
		
		if (!u2.getNombreCompleto().equals(u.getNombreCompleto()) || 
				!u2.getNombreUsuario().equals(u.getNombreUsuario()) || 
				!u2.getEmail().equals(u.getEmail()) ||
				!u2.getPassword().equals(u.getPassword()) ||
				!u2.getPresentacion().equals(u.getPresentacion()))
			fail("Los perfiles de usuario no coinciden");
		
		if (u2.getFotoPerfil() == null)
			fail("No se ha guardado bien la foto de perfil");
				
		String newPassword = "bbbb";
		u.setPassword(newPassword);
		u.setPremium(false);
		
		u2 = usDAO.get(u.getId());

		if (u2.getPassword().equals(u.getPassword())) {
			fail("No se han guardado todavía los cambios");
		}
		
		if (u2.isPremium() == u.isPremium()) {
			fail("No se han guardado todavía los cambios");
		}
		
		usDAO.update(u);
		u2 = usDAO.get(u.getId());
				
		if (!u2.getPassword().equals(u.getPassword())) {
			fail("No se han guardado bien los cambios");
		}
		
		if (u2.isPremium() != u.isPremium()) {
			fail("No se han guardado bien los cambios");
		}
		
	}

}
