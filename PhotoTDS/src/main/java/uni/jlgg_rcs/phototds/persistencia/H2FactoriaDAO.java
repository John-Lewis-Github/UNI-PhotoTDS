package uni.jlgg_rcs.phototds.persistencia;

import uni.jlgg_rcs.phototds.dominio.Album;
import uni.jlgg_rcs.phototds.dominio.Foto;

public class H2FactoriaDAO extends FactoriaDAO {
	
	public H2FactoriaDAO() {};
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return H2UsuarioDAO.getInstance();
	}
	
	@Override
	public PublicacionDAO<Foto> getFotoDAO() {
		return H2FotoDAO.getInstance();
	}
	
	@Override
	public PublicacionDAO<Album> getAlbumDAO() {
		return H2AlbumDAO.getInstance();
	}

	@Override
	public NotificacionDAO getNotificacionDAO() {
		return H2NotificacionDAO.INSTANCE;
	}

	@Override
	public ComentarioDAO getComentarioDAO() {
		return H2ComentarioDAO.INSTANCE;
	}
	
	
}
