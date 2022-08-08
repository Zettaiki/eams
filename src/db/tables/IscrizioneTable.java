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
import model.Iscrizione;

public class IscrizioneTable implements Table<Iscrizione, String> {
	
	public static final String TABLE_NAME = "iscrizione";

	private final Connection connection;

	public IscrizioneTable(final Connection connection) {
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
            			"codiceFiscale` CHAR(16) NOT NULL," +
            			"idNewsletter INT NOT NULL," +
            			"PRIMARY KEY (`codiceFiscale`, `newsletterID`)," +
            			"FOREIGN KEY (`newsletterID`) REFERENCES newsletter (idNewsletter) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (`codiceFiscale`) REFERENCES persona (codiceFiscale) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE)" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Iscrizione> findByPrimaryKey(String codiceFiscale) {
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
	public List<Iscrizione> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Iscrizione> readFromResultSet(ResultSet resultSet) {
		final List<Iscrizione> newsletters = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final Integer idNewsletter = resultSet.getInt("idNewsletter");
			    
				final Iscrizione Iscrizione = new Iscrizione(codiceFiscale, idNewsletter);
				newsletters.add(Iscrizione);
			}
		} catch (final SQLException e) {}
		return newsletters;
	}

	@Override
	public boolean save(Iscrizione Iscrizione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, idNewsletter) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, Iscrizione.getCodiceFiscale());
            statement.setInt(2, Iscrizione.getIdNewsletter());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Iscrizione updatedIscrizione) {
		final String query = "UPDATE " + TABLE_NAME + " SET idNewsletter = ?, WHERE codiceFiscale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, updatedIscrizione.getIdNewsletter());
			statement.setString(2, updatedIscrizione.getCodiceFiscale());
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
	
	public boolean dropTable() {
		try (final Statement statement = this.connection.createStatement()) {			
			statement.executeUpdate("SET foreign_key_checks = 0;");
			statement.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);            
            statement.executeUpdate("SET foreign_key_checks = 1;");
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}
}