package uni.jlgg_rcs.phototds.persistencia;

import java.util.List;

import uni.jlgg_rcs.phototds.dominio.Usuario;

public interface UsuarioDAO {
	
	/**
	 * Introduce un nuevo usuario dentro del servicio de persistencia y le asigna
	 * un identificador, de forma que se pueda consultar, modificar o borrar.
	 * 
	 * No tiene efecto si el usuario ya habia sido registrado, es decir, es una 
	 * operacion idempotente
	 * 
	 * @param usuario el usuario a introducir
	 */
	void create(Usuario usuario);
	
	/**
	 * Elimina a un usuario del servicio de persistencia, de forma que no
	 * se pueda consultar o modificar de nuevo.
	 * 
	 * @param usuario el usuario a borrar
	 */
	boolean delete(Usuario usuario);
	
	/**
	 * Actualiza la informacion correspondiente a un usuario en el servicio
	 * de persistencia.
	 * 
	 * @param usuario el usuario a actualizar
	 */
	void update(Usuario usuario);
	
	/**
	 * Recupera un usuario del servicio de persistencia, a traves de su 
	 * identificador
	 * 
	 * @param id el identificador del usuario
	 * @return el usuario asociado al identificador
	 */
	Usuario get(int id);
	
	/** 
	 * Recupera todos los usuarios almacenados por el servicio de persistencia
	 * 
	 * @return una lista con todos los usuarios
	 */
	List<Usuario> getAll();
}
