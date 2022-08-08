package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.TesseraSocioTable;
import utils.Utils;
import model.TesseraSocio;
import utils.ServerCredentials;

class TesseraSocioTableTests {
    final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static TesseraSocioTable tesseraSocioTable = new TesseraSocioTable(connectionProvider.getMySQLConnection());

    final TesseraSocio tesseraSocio1 = new TesseraSocio("1", "AAAAAAAAAAAAAAAA", Utils.buildDate(11, 10, 2022).get());
    final TesseraSocio tesseraSocio2 = new TesseraSocio("2", "BBBBBBBBBBBBBBBB", Utils.buildDate(11, 12, 2021).get());

    @BeforeEach
    void setUp() throws Exception {
        tesseraSocioTable.dropTable();
        tesseraSocioTable.createTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(tesseraSocioTable.dropTable());
        assertTrue(tesseraSocioTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(tesseraSocioTable.save(this.tesseraSocio1));
        assertFalse(tesseraSocioTable.save(this.tesseraSocio1));
        assertTrue(tesseraSocioTable.save(this.tesseraSocio2));
    }
    
    @Test
    void updateTest() {
        assertFalse(tesseraSocioTable.update(this.tesseraSocio1));
        tesseraSocioTable.save(this.tesseraSocio2);
        final TesseraSocio updatedtesseraSocio2 = new TesseraSocio("2", "BBBBBBBBBBBBBBBB", Utils.buildDate(13, 12, 2021).get());
        assertTrue(tesseraSocioTable.update(updatedtesseraSocio2));
        final Optional<TesseraSocio> foundTesseraSocio = tesseraSocioTable.findByPrimaryKey(updatedtesseraSocio2.getIdSocio());
        assertFalse(foundTesseraSocio.isEmpty());
        assertEquals(updatedtesseraSocio2.getDataAssociazione(), foundTesseraSocio.get().getDataAssociazione());
    }

    @Test
    void deleteTest() {
        tesseraSocioTable.save(this.tesseraSocio1);
        assertTrue(tesseraSocioTable.delete(this.tesseraSocio1.getIdSocio()));
        assertFalse(tesseraSocioTable.delete(this.tesseraSocio1.getIdSocio()));
        assertTrue(tesseraSocioTable.findByPrimaryKey(this.tesseraSocio1.getIdSocio()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        tesseraSocioTable.save(this.tesseraSocio1);
        tesseraSocioTable.save(this.tesseraSocio2);
        assertEquals(this.tesseraSocio1, tesseraSocioTable.findByPrimaryKey(this.tesseraSocio1.getIdSocio()).orElse(null));
        assertEquals(this.tesseraSocio2, tesseraSocioTable.findByPrimaryKey(this.tesseraSocio2.getIdSocio()).orElse(null));
    }

    @Test
    void findAllTest() {
        tesseraSocioTable.save(this.tesseraSocio1);
        tesseraSocioTable.save(this.tesseraSocio2);
        assertIterableEquals(
            List.of(this.tesseraSocio1, this.tesseraSocio2),
            tesseraSocioTable.findAll()
        );
    }
}
