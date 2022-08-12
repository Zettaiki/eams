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

import model.Supply;
import utils.Utils;

public class SupplyTable {

	public static final String TABLE_NAME = "fornitura";

	private final Connection connection;

	public SupplyTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}
	
	public Optional<Supply> findByPrimaryKey(String idProdotto, BigDecimal partitaIVA, Date data) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idProdotto = ? AND partitaIVA = ? " +
				"AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idProdotto);
            statement.setBigDecimal(2, partitaIVA);
            statement.setDate(3, Utils.dateToSqlDate(data));
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Supply> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Supply> readFromResultSet(ResultSet resultSet) {
		final List<Supply> forniture = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idProdotto = resultSet.getString("idProdotto");
				final BigDecimal partitaIVA = resultSet.getBigDecimal("partitaIVA");
				final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
				final Integer quantitàFornita = resultSet.getInt("quantitàFornita");
				
				final Supply fornitura = new Supply(idProdotto, partitaIVA, data, quantitàFornita);
				forniture.add(fornitura);
			}
		} catch (final SQLException e) {}
		return forniture;
	}

	public boolean save(Supply fornitura) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idProdotto, partitaIVA, data, quantitàFornita) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, fornitura.getIdProdotto());
            statement.setBigDecimal(2, fornitura.getPartitaIVA());
            statement.setDate(3, Utils.dateToSqlDate(fornitura.getData()));
            statement.setInt(4, fornitura.getQuantitàFornita());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Supply updatedFornitura) {
		final String query = "UPDATE " + TABLE_NAME + " SET quantitàFornita = ? "
				+ "WHERE idProdotto = ? AND partitaIVA = ? AND data = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, updatedFornitura.getQuantitàFornita());
			statement.setString(2, updatedFornitura.getIdProdotto());
			statement.setBigDecimal(3, updatedFornitura.getPartitaIVA());
			statement.setDate(4, Utils.dateToSqlDate(updatedFornitura.getData()));
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(String idProdotto, BigDecimal partitaIVA, Date data) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idProdotto = ? AND partitaIVA = ? " +
				"AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idProdotto);
            statement.setBigDecimal(2, partitaIVA);
            statement.setDate(3, Utils.dateToSqlDate(data));
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
