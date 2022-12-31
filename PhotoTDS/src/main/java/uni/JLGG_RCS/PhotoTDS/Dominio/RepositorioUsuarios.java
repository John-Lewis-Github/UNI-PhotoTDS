package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.LinkedList;
import java.util.List;

import uni.JLGG_RCS.PhotoTDS.Persistencia.DAOException;
import uni.JLGG_RCS.PhotoTDS.Persistencia.FactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.UsuarioDAO;

public enum RepositorioUsuarios {
	INSTANCE;

	private FactoriaDAO fact;
	
	private UsuarioDAO usDAO;
	private List<Usuario> usuarios;
	
	private RepositorioUsuarios() {
		try {
			fact = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		usDAO = fact.getUsuarioDAO();
		usuarios = usDAO.getAll();
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
		boolean existente = usuarios.stream()
				.anyMatch(u -> u.equals(usuario));
		
		if (!existente) {
			usuarios.add(usuario);
			usDAO.create(usuario);
		}
		
		return !existente;
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
		List<Usuario> porNombreUsuario = usuarios.stream()
				.filter(u -> u.getNombreUsuario().toLowerCase().contains(minuscula))
				.toList();
		List<Usuario> porNombreCompleto = usuarios.stream()
				.filter(u -> u.getNombreCompleto().toLowerCase().contains(minuscula))
				.toList();
		List<Usuario> porEmail = usuarios.stream()
				.filter(u -> u.getEmail().toLowerCase().contains(minuscula))
				.toList();
		
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
		// Filtra por nombre y password
		return usuarios.stream()
				.filter(u -> u.getNombreUsuario().equals(nombreUsuario))
				.filter(u -> u.getPassword().equals(password))
				.findAny()
				.orElse(null);
	}
}
