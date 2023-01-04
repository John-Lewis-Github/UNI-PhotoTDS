package uni.jlgg_rcs.phototds.persistencia;

import java.util.concurrent.ConcurrentHashMap;

import uni.jlgg_rcs.phototds.dominio.Persistente;

public enum DAOPool {
	INSTANCE;
	
	private ConcurrentHashMap<Integer,Persistente> pool;
	
	private DAOPool() {
		pool = new ConcurrentHashMap<Integer,Persistente>();
	}
	
	/**
	 * Guarda un objeto persistente en el Pool a traves de su id
	 * @param id el identificador
	 * @param object la el objeto persistente a guardar
	 */
	public void addObject(int id, Persistente object) { 
		pool.put(id, object);
	}
	
	/**
	 * Obtiene una entidad a partir de su identificador
	 * Si la entidad no pertenece al Pool, devuelve null
	 * @param id el identificador
	 * @return El objeto persistente guardado, o null si no se encuentra en el Pool
	 */
	public Persistente getObject(int id) {
		return pool.getOrDefault(id, null);
	}
	
	/**
	 * Comprueba si el objeto con identificador id pertenece al Pool
	 * 
	 * Es preferible usar getObject directamente antes que comprobar
	 * si un objeto pertenece al Pool y luego recuperarlo, por
	 * motivos de concurrencia
	 * 
	 * @param id el identificador del objeto persistente
	 * @return true si el objeto pertenece al Pool, y false si no
	 */
	public boolean contains(int id){
		return pool.containsKey(id);
	}
	
	public void removeObject(int id) {
		pool.remove(id);
	}
}

