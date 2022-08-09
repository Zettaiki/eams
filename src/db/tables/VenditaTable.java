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

import db.Table;
import model.Vendita;
import utils.Utils;

public class VenditaTable implements Table<Vendita, String> {

	public static final String TABLE_NAME = "fornitura";

	private final Connection connection;

	public VenditaTable(final Connection connection) {
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
            			"data DATETIME NOT NULL," +
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
	public Optional<Vendita> findByPrimaryKey(String codiceFiscaleCliente) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleCliente = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleCliente);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Vendita> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Vendita> readFromResultSet(ResultSet resultSet) {
		final List<Vendita> vendite = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleCliente = resultSet.getString("codiceFiscaleCliente");
			    final Integer idProdotto = resultSet.getInt("idProdotto");
			    final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
			    final Integer quantità = resultSet.getInt("quantità");
			    final String idEvento = resultSet.getString("idEvento");
			    final Time oraInizioServizio = resultSet.getTime("oraInizioServizio");
				
				final Vendita vendita = new Vendita(codiceFiscaleCliente, idProdotto, data, quantità, idEvento, oraInizioServizio);
				vendite.add(vendita);
			}
		} catch (final SQLException e) {}
		return vendite;
	}

	@Override
	public boolean save(Vendita vendita) {
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
	public boolean update(Vendita updatedVendita) {
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
	public boolean delete(String codiceFiscaleCliente) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleCliente = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleCliente);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
