package db.tables;

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
import utils.Utils;
import model.MemberCard;

public class MemberCardTable implements Table<MemberCard, String> {

	public static final String TABLE_NAME = "tesserasocio";

	private final Connection connection;

	public MemberCardTable(final Connection connection) {
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
            			"idSocio VARCHAR(15) NOT NULL PRIMARY KEY," +
            			"codiceFiscale CHAR(16) NOT NULL," +
            			"dataAssociazione DATETIME NOT NULL," +
            			"FOREIGN KEY (codiceFiscale) REFERENCES persona (codiceFiscale) ON DELETE CASCADE ON UPDATE CASCADE" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<MemberCard> findByPrimaryKey(String idSocio) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idSocio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idSocio);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<MemberCard> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public List<MemberCard> readFromResultSet(ResultSet resultSet) {
		final List<MemberCard> tesseresocio = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idSocio = resultSet.getString("idSocio");
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final Date dataAssociazione = Utils.sqlDateToDate(resultSet.getDate("dataAssociazione"));
				
				final MemberCard tesserasocio = new MemberCard(idSocio, codiceFiscale, dataAssociazione);
				tesseresocio.add(tesserasocio);
			}
		} catch (final SQLException e) {}
		return tesseresocio;
	}

	@Override
	public boolean save(MemberCard tesserasocio) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idSocio, codiceFiscale, dataAssociazione) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, tesserasocio.getIdSocio());
            statement.setString(2, tesserasocio.getCodiceFiscale());
            statement.setDate(3, Utils.dateToSqlDate(tesserasocio.getDataAssociazione()));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(MemberCard updatedTesserasocio) {
		final String query = "UPDATE " + TABLE_NAME + " SET codiceFiscale = ?," + "dataAssociazione = ? "
				+ "WHERE idSocio = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedTesserasocio.getCodiceFiscale());
			statement.setDate(2, Utils.dateToSqlDate(updatedTesserasocio.getDataAssociazione()));
			statement.setString(3, updatedTesserasocio.getIdSocio());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String idSocio) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idSocio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idSocio);
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
