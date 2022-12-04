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
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public enum MySQLComentarioDAO implements ComentarioDAO {
	INSTANCE;
	
	private static final String COMENTARIO = "comentario";
	private static final String TEXTO = "texto";
	private static final String FECHA = "fecha";
	private static final String COMENTADOR = "comentador";
	
	private ServicioPersistencia serv;
	private UsuarioDAO usDAO;
	private DAOPool pool;
	private DateFormat dformat;
	
	private MySQLComentarioDAO() {
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		usDAO = MySQLUsuarioDAO.INSTANCE;
		pool = DAOPool.INSTANCE;
		dformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Crea una nueva entidad a partir de un comentario
	 * @param cometario el comentario a partir del que formar una entidad
	 * @return Una entidad creada a partir del comentario
	 */
	private Entidad ComentarioAEntidad(Comentario comentario) {
		Entidad entidad = new Entidad();
		entidad.setNombre(COMENTARIO);
		
		ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TEXTO, comentario.getTexto()),
				new Propiedad(FECHA, dformat.format(comentario.getFecha())),
				new Propiedad(COMENTADOR, Integer.toString(comentario.getComentador().getId()))
				));
		
		entidad.setPropiedades(listaPropiedades);
		return entidad;
	}
	
	/**
	 * Crea una instancia nueva de Comentario a partir de una entidad
	 * @param entidad la entidad que genera el comentario
	 * @return un comentario 
	 */
	public Comentario EntidadAComentario(Entidad entidad) {
		// Recuperamos atributos que no referencian a entidades
		String texto = serv.recuperarPropiedadEntidad(entidad, TEXTO);
		Date fecha = null;
		try {
			fecha = dformat.parse(serv.recuperarPropiedadEntidad(entidad, FECHA));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Se crea una instancia de Comentario y se le asigna su identificador
		Comentario comentario = new Comentario(texto, fecha);
		comentario.setId(entidad.getId());
		
		// Se registra en el Pool
		pool.addObject(entidad.getId(), comentario);
		
		// Se puede recuperar el usuario comentador sin problemas
		String comentadorId = serv.recuperarPropiedadEntidad(entidad, COMENTADOR);
		Usuario comentador = (Usuario) usDAO.get(Integer.parseInt(comentadorId));

		comentario.setComentador(comentador);
		
		return comentario;
	}
	
	@Override
	public void create(Comentario comentario) {
		/**
		 * Crea directamente una entidad para el nuevo comentario y la registra
		 * mediante el servicio de persistencia. 
		 */
		Entidad entidad = ComentarioAEntidad(comentario);
		serv.registrarEntidad(entidad);
		comentario.setId(entidad.getId());
		
	}

	@Override
	public boolean delete(Comentario comentario) {
		/**
		 * Borra la entidad que tenga el mismo id que el comentario
		 * Por tanto, la operacion puede no borrar ninguna entidad
		 */
		Entidad entidad = serv.recuperarEntidad(comentario.getId());
		return serv.borrarEntidad(entidad);
	}

	@Override
	public void update(Comentario comentario) {
		// Se recupera la entidad asociada al usuario
		Entidad entidad = serv.recuperarEntidad(comentario.getId());

		// Se actualizan una por una todas sus propiedades
		for (Propiedad p : entidad.getPropiedades()) {
			switch (p.getNombre()) {
			case TEXTO:
				p.setValor(comentario.getTexto());
				break;
			case FECHA:
				p.setValor(dformat.format(comentario.getFecha()));
				break;
			case COMENTADOR:
				Usuario comentador = comentario.getComentador();
				p.setValor(Integer.toString(comentador.getId()));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Comentario get(int id) {
		/**
		 * Se comprueba si se ha creado ya una instancia de Usuario a partir de
		 * la entidad que tiene su mismo id. Para ello se consulta directamente
		 * en el Pool. Si no está, entonces se crea la instancia en cuestion.
		 */
		Comentario comentario = (Comentario) pool.getObject(id);
		if (comentario == null)
			comentario = EntidadAComentario(serv.recuperarEntidad(id));
		
		return comentario;
	}

	@Override
	public List<Comentario> getAll() {
		/**
		 *  Usa el metodo get() para recuperar los comentarios de todas las entidades
		 *  que representan a comentarios, tomando los identificadores de cada una
		 *  El metodo get(id) ya maneja el Pool y no hay que preocuparse por el
		 */
		return serv.recuperarEntidades(COMENTARIO).stream()
				.map(e -> e.getId())
				.map(id -> get(id))
				.toList();
	}

}
