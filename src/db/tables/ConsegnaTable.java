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
import model.Consegna;
import utils.Utils;

public class ConsegnaTable implements Table<Consegna, String> {

	public static final String TABLE_NAME = "consegna";

	private final Connection connection;

	public ConsegnaTable(final Connection connection) {
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
            			"materiale VARCHAR(30) NOT NULL," +
            			"partitaIVA DECIMAL(11) NOT NULL," +
            			"data DATETIME NOT NULL," +
            			"kgConsegnati` DECIMAL(11,2) NOT NULL," +
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

	@Override
	public Optional<Consegna> findByPrimaryKey(String materiale) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE materiale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, materiale);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Consegna> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Consegna> readFromResultSet(ResultSet resultSet) {
		final List<Consegna> consegne = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String materiale = resultSet.getString("materiale");
				final BigDecimal partitaIVA = resultSet.getBigDecimal("partitaIVA");
				final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
				final BigDecimal kgConsegnati = resultSet.getBigDecimal("kgConsegnati");
				
				final Consegna consegna = new Consegna(materiale, partitaIVA, data, kgConsegnati);
				consegne.add(consegna);
			}
		} catch (final SQLException e) {}
		return consegne;
	}

	@Override
	public boolean save(Consegna consegna) {
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

	@Override
	public boolean update(Consegna updatedConsegna) {
		final String query = "UPDATE " + TABLE_NAME + " SET partitaIVA = ?," + "data = ?," + "kgConsegnati = ? "
				+ "WHERE materiale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setBigDecimal(1, updatedConsegna.getPartitaIVA());
			statement.setDate(2, Utils.dateToSqlDate(updatedConsegna.getData()));
			statement.setBigDecimal(3, updatedConsegna.getKgConsegnati());
			statement.setString(4, updatedConsegna.getMateriale());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String materiale) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE materiale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, materiale);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
