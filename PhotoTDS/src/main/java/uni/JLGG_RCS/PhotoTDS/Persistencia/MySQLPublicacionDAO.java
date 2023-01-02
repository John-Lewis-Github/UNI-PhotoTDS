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
import uni.JLGG_RCS.PhotoTDS.Dominio.Comentario;
import uni.JLGG_RCS.PhotoTDS.Dominio.Persistente;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public abstract class MySQLPublicacionDAO<T extends Publicacion> implements PublicacionDAO<T> {
		
	private static final String TITULO = "titulo";
	private static final String FECHA = "fecha";
	private static final String DESCRIPCION = "descripcion";
	private static final String ME_GUSTA = "megusta";
	private static final String COMENTARIOS = "comentarios";
	private static final String USUARIO = "publicacion";

	private static ServicioPersistencia serv;
	private static FactoriaDAO fact;
	private static DAOPool pool;
	private static DateFormat dformat;
	
	protected MySQLPublicacionDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		try {
			fact = MySQLFactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool = DAOPool.INSTANCE;
		dformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Crea una nueva entidad a partir de una publicacion. La entidad
	 * NO ALMACENA datos referentes a instancias de otros objetos
	 * persistentes. Esto debera tratarse debidamente.
	 * 
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
				new Propiedad(ME_GUSTA, Integer.toString(publicacion.getMeGusta()))
				));
		

		
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
				.map(id -> fact.getComentarioDAO().get(id))
				.toList();
		publicacion.addComentarios(comentarios);
		
		// Se recupera el usuario a traves del DAO de usuarios
		String usuarioId = serv.recuperarPropiedadEntidad(entidad, USUARIO);
		Usuario usuario = null;
		if (usuarioId != "")
			usuario = fact.getUsuarioDAO().get(Integer.parseInt(usuarioId));
		
		publicacion.setUsuario(usuario);
		
		return publicacion;
	}
	
	@Override
	public void create(T publicacion) {
		/**
		 * Solo actua si el identificador es null, y en tal caso
		 * registra una entidad en la base de datos y establece el
		 * identificador
		 */
		if (publicacion.getId() != null)
			return;
		
		// Primero me aseguro de no volver a entrar en la funcion
		publicacion.setId(-1);
		
		// Creo una entidad para la publicacion
		Entidad entidad = PublicacionAEntidad(publicacion);
		
		// La registro y obtengo un identificador real
		entidad = serv.registrarEntidad(entidad);
		publicacion.setId(entidad.getId());
		
		// Incluyo en la lista los objetos persistentes referenciados
		List<Propiedad> listaPropiedades = entidad.getPropiedades();
		
		// Usuario
		Usuario usuario = publicacion.getUsuario();
		if (usuario != null) {
			fact.getUsuarioDAO().create(usuario);
			String usuarioId = Integer.toString(usuario.getId());
			listaPropiedades.add(new Propiedad(USUARIO, usuarioId));
		} else {
			listaPropiedades.add(new Propiedad(USUARIO, ""));
		}
		
		// Comentarios
		publicacion.getComentarios().stream()
			.forEach(c -> fact.getComentarioDAO().create(c));
		String comentarios = Persistente.idList2String(publicacion.getComentarios());
		listaPropiedades.add(new Propiedad(COMENTARIOS, comentarios));
		
		entidad.setPropiedades(listaPropiedades);
		serv.modificarEntidad(entidad);
	}
	

	@Override
	public boolean delete(T publicacion) {
		/**
		 * Borra la entidad que tenga el mismo id que la publicacion
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 * En particular esto ocurre si el id es null
		 */
		if (publicacion.getId() == null)
			return false;
		
		Entidad entidad = serv.recuperarEntidad(publicacion.getId());
		pool.removeObject(publicacion.getId());
		publicacion.setId(null);
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
				// Hay que asegurarse de guardar los cambios
				Usuario usuario = publicacion.getUsuario();
				if (usuario == null) {
					p.setValor("");
				} else {
					fact.getUsuarioDAO().create(usuario);
					p.setValor(Integer.toString(usuario.getId()));
				}
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
