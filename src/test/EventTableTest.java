package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.EventTable;
import model.Event;
import utils.ServerCredentials;
import utils.TableTestUtils;
import utils.Utils;

public class EventTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static EventTable eventTable = new EventTable(connectionProvider.getMySQLConnection());

    final Event event1 = new Event("1", "a", Utils.buildDate(11, 10, 2022).get(), Optional.of("molto bello"));
    final Event event2 = new Event("2", "bo", Utils.buildDate(11, 12, 2021).get(), Optional.empty());

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), eventTable.getTableName());
        eventTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), eventTable.getTableName()));
        assertTrue(eventTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(eventTable.save(this.event1));
        assertFalse(eventTable.save(this.event1));
        assertTrue(eventTable.save(this.event2));
    }
    
    @Test
    void updateTest() {
        assertFalse(eventTable.update(this.event1));
        eventTable.save(this.event2);
        final Event updatedEvent2 = new Event("2", "bo", Utils.buildDate(11, 12, 2021).get(), Optional.of("molto belloanche questo"));
        assertTrue(eventTable.update(updatedEvent2));
        final Optional<Event> foundEvent = eventTable.findByPrimaryKey(updatedEvent2.getIdEvento());
        assertFalse(foundEvent.isEmpty());
        assertEquals(updatedEvent2.getDescrizione(), foundEvent.get().getDescrizione());
    }

    @Test
    void deleteTest() {
        eventTable.save(this.event1);
        assertTrue(eventTable.delete(this.event1.getIdEvento()));
        assertFalse(eventTable.delete(this.event1.getIdEvento()));
        assertTrue(eventTable.findByPrimaryKey(this.event1.getIdEvento()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        eventTable.save(this.event1);
        eventTable.save(this.event2);
        assertEquals(this.event1, eventTable.findByPrimaryKey(this.event1.getIdEvento()).orElse(null));
        assertEquals(this.event2, eventTable.findByPrimaryKey(this.event2.getIdEvento()).orElse(null));
    }

    @Test
    void findAllTest() {
        eventTable.save(this.event1);
        eventTable.save(this.event2);
        assertIterableEquals(
            List.of(this.event1, this.event2),
            eventTable.findAll()
        );
    }
}
