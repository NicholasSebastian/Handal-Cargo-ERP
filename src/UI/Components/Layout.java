package UI.Components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import Static.Palette;
import UI.Components.ColoredButton;
import UI.Components.SearchButton;

@SuppressWarnings("serial")
public class Layout extends JPanel {
	
	private final Font buttonFont = new Font("Arial", Font.BOLD, 16);
	private final Dimension buttonSize = new Dimension(90, 90);
	
	private JTextField searchField;
	protected JScrollPane scrollPane;

	public Layout() {

		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 0));
		
		JPanel titlePanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) titlePanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		add(titlePanel, BorderLayout.NORTH);
		
		// Title.
		JLabel titleLabel = new JLabel("Accounts");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titlePanel.add(titleLabel);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout(0, 0));
		add(contentPanel, BorderLayout.CENTER);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		FlowLayout flowLayout2 = (FlowLayout) topPanel.getLayout();
		flowLayout2.setAlignment(FlowLayout.LEFT);
		contentPanel.add(topPanel, BorderLayout.NORTH);
		
		// Search field.
		searchField = new JTextField();
		searchField.setColumns(15);
		searchField.setFont(new Font("Arial", Font.PLAIN, 14));
		searchField.setMargin(new Insets(6, 6, 6, 6));
		topPanel.add(searchField);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		topPanel.add(horizontalStrut);
		
		// Search button.
		JButton searchButton = new SearchButton(e -> {
			System.out.println(searchField.getText());
		});
		topPanel.add(searchButton);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(innerPanel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		innerPanel.add(rightPanel, BorderLayout.EAST);
		
		// Add button.
		JButton addButton = new ColoredButton(
			"Add", Palette.green, Palette.greenHover, 
			buttonSize, true, buttonFont, e -> {
				System.out.println("Add");
			});
		rightPanel.add(addButton);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		rightPanel.add(verticalStrut);
		
		// Modify button.
		JButton modifyButton = new ColoredButton(
			"Modify", Palette.yellow, Palette.yellowHover, 
			buttonSize, true, buttonFont, e -> {
				System.out.println("Modify");
			});
		rightPanel.add(modifyButton);
		
		Component verticalStrut2 = Box.createVerticalStrut(10);
		rightPanel.add(verticalStrut2);
		
		// Delete button.
		JButton deleteButton = new ColoredButton(
			"Delete", Palette.red, Palette.redHover, 
			buttonSize, true, buttonFont, e -> {
				System.out.println("Delete");
			});
		rightPanel.add(deleteButton);
		
		// Table.
		scrollPane = new JScrollPane();
		innerPanel.add(scrollPane, BorderLayout.CENTER);
	}
}
