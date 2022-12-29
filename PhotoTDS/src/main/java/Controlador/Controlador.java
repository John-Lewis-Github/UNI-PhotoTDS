package Controlador;

import uni.JLGG_RCS.PhotoTDS.Dominio.RepositorioUsuarios;
import uni.JLGG_RCS.PhotoTDS.Dominio.Usuario;

public enum Controlador {
	INSTANCE;
	
	private RepositorioUsuarios usRepo;
	
	private Controlador() {
		usRepo = RepositorioUsuarios.INSTANCE;
	}
	
	public void registrarUsuario(Usuario usuario) {
		usRepo.addUsuario(usuario);
	}
	
}
