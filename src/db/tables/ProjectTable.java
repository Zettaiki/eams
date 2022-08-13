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

import model.Project;
import utils.Utils;

public class ProjectTable {

	public static final String TABLE_NAME = "progetto";

	private final Connection connection;

	public ProjectTable(final Connection connection) {
		this.connection = Objects.requireNonNull(connection);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Project> findByPrimaryKey(Integer idProgetto) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idProgetto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idProgetto);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Project> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Project> readFromResultSet(ResultSet resultSet) {
		final List<Project> progetti = new ArrayList<>();
		try {
			while (resultSet.next()) {
			    final String obbiettivo = resultSet.getString("obbiettivo");
			    final Date dataInizio = Utils.sqlDateToDate(resultSet.getDate("dataInizio"));
			    final Integer durataMesi = resultSet.getInt("durataMesi");
			    final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
				
				final Project progetto = new Project(obbiettivo, dataInizio, durataMesi, descrizione);
				progetti.add(progetto);
			}
		} catch (final SQLException e) {}
		return progetti;
	}

	public boolean save(Project progetto) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(obbiettivo, dataInizio, durataMesi, descrizione) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, progetto.getObbiettivo());
			statement.setDate(2, Utils.dateToSqlDate(progetto.getDataInizio()));
			statement.setInt(3, progetto.getDurataMesi());
			statement.setString(4, progetto.getDescrizione().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Project updatedProgetto) {
		final String query = "UPDATE " + TABLE_NAME + " SET obbiettivo = ?, dataInizio = ?, durataMesi = ?,"
				+ "descrizione = ? WHERE idProgetto = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedProgetto.getObbiettivo());
			statement.setDate(2, Utils.dateToSqlDate(updatedProgetto.getDataInizio()));
			statement.setInt(3, updatedProgetto.getDurataMesi());
			statement.setString(4, updatedProgetto.getDescrizione().orElse(null));
			statement.setInt(5, updatedProgetto.getIdProgetto());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(Integer idProgetto) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idProgetto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idProgetto);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
