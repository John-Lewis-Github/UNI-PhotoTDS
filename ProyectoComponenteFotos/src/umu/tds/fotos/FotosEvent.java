package umu.tds.fotos;

import java.util.EventObject;

public class FotosEvent extends EventObject {

	protected Fotos fotos;
	
	public FotosEvent(Object source, Fotos fotos) {
		super(source);
		this.fotos = fotos;
	}
	
	public Fotos getFotos() {
		return fotos;
	}

}
