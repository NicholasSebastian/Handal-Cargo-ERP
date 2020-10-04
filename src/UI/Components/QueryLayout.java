package UI.Components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import Static.Palette;

@SuppressWarnings("serial")
public abstract class QueryLayout extends JPanel {
	
	private static final Dimension buttonSize = new Dimension(90, 90);
	private static final int tableRowHeight = 30;
	
	private static final Font 
		titleFont = new Font("Arial", Font.BOLD, 20),
		buttonFont = new Font("Arial", Font.BOLD, 16);
	
	private CardLayout content;
	private JPanel contentPanel;
	
	private JTable table = new CustomTable();
	
	private JPanel modifyView;
	private JPanel modifyPanel;
	
	private JLabel titleLabel = new JLabel();
	private JLabel addTitleLabel = new JLabel();
	private JLabel modifyTitleLabel = new JLabel();
	
	// Override to set functions.
	protected abstract TableModel setTable();
	protected abstract void searchFunction(String query);
	protected abstract JPanel setAddPanel();
	protected abstract JPanel setModifyPanel(Object selected);
	protected abstract void deleteFunction(Object selectedRowValue);
	
	protected void setTitles(String title, String addTitle, String modifyTitle) {
		titleLabel.setText(title);
		addTitleLabel.setText(addTitle);
		modifyTitleLabel.setText(modifyTitle);
	}
	
	protected void displayPage(String viewName) {
		content.show(contentPanel, viewName);
	}

	public QueryLayout() {
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout());
		
		// Title panel
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(titlePanel, BorderLayout.NORTH);
		
		titleLabel.setFont(titleFont);
		titlePanel.add(titleLabel);
		
		// White panel
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
		
		// 'Add page' and 'Modify page'
		JPanel addView = new JPanel();
		modifyView = new JPanel();
		addView.setOpaque(false);
		addView.setLayout(new BorderLayout());
		modifyView.setOpaque(false);
		modifyView.setLayout(new BorderLayout());
		
		addView.add(new TitleAndClose(addTitleLabel, 
			e -> displayPage("Overview")), BorderLayout.NORTH);
		modifyView.add(new TitleAndClose(modifyTitleLabel, 
			e -> displayPage("Overview")), BorderLayout.NORTH);
		
		// Generate static content and add.
		TableModel model = ((model = setTable()) != null) ? model : new DefaultTableModel();
		JPanel addPanel = (addPanel = setAddPanel()) != null ? addPanel : new JPanel();
		
		table.setModel(model);
		addView.add(addPanel, BorderLayout.CENTER);
		
		contentPanel.add(new Overview(), "Overview");
		contentPanel.add(addView, "Add");
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
			
			topPanel.add(Box.createHorizontalStrut(5));
			
			// Search button.
			JButton searchButton = new IconButton(
				"/search.png", e -> searchFunction(searchField.getText()));
			topPanel.add(searchButton);
			
			// TODO: ADD TABLE FILTERS
			
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new BorderLayout(0, 0));
			innerPanel.setOpaque(false);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getViewport().setBackground(Color.WHITE);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setViewportView(table);
			table.setRowHeight(tableRowHeight);
			
			JTableHeader header = table.getTableHeader();
			header.setOpaque(false);
			header.setBackground(Palette.headerColor);
			header.setForeground(Color.WHITE);
			
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
			
			rightPanel.add(Box.createVerticalStrut(10));
			
			// Modify button.
			JButton modifyButton = new ColoredButton(
				"Modify", Palette.yellow, Palette.yellowHover, 
				buttonSize, true, buttonFont, e -> {
					if (table.getSelectedRow() != -1) {
						// Remove old content.
						contentPanel.remove(modifyView);
						if (modifyPanel != null) modifyView.remove(modifyPanel);
						
						// Create new content.
						modifyPanel = 
							(modifyPanel = setModifyPanel(table.getValueAt(table.getSelectedRow(), 0))) != null ? 
							modifyPanel : new JPanel();
						modifyView.add(modifyPanel, BorderLayout.CENTER);
						
						// Add new content.
						contentPanel.add(modifyView, "Modify");
						displayPage("Modify");
					}
					else {
						JOptionPane.showMessageDialog(
							QueryLayout.this.getParent(), 
							"Select an entry to modify first!",
							"Modify Record",
							JOptionPane.ERROR_MESSAGE);
					}
				});
			rightPanel.add(modifyButton);
			
			rightPanel.add(Box.createVerticalStrut(10));
			
			// Delete button.
			JButton deleteButton = new ColoredButton(
				"Delete", Palette.red, Palette.redHover, 
				buttonSize, true, buttonFont, e -> {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						int confirm = JOptionPane.showConfirmDialog(
							QueryLayout.this.getParent(), 
							String.format(
								"Are you sure you want to delete %s's record?", 
								table.getValueAt(selectedRow, 0)),
							"Delete Record",
							JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							deleteFunction(table.getValueAt(selectedRow, 0));
							JOptionPane.showMessageDialog(QueryLayout.this.getParent(), "Entry deleted successfully.");
						}
					}
					else {
						JOptionPane.showMessageDialog(
							QueryLayout.this.getParent(), 
							"Select an entry to delete first!",
							"Delete Record",
							JOptionPane.ERROR_MESSAGE);
					}
				});
			rightPanel.add(deleteButton);
			
			innerPanel.add(rightPanel, BorderLayout.EAST);
			add(topPanel, BorderLayout.NORTH);
			add(innerPanel, BorderLayout.CENTER);
		}
	}
}
