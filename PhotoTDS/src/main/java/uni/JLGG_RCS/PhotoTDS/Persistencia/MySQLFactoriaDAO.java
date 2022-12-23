package uni.JLGG_RCS.PhotoTDS.Persistencia;

import uni.JLGG_RCS.PhotoTDS.Dominio.Album;
import uni.JLGG_RCS.PhotoTDS.Dominio.Foto;

public class MySQLFactoriaDAO extends FactoriaDAO{
	
	public MySQLFactoriaDAO() {};
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return MySQLUsuarioDAO.INSTANCE;
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
