package db.tables;

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

import db.TableDoublePk;
import model.Organizzazione;

public class OrganizzazioneTable implements TableDoublePk<Organizzazione, String, String> {

	public static final String TABLE_NAME = "organizzazione";

	private final Connection connection;

	public OrganizzazioneTable(final Connection connection) {
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
            			"codiceFiscaleDipendente CHAR(16) NOT NULL," +
            			"idEvento CHAR(20) NOT NULL," +
            			"PRIMARY KEY (codiceFiscaleDipendente, idEvento)," +
            			"FOREIGN KEY (codiceFiscaleDipendente) REFERENCES dipendente (codiceFiscale)" +
            			"ON DELETE CASCADE ON UPDATE CASCADE" +
            			"FOREIGN KEY (idEvento) REFERENCES evento (idEvento) " +
            			"ON DELETE CASCADE ON UPDATE CASCADE," +            			
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Organizzazione> findByPrimaryKey(String codiceFiscaleDipendente, String idEvento) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscaleDipendente = ? AND idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleDipendente);
            statement.setString(2, idEvento);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Organizzazione> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Organizzazione> readFromResultSet(ResultSet resultSet) {
		final List<Organizzazione> organizzazioni = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscaleDipendente = resultSet.getString("codiceFiscaleDipendente");
				final String idEvento = resultSet.getString("idEvento");
				
				final Organizzazione organizzazione = new Organizzazione(codiceFiscaleDipendente, idEvento);
				organizzazioni.add(organizzazione);
			}
		} catch (final SQLException e) {}
		return organizzazioni;
	}

	@Override
	public boolean save(Organizzazione organizzazione) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscaleDipendente, idEvento) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, organizzazione.getCodiceFiscaleDipendente());
            statement.setString(2, organizzazione.getIdEvento());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Organizzazione updatedOrganizzazione) {
		throw new IllegalStateException();
	}

	@Override
	public boolean delete(String codiceFiscaleDipendente, String idEvento) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscaleDipendente = ? AND idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscaleDipendente);
            statement.setString(2, idEvento);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
