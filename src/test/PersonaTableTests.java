package test;

import static org.junit.jupiter.api.Assertions.*;

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
    final static String password = "unisql";
    final static String dbName = "eams";
    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(username, password, dbName);
    final static PersonaTable personaTable = new PersonaTable(connectionProvider.getMySQLConnection());

    final Persona persona1 = new Persona("AAAAAAAAAAAAAAAA", "Giacomo", "Cavalieri", 
    		"33123446756678", "via Giuseppe", "Ancona", "Marche", "320256", "Interna");
    final Persona persona2 = new Persona("BBBBBBBBBBBBBBBB", "Tommaso", "Cavalieri",
    		"33563674789056", "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", "Esterna");

    @BeforeEach
    void setUp() throws Exception {
        personaTable.dropTable();
        personaTable.createTable();
    }

    /*@AfterEach
    void tearDown() throws Exception {
        personaTable.dropTable();
    }*/

    @Test
    void creationAndDropTest() {
        assertTrue(personaTable.dropTable());
        assertFalse(personaTable.dropTable());
        assertTrue(personaTable.createTable());
        //assertFalse(personaTable.createTable()); no perché if not exists nel try di createTable controlla già
    }
    
    @Test
    void saveTest() {
        assertTrue(personaTable.save(this.persona1));
        assertFalse(personaTable.save(this.persona1));
        assertTrue(personaTable.save(this.persona2));
    }
    
    @Test
    void updateTest() {
        assertFalse(personaTable.update(this.persona1));
        personaTable.save(this.persona2);
        final Persona updatedPersona2 = new Persona("BBBBBBBBBBBBBBBB", "Gianpaolo", "Cavalieri",
        		"33563674789056", "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", "Esterna");
        assertTrue(personaTable.update(updatedPersona2));
        final Optional<Persona> foundPersona = personaTable.findByPrimaryKey(updatedPersona2.getCodiceFiscale());
        assertFalse(foundPersona.isEmpty());
        assertEquals(updatedPersona2.getNome(), foundPersona.get().getNome());
    }

    @Test
    void deleteTest() {
        personaTable.save(this.persona1);
        assertTrue(personaTable.delete(this.persona1.getCodiceFiscale()));
        assertFalse(personaTable.delete(this.persona1.getCodiceFiscale()));
        assertTrue(personaTable.findByPrimaryKey(this.persona1.getCodiceFiscale()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        personaTable.save(this.persona1);
        personaTable.save(this.persona2);
        assertEquals(this.persona1, personaTable.findByPrimaryKey(this.persona1.getCodiceFiscale()).orElse(null));
        assertEquals(this.persona2, personaTable.findByPrimaryKey(this.persona2.getCodiceFiscale()).orElse(null));
    }

    @Test
    void findAllTest() {
        personaTable.save(this.persona1);
        personaTable.save(this.persona2);
        assertIterableEquals(
            List.of(this.persona1, this.persona2),
            personaTable.findAll()
        );
    }
}
