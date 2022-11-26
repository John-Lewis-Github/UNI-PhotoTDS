package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.util.List;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;

public interface PublicacionDAO {
	
	void create(Publicacion publicacion);
	boolean delete(Publicacion Publicacion);
	void update(Publicacion Publicacion);
	Publicacion get(int id);
	List<Publicacion> getAll();
}
