package UI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import Dynamic.Groups;
import Static.Database;
import Static.Palette;
import Types.Staff;
import UI.Pages.*;

@SuppressWarnings("serial")
public class Home extends JFrame {
	
	private static final Dimension defaultWindowSize = new Dimension(1280, 720);
	private static final Dimension minimumWindowSize = new Dimension(960, 540);

	private static final Font
		logoFont = new Font("Arial Black", Font.BOLD, 22),
		profileFont = new Font("Arial", Font.BOLD, 16),
		sideBarButtonFont = new Font("Arial", Font.BOLD, 16),
		sideBarSubButtonFont = new Font("Arial", Font.PLAIN, 14);
	
	public static Staff account;
	public static Content contentBody;
	public static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

	public Home(String username) {
		setTitle("Handal Cargo - Enterprise System");
		setSize(defaultWindowSize);
		setMinimumSize(minimumWindowSize);
		// setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		// Close database connection and free memory on close.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				promptExit();
			}
		});
		
		// Initialize global variables.
		Groups.initialize();
		Home.account = new Staff(username);
		
		Header header = new Header();
		SideBar sideBar = new SideBar();
		contentBody = new Content();
		
		JPanel notNavBar = new JPanel(new BorderLayout());
		notNavBar.add(sideBar, BorderLayout.WEST);
		notNavBar.add(contentBody, BorderLayout.CENTER);
		
		JPanel body = new JPanel(new BorderLayout());
		body.add(header, BorderLayout.NORTH);
		body.add(notNavBar, BorderLayout.CENTER);
		
		add(body);
		
		// Display the application.
		setVisible(true);
	}
	
	private void promptExit() {
		int exit = JOptionPane.showConfirmDialog(
			this, 
			"Log Out and Exit?", 
			"Close Application", 
			JOptionPane.YES_NO_OPTION
		);
		if (exit == JOptionPane.YES_OPTION) {
			dispose();
			Database.closeConnection();
			System.exit(0);
		}
	}
	
	class Header extends JPanel {
		
		private final int height = 50;
		private final int profileIconSize = 25;
		
		public Header() {
			setPreferredSize(new Dimension(0, height));
			setBackground(Palette.headerColor);
			setLayout(new BorderLayout());
			
			// Handal Cargo logo.
			JLabel logo = new JLabel("Handal Cargo");
			logo.setFont(logoFont);
			logo.setForeground(Color.WHITE);
			logo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			add(logo, BorderLayout.WEST);
			
			// Profile.
			add(new Profile(), BorderLayout.EAST);
		}
		
		class Profile extends JButton {
			
			public Profile() {
				
				// Display username.
				setText(account.username);
				setFont(profileFont);
				setForeground(Color.WHITE);
				
				// Display avatar icon.
				ImageIcon icon = new ImageIcon(this.getClass().getResource("/user.png"));
				Image scaledImage = icon.getImage().getScaledInstance(profileIconSize, profileIconSize, Image.SCALE_SMOOTH);
				setIcon(new ImageIcon(scaledImage));
				setIconTextGap(8);
				
				// Background color.
				setUI((ButtonUI) BasicButtonUI.createUI(this));
				setBackground(Palette.profileHoverColor);
				
				// Styling.
				setCursor(handCursor);
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
				
				// On click event.
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						contentBody.displayPage("UserProfile");
					}
				});
			}
		}
	}
	
	class SideBar extends JPanel {
		
		private final int 
			width = 260,
			buttonHeight = 40,
			buttonIconSize = 16,
			subButtonIconSize = 12;
		
		private Insets
			buttonPadding = new Insets(0, 20, 0, 0),
			subButtonPadding = new Insets(0, 30, 0, 0);
		
		private ArrayList<CategoryButton> buttons;
		
		public SideBar() {
			setBackground(Palette.sideBarColor);
			setPreferredSize(new Dimension(width, 0));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			// Subcategory names
			// note: these names must match their corresponding page class names for navigation to work.
			String
				shipping[] = {"Sea Freight", "Air Cargo", "Factor Entries", "Payment"},
				master[] = {"Customers", "Staff", "Accounts"},
				references[] = {"Something"},
				reports[] = {"Dashboard", "Payroll"},
				settings[] = {"Staff Groups", "Company Setup", "System Access", "System User", "Backup And Restore"};
			
			LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
			
			map.put("Shipping", shipping);
			map.put("Master", master);
			map.put("References", references);
			map.put("Reports", reports);
			map.put("Settings", settings);
						
			buttons = new ArrayList<>();
			map.forEach((categoryName, subcategories) -> {
				CategoryButton button = new CategoryButton(categoryName, subcategories);
				buttons.add(button);
				add(button);
			});
			add(new CategoryButton("Log Out and Exit", null));
		}
		
		class CategoryButton extends JPanel {
			
			private GridLayout layout;
			private Dimension size;
			private JToggleButton headerButton;
			private String[] subcategories;
			private JButton subButtons[];
			
			public CategoryButton(String text, String[] subcategories) {
				
				// Panel properties.
				setOpaque(false);
				setAlignmentX(Component.RIGHT_ALIGNMENT);
				
				this.subcategories = subcategories;
				
				layout = new GridLayout(1, 1);
				setLayout(layout);
				
				size = new Dimension(width, buttonHeight);
				setMinimumSize(size);
				setMaximumSize(size);
				
				// Button properties.
				headerButton = new JToggleButton();
				
				headerButton.setText(text);
				headerButton.setFont(sideBarButtonFont);
				headerButton.setForeground(Color.WHITE);
				
				// Button icon.
				try {
					String iconPath = String.format("/%s.png", text.toLowerCase().replaceAll("\\s+", ""));
					ImageIcon icon = new ImageIcon(this.getClass().getResource(iconPath));
					Image scaledIcon = icon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH);
					headerButton.setIcon(new ImageIcon(scaledIcon));
					headerButton.setIconTextGap(10);
				}
				catch(Exception e) {
					System.out.println(text + " icon not found.");
				}

				// Button color.
				headerButton.setUI((ButtonUI) BasicButtonUI.createUI(this));
				headerButton.setBackground(Palette.sideBarButtonHoverColor);
				
				headerButton.setHorizontalAlignment(SwingConstants.LEFT);
				headerButton.setMargin(buttonPadding);
				
				headerButton.setCursor(handCursor);
				headerButton.setContentAreaFilled(false);
				headerButton.setBorderPainted(false);
				headerButton.setFocusPainted(false);
				
				// Button hover effects.
				headerButton.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						headerButton.setContentAreaFilled(true);
					}
					
					public void mouseExited(MouseEvent e) {
						headerButton.setContentAreaFilled(false);
					}
				});
				
				// Initialize subcategory buttons.
				if (subcategories != null) {
					subButtons = new JButton[subcategories.length];
					int i = 0;
					for (String subcategory : subcategories) {
						JButton subButton = new JButton();
						
						subButton.setText(subcategory);
						subButton.setFont(sideBarSubButtonFont);
						subButton.setForeground(Color.WHITE);
						
						// SubButton icon.
						ImageIcon subIcon = new ImageIcon(this.getClass().getResource("/check.png"));
						Image scaledSubIcon = subIcon.getImage().getScaledInstance(subButtonIconSize, subButtonIconSize, Image.SCALE_SMOOTH);
						subButton.setIcon(new ImageIcon(scaledSubIcon));
						subButton.setIconTextGap(10);
						
						// SubButton color.
						subButton.setUI((ButtonUI) BasicButtonUI.createUI(CategoryButton.this));
						subButton.setBackground(Palette.sideBarSubButtonColor);
						
						subButton.setHorizontalAlignment(SwingConstants.LEFT);
						subButton.setMargin(subButtonPadding);
						
						subButton.setCursor(handCursor);
						subButton.setBorderPainted(false);
						subButton.setFocusPainted(false);
						
						// SubButton hover effects.
						subButton.addMouseListener(new MouseAdapter() {
							public void mouseEntered(MouseEvent e) {
								subButton.setBackground(Palette.sideBarSubButtonHoverColor);
							}
							
							public void mouseExited(MouseEvent e) {
								subButton.setBackground(Palette.sideBarSubButtonColor);
							}
						});
						
						// SubButton functionality.
						subButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// The class name of every page is the same but with no spaces.
								String pageClassName = subcategory.replaceAll("\\s+", "");
								contentBody.displayPage(pageClassName);
							}
						});
						
						subButtons[i] = subButton;
						i++;
					}
					
					// HeaderButton functionality.
					headerButton.addItemListener(new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent e) {
							if (headerButton.isSelected()) {
								buttons.forEach(button -> {
									if (button != CategoryButton.this) {
										button.deselect();
									}
								});
								expand();
							}
							else {
								shrink();
							}
							// Re-render the component.
							CategoryButton.this.revalidate();
							CategoryButton.this.repaint();
						}
					});
				}
				else {
					// Regular button click functionality.
					headerButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							promptExit();
						}
					});
				}
				
				add(headerButton);
			}
			
			private void expand() {
				// Expand size and number of rows.
				int items = 1 + subcategories.length;
				size.setSize(width, buttonHeight * items);
				layout.setRows(items);
				CategoryButton.this.setMaximumSize(size);
				CategoryButton.this.setLayout(layout);
				
				// Add each subcategory button.
				for (JButton subButton : subButtons) {
					CategoryButton.this.add(subButton);
				}
			}
			
			private void shrink() {
				// Shrink size and number of rows.
				size.setSize(width, buttonHeight);
				layout.setRows(1);
				CategoryButton.this.setMaximumSize(size);
				CategoryButton.this.setLayout(layout);
				
				// Remove each subcategory button.
				for (JButton subButton : subButtons) {
					CategoryButton.this.remove(subButton);
				}
			}
			
			// To be called externally.
			public void deselect() {
				headerButton.setSelected(false);
				shrink();
			}
		}
	}
	
	class Content extends JPanel {
		
		CardLayout layout;
		
		public Content() {
			layout = new CardLayout();
			setLayout(layout);
			
			// Initialize all the pages.
			JPanel pages[] = {
				new Dashboard(),	// First item is loaded first, hence will be the default page.
				new UserProfile(),
				new SeaFreight(),
				new AirCargo(),
				new Accounts()
			};
			
			// Load them all into the card layout.
			for (JPanel page : pages) {
				add(page, page.getClass().getSimpleName());
			}
		}
		
		public void displayPage(String pageClassName) {
			layout.show(this, pageClassName);
		}
	}
}
