package uni.jlgg_rcs.phototds;

import java.awt.EventQueue;

import uni.jlgg_rcs.phototds.interfaz.VentanaLogin;

public class Lanzador {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin ventana = new VentanaLogin();
					ventana.mostrarVentana(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
