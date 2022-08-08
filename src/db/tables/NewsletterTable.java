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
import model.Newsletter;

public class NewsletterTable implements Table<Newsletter, Integer> {

	public static final String TABLE_NAME = "newsletter";

	private final Connection connection;

	public NewsletterTable(final Connection connection) {
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
            			"idNewsletter INT NOT NULL PRIMARY KEY," +
            			"argomento VARCHAR(45) NOT NULL," +
            			"descrizione MEDIUMTEXT NULL DEFAULT NULL" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Newsletter> findByPrimaryKey(Integer idNewsletter) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idNewsletter = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idNewsletter);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Newsletter> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Newsletter> readFromResultSet(ResultSet resultSet) {
		final List<Newsletter> newsletters = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Integer idNewsletter = resultSet.getInt("idNewsletter");
				final String argomento = resultSet.getString("argomento");
			    final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
			    
				final Newsletter newsletter = new Newsletter(idNewsletter, argomento, descrizione);
				newsletters.add(newsletter);
			}
		} catch (final SQLException e) {}
		return newsletters;
	}

	@Override
	public boolean save(Newsletter newsletter) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idNewsletter, argomento, descrizione) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, newsletter.getIdNewsletter());
            statement.setString(2, newsletter.getArgomento());
            statement.setString(3, newsletter.getDescrizione().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Newsletter updatedNewsletter) {
		final String query = "UPDATE " + TABLE_NAME + " SET argomento = ?," + "descrizione = ? "
				+ "WHERE idNewsletter = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedNewsletter.getArgomento());
			statement.setString(2, updatedNewsletter.getDescrizione().orElse(null));
			statement.setInt(3, updatedNewsletter.getIdNewsletter());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(Integer idNewsletter) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idNewsletter = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idNewsletter);
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
