package UI.Pages;

import java.util.ArrayList;
import javax.swing.*;
import java.sql.ResultSet;

import Dynamic.Groups;
import Static.Database;
import UI.Layouts.QueryLayout;

@SuppressWarnings("serial")
public class Accounts extends QueryLayout {
	
	public Accounts() {
		titleLabel.setText("Accounts");
	}
	
	@Override
	protected void setDatabaseView(JScrollPane scrollPane) {
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
		JTable table = new JTable(dataArray, columns);
		scrollPane.setViewportView(table);
	}
	
	@Override
	protected void searchFunction(String query) {
		
	}
	
	@Override
	protected void setAddPage(JPanel addView) {
		addView.setOpaque(false);
		
	}
	
	@Override
	protected void setModifyPage(JPanel modifyView) {
		modifyView.setOpaque(false);
		
	}

	@Override
	protected void setDeletePage(JPanel deleteView) {
		deleteView.setOpaque(false);
		
	}
}
