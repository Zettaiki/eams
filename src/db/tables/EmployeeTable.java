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

import model.Employee;
import utils.Utils;

public class EmployeeTable {

	public static final String TABLE_NAME = "dipendente";

	private final Connection connection;

	public EmployeeTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}
	
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

	public List<Employee> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Employee> readFromResultSet(ResultSet resultSet) {
		final List<Employee> volontari = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final String sedeCittà = resultSet.getString("sedeCittà");
				final Date dataAssunzione = Utils.sqlDateToDate(resultSet.getDate("dataAssunzione"));
				final BigDecimal salario = resultSet.getBigDecimal("salario");
				
				final Employee Dipendente = new Employee(codiceFiscale, sedeCittà, dataAssunzione, salario);
				volontari.add(Dipendente);
			}
		} catch (final SQLException e) {}
		return volontari;
	}

	public boolean save(Employee Dipendente) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, sedeCittà, dataAssunzione, salario) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, Dipendente.getCodiceFiscale());
            statement.setString(2, Dipendente.getSedeCittà());
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

	public boolean update(Employee updatedDipendente) {
		final String query = "UPDATE " + TABLE_NAME + " SET sedeCittà = ?," + "dataAssunzione = ?," + "salario = ? "
				+ "WHERE codiceFiscale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedDipendente.getSedeCittà());
			statement.setDate(2, Utils.dateToSqlDate(updatedDipendente.getDataAssunzione()));
			statement.setBigDecimal(3, updatedDipendente.getSalario());
			statement.setString(4, updatedDipendente.getCodiceFiscale());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

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
