package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaPrincipal {

	private static final int ANCHURA = 600;
	private static final int ALTURA = 700;
	private static final int ANCHURA_TITULO = 600;
	private static final int ALTURA_MENU = 70;
	
	private JFrame framePrincipal;
	private JPanel contenedor;
	private JPanel panelPrincipal;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal v = new VentanaPrincipal();
					v.framePrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaPrincipal() {
		initialize();
		panelPrincipal = crearPanelPrincipal();
		
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.setBackground(Color.yellow);
		JPanel menuSuperior = crearMenu();
		panelPrincipal.add(menuSuperior);
	}

	private void initialize() {
		framePrincipal = new JFrame();
		framePrincipal.setSize(ANCHURA, ALTURA);
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor = new JPanel();
		framePrincipal.setContentPane(contenedor);
	}
	
	// Con este método se flexibiliza la creación del panel principal
	private JPanel crearPanelPrincipal() {
		// JPanel panelPrincipal = new JPanel();
		// contenedor.add(panelPrincipal);
		contenedor.setBackground(Color.blue);
		return contenedor;
	}
	
	private JPanel crearMenu() {
		JPanel menu = new JPanel();
		menu.setBackground(Color.white);
		menu.setLayout(new BoxLayout(menu, BoxLayout.X_AXIS));
		menu.setPreferredSize(new Dimension(ANCHURA, ALTURA_MENU));
		
		JLabel titulo = new JLabel("PhotoTDS");
		titulo.setBackground(Color.red);
		titulo.setHorizontalTextPosition(SwingConstants.CENTER);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Dialog", Font.ROMAN_BASELINE, 30));
		//fixSize(titulo, ALTURA_MENU, ANCHURA_TITULO);
		menu.add(titulo);
		
		return menu;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		comp.setMaximumSize(new Dimension(x, y));
		comp.setMinimumSize(new Dimension(x, y));
	}

}
