package uni.jlgg_rcs.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import uni.jlgg_rcs.phototds.controlador.Controlador;

public class VentanaPublicar {
	
	private static final int ANCHURA = 700;
	private static final int ALTURA = 500;
	private static final int ANCHURA_BOTON = 300;
	private static final int ALTURA_BOTON = 100;
	private static final int TAMANO_TITULO = 30;
	private static final int TAMANO_CUERPO = 15;
	private static final int ANCHURA_CHOOSER = 500;
	private static final int ALTURA_CHOOSER = 600;
	private static final int ANCHURA_IMAGEN = ANCHURA/2;
	private static final int ALTURA_IMAGEN = ALTURA;
	private static final int ANCHURA_TEXTO = ANCHURA/2;
	private static final int ALTURA_TEXTO = (int)(ALTURA * 0.75);
	private static final int ANCHURA_PANEL_BOTONES = ANCHURA/2;
	private static final int ALTURA_PANEL_BOTONES = (int)(ALTURA*0.1);
	private static final int ESPACIO_BOTONES = ANCHURA_PANEL_BOTONES/2;
	
	
	private JDialog frame;
	private JFileChooser chooser;
	private Image imagen;
	private JPanel panelSubirFoto;
	private JPanel panelFotoSeleccionada;
	private JLabel labelFoto;
	private String path;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPublicar v = new VentanaPublicar();
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
	
	public VentanaPublicar() {
		initialize();
		panelSubirFoto = crearPanelSubirFoto();
		frame.setContentPane(panelSubirFoto);
		panelFotoSeleccionada = crearPanelFotoSeleccionada();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setSize(ANCHURA, ALTURA);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setModal(true);
		chooser = new JFileChooser();
		chooser.setBorder(new TitledBorder(null, "+", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chooser.setDialogTitle("File");
		chooser.setPreferredSize(new Dimension(400, 250));
		fixSize(chooser, ANCHURA_CHOOSER, ALTURA_CHOOSER);
	}
	
	private JPanel crearPanelSubirFoto() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setSize(new Dimension(ANCHURA,ALTURA));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(Box.createVerticalStrut(50));
		
		JLabel agregarFoto = new JLabel("Agregar Foto");
		agregarFoto.setFont(new Font("Dialog", Font.BOLD, TAMANO_TITULO));
		agregarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(agregarFoto);
		
		panel.add(Box.createVerticalStrut(20));
		
		JLabel animate = new JLabel("Animate a compartir una foto con tus amigos.");
		animate.setFont(new Font("Dialog", Font.PLAIN, TAMANO_CUERPO));
		animate.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(animate);
		
		
		
		JLabel puedesArrastrar = new JLabel("Puedes arrastrar el fichero aqui.");
		puedesArrastrar.setFont(new Font("Dialog", Font.PLAIN, 15));
		puedesArrastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(puedesArrastrar);
		
		panel.add(Box.createVerticalStrut(30));
		
		JButton insertarFoto = new JButton("Seleccionar de tu ordenador");
		fixSize(insertarFoto, ANCHURA_BOTON, ALTURA_BOTON);
		insertarFoto.setBackground(new Color(30, 144, 255));
		insertarFoto.setForeground(Color.WHITE);
		insertarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
		insertarFoto.setFocusPainted(false);
		panel.add(insertarFoto);
		
		
		JLabel mensajeError = new JLabel("Tienes que seleccionar una imagen :(");
		mensajeError.setFont(new Font("Dialog", Font.PLAIN, TAMANO_CUERPO));
		mensajeError.setForeground(Color.RED);
		mensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);
		mensajeError.setVisible(false);
		panel.add(mensajeError);
		
		/* Funcionalidad para el boton de insertar fotos */
		insertarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resultado = chooser.showOpenDialog(chooser);
				if(resultado == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						imagen = ImageIO.read(file);
						path = file.getAbsolutePath();
						insertarImagen(imagen);
						frame.setContentPane(panelFotoSeleccionada);
						mensajeError.setVisible(false);
					} catch (Exception e1) {
						//e1.printStackTrace();
						mensajeError.setVisible(true);
						
					}
				}
			}

		});

		return panel;
	}
	
	private void insertarImagen(Image imagen) {
		ImageIcon iconoImagen = new ImageIcon(imagen.getScaledInstance(ANCHURA_IMAGEN, ALTURA_IMAGEN, 0));
		labelFoto.setIcon(iconoImagen);
	}
	
	private JPanel crearPanelFotoSeleccionada() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setSize(new Dimension(ANCHURA,ALTURA));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		labelFoto = new JLabel();
		panel.add(labelFoto);
		
		JPanel panelTexto = new JPanel();
		panelTexto.setBackground(Color.white);
		panelTexto.setSize(new Dimension(ANCHURA_IMAGEN,ALTURA_IMAGEN));
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		
		panel.add(panelTexto);
		
		JLabel escribe = new JLabel("Escribe un comentario (maximo 200 caracteres)");
		escribe.setFont(new Font("Dialog", Font.PLAIN, 15));
		escribe.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelTexto.add(escribe);
		
		JTextArea texto = new JTextArea();
		fixSize(texto, ANCHURA_TEXTO, ALTURA_TEXTO);
		texto.setBackground(new Color(240, 240, 240));
		texto.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelTexto.add(texto);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(Color.white);
		panelBotones.setSize(new Dimension(ANCHURA_PANEL_BOTONES, ALTURA_PANEL_BOTONES));
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelTexto.add(panelBotones);
		
		panelBotones.add(Box.createHorizontalStrut(ESPACIO_BOTONES));
		
		JButton compartir = new JButton("compartir");
		compartir.setFocusPainted(false);
		compartir.setAlignmentX(Component.RIGHT_ALIGNMENT);
		compartir.addActionListener(e -> {
			Controlador.INSTANCE.publicarImagen(texto.getText(), path);
			frame.dispose();
		});
		panelBotones.add(compartir);
		
		JButton cancel = new JButton("cancel");
		cancel.setFocusPainted(false);
		cancel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		cancel.addActionListener(e -> frame.setContentPane(panelSubirFoto));
		panelBotones.add(cancel);
		
		
		return panel;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}

}
