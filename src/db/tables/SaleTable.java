package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.TableTriplePk;
import model.Sale;
import utils.Utils;

public class SaleTable implements TableTriplePk<Sale, String, Integer, Date> {

	public static final String TABLE_NAME = "fornitura";

	private final Connection connection;

	public SaleTable(final Connection connection) {
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
            			"codiceFiscaleCliente CHAR(16) NOT NULL," +
            			"idProdotto INT NOT NULL," +
            			"data DATE NOT NULL," +
            			"quantità INT NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"oraInizioServizio TIME NOT NULL," +
            			"PRIMARY KEY (codiceFiscaleCliente, idProdotto, data)," +
            			"FOREIGN KEY (idProdotto) REFERENCES prodotto (idProdotto) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (idEvento, oraInizioServizio) REFERENCES servizio (idEvento, oraInizio) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Sale> findByPrimaryKey(String codiceFiscaleCliente, Integer idProdotto, Date data) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleCliente = ? AND idProdotto = ? "
				+ "AND data = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleCliente);
            statement.setInt(2, idProdotto);
            statement.setDate(3, Utils.dateToSqlDate(data));
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Sale> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Sale> readFromResultSet(ResultSet resultSet) {
		final List<Sale> vendite = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleCliente = resultSet.getString("codiceFiscaleCliente");
			    final Integer idProdotto = resultSet.getInt("idProdotto");
			    final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
			    final Integer quantità = resultSet.getInt("quantità");
			    final String idEvento = resultSet.getString("idEvento");
			    final Time oraInizioServizio = resultSet.getTime("oraInizioServizio");
				
				final Sale vendita = new Sale(codiceFiscaleCliente, idProdotto, data, quantità, idEvento, oraInizioServizio);
				vendite.add(vendita);
			}
		} catch (final SQLException e) {}
		return vendite;
	}

	@Override
	public boolean save(Sale vendita) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscaleCliente, idProdotto, data, quantità, idEvento, oraInizioServizio) " +
				"VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, vendita.getCodiceFiscaleCliente());
            statement.setInt(2, vendita.getIdProdotto());
            statement.setDate(3, Utils.dateToSqlDate(vendita.getData()));
            statement.setInt(4, vendita.getQuantità());
            statement.setString(5, vendita.getIdEvento());
            statement.setTime(6, vendita.getOraInizioServizio());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Sale updatedVendita) {
		final String query = "UPDATE " + TABLE_NAME + " SET idProdotto = ?," + "data = ?, quantità = ?," +
				 "idEvento = ?," + "oraInizioServizio = ? WHERE codiceFiscaleCliente = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, updatedVendita.getIdProdotto());
            statement.setDate(2, Utils.dateToSqlDate(updatedVendita.getData()));
            statement.setInt(3, updatedVendita.getQuantità());
            statement.setString(4, updatedVendita.getIdEvento());
            statement.setTime(5, updatedVendita.getOraInizioServizio());
            statement.setString(6, updatedVendita.getCodiceFiscaleCliente());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String codiceFiscaleCliente, Integer idProdotto, Date data) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleCliente = ? AND idProdotto = ? "
				+ "AND data = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleCliente);
            statement.setInt(2, idProdotto);
            statement.setDate(3, Utils.dateToSqlDate(data));
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
