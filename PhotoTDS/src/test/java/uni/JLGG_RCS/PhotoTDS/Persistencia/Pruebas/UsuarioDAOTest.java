package uni.JLGG_RCS.PhotoTDS.Persistencia.Pruebas;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;
import uni.JLGG_RCS.PhotoTDS.Persistencia.DAOException;
import uni.JLGG_RCS.PhotoTDS.Persistencia.FactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.MySQLFactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.UsuarioDAO;

public class UsuarioDAOTest {
	
	private FactoriaDAO factoria;
	
	@Before
	public void setFactoria() {
		try {
			factoria = MySQLFactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Excepción al obtener la factoria DAO");
		}
	}
	
	@Test
	public void testUsuario() {
		UsuarioDAO usDAO = factoria.getUsuarioDAO();
		Usuario u = new Usuario("Pepito", "leTongue", new Date(), "letongue56@hotmail.es", "aaaa");
		
		usDAO.create(u);
		
		String newPassword = "bbbb";
		u.setPassword(newPassword);
		u.setPremium(true);

		Usuario u2 = usDAO.get(u.getId());
		
		if (u2 == null) {
			fail("No se ha creado bien el usuario en la base de datos");
		}
		
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
