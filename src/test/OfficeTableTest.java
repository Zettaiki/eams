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
import db.tables.OfficeTable;
import model.Office;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class OfficeTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static OfficeTable officeTable = new OfficeTable(connectionProvider.getMySQLConnection());

    final Office office1 = new Office("Bari", "via Garibaldi", "Puglia", "743873", "0142415245");
	final Office office2 = new Office("Roma", "via Giuseppe", "Lazio", "320256", "054542020");

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), officeTable.getTableName());
        officeTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), officeTable.getTableName()));
        assertTrue(officeTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(officeTable.save(this.office1));
        assertFalse(officeTable.save(this.office1));
        assertTrue(officeTable.save(this.office2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(officeTable.update(this.office1));
        officeTable.save(this.office2);
		final Office updatedOffice2 = new Office("Roma", "via Ciccio", "Lazio", "320256", "054542020");
		assertTrue(officeTable.update(updatedOffice2));
        final Optional<Office> foundOffice = officeTable.findByPrimaryKey(updatedOffice2.getCitt�());
        assertFalse(foundOffice.isEmpty());
        assertEquals(updatedOffice2.getIndirizzo(), foundOffice.get().getIndirizzo());
    }

    @Test
    public void deleteTest() {
        officeTable.save(this.office1);
        assertTrue(officeTable.delete(this.office1.getCitt�()));
        assertFalse(officeTable.delete(this.office1.getCitt�()));
        assertTrue(officeTable.findByPrimaryKey(this.office1.getCitt�()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        officeTable.save(this.office1);
        officeTable.save(this.office2);
        assertEquals(this.office1, officeTable.findByPrimaryKey(this.office1.getCitt�()).orElse(null));
        assertEquals(this.office2, officeTable.findByPrimaryKey(this.office2.getCitt�()).orElse(null));
    }

    @Test
    public void findAllTest() {
        officeTable.save(this.office1);
        officeTable.save(this.office2);
        assertIterableEquals(
            List.of(this.office1, this.office2),
            officeTable.findAll()
        );
    }
}
