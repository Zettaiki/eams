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

import model.Delivery;
import utils.Utils;

public class DeliveryTable {

	public static final String TABLE_NAME = "consegna";

	private final Connection connection;

	public DeliveryTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	public boolean createTable() {
		try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            			"materiale VARCHAR(30) NOT NULL," +
            			"partitaIVA DECIMAL(11) NOT NULL," +
            			"data DATE NOT NULL," +
            			"kgConsegnati DECIMAL(11,2) NOT NULL," +
            			"PRIMARY KEY (materiale, partitaIVA, data)," +
            			"FOREIGN KEY (partitaIVA) REFERENCES azienda (partitaIVA) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (materiale) REFERENCES rifiuto (materiale) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	public Optional<Delivery> findByPrimaryKey(String materiale, BigDecimal partitaIVA, Date data) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE materiale = ? AND partitaIVA = ? "
				+ "AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, materiale);
            statement.setBigDecimal(2, partitaIVA);
            statement.setDate(3, Utils.dateToSqlDate(data));
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Delivery> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Delivery> readFromResultSet(ResultSet resultSet) {
		final List<Delivery> consegne = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String materiale = resultSet.getString("materiale");
				final BigDecimal partitaIVA = resultSet.getBigDecimal("partitaIVA");
				final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
				final BigDecimal kgConsegnati = resultSet.getBigDecimal("kgConsegnati");
				
				final Delivery consegna = new Delivery(materiale, partitaIVA, data, kgConsegnati);
				consegne.add(consegna);
			}
		} catch (final SQLException e) {}
		return consegne;
	}

	public boolean save(Delivery consegna) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(materiale, partitaIVA, data, kgConsegnati) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, consegna.getMateriale());
            statement.setBigDecimal(2, consegna.getPartitaIVA());
            statement.setDate(3, Utils.dateToSqlDate(consegna.getData()));
            statement.setBigDecimal(4, consegna.getKgConsegnati());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Delivery updatedConsegna) {
		final String query = "UPDATE " + TABLE_NAME + " SET kgConsegnati = ? "
				+ "WHERE materiale = ? AND partitaIVA = ? AND data = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setBigDecimal(1, updatedConsegna.getKgConsegnati());
			statement.setString(2, updatedConsegna.getMateriale());
			statement.setBigDecimal(3, updatedConsegna.getPartitaIVA());
			statement.setDate(4, Utils.dateToSqlDate(updatedConsegna.getData()));
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(String materiale, BigDecimal partitaIVA, Date data) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE materiale = ? AND partitaIVA = ? " +
				"AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, materiale);
            statement.setBigDecimal(2, partitaIVA);
            statement.setDate(3, Utils.dateToSqlDate(data));
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
