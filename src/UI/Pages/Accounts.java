package UI.Pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dynamic.Groups;
import Static.Database;
import Static.Encryption;
import UI.Components.QueryLayout;
import UI.Components.SliderButton;
import UI.Components.DatePicker;
import UI.Components.FormLayout2;
import UI.Components.ModifyLayout;
import UI.Components.NumberField;
import UI.Components.CustomComboBox;
import UI.Components.CustomTable;

@SuppressWarnings({ "serial", "rawtypes" })
public class Accounts extends QueryLayout {
	
	// TODO: Refresh not fucking working.
	// Idea on how to refresh: 
		// just like when dynamically replacing the modify pages,
		// just abandon the old page instance in favor for a new one.
		// Java's garbage collector will free up memory automatically assuming the old page instance has no more references.
	
	public Accounts() {
		setTitles("Accounts", "Create an Account", "Modify Account");
	}

	@Override
	protected TableModel setTable() {
		final String[] columns = {"Username", "Encrypted Password", "Full Name", "Group"};
		ArrayList<String[]> data = new ArrayList<>();
		
		try {
			ResultSet results = Database.query("SELECT * FROM accounts");
			
			while (results.next()) {
				String staffId = results.getString("staffid");
				ResultSet details = Database.query("SELECT `name`, `group` FROM staff WHERE staffId=" + staffId);
				details.next();
				
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
		return new DefaultTableModel(dataArray, columns);
	}
	
	@Override
	protected void searchFunction(String query) {
		System.out.println(query);
	}

	@Override
	protected JPanel setAddPanel() {
		LinkedHashMap<String, Component> formContent = new LinkedHashMap<>();
		formContent.put("Username", new JTextField());
		formContent.put("Password", new JPasswordField());
		
		ArrayList<String> groups = new ArrayList<>();
		Groups.groups.values().forEach(entry -> groups.add(entry.getKey()));
		
		LinkedHashMap<String, Component> innerFormContent = new LinkedHashMap<>();
		innerFormContent.put("Name", new JTextField());
		innerFormContent.put("Group", new CustomComboBox(groups.toArray()));
		innerFormContent.put("Address", new JTextField());
		innerFormContent.put("Kelurahan", new JTextField());
		innerFormContent.put("City", new JTextField());
		innerFormContent.put("Phone", new JTextField());
		innerFormContent.put("Mobile", new JTextField());
		innerFormContent.put("Sex", new CustomComboBox(new String[] {"Male", "Female"}));
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
		
		final String[] columns = {
			"Staff ID", "Full Name", "Group", "Address", "Kelurahan", "City", "Phone Number", "Mobile Number",
			"Sex", "Religion", "Birth Place", "Birthday", "Lembur", "Salary", "Allowance", "THR", "Bonus", 
			"Active", "Employment Date"
		};
		
		ArrayList<String[]> data = new ArrayList<>();
		ResultSet staffResults = Database.query("SELECT * FROM staff");
		try {
			while (staffResults.next()) {
				data.add(new String[] {
					staffResults.getString(1),
					staffResults.getString(2),
					Groups.getRoleName(staffResults.getInt(3)),
					staffResults.getString(4),
					staffResults.getString(5),
					staffResults.getString(6),
					staffResults.getString(7),
					staffResults.getString(8),
					staffResults.getBoolean(9) ? "Female" : "Male",
					staffResults.getString(10),
					staffResults.getString(11),
					staffResults.getString(12),
					staffResults.getString(13),
					staffResults.getString(14),
					staffResults.getString(15),
					staffResults.getString(16),
					staffResults.getString(17),
					staffResults.getBoolean(18) ? "Active" :  "Inactive",
					staffResults.getString(19),
				});
			}
		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		// Convert data arrayList to 2D array.
		String dataArray[][] = new String[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			dataArray[i] = data.get(i);
		}
		
		JTable staffTable = new CustomTable();
		staffTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		staffTable.setModel(new DefaultTableModel(dataArray, columns));
		for (int i = 0; i < columns.length; i++) {
			staffTable.getColumnModel().getColumn(i).setPreferredWidth(100);
		}
		
		return new FormLayout2(formContent, innerFormContent, staffTable, "Create Account", formPage -> {
			if (formPage) {
				
				// Form Validation, if required, prompt, if not, fill default value.
				if (((JTextField) formContent.get("Username")).getText().isEmpty()) {
					JOptionPane.showMessageDialog(Accounts.this, "Username must be entered.");
					return;
				}
				else if (((JPasswordField) formContent.get("Password")).getPassword().length == 0) {
					JOptionPane.showMessageDialog(Accounts.this, "Password must be entered.");
					return;
				}
				else if (((JTextField) innerFormContent.get("Name")).getText().isEmpty()) {
					JOptionPane.showMessageDialog(Accounts.this, "Name must be entered.");
					return;
				}
				
				// Add a new staff entry to the database.
				Database.update(
					"INSERT INTO staff (`name`, `group`, `address`, `kelurahan`, `city`, `phone`, `mobile`, `sex`, `religion`, " +
						"`birthplace`, `birthday`, `lembur`, `salary`, `allowance`, `thr`, `bonus`, `active`, `employmentdate`) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
					statement -> {
						try {
							statement.setString(1, ((JTextField) innerFormContent.get("Name")).getText());
							statement.setInt(2, Groups.getRoleId((String) ((JComboBox) innerFormContent.get("Group")).getSelectedItem()));
							statement.setString(3, ((JTextField) innerFormContent.get("Address")).getText());
							statement.setString(4, ((JTextField) innerFormContent.get("Kelurahan")).getText());
							statement.setString(5, ((JTextField) innerFormContent.get("City")).getText());
							statement.setString(6, ((JTextField) innerFormContent.get("Phone")).getText());
							statement.setString(7, ((JTextField) innerFormContent.get("Mobile")).getText());
							statement.setBoolean(8, ((JComboBox) innerFormContent.get("Sex")).getSelectedItem() == "Female" ? true : false);
							statement.setString(9, ((JTextField) innerFormContent.get("Religion")).getText());
							statement.setString(10, ((JTextField) innerFormContent.get("Birth Place")).getText());
							statement.setDate(11, ((DatePicker) innerFormContent.get("Birthday")).getDate());
							statement.setFloat(12, ((NumberField) innerFormContent.get("Lembur")).getValue());
							statement.setFloat(13, ((NumberField) innerFormContent.get("Salary")).getValue());
							statement.setFloat(14, ((NumberField) innerFormContent.get("Allowance")).getValue());
							statement.setFloat(15, ((NumberField) innerFormContent.get("THR")).getValue());
							statement.setFloat(16, ((NumberField) innerFormContent.get("Bonus")).getValue());
							statement.setBoolean(17, ((SliderButton) innerFormContent.get("Active")).isSelected());
							statement.setDate(18, ((DatePicker) innerFormContent.get("Employment Date")).getDate());
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
				});
				
				Database.update("INSERT INTO accounts VALUES (?, ?, LAST_INSERT_ID())", 
					statement -> {
						try {
							statement.setString(1, ((JTextField) formContent.get("Username")).getText());
							statement.setString(2, Encryption.encrypt(
									String.valueOf(((JPasswordField) formContent.get("Password")).getPassword())
							));
						} 
						catch (Exception ex) {
							ex.printStackTrace();
						}
				});
				
				JOptionPane.showMessageDialog(this.getParent(), "Entry added successfully.");
			}
			else {
				int selectedRow = staffTable.getSelectedRow();
				if (selectedRow != -1) {
					Database.update("INSERT INTO accounts VALUES (?, ?, ?)", 
						statement -> {
							try {
								statement.setString(1, ((JTextField) formContent.get("Username")).getText());
								statement.setString(2, Encryption.encrypt(
										String.valueOf(((JPasswordField) formContent.get("Password")).getPassword())
								));
								statement.setInt(3, Integer.parseInt((String) staffTable.getValueAt(selectedRow, 0)));
							} 
							catch (Exception ex) {
								ex.printStackTrace();
							}
					});
					JOptionPane.showMessageDialog(this.getParent(), "Entry added successfully.");
				}
				else {
					JOptionPane.showMessageDialog(
						Accounts.this.getParent(), 
						"Select an entry for the account first!",
						"Create an Account",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	protected JPanel setModifyPanel(Object selected) {
		LinkedHashMap<String, Component> formContent = new LinkedHashMap<>();
		
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		
		String query = String.format("SELECT `username`, `password` FROM accounts WHERE username='%s'", selected);
		ResultSet results = Database.query(query);
		try {
			results.next();
			
			String username = results.getString(1);
			usernameField.setText(username);
			
			String password = Encryption.decrypt(results.getString(2));
			passwordField.setText(password);
			
			formContent.put("Username", usernameField);
			formContent.put("Password", passwordField);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new ModifyLayout(formContent, "Modify Account", () -> {
			String updateQuery = String.format("UPDATE accounts SET `username` = ?, `password` = ? WHERE `username`='%s'", selected);
			Database.update(updateQuery, statement -> {
				try {
					// TODO: before updating the username, check if the username is taken first.
					
					statement.setString(1, usernameField.getText());
					statement.setString(2, Encryption.encrypt(String.valueOf(passwordField.getPassword())));
				} 
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			});
		});
	}
	
	@Override
	protected void deleteFunction(Object selectedRowValue) {
		Database.update("DELETE FROM accounts WHERE username=?", 
			statement -> {
				try {
					statement.setString(1, selectedRowValue.toString());
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			});
	}
}
