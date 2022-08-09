package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Partecipazione;

public class PartecipazioneTable implements Table<Partecipazione, String> {

	public static final String TABLE_NAME = "partecipazione";

	private final Connection connection;

	public PartecipazioneTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            			"codiceFiscaleVolontario CHAR(16) NOT NULL," +
            			"oraInizioServizio TIME NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"PRIMARY KEY (codiceFiscaleVolontario, oraInizioServizio, idEvento)," +
            			"FOREIGN KEY (oraInizioServizio, idEvento) REFERENCES servizio (oraInizio , idEvento) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (codiceFiscaleVolontario) REFERENCES volontario (codiceFiscale)" +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Partecipazione> findByPrimaryKey(String codiceFiscaleVolontario) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleVolontario = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleVolontario);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Partecipazione> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Partecipazione> readFromResultSet(ResultSet resultSet) {
		final List<Partecipazione> partecipazioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleVolontario = resultSet.getString("codiceFiscaleVolontario");
				final Timestamp oraInizioServizio = resultSet.getTimestamp("oraInizioServizio");
				final String idEvento = resultSet.getString("idEvento");
				
				final Partecipazione partecipazione = new Partecipazione(codiceFiscaleVolontario, oraInizioServizio, idEvento);
				partecipazioni.add(partecipazione);
			}
		} catch (final SQLException e) {}
		return partecipazioni;
	}

	@Override
	public boolean save(Partecipazione partecipazione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscaleVolontario, oraInizioServizio, idEvento) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, partecipazione.getCodiceFiscaleVolontario());
            statement.setTimestamp(2, partecipazione.getOraInizioServizio());
            statement.setString(3, partecipazione.getIdEvento());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Partecipazione updatedPartecipazione) {
		final String query = "UPDATE " + TABLE_NAME + " SET oraInizioServizio = ?," + "idEvento = ? "
				+ "WHERE codiceFiscaleVolontario = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedPartecipazione.getCodiceFiscaleVolontario());
			statement.setTimestamp(2, updatedPartecipazione.getOraInizioServizio());
			statement.setString(3, updatedPartecipazione.getCodiceFiscaleVolontario());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String codiceFiscaleVolontario) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleVolontario = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleVolontario);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
