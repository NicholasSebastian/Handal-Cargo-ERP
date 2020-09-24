package UI.Pages;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

import Dynamic.Groups;
import Static.Database;
import UI.Layouts.QueryLayout;

@SuppressWarnings("serial")
public class Accounts extends QueryLayout {
	
	public Accounts() {
		titleLabel.setText("Accounts");
		addTitleLabel.setText("Create an Account");
		modifyTitleLabel.setText("Modify Account");
	}
	
	@Override
	protected void setDatabaseView(JTable table) {
		final String[] columns = {"Username", "Encrypted Password", "Full Name", "Group"};
		ArrayList<String[]> data = new ArrayList<>();
		
		try {
			ResultSet results = Database.query("SELECT * FROM accounts");
			ResultSet details = Database.query("SELECT `name`, `group` FROM staff");
			
			while (results.next() && details.next()) {
				data.add(new String[] {
					results.getString("username"), 
					results.getString("password"), 
					details.getString("name"), 
					Groups.getRoleName(details.getInt("group"))
				});
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Convert data arrayList to 2D array.
		String dataArray[][] = new String[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			dataArray[i] = data.get(i);
		}
		
		// Create table and set view.
		DefaultTableModel model = new DefaultTableModel(dataArray, columns);
		table.setModel(model);
	}
	
	@Override
	protected void searchFunction(String query) {
		System.out.println(query);
	}
	
	@Override
	protected void setAddPage(JPanel addView) {
		
		// Enter username and password.
		// Then can either choose to pick an existing staff in the database or
		// Enter details to create a new staff sekalian, then use that staff.
		
//			JLabel label = new JLabel();
//			JTextField textField = new JTextField();
//			
//			label.setLocation(100, 100);
//			label.setSize(100, 20);
//			textField.setLocation(200, 100);
//			textField.setSize(200, 20);
//			
//			content.add(label);
//			content.add(textField);
	}
	
	@Override
	protected void setModifyPage(JPanel modifyView) {
		
	}

	@Override
	protected void deleteFunction() {
		
	}
}
