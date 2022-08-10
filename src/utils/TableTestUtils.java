package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class TableTestUtils {
	public static boolean dropTable(Connection connection, String tableName) {
		try (final Statement statement = connection.createStatement()) {			
			statement.executeUpdate("SET foreign_key_checks = 0;");
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);            
            statement.executeUpdate("SET foreign_key_checks = 1;");
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}
}
