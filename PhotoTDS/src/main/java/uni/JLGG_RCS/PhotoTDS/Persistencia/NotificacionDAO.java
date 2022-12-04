package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.util.List;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;

public interface NotificacionDAO {
	
	/**
	 * Introduce una nueva notificacion dentro del servicio de persistencia y le 
	 * asigna un identificador, de forma que se pueda consultar, modificar o borrar
	 * 
	 * @param notificacion la notificacion a introducir
	 */
	void create(Notificacion notificacion);
	
	/**
	 * Elimina a una notificacion del servicio de persistencia, de forma que no
	 * se pueda consultar o modificar de nuevo.
	 * 
	 * @param notificacion la notificacion a borrar
	 */
	boolean delete(Notificacion notificacion);
	
	/**
	 * Actualiza la informacion correspondiente a una notificacion en el servicio
	 * de persistencia.
	 * 
	 * @param notificacion la notificacion a actualizar
	 */
	void update(Notificacion notificacion);
	
	/**
	 * Recupera una notificacion del servicio de persistencia, a traves de su 
	 * identificador
	 * 
	 * @param id el identificador de la notificacion
	 * @return la notificacion asociada al identificador
	 */
	Notificacion get(int id);
	
	/** 
	 * Recupera todas las notificaciones almacenadas por el servicio de persistencia
	 * 
	 * @return una lista con todas las notificaciones
	 */
	List<Notificacion> getAll();
}