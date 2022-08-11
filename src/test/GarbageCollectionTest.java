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

import db.ConnectionProvider;
import db.tables.GarbageCollectionTable;
import model.GarbageCollection;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class GarbageCollectionTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static GarbageCollectionTable garbageCollectionTable = new GarbageCollectionTable(connectionProvider.getMySQLConnection());

    final GarbageCollection garbageCollection1 = new GarbageCollection("s1", "e1", "carta", new BigDecimal("30.20"));
    final GarbageCollection garbageCollection2 = new GarbageCollection("s2", "e2", "plastica", new BigDecimal("220.10"));

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), garbageCollectionTable.getTableName());
        garbageCollectionTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), garbageCollectionTable.getTableName()));
        assertTrue(garbageCollectionTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(garbageCollectionTable.save(this.garbageCollection1));
        assertFalse(garbageCollectionTable.save(this.garbageCollection1));
        assertTrue(garbageCollectionTable.save(this.garbageCollection2));
    }
    
    @Test
    void updateTest() {
        assertFalse(garbageCollectionTable.update(this.garbageCollection1));
        garbageCollectionTable.save(this.garbageCollection2);
        final GarbageCollection updatedGarbageCollection2 = new GarbageCollection("s2", "e2", "plastica", new BigDecimal("200.00"));
        assertTrue(garbageCollectionTable.update(updatedGarbageCollection2));
		final Optional<GarbageCollection> foundGarbageCollection = garbageCollectionTable.findByPrimaryKey(
				updatedGarbageCollection2.getIdServizio(), updatedGarbageCollection2.getIdEvento(),
				updatedGarbageCollection2.getMateriale());
		assertFalse(foundGarbageCollection.isEmpty());
        assertEquals(updatedGarbageCollection2.getKg(), foundGarbageCollection.get().getKg());
    }

    @Test
    void deleteTest() {
        garbageCollectionTable.save(this.garbageCollection1);
        assertTrue(garbageCollectionTable.delete(
        		this.garbageCollection1.getIdServizio(), this.garbageCollection1.getIdEvento(),
        		this.garbageCollection1.getMateriale()));
        assertFalse(garbageCollectionTable.delete(
        		this.garbageCollection1.getIdServizio(), this.garbageCollection1.getIdEvento(),
        		this.garbageCollection1.getMateriale()));
        assertTrue(garbageCollectionTable.findByPrimaryKey(
        		this.garbageCollection1.getIdServizio(), this.garbageCollection1.getIdEvento(),
        		this.garbageCollection1.getMateriale()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        garbageCollectionTable.save(this.garbageCollection1);
        garbageCollectionTable.save(this.garbageCollection2);
		assertEquals(this.garbageCollection1, garbageCollectionTable
				.findByPrimaryKey(this.garbageCollection1.getIdEvento(), this.garbageCollection1.getIdEvento(),
		        		this.garbageCollection1.getMateriale())
				.orElse(null));
		assertEquals(this.garbageCollection2, garbageCollectionTable
				.findByPrimaryKey(this.garbageCollection2.getIdEvento(), this.garbageCollection2.getIdServizio(),
						this.garbageCollection2.getMateriale())
				.orElse(null));
	}

    @Test
    void findAllTest() {
        garbageCollectionTable.save(this.garbageCollection1);
        garbageCollectionTable.save(this.garbageCollection2);
        assertIterableEquals(
            List.of(this.garbageCollection1, this.garbageCollection2),
            garbageCollectionTable.findAll()
        );
    }
}
