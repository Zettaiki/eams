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
import db.tables.ProgettoTable;
import model.Progetto;
import utils.ServerCredentials;
import utils.Utils;

public class ProgettoTableTests {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(),
			ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
	final static ProgettoTable ProgettoTable = new ProgettoTable(connectionProvider.getMySQLConnection());

	final Progetto progetto1 = new Progetto(1, "m",	Utils.buildDate(11, 10, 2022).get(), 4);
	final Progetto progetto2 = new Progetto(2, "a",	Utils.buildDate(11, 12, 2021).get(), 5);

    @BeforeEach
    void setUp() throws Exception {
        ProgettoTable.dropTable();
        ProgettoTable.createTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(ProgettoTable.dropTable());
        assertTrue(ProgettoTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(ProgettoTable.save(this.progetto1));
        assertFalse(ProgettoTable.save(this.progetto1));
        assertTrue(ProgettoTable.save(this.progetto2));
    }
    
    @Test
    void updateTest() {
        assertFalse(ProgettoTable.update(this.progetto1));
        ProgettoTable.save(this.progetto2);
        final Progetto updatedProgetto2 = new Progetto(2, "a",	Utils.buildDate(11, 12, 2011).get(), 5);
        assertTrue(ProgettoTable.update(updatedProgetto2));
        final Optional<Progetto> foundProgetto = ProgettoTable.findByPrimaryKey(updatedProgetto2.getIdProgetto());
        assertFalse(foundProgetto.isEmpty());
        assertEquals(updatedProgetto2.getDataInizio(), foundProgetto.get().getDataInizio());
    }

    @Test
    void deleteTest() {
        ProgettoTable.save(this.progetto1);
        assertTrue(ProgettoTable.delete(this.progetto1.getIdProgetto()));
        assertFalse(ProgettoTable.delete(this.progetto1.getIdProgetto()));
        assertTrue(ProgettoTable.findByPrimaryKey(this.progetto1.getIdProgetto()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        ProgettoTable.save(this.progetto1);
        ProgettoTable.save(this.progetto2);
        assertEquals(this.progetto1, ProgettoTable.findByPrimaryKey(this.progetto1.getIdProgetto()).orElse(null));
        assertEquals(this.progetto2, ProgettoTable.findByPrimaryKey(this.progetto2.getIdProgetto()).orElse(null));
    }

    @Test
    void findAllTest() {
        ProgettoTable.save(this.progetto1);
        ProgettoTable.save(this.progetto2);
        assertIterableEquals(
            List.of(this.progetto1, this.progetto2),
            ProgettoTable.findAll()
        );
    }
}
