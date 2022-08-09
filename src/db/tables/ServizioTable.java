package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Servizio;

public class ServizioTable implements Table<Servizio, String> {

	public static final String TABLE_NAME = "servizio";

	private final Connection connection;

	public ServizioTable(final Connection connection) {
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
            			"idEvento CHAR(20) NOT NULL," +
            			"oraInizio TIME NOT NULL," +
            			"oraFine TIME NOT NULL," +
            			"tipo VARCHAR(45) NOT NULL," +
            			"idProgetto INT NULL DEFAULT NULL," +
            			"PRIMARY KEY (idEvento, oraInizio)," +
            			"FOREIGN KEY (idEvento) REFERENCES evento (idEvento)" +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (idProgetto) REFERENCES progetto (idProgetto) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Servizio> findByPrimaryKey(String idEvento) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idEvento);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Servizio> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Servizio> readFromResultSet(ResultSet resultSet) {
		final List<Servizio> servizi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idEvento = resultSet.getString("idEvento");
				final String tipo = resultSet.getString("tipo");
				final Time oraInizio = resultSet.getTime("oraInizio");
			    final Time oraFine = resultSet.getTime("oraFine");
			    final Optional<String> idProgetto = Optional.ofNullable(resultSet.getString("idProgetto"));
				
				final Servizio servizio = new Servizio(idEvento, tipo, oraInizio, oraFine, idProgetto);
				servizi.add(servizio);
			}
		} catch (final SQLException e) {}
		return servizi;
	}

	@Override
	public boolean save(Servizio servizio) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idEvento, tipo, oraInizio, oraFine, idProgetto) VALUES (?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, servizio.getIdEvento());
            statement.setString(2, servizio.getTipo());
            statement.setTime(3, servizio.getOraInizio());
            statement.setTime(4, servizio.getOraFine());
            statement.setString(5, servizio.getIdProgetto().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Servizio updatedServizio) {
		final String query = "UPDATE " + TABLE_NAME + " SET tipo = ?," + "oraInizio = ?," + "oraFine = ?,"
				+ "idProgetto = ? WHERE idEvento = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedServizio.getTipo());
			statement.setTime(2, updatedServizio.getOraInizio());
            statement.setTime(3, updatedServizio.getOraFine());
            statement.setString(4, updatedServizio.getIdProgetto().orElse(null));
            statement.setString(5, updatedServizio.getIdEvento());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String idEvento) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idEvento);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
