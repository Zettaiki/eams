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
import db.tables.DonazioneTable;
import model.Donazione;
import utils.ServerCredentials;
import utils.Utils;

public class DonazioneTableTests {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(),
			ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
	final static DonazioneTable DonazioneTable = new DonazioneTable(connectionProvider.getMySQLConnection());

	final Donazione donazione1 = new Donazione(new BigDecimal("22.20"), "AAAAAAAAAAAAAAAA",
			Utils.buildDate(11, 10, 2022).get(), Optional.of(1));
	final Donazione donazione2 = new Donazione(new BigDecimal("3333.33"), "BBBBBBBBBBBBBBBB",
			Utils.buildDate(11, 12, 2021).get(), Optional.of(2));

    @BeforeEach
    void setUp() throws Exception {
        DonazioneTable.dropTable();
        DonazioneTable.createTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(DonazioneTable.dropTable());
        assertTrue(DonazioneTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(DonazioneTable.save(this.donazione1));
        assertFalse(DonazioneTable.save(this.donazione1));
        assertTrue(DonazioneTable.save(this.donazione2));
    }
    
    @Test
    void updateTest() {
        assertFalse(DonazioneTable.update(this.donazione1));
        DonazioneTable.save(this.donazione2);
        final Donazione updatedDonazione2 = new Donazione(new BigDecimal("7777.77"), "BBBBBBBBBBBBBBBB",
    			Utils.buildDate(11, 12, 2021).get(), Optional.of(2));
        assertTrue(DonazioneTable.update(updatedDonazione2));
        final Optional<Donazione> foundDonazione = DonazioneTable.findByPrimaryKey(updatedDonazione2.getIdDonazione());
        assertFalse(foundDonazione.isEmpty());
        assertEquals(updatedDonazione2.getImporto(), foundDonazione.get().getImporto());
    }

    @Test
    void deleteTest() {
        DonazioneTable.save(this.donazione1);
        assertTrue(DonazioneTable.delete(this.donazione1.getIdDonazione()));
        assertFalse(DonazioneTable.delete(this.donazione1.getIdDonazione()));
        assertTrue(DonazioneTable.findByPrimaryKey(this.donazione1.getIdDonazione()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        DonazioneTable.save(this.donazione1);
        DonazioneTable.save(this.donazione2);
        assertEquals(this.donazione1, DonazioneTable.findByPrimaryKey(this.donazione1.getIdDonazione()).orElse(null));
        assertEquals(this.donazione2, DonazioneTable.findByPrimaryKey(this.donazione2.getIdDonazione()).orElse(null));
    }

    @Test
    void findAllTest() {
        DonazioneTable.save(this.donazione1);
        DonazioneTable.save(this.donazione2);
        assertIterableEquals(
            List.of(this.donazione1, this.donazione2),
            DonazioneTable.findAll()
        );
    }
}
