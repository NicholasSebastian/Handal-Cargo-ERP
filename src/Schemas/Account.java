package Schemas;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	
	public String username;
	public int level;
	
	public Account(String username) {
		String query = "SELECT level FROM accounts WHERE username = " + username;
		ResultSet account = Database.query(query);
		
		try {
			account.next();
			this.username = username;
			this.level = account.getInt("level");
		}
		catch(SQLException e) {
			Database.printErrors(e);
		}
	}
}
