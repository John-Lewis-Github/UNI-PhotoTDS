package uni.JLGG_RCS.PhotoTDS.Persistencia;

public class MySQLFactoriaDAO extends FactoriaDAO{
	
	public MySQLFactoriaDAO() {};
	
	public UsuarioDAO getUsuarioDAO() {
		return MySQLUsuarioDAO.INSTANCE;
	}
	
	public PublicacionDAO getPublicacionDAO() {
		return MySQLPublicacionDAO.INSTANCE;
	}

	public NotificacionDAO getNotificacionDAO() {
		return MySQLNotificacionDAO.INSTANCE;
	}
}
