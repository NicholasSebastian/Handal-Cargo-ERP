package UI.Pages;

import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

import Dynamic.Groups;
import Static.Database;
import UI.Layouts.QueryLayout;
import UI.Components.TitleAndClose;

@SuppressWarnings("serial")
public class Accounts extends QueryLayout {
	
	public Accounts() {
		titleLabel.setText("Accounts");
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
		addView.setOpaque(false);
		addView.setLayout(new BorderLayout());
		
		addView.add(
			new TitleAndClose(
				"Create a new Account", 
				e -> displayPage("Overview")), 
			BorderLayout.NORTH);
		
		// here.
	}
	
	@Override
	protected void setModifyPage(JPanel modifyView) {
		modifyView.setOpaque(false);
		modifyView.setLayout(new BorderLayout());
		
		modifyView.add(
			new TitleAndClose(
				"Modify Account", 
				e -> displayPage("Overview")), 
			BorderLayout.NORTH);
		
		// here.
	}

	@Override
	protected void deleteFunction() {
		
	}
}
