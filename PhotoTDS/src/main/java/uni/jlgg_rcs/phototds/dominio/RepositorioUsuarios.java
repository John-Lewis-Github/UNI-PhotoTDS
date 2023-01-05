package uni.jlgg_rcs.phototds.dominio;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uni.jlgg_rcs.phototds.persistencia.DAOException;
import uni.jlgg_rcs.phototds.persistencia.FactoriaDAO;
import uni.jlgg_rcs.phototds.persistencia.UsuarioDAO;

public enum RepositorioUsuarios {
	INSTANCE;

	private Map<String, Usuario> usuarios;
	
	private RepositorioUsuarios() {
		usuarios = new HashMap<>();
	}
	
	/**
	 * Incluye a un usuario en el repositorio de usuarios, y lo
	 * guarda como objeto persistente. Para ello es necesario que
	 * no exista un usuario igual (con el mismo nombre) en la base de datos.
	 * 
	 * @param usuario el usuario a crear
	 * @return true si se ha podido crear el usuario, false si no
	 */
	public boolean addUsuario(Usuario usuario) {
		Usuario previo = usuarios.putIfAbsent(usuario.getNombreUsuario(), usuario);
		return previo == null;
	}
	
	/**
	 * Incluye una coleccion de usuarios en el repositorio de usuarios
	 * 
	 * @param coll una coleccion de usuarios
	 */
	public void addUsuarios(Collection<Usuario> coll) {
		coll.stream().forEach(u -> addUsuario(u));
	}
	
	/**
	 * Devuelve una lista de usuarios cuyo nombre completo, nombre de
	 * usuario o email contiene el patron descrito
	 * 
	 * @param patron una cadena con la que buscar al usuario
	 * @return una lista de usuarios que cumplen la condicion
	 */
	public List<Usuario> findUsuarios(String patron) {
		String minuscula = patron.toLowerCase();
		List<Usuario> porNombreUsuario = usuarios.keySet().stream()
				.filter(s -> s.toLowerCase().contains(minuscula))
				.map(s -> usuarios.get(s))
				.collect(Collectors.toList());
		List<Usuario> porNombreCompleto = usuarios.values().stream()
				.filter(u -> u.getNombreCompleto().toLowerCase().contains(minuscula))
				.collect(Collectors.toList());
		List<Usuario> porEmail = usuarios.values().stream()
				.filter(u -> u.getEmail().toLowerCase().contains(minuscula))
				.collect(Collectors.toList());
		
		List<Usuario> listaUsuarios = new LinkedList<Usuario>();
		listaUsuarios.addAll(porNombreCompleto);
		listaUsuarios.addAll(porNombreUsuario);
		listaUsuarios.addAll(porEmail);
		
		return listaUsuarios;
	}
	
	/**
	 * Recupera un usuario a partir de su nombre de usuario y su password
	 * 
	 * @param nombreUsuario el nombre de usuario del usuario
	 * @param password el password del usuario
	 * @return el usuario que con tales atributos, o null si no se encuentra
	 */
	public Usuario recuperarUsuario(String nombreUsuario, String password) {
		Usuario usuario = usuarios.get(nombreUsuario);
		if ((usuario != null) && (password.equals(usuario.getPassword())))
			return usuario;
		else
			return null;
	}
}
