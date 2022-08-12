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

import db.TableDoublePk;
import model.Participation;

public class ParticipationTable implements TableDoublePk<Participation, String, String> {

	public static final String TABLE_NAME = "partecipazione";

	private final Connection connection;

	public ParticipationTable(final Connection connection) {
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
            			"idServizio CHAR(20) NOT NULL," +
            			"PRIMARY KEY (codiceFiscaleVolontario, idServizio)," +
            			"FOREIGN KEY (idServizio) REFERENCES servizio (idServizio) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (codiceFiscaleVolontario) REFERENCES volontario (codiceFiscale)" +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {
        	System.out.println(e.toString());
            return false;
        }
	}

	@Override
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

	@Override
	public List<Participation> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
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

	@Override
	public boolean save(Participation partecipazione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscaleVolontario, idServizio, idEvento) VALUES (?,?)";
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

	@Override
	public boolean update(Participation updatedPartecipazione) {
		throw new IllegalStateException();
	}

	@Override
	public boolean delete(String codiceFiscaleVolontario, String idServizio) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleVolontario = ?" +
				"AND idServizio = ? AND idEvento = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleVolontario);
            statement.setString(2, idServizio);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
