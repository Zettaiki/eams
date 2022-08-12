package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

	public Optional<Participation> findByPrimaryKey(String codiceFiscaleVolontario, String idServizio) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleVolontario = ? AND idServizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleVolontario);
            statement.setString(2, idServizio);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Participation> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Participation> readFromResultSet(ResultSet resultSet) {
		final List<Participation> partecipazioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleVolontario = resultSet.getString("codiceFiscaleVolontario");
				final String idServizio = resultSet.getString("idServizio");
				
				final Participation partecipazione = new Participation(codiceFiscaleVolontario, idServizio);
				partecipazioni.add(partecipazione);
			}
		} catch (final SQLException e) {}
		return partecipazioni;
	}

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

	public boolean delete(String codiceFiscaleVolontario, String idServizio) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleVolontario = ?" +
				"AND idServizio = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleVolontario);
            statement.setString(2, idServizio);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
