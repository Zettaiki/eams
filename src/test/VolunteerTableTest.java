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
import db.tables.VolunteerTable;
import model.Volunteer;
import utils.ServerCredentials;
import utils.TableTestUtils;
import utils.Utils;

public class VolunteerTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static VolunteerTable volunteerTable = new VolunteerTable(connectionProvider.getMySQLConnection());

	final Volunteer volunteer1 = new Volunteer("AAAAAAAAAAAAAAAA", "Roma", Utils.buildDate(11, 12, 2022).get());
	final Volunteer volunteer2 = new Volunteer("BBBBBBBBBBBBBBBB", "Bari", Utils.buildDate(11, 12, 2021).get());

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), volunteerTable.getTableName());
        volunteerTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), volunteerTable.getTableName()));
        assertTrue(volunteerTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(volunteerTable.save(this.volunteer1));
        assertFalse(volunteerTable.save(this.volunteer1));
        assertTrue(volunteerTable.save(this.volunteer2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(volunteerTable.update(this.volunteer1));
        volunteerTable.save(this.volunteer2);
		final Volunteer updatedVolunteer2 = new Volunteer("BBBBBBBBBBBBBBBB", "Bari", Utils.buildDate(11, 12, 2012).get());
		assertTrue(volunteerTable.update(updatedVolunteer2));
        final Optional<Volunteer> foundVolunteer = volunteerTable.findByPrimaryKey(updatedVolunteer2.getCodiceFiscale());
        assertFalse(foundVolunteer.isEmpty());
        assertEquals(updatedVolunteer2.getDataIscrizione(), foundVolunteer.get().getDataIscrizione());
    }

    @Test
    public void deleteTest() {
        volunteerTable.save(this.volunteer1);
        assertTrue(volunteerTable.delete(this.volunteer1.getCodiceFiscale()));
        assertFalse(volunteerTable.delete(this.volunteer1.getCodiceFiscale()));
        assertTrue(volunteerTable.findByPrimaryKey(this.volunteer1.getCodiceFiscale()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        volunteerTable.save(this.volunteer1);
        volunteerTable.save(this.volunteer2);
        assertEquals(this.volunteer1, volunteerTable.findByPrimaryKey(this.volunteer1.getCodiceFiscale()).orElse(null));
        assertEquals(this.volunteer2, volunteerTable.findByPrimaryKey(this.volunteer2.getCodiceFiscale()).orElse(null));
    }

    @Test
    public void findAllTest() {
        volunteerTable.save(this.volunteer1);
        volunteerTable.save(this.volunteer2);
        assertIterableEquals(
            List.of(this.volunteer1, this.volunteer2),
            volunteerTable.findAll()
        );
    }
}
