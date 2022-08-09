package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Volunteer;
import utils.Utils;

public class VolunteerTable implements Table<Volunteer, String> {

	public static final String TABLE_NAME = "volontario";

	private final Connection connection;

	public VolunteerTable(final Connection connection) {
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
            			"codiceFiscale CHAR(16) NOT NULL PRIMARY KEY," +
            			"sedeCittà VARCHAR(25) NOT NULL," +
            			"dataIscrizione DATE NOT NULL," +
            			"FOREIGN KEY (codiceFiscale) REFERENCES persona (codiceFiscale) ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (sedeCittà) REFERENCES sede (città) ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Volunteer> findByPrimaryKey(String codiceFiscale) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Volunteer> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Volunteer> readFromResultSet(ResultSet resultSet) {
		final List<Volunteer> volontari = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final String sedeCittà = resultSet.getString("sedeCittà");
				final Date dataIscrizione = Utils.sqlDateToDate(resultSet.getDate("dataIscrizione"));
				
				final Volunteer volontario = new Volunteer(codiceFiscale, sedeCittà, dataIscrizione);
				volontari.add(volontario);
			}
		} catch (final SQLException e) {}
		return volontari;
	}

	@Override
	public boolean save(Volunteer volontario) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, sedeCittà, dataIscrizione) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, volontario.getCodiceFiscale());
            statement.setString(2, volontario.getSedeCittà());
            statement.setDate(3, Utils.dateToSqlDate(volontario.getDataIscrizione()));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Volunteer updatedVolontario) {
		final String query = "UPDATE " + TABLE_NAME + " SET sedeCittà = ?," + "dataIscrizione = ? "
				+ "WHERE codiceFiscale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedVolontario.getSedeCittà());
			statement.setDate(2, Utils.dateToSqlDate(updatedVolontario.getDataIscrizione()));
			statement.setString(3, updatedVolontario.getCodiceFiscale());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String codiceFiscale) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
