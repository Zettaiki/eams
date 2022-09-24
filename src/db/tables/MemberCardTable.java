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

import utils.Utils;
import model.MemberCard;

public class MemberCardTable {

	public static final String TABLE_NAME = "tesserasocio";

	private final Connection connection;

	public MemberCardTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

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

	public Optional<MemberCard> findByCodiceFiscale(final String codiceFiscale) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            final ResultSet resultSet = statement.executeQuery(); 
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<MemberCard> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<MemberCard> readFromResultSet(final ResultSet resultSet) {
		final List<MemberCard> tesseresocio = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Optional<Integer> idSocio = Optional.of(resultSet.getInt("idSocio"));
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final Date dataAssociazione = Utils.sqlDateToDate(resultSet.getDate("dataAssociazione"));
				if(resultSet.wasNull()) {
					idSocio = Optional.empty();
				}
				final MemberCard tesserasocio = new MemberCard(idSocio, codiceFiscale, dataAssociazione);
				tesseresocio.add(tesserasocio);
			}
		} catch (final SQLException e) {}
		return tesseresocio;
	}

	public boolean save(MemberCard tesserasocio) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, dataAssociazione) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, tesserasocio.getCodiceFiscale());
            statement.setDate(2, Utils.dateToSqlDate(tesserasocio.getDataAssociazione()));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean delete(String idSocio) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idSocio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idSocio);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
