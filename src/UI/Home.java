package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import Static.Database;
import Static.State;

@SuppressWarnings("serial")
public class Home extends JFrame {

	public Home() {
		setTitle("Handal Cargo - Enterprise System");
		setSize(1280, 720);
		// setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
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
		
		Header header = new Header();
		SideBar sideBar = new SideBar();
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		
		JPanel notNavBar = new JPanel(new BorderLayout());
		notNavBar.add(sideBar, BorderLayout.WEST);
		notNavBar.add(contentPane, BorderLayout.CENTER);
		
		JPanel body = new JPanel(new BorderLayout());
		body.add(header, BorderLayout.NORTH);
		body.add(notNavBar, BorderLayout.CENTER);
		
		add(body);
		
		// Display the application.
		setVisible(true);
	}
	
	class Header extends JPanel {
		public Header() {
			setPreferredSize(new Dimension(0, 60));
			setBackground(new Color(0, 119, 182));
			setLayout(new BorderLayout());
			
			// Handal Cargo logo.
			JLabel logo = new JLabel("Handal Cargo");
			logo.setFont(new Font("Arial", Font.BOLD, 25));
			add(logo, BorderLayout.WEST);
			
			// Profile.
			Profile profile = new Profile();
			add(profile, BorderLayout.EAST);
		}
		
		class Profile extends JToggleButton {
			public Profile() {
				
				// Display username.
				setText(State.account.username);
				setFont(new Font("Arial", Font.BOLD, 16));
				setForeground(Color.WHITE);
				
				// Display avatar icon.
				ImageIcon icon = new ImageIcon(this.getClass().getResource("/user.png"));
				Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_FAST);
				setIcon(new ImageIcon(scaledImage));
				
				// For some reason I have to do this just to change the background color.
				this.setUI((ButtonUI) BasicButtonUI.createUI(this));
				setBackground(new Color(35, 110, 165));
				
				// Styling.
				setIconTextGap(10);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
				
				// Hover effects.
				addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						Profile.this.setContentAreaFilled(true);
					}
					
					public void mouseExited(MouseEvent e) {
						Profile.this.setContentAreaFilled(false);
					}
				});
				
				JPopupMenu popupMenu = new JPopupMenu();
				popupMenu.add(new JMenuItem("View Profile"));
				popupMenu.add(new JMenuItem("Log Off and Exit"));
				
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JToggleButton toggle = Profile.this;
						if (toggle.isSelected()) {
							popupMenu.show(toggle, 0, toggle.getBounds().height);	// Temporary
						}
						else {
							popupMenu.setVisible(false);
						}
					}
				});
			}
		}
	}
	
	class SideBar extends JPanel {
		public SideBar() {
			setPreferredSize(new Dimension(260, 0));
			setLayout(new BorderLayout());
			
			// List content.
			DefaultListModel<String> listModel = new DefaultListModel<>();
			listModel.addElement("Shipping");
			listModel.addElement("Master");
			listModel.addElement("References");
			listModel.addElement("Reports");
			listModel.addElement("Utilities");
			
			// List component.
			JList<String> list = new JList<>(listModel);
			list.setFont(new Font("Arial", Font.BOLD, 16));
			list.setFixedCellHeight(35);
			
			list.setBackground(new Color(32, 43, 50));
			list.setSelectionBackground(new Color(41, 58, 65));
			list.setForeground(Color.WHITE);
			
			// Remove dotted border around selection.
			UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
			
			// Center list items.
			DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			 
			add(list);
		}
	}
}
