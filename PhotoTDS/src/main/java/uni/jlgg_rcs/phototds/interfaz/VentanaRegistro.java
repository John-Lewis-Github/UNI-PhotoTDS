package uni.jlgg_rcs.phototds.interfaz;

import java.awt.EventQueue;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import uni.jlgg_rcs.phototds.controlador.Controlador;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

public class VentanaRegistro extends JFrame {

	private static final int ANCHURA = 450;
	private static final int ALTURA = 400;
	private static final int MARGEN_HORIZONTAL = 10;
	private static final int ANCHO_CHOOSER = 500;
	private static final int ALTO_CHOOSER = 400;
	
	private JDialog framePrincipal;
	private JPanel contenedor;
	private JPanel panelPrincipal;
	private JPanel panelTitulo;
	private JPanel panelTexto;
	private JPanel panelFecha;
	private JPanel panelFoto;
	private JPanel panelPresentacion;
	private JPanel panelBotones;
	private JTextField txtEmail;
	private JTextField txtNombreCompleto;
	private JTextField txtNombreDeUsuario;
	private JPasswordField pwdPassword;
	
	private String fotoPerfilPath;
	private JDateChooser dateChooser;
	private String presentacion = "";
	private DialogoPresentacion dialogo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro window = new VentanaRegistro();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Pone la ventana de registro visible o la oculta
	 * @param b true o false dependiendo de si se quiere mostrar o esconder la ventana
	 */
	public void setVisible(boolean b) {
		framePrincipal.setVisible(b);
	}
	
	public void setLocationRelativeTo(Component c) {
		framePrincipal.setLocationRelativeTo(c);
	}
	
