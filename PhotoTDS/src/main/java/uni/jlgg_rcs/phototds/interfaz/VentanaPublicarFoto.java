package uni.jlgg_rcs.phototds.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import uni.jlgg_rcs.phototds.controlador.Controlador;

public class VentanaPublicarFoto {
	
	private static final int ANCHURA = 800;
	private static final int ALTURA = 400;
	private static final int ANCHURA_BOTON = 300;
	private static final int ALTURA_BOTON = 100;
	private static final int TAMANO_TITULO = 30;
	private static final int TAMANO_CUERPO = 15;
	private static final int ANCHURA_CHOOSER = 500;
	private static final int ALTURA_CHOOSER = 600;
	private static final int ANCHURA_IMAGEN = ANCHURA/2;
	private static final int ALTURA_IMAGEN = (int)(ALTURA*0.9);
	private static final int ANCHURA_TEXTO = ANCHURA/2;
	private static final int ALTURA_TEXTO = (int)(ALTURA * 0.7);
	
	
	private JDialog frame;
	private JPanel contenedor;
	private JFileChooser chooser;
	private Image imagen;
	private JPanel panelSubirFoto;
	private JPanel panelFotoSeleccionada;
	private JPanel panelComentario;
	private JPanel panelCabeceraAlbum;
	private JLabel labelFoto;
	private String path;
	private String nombreAlbum;
	private boolean estaEnAlbum;
	private boolean fotoPublicada;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPublicarFoto v = new VentanaPublicarFoto("");
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
	public boolean isFotoPublicada() {
		return fotoPublicada;
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
	
	public VentanaPublicarFoto() {
		initialize();
		nombreAlbum = "";
		estaEnAlbum = false;
		panelSubirFoto = crearPanelSubirFoto();
		frame.getContentPane().add(panelSubirFoto,  BorderLayout.CENTER);
		panelFotoSeleccionada = crearPanelFotoSeleccionada();
		panelComentario = crearPanelComentario();
	}
	
	public VentanaPublicarFoto(String nombreAlbum) {
		initialize();
		this.nombreAlbum = nombreAlbum;
		estaEnAlbum = true;
		panelCabeceraAlbum = crearCabeceraAlbum(nombreAlbum);
		panelSubirFoto = crearPanelSubirFoto();
		frame.getContentPane().add(panelSubirFoto,  BorderLayout.CENTER);
		panelFotoSeleccionada = crearPanelFotoSeleccionada();
		panelComentario = crearPanelComentario();
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
		
		
		contenedor = new JPanel();
		frame.setContentPane(contenedor);
		contenedor.setPreferredSize(new Dimension(ANCHURA, ALTURA));
		contenedor.setLayout(new BorderLayout());
		
		fotoPublicada = false;
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
		
		
		
		JTextArea puedesArrastrar = new JTextArea("Puedes arrastrar el fichero aqui.");
		puedesArrastrar.setEditable(false);
		fixSize(puedesArrastrar, (int)puedesArrastrar.getPreferredSize().getWidth(), 30);
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
		
		
		JLabel mensajeError1 = new JLabel("Tienes que seleccionar una imagen :(");
		mensajeError1.setFont(new Font("Dialog", Font.PLAIN, TAMANO_CUERPO));
		mensajeError1.setForeground(Color.RED);
		mensajeError1.setAlignmentX(Component.CENTER_ALIGNMENT);
		mensajeError1.setVisible(false);
		panel.add(mensajeError1);
		
		JLabel mensajeError2 = new JLabel("No puedes seleccionar más de una imagen a la vez");
		mensajeError2.setFont(new Font("Dialog", Font.PLAIN, TAMANO_CUERPO));
		mensajeError2.setForeground(Color.RED);
		mensajeError2.setAlignmentX(Component.CENTER_ALIGNMENT);
		mensajeError2.setVisible(false);
		panel.add(mensajeError2);
		
		puedesArrastrar.setDropTarget(new DropTarget() {
		    public synchronized void drop(DropTargetDropEvent e) {
		        try {
		            e.acceptDrop(DnDConstants.ACTION_COPY);
		            Transferable transferible = e.getTransferable();
		            DataFlavor[] flavors = transferible.getTransferDataFlavors();
		                e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		                for (DataFlavor flavor : flavors) {
		                    try {
		                     // Si es un fichero
		                        if (flavor.isFlavorJavaFileListType()) {
		                            // Lo metemos en una lista
									List<File> files = (List<File>) transferible.getTransferData(flavor);
									//Si solo hay un fichero, lo miramos
									if(files.size() == 1) {
										 for (File file : files) {
				                            	//Miramos si podeemos leerlo como imagen
											 	Image imagen = ImageIO.read(file);
											 	if(imagen != null) {
											 		path = file.getAbsolutePath();
													insertarImagen(imagen);
													frame.getContentPane().removeAll();
													frame.getContentPane().add(panelFotoSeleccionada,  BorderLayout.WEST);
													frame.getContentPane().add(panelComentario, BorderLayout.CENTER);
													if(estaEnAlbum) frame.getContentPane().add(panelCabeceraAlbum, BorderLayout.NORTH);
													mensajeError1.setVisible(false);
													mensajeError2.setVisible(false);
													
													frame.getContentPane().revalidate();
													frame.getContentPane().repaint();
													frame.validate();
											 	} else {
											 		mensajeError1.setVisible(true);
											 		mensajeError2.setVisible(false);
											 	}

				                            }
									} else {
										mensajeError1.setVisible(false);
								 		mensajeError2.setVisible(true);
									}
		                           
		                           

		                        }

		                    } catch (Exception ex) {

		                        // Print out the error stack
		                        ex.printStackTrace();

		                    }
		                }
		            e.dropComplete(true);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
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
						frame.getContentPane().removeAll();
						frame.getContentPane().add(panelFotoSeleccionada,  BorderLayout.WEST);
						frame.getContentPane().add(panelComentario, BorderLayout.CENTER);
						if(estaEnAlbum) frame.getContentPane().add(panelCabeceraAlbum, BorderLayout.NORTH);
						mensajeError1.setVisible(false);
						mensajeError2.setVisible(false);
						
						frame.getContentPane().revalidate();
						frame.getContentPane().repaint();
						frame.validate();
					} catch (Exception e1) {
						//e1.printStackTrace();
						mensajeError1.setVisible(true);
						mensajeError2.setVisible(false);
						
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
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelBotones.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		panel.add(panelBotones);
		
		
		JButton compartir = new JButton("compartir");
		compartir.setFocusPainted(false);
		compartir.addActionListener(e -> {
			//TODO Comprobar si la foto esta en un album, y si el album no esta creado, crearlo.
			Controlador.INSTANCE.publicarImagen(texto.getText(), path);
			fotoPublicada = true;
			frame.dispose();
		});
		panelBotones.add(compartir);
		
		JButton cancel = new JButton("cancel");
		cancel.setFocusPainted(false);
		
		cancel.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(panelSubirFoto,  BorderLayout.CENTER);
			
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
			frame.validate();
		});
		panelBotones.add(cancel);
		
		
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
