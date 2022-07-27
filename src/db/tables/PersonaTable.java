package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Persona;

public class PersonaTable implements Table<Persona, Integer> {

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
            			"CodiceFiscale NOT NULL PRIMARY KEY CHAR(16)," +
            			"Nome VARCHAR(25) NULL DEFAULT NULL," +
            			"Cognome VARCHAR(25) NULL DEFAULT NULL," +
            			"Telefono VARCHAR(24) NULL DEFAULT NULL," +
            			"Indirizzo VARCHAR(60) NULL DEFAULT NULL," +
            			"Citt� VARCHAR(15) NULL DEFAULT NULL," +
            			"Regione VARCHAR(20) NULL DEFAULT NULL," +
            			"CodicePostale VARCHAR(10) NULL DEFAULT NULL," +
            			"Tipo VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NULL DEFAULT NULL" +
            			")");
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}

	@Override
	public boolean dropTable() {
		try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);
            return true;
        } catch (final SQLException e) {
            return false;
        }
	}

	@Override
	public Optional<Persona> findByPrimaryKey(Integer primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Persona> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Persona persona) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(codiceFiscale, nome, cognome, telefono, indirizzo, citt�, regione, codicePostale, tipo)"
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, persona.getCodiceFiscale());
            statement.setString(2, persona.getNome());
            statement.setString(3, persona.getCognome());
            statement.setString(4, persona.getTelefono());
            statement.setString(5, persona.getIndirizzo());
            statement.setString(6, persona.getCitt�());
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
	                "cognome = ? " + 
	                "telefono = ? " + 
	                "indirizzo = ? " + 
	                "citt� = ? " + 
	                "regione = ? " + 
	                "codicePostale = ? " + 
	                "tipo = ? " + 
	            "WHERE codiceFiscale = ?";
	        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
	        	statement.setString(1, updatedPersona.getCodiceFiscale());
	            statement.setString(2, updatedPersona.getNome());
	            statement.setString(3, updatedPersona.getCognome());
	            statement.setString(4, updatedPersona.getTelefono());
	            statement.setString(5, updatedPersona.getIndirizzo());
	            statement.setString(6, updatedPersona.getCitt�());
	            statement.setString(7, updatedPersona.getRegione());
	            statement.setString(8, updatedPersona.getCodicePostale());
	            statement.setString(9, updatedPersona.getTipo());
	            return statement.executeUpdate() > 0;
	        } catch (final SQLException e) {
	            throw new IllegalStateException(e);
	        }
	}

	@Override
	public boolean delete(Integer codiceFiscale) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE codiceFiscale = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, codiceFiscale);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
}
