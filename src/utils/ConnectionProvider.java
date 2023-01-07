package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Expose a utility method to connect to a MySQL database 
 */
public final class ConnectionProvider {
	
	private static String PORT = "3306";
	private static String DBNAME = "eams";
	private static String USERNAME = "root";
	private static String PASSWORD = "";
	
    /**
     * @return a Connection with the database specified in the class constructor
     * @throws IllegalStateException if the connection could not be establish
     */
    public static Connection getMySQLConnection() {
        final String dbUri = "jdbc:mysql://localhost:" + PORT + "/" + DBNAME;
        try {
            return DriverManager.getConnection(dbUri, USERNAME, PASSWORD);
        } catch (final SQLException e) {
        	
            throw new IllegalStateException("Could not establish a connection with db", e);
        }
    }
    
	public static String getPORT() {
		return PORT;
	}

	public static void setPORT(String port) {
		PORT = port;
	}

	public static String getDBNAME() {
		return DBNAME;
	}

	public static void setDBNAME(String dbname) {
		DBNAME = dbname;
	}

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String username) {
		USERNAME = username;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String password) {
		PASSWORD = password;
	}

}
