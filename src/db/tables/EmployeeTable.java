package db.tables;

import java.math.BigDecimal;
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
import model.Employee;
import utils.Utils;

public class EmployeeTable implements Table<Employee, String> {

	public static final String TABLE_NAME = "dipendente";

	private final Connection connection;

	public EmployeeTable(final Connection connection) {
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
            			"sedeCittą VARCHAR(25) NOT NULL," +
            			"dataAssunzione DATE NOT NULL," +
            			"salario DECIMAL(8,2) NOT NULL," +
            			"FOREIGN KEY (codiceFiscale) REFERENCES persona (codiceFiscale) ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (sedeCittą) REFERENCES sede (cittą) ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Employee> findByPrimaryKey(String codiceFiscale) {
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
	public List<Employee> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Employee> readFromResultSet(ResultSet resultSet) {
		final List<Employee> volontari = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final String sedeCittą = resultSet.getString("sedeCittą");
				final Date dataAssunzione = Utils.sqlDateToDate(resultSet.getDate("dataAssunzione"));
				final BigDecimal salario = resultSet.getBigDecimal("salario");
				
				final Employee Dipendente = new Employee(codiceFiscale, sedeCittą, dataAssunzione, salario);
				volontari.add(Dipendente);
			}
		} catch (final SQLException e) {}
		return volontari;
	}

	@Override
	public boolean save(Employee Dipendente) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, sedeCittą, dataAssunzione, salario) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, Dipendente.getCodiceFiscale());
            statement.setString(2, Dipendente.getSedeCittą());
            statement.setDate(3, Utils.dateToSqlDate(Dipendente.getDataAssunzione()));
            statement.setBigDecimal(4, Dipendente.getSalario());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Employee updatedDipendente) {
		final String query = "UPDATE " + TABLE_NAME + " SET sedeCittą = ?," + "dataAssunzione = ?," + "salario = ? "
				+ "WHERE codiceFiscale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedDipendente.getSedeCittą());
			statement.setDate(2, Utils.dateToSqlDate(updatedDipendente.getDataAssunzione()));
			statement.setBigDecimal(3, updatedDipendente.getSalario());
			statement.setString(4, updatedDipendente.getCodiceFiscale());
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
