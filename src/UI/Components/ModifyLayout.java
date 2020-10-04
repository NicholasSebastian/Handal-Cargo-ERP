package UI.Components;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Static.Palette;

@SuppressWarnings({"serial", "rawtypes"})
public class ModifyLayout extends JPanel {
	
	private static final Font
		formFont = new Font("Arial", Font.PLAIN, 13);
	
	private ImageIcon changeIcon;
	
	public ModifyLayout(
		LinkedHashMap<String, Component> formContent,
		String buttonText, Runnable submitFunction
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
		
		// Buttons
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_START;
		
		JLabel editedIcons[] = new JLabel[formContent.size()];
		for (int j = 0; j < formContent.size(); j++) {
			JLabel editedIcon = new JLabel();
			
			ImageIcon icon = new ImageIcon(this.getClass().getResource("/edited.png"));
			ImageIcon blankIcon = new ImageIcon(this.getClass().getResource("/blank.png"));
			Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			Image scaledBlankIcon = blankIcon.getImage().getScaledInstance(20, 20, Image.SCALE_FAST);
			
			changeIcon = new ImageIcon(scaledIcon);
			editedIcon.setIcon(new ImageIcon(scaledBlankIcon));
			
			c.gridy = j;
			add(editedIcon, c);
			editedIcons[j] = editedIcon;
		}
		
		// Fields
		c.gridx = 1;	c.weightx = 1.0;
		
		i = 0;
		for(Entry<String, Component> entry : formContent.entrySet()) {
			Component component = entry.getValue();
			if (component instanceof JTextField) {
				component.setFont(formFont);
				((JTextField) component).setMargin(new Insets(6, 6, 6, 6));
				
				int j = i;	// this is retarded.
				((JTextField) component).getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent e) {
						editedIcons[j].setIcon(changeIcon);
					}
					@Override
					public void insertUpdate(DocumentEvent e) {
						editedIcons[j].setIcon(changeIcon);
					}
					@Override
					public void removeUpdate(DocumentEvent e) {
						editedIcons[j].setIcon(changeIcon);
					}
				});
			}
			else if (component instanceof JComboBox) {
				int j = i;	// whyyyy?
				((JComboBox) component).addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						editedIcons[j].setIcon(changeIcon);
					}
				});
			}
			// TODO: add for other types.
			
			c.gridy = i++;
			add(component, c);
		}
		
		// Finish Button
		JButton finishButton = new ColoredButton(
			buttonText, Palette.blue, Palette.blueHover, 
			new Dimension(140, 30), true, formFont, 
			e -> {
				submitFunction.run();
				JOptionPane.showMessageDialog(this.getParent(), "Entry updated successfully.");
			});
		
		c.gridx = 0; c.gridy = formContent.size();
		c.gridwidth = 3;
		c.weightx = 1.0; c.weighty = 1.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHEAST;
		
		JPanel finishPanel = new JPanel();
		finishPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		finishPanel.setOpaque(false);
		finishPanel.setMinimumSize(new Dimension(160, 30));
		finishPanel.add(finishButton);
		
		add(finishPanel, c);
	}
}
