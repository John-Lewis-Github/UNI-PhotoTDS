package uni.JLGG_RCS.PhotoTDS.Controlador;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import umu.tds.fotos.ComponenteCargadorFotos;
import umu.tds.fotos.Fotos;
import umu.tds.fotos.FotosEvent;
import umu.tds.fotos.FotosListener;
import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.GeneradorExcel;
import uni.JLGG_RCS.PhotoTDS.Dominio.GeneradorPDF;
import uni.JLGG_RCS.PhotoTDS.Dominio.Hashtag;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioHashtags;
import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioPublicaciones;
import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioUsuarios;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;
import uni.JLGG_RCS.PhotoTDS.Persistencia.DAOException;
import uni.JLGG_RCS.PhotoTDS.Persistencia.FactoriaDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.PublicacionDAO;
import uni.JLGG_RCS.PhotoTDS.Persistencia.UsuarioDAO;

public enum Controlador implements FotosListener{
	INSTANCE;
	
	private static final int MAX_PUBLICACIONES_USUARIO = 20;
	 private static final String RUTA_DEFAULT_PROF_PIC = "/default_user_pic.png";

	
	private FactoriaDAO fact;
	private UsuarioDAO usDAO;
	private PublicacionDAO<Foto> fotoDAO;
	private PublicacionDAO<Album> albumDAO;
	
	private RepositorioUsuarios usRepo;
	private RepositorioPublicaciones pubRepo;
	private RepositorioHashtags hashRepo;
	
	private GeneradorExcel genExcel;
	private GeneradorPDF genPDF;
	
	private ComponenteCargadorFotos cargador;
	
	private Usuario usuario;
	
	private Controlador() {
		
		try {
			fact = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		usDAO = fact.getUsuarioDAO();
		fotoDAO = fact.getFotoDAO();
		albumDAO = fact.getAlbumDAO();
		
		usRepo = RepositorioUsuarios.INSTANCE;
		usRepo.addUsuarios(usDAO.getAll());
		
		pubRepo = RepositorioPublicaciones.INSTANCE;
		pubRepo.addPublicaciones(fotoDAO.getAll());
		pubRepo.addPublicaciones(albumDAO.getAll());
		
		hashRepo = RepositorioHashtags.INSTANCE;
		
		genExcel = GeneradorExcel.INSTANCE;
		genPDF = GeneradorPDF.INSTANCE;
		
		cargador = ComponenteCargadorFotos.INSTANCE;
		cargador.addFotosListener(this);
	}
	
	/**
	 * Registra un usuario en la aplicacion
	 * 
	 * @param usuario el usuario a registrar
	 * @return true si se ha podido registrar al usuario, false si no
	 */
	public boolean registrarUsuario(Usuario usuario) {
		
		// Si no estaba, se crea en la base de datos
		if (usRepo.addUsuario(usuario)) {
			usDAO.create(usuario);
			return true;
		} else {
			return false;
		}
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
	public boolean recuperarUsuario(String nombreUsuario, String password) {
		usuario = usRepo.recuperarUsuario(nombreUsuario, password);
		return usuario != null;
	}
	
	/**
	 * Devuelve el usuario que ha hecho login en la aplicacion. No tiene 
	 * sentido llamar a esta funcion si no se ha logeado ningun usuario
	 * todavia. 
	 * 
	 * @return el usuario logeado, o null si todavia no se ha logeado ninguno
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * Devuelve una lista de hasta 20 publicaciones del usuario de la 
	 * aplicacion o sus seguidores, ordenadas por fecha: las mas recientes
	 * primero
	 * 
	 * @param nombreUsuario el nombre del usuario
	 * @return una lista de publicaciones
	 */
	public List<Publicacion> getListaPrincipalUsuario(){
		return usuario.getPublicacionesRelevantes().stream()
				.sorted((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()))
				.limit(MAX_PUBLICACIONES_USUARIO)
				.toList();
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
	
	/**
	 * Publica una foto para el usuario logeado a partir de su
	 * descripcion y una ruta al fichero de la imagen
	 * 
	 * @param comentario la descripcion de la foto
	 * @param path la ruta a la imagen
	 */
	public void publicarImagen(String comentario, String path) {
		// Se obtiene el titulo de la foto como su nombre
		Path nombreFichero = Paths.get(path).getFileName();
		String titulo = nombreFichero.toString();
		
		// Se crea y registra la foto
		Foto foto = new Foto(titulo, comentario, path);
		pubRepo.addPublicacion(foto);
		fotoDAO.create(foto);
		
		// Se publica la foto
		usuario.publicarFoto(foto);
	}
	
	public Image getImagenUsuario(Usuario usuario) {
		Image imagen = usuario.getFotoPerfil();
		if (imagen == null)
			try {
				imagen = ImageIO.read(this.getClass().getResource(RUTA_DEFAULT_PROF_PIC));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return imagen;
	}
	
	public Image getImagenUsuario(Publicacion publicacion) {
		return getImagenUsuario(publicacion.getUsuario());
	}
	
	/**
	 * Genera un archivo excel con la informacion de los seguidores
	 * del usuario
	 */
	public void generarExcel() {
		if (usuario.isPremium())
			genExcel.generarExcel(usuario);
	}
	
	/**
	 * Genera un archivo PDF con la informacion de los seguidores
	 * del usuario
	 */
	public void generarPDF() {
		if (usuario.isPremium())
			genPDF.generarPDF(usuario);
	}

	/**
	 * Cuando el componente cargador de fotos toma un archivo XML
	 * y obtiene una serie de fotos, el controlador se encarga de 
	 * subir todas estas fotos 
	 */
	@Override
	public void notificaNuevasFotos(FotosEvent e) {
		// Se obtienen las fotos del cargador
		Fotos fotos = cargador.nuevasFotos();
		
		// Para cada una
		for (umu.tds.fotos.Foto f : fotos.getFoto()) {
			// Se obtienen los atributos
			String titulo = f.getTitulo();
			String descripcion = f.getDescripcion();
			String path = f.getPath();
			
			// Se crea una instancia de Foto para PhotoTDS
			Foto foto = new Foto(titulo, descripcion, path);
			
			// Se incluyen los hashtags
			List<Hashtag> hashtags = f.getHashTags().stream()
					.map(h -> h.getHashTag().get(0))
					.filter(s -> Hashtag.verificaHashtag(s))
					.map(s -> hashRepo.getHashtag(s))
					.toList();
			foto.addHashtags(hashtags);
			
			// Se crea y registra la foto
			pubRepo.addPublicacion(foto);
			fotoDAO.create(foto);
			
			// Se publica la foto
			usuario.publicarFoto(foto);
		}
	}

	/**
	 * Permite darle "meGusta" a una publicacion
	 * @param publicacion la publicacion a la que darle "meGusta"
	 */
	public void darMeGusta(Publicacion publicacion) {
		publicacion.darMeGusta();
	}
	
}
