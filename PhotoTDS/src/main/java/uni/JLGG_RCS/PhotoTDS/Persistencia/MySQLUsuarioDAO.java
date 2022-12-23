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
	private static final String PREMIUM = "premium";
	private static final String SEGUIDORES = "seguidores";
	private static final String FOTOS = "fotos";
	private static final String ALBUMES = "albumes";
	private static final String NOTIFICACIONES = "notificaciones";

	private ServicioPersistencia serv;
	private PublicacionDAO<Foto> fotoDAO;
	private PublicacionDAO<Album> albumDAO;
	private NotificacionDAO notiDAO;
	private DAOPool pool;
	private DateFormat dformat;
	
	private MySQLUsuarioDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		fotoDAO = MySQLFotoDAO.getInstance();
		albumDAO = MySQLAlbumDAO.getInstance();
		notiDAO = MySQLNotificacionDAO.INSTANCE;
		pool = DAOPool.INSTANCE;
		dformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Crea una nueva entidad a partir de un usuario
	 * @param usuario El usuario a partir del que formar una entidad
	 * @return Una entidad creada a partir del usuario
	 */
	private Entidad UsuarioAEntidad(Usuario usuario) {
		
		// Se trata primero el caso nulo
		if (usuario == null)
			return null;
		
		Entidad entidad = new Entidad();
		entidad.setNombre(USUARIO);
		
		Date fecha = usuario.getFechaNacimiento();
		String dateString = (fecha == null) ? "" : dformat.format(fecha);
		
		Foto fotoPerfil = usuario.getFotoPerfil();
		String fotoPerfilId = (fotoPerfil == null) ? "" : Integer.toString(fotoPerfil.getId());

		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(NOMBRE_COMPLETO, usuario.getNombreCompleto()),
				new Propiedad(NOMBRE_USUARIO, usuario.getNombreUsuario()),
				new Propiedad(FECHA, dateString),
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(PRESENTACION, usuario.getPresentacion()),
				new Propiedad(FOTO_PERFIL, fotoPerfilId),
				new Propiedad(PREMIUM, Boolean.toString(usuario.isPremium()))
				));
		
		/**
		 *  Los objetos referenciados mediante listas se representaran
		 *  a partir de sus identificadores, que se concatenarán para
		 *  formar un String
		 */
		String seguidores = Persistente.idList2String(usuario.getSeguidores());
		listaPropiedades.add(new Propiedad(SEGUIDORES, seguidores));
		
		String fotos = Persistente.idList2String(usuario.getFotos());
		listaPropiedades.add(new Propiedad(FOTOS, fotos));
		
		String albumes = Persistente.idList2String(usuario.getAlbumes());
		listaPropiedades.add(new Propiedad(ALBUMES, albumes));
		
		String notificaciones = Persistente.idList2String(usuario.getNotificaciones());
		listaPropiedades.add(new Propiedad(NOTIFICACIONES, notificaciones));
		
		entidad.setPropiedades(listaPropiedades);
		
		return entidad;
	}
	
	/**
	 * Crea una instancia nueva de Usuario a partir de una entidad
	 * @param entidad la entidad que genera el usuario
	 * @return un usuario 
	 */
	private Usuario EntidadAUsuario(Entidad entidad) { 
		
		// Se trata primero el caso nulo
		if (entidad == null)
			return null;
		
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
		String password = serv.recuperarPropiedadEntidad(entidad, PASSWORD);
		
		// Creamos el usuario y le asignamos su identificador
		Usuario usuario = new Usuario(nombreCompleto, nombreUsuario, fecha, email, password);
		usuario.setId(entidad.getId());

		// Lo registramos en el Pool
		pool.addObject(entidad.getId(), usuario);
		
		// Ahora podemos procesar el resto de los atributos, pues no habrá
		// problemas con las referencias bidireccionales
		
		String presentacion = serv.recuperarPropiedadEntidad(entidad, PRESENTACION);

		String fotoPerfilId = serv.recuperarPropiedadEntidad(entidad, FOTO_PERFIL);
		Foto fotoPerfil = null;
		if (fotoPerfilId != "")
			fotoPerfil = fotoDAO.get(Integer.parseInt(fotoPerfilId));
		
		String premiumStr = serv.recuperarPropiedadEntidad(entidad, PREMIUM);
		boolean premium = Boolean.parseBoolean(premiumStr);
		
		usuario.setPassword(password);
		usuario.setPresentacion(presentacion);
		usuario.setFotoPerfil(fotoPerfil);
		usuario.setPremium(premium);
		
		// Se transforman las listas de identificadores enteros a listas de atributos
		// En funcion del tipo de atributo se usan diferentes DAOs
		List<Integer> seguidoresIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, SEGUIDORES));
		List<Usuario> seguidores = seguidoresIds.stream()
				.map(id -> get(id))
				.toList();
		
		List<Integer> fotosIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Foto> fotos = fotosIds.stream()
				.map(id -> fotoDAO.get(id))
				.toList();
		
		List<Integer> albumesIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Album> albumes = albumesIds.stream()
				.map(id -> albumDAO.get(id))
				.toList();
		
		List<Integer> notificacionesIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
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
		entidad = serv.registrarEntidad(entidad);
		usuario.setId(entidad.getId());
	}

	@Override
	public boolean delete(Usuario usuario) {
		/**
		 * Borra la entidad que tenga el mismo id que el usuario
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 */
		Entidad entidad = serv.recuperarEntidad(usuario.getId());
		pool.removeObject(usuario.getId());
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
				p.setValor(usuario.getPassword());
				break;
			case FOTO_PERFIL:
				Foto fotoPerfil = usuario.getFotoPerfil();
				if (fotoPerfil == null)
					p.setValor("");
				else
					p.setValor(Integer.toString(fotoPerfil.getId()));
				break;
			case PREMIUM:
				p.setValor(Boolean.toString(usuario.isPremium()));
				break;
			case SEGUIDORES: 
				p.setValor(Persistente.idList2String(usuario.getSeguidores()));
				break;
			case FOTOS:
				p.setValor(Persistente.idList2String(usuario.getFotos()));
				break;
			case ALBUMES:
				p.setValor(Persistente.idList2String(usuario.getAlbumes()));
				break;
			case NOTIFICACIONES:
				p.setValor(Persistente.idList2String(usuario.getNotificaciones()));
				break;
			default:
				break;
			}
			serv.modificarPropiedad(p);
		}
		
		// Se reemplaza el objeto en el pool
		pool.addObject(usuario.getId(), usuario);
		
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
		 *  El metodo get(id) ya maneja el Pool y no hay que preocuparse por el
		 */
		return serv.recuperarEntidades(USUARIO).stream()
				.map(e -> e.getId())
				.map(id -> get(id))
				.toList();
	}

}
