package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;
import uni.JLGG_RCS.PhotoTDS.Dominio.Persistente;

public class MySQLAlbumDAO extends MySQLPublicacionDAO<Album> {
	
	private static MySQLAlbumDAO instance = null;
	
	/**
	 * Se programa esta clase como un Singleton
	 * 
	 * @return la única instancia de esta clase
	 */
	public static MySQLAlbumDAO getInstance() {
		if (instance == null)
			instance = new MySQLAlbumDAO();
		
		return instance;
	}
	
	private ServicioPersistencia serv;
	private PublicacionDAO<Foto> fotoDAO;
	private DAOPool pool;
	
	private MySQLAlbumDAO() {
		super();
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		fotoDAO = MySQLFotoDAO.getInstance();
		pool = DAOPool.INSTANCE;
	}
		
	private static final String FOTOS = "fotos";
	
	@Override
	protected Album crearInstancia(String titulo, String descripcion) {
		return new Album(titulo, descripcion);
	}
	
	@Override 
	protected Album EntidadAPublicacion(Entidad entidad) {
		Album album = super.EntidadAPublicacion(entidad);
		if (album == null)
			return null;
		
		// Se obtienen los Ids de las fotos del album y se recuperan
		List<Integer> fotosIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Foto> fotos = fotosIds.stream()
				.map(id -> (Foto) fotoDAO.get(id))
				.toList();
		
		album.setFotos(fotos);
		return album;
	}
	
	@Override
	public void create(Album publicacion) {
		super.create(publicacion);
		
		Entidad entidad = serv.recuperarEntidad(publicacion.getId());
		List<Propiedad> listaPropiedades = entidad.getPropiedades();
		
		// Incluyo las referencias a la lista de fotos
		publicacion.getFotos().stream()
			.forEach(f -> fotoDAO.create(f));
		String fotos = Persistente.idList2String(publicacion.getFotos());
		listaPropiedades.add(new Propiedad(FOTOS, fotos));
		
		entidad.setPropiedades(listaPropiedades);	
		serv.modificarEntidad(entidad);
	}
	
	@Override
	public void update(Album album) {
		super.update(album);
		Entidad entidad = serv.recuperarEntidad(album.getId());
		Propiedad fotos = entidad.getPropiedades().stream()
				.filter(p -> (p.getNombre().equals(FOTOS)))
				.findFirst()
				.orElseThrow();
		
		// Se incluye la cadena con identificadores de las fotos, previamente actualizadas
		album.getFotos().stream()
			.forEach(f -> fotoDAO.update(f));
		fotos.setValor(Persistente.idList2String(album.getFotos()));
		serv.modificarPropiedad(fotos);
		pool.addObject(album.getId(), album);
	}
}

