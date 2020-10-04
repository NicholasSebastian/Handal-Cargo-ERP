package UI.Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class CustomComboBox extends JComboBox {
	
	private final Font font = new Font("Arial", Font.PLAIN, 12);
	private int
		width = 100,
		height = 25;
	
	public CustomComboBox(Object[] items) {
		super(items);
		setRenderer(new CustomRenderer());
	}
	
	public CustomComboBox(Object[] items, int width, int height) {
		super(items);
		this.width = width;
		this.height = height;
		setRenderer(new CustomRenderer());
	}
	
	class CustomRenderer extends DefaultListCellRenderer {
		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus
        ) {
			JComponent component = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			
			// TODO: Change combobox background color to white.
			// TODO: Change the currently selected option font size to be 12.
			
			component.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			((JLabel) component).setPreferredSize(new Dimension(width, height));
			((JLabel) component).setText(String.valueOf(value));
			((JLabel) component).setFont(font);

            return component;
        }
	}
}
