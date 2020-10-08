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
        public Component getListCellRendererComponent(JList list, Object value, 
        		int index, boolean isSelected, boolean cellHasFocus
        ) {
			JLabel component = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			
			// TODO: Change combobox selected item background color to white.
			
			component.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			component.setPreferredSize(new Dimension(width, height));
			component.setText(String.valueOf(value));
			component.setFont(font);
			component.setBackground(Color.WHITE);

            return component;
        }
	}
}
