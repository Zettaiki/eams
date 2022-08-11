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

import db.TableTriplePk;
import model.Production;

public class ProductionTable implements TableTriplePk<Production, String, String, Integer> {

	public static final String TABLE_NAME = "produzione";

	private final Connection connection;

	public ProductionTable(final Connection connection) {
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
            			"idServizio CHAR(20) NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"idProdotto INT NOT NULL," +
            			"quantitàProdotta INT NOT NULL," +
            			"materialeUsato VARCHAR(30) NOT NULL," +
            			"kgRifiutiUsati DECIMAL(8,2) NOT NULL," +
            			"PRIMARY KEY (idServizio, idEvento, materialeUsato, idProdotto)," +
            			"FOREIGN KEY (materialeUsato) REFERENCES rifiuto (materiale) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (idServizio, idEvento) REFERENCES servizio (idServizio, idEvento)" +
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
	public Optional<Production> findByPrimaryKey(String idServizio, String idEvento, Integer idProdotto) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idServizio = ? AND idEvento = ? " +
				"AND idProdotto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idServizio);
            statement.setString(2, idEvento);
            statement.setInt(3, idProdotto);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Production> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Production> readFromResultSet(ResultSet resultSet) {
		final List<Production> produzioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idServizio = resultSet.getString("idServizio");
			    final String idEvento = resultSet.getString("idEvento");
			    final Integer idProdotto = resultSet.getInt("idProdotto");
			    final Integer quantitàProdotta = resultSet.getInt("quantitàProdotta");
			    final String materialeUsato = resultSet.getString("materialeUsato");
			    final BigDecimal kgRifiutiUsati = resultSet.getBigDecimal("kgRifiutiUsati");
				
				final Production produzione = new Production(idServizio, idEvento, idProdotto, quantitàProdotta,
						materialeUsato, kgRifiutiUsati);
				produzioni.add(produzione);
			}
		} catch (final SQLException e) {}
		return produzioni;
	}

	@Override
	public boolean save(Production produzione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idServizio, idEvento, idProdotto, quantitàProdotta, " +
				"materialeUsato, kgRifiutiUsati) VALUES (?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, produzione.getIdServizio());
            statement.setString(2, produzione.getIdEvento());
            statement.setInt(3, produzione.getIdProdotto());
            statement.setInt(4, produzione.getQuantitàProdotta());
            statement.setString(5, produzione.getMaterialeUsato());
            statement.setBigDecimal(6, produzione.getKgRifiutiUsati());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Production updatedProduzione) {
		final String query = "UPDATE " + TABLE_NAME + " SET quantitàProdotta = ?," + "materialeUsato = ?," +
				"kgRifiutiUsati = ? WHERE idServizio = ? AND idEvento = ? AND idProdotto = ?,";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, updatedProduzione.getQuantitàProdotta());
			statement.setString(2, updatedProduzione.getMaterialeUsato());
			statement.setBigDecimal(3, updatedProduzione.getKgRifiutiUsati());
			statement.setString(4, updatedProduzione.getIdServizio());
			statement.setString(5, updatedProduzione.getIdEvento());
			statement.setInt(6, updatedProduzione.getIdProdotto());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String idServizio, String idEvento, Integer idProdotto) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idServizio = ? AND idEvento = ? " +
				"AND idProdotto = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idServizio);
            statement.setString(2, idEvento);
            statement.setInt(3, idProdotto);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
