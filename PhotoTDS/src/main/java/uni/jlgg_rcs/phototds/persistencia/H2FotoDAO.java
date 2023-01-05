package uni.jlgg_rcs.phototds.persistencia;

import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import uni.jlgg_rcs.phototds.dominio.Foto;

public class H2FotoDAO extends H2PublicacionDAO<Foto>{
	
	private static H2FotoDAO instance = null;
	
	/**
	 * Se programa esta clase como un Singleton
	 * 
	 * @return la única instancia de esta clase
	 */
	public static H2FotoDAO getInstance() {
		if (instance == null)
			instance = new H2FotoDAO();
		
		return instance;
	}
	
	private ServicioPersistencia serv;
	private DAOPool pool;
	
	private H2FotoDAO() {
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
		if (entidad == null)
			return null;
		
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
