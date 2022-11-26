package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Persistente;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

/**
 * Implementación del Data Access Object UsuarioDAO para el servicio de persistencia
 * de la aplicacion PhotoTDS. Recibe su nombre de la base de datos que usa dicho
 * servicio de persistencia.
 *
 */
public enum MySQLUsuarioDAO implements UsuarioDAO {
	INSTANCE;
	
	private static final String USUARIO = "Usuario";
	
	private static final String NOMBRE_COMPLETO = "nombreCompleto";
	private static final String NOMBRE_USUARIO = "nombreUsuario";
	private static final String FECHA = "fechaNacimiento";
	private static final String EMAIL = "email";
	private static final String PRESENTACION = "presentacion";
	private static final String PASSWORD = "password";
	private static final String FOTO_PERFIL = "fotoPerfil";
	private static final String SEGUIDORES = "seguidores";
	private static final String FOTOS = "fotos";
	private static final String ALBUMES = "albumes";
	private static final String NOTIFICACIONES = "notificaciones";

	private ServicioPersistencia serv;
	private PublicacionDAO pubDAO;
	private NotificacionDAO notiDAO;
	private DAOPool pool;
	private DateFormat dformat;
	
	private MySQLUsuarioDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		pubDAO = MySQLPublicacionDAO.INSTANCE;
		notiDAO = MySQLNotificacionDAO.INSTANCE;
		pool = DAOPool.INSTANCE;
		dformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Construye una cadena con los identificadores de una lista de 
	 * elementos persistentes.
	 * 
	 * @param l la lista con elementos persistentes
	 * @return una cadena con los identificadores separados por espacios
	 */
	private <T extends Persistente> String idList2String(List<T> l) {
		return l.stream()
				.map(e -> e.getId())
				.map(id -> Integer.toString(id))
				.reduce("", (s1, s2) -> s1+" "+s2);
	}
	
	/**
	 * Devuelve una lista de identificadores enteros a partir de una
	 * cadena que los contiene separados por espacios.
	 * 
	 * @param s la cadena con los identificadores
	 * @return una lista de enteros
	 */
	private List<Integer> string2IdList(String s) {
		return Arrays.asList(s.split(" ")).stream()
				.map(e -> Integer.parseInt(e))
				.toList();
	}
	
