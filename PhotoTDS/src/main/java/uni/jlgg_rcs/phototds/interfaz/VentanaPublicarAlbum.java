package uni.jlgg_rcs.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaPublicarAlbum {

	private static final int ANCHURA = 400;
	private static final int ALTURA = 200;
	private static final int ANCHURA_BOTON = 300;
	private static final int ALTURA_BOTON = 75;
	private JDialog frame;
	private JTextField campoNombreAlbum;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPublicarAlbum v = new VentanaPublicarAlbum();
					v.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	public VentanaPublicarAlbum() {
		initialize();
		JPanel panelTexto = crearPanelTexto();
		frame.getContentPane().add(panelTexto,  BorderLayout.CENTER);
		JPanel panelBoton = crearPanelBoton();
		frame.getContentPane().add(panelBoton,  BorderLayout.SOUTH);
	}

	private JPanel crearPanelTexto() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel lblInformativo = new JLabel("Introduce el nombre del nuevo album");
		lblInformativo.setFont(new Font("Dialog", Font.BOLD, 20));
		
		
		campoNombreAlbum = new JTextField("", 20);
		campoNombreAlbum.setFont(new Font("Dialog", Font.PLAIN, 18));
		
		JLabel lblAlbumYaExiste = new JLabel("Ya tienes un album con ese nombre :(");
		lblAlbumYaExiste.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblAlbumYaExiste.setForeground(Color.RED);
		lblAlbumYaExiste.setVisible(false);
		
		panel.add(lblInformativo);
		panel.add(campoNombreAlbum);
		panel.add(lblAlbumYaExiste);
		
		return panel;
	}

	private JPanel crearPanelBoton() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JButton botonCrearAlbum = new JButton("Crear Album");
		fixSize(botonCrearAlbum, ANCHURA_BOTON, ALTURA_BOTON);
		botonCrearAlbum.setIconTextGap(10);
		botonCrearAlbum.setFocusPainted(false);
		botonCrearAlbum.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonCrearAlbum.setBackground(new Color(30, 144, 255));
		
		botonCrearAlbum.addActionListener(e -> {
			String nombreAlbum = campoNombreAlbum.getText();
			//TODO Comprobar que el nombre del album no existe, usando el controlador
			VentanaPublicarFoto ventanaSubirFotoAlbum = new VentanaPublicarFoto(nombreAlbum);
			ventanaSubirFotoAlbum.setVisible(true);
			if(ventanaSubirFotoAlbum.isFotoPublicada()) frame.dispose();
		});
		
		panel.add(botonCrearAlbum);
		
		return panel;
	}

	private void initialize() {
		frame = new JDialog();
		frame.setSize(ANCHURA, ALTURA);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setModal(true);
		JPanel contenedor = new JPanel();
		frame.setContentPane(contenedor);
		contenedor.setPreferredSize(new Dimension(ANCHURA, ALTURA));
		contenedor.setLayout(new BorderLayout());
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}

}
