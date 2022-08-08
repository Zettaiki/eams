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

import db.Table;
import model.Persona;

public class PersonaTable implements Table<Persona, String> {

	public static final String TABLE_NAME = "persona";
    
    private final Connection connection;
    
    public PersonaTable(final Connection connection) {
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
            			"codiceFiscale CHAR(16) NOT NULL PRIMARY KEY," +
            			"nome VARCHAR(25)," +
            			"cognome VARCHAR(25)," +
            			"mail VARCHAR(45)," +
            			"telefono VARCHAR(24)," +
            			"indirizzo VARCHAR(60)," +
            			"citt� VARCHAR(15)," +
            			"regione VARCHAR(20)," +
            			"codicePostale VARCHAR(10)," +
            			"tipo VARCHAR(45)" +
            		")");
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}

	@Override
	public Optional<Persona> findByPrimaryKey(final String codiceFiscale) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Persona> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public List<Persona> readFromResultSet(final ResultSet resultSet) {
		final List<Persona> persone = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("codiceFiscale");
				final String nome = resultSet.getString("nome");
				final String cognome = resultSet.getString("cognome");
				final String mail = resultSet.getString("mail");
				final Optional<String> telefono = Optional.ofNullable(resultSet.getString("telefono"));
				final String indirizzo = resultSet.getString("indirizzo");
				final String citt� = resultSet.getString("citt�");
				final String regione = resultSet.getString("regione");
				final String codicePostale = resultSet.getString("codicePostale");
				final Optional<String> tipo = Optional.ofNullable(resultSet.getString("tipo"));
				
				final Persona persona = new Persona(codiceFiscale, nome, cognome, mail, telefono, indirizzo, citt�, regione, codicePostale, tipo);
				persone.add(persona);
			}
		} catch (final SQLException e) {}
		return persone;
	}

	@Override
	public boolean save(Persona persona) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, nome, cognome, mail, telefono, indirizzo, citt�, regione, codicePostale, tipo)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, persona.getCodiceFiscale());
            statement.setString(2, persona.getNome());
            statement.setString(3, persona.getCognome());
            statement.setString(4, persona.getMail());
            statement.setString(5, persona.getTelefono().orElse(null));
            statement.setString(6, persona.getIndirizzo());
            statement.setString(7, persona.getCitt�());
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

	@Override
	public boolean update(final Persona updatedPersona) {
		final String query =
	            "UPDATE " + TABLE_NAME + " SET " +
	                "nome = ?," +
	                "cognome = ?," + 
	                "mail = ?," + 
	                "telefono = ?," + 
	                "indirizzo = ?," + 
	                "citt� = ?," + 
	                "regione = ?," + 
	                "codicePostale = ?," + 
	                "tipo = ? " + 
	            "WHERE codiceFiscale = ?";
	        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
	        	statement.setString(1, updatedPersona.getNome());
	            statement.setString(2, updatedPersona.getCognome());
	            statement.setString(3, updatedPersona.getMail());
	            statement.setString(4, updatedPersona.getTelefono().orElse(null));
	            statement.setString(5, updatedPersona.getIndirizzo());
	            statement.setString(6, updatedPersona.getCitt�());
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

	@Override
	public boolean delete(String codiceFiscale) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, codiceFiscale);
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
