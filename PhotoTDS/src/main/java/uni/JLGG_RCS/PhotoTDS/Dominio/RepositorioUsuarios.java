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
	
	public void addUsuario(Usuario usuario) {
		usuarios.add(usuario);
		usDAO.create(usuario);
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
}
