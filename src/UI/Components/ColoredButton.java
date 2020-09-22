package UI.Components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import java.util.function.Consumer;

import UI.Home;

@SuppressWarnings("serial")
public class ColoredButton extends JButton {
	
	public ColoredButton(
			String text, Color backgroundColor, Color hoverColor, 
			Dimension size, Consumer<ActionEvent> function
	) {
		super(text);
		
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		// Background color
		setUI((ButtonUI) BasicButtonUI.createUI(this));
		setBackground(backgroundColor);
		
		setCursor(Home.handCursor);
		setContentAreaFilled(true);
		setBorderPainted(false);
		setFocusPainted(false);
		
		// Hover effects
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ColoredButton.this.setBackground(hoverColor);
			}
			
			public void mouseExited(MouseEvent e) {
				ColoredButton.this.setBackground(backgroundColor);
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
	
	public ColoredButton(
			String text, Color backgroundColor, Color hoverColor, 
			Dimension size, boolean whiteText, Consumer<ActionEvent> function
	) {
		this(text, backgroundColor, hoverColor, size, function);
		setForeground(whiteText ? Color.WHITE : Color.BLACK);
	}
	
	public ColoredButton(
			String text, Color backgroundColor, Color hoverColor, 
			Dimension size, Font font, Consumer<ActionEvent> function
	) {
		this(text, backgroundColor, hoverColor, size, function);
		setFont(font);
	}
	
	public ColoredButton(
			String text, Color backgroundColor, Color hoverColor, 
			Dimension size, boolean whiteText, Font font, 
			Consumer<ActionEvent> function
	) {
		this(text, backgroundColor, hoverColor, size, whiteText, function);
		setFont(font);
	}
}
