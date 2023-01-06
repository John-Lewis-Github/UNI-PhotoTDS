package uni.jlgg_rcs.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;

import uni.jlgg_rcs.phototds.controlador.Controlador;
import uni.jlgg_rcs.phototds.dominio.Foto;

public class FotoListRenderer extends DefaultListCellRenderer{

	
	private Color background = new Color(0, 100, 255, 15);
	private Color defaultBackground = (Color) UIManager.get("List.background");
	private int ancho = -1;
	private int alto = -1;
	private boolean customSize = false;
	
	public FotoListRenderer(){
		super();
	}

	
	public FotoListRenderer(int ancho, int alto){
		super();
		this.ancho = ancho;
		this.alto = alto;
		customSize = true;
	}



	@Override
	public Component getListCellRendererComponent(JList<?> list, 
						Object value, int index, boolean isSelected, 
						boolean cellHasFocus) {
		
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		if (c instanceof JLabel) {
			JLabel label = (JLabel) c;
			label.setText("");
			Image imagen = (Image) value;
			if(customSize) imagen = imagen.getScaledInstance(ancho, alto, 0);
			label.setIcon(new ImageIcon(imagen));
			label.setVisible(true);
			if (!isSelected)
				label.setBackground(index % 2 == 0 ? background : defaultBackground);
		}
		return c;
	}
}