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
		panelPrincipal = crearPanelPrincipal();
		panelCrearCuenta = crearPanelCrearCuenta();
		addTitulo(panelPrincipal);
		//addCamposTexto(panelPrincipal);
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
	private JPanel crearPanelPrincipal() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(ANCHOPANELPRINCIPAL, ALTOPANELPRINCIPAL));
		frame.getContentPane().add(panel,  BorderLayout.NORTH);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// Panel del titulo
		
		JLabel lblPhototds = new JLabel("PhotoTDS", JLabel.CENTER);
		fixSize(lblPhototds, ANCHOPANELPRINCIPAL, 50);
		lblPhototds.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		lblPhototds.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblPhototds);
				
		panelTexto = new JPanel();
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		fixSize(panelTexto, 400, 50);
		panelTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
		textField = new JTextField();
		textField.setText("nombre de usuario o email");
		textField.setColumns(30);
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
		
		return panel;
	}
	
	private void addTitulo(JPanel panel) {
		
	}
	
	
	private JPanel crearPanelCrearCuenta() {
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(ANCHOPANELPRINCIPAL, ALTOPANELCREARCUENTA));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
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
	            registrarse.setLocationRelativeTo(btnCrearCuenta);
	            registrarse.setVisible(true);
			}
		});
		fixSize(btnCrearCuenta, ANCHOPANELPRINCIPAL, 40);
		//btnCrearCuenta.setPreferredSize(new Dimension(275, 27));
		btnCrearCuenta.setIconTextGap(10);
		btnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		//contenedor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(lblnoTienesUna);
		panel.add(btnCrearCuenta);
		
		return panel;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}
}
