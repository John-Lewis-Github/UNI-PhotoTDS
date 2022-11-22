package Persistencia;

import java.util.List;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public interface UsuarioDAO {
	
	void create(Usuario usuario);
	boolean delete(Usuario usuario);
	void update(Usuario usuario);
	Usuario get(int id);
	List<Usuario> getAll();
}
