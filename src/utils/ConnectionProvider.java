package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Expose a utility method to connect to a MySQL database 
 */
public final class ConnectionProvider {
	
    /**
     * @return a Connection with the database specified in the class constructor
     * @throws IllegalStateException if the connection could not be establish
     */
    public static Connection getMySQLConnection() {
        final String dbUri = "jdbc:mysql://localhost:3306/" + ServerCredentials.DBNAME.toString();
        try {
            // Thanks to the JDBC DriverManager we can get a connection to the database
            return DriverManager.getConnection(dbUri, ServerCredentials.USERNAME.toString(), ServerCredentials.PASSWORD.toString());
        } catch (final SQLException e) {
            throw new IllegalStateException("Could not establish a connection with db", e);
        }
    }
}
