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

import db.Table;
import model.Product;

public class ProductTable implements Table<Product, String> {


	public static final String TABLE_NAME = "prodotto";
    
    private final Connection connection;
    
    public ProductTable(final Connection connection) {
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
            			"idProdotto INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
            			"categoria VARCHAR(30) NOT NULL," +
            			"nome VARCHAR(30) NOT NULL," +
            			"prezzo DECIMAL(6,2) NOT NULL," +
            			"quantitàImmagazzinata INT NOT NULL," +
            			"provenienza VARCHAR(20) NOT NULL," +
            			"descrizione MEDIUMTEXT NULL DEFAULT NULL," +
            			"FOREIGN KEY (categoria) REFERENCES categoria (nome) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}

	@Override
	public Optional<Product> findByPrimaryKey(String idProdotto) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idProdotto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idProdotto);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Product> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Product> readFromResultSet(ResultSet resultSet) {
		final List<Product> prodotti = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idProdotto = resultSet.getString("idProdotto");
				final String categoria = resultSet.getString("categoria");
				final String nome = resultSet.getString("nome");
				final BigDecimal prezzo = resultSet.getBigDecimal("prezzo");
				final Integer quantitàImmagazzinata = resultSet.getInt("quantitàImmagazzinata");
				final String provenienza = resultSet.getString("provenienza");
				final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
								
				final Product prodotto = new Product(idProdotto, categoria, nome, prezzo, quantitàImmagazzinata, provenienza, descrizione);
				prodotti.add(prodotto);
			}
		} catch (final SQLException e) {}
		return prodotti;
	}

	@Override
	public boolean save(Product prodotto) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idProdotto, categoria, nome, prezzo, quantitàImmagazzinata, provenienza, descrizione)"
				+ " VALUES (?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, prodotto.getIdProdotto());
            statement.setString(2, prodotto.getCategoria());
            statement.setString(3, prodotto.getNome());
            statement.setBigDecimal(4, prodotto.getPrezzo());
            statement.setInt(5, prodotto.getQuantitàImmagazzinata());
            statement.setString(6, prodotto.getProvenienza());
            statement.setString(7, prodotto.getDescrizione().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Product updatedProdotto) {
		final String query = "UPDATE " + TABLE_NAME + " SET " + "categoria = ?," + "nome = ?," + "prezzo = ?,"
				+ "quantitàImmagazzinata = ?," + "provenienza = ?," + "descrizione = ? "
				+ "WHERE idProdotto = ?";
	        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
	            statement.setString(1, updatedProdotto.getCategoria());
	            statement.setString(2, updatedProdotto.getNome());
	            statement.setBigDecimal(3, updatedProdotto.getPrezzo());
	            statement.setInt(4, updatedProdotto.getQuantitàImmagazzinata());
	            statement.setString(5, updatedProdotto.getProvenienza());
	            statement.setString(6, updatedProdotto.getDescrizione().orElse(null));
	            statement.setString(7, updatedProdotto.getIdProdotto());
	            return statement.executeUpdate() > 0;
	        } catch (final SQLException e) {
	        	System.out.println(e.toString());
	            throw new IllegalStateException(e);
	        }
	}

	@Override
	public boolean delete(String idProdotto) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idProdotto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idProdotto);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
