package uni.JLGG_RCS.PhotoTDS.Interfaz;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class EntradaPublicacionListRenderer extends DefaultListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList<?> list, 
						Object value, int index, boolean isSelected, 
						boolean cellHasFocus) {
		if (value!=null && value instanceof EntradaPublicacion) {
			EntradaPublicacion ele=(EntradaPublicacion) value;
			if (isSelected) {
				ele.setBackground(Color.PINK);
	               //ele.setBackground(list.getSelectionBackground());
	               //ele.setForeground(list.getSelectionForeground());
	           } else {
	               ele.setBackground(list.getBackground());
	               ele.setForeground(list.getForeground());
	           }
			return ele;
		}
		else return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
