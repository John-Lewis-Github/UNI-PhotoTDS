package umu.tds.fotos;

public class CargadorFotos {
	
	public static Fotos cargarFotos(String ruta) {
		return MapperFotosXMLtoJava.cargarFotos(ruta);
	}
	
}
