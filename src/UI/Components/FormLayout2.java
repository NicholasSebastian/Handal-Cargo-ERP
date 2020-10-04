package UI.Components;

import java.util.Map.Entry;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import Static.Palette;
import UI.Components.ColoredButton;

@SuppressWarnings("serial")
public class FormLayout2 extends JPanel {
	
	private static final Font
		formFont = new Font("Arial", Font.PLAIN, 13);
	
	private JTable table;
	
	public FormLayout2(
		LinkedHashMap<String, Component> formContent, 
		LinkedHashMap<String, Component> innerformContent,
		JTable table, String buttonText, Consumer<Boolean> submitFunction
	) {
		setOpaque(false);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 8, 5, 8);
		
		// Labels
		c.gridx = 0;
		c.anchor = GridBagConstraints.LINE_END;
		
		int i = 0;
		for(Entry<String, Component> entry : formContent.entrySet()) {
			JLabel formLabel = new JLabel(entry.getKey());
			formLabel.setFont(formFont);
			c.gridy = i++;
			add(formLabel, c);
		}
		
		// Fields
		c.gridx = 1;	c.weightx = 1.0;
		c.anchor = GridBagConstraints.LINE_START;
		
		i = 0;
		for(Entry<String, Component> entry : formContent.entrySet()) {
			Component component = entry.getValue();
			if (component instanceof JTextField) {
				component.setFont(formFont);
				((JTextField) component).setMargin(new Insets(6, 6, 6, 6));
			}
			c.gridy = i++;
			add(component, c);
		}
		
		// Had to move this here for option button reference
		JPanel cardPanel = new JPanel();
		CardLayout addLayout = new CardLayout();
		
		// Option Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
		buttonPanel.setOpaque(false);
		buttonPanel.setMinimumSize(new Dimension(240, 40));
		c.gridx = 0; c.gridy = formContent.size();
		c.gridwidth = 2; c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		add(buttonPanel, c);
		
		Dimension buttonSize = new Dimension(110, 30);
		JButton optionButton = new ColoredButton(
			"New Staff", Palette.blue, Palette.blueHover, 
			buttonSize, true, formFont, e -> addLayout.show(cardPanel, "Create"));
		JButton optionButton2 = new ColoredButton(
			"Existing Staff", Palette.blue, Palette.blueHover, 
			buttonSize, true, formFont, e -> addLayout.show(cardPanel, "Existing"));
		buttonPanel.add(optionButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(optionButton2);
		
		// CardLayout panel
		cardPanel.setLayout(addLayout);
		cardPanel.setOpaque(false);
		c.gridx = 0; c.gridy = formContent.size() + 1; 
		c.gridwidth = 2; c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		add(cardPanel, c);
		
		// Finish Button
		JButton finishButton = new ColoredButton(
			buttonText, Palette.blue, Palette.blueHover, 
			new Dimension(140, 30), true, formFont, 
			e -> {
				submitFunction.accept(cardPanel.getComponent(0).isVisible());
				JOptionPane.showMessageDialog(this.getParent(), "Entry added successfully.");
			});
		
		c.gridx = 1; c.gridy = formContent.size() + 2;
		c.gridwidth = 1; c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHEAST;
		
		JPanel finishPanel = new JPanel();
		finishPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		finishPanel.setOpaque(false);
		finishPanel.setMinimumSize(new Dimension(160, 30));
		finishPanel.add(finishButton);
		
		add(finishPanel, c);
		
		// Form content
		JPanel formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setLayout(new GridBagLayout());
		formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JScrollPane scrollForm = new JScrollPane(formPanel);
		scrollForm.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollForm.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollForm.getVerticalScrollBar().setUnitIncrement(16);
		
		scrollForm.setName("Create");
		cardPanel.add(scrollForm, scrollForm.getName());
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.insets = new Insets(5, 8, 5, 8);
		
		// Labels
		c2.gridx = 0;
		c2.anchor = GridBagConstraints.LINE_END;
		
		i = 0;
		for(Entry<String, Component> entry : innerformContent.entrySet()) {
			JLabel formLabel = new JLabel(entry.getKey());
			formLabel.setFont(formFont);
			c2.gridy = i++;
			formPanel.add(formLabel, c2);
		}
		
		// Fields
		c2.gridx = 1;	c2.weightx = 1.0;
		c2.anchor = GridBagConstraints.LINE_START;
		
		i = 0;
		for(Entry<String, Component> entry : innerformContent.entrySet()) {
			Component component = entry.getValue();
			if (component instanceof JTextField) {
				component.setFont(formFont);
				((JTextField) component).setMargin(new Insets(6, 6, 6, 6));
			}
			c2.gridy = i++;
			formPanel.add(component, c2);
		}
		
		// Empty space
		JPanel emptySpace = new JPanel();
		emptySpace.setOpaque(false);
		c2.gridx = 0; c2.gridy = innerformContent.size() + 1;
		c2.gridwidth = 2; c2.weighty = 1.0;
		formPanel.add(emptySpace, c2);
		
		// Staff Table content
		this.table = table;
		
		JTableHeader header = this.table.getTableHeader();
		header.setOpaque(false);
		header.setBackground(Palette.headerColor);
		header.setForeground(Color.WHITE);
		
		JScrollPane scrollTable = new JScrollPane(this.table);
		scrollTable.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
		scrollTable.getViewport().setBackground(Color.WHITE);
		scrollTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollTable.setName("Existing");
		cardPanel.add(scrollTable, scrollTable.getName());
	}
}
