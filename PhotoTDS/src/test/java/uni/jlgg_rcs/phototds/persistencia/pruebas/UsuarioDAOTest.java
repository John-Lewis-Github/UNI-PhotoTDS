package uni.jlgg_rcs.phototds.persistencia.pruebas;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uni.jlgg_rcs.phototds.dominio.Album;
import uni.jlgg_rcs.phototds.dominio.Foto;
import uni.jlgg_rcs.phototds.dominio.Notificacion;
import uni.jlgg_rcs.phototds.dominio.Usuario;
import uni.jlgg_rcs.phototds.persistencia.DAOException;
import uni.jlgg_rcs.phototds.persistencia.FactoriaDAO;
import uni.jlgg_rcs.phototds.persistencia.H2FactoriaDAO;
import uni.jlgg_rcs.phototds.persistencia.UsuarioDAO;


public class UsuarioDAOTest {
	
	private FactoriaDAO factoria;
	private UsuarioDAO usDAO;

	private Usuario u;
	private Foto foto;
	private Album album;
	private Notificacion noti;
	
	@Before
	public void setFactoria() {
		try {
			factoria = H2FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Excepción al obtener la factoria DAO");
		}
		
		usDAO = factoria.getUsuarioDAO();

		u = new Usuario("Pepito", "leTongue", new Date(), "letongue56@hotmail.es", "aaaa", "./resources/default_user_pic.png");
		foto = new Foto("Titulo", "Descripcion", "/home/ruben/Firefox_wallpaper.png");
		u.publicarFoto(foto);
		
		album = new Album("Titulo album", "descripcion");
		u.publicarAlbum(album);
		
		Usuario u2 = new Usuario("Pepitoasdf", "leTongueasdf", new Date(), "letongue56@hotmail.es", "aaaa");
		
		Foto foto2 = new Foto("asdf", "asdf");
		foto2.setUsuario(u2);
		noti = new Notificacion(foto2);
				
		album.addFoto(foto);
				
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
