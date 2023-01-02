package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class VentanaRegistro {

	private static final int ANCHURA = 450;
	private static final int ALTURA = 600;
	private static final int MARGEN_HORIZONTAL = 10;
	private JFrame framePrincipal;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro window = new VentanaRegistro();
					window.framePrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JFrame getframePrincipal() {
		return framePrincipal;
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
		framePrincipal = new JFrame();
		framePrincipal.setBounds(400, 50, ANCHURA, ALTURA);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		JDateChooser lblNewLabel = new JDateChooser();
		panelFecha.add(lblNewLabel);
		lblNewLabel.setDateFormatString("dd/MM/yyyy");
	}

	private void addPanelFoto(JPanel panel) {
		panelFoto = new JPanel();
		panelFoto.setLayout(new BorderLayout());
		panel.add(panelFoto);
		
		JLabel lblIncluirFotoDe = new JLabel("Incluir foto de usuario (opcional)");
		lblIncluirFotoDe.setHorizontalAlignment(SwingConstants.CENTER);
		panelFoto.add(lblIncluirFotoDe, BorderLayout.NORTH);
		
		JFileChooser lblTest = new JFileChooser("test");
		lblTest.setBorder(new TitledBorder(null, "+", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblTest.setDialogTitle("File");
		lblTest.setPreferredSize(new Dimension(400, 250));
		panelFoto.add(lblTest);
	}


	private void addPanelPresentacion(JPanel panel) {
		panelPresentacion = new JPanel();
		panelPresentacion.setLayout(new BorderLayout());
		panel.add(panelPresentacion);
		
		JLabel lblIncluirPresentacionOpcional = new JLabel("Incluir presentación (opcional)");
		lblIncluirPresentacionOpcional.setHorizontalAlignment(SwingConstants.CENTER);
		panelPresentacion.add(lblIncluirPresentacionOpcional, BorderLayout.NORTH);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setText("...");
		panelPresentacion.add(textArea, BorderLayout.CENTER);
	}
	
	
	private void addBotones(JPanel panel) {
		panelBotones= new JPanel();
		panel.add(panelBotones);
		
		JButton btnOk = new JButton("OK");
		
		panelBotones.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				framePrincipal.dispose();
				System.exit(0);
			}
		});
		panelBotones.add(btnCancel);
	}
}
