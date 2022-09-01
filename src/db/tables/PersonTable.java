package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.Person;

public class PersonTable {

	public static final String TABLE_NAME = "persona";
    
    private final Connection connection;
    
    public PersonTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
    
	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Person> findByPrimaryKey(final String codiceFiscale) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Person> readFromResultSet(final ResultSet resultSet) {
		final List<Person> persone = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final String nome = resultSet.getString("nome");
				final String cognome = resultSet.getString("cognome");
				final String mail = resultSet.getString("mail");
				final Optional<String> telefono = Optional.ofNullable(resultSet.getString("telefono"));
				final String indirizzo = resultSet.getString("indirizzo");
				final String città = resultSet.getString("città");
				final String regione = resultSet.getString("regione");
				final String codicePostale = resultSet.getString("codicePostale");
				final Optional<String> tipo = Optional.ofNullable(resultSet.getString("tipo"));
				
				final Person persona = new Person(codiceFiscale, nome, cognome, mail, telefono, indirizzo, città, regione, codicePostale, tipo);
				persone.add(persona);
			}
		} catch (final SQLException e) {}
		return persone;
	}

	public boolean save(Person persona) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, nome, cognome, mail, telefono, indirizzo, città, regione, codicePostale, tipo)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, persona.getCodiceFiscale());
            statement.setString(2, persona.getNome());
            statement.setString(3, persona.getCognome());
            statement.setString(4, persona.getMail());
            statement.setString(5, persona.getTelefono().orElse(null));
            statement.setString(6, persona.getIndirizzo());
            statement.setString(7, persona.getCittà());
            statement.setString(8, persona.getRegione());
            statement.setString(9, persona.getCodicePostale());
            statement.setString(10, persona.getTipo().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}	

	public boolean update(final Person updatedPersona) {
		final String query =
			"UPDATE " + TABLE_NAME + " SET " + "nome = ?," + "cognome = ?," + "mail = ?," + "telefono = ?,"
					+ "indirizzo = ?," + "città = ?," + "regione = ?," + "codicePostale = ?," + "tipo = ? "
					+ "WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, updatedPersona.getNome());
            statement.setString(2, updatedPersona.getCognome());
            statement.setString(3, updatedPersona.getMail());
            statement.setString(4, updatedPersona.getTelefono().orElse(null));
            statement.setString(5, updatedPersona.getIndirizzo());
            statement.setString(6, updatedPersona.getCittà());
            statement.setString(7, updatedPersona.getRegione());
            statement.setString(8, updatedPersona.getCodicePostale());
            statement.setString(9, updatedPersona.getTipo().orElse(null));
            statement.setString(10, updatedPersona.getCodiceFiscale());
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
        	System.out.println(e.toString());
            throw new IllegalStateException(e);
        }
	}
	
}
