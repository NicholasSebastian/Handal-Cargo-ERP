package UI.Components;

import java.util.function.Consumer;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TitleAndClose extends JPanel {
	
	private final Font 
		titleFont = new Font("Arial", Font.BOLD, 18);
	
	public TitleAndClose(String title, Consumer<ActionEvent> function) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(titleFont);
		add(titleLabel, BorderLayout.WEST);
		
		JButton closeButton = new IconButton("/close.png", function);
		add(closeButton, BorderLayout.EAST);
	}
}
