package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.util.List;

import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;

public interface NotificacionDAO {

	void create(Notificacion notificacion);
	boolean delete(Notificacion notificacion);
	void update(Notificacion notificacion);
	Notificacion get(int id);
	List<Notificacion> getAll();
}
