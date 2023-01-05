package uni.jlgg_rcs.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import uni.jlgg_rcs.phototds.dominio.*;
import uni.jlgg_rcs.phototds.controlador.Controlador;

public class VentanaVerFoto {
	
	private static final int ANCHURA = 800;
	private static final int ALTURA = 400;
	private static final int ANCHURA_IMAGEN = ANCHURA/2;
	private static final int ALTURA_IMAGEN = (int)(ALTURA*0.9);
	private static final int ANCHURA_TEXTO = ANCHURA/2;
	private static final int ALTURA_TEXTO = (int)(ALTURA * 0.7);
	
	
	private JDialog frame;
	private JPanel contenedor;
	private Foto fotoSeleccionada;
	private JPanel panelFoto;
	private JPanel panelTexto;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Foto prueba = new Foto("prueba", "esto es una prueba", "resources/default_user_pic.png");
					VentanaVerFoto v = new VentanaVerFoto(prueba, "Parajes franceses");
					v.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Pone la ventana de publicar visible o la oculta
	 * @param b true o false dependiendo de si se quiere mostrar o esconder la ventana
	 */
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	
	public VentanaVerFoto(Foto foto) {
		initialize();
		
		panelFoto = crearPanelFotoSeleccionada(foto);
		panelTexto = crearPanelComentario();
		
		contenedor.add(panelFoto, BorderLayout.WEST);
		contenedor.add(panelTexto, BorderLayout.CENTER);
	}
	
	public VentanaVerFoto(Foto foto, String nombreAlbum) {
		initialize();
		
		JPanel panelCabeceraAlbum = crearCabeceraAlbum(nombreAlbum);
		panelFoto = crearPanelFotoSeleccionada(foto);
		panelTexto = crearPanelComentario();
		
		frame.getContentPane().add(panelCabeceraAlbum, BorderLayout.NORTH);
		frame.getContentPane().add(panelFoto, BorderLayout.WEST);
		frame.getContentPane().add(panelTexto, BorderLayout.CENTER);
	}

	private void initialize() {
		frame = new JDialog();
		frame.setSize(ANCHURA, ALTURA);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setModal(true);
		contenedor = new JPanel();
		frame.setContentPane(contenedor);
		contenedor.setPreferredSize(new Dimension(ANCHURA, ALTURA));
		contenedor.setLayout(new BorderLayout());
	}
	
	private void insertarImagen(JLabel label, Image imagen) {
		ImageIcon iconoImagen = new ImageIcon(imagen.getScaledInstance(ANCHURA_IMAGEN, ALTURA_IMAGEN, 0));
		label.setIcon(iconoImagen);
	}
	
	
	private JPanel crearPanelFotoSeleccionada(Foto foto) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setSize(new Dimension(ANCHURA,ALTURA));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JLabel labelFoto = new JLabel();
		insertarImagen(labelFoto, foto.getImagen());
		panel.add(labelFoto);
		return panel;
	}
	
	private JPanel crearPanelComentario() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setSize(new Dimension(ANCHURA_IMAGEN,ALTURA_IMAGEN));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		JLabel escribe = new JLabel("Escribe un comentario (maximo 200 caracteres)");
		escribe.setFont(new Font("Dialog", Font.PLAIN, 15));
		escribe.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		escribe.setBackground(Color.blue);
		panel.add(escribe);
		
		JTextArea texto = new JTextArea();
		fixSize(texto, ANCHURA_TEXTO, ALTURA_TEXTO);
		texto.setBackground(new Color(240, 240, 240));
		texto.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
		panel.add(texto);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.white);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBotones.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		panel.add(panelBotones);

		JButton botonOK = new JButton("OK");
		botonOK.setFocusPainted(false);
		botonOK.addActionListener(e -> {
			if(!texto.getText().isBlank()) {
				//TODO Poner una funcion del controlador para escribir comentarios
			}
			frame.dispose();
		});
		panelBotones.add(botonOK);
		
		
		return panel;
	}
	
	private JPanel crearCabeceraAlbum(String nombreAlbum) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel nombreDelAlbum = new JLabel(nombreAlbum);
		nombreDelAlbum.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(nombreDelAlbum);
		return panel;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}

}


