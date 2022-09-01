package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

import model.Participation;

public class ParticipationTable {

	public static final String TABLE_NAME = "partecipazione";

	private final Connection connection;

	public ParticipationTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	// 13
	public boolean save(Participation partecipazione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscaleVolontario, idServizio) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, partecipazione.getCodiceFiscaleVolontario());
            statement.setString(2, partecipazione.getIdServizio());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
