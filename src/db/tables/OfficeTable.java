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

import model.Office;

public class OfficeTable {

	public static final String TABLE_NAME = "sede";

	private final Connection connection;

	public OfficeTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Office> findByPrimaryKey(String città) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE città = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, città);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Office> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Office> readFromResultSet(ResultSet resultSet) {
		final List<Office> sedi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String città = resultSet.getString("città");
				final String indirizzo = resultSet.getString("indirizzo");
				final String regione = resultSet.getString("regione");
				final String codicePostale = resultSet.getString("codicePostale");
				final String telefono = resultSet.getString("telefono");
				
				final Office sede = new Office(città, indirizzo, regione, codicePostale, telefono);
				sedi.add(sede);
			}
		} catch (final SQLException e) {}
		return sedi;
	}

	public boolean save(Office sede) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(città, indirizzo, regione, codicePostale, telefono) VALUES (?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, sede.getCittà());
            statement.setString(2, sede.getIndirizzo());
            statement.setString(3, sede.getRegione());
            statement.setString(4, sede.getCodicePostale());
            statement.setString(5, sede.getTelefono());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Office updatedSede) {
		final String query = "UPDATE " + TABLE_NAME + " SET indirizzo = ?," + "regione = ?,"
				+ "codicePostale = ?," + "telefono = ? "
				+ "WHERE città = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedSede.getIndirizzo());
			statement.setString(2, updatedSede.getRegione());
			statement.setString(3, updatedSede.getCodicePostale());
			statement.setString(4, updatedSede.getTelefono());
			statement.setString(5, updatedSede.getCittà());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(String città) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE città = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, città);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
