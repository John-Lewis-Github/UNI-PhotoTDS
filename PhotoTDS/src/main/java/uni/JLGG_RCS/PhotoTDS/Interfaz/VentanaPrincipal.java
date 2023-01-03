package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.*;

import uni.JLGG_RCS.PhotoTDS.Controlador.Controlador;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;

public class VentanaPrincipal {

	private static final int ANCHURA = 600;
	private static final int ALTURA = 700;
	private static final int ANCHURA_TITULO = 600;
	private static final int ALTURA_MENU = 70;
	private static final int ALTURA_PANEL_PUBLICACIONES = ALTURA - ALTURA_MENU;
	private static final int ANCHURA_ENTRADA = ANCHURA;
	private static final int ALTURA_ENTRADA = ALTURA/10;
	
	private JFrame framePrincipal;
	private JPanel contenedor;
	private JPanel menu;
	private JPanel panelPublicaciones;
	
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
		menu = crearMenu();
		contenedor.add(menu);
		panelPublicaciones = crearPanelPublicaciones();
		contenedor.add(panelPublicaciones);
	}

	private void initialize() {
		
		//inicializamos el frame principal
		framePrincipal = new JFrame();
		framePrincipal.setSize(ANCHURA, ALTURA);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//y ponemos el panel contenedor como panel de contenido del frame principal
		contenedor = new JPanel();
		framePrincipal.setContentPane(contenedor);
		contenedor.setBackground(Color.white);
	}
	
	// Con este método se flexibiliza la creación del panel principal
	/**
	 *  Crea el panel de publicaciones, y devuelve su JPanel
	 * @return devuelve un JPanel, que representa el panel de publicaciones
	 */
	private JPanel crearPanelPublicaciones() {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.blue);
		fixSize(panel, ANCHURA, ALTURA_PANEL_PUBLICACIONES);
		List<Publicacion> listaPublicaciones = Controlador.INSTANCE.getListaPrincipalUsuario();
		
		//Lista publicaciones
		JList<EntradaPublicacion> lista=new JList<EntradaPublicacion>();
		DefaultListModel<EntradaPublicacion> model= new DefaultListModel<>();
		listaPublicaciones.stream()
			.map(p -> new EntradaPublicacion(ANCHURA_ENTRADA, ALTURA_ENTRADA, p))
			.forEach(e -> model.addElement(e));
		
		lista.setModel(model);
		lista.setCellRenderer(new EntradaPublicacionListRenderer());
				
		JScrollPane scroll=new JScrollPane(lista);
		fixSize(scroll,ANCHURA,ALTURA_PANEL_PUBLICACIONES);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(scroll);
		
		return panel;
	}
	
	/**
	 * 	Crea el menu de la ventana principal, y devuelve su JPanel
	 * @return devuelve un JPanel, que representa el menu
	 */
	private JPanel crearMenu() {
		/**--Hacemos el menú de arriba--*/
		JPanel menu = new JPanel();
		JLabel titulo = new JLabel("PhotoTDS");
		fixSize(menu, ANCHURA, ALTURA_MENU);
		menu.setBackground(Color.WHITE);
		menu.setLayout(new BoxLayout(menu,BoxLayout.X_AXIS));
		/** Añadimos el nombre de la aplicación **/
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
		
		nuevaFoto.addActionListener(e -> {
			VentanaPublicar publicar = new VentanaPublicar();
			publicar.setVisible(true);
		});
		
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
		
		return menu;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}

}
