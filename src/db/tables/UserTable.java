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

import model.User;

public class UserTable {
	
	public static final String TABLE_NAME = "utente";

	private final Connection connection;

	public UserTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}
	
	public Optional<User> findByPrimaryKey(String codiceFiscaleDipendente) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleDipendente = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleDipendente);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public Optional<User> findByUsername(String username) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, username);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<User> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<User> readFromResultSet(ResultSet resultSet) {
		final List<User> volontari = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleDipendente = resultSet.getString("codiceFiscaleDipendente");
				final String username = resultSet.getString("username");
				final String password = resultSet.getString("password");
				
				final User User = new User(codiceFiscaleDipendente, username, password);
				volontari.add(User);
			}
		} catch (final SQLException e) {}
		return volontari;
	}

	public boolean save(User user) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscaleDipendente, username, password) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user.getCodiceFiscaleDipendente());
            statement.setString(2, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(User updatedUser) {
		final String query = "UPDATE " + TABLE_NAME + " SET username = ?," + "password = ?,"
				+ "WHERE codiceFiscaleDipendente = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedUser.getUsername());
			statement.setString(2, updatedUser.getPassword());
			statement.setString(3, updatedUser.getCodiceFiscaleDipendente());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(String codiceFiscaleDipendente) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleDipendente = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleDipendente);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
}
