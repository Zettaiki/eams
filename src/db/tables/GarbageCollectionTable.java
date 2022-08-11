package db.tables;

import java.math.BigDecimal;
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

import db.TableTriplePk;
import model.GarbageCollection;

public class GarbageCollectionTable implements TableTriplePk<GarbageCollection, String, String, String> {

	public static final String TABLE_NAME = "raccolta";

	private final Connection connection;

	public GarbageCollectionTable(final Connection connection) {
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
            			"idServizio CHAR(20) NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"materiale VARCHAR(30) NOT NULL," +
            			"kg FLOAT NOT NULL," +
            			"PRIMARY KEY (idServizio, idEvento, materiale)," +
            			"FOREIGN KEY (materiale) REFERENCES rifiuto (materiale) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (idServizio, idEvento) REFERENCES servizio (idServizio, idEvento) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {    
        	System.out.println(e.toString());
            return false;
        }
	}

	@Override
	public Optional<GarbageCollection> findByPrimaryKey(String idServizio, String idEvento, String materiale) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idServizio = ? AND idEvento = ? AND materiale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idServizio);
            statement.setString(2, idEvento);
            statement.setString(3, materiale);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<GarbageCollection> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<GarbageCollection> readFromResultSet(ResultSet resultSet) {
		final List<GarbageCollection> raccolte = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idServizio = resultSet.getString("idServizio");
				final String idEvento = resultSet.getString("idEvento");
				final String materiale = resultSet.getString("materiale");
				final BigDecimal kg = resultSet.getBigDecimal("kg");
				
				final GarbageCollection raccolta = new GarbageCollection(idServizio, idEvento, materiale, kg);
				raccolte.add(raccolta);
			}
		} catch (final SQLException e) {}
		return raccolte;
	}

	@Override
	public boolean save(GarbageCollection raccolta) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idServizio, idEvento, materiale, kg) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, raccolta.getIdServizio());
            statement.setString(2, raccolta.getIdEvento());
            statement.setString(3, raccolta.getMateriale());
            statement.setBigDecimal(4, raccolta.getKg());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(GarbageCollection updatedRaccolta) {
		final String query = "UPDATE " + TABLE_NAME + " SET kg = ? "
				+ "WHERE idServizio = ? AND idEvento = ? AND materiale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setBigDecimal(1, updatedRaccolta.getKg());
			statement.setString(2, updatedRaccolta.getIdServizio());
			statement.setString(3, updatedRaccolta.getIdEvento());
			statement.setString(4, updatedRaccolta.getMateriale());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String idServizio, String idEvento, String materiale) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idServizio = ? AND idEvento = ? AND materiale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idServizio);
            statement.setString(2, idEvento);
			statement.setString(3, materiale);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
