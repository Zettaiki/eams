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
            			"CodiceFiscale CHAR(16) NOT NULL PRIMARY KEY," +
            			"Nome VARCHAR(25)," +
            			"Cognome VARCHAR(25)," +
            			"Telefono VARCHAR(24)," +
            			"Indirizzo VARCHAR(60)," +
            			"Città VARCHAR(15)," +
            			"Regione VARCHAR(20)," +
            			"CodicePostale VARCHAR(10)," +
            			"Tipo VARCHAR(45)" +
            		")");
            return true;
        } catch (final SQLException e) {
        	System.out.println(e.toString());
            return false;
        }
	}

	@Override
	public boolean dropTable() {
		try (final Statement statement = this.connection.createStatement()) {			
			statement.executeUpdate("SET foreign_key_checks = 0;");
			statement.executeUpdate("DROP TABLE " + TABLE_NAME);            
            statement.executeUpdate("SET foreign_key_checks = 1;");
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
	
	private List<Persona> readFromResultSet(final ResultSet resultSet) {
		final List<Persona> persone = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String codiceFiscale = resultSet.getString("CodiceFiscale");
				final String nome = resultSet.getString("Nome");
				final String cognome = resultSet.getString("Cognome");
				final String telefono = resultSet.getString("Telefono");
				final String indirizzo = resultSet.getString("Indirizzo");
				final String città = resultSet.getString("Città");
				final String regione = resultSet.getString("Regione");
				final String codicePostale = resultSet.getString("CodicePostale");
				final String tipo = resultSet.getString("Tipo");
				
				final Persona persona = new Persona(codiceFiscale, nome, cognome, telefono, indirizzo, città, regione, codicePostale, tipo);
				persone.add(persona);
			}
		} catch (final SQLException e) {}
		return persone;
	}

	@Override
	public boolean save(Persona persona) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, nome, cognome, telefono, indirizzo, città, regione, codicePostale, tipo)"
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, persona.getCodiceFiscale());
            statement.setString(2, persona.getNome());
            statement.setString(3, persona.getCognome());
            statement.setString(4, persona.getTelefono());
            statement.setString(5, persona.getIndirizzo());
            statement.setString(6, persona.getCittà());
            statement.setString(7, persona.getRegione());
            statement.setString(8, persona.getCodicePostale());
            statement.setString(9, persona.getTipo());
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
	                "telefono = ?," + 
	                "indirizzo = ?," + 
	                "città = ?," + 
	                "regione = ?," + 
	                "codicePostale = ?," + 
	                "tipo = ? " + 
	            "WHERE codiceFiscale = ?";
	        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
	        	statement.setString(1, updatedPersona.getCodiceFiscale());
	            statement.setString(2, updatedPersona.getNome());
	            statement.setString(3, updatedPersona.getCognome());
	            statement.setString(4, updatedPersona.getTelefono());
	            statement.setString(5, updatedPersona.getIndirizzo());
	            statement.setString(6, updatedPersona.getCittà());
	            statement.setString(7, updatedPersona.getRegione());
	            statement.setString(8, updatedPersona.getCodicePostale());
	            statement.setString(9, updatedPersona.getTipo());
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
}
