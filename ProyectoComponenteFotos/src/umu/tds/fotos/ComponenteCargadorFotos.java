package umu.tds.fotos;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public enum ComponenteCargadorFotos implements IBuscadorFotos {
	INSTANCE;
	
	private List<FotosListener> listeners;
	private String archivoFotos;
	private Fotos fotosActuales;
	
	private ComponenteCargadorFotos() {
		archivoFotos = null;
		fotosActuales = null;
		listeners = new LinkedList<>();
	}
	
	public void addFotosListener(FotosListener l) {
		listeners.add(l);
	}
	
	public void removeFotosListener(FotosListener l) {
		listeners.remove(l);
	}
	
	public void setArchivoFotos(String ruta) {
		archivoFotos = ruta;
		fotosActuales = CargadorFotos.cargarFotos(ruta);
		FotosEvent e = new FotosEvent(this, fotosActuales);
		
		listeners.stream()
			.forEach(l -> l.notificaNuevasFotos(e));
	}

	@Override
	public Fotos nuevasFotos() {
		return fotosActuales;
	}
	
	
	
	
}
