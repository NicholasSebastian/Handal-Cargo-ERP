import java.awt.event.*;
import javax.swing.*;

// Good luck ifat :)))

public class UserInterface extends JFrame {
	
	public UserInterface() {
		super("Handal Cargo - Integrated System");
		setSize(800, 500);
		
		// Close database connection and free memory on close.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Database.closeConnection();
				dispose();
				System.exit(0);
			}
		});
	}
}
