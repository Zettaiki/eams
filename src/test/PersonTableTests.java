package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.PersonTable;
import model.Person;
import utils.ServerCredentials;

class PersonTableTests {    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static PersonTable personaTable = new PersonTable(connectionProvider.getMySQLConnection());

    final Person persona1 = new Person("AAAAAAAAAAAAAAAA", "Giacomo", "Cavalieri", "mail1@nicemail.it",
    		Optional.of("33123446756678"), "via Giuseppe", "Ancona", "Marche", "320256", Optional.of("Interna"));
    final Person persona2 = new Person("BBBBBBBBBBBBBBBB", "Tommaso", "Cavalieri", "mail2@nicemail.it",
    		Optional.of("33563674789056"), "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", Optional.empty());

    @BeforeEach
    void setUp() throws Exception {
        personaTable.dropTable();
        personaTable.createTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(personaTable.dropTable());
        assertTrue(personaTable.createTable());
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
        final Person updatedPersona2 = new Person("BBBBBBBBBBBBBBBB", "Gianpaolo", "Cavalieri", "mail2@nicemail.it",
        		Optional.of("33563674789056"), "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", Optional.of("Esterna"));
        assertTrue(personaTable.update(updatedPersona2));
        final Optional<Person> foundPersona = personaTable.findByPrimaryKey(updatedPersona2.getCodiceFiscale());
        assertFalse(foundPersona.isEmpty());
        assertEquals(updatedPersona2.getNome(), foundPersona.get().getNome());
        assertEquals(updatedPersona2.getTipo(), foundPersona.get().getTipo());
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
