package uni.jlgg_rcs.phototds.interfaz;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

import uni.jlgg_rcs.phototds.dominio.Foto;
import uni.jlgg_rcs.phototds.controlador.Controlador;
import uni.jlgg_rcs.phototds.dominio.Publicacion;
import uni.jlgg_rcs.phototds.dominio.Usuario;

public class VentanaPrincipal {

	private static final int ANCHURA = 600;
	private static final int ALTURA = 700;
	private static final int ANCHURA_TITULO = 600;
	private static final int ALTURA_MENU = 70;
	private static final int ALTURA_PANEL_PUBLICACIONES = ALTURA - ALTURA_MENU - 45;
	private static final int ANCHURA_ENTRADA = ANCHURA;
	private static final int ALTURA_ENTRADA = ALTURA/10;
	private static final int ANCHURA_PANEL_PERFIL_USUARIO = ANCHURA;
	private static final int ALTURA_PANEL_PERFIL_USUARIO = ALTURA_PANEL_PUBLICACIONES;
	private static final int ALTURA_PANEL_INFO_USUARIO = ALTURA_PANEL_PERFIL_USUARIO/3;
	private static final int ANCHURA_FOTO_PERFIL_GRANDE = ANCHURA/5;
	private static final int ANCHURA_INFO_USUARIO = ANCHURA - ANCHURA_FOTO_PERFIL_GRANDE;
	private static final int ANCHURA_PANEL_FOTOS_Y_ALBUMES_USUARIO = ANCHURA;
	private static final int ALTURA_PANEL_FOTOS_Y_ALBUMES_USUARIO =  ALTURA - ALTURA_PANEL_INFO_USUARIO;
	private static final int ANCHURA_PANEL_BOTONES_FOTOS_ALBUMES_USUARIO = ANCHURA;
	private static final int ALTURA_PANEL_BOTONES_FOTOS_ALBUMES_USUARIO = ALTURA_PANEL_FOTOS_Y_ALBUMES_USUARIO/12;
	private static final int ANCHURA_MATRIZ_FOTOS = ANCHURA;
	private static final int ALTURA_MATRIZ_FOTOS = ALTURA_PANEL_FOTOS_Y_ALBUMES_USUARIO - ALTURA_PANEL_BOTONES_FOTOS_ALBUMES_USUARIO;
	
	private JFrame frame;
	private JPanel contenedor;
	private JPanel menuPrincipal;
	private JPanel panelPublicaciones;
	private JPanel panelPerfilUsuario;
	private JList<Foto> listaFotos;
	private DefaultListModel<Foto> fotosListModel;
	private Usuario usuario;
	private boolean vistaPerfil;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal v = new VentanaPrincipal();
					v.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaPrincipal() {
		initialize();
		menuPrincipal = crearMenuPrincipal();
		contenedor.add(menuPrincipal,  BorderLayout.NORTH);
		panelPublicaciones = crearPanelPublicaciones();
		contenedor.add(panelPublicaciones,  BorderLayout.CENTER);
		
	}

	private void initialize() {
		usuario = Controlador.INSTANCE.getUsuario();
		//inicializamos el frame
		frame = new JFrame();
		frame.setSize(ANCHURA, ALTURA);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ponemos el booleano vistaPerfil a false, porque empezamos con la vista
		//de la ventana principal, con las entradas de publicaciones
		vistaPerfil = false;
		
		//y ponemos el panel contenedorPrincipal como panel de contenido del frame
		contenedor = new JPanel();
		frame.setContentPane(contenedor);
		contenedor.setBackground(Color.white);
	}
	
	/**
	 *  Crea el panel de publicaciones, y devuelve su JPanel
	 * @return devuelve un JPanel, que representa el panel de publicaciones
	 */
	private JPanel crearPanelPublicaciones() {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.blue);
		fixSize(panel, ANCHURA, ALTURA_PANEL_PUBLICACIONES);
		/*List<Publicacion> listaPublicaciones = Controlador.INSTANCE.getListaPrincipalUsuario();
		
		// Lista publicaciones
		JList<EntradaPublicacion> lista=new JList<EntradaPublicacion>();
		DefaultListModel<EntradaPublicacion> model= new DefaultListModel<>();
		listaPublicaciones.stream()
			.map(p -> new EntradaPublicacion(ANCHURA_ENTRADA, ALTURA_ENTRADA, p))
			.forEach(e -> model.addElement(e));
		
		lista.setModel(model);
		lista.setCellRenderer(new EntradaPublicacionListRenderer());
				
		JScrollPane scroll = new JScrollPane(lista);
		fixSize(scroll, ANCHURA, ALTURA_PANEL_PUBLICACIONES);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(scroll);*/
		
