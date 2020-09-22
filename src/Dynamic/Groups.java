package Dynamic;

import java.util.AbstractMap;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

import Static.Database;

public class Groups {
	
	// Integer : roleId
	// SimpleEntry<String, boolean[]> : roleName and accessRights
	public static HashMap<Integer, AbstractMap.SimpleEntry<String, boolean[]>> groups = new HashMap<>();
	
	// Load/reload settings from database.
	public static void initialize() {
		
		// Query from database.
		ResultSet results = Database.query("SELECT * FROM groups");
		
		try {	
			int boolCount = results.getMetaData().getColumnCount() - 2;
			
			while (results.next()) {
				Integer roleId = results.getInt("id");
				String roleName = results.getString("role");
				
				boolean[] accessRights = new boolean[boolCount];
				for (int i = 0; i < boolCount; i++) {
					accessRights[i] = results.getBoolean(i + 3);
				}
				
				groups.put(roleId, new AbstractMap.SimpleEntry<String, boolean[]>(roleName, accessRights));
			}
		} 
		catch (SQLException e) {
			Database.printErrors(e);
		}
	}
}
