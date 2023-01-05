package uni.jlgg_rcs.phototds.interfaz;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import uni.jlgg_rcs.phototds.controlador.Controlador;
import uni.jlgg_rcs.phototds.dominio.Foto;

public class FotoListRenderer extends DefaultListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList<?> list, 
						Object value, int index, boolean isSelected, 
						boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (c instanceof JLabel) {
			JLabel lbl = (JLabel) c;
			Foto f =(Foto) value;
			lbl.setIcon(new ImageIcon(f.getImagen()));
			if (isSelected) {
				VentanaVerFoto verFoto = new VentanaVerFoto(f);
				verFoto.setVisible(true);
	        } 
		}
		 return c;
	}
}