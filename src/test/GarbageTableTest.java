package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.GarbageTable;
import model.Garbage;
import utils.ConnectionProvider;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class GarbageTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static GarbageTable garbageTable = new GarbageTable(connectionProvider.getMySQLConnection());

    final Garbage garbage1 = new Garbage("carta", "riciclabile", new BigDecimal("123.51"), Optional.of("note note"));
    final Garbage garbage2 = new Garbage("plastica", "riciclabile", new BigDecimal("56.02"), Optional.empty());

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), garbageTable.getTableName());
        garbageTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), garbageTable.getTableName()));
        assertTrue(garbageTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(garbageTable.save(this.garbage1));
        assertFalse(garbageTable.save(this.garbage1));
        assertTrue(garbageTable.save(this.garbage2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(garbageTable.update(this.garbage1));
        garbageTable.save(this.garbage2);
		final Garbage updatedGarbage2 = new Garbage("plastica", "riciclabile", new BigDecimal("56.02"), Optional.of("still note note"));
		assertTrue(garbageTable.update(updatedGarbage2));
        final Optional<Garbage> foundGarbage = garbageTable.findByPrimaryKey(updatedGarbage2.getMateriale());
        assertFalse(foundGarbage.isEmpty());
        assertEquals(updatedGarbage2.getNote(), foundGarbage.get().getNote());
    }

    @Test
    public void deleteTest() {
        garbageTable.save(this.garbage1);
        assertTrue(garbageTable.delete(this.garbage1.getMateriale()));
        assertFalse(garbageTable.delete(this.garbage1.getMateriale()));
        assertTrue(garbageTable.findByPrimaryKey(this.garbage1.getMateriale()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        garbageTable.save(this.garbage1);
        garbageTable.save(this.garbage2);
        assertEquals(this.garbage1, garbageTable.findByPrimaryKey(this.garbage1.getMateriale()).orElse(null));
        assertEquals(this.garbage2, garbageTable.findByPrimaryKey(this.garbage2.getMateriale()).orElse(null));
    }

    @Test
    public void findAllTest() {
        garbageTable.save(this.garbage1);
        garbageTable.save(this.garbage2);
        assertIterableEquals(
            List.of(this.garbage1, this.garbage2),
            garbageTable.findAll()
        );
    }
}