	/**
	 * Create the application.
	 */
	public VentanaRegistro() {
		initialize();

		panelPrincipal = crearPanelPrincipal();

		addTitulo(panelPrincipal);
		
		addCamposTexto(panelPrincipal);
		panelTexto.add(Box.createVerticalStrut(10));
		
		addPanelFecha(panelPrincipal);
		panelPrincipal.add(Box.createVerticalStrut(10));
		
		addPanelFoto(panelPrincipal);
		panelPrincipal.add(Box.createVerticalStrut(10));
		
		addPanelPresentacion(panelPrincipal);
		
		addBotones(panelPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePrincipal = new JDialog(this);
		framePrincipal.setBounds(400, 50, ANCHURA, ALTURA);
		framePrincipal.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// framePrincipal.setModal(true);
		
		contenedor = new JPanel();
		framePrincipal.setContentPane(contenedor);
		contenedor.setPreferredSize(new Dimension(ANCHURA, ALTURA));
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.X_AXIS));
	}
	
	/**
	 * La ventana mantendrá una estructura vertical, por lo que se usará un panel
	 * central con un BoxLayout que organice los elementos verticalmente. Para añadir
	 * márgenes, se coloca este panel dentro de un contenedor que los posea.
	 */
	private JPanel crearPanelPrincipal() {
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));


		contenedor.add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));
		contenedor.add(panelPrincipal);
		contenedor.add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));
		
		return panelPrincipal;
	}
	

	private void addTitulo(JPanel panelPrincipal) {
		// Panel del título
		panelTitulo = new JPanel();
		panelPrincipal.add(panelTitulo);
		
		JLabel lblPhototds = new JLabel("PhotoTDS");
		lblPhototds.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPhototds.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhototds.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		panelTitulo.setMaximumSize(new Dimension(450,30));
		panelTitulo.add(lblPhototds);
	}

	private void addCamposTexto(JPanel panelPrincipal) {
		panelTexto = new JPanel();
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		// Truco para fijar solo la altura máxima
		panelTexto.setMaximumSize(new Dimension(4000, 100));
		panelPrincipal.add(panelTexto);

		// Cada campo de texto se incluye en el panel de texto
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		panelTexto.add(txtEmail);
		txtEmail.setColumns(30);
		
		txtNombreCompleto = new JTextField();
		txtNombreCompleto.setText("Nombre completo");
		panelTexto.add(txtNombreCompleto);
		txtNombreCompleto.setColumns(30);
		
		txtNombreDeUsuario = new JTextField();
		txtNombreDeUsuario.setText("Nombre de usuario");
		panelTexto.add(txtNombreDeUsuario);
		txtNombreDeUsuario.setColumns(30);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setColumns(30);
		pwdPassword.setText("Password");
		panelTexto.add(pwdPassword);
	}
	
	private void addPanelFecha(JPanel panel) {
		// El panel de fecha tendrá Layout de flujo
		panelFecha = new JPanel();
		panelFecha.setName("Fecha de Nacimiento");
		panelFecha.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		panel.add(panelFecha);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento");
		panelFecha.add(lblFechaDeNacimiento);
		
		dateChooser = new JDateChooser();
		panelFecha.add(dateChooser);
		dateChooser.setDateFormatString("dd/MM/yyyy");
	}

	private void addPanelFoto(JPanel panel) {
		panelFoto = new JPanel();
		panelFoto.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		panel.add(panelFoto);
		
		JLabel lblIncluirFotoDe = new JLabel("Incluir foto de usuario (opcional)");
		panelFoto.add(lblIncluirFotoDe, BorderLayout.NORTH);
		
		JButton botonFoto = new JButton("+");
		botonFoto.addActionListener(e -> {			
			JFileChooser chooser = new JFileChooser();
			chooser.setBorder(new TitledBorder(null, "+", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			chooser.setDialogTitle("File");
			chooser.setPreferredSize(new Dimension(ANCHO_CHOOSER, ALTO_CHOOSER));
			int resultado = chooser.showOpenDialog(panelFoto);
			if(resultado == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				fotoPerfilPath = file.getAbsolutePath();
			}
		});
		
		panelFoto.add(botonFoto);	
	}


	private void addPanelPresentacion(JPanel panel) {
		panelPresentacion = new JPanel();
		panelPresentacion.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		panel.add(panelPresentacion);
		
		JLabel lblIncluirPresentacionOpcional = new JLabel("Incluir presentación (opcional)");
		panelPresentacion.add(lblIncluirPresentacionOpcional);
		
		JButton botonPresentacion = new JButton("...");
		botonPresentacion.addActionListener(e -> {
			dialogo = new DialogoPresentacion();
			dialogo.setLocationRelativeTo(botonPresentacion);
			dialogo.setVisible(true);
		});
		
		panelPresentacion.add(botonPresentacion);
	}
	
	
	private void addBotones(JPanel panel) {
		panelBotones= new JPanel();
		panel.add(panelBotones);
		
		JLabel usuarioYaRegistrado = new JLabel("Ese usuario ya existe");
		usuarioYaRegistrado.setFont(new Font("Dialog", Font.PLAIN, 15));
		usuarioYaRegistrado.setForeground(Color.RED);
		usuarioYaRegistrado.setAlignmentX(Component.CENTER_ALIGNMENT);
		usuarioYaRegistrado.setVisible(false);
		
		panel.add(usuarioYaRegistrado);
		
		JButton btnOk = new JButton("OK");
		
		panelBotones.add(btnOk);
		btnOk.addActionListener(e -> {
			String nombreCompleto = this.txtNombreCompleto.getText();
			String nombreUsuario = this.txtNombreDeUsuario.getText();
			String email = this.txtEmail.getText();
			String password = this.pwdPassword.getText();
			
			Date fecha = dateChooser.getDate();
			
			String presentacion = dialogo.getPresentacion();
			
			boolean registrado = Controlador.INSTANCE.registrarUsuario(nombreCompleto, nombreUsuario, fecha, email, password, fotoPerfilPath, presentacion);

			if (registrado) {
				framePrincipal.dispose();
			} else {
				usuarioYaRegistrado.setVisible(true);
			}
			
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			framePrincipal.dispose();
		});
		panelBotones.add(btnCancel);
	}
}
