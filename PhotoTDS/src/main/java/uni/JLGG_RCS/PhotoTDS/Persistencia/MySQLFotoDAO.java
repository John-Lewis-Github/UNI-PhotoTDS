package uni.JLGG_RCS.PhotoTDS.Persistencia;

import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;

public class MySQLFotoDAO extends MySQLPublicacionDAO<Foto>{
	
	private static MySQLFotoDAO instance = null;
	
	/**
	 * Se programa esta clase como un Singleton
	 * 
	 * @return la única instancia de esta clase
	 */
	public static MySQLFotoDAO getInstance() {
		if (instance == null)
			instance = new MySQLFotoDAO();
		
		return instance;
	}
	
	private ServicioPersistencia serv;
	private DAOPool pool;
	
	private MySQLFotoDAO() {
		super();
		serv = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		pool = DAOPool.INSTANCE;
	}
	
	private static final String PATH = "path";
	
	@Override
	protected Foto crearInstancia(String titulo, String descripcion) {
		return new Foto(titulo, descripcion);
	}
	
	@Override
	protected Entidad PublicacionAEntidad(Foto foto) {
		Entidad entidad = super.PublicacionAEntidad(foto);
		List<Propiedad> listaPropiedades = entidad.getPropiedades();
		
		// Se incluye el path
		listaPropiedades.add(new Propiedad(PATH, foto.getPath()));
		entidad.setPropiedades(listaPropiedades);
		
		return entidad;
	}
	
	@Override 
	protected Foto EntidadAPublicacion(Entidad entidad) {
		Foto foto = super.EntidadAPublicacion(entidad);
		String path = serv.recuperarPropiedadEntidad(entidad, PATH);
		
		// Se incluye el path
		foto.setPath(path);
		return foto;
	}
	
	@Override
	public void update(Foto foto) {
		super.update(foto);
		
		Entidad entidad = serv.recuperarEntidad(foto.getId());
		Propiedad path = entidad.getPropiedades().stream()
				.filter(p -> (p.getNombre().equals(PATH)))
				.findFirst()
				.orElseThrow();
		
		// Se actualiza el path
		path.setValor(foto.getPath());
		serv.modificarPropiedad(path);
		pool.addObject(foto.getId(), foto);
	}
}
