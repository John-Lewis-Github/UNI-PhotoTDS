package Persistencia;

public class MySQLFactoriaDAO extends FactoriaDAO{
	
	public MySQLFactoriaDAO() {};
	
	public UsuarioDAO getUsuarioDAO() {
		return MySQLUsuarioDAO.INSTANCE;
	}

}
