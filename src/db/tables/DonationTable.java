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

import model.Donation;
import utils.Utils;

public class DonationTable {

	public static final String TABLE_NAME = "donazione";

	private final Connection connection;

	public DonationTable(final Connection connection) {
		this.connection = Objects.requireNonNull(connection);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Donation> findByPrimaryKey(Integer idDonazione) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idDonazione = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setInt(1, idDonazione);
			final ResultSet resultSet = statement.executeQuery();
			return readFromResultSet(resultSet).stream().findFirst();
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public List<Donation> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
			return readFromResultSet(resultSet);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public List<Donation> readFromResultSet(ResultSet resultSet) {
		final List<Donation> donazioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final Optional<Integer> idDonazione = Optional.of(resultSet.getInt("idDonazione"));
				final BigDecimal importo = resultSet.getBigDecimal("importo");
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final Date dataDonazione = Utils.sqlDateToDate(resultSet.getDate("dataDonazione"));
				final Optional<Integer> idProgetto = Optional.ofNullable(resultSet.getInt("idProgetto"));

				final Donation donazione = new Donation(idDonazione, importo, codiceFiscale, dataDonazione,
						idProgetto);
				donazioni.add(donazione);
			}
		} catch (final SQLException e) {
		}
		return donazioni;
	}

	public boolean save(Donation donazione) {
		final String query = "INSERT INTO " + TABLE_NAME
				+ "(importo, codiceFiscale, dataDonazione, idProgetto) VALUES (?,?,?,?)";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setBigDecimal(1, donazione.getImporto());
			statement.setString(2, donazione.getCodiceFiscale());
			statement.setDate(3, Utils.dateToSqlDate(donazione.getDataDonazione()));
			statement.setInt(4, donazione.getIdProgetto().orElse(null));
			statement.executeUpdate();
			return true;
		} catch (final SQLIntegrityConstraintViolationException e) {
			System.out.println(e.toString());
			return false;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean update(Donation updatedDonazione) {
		final String query = "UPDATE " + TABLE_NAME + " SET " + "importo = ?," + "codiceFiscale = ?, "
				+ "dataDonazione = ?, " + "idProgetto = ? " + "WHERE idDonazione = ?";
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

	public boolean delete(Integer idDonazione) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idDonazione = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, idDonazione);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
