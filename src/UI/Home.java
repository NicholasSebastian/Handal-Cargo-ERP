package UI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import Static.Database;
import Types.Account;
import UI.Pages.*;

@SuppressWarnings("serial")
public class Home extends JFrame {
	
	private Dimension defaultWindowSize = new Dimension(1280, 720);
	private Dimension minimumWindowSize = new Dimension(880, 520);
	
	private Color
		headerColor = new Color(0, 119, 182),
		profileHoverColor = new Color(35, 110, 165),
		sideBarColor = new Color(27, 38, 42),
		sideBarButtonHoverColor = new Color(42, 51, 57),
		sideBarSubButtonColor = new Color(42, 60, 71),
		sideBarSubButtonHoverColor = new Color(52, 70, 81);

	private Font
		logoFont = new Font("Arial", Font.BOLD, 25),
		profileFont = new Font("Arial", Font.BOLD, 16),
		sideBarButtonFont = new Font("Arial", Font.BOLD, 16),
		sideBarSubButtonFont = new Font("Arial", Font.PLAIN, 14);
	
	public Account account;
	public Content contentBody;
	public Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

	public Home(Account account) {
		setTitle("Handal Cargo - Enterprise System");
		setSize(defaultWindowSize);
		setMinimumSize(minimumWindowSize);
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
		
		this.account = account;
		
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
	
	class Header extends JPanel {
		
		private final int height = 60;
		private final int profileIconSize = 30;
		
		public Header() {
			setPreferredSize(new Dimension(0, height));
			setBackground(headerColor);
			setLayout(new BorderLayout());
			
			// Handal Cargo logo.
			JLabel logo = new JLabel("Handal Cargo");
			logo.setFont(logoFont);
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
				
				// Background color.
				setUI((ButtonUI) BasicButtonUI.createUI(this));
				setBackground(profileHoverColor);
				
				// Styling.
				setIconTextGap(10);
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
		
		private final int width = 260;
		private final int buttonHeight = 40;
		
		private ArrayList<CategoryButton> buttons;
		
		public SideBar() {
			setBackground(sideBarColor);
			setPreferredSize(new Dimension(width, 0));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			// Subcategory names
			// note: these names must match their corresponding page class names for navigation to work.
			String
				shipping[] = {"Sea Freight", "Air Cargo,", "Factor Entries", "Payment"},
				master[] = {"Customers", "Staff", "Accounts"},
				references[] = {"I dont know"},
				reports[] = {"Dashboard", "I also dont know"},
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

				// Button color.
				headerButton.setUI((ButtonUI) BasicButtonUI.createUI(this));
				headerButton.setBackground(sideBarButtonHoverColor);
				
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
				subButtons = new JButton[subcategories.length];
				int i = 0;
				for (String subcategory : subcategories) {
					JButton subButton = new JButton();
					
					subButton.setText(subcategory);
					subButton.setFont(sideBarSubButtonFont);
					subButton.setForeground(Color.WHITE);
					
					// SubButton color.
					subButton.setUI((ButtonUI) BasicButtonUI.createUI(CategoryButton.this));
					subButton.setBackground(sideBarSubButtonColor);
					
					subButton.setCursor(handCursor);
					subButton.setBorderPainted(false);
					subButton.setFocusPainted(false);
					
					// SubButton hover effects.
					subButton.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent e) {
							subButton.setBackground(sideBarSubButtonHoverColor);
						}
						
						public void mouseExited(MouseEvent e) {
							subButton.setBackground(sideBarSubButtonColor);
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
				new Accounts(),
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
