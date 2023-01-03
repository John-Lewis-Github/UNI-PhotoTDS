package uni.JLGG_RCS.PhotoTDS.Interfaz;
import uni.JLGG_RCS.PhotoTDS.Controlador.Controlador;
import uni.JLGG_RCS.PhotoTDS.Dominio.Publicacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class EntradaPublicacion extends JPanel {

	
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
		JLabel lblImagen=new JLabel();
		//lblimagen.setIcon(new ImageIcon(getClass().getResource(path)));
		Image imagenPublicacion = publicacion.getImagenPrincipal(ancho/3, alto);
		ImageIcon iconoImagenPublicacion = new ImageIcon(imagenPublicacion);
		lblImagen.setIcon(iconoImagenPublicacion);
		fixSize(lblImagen,ancho/3,alto);
		
		
		//ponemos un panel con un BoxLayout orientado en el eje Y para colocar el resto de la info
		JPanel panelInfo=new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo,BoxLayout.Y_AXIS));
		fixSize(panelInfo,2*ancho/3, alto);
		panelInfo.setOpaque(false);
		
		//ponemos un panel con un FlowLayout para los botones y el contador de me gusta
		JPanel panelBotonesyMeGusta = new JPanel();
		panelBotonesyMeGusta.setLayout(new FlowLayout());
		fixSize(panelBotonesyMeGusta, 2*ancho/3, alto/2);
		
		//metemos el boton de me gusta
		JButton botonMeGusta = new JButton("❤️");
		botonMeGusta.setForeground(Color.white);
		
		//metemos el boton de comentar 💬
		JButton botonComentar = new JButton("🗨︎");
		botonComentar.setForeground(Color.white);
		
		//metemos el contador de me gusta
		JLabel lblNumeroMeGustas = new JLabel(publicacion.getMeGusta() + " Me gusta");
		lblNumeroMeGustas.setFont(new Font("Dialog", Font.ITALIC, 15));
		
		//ponemos un panel con un FlowLayout para poner la imagen del usuario
		//que ha subido la publicacion, y su nombre
		JPanel panelUsuario = new JPanel();
		panelUsuario.setLayout(new FlowLayout());
		fixSize(panelUsuario, 2*ancho/3, alto/2);
		
		//metemos la foto del usuario que ha subido la publicacion
		JLabel lblFotoUsuario = new JLabel();
		Image imagenUsuario = publicacion.getUsuario().getFotoPerfil().getImagen();
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
