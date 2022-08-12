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

import model.Garbage;

public class GarbageTable {

	public static final String TABLE_NAME = "rifiuto";

	private final Connection connection;

	public GarbageTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}
	
	public Optional<Garbage> findByPrimaryKey(String materiale) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE materiale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, materiale);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Garbage> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Garbage> readFromResultSet(ResultSet resultSet) {
		final List<Garbage> rifiuti = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String materiale = resultSet.getString("materiale");
				final String tipo = resultSet.getString("tipo");
				final BigDecimal kgImagazzinati = resultSet.getBigDecimal("kgImagazzinati");
				final Optional<String> note =  Optional.ofNullable(resultSet.getString("note"));
				
				final Garbage rifiuto = new Garbage(materiale, tipo, kgImagazzinati, note);
				rifiuti.add(rifiuto);
			}
		} catch (final SQLException e) {}
		return rifiuti;
	}

	public boolean save(Garbage rifiuto) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(materiale, tipo, kgImagazzinati, note) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, rifiuto.getMateriale());
            statement.setString(2, rifiuto.getTipo());
            statement.setBigDecimal(3, rifiuto.getKgImagazzinati());
            statement.setString(4, rifiuto.getNote().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Garbage updatedRifiuto) {
		final String query = "UPDATE " + TABLE_NAME + " SET tipo = ?," + "kgImagazzinati = ?," + "note = ? "
				+ "WHERE materiale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedRifiuto.getTipo());
			statement.setBigDecimal(2, updatedRifiuto.getKgImagazzinati());
			statement.setString(3, updatedRifiuto.getNote().orElse(null));
			statement.setString(4, updatedRifiuto.getMateriale());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(String materiale) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE materiale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, materiale);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
