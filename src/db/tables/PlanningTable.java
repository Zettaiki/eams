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

import model.Planning;

public class PlanningTable {

	public static final String TABLE_NAME = "organizzazione";

	private final Connection connection;

	public PlanningTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Planning> findByPrimaryKey(String codiceFiscaleDipendente, String idEvento) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleDipendente = ? AND idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleDipendente);
            statement.setString(2, idEvento);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Planning> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Planning> readFromResultSet(ResultSet resultSet) {
		final List<Planning> organizzazioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleDipendente = resultSet.getString("codiceFiscaleDipendente");
				final String idEvento = resultSet.getString("idEvento");
				
				final Planning organizzazione = new Planning(codiceFiscaleDipendente, idEvento);
				organizzazioni.add(organizzazione);
			}
		} catch (final SQLException e) {}
		return organizzazioni;
	}

	public boolean save(Planning organizzazione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				" (codiceFiscaleDipendente, idEvento) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, organizzazione.getCodiceFiscaleDipendente());
            statement.setString(2, organizzazione.getIdEvento());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean delete(String codiceFiscaleDipendente, String idEvento) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleDipendente = ? AND idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleDipendente);
            statement.setString(2, idEvento);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
