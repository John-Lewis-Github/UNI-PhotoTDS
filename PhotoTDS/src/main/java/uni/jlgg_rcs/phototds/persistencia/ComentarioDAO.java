package uni.jlgg_rcs.phototds.persistencia;

import java.util.List;

import uni.jlgg_rcs.phototds.dominio.Comentario;

public interface ComentarioDAO {
	
	/**
	 * Introduce un nuevo comentario dentro del servicio de persistencia y le asigna
	 * un identificador, de forma que se pueda consultar, modificar o borrar
	 * 
	 * No tiene efecto si el comentario ya habia sido registrado, es decir, es una 
	 * operacion idempotente
	 * 
	 * @param comentario el comentario a introducir
	 */
	void create(Comentario comentario);
	
	/**
	 * Elimina a un comentario del servicio de persistencia, de forma que no
	 * se pueda consultar o modificar de nuevo.
	 * 
	 * @param comentario el comentario a borrar
	 */
	boolean delete(Comentario comentario);
	
	/**
	 * Actualiza la informacion correspondiente a un comentario en el servicio
	 * de persistencia.
	 * 
	 * @param comentario el comentario a actualizar
	 */
	void update(Comentario comentario);
	
	/**
	 * Recupera un comentario del servicio de persistencia, a traves de su 
	 * identificador
	 * 
	 * @param id el identificador del comentario
	 * @return el comentario asociado al identificador
	 */
	Comentario get(int id);
	
	/** 
	 * Recupera todos los comentarios almacenados por el servicio de persistencia
	 * 
	 * @return una lista con todos los comentarios
	 */
	List<Comentario> getAll();
}