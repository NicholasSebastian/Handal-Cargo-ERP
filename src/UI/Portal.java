package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import Static.Database;
import Static.Encryption;
import Static.Palette;
import UI.Components.ColoredButton;

@SuppressWarnings("serial")
public class Portal extends JFrame {
	
	public static void main(String[] args) {
		// Set the design of UI components.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// Initialize connection to database.
		Exception ok = Database.initialize();
		
		// Launch the portal.
		new Portal(ok);
	}
	
	public Portal(Exception ok) {
		super("Handal Cargo - Login");
		setResizable(false);
		setLocationRelativeTo(null);
		
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
			setSize(400, 300);
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
		
		private final int iconSize = 20;
		private final Font
			titleFont = new Font("Arial", Font.BOLD, 18),
			formFont = new Font("Arial", Font.PLAIN, 14),
			buttonFont = new Font("Arial", Font.BOLD, 14);
		
		public Login() {
			setLayout(new BorderLayout());
			
			// Initialize encryption.
			Encryption.initialize();
			
			// Top panel.
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			topPanel.setBackground(Palette.sideBarColor);
			topPanel.setPreferredSize(new Dimension(0, 50));
			add(topPanel, BorderLayout.NORTH);
			
			JLabel titleLabel = new JLabel("HANDAL CARGO");
			titleLabel.setFont(titleFont);
			titleLabel.setForeground(Color.WHITE);
			titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			topPanel.add(titleLabel, BorderLayout.WEST);
			
			// Content panel.
			JPanel contentPanel = new JPanel();
			contentPanel.setBackground(Color.WHITE);
			add(contentPanel, BorderLayout.CENTER);
			
			contentPanel.add(Box.createRigidArea(new Dimension(400, 30)));
			
			// Username.
			ImageIcon usernameIcon = new ImageIcon(this.getClass().getResource("/username.png"));
			Image scaledUsernameImage = usernameIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
			JLabel usernameLabel = new JLabel(new ImageIcon(scaledUsernameImage));
			JTextField usernameField = new JTextField();
			usernameLabel.setOpaque(true);
			usernameLabel.setBackground(Palette.gray);
			usernameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			usernameField.setColumns(25);
			usernameField.setFont(formFont);
			usernameField.setMargin(new Insets(6, 6, 6, 6));
			usernameField.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					usernameLabel.setBackground(Palette.blue);
				}

				@Override
				public void focusLost(FocusEvent e) {
					usernameLabel.setBackground(Palette.gray);
				}
			});
			contentPanel.add(usernameLabel);
			contentPanel.add(usernameField);
			
			contentPanel.add(Box.createRigidArea(new Dimension(400, 10)));
			
			// Password.
			ImageIcon passwordIcon = new ImageIcon(this.getClass().getResource("/password.png"));
			Image scaledPasswordImage = passwordIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
			JLabel passwordLabel = new JLabel(new ImageIcon(scaledPasswordImage));
			JPasswordField passwordField = new JPasswordField();
			passwordLabel.setOpaque(true);
			passwordLabel.setBackground(Palette.gray);
			passwordLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			passwordField.setColumns(25);
			passwordField.setFont(formFont);
			passwordField.setMargin(new Insets(6, 6, 6, 6));
			passwordField.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					passwordLabel.setBackground(Palette.blue);
				}
				@Override
				public void focusLost(FocusEvent e) {
					passwordLabel.setBackground(Palette.gray);
				}
			});
			contentPanel.add(passwordLabel);
			contentPanel.add(passwordField);
			
			contentPanel.add(Box.createRigidArea(new Dimension(400, 10)));

			// Login Button and functionality.
			JButton loginButton = new ColoredButton(
				"Login", Palette.blue, Palette.blueHover, new Dimension(100, 40), true, buttonFont, 
				e -> {
					String username = usernameField.getText();
					String password = String.valueOf(passwordField.getPassword());
					String encryptedPassword = Encryption.encrypt(password);
					
					// Authentication.
					String query = "SELECT EXISTS ( SELECT * FROM accounts WHERE username = ? AND password = ? )";
					ResultSet results = Database.query(query, statement -> {
						try {
							statement.setString(1, username);
							statement.setString(2, encryptedPassword);
						}
						catch(SQLException ex) {
							Database.printErrors(ex);
						}
					});
					try {
						results.next();
						if (results.getInt(1) == 1) {
							new Home(username);
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
				});
			Portal.this.getRootPane().setDefaultButton(loginButton);
			contentPanel.add(loginButton);
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
