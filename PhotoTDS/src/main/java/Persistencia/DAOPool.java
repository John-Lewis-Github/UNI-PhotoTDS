package Persistencia;

import java.util.concurrent.ConcurrentHashMap;

import beans.Entidad;

public enum DAOPool {
	INSTANCE;
	
	private ConcurrentHashMap<Integer,Entidad> pool;
	
	private DAOPool() {
		pool = new ConcurrentHashMap<Integer,Entidad>();
	}
	
	/**
	 * Guarda la entidad en el Pool a traves de su id
	 * @param id el identificador
	 * @param entidad la entidad a guardar
	 */
	public void addObject(int id, Entidad entidad) { 
		pool.putIfAbsent(id, entidad);
	}
	
	/**
	 * Obtiene una entidad a partir de su identificador
	 * Si la entidad no pertenece al Pool, devuelve null
	 * @param id el identificador
	 * @return La entidad guardada, o null si no se encuentra en el Pool
	 */
	public Entidad getEntidad(int id) {
		return pool.getOrDefault(id, null);
	}
	
	/**
	 * Comprueba si la entidad con identificador id pertenece al Pool
	 * 
	 * Es preferible usar getObject directamente antes que comprobar
	 * si una entidad pertenece al Pool y luego recuperarla, por
	 * motivos de concurrencia
	 * 
	 * @param id el identificador de la entidad
	 * @return true si la entidad con identificador id pertenece al Pool,
	 * y false si no
	 */
	public boolean contains(int id){
		return pool.containsKey(id);
	}
}

