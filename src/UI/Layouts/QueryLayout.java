package UI.Layouts;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import Static.Palette;
import UI.Components.ColoredButton;
import UI.Components.IconButton;

@SuppressWarnings("serial")
public abstract class QueryLayout extends JPanel {
	
	private static final Dimension buttonSize = new Dimension(90, 90);
	private static final int tableRowHeight = 30;
	
	private static final Font 
		titleFont = new Font("Arial", Font.BOLD, 20),
		buttonFont = new Font("Arial", Font.BOLD, 16);
	
	private CardLayout content;
	private JPanel contentPanel;
	private JTable table;
	
	// Override to set the page title.
	protected JLabel titleLabel = new JLabel();
	
	// Override to set content.
	protected abstract void setDatabaseView(JTable table);	
	protected abstract void searchFunction(String query);
	protected abstract void setAddPage(JPanel addView);
	protected abstract void setModifyPage(JPanel modifyView);
	protected abstract void deleteFunction();

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
		table =  new CustomTable();
		JPanel addView = new JPanel();
		JPanel modifyView = new JPanel();
		
		setDatabaseView(table);
		setAddPage(addView);
		setModifyPage(modifyView);
		
		// Load the content into the content panel.
		contentPanel.add(new Overview(), "Overview");	// Default view.
		contentPanel.add(addView, "Add");
		contentPanel.add(modifyView, "Modify");
	}
	
	protected void displayPage(String viewName) {
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
			JButton searchButton = new IconButton(
				"/search.png", e -> searchFunction(searchField.getText()));
			topPanel.add(searchButton);
			
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new BorderLayout(0, 0));
			innerPanel.setOpaque(false);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getViewport().setBackground(Color.WHITE);
			
			scrollPane.setViewportView(table);
			table.setFillsViewportHeight(true);
			
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
				buttonSize, true, buttonFont, e -> {
					if (table.getSelectedRow() != -1) {
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
			
			Component verticalStrut2 = Box.createVerticalStrut(10);
			rightPanel.add(verticalStrut2);
			
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
							deleteFunction();
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
	
	class CustomTable extends JTable {
		
		public CustomTable() {
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setRowHeight(tableRowHeight);
			setSelectionBackground(Palette.tableSelectColor);
			setSelectionForeground(Color.WHITE);
			setShowGrid(false);
			setIntercellSpacing(new Dimension(0, 0));
			
			// Remove cell highlight border.
			setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			    @Override
			    public Component getTableCellRendererComponent(JTable table, Object value,
			            boolean isSelected, boolean hasFocus, int row, int column) {
			    	hasFocus = false;
			        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			    }
			});
		}
		
		@Override	// Allows deselecting of selected rows.
		public void changeSelection(
			int rowIndex, int columnIndex, boolean toggle, boolean extend
	    ) {
	        super.changeSelection(rowIndex, columnIndex, true, false);
		}
		
		@Override	// Disable cell editing.
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
		@Override	// Alternating row colors.
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component returnComp = super.prepareRenderer(renderer, row, column);
	        Color alternateColor = Palette.tableAlternateColor;
	        Color whiteColor = Color.WHITE;
	        if (!returnComp.getBackground().equals(getSelectionBackground())){
	            Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
	            returnComp .setBackground(bg);
	            bg = null;
	        }
	        return returnComp;
		}
	}
}
