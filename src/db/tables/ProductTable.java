package db.tables;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Product;

public class ProductTable {


	public static final String TABLE_NAME = "prodotto";
    
    private final Connection connection;
    
    public ProductTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
    
	public String getTableName() {
		return TABLE_NAME;
	}

	// show
	public List<Product> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Product> readFromResultSet(ResultSet resultSet) {
		final List<Product> prodotti = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idProdotto = resultSet.getString("idProdotto");
				final String categoria = resultSet.getString("categoria");
				final String nome = resultSet.getString("nome");
				final BigDecimal prezzo = resultSet.getBigDecimal("prezzo");
				final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
								
				final Product prodotto = new Product(idProdotto, categoria, nome, prezzo, descrizione);
				prodotti.add(prodotto);
			}
		} catch (final SQLException e) {}
		return prodotti;
	}

}
