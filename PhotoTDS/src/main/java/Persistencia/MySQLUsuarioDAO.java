package Persistencia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public enum MySQLUsuarioDAO implements UsuarioDAO {
	INSTANCE;
	
	private static final String NOMBRE_COMPLETO = "nombreCompleto";
	private static final String NOMBRE_USUARIO = "nombreUsuario";
	private static final String FECHA = "fechaNacimiento";
	private static final String EMAIL = "email";
	private static final String PRESENTACION = "presentacion";
	private static final String CONTRASEÑA = "contraseña";
	private static final String FOTO_PERFIL = "fotoPerfil";
	private static final String SEGUIDOR = "seguidor";
	private static final String FOTO = "foto";
	private static final String ALBUM = "album";
	private static final String NOTIFICACION = "notificacion";

	private ServicioPersistencia serv;
	private DateFormat dformat;
	
	private MySQLUsuarioDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Crea una nueva entidad a partir de un usuario
	 * @param usuario El usuario a partir del que formar una entidad
	 * @return Una entidad creada a partir del usuario
	 */
	public Entidad UsuarioAEntidad(Usuario usuario) {
		
		Entidad entidad = new Entidad();

		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE_COMPLETO, usuario.getNombreCompleto()),
				new Propiedad(NOMBRE_USUARIO, usuario.getNombreUsuario()),
				new Propiedad(FECHA, dformat.format(usuario.getFechaNacimiento())),
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(PRESENTACION, usuario.getPresentacion()),
				new Propiedad(CONTRASEÑA, usuario.getContraseña()),
				new Propiedad(FOTO_PERFIL, Integer.toString(usuario.getFotoPerfil().getId())),
				new Propiedad(SEGUIDOR, "a")
				));
		
		/**
		 *  Los objetos referenciados mediante listas se representaran
		 *  a partir de sus identificadores, junto con una palabra clave
		 *  que indica el tipo de objeto.
		 */
		listaPropiedades.addAll(usuario.getSeguidores().stream()
				.map(seguidor -> seguidor.getId())
				.map(id -> Integer.toString(id))
				.map(s -> new Propiedad(SEGUIDOR, s))
				.toList());
		
		listaPropiedades.addAll(usuario.getAlbumes().stream()
				.map(album -> album.getId())
				.map(id -> Integer.toString(id))
				.map(s -> new Propiedad(ALBUM, s))
				.toList());
		
		listaPropiedades.addAll(usuario.getFotos().stream()
				.map(foto -> foto.getId())
				.map(id -> Integer.toString(id))
				.map(s -> new Propiedad(FOTO, s))
				.toList());
		
		listaPropiedades.addAll(usuario.getNotificaciones().stream()
				.map(notificacion -> notificacion.getId())
				.map(id -> Integer.toString(id))
				.map(s -> new Propiedad(NOTIFICACION, s))
				.toList());
		
		entidad.setPropiedades(listaPropiedades);
		return null;
	}
	
	public Usuario EntidadAUsuario(Entidad entidad) { 
		String nombreCompleto;
		String nombreUsuario;
		String fechaNacimiento;
		String email;
		String presentacion;
		String contraseña;
		Foto fotoPerfil;
		
		List<Usuario> seguidores = new ArrayList<Usuario>();
		List<Album> albumes = new ArrayList<Album>();
		List<Foto> fotos = new ArrayList<Foto>();
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		for (Propiedad p : entidad.getPropiedades()) {
			switch (p.getNombre()) {
			case NOMBRE_COMPLETO:
				nombreCompleto = p.getValor();
				break;
			case NOMBRE_USUARIO:
				nombreUsuario = p.getValor();
				break;
			case FECHA:
				nombreUsuario = p.getValor();
				break;
			case EMAIL:
				email = p.getValor();
				break;
			case PRESENTACION:
				nombreUsuario = p.getValor();
				break;
			case CONTRASEÑA:
				nombreUsuario = p.getValor();
				break;
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public void create(Usuario usuario) {
		Entidad eUsuario = serv.recuperarEntidad(usuario.getId());
		
		// Si el usuario ya ha sido creado, no se hace nada
		if (eUsuario != null) {
			return;
		}
		
		eUsuario = new Entidad();
		
		// TODO Crear propiedades de la entidad
		
		serv.registrarEntidad(eUsuario);

	}

	@Override
	public boolean delete(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
