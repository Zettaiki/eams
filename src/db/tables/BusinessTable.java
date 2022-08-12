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

import model.Business;

public class BusinessTable {

	public static final String TABLE_NAME = "azienda";
    
    private final Connection connection;
    
    public BusinessTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
    
	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Business> findByPrimaryKey(BigDecimal partitaIVA) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE partitaIVA = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setBigDecimal(1, partitaIVA);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Business> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Business> readFromResultSet(ResultSet resultSet) {
		final List<Business> aziende = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final BigDecimal partitaIVA = resultSet.getBigDecimal("partitaIVA");
			    final String denominazioneSociale = resultSet.getString("denominazioneSociale");
			    final String telefono = resultSet.getString("telefono");
			    final String indirizzo = resultSet.getString("indirizzo");
			    final String città = resultSet.getString("città");
			    final String regione = resultSet.getString("regione");
			    final String codicePostale = resultSet.getString("codicePostale");
			    final String mail = resultSet.getString("mail");
			    final Optional<String> fax = Optional.ofNullable(resultSet.getString("fax"));
				
				final Business azienda = new Business(partitaIVA, denominazioneSociale, telefono, indirizzo, città, regione, codicePostale, mail, fax);
				aziende.add(azienda);
			}
		} catch (final SQLException e) {}
		return aziende;
	}

	public boolean save(Business azienda) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(partitaIVA, denominazioneSociale, telefono, indirizzo, città, regione, codicePostale, mail, fax)"
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setBigDecimal(1, azienda.getPartitaIVA());
        	statement.setString(2, azienda.getDenominazioneSociale());
        	statement.setString(3, azienda.getTelefono());
        	statement.setString(4, azienda.getIndirizzo());
        	statement.setString(5, azienda.getCittà());
        	statement.setString(6, azienda.getRegione());
        	statement.setString(7, azienda.getCodicePostale());
        	statement.setString(8, azienda.getMail());
        	statement.setString(9, azienda.getFax().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public boolean update(Business updatedAzienda) {
		final String query =
			"UPDATE " + TABLE_NAME + " SET " + "denominazioneSociale = ?," + "telefono = ?," 
					+ "indirizzo = ?," + "città = ?," + "regione = ?," + "codicePostale = ?,"
					+ "mail = ?," + "fax = ? "
					+ "WHERE partitaIVA = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, updatedAzienda.getDenominazioneSociale());
            statement.setString(2, updatedAzienda.getTelefono());
            statement.setString(3, updatedAzienda.getIndirizzo());
            statement.setString(4, updatedAzienda.getCittà());
            statement.setString(5, updatedAzienda.getRegione());
            statement.setString(6, updatedAzienda.getCodicePostale());
            statement.setString(7, updatedAzienda.getMail());
            statement.setString(8, updatedAzienda.getFax().orElse(null));
            statement.setBigDecimal(9, updatedAzienda.getPartitaIVA());
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
        	System.out.println(e.toString());
            throw new IllegalStateException(e);
        }
	}

	public boolean delete(BigDecimal partitaIVA) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE partitaIVA = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setBigDecimal(1, partitaIVA);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
