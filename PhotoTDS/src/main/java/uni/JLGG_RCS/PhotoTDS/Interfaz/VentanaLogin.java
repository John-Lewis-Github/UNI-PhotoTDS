package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VentanaLogin {

	private static final int ANCHURA = 400;
	private static final int ALTURA = 400;
	private static final int MARGEN_HORIZONTAL = 10;
	private static final int ANCHOPANELPRINCIPAL = 350;
	private static final int ALTOPANELPRINCIPAL = 250;
	private static final int ALTOPANELCREARCUENTA = 120;
	private JFrame frame;
	private JPanel contenedor;
	private JPanel panelPrincipal;
	private JPanel panelCrearCuenta;
	//private JPanel panelTitulo;
	private JPanel panelTexto;
	private JTextField textField;
	private JTextField txtEmail;
	private JTextField txtNombreCompleto;
	private JTextField txtNombreDeUsuario;
	private JPasswordField passwordField;
	private JLabel lblnoTienesUna;
	private JButton btnCrearCuenta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
		panelPrincipal = crearPanel(0);
		addTitulo(panelPrincipal);
		//addCamposTexto(panelPrincipal);
		panelCrearCuenta = crearPanel(1);
		addCrearCuenta(panelCrearCuenta);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// En esta ventana las dimensiones quedarán fijas
		frame.getContentPane().setMaximumSize(new Dimension(400, 400));
		frame.getContentPane().setMinimumSize(new Dimension(400, 400));
		frame.setResizable(false);
		
		contenedor = new JPanel();
		frame.setContentPane(contenedor);
		contenedor.setPreferredSize(new Dimension(ANCHURA, ALTURA));
		contenedor.setLayout(new BorderLayout());

		
		/*JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(300, 55));
		panel.add(panel_1);*/
		
		
		
		/*JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		*/
		
		
	}
	
	/**
	 * La ventana mantendrá una estructura vertical, por lo que se usará un panel
	 * central con un BoxLayout que organice los elementos verticalmente. Para añadir
	 * márgenes, se coloca este panel dentro de un contenedor que los posea.
	 */
	private JPanel crearPanel(int tipo) {
		JPanel panel = new JPanel();
		if(tipo==0) {
			panel.setPreferredSize(new Dimension(ANCHOPANELPRINCIPAL, ALTOPANELPRINCIPAL));
			frame.getContentPane().add(panel,  BorderLayout.NORTH);
		}
		else {
			frame.getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setPreferredSize(new Dimension(ANCHOPANELPRINCIPAL, ALTOPANELCREARCUENTA));
		}
			
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//JPanel panel = new JPanel();
		
		//frame.getContentPane().add(panel, BorderLayout.SOUTH);
		//panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		//contenedor.add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));

		//contenedor.add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));
		//frame.getContentPane().add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));
		return panel;
	}
	
	private void addTitulo(JPanel panel) {
		// Panel del título
		//panelTitulo = new JPanel();
		//contenedor.add(panelTitulo, BorderLayout.SOUTH);
		
		JLabel lblPhototds = new JLabel("PhotoTDS", JLabel.CENTER);
		//lblPhototds.setHorizontalTextPosition(SwingConstants.CENTER);
		//lblPhototds.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhototds.setPreferredSize(new Dimension(ANCHOPANELPRINCIPAL, 50));
		lblPhototds.setMinimumSize(new Dimension(ANCHOPANELPRINCIPAL, 50));
		lblPhototds.setMaximumSize(new Dimension(ANCHOPANELPRINCIPAL, 50));
		lblPhototds.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		lblPhototds.setAlignmentX(Component.CENTER_ALIGNMENT);
		//panelTitulo.setMaximumSize(new Dimension(450,30));
		panel.add(lblPhototds);
		
		panelTexto = new JPanel();
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		Dimension dimensionPanelTexto = new Dimension(400, 50);
		panelTexto.setPreferredSize(dimensionPanelTexto);
		panelTexto.setMaximumSize(dimensionPanelTexto);
		panelTexto.setMinimumSize(dimensionPanelTexto);
		panelTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
		textField = new JTextField();
		textField.setText("nombre de usuario o email");
		textField.setColumns(30);
		//textField.setFont(new Font("Arial", Font.PLAIN, 10));
		panelTexto.add(textField);
		
		
		passwordField = new JPasswordField();
		passwordField.setText("password");
		passwordField.setColumns(30);
		panelTexto.add(passwordField);
		panel.add(panelTexto);
		
		JButton btnNewButton = new JButton("Iniciar Sesión");
		btnNewButton.setPreferredSize(new Dimension(ANCHOPANELPRINCIPAL, 75));
		btnNewButton.setMinimumSize(new Dimension(ANCHOPANELPRINCIPAL, 75));
		btnNewButton.setMaximumSize(new Dimension(ANCHOPANELPRINCIPAL, 75));
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnNewButton);
	}
	
	/*private void addCamposTexto(JPanel panelPrincipal) {
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
		
		/*txtNombreCompleto = new JTextField();
		txtNombreCompleto.setText("Nombre completo");
		panelTexto.add(txtNombreCompleto);
		txtNombreCompleto.setColumns(30);*/
		
		/*txtNombreDeUsuario = new JTextField();
		txtNombreDeUsuario.setText("Nombre de usuario");
		panelTexto.add(txtNombreDeUsuario);
		txtNombreDeUsuario.setColumns(30);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setColumns(30);
		pwdPassword.setText("Password");
		panelTexto.add(pwdPassword);
	} */

	
	
	private void addCrearCuenta(JPanel panel) {
		JLabel lblnoTienesUna = new JLabel("¿No tienes una cuenta?", JLabel.CENTER);
		fixSize(lblnoTienesUna,ANCHOPANELPRINCIPAL,30);
		//lblnoTienesUna.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblnoTienesUna.setIconTextGap(10);
		lblnoTienesUna.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblnoTienesUna.setForeground(Color.RED);
		
		JButton btnCrearCuenta = new JButton("Crear una cuenta");
		btnCrearCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            VentanaRegistro registrarse = new VentanaRegistro();
	            registrarse.getframePrincipal().setLocation(frame.getLocationOnScreen());
	            frame.setVisible(false);
	            registrarse.getframePrincipal().setVisible(true);
			}
		});
		fixSize(btnCrearCuenta, ANCHOPANELPRINCIPAL, 40);
		//btnCrearCuenta.setPreferredSize(new Dimension(275, 27));
		btnCrearCuenta.setIconTextGap(10);
		btnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		//contenedor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(lblnoTienesUna);
		panel.add(btnCrearCuenta);
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}
}
