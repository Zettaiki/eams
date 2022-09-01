package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

import model.GarbageCollection;

public class GarbageCollectionTable {

	public static final String TABLE_NAME = "raccolta";

	private final Connection connection;

	public GarbageCollectionTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	// 6 credo
	public boolean save(GarbageCollection raccolta) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idServizio, materiale, kg) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, raccolta.getIdServizio());
            statement.setString(2, raccolta.getMateriale());
            statement.setBigDecimal(3, raccolta.getKg());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
