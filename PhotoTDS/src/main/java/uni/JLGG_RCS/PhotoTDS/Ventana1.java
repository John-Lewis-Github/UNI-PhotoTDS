package uni.JLGG_RCS.PhotoTDS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Ventana1 {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana1 window = new Ventana1();
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
	public Ventana1() {
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
		panel.setPreferredSize(new Dimension(75, 75));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(300, 55));
		panel.add(panel_1);
		
		JLabel lblnoTienesUna = new JLabel("¿No tienes una cuenta?");
		lblnoTienesUna.setHorizontalTextPosition(SwingConstants.CENTER);
		lblnoTienesUna.setHorizontalAlignment(SwingConstants.CENTER);
		lblnoTienesUna.setIconTextGap(10);
		lblnoTienesUna.setForeground(Color.RED);
		
		JButton btnCrearCuenta = new JButton("Crear una cuenta");
		btnCrearCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCrearCuenta.setPreferredSize(new Dimension(275, 27));
		btnCrearCuenta.setIconTextGap(10);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_1.add(lblnoTienesUna);
		panel_1.add(btnCrearCuenta);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblPhototds = new JLabel("PhotoTDS");
		lblPhototds.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPhototds.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhototds.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 30));
		panel_2.add(lblPhototds);
		
		textField = new JTextField();
		textField.setName("nombre de usuario o email");
		panel_2.add(textField);
		textField.setColumns(30);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(30);
		panel_2.add(passwordField);
		
		JButton btnNewButton = new JButton("Inciar Sesión");
		btnNewButton.setPreferredSize(new Dimension(300, 75));
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setForeground(Color.WHITE);
		panel_2.add(btnNewButton);
	}
}
