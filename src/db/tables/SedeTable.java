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

import db.Table;
import model.Sede;

public class SedeTable implements Table<Sede, String> {

	public static final String TABLE_NAME = "tesserasocio";

	private final Connection connection;

	public SedeTable(final Connection connection) {
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
            			"città VARCHAR(25) NOT NULL PRIMARY KEY," +
            			"indirizzo VARCHAR(60) NOT NULL," +
            			"regione VARCHAR(20) NOT NULL," +
            			"codicePostale VARCHAR(10) NOT NULL," +
            			"telefono VARCHAR(24) NOT NULL" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Sede> findByPrimaryKey(String città) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE città = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, città);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Sede> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Sede> readFromResultSet(ResultSet resultSet) {
		final List<Sede> sedi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String città = resultSet.getString("città");
				final String indirizzo = resultSet.getString("indirizzo");
				final String regione = resultSet.getString("regione");
				final String codicePostale = resultSet.getString("codicePostale");
				final String telefono = resultSet.getString("telefono");
				
				final Sede sede = new Sede(città, indirizzo, regione, codicePostale, telefono);
				sedi.add(sede);
			}
		} catch (final SQLException e) {}
		return sedi;
	}

	@Override
	public boolean save(Sede sede) {
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

	@Override
	public boolean update(Sede updatedSede) {
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

	@Override
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
