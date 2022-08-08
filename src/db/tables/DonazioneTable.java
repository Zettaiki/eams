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
import model.Donazione;
import utils.Utils;

public class DonazioneTable implements Table<Donazione, Integer> {

	public static final String TABLE_NAME = "donazione";

	private final Connection connection;

	public DonazioneTable(final Connection connection) {
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
					"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
					+ "idDonazione INT NOT NULL AUTO_INCREMENT," + "importo DECIMAL(11,2) NOT NULL,"
					+ "codiceFiscale CHAR(16) NOT NULL," + "dataDonazione DATETIME NOT NULL,"
					+ "idProgetto INT NULL,"					
					+ "PRIMARY KEY (idDonazione, codiceFiscale),"					
					+ "FOREIGN KEY (codiceFiscale) REFERENCES persona (codiceFiscale) "
					+ "ON DELETE CASCADE ON UPDATE CASCADE,"
					+ "FOREIGN KEY (idProgetto) REFERENCES progetto (idProgetto) "
					+ "ON DELETE CASCADE ON UPDATE CASCADE" +
					")");
			return true;
		} catch (final SQLException e) {
			return false;
		}
	}

	@Override
	public Optional<Donazione> findByPrimaryKey(Integer idDonazione) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idDonazione = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, idDonazione);
			final ResultSet resultSet = statement.executeQuery();
			return readFromResultSet(resultSet).stream().findFirst();
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public List<Donazione> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			return readFromResultSet(resultSet);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public List<Donazione> readFromResultSet(ResultSet resultSet) {
		final List<Donazione> donazioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Integer idDonazione = resultSet.getInt("idDonazione");
				final BigDecimal importo = resultSet.getBigDecimal("importo");
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final Date dataDonazione = Utils.sqlDateToDate(resultSet.getDate("dataDonazione"));
				final Optional<Integer> idProgetto = Optional.ofNullable(resultSet.getInt("idProgetto"));

				final Donazione donazione = new Donazione(idDonazione, importo, codiceFiscale, dataDonazione,
						idProgetto);
				donazioni.add(donazione);
			}
		} catch (final SQLException e) {
		}
		return donazioni;
	}

	@Override
	public boolean save(Donazione donazione) {
		final String query = "INSERT INTO " + TABLE_NAME
				+ "(idDonazione, importo, codiceFiscale, dataDonazione, idProgetto) VALUES (?,?,?,?,?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, donazione.getIdDonazione());
			statement.setBigDecimal(2, donazione.getImporto());
			statement.setString(3, donazione.getCodiceFiscale());
			statement.setDate(4, Utils.dateToSqlDate(donazione.getDataDonazione()));
			statement.setInt(5, donazione.getIdProgetto().orElse(null));
			statement.executeUpdate();
			return true;
		} catch (final SQLIntegrityConstraintViolationException e) {
			return false;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean update(Donazione updatedDonazione) {
		final String query = "UPDATE " + TABLE_NAME + " SET " + "importo = ?," + "codiceFiscale = ? "
				+ "dataDonazione = ? " + "idProgetto = ? " + "WHERE idDonazione = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setBigDecimal(1, updatedDonazione.getImporto());
			statement.setString(2, updatedDonazione.getCodiceFiscale());
			statement.setDate(3, Utils.dateToSqlDate(updatedDonazione.getDataDonazione()));
			statement.setInt(4, updatedDonazione.getIdProgetto().orElse(null));
			statement.setInt(5, updatedDonazione.getIdDonazione());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(Integer idDonazione) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idSocio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idDonazione);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public boolean dropTable() {
		try (final Statement statement = this.connection.createStatement()) {			
			statement.executeUpdate("SET foreign_key_checks = 0;");
			statement.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);            
            statement.executeUpdate("SET foreign_key_checks = 1;");
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}

}
