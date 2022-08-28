package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Service;

public class ServiceTable {

	public static final String TABLE_NAME = "servizio";

	private final Connection connection;

	public ServiceTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

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

	public List<Service> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Service> readFromResultSet(ResultSet resultSet) {
		final List<Service> servizi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idServizio = resultSet.getString("idServizio");
				final String idEvento = resultSet.getString("idEvento");
				final Time oraInizio = resultSet.getTime("oraInizio");
			    final Time oraFine = resultSet.getTime("oraFine");
				final String tipo = resultSet.getString("tipo");
			    Optional<Integer> idProgetto = Optional.ofNullable(resultSet.getInt("idProgetto"));
			    if(resultSet.wasNull()) {
			    	idProgetto = Optional.empty();
			    }
				
				final Service servizio = new Service(idServizio, idEvento, oraInizio, oraFine, tipo, idProgetto);
				servizi.add(servizio);
			}
		} catch (final SQLException e) {}
		return servizi;
	}

	public boolean save(Service servizio) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idServizio, idEvento, oraInizio, oraFine, tipo, idProgetto) VALUES (?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, servizio.getIdServizio());
            statement.setString(2, servizio.getIdEvento());
            statement.setTime(3, servizio.getOraInizio());
            statement.setTime(4, servizio.getOraFine());
            statement.setString(5, servizio.getTipo());
            if(servizio.getIdProgetto().isEmpty()) {
                statement.setNull(6, Types.INTEGER);
            } else {
                statement.setInt(6, servizio.getIdProgetto().get());
            }
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
        	System.out.println(e.toString());
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

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
