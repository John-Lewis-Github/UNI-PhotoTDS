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
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public enum MySQLNotificacionDAO implements NotificacionDAO {
	INSTANCE;

	private static final String NOTIFICACION = Notificacion.class.getName();
	
	private static final String FECHA = "fecha";
	private static final String PUBLICACION = "publicacion";
	
	private ServicioPersistencia serv;
	private PublicacionDAO<Foto> fotoDAO;
	private PublicacionDAO<Album> albumDAO;
	private DAOPool pool;
	private DateFormat dformat;
	
	private MySQLNotificacionDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		fotoDAO = MySQLFotoDAO.getInstance();
		albumDAO = MySQLAlbumDAO.getInstance();
		pool = DAOPool.INSTANCE;
		dformat = new SimpleDateFormat("dd/MM/yyyy");

	}
	
	/**
	 * Crea una nueva entidad a partir de una notificacion
	 * @param notificacion La notificacion a partir de la que formar una entidad
	 * @return Una entidad creada a partir de la notificacion
	 */
	private Entidad NotificacionAEntidad(Notificacion notificacion) {
		
		// Se trata primero el caso nulo
		if (notificacion == null)
			return null;
		
		Entidad entidad = new Entidad();
		entidad.setNombre(NOTIFICACION);
		
		Publicacion publicacion = notificacion.getPublicacion();
		String publicacionId = "";
		if (publicacion != null) {
			if (publicacion instanceof Foto) {
				Foto foto = (Foto) publicacion;
				fotoDAO.create(foto);
				publicacionId = Integer.toString(foto.getId());
			} 
			else if (publicacion instanceof Album) {
				Album album = (Album) publicacion;
				albumDAO.create(album);
				publicacionId = Integer.toString(album.getId());
			}
		}
		
		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(FECHA, dformat.format(publicacion.getFecha())),
				new Propiedad(PUBLICACION, publicacionId)
				));
		
		entidad.setPropiedades(listaPropiedades);
		return entidad;
	}
	
	/**
	 * Crea una instancia nueva de Notificacion a partir de una entidad
	 * @param entidad la entidad que genera la notificacion
	 * @return una notificacion 
	 */
	public Notificacion EntidadANotificacion(Entidad entidad) {
		/**
		 * Por la semantica de los seguidores no habra ciclos nunca
		 */
		
		String fechaStr = serv.recuperarPropiedadEntidad(entidad, FECHA);
		Date fecha = null;
		try {
			fecha = dformat.parse(fechaStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String publicacionIdStr = serv.recuperarPropiedadEntidad(entidad, PUBLICACION);
		Integer publicacionId = null;
		if (publicacionIdStr != "")
			publicacionId = Integer.parseInt(publicacionIdStr);
		
		// TODO TESTEAR
		// Como se supone que get(id) devuelve null si no lo tiene, basta comprobar alguno
		
		// Vemos si es una publicacion
		Publicacion publicacion = fotoDAO.get(publicacionId);
		
		// Si no lo es, probamos con lo otro
		if (publicacion == null)
			publicacion = albumDAO.get(publicacionId);
		
		// Si en este punto tambien es null es porque antes ya era null
		// Ergo, no debería haber problema
		
		return new Notificacion(publicacion, fecha);
	}
	
	@Override
	public void create(Notificacion notificacion) {
		/**
		 * Solo actua si el identificador es null, y en tal caso
		 * registra una entidad en la base de datos y establece el
		 * identificador
		 */
		if (notificacion.getId() == null) {
			Entidad entidad = NotificacionAEntidad(notificacion);
			entidad = serv.registrarEntidad(entidad);
			notificacion.setId(entidad.getId());
		}
	}

	@Override
	public boolean delete(Notificacion notificacion) {
		/**
		 * Borra la entidad que tenga el mismo id que el usuario
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 */
		Entidad entidad = serv.recuperarEntidad(notificacion.getId());
		pool.removeObject(notificacion.getId());
		return serv.borrarEntidad(entidad);
	}

	@Override
	public void update(Notificacion notificacion) {
		// No hay nada que hacer, la notificacion es inmutable
	}

	@Override
	public Notificacion get(int id) {
		/**
		 * Se comprueba si se ha creado ya una instancia de Notificacion a partir de
		 * la entidad que tiene su mismo id. Para ello se consulta directamente
		 * en el Pool. Si no está, entonces se crea la instancia en cuestion.
		 */
		Notificacion notificacion = (Notificacion) pool.getObject(id);
		if (notificacion == null)
			notificacion = EntidadANotificacion(serv.recuperarEntidad(id));
		
		return notificacion;
	}

	@Override
	public List<Notificacion> getAll() {
		/**
		 *  Usa el metodo get() para recuperar las notificaciones de todas las entidades
		 *  que representan a notificaciones, tomando los identificadores de cada una
		 *  El metodo get(id) ya maneja el Pool y no hay que preocuparse por el
		 */
		return serv.recuperarEntidades(NOTIFICACION).stream()
				.map(e -> e.getId())
				.map(id -> get(id))
				.toList();
	}

}
