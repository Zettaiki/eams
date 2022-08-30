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

import model.Newsletter;

public class NewsletterTable {

	public static final String TABLE_NAME = "newsletter";

	private final Connection connection;

	public NewsletterTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	// show
	public List<Newsletter> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Newsletter> readFromResultSet(ResultSet resultSet) {
		final List<Newsletter> newsletters = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Optional<Integer> idNewsletter = Optional.of(resultSet.getInt("idNewsletter"));
				final String argomento = resultSet.getString("argomento");
			    final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
			    
				final Newsletter newsletter = new Newsletter(idNewsletter, argomento, descrizione);
				newsletters.add(newsletter);
			}
		} catch (final SQLException e) {}
		return newsletters;
	}

	// query
	public boolean save(Newsletter newsletter) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(argomento, descrizione) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, newsletter.getArgomento());
            statement.setString(2, newsletter.getDescrizione().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
}
