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
import model.Service;

public class ServiceTable implements Table<Service, String> {

	public static final String TABLE_NAME = "servizio";

	private final Connection connection;

	public ServiceTable(final Connection connection) {
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
            			"idServizio CHAR(20) NOT NULL PRIMARY KEY," +
            			"idEvento CHAR(20) NOT NULL," +
            			"oraInizio TIME NOT NULL," +
            			"oraFine TIME NOT NULL," +
            			"tipo VARCHAR(45) NOT NULL," +
            			"idProgetto INT NULL DEFAULT NULL," +
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
	public Optional<Service> findByPrimaryKey(String idServizio) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idServizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idServizio);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Service> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Service> readFromResultSet(ResultSet resultSet) {
		final List<Service> servizi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idServizio = resultSet.getString("idServizio");
				final String idEvento = resultSet.getString("idEvento");
				final String tipo = resultSet.getString("tipo");
				final Time oraInizio = resultSet.getTime("oraInizio");
			    final Time oraFine = resultSet.getTime("oraFine");
			    final Optional<Integer> idProgetto = Optional.ofNullable(resultSet.getInt("idProgetto"));
				
				final Service servizio = new Service(idEvento, idServizio, oraInizio, oraFine, tipo, idProgetto);
				servizi.add(servizio);
			}
		} catch (final SQLException e) {}
		return servizi;
	}

	@Override
	public boolean save(Service servizio) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idServizio, idEvento, oraInizio, oraFine, tipo, idProgetto) VALUES (?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, servizio.getIdServizio());
            statement.setString(2, servizio.getIdEvento());
            statement.setTime(3, servizio.getOraInizio());
            statement.setTime(4, servizio.getOraFine());
            statement.setString(5, servizio.getTipo());
            statement.setInt(6, servizio.getIdProgetto().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
        	System.out.println(e.toString());
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Service updatedServizio) {
		final String query = "UPDATE " + TABLE_NAME + " SET idEvento = ?, oraInizio = ?, oraFine = ?, tipo = ?, idProgetto = ? "
				+ "WHERE idServizio = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedServizio.getIdEvento());
			statement.setTime(2, updatedServizio.getOraInizio());
			statement.setTime(3, updatedServizio.getOraFine());
			statement.setString(4, updatedServizio.getTipo());
            statement.setInt(5, updatedServizio.getIdProgetto().orElse(null));
            statement.setString(6, updatedServizio.getIdServizio());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String idServizio) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idServizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idServizio);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
