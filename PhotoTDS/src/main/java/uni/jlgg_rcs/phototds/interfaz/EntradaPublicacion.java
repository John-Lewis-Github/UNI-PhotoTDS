package uni.jlgg_rcs.phototds.interfaz;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import uni.jlgg_rcs.phototds.controlador.Controlador;
import uni.jlgg_rcs.phototds.dominio.Publicacion;


public class EntradaPublicacion extends JPanel {
	
	private Image imagenPublicacion;
	private Image imagenEscalada;
	/**
	 * Esta clase representa una entrada del conjunto de publicaciones mostrado
	 * en la ventana principal.
	 * 
	 * Está formada por una imagen, dos botones, un contador de me gusta y el usuario
	 * que la ha publicado
	 * 
	 * Para programarla lo suyo sería:
	 * 
	 * Panel principal (BoxLayout X Axis)
	 *    Panel imagen (BoxLayout el que sea)
	 *    	Imagen
	 *    Panel info (BoxLayout Y Axis)
	 *    	PanelBotonesyMegusta(FlowLayout)
	 *    		Boton Megusta
	 *    		Boton Comentar
	 *    		Contador de MeGusta
	 *    	Panel Usuario (FlowLayout)
	 *    		ImagenUsuario (la miniatura esa)
	 *    		NombreUsuario
	 */
	public EntradaPublicacion(int ancho, int alto, Publicacion publicacion) {
		
		//Construimos la entrada publicación usando un BoxLayout orientado en el eje X
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		fixSize(this,ancho,alto);
		this.setBackground(Color.WHITE);
		
		//Le ponemos la imagen pasada como parametro en el constructor
		JLabel lblImagen = new JLabel();
		imagenPublicacion = publicacion.getImagenPrincipal();
		imagenEscalada = imagenPublicacion.getScaledInstance(ancho/3, alto, 0);
		ImageIcon iconoImagenPublicacion = new ImageIcon(imagenEscalada);
		lblImagen.setIcon(iconoImagenPublicacion);
		fixSize(lblImagen,ancho/3,alto);
		
		// Al pinchar en la imagen debemos obtener un dialogo con la imagen real
		lblImagen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JDialog dialog = new JDialog();
				dialog.setModal(true);
				JPanel panel = new JPanel();
				dialog.setContentPane(panel);
				
				JLabel lblImagenAmpliada = new JLabel();
				ImageIcon iconoAmpliada = new ImageIcon(imagenPublicacion);
				lblImagenAmpliada.setIcon(iconoAmpliada);
				panel.add(lblImagenAmpliada);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		
		//ponemos un panel con un BoxLayout orientado en el eje Y para colocar el resto de la info
		JPanel panelInfo=new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo,BoxLayout.Y_AXIS));
		fixSize(panelInfo,2*ancho/3, alto);
		panelInfo.setOpaque(false);
		
		//ponemos un panel con un FlowLayout para los botones y el contador de me gusta
		JPanel panelBotonesyMeGusta = new JPanel();
		panelBotonesyMeGusta.setLayout(new FlowLayout());
		fixSize(panelBotonesyMeGusta, 2*ancho/3, alto/2);
		
		//metemos el contador de me gusta
		JLabel lblNumeroMeGustas = new JLabel(publicacion.getMeGusta() + " Me gusta");
		lblNumeroMeGustas.setFont(new Font("Dialog", Font.ITALIC, 15));
		
		//metemos el boton de me gusta
		JButton botonMeGusta = new JButton("❤️");
		botonMeGusta.setForeground(Color.white);
		
		// Al dar "meGusta" se repintara la etiqueta de "meGusta"
		botonMeGusta.addActionListener(e -> {
			Controlador.INSTANCE.darMeGusta(publicacion);
			lblNumeroMeGustas.setText(publicacion.getMeGusta() + " Me gusta");
			lblNumeroMeGustas.repaint();
		});
		
		//metemos el boton de comentar 💬
		JButton botonComentar = new JButton("🗨︎");
		botonComentar.setForeground(Color.white);
		
		//ponemos un panel con un FlowLayout para poner la imagen del usuario
		//que ha subido la publicacion, y su nombre
		JPanel panelUsuario = new JPanel();
		panelUsuario.setLayout(new FlowLayout());
		fixSize(panelUsuario, 2*ancho/3, alto/2);
		
		//metemos la foto del usuario que ha subido la publicacion
		JLabel lblFotoUsuario = new JLabel();
		Image imagenUsuario = Controlador.INSTANCE.getImagenUsuario(publicacion);
		ImageIcon iconoImagenUsuario = new ImageIcon(imagenUsuario.getScaledInstance(ancho/6, alto, 0));
		lblFotoUsuario.setIcon(iconoImagenUsuario);
		fixSize(lblFotoUsuario,ancho/6,alto);
		
		//metemos el nombre del usuario
		String nombreUsuario = publicacion.getUsuario().getNombreUsuario();
		JLabel lblNombreUsuario = new JLabel(nombreUsuario);
		lblNombreUsuario.setFont(new Font("Dialog", Font.PLAIN, 10));
		
		this.add(lblImagen);
		this.add(panelInfo);
		panelInfo.add(panelBotonesyMeGusta);
		panelBotonesyMeGusta.add(botonMeGusta);
		panelBotonesyMeGusta.add(botonComentar);
		panelBotonesyMeGusta.add(lblNumeroMeGustas);
		panelInfo.add(panelUsuario);
		panelUsuario.add(lblFotoUsuario);
		panelUsuario.add(lblNombreUsuario);
		
	}
	
	private void fixSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
	}

}
