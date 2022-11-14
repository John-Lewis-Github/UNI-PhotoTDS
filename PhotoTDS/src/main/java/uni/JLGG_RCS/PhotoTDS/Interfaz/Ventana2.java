package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Ventana2 {

	private static final int MARGEN_HORIZONTAL = 10;
	private JFrame frame;
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
					Ventana2 window = new Ventana2();
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
	public Ventana2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 50, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * La ventana mantendrá una estructura vertical, por lo que se usará un panel
		 * central con un BoxLayout que organice los elementos verticalmente. Para añadir
		 * márgenes, se coloca este panel dentro de un contenedor que los posea.
		 */
		JPanel contenedor = new JPanel();
		frame.setContentPane(contenedor);
		contenedor.setPreferredSize(new Dimension(450, 600));
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.X_AXIS));
		contenedor.add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));
		JPanel panel = new JPanel();		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		contenedor.add(panel);
		contenedor.add(Box.createHorizontalStrut(MARGEN_HORIZONTAL));

		// Panel del título
		JPanel panelTitulo = new JPanel();
		panel.add(panelTitulo);
		
		JLabel lblPhototds = new JLabel("PhotoTDS");
		lblPhototds.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPhototds.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhototds.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		panelTitulo.setMaximumSize(new Dimension(450,30));
		panelTitulo.add(lblPhototds);
		
		
		JPanel panelTexto = new JPanel();
		panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
		// Truco para fijar solo la altura máxima
		panelTexto.setMaximumSize(new Dimension(4000, 100));
		panel.add(panelTexto);

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
		
		// Tras el texto se deja un margen vertical
		panelTexto.add(Box.createVerticalStrut(10));
		
		// El panel de fecha tendrá Layout de flujo
		JPanel panelFecha = new JPanel();
		panelFecha.setName("Fecha de Nacimiento");
		panelFecha.setPreferredSize(new Dimension(400, 40));
		panelFecha.setMaximumSize(new Dimension(400, 40));
		panel.add(panelFecha);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento");
		panelFecha.add(lblFechaDeNacimiento);
		
		JDateChooser lblNewLabel = new JDateChooser();
		panelFecha.add(lblNewLabel);
		lblNewLabel.setDateFormatString("dd/MM/yyyy");
		
		// Añadimos un pequeño margen
		panel.add(Box.createVerticalStrut(10));
		
		// Después se incluye un panel para elegir la foto
		JPanel panelFoto = new JPanel();
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
		
		// Añadimos un pequeño margen
		panel.add(Box.createVerticalStrut(10));
		
		JPanel panelPresentacion = new JPanel();
		panelPresentacion.setLayout(new BorderLayout());
		panel.add(panelPresentacion);
		
		JLabel lblIncluirPresentacionOpcional = new JLabel("Incluir presentación (opcional)");
		lblIncluirPresentacionOpcional.setHorizontalAlignment(SwingConstants.CENTER);
		panelPresentacion.add(lblIncluirPresentacionOpcional, BorderLayout.NORTH);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setText("...");
		panelPresentacion.add(textArea, BorderLayout.CENTER);
		
		JPanel panelBotones= new JPanel();
		panel.add(panelBotones);
		
		JButton btnOk = new JButton("OK");
		panelBotones.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		panelBotones.add(btnCancel);
	}
}
