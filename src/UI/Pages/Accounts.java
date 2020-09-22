package UI.Pages;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import Static.Database;
import UI.Components.Layout;

@SuppressWarnings("serial")
public class Accounts extends Layout {
	
	public Accounts() {
		
		String[] columns = {"Username", "Encrypted Password", "Full Name", "Group"};
		String[][] data = {
			{"nicholas", "Nicholas Sebastian", "1"}, 
			{"jacky", "Jacky Richie", "2"}, 
			{"seiya", "Seiya Watanabe", "3"}
		};
		
		Database.query("SELECT * FROM accounts");
		
		JTable table = new JTable(data, columns);
		
		// Set the view.
		scrollPane.setViewportView(table);
	}
}
