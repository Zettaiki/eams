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
import model.Raccolta;

public class RaccoltaTable implements Table<Raccolta, Time> {

	public static final String TABLE_NAME = "fornitura";

	private final Connection connection;

	public RaccoltaTable(final Connection connection) {
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
            			"oraInizioServizio` TIME NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"materiale VARCHAR(30) NOT NULL," +
            			"kg FLOAT NOT NULL," +
            			"PRIMARY KEY (`oraInizioServizio`, `idEvento`, `materiale`)," +
            			"FOREIGN KEY (materiale) REFERENCES rifiuto (materiale) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +
            			"FOREIGN KEY (oraInizioServizio, idEvento) REFERENCES servizio (oraInizio, idEvento) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Raccolta> findByPrimaryKey(Time oraInizioServizio) {
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
	public List<Raccolta> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Raccolta> readFromResultSet(ResultSet resultSet) {
		final List<Raccolta> raccolte = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Time oraInizioServizio = resultSet.getTime("oraInizioServizio");
				final String idEvento = resultSet.getString("idEvento");
				final String materiale = resultSet.getString("materiale");
				final BigDecimal kg = resultSet.getBigDecimal("kg");
				
				final Raccolta raccolta = new Raccolta(oraInizioServizio, idEvento, materiale, kg);
				raccolte.add(raccolta);
			}
		} catch (final SQLException e) {}
		return raccolte;
	}

	@Override
	public boolean save(Raccolta raccolta) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(oraInizioServizio, idEvento, materiale, kg) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setTime(1, raccolta.getOraInizioServizio());
            statement.setString(2, raccolta.getIdEvento());
            statement.setString(3, raccolta.getMateriale());
            statement.setBigDecimal(4, raccolta.getKg());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Raccolta updatedRaccolta) {
		final String query = "UPDATE " + TABLE_NAME + " SET partitaIVA = ?," + "data = ?," + "kgConsegnati = ? "
				+ "WHERE materiale = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedRaccolta.getIdEvento());
			statement.setString(2, updatedRaccolta.getMateriale());
			statement.setBigDecimal(3, updatedRaccolta.getKg());
			statement.setTime(4, updatedRaccolta.getOraInizioServizio());
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
