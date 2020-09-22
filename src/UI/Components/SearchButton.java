package UI.Components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import Static.Palette;
import UI.Home;

@SuppressWarnings("serial")
public class SearchButton extends JButton {
	
	private static final Dimension size = new Dimension(30, 30);
	private static final int iconSize = 15;
	
	public SearchButton(Consumer<ActionEvent> function) {
		
		// Set icon
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/search.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(scaledIcon));
		
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		// Background color
		setUI((ButtonUI) BasicButtonUI.createUI(this));
		setBackground(Palette.blue);
		
		setCursor(Home.handCursor);
		setContentAreaFilled(true);
		setBorderPainted(false);
		setFocusPainted(false);
		
		// Hover effects
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				SearchButton.this.setBackground(Palette.blueHover);
			}
			
			public void mouseExited(MouseEvent e) {
				SearchButton.this.setBackground(Palette.blue);
			}
		});
		
		// Functionality
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function.accept(e);
			}
		});
	}
}
