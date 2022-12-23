package uni.JLGG_RCS.PhotoTDS.Persistencia;

import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;

/**
 * Factoria abstracta DAO.
 */

public abstract class FactoriaDAO {
	
	public static final String DAO_MYSQL = "uni.JLGG_RCS.PhotoTDS.Persistencia.MySQLFactoriaDAO";

	private static FactoriaDAO unicaInstancia = null;
	
	/** 
	 * Crea un tipo de factoria DAO.
	 * De momento solo existe el tipo MySQLFactoriaDAO
	 */
	public static FactoriaDAO getInstancia(String tipo) throws DAOException{
		if (unicaInstancia == null)
			try { 
				unicaInstancia=(FactoriaDAO) Class.forName(tipo).getConstructor().newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
		} 
		return unicaInstancia;
	}

	public static FactoriaDAO getInstancia() throws DAOException{
		return getInstancia(FactoriaDAO.DAO_MYSQL);
	}

	protected FactoriaDAO (){}
	
	// Metodos factoria para obtener adaptadores
	
	public abstract UsuarioDAO getUsuarioDAO();	
	public abstract PublicacionDAO<Foto> getFotoDAO();
	public abstract PublicacionDAO<Album> getAlbumDAO();
	public abstract NotificacionDAO getNotificacionDAO();
	public abstract ComentarioDAO getComentarioDAO();
	
}