	/**
	 * Crea una nueva entidad a partir de un usuario
	 * @param usuario El usuario a partir del que formar una entidad
	 * @return Una entidad creada a partir del usuario
	 */
	public Entidad UsuarioAEntidad(Usuario usuario) {
		
		Entidad entidad = new Entidad();
		entidad.setNombre(USUARIO);

		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE_COMPLETO, usuario.getNombreCompleto()),
				new Propiedad(NOMBRE_USUARIO, usuario.getNombreUsuario()),
				new Propiedad(FECHA, dformat.format(usuario.getFechaNacimiento())),
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(PRESENTACION, usuario.getPresentacion()),
				new Propiedad(FOTO_PERFIL, Integer.toString(usuario.getFotoPerfil().getId()))
				));
		
		/**
		 *  Los objetos referenciados mediante listas se representaran
		 *  a partir de sus identificadores, que se concatenarán para
		 *  formar un String
		 */
		String seguidores = idList2String(usuario.getSeguidores());
		listaPropiedades.add(new Propiedad(SEGUIDORES, seguidores));
		
		String fotos = idList2String(usuario.getFotos());
		listaPropiedades.add(new Propiedad(FOTOS, fotos));
		
		String albumes = idList2String(usuario.getAlbumes());
		listaPropiedades.add(new Propiedad(ALBUMES, albumes));
		
		String notificaciones = idList2String(usuario.getNotificaciones());
		listaPropiedades.add(new Propiedad(NOTIFICACIONES, notificaciones));
		
		entidad.setPropiedades(listaPropiedades);
		
		return entidad;
	}
	
	/**
	 * Crea un usuario a partir de una entidad
	 * @param entidad la entidad que genera el usuario
	 * @return un usuario 
	 */
	public Usuario EntidadAUsuario(Entidad entidad) { 
		// Recuperamos atributos que no referencian a entidades
		
		String nombreCompleto = serv.recuperarPropiedadEntidad(entidad, NOMBRE_COMPLETO);
		String nombreUsuario = serv.recuperarPropiedadEntidad(entidad, NOMBRE_USUARIO);
		Date fecha = null;
		try {
			fecha = dformat.parse(serv.recuperarPropiedadEntidad(entidad, FECHA));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String email = serv.recuperarPropiedadEntidad(entidad, EMAIL);
		
		// Creamos el usuario y le asignamos su identificador
		Usuario usuario = new Usuario(nombreCompleto, nombreUsuario, fecha, email);
		usuario.setId(entidad.getId());

		// Lo registramos en el Pool
		pool.addObject(entidad.getId(), usuario);
		
		// Ahora podemos procesar el resto de los atributos, pues no habrá
		// problemas con las referencias bidireccionales
		
		String presentacion = serv.recuperarPropiedadEntidad(entidad, PRESENTACION);
		String password = serv.recuperarPropiedadEntidad(entidad, PASSWORD);

		String fotoPerfilId = serv.recuperarPropiedadEntidad(entidad, NOMBRE_COMPLETO);
		Foto fotoPerfil = (Foto) pubDAO.get(Integer.parseInt(fotoPerfilId));
		
		usuario.setPassword(password);
		usuario.setPresentacion(presentacion);
		usuario.setFotoPerfil(fotoPerfil);
		
		// Se transforman las listas de identificadores enteros a listas de atributos
		// En funcion del tipo de atributo se usan diferentes DAOs
		List<Integer> seguidoresIds = string2IdList(serv.recuperarPropiedadEntidad(entidad, SEGUIDORES));
		List<Usuario> seguidores = seguidoresIds.stream()
				.map(id -> get(id))
				.toList();
		
		List<Integer> fotosIds = string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Foto> fotos = fotosIds.stream()
				.map(id -> (Foto) pubDAO.get(id))
				.toList();
		
		List<Integer> albumesIds = string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Album> albumes = albumesIds.stream()
				.map(id -> (Album) pubDAO.get(id))
				.toList();
		
		List<Integer> notificacionesIds = string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Notificacion> notificaciones = notificacionesIds.stream()
				.map(id -> notiDAO.get(id))
				.toList();

		usuario.addSeguidores(seguidores);
		usuario.addFotos(fotos);
		usuario.addAlbumes(albumes);
		usuario.addNotificaciones(notificaciones);
		return usuario;
	}

	@Override
	public void create(Usuario usuario) {
		/**
		 * Crea directamente una entidad para el nuevo usuario y la registra
		 * mediante el servicio de persistencia. 
		 */
		Entidad entidad = UsuarioAEntidad(usuario);
		serv.registrarEntidad(entidad);
		usuario.setId(entidad.getId());
	}

	@Override
	public boolean delete(Usuario usuario) {
		/**
		 * Borra la entidad que tenga el mismo id que el usuario
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 */
		Entidad entidad = serv.recuperarEntidad(usuario.getId());
		return serv.borrarEntidad(entidad);
	}

	@Override
	public void update(Usuario usuario) {
		// Se recupera la entidad asociada al usuario
		Entidad entidad = serv.recuperarEntidad(usuario.getId());

		// Se actualizan una por una todas sus propiedades
		for (Propiedad p : entidad.getPropiedades()) {
			switch (p.getNombre()) {
			case NOMBRE_COMPLETO:
				p.setValor(usuario.getNombreCompleto());
				break;
			case NOMBRE_USUARIO:
				p.setValor(usuario.getNombreUsuario());
				break;
			case FECHA:
				p.setValor(dformat.format(usuario.getFechaNacimiento()));
				break;
			case EMAIL:
				p.setValor(usuario.getEmail());
				break;
			case PRESENTACION:
				p.setValor(usuario.getPresentacion());
				break;
			case PASSWORD:
				p.setValor(usuario.getPresentacion());
				break;
			case FOTO_PERFIL:
				Foto fotoPerfil = usuario.getFotoPerfil();
				p.setValor(Integer.toString(fotoPerfil.getId()));
				break;
			case SEGUIDORES: 
				p.setValor(idList2String(usuario.getSeguidores()));
				break;
			case FOTOS:
				p.setValor(idList2String(usuario.getFotos()));
				break;
			case ALBUMES:
				p.setValor(idList2String(usuario.getAlbumes()));
				break;
			case NOTIFICACIONES:
				p.setValor(idList2String(usuario.getNotificaciones()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Usuario get(int id) {
		/**
		 * Se comprueba si se ha creado ya una instancia de Usuario a partir de
		 * la entidad que tiene su mismo id. Para ello se consulta directamente
		 * en el Pool. Si no está, entonces se crea la instancia en cuestion.
		 */
		
		Usuario usuario = (Usuario) pool.getObject(id);
		if (usuario == null)
			usuario = EntidadAUsuario(serv.recuperarEntidad(id));
		
		return usuario;
	}

	@Override
	public List<Usuario> getAll() {
		/**
		 *  Usa el metodo get() para recuperar los usuarios de todas las entidades
		 *  que representan a usuarios, tomando los identificadores de cada una
		 */
		return serv.recuperarEntidades(USUARIO).stream()
				.map(e -> e.getId())
				.map(id -> get(id))
				.toList();
	}

}
