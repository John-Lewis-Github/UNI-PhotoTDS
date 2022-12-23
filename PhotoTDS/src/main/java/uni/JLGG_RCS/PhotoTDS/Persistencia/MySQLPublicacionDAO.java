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

public abstract class MySQLPublicacionDAO<T extends Publicacion> implements PublicacionDAO<T> {
	
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
	
	protected MySQLPublicacionDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		usDAO = MySQLUsuarioDAO.INSTANCE;
		comDAO = MySQLComentarioDAO.INSTANCE;
		pool = DAOPool.INSTANCE;
		dformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Crea una nueva entidad a partir de una publicacion
	 * @param publicacion la publicacion a partir de la que formar una entidad
	 * @return Una entidad creada a partir de la publicacion
	 */
	protected Entidad PublicacionAEntidad(T publicacion) {
		
		// Se trata primero el caso nulo
		if (publicacion == null)
			return null;
		
		Entidad entidad = new Entidad();
		
		// El nombre de la entidad viene dado por el tipo de T
		entidad.setNombre(publicacion.getClass().getName());

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
		String comentarios = Persistente.idList2String(publicacion.getComentarios());
		listaPropiedades.add(new Propiedad(COMENTARIOS, comentarios));
		
		entidad.setPropiedades(listaPropiedades);
		
		return entidad;
	}
	
	/**
	 * Método factoría que permite trabajar con instancias que hereden
	 * de la clase Publicacion. Basta con que usen su constructor principal
	 * 
	 * @param titulo
	 * @param Publicacion
	 * @return
	 */
	protected abstract T crearInstancia(String titulo, String descripcion);
	
	/**
	 * Crea una instancia nueva de Publicacion a partir de una entidad
	 * @param entidad la entidad que genera la publicacion
	 * @return una publicacion 
	 */
	protected T EntidadAPublicacion(Entidad entidad) { 
		
		// Se trata primero el caso nulo
		if (entidad == null)
			return null;
		
		// Recuperamos atributos que no referencian a entidades
		String titulo = serv.recuperarPropiedadEntidad(entidad, TITULO);
		String descripcion = serv.recuperarPropiedadEntidad(entidad, DESCRIPCION);
		
		// Creamos la publicacion y le asignamos su identificador
		T publicacion = crearInstancia(titulo, descripcion);
		publicacion.setId(entidad.getId());

		// Lo registramos en el Pool
		pool.addObject(entidad.getId(), publicacion);
		
		// Ahora podemos procesar el resto de los atributos, pues no habrá
		// problemas con las referencias bidireccionales
		
		Date fecha = null;
		try {
			fecha = dformat.parse(serv.recuperarPropiedadEntidad(entidad, FECHA));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		publicacion.setFecha(fecha);
		
		int megusta = Integer.parseInt(serv.recuperarPropiedadEntidad(entidad, ME_GUSTA));
		publicacion.setMeGusta(megusta);
		
		// Se transforman las listas de identificadores enteros a listas de atributos
		List<Integer> comentariosIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, COMENTARIOS));
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
	public void create(T publicacion) {
		/**
		 * Crea directamente una entidad para la nueva publicacion y la registra
		 * mediante el servicio de persistencia. 
		 */
		Entidad entidad = PublicacionAEntidad(publicacion);
		entidad = serv.registrarEntidad(entidad);
		publicacion.setId(entidad.getId());
	}

	@Override
	public boolean delete(T publicacion) {
		/**
		 * Borra la entidad que tenga el mismo id que la publicacion
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 */
		Entidad entidad = serv.recuperarEntidad(publicacion.getId());
		pool.removeObject(publicacion.getId());
		return serv.borrarEntidad(entidad);
	}

	@Override
	public void update(T publicacion) {
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
				p.setValor(Persistente.idList2String(publicacion.getComentarios()));
				break;
			case USUARIO:
				Usuario usuario = publicacion.getUsuario();
				p.setValor(Integer.toString(usuario.getId()));
				break;
			default:
				break;
			}
			serv.modificarPropiedad(p);
		}
		
		// Se reemplaza el objeto en el pool
		pool.addObject(publicacion.getId(), publicacion);
	}

	@Override
	public T get(int id) {
		/**
		 * Se comprueba si se ha creado ya una instancia de Publicacion a partir de
		 * la entidad que tiene su mismo id. Para ello se consulta directamente
		 * en el Pool. Si no está, entonces se crea la instancia en cuestion.
		 */
		T publicacion = (T) pool.getObject(id);
		if (publicacion == null)
			publicacion = EntidadAPublicacion(serv.recuperarEntidad(id));
		
		return publicacion;
	}

	@Override
	public List<T> getAll() {
		/**
		 *  Usa el metodo get() para recuperar las publicaciones de todas las entidades
		 *  que representan a publicaciones, tomando los identificadores de cada una
		 *  El metodo get(id) ya maneja el Pool y no hay que preocuparse por el
		 */
		T aux = crearInstancia("","");
		return serv.recuperarEntidades(aux.getClass().getName()).stream()
				.map(e -> e.getId())
				.map(id -> get(id))
				.toList();
	}

}
