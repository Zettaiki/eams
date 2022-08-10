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
import utils.TableTestUtils;

class PersonTableTest {    
    final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static PersonTable personTable = new PersonTable(connectionProvider.getMySQLConnection());

    final Person person1 = new Person("AAAAAAAAAAAAAAAA", "Giacomo", "Cavalieri", "mail1@nicemail.it",
    		Optional.of("33123446756678"), "via Giuseppe", "Ancona", "Marche", "320256", Optional.of("Interna"));
    final Person person2 = new Person("BBBBBBBBBBBBBBBB", "Tommaso", "Cavalieri", "mail2@nicemail.it",
    		Optional.of("33563674789056"), "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", Optional.empty());

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), personTable.getTableName());
        personTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), personTable.getTableName()));
        assertTrue(personTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(personTable.save(this.person1));
        assertFalse(personTable.save(this.person1));
        assertTrue(personTable.save(this.person2));
    }
    
    @Test
    void updateTest() {
        assertFalse(personTable.update(this.person1));
        personTable.save(this.person2);
        final Person updatedPerson2 = new Person("BBBBBBBBBBBBBBBB", "Gianpaolo", "Cavalieri", "mail2@nicemail.it",
        		Optional.of("33563674789056"), "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", Optional.of("Esterna"));
        assertTrue(personTable.update(updatedPerson2));
        final Optional<Person> foundPersona = personTable.findByPrimaryKey(updatedPerson2.getCodiceFiscale());
        assertFalse(foundPersona.isEmpty());
        assertEquals(updatedPerson2.getNome(), foundPersona.get().getNome());
        assertEquals(updatedPerson2.getTipo(), foundPersona.get().getTipo());
    }

    @Test
    void deleteTest() {
        personTable.save(this.person1);
        assertTrue(personTable.delete(this.person1.getCodiceFiscale()));
        assertFalse(personTable.delete(this.person1.getCodiceFiscale()));
        assertTrue(personTable.findByPrimaryKey(this.person1.getCodiceFiscale()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        personTable.save(this.person1);
        personTable.save(this.person2);
        assertEquals(this.person1, personTable.findByPrimaryKey(this.person1.getCodiceFiscale()).orElse(null));
        assertEquals(this.person2, personTable.findByPrimaryKey(this.person2.getCodiceFiscale()).orElse(null));
    }

    @Test
    void findAllTest() {
        personTable.save(this.person1);
        personTable.save(this.person2);
        assertIterableEquals(
            List.of(this.person1, this.person2),
            personTable.findAll()
        );
    }
}
