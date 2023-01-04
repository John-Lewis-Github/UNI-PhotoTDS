package uni.jlgg_rcs.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class DialogoPresentacion extends JDialog {
	
	private static final long serialVersionUID = 2784540375436754454L;
	
	private static final int ANCHO_PRESENTACION = 500;
	private static final int ALTO_PRESENTACION = 300;

	private static final int MARGEN = 10;
	private static final int TAMANO_FUENTE = 15;

	private static final int ANCHO_CENTRAL = ANCHO_PRESENTACION - 2*MARGEN;
	private static final int ALTO_CENTRAL = ALTO_PRESENTACION;
	
	private static final int ANCHO_BOTONES = 100;
	private static final int ALTO_BOTONES = 20;

	
	private String textoPresentacion;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DialogoPresentacion window = new DialogoPresentacion();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DialogoPresentacion() {
		this.setVisible(true);
		this.setSize(new Dimension(ANCHO_PRESENTACION, ALTO_PRESENTACION));
		// this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel panelDialogo = new JPanel();
		this.setContentPane(panelDialogo);

		panelDialogo.setLayout(new BoxLayout(panelDialogo, BoxLayout.X_AXIS));
		fixSize(panelDialogo, ANCHO_PRESENTACION, ALTO_PRESENTACION);
		
		panelDialogo.add(Box.createHorizontalStrut(MARGEN));
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		fixSize(panelDialogo, ANCHO_CENTRAL, ALTO_PRESENTACION);
		
		panelDialogo.add(panelCentral);
		panelDialogo.add(Box.createHorizontalStrut(MARGEN));
		
		panelCentral.add(Box.createVerticalStrut(MARGEN));
		
		JLabel indicacion = new JLabel("Escribe tu presentacion (maximo 200 caracteres): ");
		indicacion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, TAMANO_FUENTE));
		panelCentral.add(indicacion);
		
		panelCentral.add(Box.createVerticalStrut(MARGEN));
				
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		textArea.setText("...");
		panelCentral.add(textArea);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		panelCentral.add(panelBotones);
		
		JButton botonOk = new JButton("Ok");
		botonOk.setAlignmentX(Component.RIGHT_ALIGNMENT);
		botonOk.addActionListener(e -> {
			textoPresentacion = textArea.getText();
			this.dispose();
		});
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setAlignmentX(Component.RIGHT_ALIGNMENT);
		botonVolver.addActionListener(e2 -> {
			textoPresentacion = "";
			this.dispose();
		});
		
		panelBotones.add(botonOk);
		panelBotones.add(botonVolver);
		
		panelCentral.add(Box.createVerticalStrut(MARGEN));
	}
	
	public String getPresentacion() {
		return textoPresentacion;
	}
	
	private void fixSize(JComponent comp, int x, int y) {
		Dimension size = new Dimension(x,y);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setPreferredSize(size);
	}
}
