package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;

public class VentanaPrincipal {

	private static final int ANCHURA = 600;
	private static final int ALTURA = 700;
	private static final int ANCHURA_TITULO = 600;
	private static final int ALTURA_MENU = 70;
	
	private JFrame framePrincipal;
	private JPanel contenedor;
	private JPanel menu;
	private JPanel panelPrincipal;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal v = new VentanaPrincipal();
					v.framePrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaPrincipal() {
		initialize();
		menu = crearPanel(0);
		contenedor.add(menu);
		panelPrincipal = crearPanel(1);
		contenedor.add(panelPrincipal);
		rellenarMenu(menu);
	}

	private void initialize() {
		framePrincipal = new JFrame();
		framePrincipal.setSize(ANCHURA, ALTURA);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor = new JPanel();
		framePrincipal.setContentPane(contenedor);
		contenedor.setBackground(Color.white);
	}
	
	// Con este método se flexibiliza la creación del panel principal
	private JPanel crearPanel(int tipo) {
		JPanel panel=new JPanel();
		// JPanel panelPrincipal = new JPanel();
		// contenedor.add(panelPrincipal);
		if(tipo == 0) {
			/**--Hacemos el menú de arriba--*/
			fixSize(panel, ANCHURA, ALTURA_MENU);
			panel.setBackground(Color.WHITE);
			panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		} else {
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.blue);
		}
		return panel;
	}
	
	
	private void rellenarMenu(JPanel menu) {
		/** Añadimos el nombre de la aplicación **/
		JLabel titulo = new JLabel("PhotoTDS");
		titulo.setBackground(Color.red);
		titulo.setHorizontalTextPosition(SwingConstants.CENTER);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 30));
		//fixSize(titulo, ALTURA_MENU, ANCHURA_TITULO);
		menu.add(titulo);
		
		/** Ponemos cierto espacio horizontal **/
		
		JPanel espacio1 = new JPanel();
		fixSize(espacio1, 80, ALTURA_MENU/2);
		espacio1.setBackground(menu.getBackground());
		menu.add(espacio1);
		
		
		/** Insertamos un botón para subir fotos **/
		JButton nuevaFoto = new JButton(" ＋ ");
		nuevaFoto.setFont(new Font("Dialog", Font.BOLD, 20));
		nuevaFoto.setBackground(Color.WHITE);
		nuevaFoto.setFocusPainted(false);
		nuevaFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		menu.add(nuevaFoto);
		JPanel formateo = new JPanel();
		fixSize(formateo, 110, ALTURA_MENU/2);
		formateo.setBackground(menu.getBackground());
		menu.add(formateo);
		JTextField barraDeBusqueda = new JTextField();
		barraDeBusqueda.setColumns(9);
		formateo.add(barraDeBusqueda);
		JButton lupa = new JButton("🔍");
		lupa.setFont(new Font("Dialog", Font.BOLD, 20));
		lupa.setFocusPainted(false);
		lupa.setBackground(Color.WHITE);
		lupa.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		menu.add(lupa);
		
		/*JMenu opcionesPremium = new JMenu("≡");
		menubar.add(opcionesPremium);
		JMenuItem premium=new JMenuItem("Premium");
		JMenuItem generarPDF=new JMenuItem("Generar PDF");
		JMenuItem generarExcel=new JMenuItem("Generar Excel");
		JMenuItem topMeGusta=new JMenuItem("Top me gusta");
		opcionesPremium.add(premium);
		opcionesPremium.add(generarPDF);
		opcionesPremium.add(generarExcel);
		opcionesPremium.add(topMeGusta);*/
		
		/** Ponemos, de nuevo, espacio horizontal **/
		
		JPanel espacio2 = new JPanel();
		fixSize(espacio2, 80, ALTURA_MENU/2);
		espacio2.setBackground(menu.getBackground());
		menu.add(espacio2);
		
		/*TODO cambiar este JLabel por una foto del usuario inscrita en un círculo.
		 		Hacer una nueva clase que extienda JPanel */
		JLabel foto = new JLabel("foto");
		foto.setFont(new Font("Arial", Font.PLAIN, 20));
		menu.add(foto);
		
		/** Ultimo JPanel del menu que usaremos para espaciar elementos **/
		
		JPanel espacio3 = new JPanel();
		fixSize(espacio3, 70, ALTURA_MENU/2);
		espacio3.setBackground(menu.getBackground());
		menu.add(espacio3);
		
		
		JButton opcionesPremium = new JButton("≡");
		opcionesPremium.setFont(new Font("Dialog", Font.BOLD, 20));
		opcionesPremium.setFocusPainted(false);
		opcionesPremium.setBackground(Color.WHITE);
		opcionesPremium.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		menu.add(opcionesPremium);
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}

}
