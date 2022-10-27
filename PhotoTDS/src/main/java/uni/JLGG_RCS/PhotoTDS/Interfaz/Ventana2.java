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
import javax.swing.JButton;

public class Ventana2 {

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblPhototds = new JLabel("PhotoTDS");
		lblPhototds.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPhototds.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhototds.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		panel.add(lblPhototds);
		
		txtEmail = new JTextField();
		txtEmail.setText("Email");
		panel.add(txtEmail);
		txtEmail.setColumns(30);
		
		txtNombreCompleto = new JTextField();
		txtNombreCompleto.setText("Nombre completo");
		panel.add(txtNombreCompleto);
		txtNombreCompleto.setColumns(30);
		
		txtNombreDeUsuario = new JTextField();
		txtNombreDeUsuario.setText("Nombre de usuario");
		panel.add(txtNombreDeUsuario);
		txtNombreDeUsuario.setColumns(30);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setColumns(30);
		pwdPassword.setText("Password");
		panel.add(pwdPassword);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_1.setPreferredSize(new Dimension(200, 120));
		panel_1.setMinimumSize(new Dimension(100, 100));
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		panel_2.setName("Fecha de Nacimiento");
		panel_2.setPreferredSize(new Dimension(250, 30));
		panel_1.add(panel_2);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento");
		panel_2.add(lblFechaDeNacimiento);
		
		JDateChooser lblNewLabel = new JDateChooser();
		panel_2.add(lblNewLabel);
		lblNewLabel.setDateFormatString("dd/MM/yyyy");
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(250, 30));
		panel_1.add(panel_3);
		
		JLabel lblIncluirFotoDe = new JLabel("Incluir foto de usuario (opcional)");
		panel_3.add(lblIncluirFotoDe);
		
		JFileChooser lblTest = new JFileChooser("test");
		lblTest.setBorder(new TitledBorder(null, "+", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblTest.setDialogTitle("File");
		lblTest.setPreferredSize(new Dimension(20, 20));
		panel_3.add(lblTest);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(250, 30));
		panel_1.add(panel_4);
		
		JLabel lblIncluirPresentacinopcional = new JLabel("Incluir presentación (opcional)");
		panel_4.add(lblIncluirPresentacinopcional);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setText("...");
		panel_4.add(textArea);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		
		JButton btnOk = new JButton("OK");
		panel_5.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		panel_5.add(btnCancel);
	}
}
