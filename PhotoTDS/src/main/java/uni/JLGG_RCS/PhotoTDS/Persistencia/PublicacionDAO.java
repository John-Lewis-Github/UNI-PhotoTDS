package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.util.List;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;

public interface PublicacionDAO {
	
	/**
	 * Introduce una nueva publicacion dentro del servicio de persistencia y le 
	 * asigna un identificador, de forma que se pueda consultar, modificar o borrar
	 * 
	 * @param publicacion la publicacion a introducir
	 */
	void create(Publicacion publicacion);
	
	/**
	 * Elimina a una publicacion del servicio de persistencia, de forma que no
	 * se pueda consultar o modificar de nuevo.
	 * 
	 * @param publicacion la publicacion a borrar
	 */
	boolean delete(Publicacion publicacion);
	
	/**
	 * Actualiza la informacion correspondiente a una publicacion en el servicio
	 * de persistencia.
	 * 
	 * @param publicacion la publicacion a actualizar
	 */
	void update(Publicacion publicacion);
	
	/**
	 * Recupera una publicacion del servicio de persistencia, a traves de su 
	 * identificador
	 * 
	 * @param id el identificador de la publicacion
	 * @return la publicacion asociada al identificador
	 */
	Publicacion get(int id);
	
	/** 
	 * Recupera todas las publicaciones almacenadas por el servicio de persistencia
	 * 
	 * @return una lista con todas las publicaciones
	 */
	List<Publicacion> getAll();
}
