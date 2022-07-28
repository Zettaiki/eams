package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.PersonaTable;
import model.Persona;

class PersonaTableTests {
    final static String username = "root";
    final static String password = "";
    final static String dbName = "eams";
    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
    final static PersonaTable personaTable = new PersonaTable(connectionProvider.getMySQLConnection());

    final Persona persona1 = new Persona("A", "Giacomo", "Cavalieri", "A", "A", "A", "A", "A", "A");
    final Persona persona2 = new Persona("B", "Tommaso", "Cavalieri", "B", "B", "B", "B", "B", "B");

    @BeforeEach
    void setUp() throws Exception {
        personaTable.dropTable();
        personaTable.createTable();
    }

    @AfterEach
    void tearDown() throws Exception {
        personaTable.dropTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(personaTable.dropTable());
        assertFalse(personaTable.dropTable());
        assertTrue(personaTable.createTable());
        assertFalse(personaTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(personaTable.save(persona1));
        assertFalse(personaTable.save(persona1));
        assertTrue(personaTable.save(persona2));
    }
    
    @Test
    void updateTest() {
        assertFalse(personaTable.update(persona1));
        personaTable.save(persona2);
        final Persona updatedPersona2 = new Persona("B", "Tommaso", "Cavalieri", "BB", "B", "B", "B", "B", "B");
        assertTrue(personaTable.update(updatedPersona2));
        final Optional<Persona> foundPersona = personaTable.findByPrimaryKey(updatedPersona2.getCodiceFiscale());
        assertFalse(foundPersona.isEmpty());
        assertEquals(updatedPersona2.getNome(), foundPersona.get().getNome());
    }

    @Test
    void deleteTest() {
        personaTable.save(persona1);
        assertTrue(personaTable.delete(persona1.getCodiceFiscale()));
        assertFalse(personaTable.delete(persona1.getCodiceFiscale()));
        assertTrue(personaTable.findByPrimaryKey(persona1.getCodiceFiscale()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        personaTable.save(persona1);
        personaTable.save(persona2);
        assertEquals(persona1, personaTable.findByPrimaryKey(persona1.getCodiceFiscale()).orElse(null));
        assertEquals(persona2, personaTable.findByPrimaryKey(persona2.getCodiceFiscale()).orElse(null));
    }

    @Test
    void findAllTest() {
        personaTable.save(persona1);
        personaTable.save(persona2);
        assertIterableEquals(
            List.of(persona1, persona2),
            personaTable.findAll()
        );
    }
}
