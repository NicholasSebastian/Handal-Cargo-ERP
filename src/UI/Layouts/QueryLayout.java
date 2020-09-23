package UI.Layouts;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import Static.Palette;
import UI.Components.ColoredButton;
import UI.Components.SearchButton;

@SuppressWarnings("serial")
public abstract class QueryLayout extends JPanel {
	
	private final Dimension buttonSize = new Dimension(90, 90);
	private final Font 
		titleFont = new Font("Arial", Font.BOLD, 20),
		buttonFont = new Font("Arial", Font.BOLD, 16);
	
	private CardLayout content;
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	
	protected JLabel titleLabel = new JLabel();	// For setting the page title.
	
	protected abstract void setDatabaseView(JScrollPane scrollPane);	
	protected abstract void searchFunction(String query);
	protected abstract void setAddPage(JPanel addView);
	protected abstract void setModifyPage(JPanel modifyView);
	protected abstract void setDeletePage(JPanel deleteView);

	public QueryLayout() {
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(titlePanel, BorderLayout.NORTH);
		
		titleLabel.setFont(titleFont);
		titlePanel.add(titleLabel);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(
			new CompoundBorder(
				BorderFactory.createMatteBorder(2, 0, 0, 0, Palette.headerColor), 
				BorderFactory.createEmptyBorder(10, 10, 10, 10))
			);
		contentPanel.setBackground(Color.WHITE);
		content = new CardLayout(0, 0);
		contentPanel.setLayout(content);
		add(contentPanel, BorderLayout.CENTER);
		
		// Initialize all content.
		scrollPane =  new JScrollPane();
		JPanel addView = new JPanel();
		JPanel modifyView = new JPanel();
		JPanel deleteView = new JPanel();
		
		setDatabaseView(scrollPane);
		setAddPage(addView);
		setModifyPage(modifyView);
		setDeletePage(deleteView);
		
		// Load the content into the content panel.
		contentPanel.add(new Overview(), "Overview");	// Default view.
		contentPanel.add(addView, "Add");
		contentPanel.add(modifyView, "Modify");
		contentPanel.add(deleteView, "Delete");
	}
	
	public void displayPage(String viewName) {
		content.show(contentPanel, viewName);
	}
	
	class Overview extends JPanel {
		public Overview() {
			setOpaque(false);
			setLayout(new BorderLayout(0, 0));
			
			JPanel topPanel = new JPanel();
			topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
			topPanel.setOpaque(false);
			topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// Search field.
			JTextField searchField = new JTextField();
			searchField.setColumns(15);
			searchField.setFont(new Font("Arial", Font.PLAIN, 14));
			searchField.setMargin(new Insets(6, 6, 6, 6));
			topPanel.add(searchField);
			
			Component horizontalStrut = Box.createHorizontalStrut(5);
			topPanel.add(horizontalStrut);
			
			// Search button.
			JButton searchButton = new SearchButton(
				e -> searchFunction(searchField.getText()));
			topPanel.add(searchButton);
			
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new BorderLayout(0, 0));
			innerPanel.setOpaque(false);
			
			scrollPane.getViewport().setBackground(Color.WHITE);
			innerPanel.add(scrollPane, BorderLayout.CENTER);
			
			JPanel rightPanel = new JPanel();
			rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
			rightPanel.setOpaque(false);
			
			// Add button.
			JButton addButton = new ColoredButton(
				"Add", Palette.green, Palette.greenHover, 
				buttonSize, true, buttonFont, e -> displayPage("Add"));
			rightPanel.add(addButton);
			
			Component verticalStrut = Box.createVerticalStrut(10);
			rightPanel.add(verticalStrut);
			
			// Modify button.
			JButton modifyButton = new ColoredButton(
				"Modify", Palette.yellow, Palette.yellowHover, 
				buttonSize, true, buttonFont, e -> displayPage("Modify"));
			rightPanel.add(modifyButton);
			
			Component verticalStrut2 = Box.createVerticalStrut(10);
			rightPanel.add(verticalStrut2);
			
			// Delete button.
			JButton deleteButton = new ColoredButton(
				"Delete", Palette.red, Palette.redHover, 
				buttonSize, true, buttonFont, e -> displayPage("Delete"));
			rightPanel.add(deleteButton);
			
			innerPanel.add(rightPanel, BorderLayout.EAST);
			add(topPanel, BorderLayout.NORTH);
			add(innerPanel, BorderLayout.CENTER);
		}
	}
}
