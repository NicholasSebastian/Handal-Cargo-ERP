package Dynamic;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.sql.ResultSet;

import Static.Database;

public class Groups {
	
	// Integer : roleId
	// SimpleEntry<String, boolean[]> : roleName and accessRights
	public static HashMap<Integer, SimpleEntry<String, boolean[]>> groups = new HashMap<>();
	
	// Load/reload settings from database.
	public static void initialize() {
		try {	
			// Query from database.
			ResultSet results = Database.query("SELECT * FROM staffgroups");
			
			int boolCount = results.getMetaData().getColumnCount() - 2;
			
			while (results.next()) {
				Integer roleId = results.getInt("id");
				String roleName = results.getString("role");
				
				boolean[] accessRights = new boolean[boolCount];
				for (int i = 0; i < boolCount; i++) {
					accessRights[i] = results.getBoolean(i + 3);
				}
				
				groups.put(roleId, new SimpleEntry<String, boolean[]>(roleName, accessRights));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Get the role name from the id.
	public static String getRoleName(int roleId) {
		return groups.get(roleId).getKey();
	}
}
