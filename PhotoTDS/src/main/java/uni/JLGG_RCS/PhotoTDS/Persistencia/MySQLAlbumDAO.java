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
		pool = DAOPool.INSTANCE;
	}
		
	private static final String FOTOS = "fotos";
	
	@Override
	protected Album crearInstancia(String titulo, String descripcion) {
		return new Album(titulo, descripcion);
	}
	
	@Override
	protected Entidad PublicacionAEntidad(Album album) {
		Entidad entidad = super.PublicacionAEntidad(album);
		List<Propiedad> listaPropiedades = entidad.getPropiedades();
		
		// Añado la lista de fotos, previamente creadas
		album.getFotos().stream()
			.forEach(f -> fotoDAO.create(f));
		listaPropiedades.add(new Propiedad(FOTOS, Persistente.idList2String(album.getFotos())));
		entidad.setPropiedades(listaPropiedades);
		
		return entidad;
	}
	
	@Override 
	protected Album EntidadAPublicacion(Entidad entidad) {
		Album album = super.EntidadAPublicacion(entidad);
		
		// Se obtienen los Ids de las fotos del album y se recuperan
		List<Integer> fotosIds = Persistente.string2IdList(serv.recuperarPropiedadEntidad(entidad, FOTOS));
		List<Foto> fotos = fotosIds.stream()
				.map(id -> (Foto) fotoDAO.get(id))
				.toList();
		
		album.setFotos(fotos);
		return album;
	}
	
	@Override
	public void update(Album album) {
		super.update(album);
		Entidad entidad = serv.recuperarEntidad(album.getId());
		Propiedad fotos = entidad.getPropiedades().stream()
				.filter(p -> (p.getNombre().equals(FOTOS)))
				.findFirst()
				.orElseThrow();
		
		// Se incluye la cadena con identificadores de las fotos, previamente creadas
		album.getFotos().stream()
			.forEach(f -> fotoDAO.create(f));
		fotos.setValor(Persistente.idList2String(album.getFotos()));
		serv.modificarPropiedad(fotos);
		pool.addObject(album.getId(), album);
	}
}

