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

import db.TableTriplePk;
import model.Supply;
import utils.Utils;

public class SupplyTable implements TableTriplePk<Supply, Integer, BigDecimal, Date> {

	public static final String TABLE_NAME = "fornitura";

	private final Connection connection;

	public SupplyTable(final Connection connection) {
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
            			"idProdotto INT NOT NULL," +
            			"partitaIVA DECIMAL(11) NOT NULL," +
            			"data DATETIME NOT NULL," +
            			"quantit‡Fornita INT NOT NULL," +
            			"PRIMARY KEY (idProdotto, partitaIVA, data)," +
            			"FOREIGN KEY (partitaIVA) REFERENCES azienda (partitaIVA) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (idProdotto) REFERENCES prodotto (idProdotto) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Supply> findByPrimaryKey(Integer idProdotto, BigDecimal partitaIVA, Date data) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idProdotto = ? AND partitaIVA = ? " +
				"AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idProdotto);
            statement.setBigDecimal(2, partitaIVA);
            statement.setDate(3, Utils.dateToSqlDate(data));
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Supply> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Supply> readFromResultSet(ResultSet resultSet) {
		final List<Supply> forniture = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Integer idProdotto = resultSet.getInt("idProdotto");
				final BigDecimal partitaIVA = resultSet.getBigDecimal("partitaIVA");
				final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
				final Integer quantit‡Fornita = resultSet.getInt("quantit‡Fornita");
				
				final Supply fornitura = new Supply(idProdotto, partitaIVA, data, quantit‡Fornita);
				forniture.add(fornitura);
			}
		} catch (final SQLException e) {}
		return forniture;
	}

	@Override
	public boolean save(Supply fornitura) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idProdotto, partitaIVA, data, quantit‡Fornita) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, fornitura.getIdProdotto());
            statement.setBigDecimal(2, fornitura.getPartitaIVA());
            statement.setDate(3, Utils.dateToSqlDate(fornitura.getData()));
            statement.setInt(4, fornitura.getQuantit‡Fornita());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Supply updatedFornitura) {
		final String query = "UPDATE " + TABLE_NAME + " SET quantit‡Fornita = ? "
				+ "WHERE idProdotto = ? AND partitaIVA = ? AND data = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, updatedFornitura.getQuantit‡Fornita());
			statement.setInt(2, updatedFornitura.getIdProdotto());
			statement.setBigDecimal(3, updatedFornitura.getPartitaIVA());
			statement.setDate(4, Utils.dateToSqlDate(updatedFornitura.getData()));
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(Integer idProdotto, BigDecimal partitaIVA, Date data) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idProdotto = ? AND partitaIVA = ? " +
				"AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idProdotto);
            statement.setBigDecimal(2, partitaIVA);
            statement.setDate(3, Utils.dateToSqlDate(data));
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
