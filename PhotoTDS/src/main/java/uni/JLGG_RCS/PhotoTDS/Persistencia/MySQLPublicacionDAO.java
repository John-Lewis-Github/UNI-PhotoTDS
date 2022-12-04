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
import uni.JLGG_RCS.PhotoTDS.Dominio.Comentario;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Notificacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Persistente;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;

public enum MySQLPublicacionDAO implements PublicacionDAO {
	INSTANCE;
	
	private static final String PUBLICACION = "Publicacion";
	
	private static final String TITULO = "titulo";
	private static final String FECHA = "fecha";
	private static final String DESCRIPCION = "descripcion";
	private static final String ME_GUSTA = "megusta";
	private static final String HASHTAGS = "hashtags";
	private static final String COMENTARIOS = "comentarios";
	private static final String USUARIO = "publicacion";

	private ServicioPersistencia serv;
	private UsuarioDAO usDAO;
	private ComentarioDAO comDAO;
	private DAOPool pool;
	private DateFormat dformat;
	
	private MySQLPublicacionDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		usDAO = MySQLUsuarioDAO.INSTANCE;
		comDAO = MySQLComentarioDAO.INSTANCE;
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
	 * Crea una nueva entidad a partir de una publicacion
	 * @param publicacion la publicacion a partir de la que formar una entidad
	 * @return Una entidad creada a partir de la publicacion
	 */
	private Entidad PublicacionAEntidad(Publicacion publicacion) {
		
		Entidad entidad = new Entidad();
		entidad.setNombre(PUBLICACION);

		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TITULO, publicacion.getTitulo()),
				new Propiedad(FECHA, dformat.format(publicacion.getFecha())),
				new Propiedad(DESCRIPCION, publicacion.getDescripcion()),
				new Propiedad(ME_GUSTA, Integer.toString(publicacion.getMeGusta())),
				new Propiedad(USUARIO, Integer.toString(publicacion.getUsuario().getId()))
				));
		
		/**
		 *  No será necesario guardar los hashtags, pues aparecen en el texto
		 *  de la descripcion. Cuando se cree una instancia de Publicacion, junto
		 *  con la descripcion se procesaran los hashtags.
		 */
		
		/**
		 *  Los comentarios, referenciados mediante listas, se representaran
		 *  a partir de sus identificadores, que se concatenarán para
		 *  formar un String
		 */
		String comentarios = idList2String(publicacion.getComentarios());
		listaPropiedades.add(new Propiedad(COMENTARIOS, comentarios));
		
		entidad.setPropiedades(listaPropiedades);
		
		return entidad;
	}
	
	/**
	 * Crea una instancia nueva de Publicacion a partir de una entidad
	 * @param entidad la entidad que genera la publicacion
	 * @return una publicacion 
	 */
	private Publicacion EntidadAPublicacion(Entidad entidad) { 
		// Recuperamos atributos que no referencian a entidades
		
		String titulo = serv.recuperarPropiedadEntidad(entidad, TITULO);
		Date fecha = null;
		try {
			fecha = dformat.parse(serv.recuperarPropiedadEntidad(entidad, FECHA));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String descripcion = serv.recuperarPropiedadEntidad(entidad, DESCRIPCION);
		
		// Creamos la publicacion y le asignamos su identificador
		Publicacion publicacion = new Publicacion(titulo, fecha, descripcion);
		publicacion.setId(entidad.getId());

		// Lo registramos en el Pool
		pool.addObject(entidad.getId(), publicacion);
		
		// Ahora podemos procesar el resto de los atributos, pues no habrá
		// problemas con las referencias bidireccionales
		
		int megusta = Integer.parseInt(serv.recuperarPropiedadEntidad(entidad, ME_GUSTA));
		publicacion.setMeGusta(megusta);
		
		// Se transforman las listas de identificadores enteros a listas de atributos
		List<Integer> comentariosIds = string2IdList(serv.recuperarPropiedadEntidad(entidad, COMENTARIOS));
		List<Comentario> comentarios = comentariosIds.stream()
				.map(id -> comDAO.get(id))
				.toList();
		publicacion.addComentarios(comentarios);
		
		// Se recupera el usuario a traves del DAO de usuarios
		String usuarioId = serv.recuperarPropiedadEntidad(entidad, USUARIO);
		Usuario usuario = usDAO.get(Integer.parseInt(usuarioId));
		publicacion.setUsuario(usuario);
		
		return publicacion;
	}
	
	@Override
	public void create(Publicacion publicacion) {
		/**
		 * Crea directamente una entidad para la nueva publicacion y la registra
		 * mediante el servicio de persistencia. 
		 */
		Entidad entidad = PublicacionAEntidad(publicacion);
		serv.registrarEntidad(entidad);
		publicacion.setId(entidad.getId());
	}

	@Override
	public boolean delete(Publicacion publicacion) {
		/**
		 * Borra la entidad que tenga el mismo id que la publicacion
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 */
		Entidad entidad = serv.recuperarEntidad(publicacion.getId());
		return serv.borrarEntidad(entidad);
	}

	@Override
	public void update(Publicacion publicacion) {
		// Se recupera la entidad asociada al usuario
		Entidad entidad = serv.recuperarEntidad(publicacion.getId());

		// Se actualizan una por una todas sus propiedades
		for (Propiedad p : entidad.getPropiedades()) {
			switch (p.getNombre()) {
			case TITULO:
				p.setValor(publicacion.getTitulo());
				break;
			case FECHA:
				p.setValor(dformat.format(publicacion.getFecha()));
				break;
			case DESCRIPCION:
				p.setValor(publicacion.getDescripcion());
				break;
			case ME_GUSTA:
				p.setValor(Integer.toString(publicacion.getMeGusta()));
				break;
			case COMENTARIOS:
				p.setValor(idList2String(publicacion.getComentarios()));
				break;
			case USUARIO:
				Usuario usuario = publicacion.getUsuario();
				p.setValor(Integer.toString(usuario.getId()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Publicacion get(int id) {
		/**
		 * Se comprueba si se ha creado ya una instancia de Publicacion a partir de
		 * la entidad que tiene su mismo id. Para ello se consulta directamente
		 * en el Pool. Si no está, entonces se crea la instancia en cuestion.
		 */
		Publicacion publicacion = (Publicacion) pool.getObject(id);
		if (publicacion == null)
			publicacion = EntidadAPublicacion(serv.recuperarEntidad(id));
		
		return publicacion;
	}

	@Override
	public List<Publicacion> getAll() {
		/**
		 *  Usa el metodo get() para recuperar las publicaciones de todas las entidades
		 *  que representan a publicaciones, tomando los identificadores de cada una
		 *  El metodo get(id) ya maneja el Pool y no hay que preocuparse por el
		 */
		return serv.recuperarEntidades(PUBLICACION).stream()
				.map(e -> e.getId())
				.map(id -> get(id))
				.toList();
	}

}
