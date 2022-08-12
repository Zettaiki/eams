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

import model.Subscription;

public class SubscriptionTable {
	
	public static final String TABLE_NAME = "iscrizione";

	private final Connection connection;

	public SubscriptionTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Subscription> findByPrimaryKey(String codiceFiscale, Integer idNewsletter) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscale = ? AND idNewsletter = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            statement.setInt(2, idNewsletter);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Subscription> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Subscription> readFromResultSet(ResultSet resultSet) {
		final List<Subscription> newsletters = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final Integer idNewsletter = resultSet.getInt("idNewsletter");
			    
				final Subscription Iscrizione = new Subscription(codiceFiscale, idNewsletter);
				newsletters.add(Iscrizione);
			}
		} catch (final SQLException e) {}
		return newsletters;
	}

	public boolean save(Subscription Iscrizione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, idNewsletter) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, Iscrizione.getCodiceFiscale());
            statement.setInt(2, Iscrizione.getIdNewsletter());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
        	System.out.println(e.toString());
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean delete(String codiceFiscale, Integer idNewsletter) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscale = ? AND idNewsletter = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            statement.setInt(2, idNewsletter);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
}
