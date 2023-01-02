package uni.JLGG_RCS.PhotoTDS.Controlador;

import java.util.LinkedList;
import java.util.List;

import uni.JLGG_RCS.PhotoTDS.Dominio.GeneradorExcel;
import uni.JLGG_RCS.PhotoTDS.Dominio.GeneradorPDF;
import uni.JLGG_RCS.PhotoTDS.Dominio.Hashtag;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioHashtags;
import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioPublicaciones;
import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioUsuarios;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public enum Controlador {
	INSTANCE;
	
	private RepositorioUsuarios usRepo;
	private RepositorioPublicaciones pubRepo;
	private RepositorioHashtags hashRepo;
	
	private GeneradorExcel genExcel;
	private GeneradorPDF genPDF;
	
	private Usuario usuario;
	
	private Controlador() {
		usRepo = RepositorioUsuarios.INSTANCE;
		pubRepo = RepositorioPublicaciones.INSTANCE;
		hashRepo = RepositorioHashtags.INSTANCE;
		
		genExcel = GeneradorExcel.INSTANCE;
		genPDF = GeneradorPDF.INSTANCE;
	}
	
	/**
	 * Registra un usuario en la aplicacion
	 * 
	 * @param usuario el usuario a registrar
	 * @return true si se ha podido registrar al usuario, false si no
	 */
	public boolean registrarUsuario(Usuario usuario) {
		
		return usRepo.addUsuario(usuario);
	}
	
	/**
	 * Recupera un usuario a partir de su nombre de usuario y su password
	 * En caso de que no exista un usuario con tales caracteristicas se 
	 * devuelve null. Es la forma de obtener un usuario en la ventana de 
	 * login.
	 * 
	 * @param nombreUsuario el nombre de usuario del usuario
	 * @param password el password del usuario
	 * @return el usuario con tales atributos, o null si no se encuentra
	 */
	public Usuario recuperarUsuario(String nombreUsuario, String password) {
		return usuario = usRepo.recuperarUsuario(nombreUsuario, password);
	}
	
	/**
	 * Busca usuarios con un patron. Devuelve todos los usuarios cuyo nombre
	 * completo, nombre de usuario o email contiene al patron descrito. Es 
	 * case-insensitive.
	 * 
	 * Por ejemplo, si el patron es "ani pedro", entonces el usuario de nombre
	 * completo "Dani Pedrosa" sera devuelto, pero no el usuario de nombre de usuario
	 * "ani_pedro@gmail.com"
	 * 
	 * @param patron una cadena con la que buscar usuarios
	 * @return una lista de usuarios que cumplen la condicion
	 */
	public List<Usuario> findUsuarios(String patron) {
		return usRepo.findUsuarios(patron);
	}
	
	/**
	 * Busca publicaciones a partir de una cadena hashtag. La cadena de hashtag
	 * puede no cumplir las condiciones necesarias para considerarse hashtag (16 letras
	 * mayusculas o minusculas contando el simbolo inicial '#', que es opcional), y
	 * en tal caso no se devolveran publicaciones
	 * 
	 * @param cadenaHashtag la cadena que representa el hashtag de las publicaciones
	 * @return una lista de publicaciones que contienen el hashtag descrito
	 */
	public List<Publicacion> findPublicaciones(String cadenaHashtag) {
		if (!Hashtag.verificaHashtag(cadenaHashtag))
			return new LinkedList<Publicacion>();
		
		Hashtag h = hashRepo.getHashtag(cadenaHashtag);
		return pubRepo.findPublicaciones(h);
	}
	
	public void generarExcel(Usuario u) {
		if (u.isPremium())
			genExcel.generarExcel(u);
	}
	
	public void generarPDF(Usuario u) {
		if (u.isPremium())
			genPDF.generarPDF(u);
	}
	
}
