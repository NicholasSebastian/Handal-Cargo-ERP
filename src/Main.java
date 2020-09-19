// https://www.javatpoint.com/java-jdbc
// https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html#connector-j-examples-connection-drivermanager
// https://www.youtube.com/playlist?list=PLsyeobzWxl7rU7Jz3zDRpqB-EODzBbHOI

public class Main {
	
	static final String 
	    hostname = "192.168.0.1", 
	    port = "3306",	// Default value. Can be changed in the my.ini file.
	    databaseName = "handalcargo",
	    user = "handalcargo",
	    password = "hunter1389";

	public static void main(String[] args) {
		try {
			// Initialize connection with database.
			Database.loadDriver();
			Database.loadConnection(hostname, port, databaseName, user, password);
			
			// Run the application.
			UserInterface app = new UserInterface();
			app.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
