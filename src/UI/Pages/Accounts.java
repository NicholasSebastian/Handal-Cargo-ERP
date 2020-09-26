package UI.Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Component;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Dynamic.Groups;
import Static.Database;
import UI.Components.QueryLayout;
import UI.Components.SliderButton;
import UI.Components.DatePicker;
import UI.Components.FormLayout2;
import UI.Components.NumberField;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
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
	protected void setAddPage(JPanel addContent) {
		HashMap<String, Component> formContent = new HashMap<>();
		formContent.put("Username", new JTextField());
		formContent.put("Password", new JTextField());
		
		ArrayList<String> groups = new ArrayList<>();
		Groups.groups.values().forEach(entry -> groups.add(entry.getKey()));
		
		HashMap<String, Component> innerFormContent = new HashMap<>();
		innerFormContent.put("Name", new JTextField());
		innerFormContent.put("Group", new JComboBox(groups.toArray()));
		innerFormContent.put("Address", new JTextField());
		innerFormContent.put("Kelurahan", new JTextField());
		innerFormContent.put("City", new JTextField());
		innerFormContent.put("Phone", new JTextField());
		innerFormContent.put("Mobile", new JTextField());
		innerFormContent.put("Sex", new JComboBox(new String[] {"Male", "Female"}));
		innerFormContent.put("Religion", new JTextField());
		innerFormContent.put("Birth Place", new JTextField());
		innerFormContent.put("Birthday", new DatePicker());
		innerFormContent.put("Lembur", new NumberField());
		innerFormContent.put("Salary", new NumberField());
		innerFormContent.put("Allowance", new NumberField());
		innerFormContent.put("THR", new NumberField());
		innerFormContent.put("Bonus", new NumberField());
		innerFormContent.put("Active", new SliderButton());
		innerFormContent.put("Employment Date", new DatePicker());
		
		JTable staffTable = new JTable();
		
		FormLayout2.addTo(addContent, formContent, innerFormContent, staffTable, e -> {
			// TODO: Password Encryption
			// TODO: Form Validation
			
		});
	}
	
	@Override
	protected void setModifyPage(JPanel modifyContent) {
		
	}

	@Override
	protected void deleteFunction(Object selectedRowValue) {
		
	}
}
