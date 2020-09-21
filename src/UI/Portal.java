package UI;

import java.awt.event.*;
import javax.swing.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import Static.Database;
import Types.Account;

@SuppressWarnings("serial")
public class Portal extends JFrame {
	
	public static void main(String[] args) {
		// Initialize connection to database.
		Exception ok = Database.initialize();
		
		// Set the design of UI components.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// Launch the portal.
		new Portal(ok);
	}
	
	public Portal(Exception ok) {
		super("Handal Cargo - Login");
		setResizable(false);
		
		// Close database connection and free memory on close.
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						dispose();
						Database.closeConnection();
						System.exit(0);
					}
				});
		
		if (ok == null) {
			// If database ok, show login screen.
			setSize(380, 180);
			add(new Login());
		}
		else {
			// If not ok, show error log.
			setSize(460, 200);
			add(new InitializeError(ok));
		}
		
		// Display the application.
		setVisible(true);
	}
	
	class Login extends JPanel {
		public Login() {
			setLayout(null);
			
			// Username.
			JLabel usernameLabel = new JLabel("Username");
			usernameLabel.setBounds(30, 20, 80, 20);
			JTextField usernameField = new JTextField();
			usernameField.setBounds(130, 20, 200, 20);
			add(usernameLabel);
			add(usernameField);
			
			// Password.
			JLabel passwordLabel = new JLabel("Password");
			passwordLabel.setBounds(30, 50, 80, 20);
			JPasswordField passwordField = new JPasswordField();
			passwordField.setBounds(130, 50, 200, 20);
			add(passwordLabel);
			add(passwordField);

			// Login Button and functionality.
			JButton loginButton = new JButton("Login");
			loginButton.setBounds(80, 80, 100, 40);
			loginButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String username = usernameField.getText();
					String password = String.valueOf(passwordField.getPassword());
					
					// Authentication.
					String query = "SELECT EXISTS ( SELECT * FROM accounts WHERE username = ? AND password = ? )";
					ResultSet results = Database.query(query, statement -> {
						try {
							statement.setString(1, username);
							statement.setString(2, password);
						}
						catch(SQLException ex) {
							Database.printErrors(ex);
						}
					});
					try {
						results.next();
						if (results.getInt(1) == 1) {
							new Home(new Account(username));
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(
								Portal.this, 
								"Invalid Credentials.", 
								"Alert", 
								JOptionPane.WARNING_MESSAGE);
						}
					}
					catch(SQLException ex) {
						Database.printErrors(ex);
					}
				}
			});
			add(loginButton);
		}
	}
	
	class InitializeError extends JPanel {
		public InitializeError(Exception e) {
			setLayout(null);
			
			// Display error name.
			JLabel errorLabel = new JLabel("ERROR connecting to the database...");
			errorLabel.setBounds(30, 20, 400, 20);
			add(errorLabel);
			
			// Display stack trace.
			JTextArea errorArea = new JTextArea(e.toString());
			errorArea.setBounds(30, 50, 400, 100);
			errorArea.setLineWrap(true);
			errorArea.setWrapStyleWord(true);
			errorArea.setEditable(false);
			add(errorArea);
		}
	}
}