		return panel;
	}
	
	/**
	 * 	Crea el menu de la ventana principal, y devuelve su JPanel
	 * @return devuelve un JPanel, que representa el menu
	 */
	private JPanel crearMenuPrincipal() {
		JPanel menu = new JPanel();
		fixSize(menu, ANCHURA, ALTURA_MENU);
		menu.setBackground(Color.WHITE);
		menu.setLayout(new BoxLayout(menu,BoxLayout.X_AXIS));
		
		/** Ponemos el nombre de la aplicación en un JLabel **/
		JLabel lblTitulo = new JLabel("PhotoTDS");
		lblTitulo.setBackground(Color.red);
		lblTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 30));
		
		/** Lo ponemos también en un botón, que haremos visible cuando pasemos a la vista de perfil de usuario **/
		JButton botonTitulo = new JButton("PhotoTDS");
		botonTitulo.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 30));
		botonTitulo.setFocusPainted(false);
		botonTitulo.setBackground(Color.WHITE);
		botonTitulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		botonTitulo.setVisible(false);
		
		/** Insertamos un botón para subir fotos **/
		JButton botonInsertarFoto = new JButton(" ＋ ");
		botonInsertarFoto.setFont(new Font("Dialog", Font.BOLD, 20));
		botonInsertarFoto.setBackground(Color.WHITE);
		botonInsertarFoto.setFocusPainted(false);
		botonInsertarFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		botonInsertarFoto.addActionListener(e -> {
			VentanaPublicarFoto publicar = new VentanaPublicarFoto();
			publicar.setVisible(true);
		});
		
		/** Insertamos un botón para subir albumes **/
		JButton botonInsertarAlbum = new JButton(" A＋ ");
		botonInsertarAlbum.setFont(new Font("Dialog", Font.BOLD, 20));
		botonInsertarAlbum.setBackground(Color.WHITE);
		botonInsertarAlbum.setFocusPainted(false);
		botonInsertarAlbum.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		botonInsertarAlbum.addActionListener(e -> {
			VentanaPublicarAlbum publicarAlbum = new VentanaPublicarAlbum();
			publicarAlbum.setVisible(true);
		});
		
		botonInsertarAlbum.setVisible(false); //y lo ocultamos para la ventana principal
		
		/** Insertamos la barra de busqueda **/		
		JTextField barraDeBusqueda = new JTextField("", 5);
		barraDeBusqueda.setFont(new Font("Dialog", Font.PLAIN, 20));
		barraDeBusqueda.setMaximumSize(barraDeBusqueda.getPreferredSize());
		
		/** Insertamos la lupa a la derecha de la barra de busqueda **/
		JButton lupa = new JButton("🔍");
		lupa.setFont(new Font("Dialog", Font.BOLD, 20));
		lupa.setFocusPainted(false);
		lupa.setBackground(Color.WHITE);
		lupa.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		
		/*TODO cambiar este JButton, y el JLabel siguiente por una foto 
		 * del usuario inscrita en un círculo.
		 * */
		JButton botonFotoUsuario = new JButton("foto");
		botonFotoUsuario.setFont(new Font("Arial", Font.PLAIN, 20));
		botonFotoUsuario.setFocusPainted(false);
		botonFotoUsuario.setBackground(Color.WHITE);
		botonFotoUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		
		JLabel lblFotoUsuario = new JLabel("foto");
		lblFotoUsuario.setBackground(Color.red);
		lblFotoUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		lblFotoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblFotoUsuario.setFont(new Font("Arial", Font.PLAIN, 20));
		lblFotoUsuario.setVisible(false);
		
		/** Finalmente, insertamos el boton para las opciones Premium **/
		JButton opcionesPremium = new JButton("≡");
		opcionesPremium.setFont(new Font("Dialog", Font.BOLD, 20));
		opcionesPremium.setFocusPainted(false);
		opcionesPremium.setBackground(Color.WHITE);
		opcionesPremium.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		
		Component ultimoEspaciado = Box.createHorizontalStrut(70);
		
		botonFotoUsuario.addActionListener(e -> {
			/** al hacer click sobre la foto del usuario en el menu, cambiamos al perfil del usuario **/
			vistaPerfil = true;
			
			panelPerfilUsuario = crearPanelUsuario();
			panelPublicaciones.setVisible(false);
			contenedor.add(panelPerfilUsuario, BorderLayout.CENTER);
			
			botonTitulo.setVisible(true);
			botonInsertarAlbum.setVisible(true);
			lblFotoUsuario.setVisible(true);

			lblTitulo.setVisible(false);
			botonFotoUsuario.setVisible(false);
			ultimoEspaciado.setVisible(false);
			opcionesPremium.setVisible(false);
			
			//finalmente refrescamos los componentes necesarios
			contenedor.revalidate();
			contenedor.repaint();
			frame.validate();
		});
		
		botonTitulo.addActionListener(e -> {
			vistaPerfil = false;
			
			panelPublicaciones = crearPanelPublicaciones();
			panelPerfilUsuario.setVisible(false);
			contenedor.add(panelPublicaciones, BorderLayout.CENTER);
			
			lblTitulo.setVisible(true);
			botonFotoUsuario.setVisible(true);
			ultimoEspaciado.setVisible(true);
			opcionesPremium.setVisible(true);
			
			botonTitulo.setVisible(false);
			botonInsertarAlbum.setVisible(false);
			lblFotoUsuario.setVisible(false);
			
			//finalmente refrescamos los componentes necesarios
			contenedor.revalidate();
			contenedor.repaint();
			frame.validate();
			
		});
		
		
		menu.add(Box.createHorizontalStrut(10));
		menu.add(lblTitulo);
		menu.add(botonTitulo);
		menu.add(Box.createHorizontalStrut(70));
		menu.add(botonInsertarFoto);
		menu.add(Box.createHorizontalStrut(10));
		menu.add(botonInsertarAlbum);
		menu.add(Box.createHorizontalStrut(10));
		menu.add(barraDeBusqueda);
		menu.add(lupa);
		menu.add(Box.createHorizontalStrut(70));
		menu.add(botonFotoUsuario);
		menu.add(lblFotoUsuario);
		menu.add(ultimoEspaciado);
		menu.add(opcionesPremium);
		
		return menu;
	}
	
	private JPanel crearPanelUsuario() {
		
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.yellow);
		fixSize(panel, ANCHURA_PANEL_PERFIL_USUARIO, ALTURA_PANEL_PERFIL_USUARIO);
		
		//Insertamos un panel con un BoxLayout orientado en el eje X
		//para incluir la foto de perfil, el nombre de usuario, los seguidores, etc.
		
		JPanel panelInfoYFoto = new JPanel();
		panelInfoYFoto.setLayout(new BoxLayout(panelInfoYFoto, BoxLayout.X_AXIS));
		panelInfoYFoto.setBackground(Color.orange);
		fixSize(panelInfoYFoto, ANCHURA_PANEL_PERFIL_USUARIO, ALTURA_PANEL_INFO_USUARIO);
		
		//Insertamos la foto de perfil en grande
		JLabel lblFotoPerfil = new JLabel("Foto");
		fixSize(lblFotoPerfil, ANCHURA_FOTO_PERFIL_GRANDE, ALTURA_PANEL_INFO_USUARIO);
		
		//Insertamos un nuevo panel, con un BoxLayout orientado en el eje Y
		//para meter toda la info del perfil, menos las fotos y los albumes
		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		panelInfo.setBackground(Color.red);
		fixSize(panelInfo, ANCHURA_INFO_USUARIO , ALTURA_PANEL_INFO_USUARIO);
		
		/** Empezamos metiendo un FlowLayout con el nombre del usuario y el boton correspondiente **/
		JPanel panelNombreYBoton = new JPanel();
		panelNombreYBoton.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixSize(panelNombreYBoton, ANCHURA_INFO_USUARIO, ALTURA_PANEL_INFO_USUARIO/3);
		
		JLabel lblNombreUsuario = new JLabel("jesus.gmolina");
		lblNombreUsuario.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		JButton botonEditarPerfil = new JButton("\bEditar Perfil\b");
		botonEditarPerfil.setFont(new Font("Dialog", Font.ITALIC, 20));
		botonEditarPerfil.setFocusPainted(false);
		botonEditarPerfil.setForeground(Color.white);
		botonEditarPerfil.setBackground(new Color(30, 144, 255));
		botonEditarPerfil.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 5));
		
		JButton botonSeguir = new JButton(" Seguir ");
		botonSeguir.setFont(new Font("Dialog", Font.ITALIC, 20));
		botonSeguir.setFocusPainted(false);
		botonSeguir.setForeground(Color.white);
		botonSeguir.setBackground(new Color(30, 144, 255));
		botonSeguir.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		botonSeguir.setVisible(false);
		
		/** Ahora metemos otro FlowLayout para mostrar las publicaciones, seguidores y seguidos del usuario **/
		JPanel panelPublicacionesSeguidoresYSeguidos = new JPanel();
		panelPublicacionesSeguidoresYSeguidos.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixSize(panelPublicacionesSeguidoresYSeguidos, ANCHURA_INFO_USUARIO, ALTURA_PANEL_INFO_USUARIO/3);
		
		JLabel lblPublicaciones = new JLabel("13 publicaciones");
		lblPublicaciones.setFont(new Font("Dialog", Font.PLAIN, 17));
		
		JLabel lblSeguidores = new JLabel("7 seguidores");
		lblSeguidores.setFont(new Font("Dialog", Font.PLAIN, 17));
		
		JLabel lblSeguidos = new JLabel("7 seguidos");
		lblSeguidos.setFont(new Font("Dialog", Font.PLAIN, 17));
		
		
		/** A continuacion, insertamos un tercer FlowLayout para mostrar el nombre completo del usuario **/
		JPanel panelNombreCompleto = new JPanel();
		panelNombreCompleto.setLayout(new FlowLayout(FlowLayout.LEFT));
		fixSize(panelNombreCompleto, ANCHURA_INFO_USUARIO, ALTURA_PANEL_INFO_USUARIO/3);
		
		JLabel lblNombreCompleto = new JLabel("Jesús García Molina");
		lblNombreCompleto.setFont(new Font("Dialog", Font.PLAIN, 17));
		
		/** Ahora metemos el panel con las fotos y los albumes **/
		JPanel panelFotosYAlbumes = new JPanel();
		panelFotosYAlbumes.setLayout(new BoxLayout(panelFotosYAlbumes, BoxLayout.Y_AXIS));
		fixSize(panelFotosYAlbumes, ANCHURA_PANEL_FOTOS_Y_ALBUMES_USUARIO, ALTURA_PANEL_FOTOS_Y_ALBUMES_USUARIO);
		
		/** Empezamos introduciendo un FlowLayout con los botones para cambiar entre albumes y fotos **/
		JPanel panelBotonesFotosAlbumes = new JPanel();
		panelBotonesFotosAlbumes.setLayout(new FlowLayout(FlowLayout.CENTER));
		fixSize(panelBotonesFotosAlbumes, ANCHURA_PANEL_BOTONES_FOTOS_ALBUMES_USUARIO, ALTURA_PANEL_BOTONES_FOTOS_ALBUMES_USUARIO);
		panelBotonesFotosAlbumes.setBackground(Color.red);
		
		JButton botonFotos = new JButton("Fotos");
		botonFotos.setFont(new Font("Dialog", Font.PLAIN, 17));
		botonFotos.setFocusPainted(false);
		botonFotos.setBackground(Color.WHITE);
		botonFotos.setForeground(Color.lightGray);
		botonFotos.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		
		JButton botonAlbumes = new JButton("Albumes");
		botonAlbumes.setFont(new Font("Dialog", Font.PLAIN, 17));
		botonAlbumes.setFocusPainted(false);
		botonAlbumes.setBackground(Color.WHITE);
		botonAlbumes.setForeground(Color.lightGray);
		botonAlbumes.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
		
		/** Por último, el panel con la matriz de fotos o albumes**/
		JPanel panelMatrizFotos = new JPanel();
		panelMatrizFotos.setLayout(new BoxLayout(panelMatrizFotos, BoxLayout.Y_AXIS));
		panelMatrizFotos.setAlignmentX(Component.CENTER_ALIGNMENT);
		fixSize(panelMatrizFotos, ANCHURA_MATRIZ_FOTOS, ALTURA_MATRIZ_FOTOS);
		panelMatrizFotos.setBackground(Color.green);
		
		/*Ponemos la matriz de fotos */
		
		JScrollPane scrollFotos = new JScrollPane();
		scrollFotos.setBackground(Color.WHITE);
		panelMatrizFotos.add(scrollFotos);
		listaFotos = new JList<Foto>();
		//TODO funcion que te devuelva la lista de imagenes que ha publicado un usuario
		listaFotos = Controlador.INSTANCE.getListaImagenesUsuario();
		fotosListModel = new DefaultListModel<Foto>();
		listaFotos.setModel(fotosListModel);
		usuario.getFotos().forEach(f -> fotosListModel.addElement(f));
		listaFotos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaFotos.setVisibleRowCount(-1);
		listaFotos.ensureIndexIsVisible(panelMatrizFotos.getHeight());
		listaFotos.setCellRenderer(new EntradaPublicacionListRenderer());
		fixSize(scrollFotos, ANCHURA_MATRIZ_FOTOS, ALTURA_MATRIZ_FOTOS);
		scrollFotos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollFotos.setViewportView(listaFotos);
		
		/**Metemos un Mouse Listener para sacar un PopUp Menu cuando se hace click
		 * izquierdo sobre una foto
		 */
		
		listaFotos.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
	             if (SwingUtilities.isRightMouseButton(e)) {
	                int indice = listaFotos.locationToIndex(e.getPoint());
	                if(indice != -1  && listaFotos.getCellBounds(indice, indice).contains(e.getPoint())) {
	                	JPopupMenu menuContextual = new JPopupMenu();
				        JMenuItem itemVerFoto = new JMenuItem("Ver");
				        JMenuItem itemBorrarFoto = new JMenuItem("Borrar");
				        itemVerFoto.addActionListener(evento -> {
				        	VentanaVerFoto verFoto = new VentanaVerFoto(fotosListModel.getElementAt(indice));
							verFoto.setVisible(true);
				        });
				        itemBorrarFoto.addActionListener(evento -> {
				        	//TODO crear metodo del controlador que elimine una foto subida por un usuario
				        	// en este caso hay que eliminar la foto fotosListModel.getElementAt(indice)
				        });
				        
				        
				        menuContextual.add(itemVerFoto);
				        menuContextual.add(itemBorrarFoto);  
				        menuContextual.show(e.getComponent(), e.getX(), e.getY());
	                }
	             }
	          }
	       });
		
		
		
		panel.add(panelInfoYFoto);
		panelInfoYFoto.add(lblFotoPerfil);
		panelInfoYFoto.add(panelInfo);
		
		panelInfo.add(panelNombreYBoton);
		panelNombreYBoton.add(Box.createHorizontalStrut(20));
		panelNombreYBoton.add(lblNombreUsuario);
		panelNombreYBoton.add(Box.createHorizontalStrut(20));
		panelNombreYBoton.add(botonEditarPerfil);
		panelNombreYBoton.add(botonSeguir);
		
		panelInfo.add(panelPublicacionesSeguidoresYSeguidos);
		panelPublicacionesSeguidoresYSeguidos.add(Box.createHorizontalStrut(20));
		panelPublicacionesSeguidoresYSeguidos.add(lblPublicaciones);
		panelPublicacionesSeguidoresYSeguidos.add(Box.createHorizontalStrut(20));
		panelPublicacionesSeguidoresYSeguidos.add(lblSeguidores);
		panelPublicacionesSeguidoresYSeguidos.add(Box.createHorizontalStrut(20));
		panelPublicacionesSeguidoresYSeguidos.add(lblSeguidos);
		
		panelInfo.add(panelNombreCompleto);
		panelNombreCompleto.add(Box.createHorizontalStrut(20));
		panelNombreCompleto.add(lblNombreCompleto);
		
		
		panel.add(panelFotosYAlbumes);
		panelFotosYAlbumes.add(panelBotonesFotosAlbumes);
		panelFotosYAlbumes.add(panelMatrizFotos);
		
		panelBotonesFotosAlbumes.add(botonFotos);
		panelBotonesFotosAlbumes.add(Box.createHorizontalStrut(20));
		panelBotonesFotosAlbumes.add(botonAlbumes);
		
		
		
		return panel;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}

}