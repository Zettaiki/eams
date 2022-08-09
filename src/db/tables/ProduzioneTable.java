package db.tables;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Produzione;

public class ProduzioneTable implements Table<Produzione, Time> {

	public static final String TABLE_NAME = "produzione";

	private final Connection connection;

	public ProduzioneTable(final Connection connection) {
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
            			"oraInizioServizio TIME NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"idProdotto INT NOT NULL," +
            			"quantit‡Prodotta INT NOT NULL," +
            			"materialeUsato VARCHAR(30) NOT NULL," +
            			"kgRifiutiUsati DECIMAL(8,2) NOT NULL," +
            			"PRIMARY KEY (oraInizioServizio, idEvento, materialeUsato, idProdotto)," +
            			"FOREIGN KEY (materialeUsato) REFERENCES rifiuto (materiale) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (oraInizioServizio, idEvento) REFERENCES servizio (oraInizio, idEvento)" +
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
	public Optional<Produzione> findByPrimaryKey(Time oraInizioServizio) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE oraInizioServizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setTime(1, oraInizioServizio);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Produzione> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Produzione> readFromResultSet(ResultSet resultSet) {
		final List<Produzione> produzioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Time oraInizioServizio = resultSet.getTime("oraInizioServizio");
			    final String idEvento = resultSet.getString("idEvento");
			    final Integer idProdotto = resultSet.getInt("idProdotto");
			    final Integer quantit‡Prodotta = resultSet.getInt("quantit‡Prodotta");
			    final String materialeUsato = resultSet.getString("materialeUsato");
			    final BigDecimal kgRifiutiUsati = resultSet.getBigDecimal("kgRifiutiUsati");
				
				final Produzione produzione = new Produzione(oraInizioServizio, idEvento, idProdotto, quantit‡Prodotta,
						materialeUsato, kgRifiutiUsati);
				produzioni.add(produzione);
			}
		} catch (final SQLException e) {}
		return produzioni;
	}

	@Override
	public boolean save(Produzione produzione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(oraInizioServizio, idEvento, idProdotto, quantit‡Prodotta, " +
				"materialeUsato, kgRifiutiUsati) VALUES (?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setTime(1, produzione.getOraInizioServizio());
            statement.setString(2, produzione.getIdEvento());
            statement.setInt(3, produzione.getIdProdotto());
            statement.setInt(4, produzione.getQuantit‡Prodotta());
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
	public boolean update(Produzione updatedProduzione) {
		final String query = "UPDATE " + TABLE_NAME + " SET idEvento = ?, idProdotto = ?,"
				 + "quantit‡Prodotta = ?," + "materialeUsato = ?,"+ "kgRifiutiUsati = ? "
				+ "WHERE oraInizioServizio = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedProduzione.getIdEvento());
			statement.setInt(2, updatedProduzione.getIdProdotto());
			statement.setInt(3, updatedProduzione.getQuantit‡Prodotta());
			statement.setString(4, updatedProduzione.getMaterialeUsato());
			statement.setBigDecimal(5, updatedProduzione.getKgRifiutiUsati());
			statement.setTime(6, updatedProduzione.getOraInizioServizio());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(Time oraInizioServizio) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE oraInizioServizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setTime(1, oraInizioServizio);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
