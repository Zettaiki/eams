package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.ParticipationTable;
import model.Participation;
import utils.ConnectionProvider;
import utils.TableTestUtils;

public class ParticipationTableTest {
    final static ParticipationTable participationTable = new ParticipationTable(ConnectionProvider.getMySQLConnection());

    final Participation participation1 = new Participation("AAAAAAAAAAAAAAAA", "s1");
    final Participation participation2 = new Participation("BBBBBBBBBBBBBBBB", "s2");

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), participationTable.getTableName());
        participationTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), participationTable.getTableName()));
        assertTrue(participationTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(participationTable.save(this.participation1));
        assertFalse(participationTable.save(this.participation1));
        assertTrue(participationTable.save(this.participation2));
    }
    
    @Test
    void updateTest() {
        assertThrows(IllegalStateException.class, () -> participationTable.update(new Participation("AAAAAAAAAAAAAAAA", "s1")));
    }

    @Test
    void deleteTest() {
		participationTable.save(this.participation1);
		assertTrue(participationTable.delete(this.participation1.getCodiceFiscaleVolontario(),
				this.participation1.getIdServizio()));
		assertFalse(participationTable.delete(this.participation1.getCodiceFiscaleVolontario(),
				this.participation1.getIdServizio()));
		assertTrue(participationTable
				.findByPrimaryKey(this.participation1.getCodiceFiscaleVolontario(), this.participation1.getIdServizio())
				.isEmpty());
	}

    @Test
    void findByPrimaryKeyTest() {
        participationTable.save(this.participation1);
        participationTable.save(this.participation2);
		assertEquals(this.participation1,
				participationTable.findByPrimaryKey(this.participation1.getCodiceFiscaleVolontario(),
						this.participation1.getIdServizio()).orElse(null));
		assertEquals(this.participation2,
				participationTable.findByPrimaryKey(this.participation2.getCodiceFiscaleVolontario(),
						this.participation2.getIdServizio()).orElse(null));
	}

    @Test
    void findAllTest() {
        participationTable.save(this.participation1);
        participationTable.save(this.participation2);
        assertIterableEquals(
            List.of(this.participation1, this.participation2),
            participationTable.findAll()
        );
    }
}
