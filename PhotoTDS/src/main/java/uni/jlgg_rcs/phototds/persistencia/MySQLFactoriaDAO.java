package uni.jlgg_rcs.phototds.persistencia;

import uni.jlgg_rcs.phototds.dominio.Album;
import uni.jlgg_rcs.phototds.dominio.Foto;

public class MySQLFactoriaDAO extends FactoriaDAO{
	
	public MySQLFactoriaDAO() {};
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return MySQLUsuarioDAO.getInstance();
	}
	
	@Override
	public PublicacionDAO<Foto> getFotoDAO() {
		return MySQLFotoDAO.getInstance();
	}
	
	@Override
	public PublicacionDAO<Album> getAlbumDAO() {
		return MySQLAlbumDAO.getInstance();
	}

	@Override
	public NotificacionDAO getNotificacionDAO() {
		return MySQLNotificacionDAO.INSTANCE;
	}

	@Override
	public ComentarioDAO getComentarioDAO() {
		return MySQLComentarioDAO.INSTANCE;
	}
	
	
}
