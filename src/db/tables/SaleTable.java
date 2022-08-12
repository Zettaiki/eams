package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Sale;

public class SaleTable {

	public static final String TABLE_NAME = "fornitura";

	private final Connection connection;

	public SaleTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Sale> findByPrimaryKey(String idProdotto, String idServizio, String codiceFiscaleCliente) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idProdotto = ? AND idServizio = ? "
				+ "AND codiceFiscaleCliente = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(2, idProdotto);
            statement.setString(1, idServizio);
            statement.setString(3, codiceFiscaleCliente);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Sale> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Sale> readFromResultSet(ResultSet resultSet) {
		final List<Sale> vendite = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idProdotto = resultSet.getString("idProdotto");
				final String idServizio = resultSet.getString("idServizio");
				final String codiceFiscaleCliente = resultSet.getString("codiceFiscaleCliente");
			    final Integer quantità = resultSet.getInt("quantità");
				
				final Sale vendita = new Sale(idProdotto, idServizio, codiceFiscaleCliente, quantità);
				vendite.add(vendita);
			}
		} catch (final SQLException e) {}
		return vendite;
	}

	public boolean save(Sale vendita) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idProdotto, idServizio, codiceFiscaleCliente, quantità) " +
				"VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, vendita.getIdProdotto());
            statement.setString(2, vendita.getIdServizio());
            statement.setString(3, vendita.getCodiceFiscaleCliente());
            statement.setInt(4, vendita.getQuantità());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
        	System.out.println(e.toString());
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Sale updatedVendita) {
		final String query = "UPDATE " + TABLE_NAME + " SET quantità = ? " +
				 "WHERE idProdotto = ? AND idServizio = ? AND codiceFiscaleCliente = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, updatedVendita.getQuantità());
            statement.setString(2, updatedVendita.getIdProdotto());
            statement.setString(3, updatedVendita.getIdServizio());
            statement.setString(4, updatedVendita.getCodiceFiscaleCliente());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean delete(String idProdotto, String idServizio, String codiceFiscaleCliente) {
		final String query = "DELETE FROM " + TABLE_NAME
				+ " WHERE  idProdotto = ? AND idServizio = ? AND codiceFiscaleCliente = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(2, idProdotto);
			statement.setString(3, idServizio);
            statement.setString(1, codiceFiscaleCliente);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
