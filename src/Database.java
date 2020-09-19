import java.sql.*;
import java.util.function.Consumer;

public class Database {
	
	static Connection connection;
	
	// Register the MySQL Connector/J
	public static void loadDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Connect to the MySQL database
	public static void loadConnection(String hostname, String port, String databaseName, String user, String password) {
	    try {
	    	String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName;
	        connection = DriverManager.getConnection(url, user, password);
	    }
	    catch (SQLException e) {
	    	printException(e);
	    }
	}
	
	// Close the connection
	public static void closeConnection() {
		try {
			connection.close();
		}
		catch (SQLException e) {
			printException(e);
		}
	}
	
	// Reading from the database
	public static ResultSet query(String query) {
		try {
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			return results;
		}
		catch (SQLException e) {
			printException(e);
		}
		return null;
	}
	
	// Modifying the database
	public static void update(String query, Consumer<PreparedStatement> setStatement) {
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			setStatement.accept(statement);
			int i = statement.executeUpdate();
			System.out.println(i + " records updated.");
		}
		catch (SQLException e) {
			printException(e);
		}
	}
	
	// Custom error printing format
	private static void printException(SQLException e) {
		System.out.println("SQL Exception: " + e.getMessage());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Vendor Error: " + e.getErrorCode());
	}
}
