package uni.jlgg_rcs.phototds.persistencia;

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
import uni.jlgg_rcs.phototds.dominio.Album;
import uni.jlgg_rcs.phototds.dominio.Foto;
import uni.jlgg_rcs.phototds.dominio.Notificacion;
import uni.jlgg_rcs.phototds.dominio.Publicacion;
import uni.jlgg_rcs.phototds.dominio.Usuario;

public enum MySQLNotificacionDAO implements NotificacionDAO {
	INSTANCE;

	private static final String NOTIFICACION = Notificacion.class.getName();
	
	private static final String FECHA = "fecha";
	private static final String PUBLICACION = "publicacion";
	
	private ServicioPersistencia serv;
	private FactoriaDAO fact;
	private DAOPool pool;
	private DateFormat dformat;
	
	private MySQLNotificacionDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		try {
			fact = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(FECHA, dformat.format(notificacion.getFecha()))
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
		 * No pueden seguirse a si mismos
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
		Publicacion publicacion = null;
		if (publicacionIdStr != "") {
			publicacionId = Integer.parseInt(publicacionIdStr);

			// Vemos si es una publicacion
			publicacion = fact.getFotoDAO().get(publicacionId);
		
			// Si no lo es, probamos con lo otro
			if (publicacion == null)
				publicacion = fact.getAlbumDAO().get(publicacionId);
		}
		
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
		if (notificacion.getId() != null)
		return; 
			
		// Creo una entidad para la notificacion
		Entidad entidad = NotificacionAEntidad(notificacion);
		
		// Registro la entidad y obtengo un identificador
		entidad = serv.registrarEntidad(entidad);
		notificacion.setId(entidad.getId());
		
		// Obtengo la lista de propiedades
		List<Propiedad> listaPropiedades = entidad.getPropiedades();
		
		// Trato la referencia a la publicacion, persistente
		Publicacion publicacion = notificacion.getPublicacion();
		if (publicacion != null) {
			String publicacionId = "";
			if (publicacion instanceof Foto) {
				Foto foto = (Foto) publicacion;
				fact.getFotoDAO().create(foto);
				publicacionId = Integer.toString(foto.getId());
			} 
			else if (publicacion instanceof Album) {
				Album album = (Album) publicacion;
				fact.getAlbumDAO().create(album);
				publicacionId = Integer.toString(album.getId());
			}
			listaPropiedades.add(new Propiedad(PUBLICACION, publicacionId));
		} else {
			listaPropiedades.add(new Propiedad(PUBLICACION, ""));
		}
		
		entidad.setPropiedades(listaPropiedades);
		serv.modificarEntidad(entidad);
	}

	@Override
	public boolean delete(Notificacion notificacion) {
		/**
		 * Borra la entidad que tenga el mismo id que el usuario
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 * En particular esto ocurre si el id es null
		 */
		if (notificacion.getId() == null)
			return false;
		
		Entidad entidad = serv.recuperarEntidad(notificacion.getId());
		pool.removeObject(notificacion.getId());
		notificacion.setId(null);
		return serv.borrarEntidad(entidad);
	}

	@Override
	public void update(Notificacion notificacion) {
		// Si la notificacion no estaba en la base de datos, se crea
		if (notificacion.getId() == null) {
			create(notificacion);
			return;
		}	
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
