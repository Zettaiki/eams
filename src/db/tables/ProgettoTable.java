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
import model.Progetto;
import utils.Utils;

public class ProgettoTable implements Table<Progetto, Integer> {

	public static final String TABLE_NAME = "donazione";

	private final Connection connection;

	public ProgettoTable(final Connection connection) {
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
					"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
					+ "idProgetto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
					+ "obbiettivo VARCHAR(45) NOT NULL,"
					+ "dataInizio DATETIME NOT NULL,"
					+ "durataMesi INT NOT NULL"	+
					")");
			return true;
		} catch (final SQLException e) {
			return false;
		}
	}

	@Override
	public Optional<Progetto> findByPrimaryKey(Integer idProgetto) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idProgetto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idProgetto);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Progetto> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Progetto> readFromResultSet(ResultSet resultSet) {
		final List<Progetto> progetti = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Integer idProgetto = resultSet.getInt("idProgetto");
			    final String obbiettivo = resultSet.getString("obbiettivo");
			    final Date dataInizio = Utils.sqlDateToDate(resultSet.getDate("dataInizio"));
			    final Integer durataMesi = resultSet.getInt("durataMesi");
				
				final Progetto progetto = new Progetto(idProgetto, obbiettivo, dataInizio, durataMesi);
				progetti.add(progetto);
			}
		} catch (final SQLException e) {}
		return progetti;
	}

	@Override
	public boolean save(Progetto progetto) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idProgetto, obbiettivo, dataInizio, durataMesi) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setInt(1, progetto.getIdProgetto());
        	statement.setString(2, progetto.getObbiettivo());
			statement.setDate(3, Utils.dateToSqlDate(progetto.getDataInizio()));
			statement.setInt(4, progetto.getDurataMesi());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Progetto updatedProgetto) {
		final String query = "UPDATE " + TABLE_NAME + " SET " + "obbiettivo = ?," + "dataInizio = ?, " + "durataMesi = ? "
				+ "WHERE idProgetto = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedProgetto.getObbiettivo());
			statement.setDate(2, Utils.dateToSqlDate(updatedProgetto.getDataInizio()));
			statement.setInt(3, updatedProgetto.getDurataMesi());
			statement.setInt(4, updatedProgetto.getIdProgetto());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(Integer idProgetto) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idProgetto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idProgetto);
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
