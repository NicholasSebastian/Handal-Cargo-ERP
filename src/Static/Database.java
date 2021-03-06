package Static;

import java.sql.*;
import java.util.function.Consumer;

// REMINDER: SQL has many reserved keywords such as `name`, `desc`, `groups`, etc.
// use backticks to fix this problem.

public class Database {
	
	static Connection connection;
	
	static final String 
	    hostname = "192.168.1.112", 
	    port = "3306",	// Default value. Can be changed in the my.ini file.
	    databaseName = "handalcargo",
	    user = "handalcargo",
	    password = "hunter1389";
	
	public static Exception initialize() {
		try {
			// Register the MySQL Connector/J.
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			
			// Initialize connection with database.
			String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName + "?serverTimezone=UTC";
	        connection = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			e.printStackTrace();
			return e;
		}
		return null;
	}
	
	// Reading from the database with a static statement.
	public static ResultSet query(String query) {
		try {
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(query);
			return results;
		}
		catch (SQLException e) {
			printErrors(e);
		}
		return null;
	}
	
	// Reading from the database with a prepared statement.
	public static ResultSet query(String query, Consumer<PreparedStatement> setStatement) {
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			setStatement.accept(statement);
			ResultSet results = statement.executeQuery();
			return results;
		}
		catch(SQLException e) {
			printErrors(e);
		}
		return null;
	}
		
	// Modifying the database.
	public static void update(String query, Consumer<PreparedStatement> setStatement) {
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			setStatement.accept(statement);
			int i = statement.executeUpdate();
			System.out.println(i + " records updated.");
		}
		catch (SQLException e) {
			printErrors(e);
		}
	}
	
	// Closing the connection.
	public static void closeConnection() {
		try {
			connection.close();
		}
		catch(SQLException e) {
			printErrors(e);
		}
	}
	
	// Formatted SQL error logging.
	public static void printErrors(SQLException e) {
		System.out.println("SQLException: " + e.getMessage());
	    System.out.println("SQLState: " + e.getSQLState());
	    System.out.println("VendorError: " + e.getErrorCode());
	}
}
