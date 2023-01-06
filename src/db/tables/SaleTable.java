package db.tables;

import java.math.BigDecimal;
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

	public static final String TABLE_NAME = "vendita";

	private final Connection connection;

	public SaleTable(final Connection connection) {
		this.connection = Objects.requireNonNull(connection);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	// 16 CON RIDONDANZA
	public boolean save(Sale vendita) {
		final String query = "INSERT INTO " + TABLE_NAME +
				" (idProdotto, idServizio, codiceFiscaleCliente, quantità, prezzoVendita) " +
				"SELECT ?,?,?,?, MIN(CAST((FORMAT(((p.prezzo / 100) * (100 - IF(t.codiceFiscale = ?, 20, 0))*?), 5)) AS DECIMAL(8,2))) " +
				"FROM prodotto p, persona pe , servizio s, vendita v, tesserasocio t " +
				"WHERE p.idProdotto = ?" +
				"AND s.idServizio = ?" +
				"AND pe.codiceFiscale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, vendita.getIdProdotto());
			statement.setString(2, vendita.getIdServizio());
			statement.setString(3, vendita.getCodiceFiscaleCliente());
			statement.setInt(4, vendita.getQuantità());
			statement.setString(5, vendita.getCodiceFiscaleCliente());
			statement.setInt(6, vendita.getQuantità());
			statement.setString(7, vendita.getIdProdotto());
			statement.setString(8, vendita.getIdServizio());
			statement.setString(9, vendita.getCodiceFiscaleCliente());
			statement.executeUpdate();
			return true;
		} catch (final SQLIntegrityConstraintViolationException e) {
			return false;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	// 16 bis CON RIDONDANZA
	public List<Sale> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			return readFromResultSet(resultSet);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public List<Sale> readFromResultSet(ResultSet resultSet) {
		final List<Sale> sales = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idProdotto = resultSet.getString("idProdotto");
				final String idServizio = resultSet.getString("idServizio");
				final  String codiceFiscaleCliente = resultSet.getString("codiceFiscaleCliente");
			    final Integer quantità = resultSet.getInt("quantità");
			    final Optional<BigDecimal> prezzoVendita = Optional.ofNullable(resultSet.getBigDecimal("prezzoVendita"));
							    
				final Sale sale = new Sale(idProdotto, idServizio, codiceFiscaleCliente, quantità, prezzoVendita);
				sales.add(sale);
			}
		} catch (final SQLException e) {}
		return sales;
	}

}
