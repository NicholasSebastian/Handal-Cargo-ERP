package UI;

import java.awt.event.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import Schemas.Account;

public class Home {
	
	public Home(Account account) {
		
	}
}

//JButton button = new JButton("Insert to database");
//button.addActionListener(new ActionListener() {
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Database.update("INSERT INTO accounts (username, password) VALUES (?, ?)", statement -> {
//			try {
//				statement.setString(1, "nicholas");
//				statement.setString(2, "locupan");
//			}
//			catch (SQLException ex) {
//				Database.printErrors(ex);
//			}
//		});
//	}
//});
//add(button);