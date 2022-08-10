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
import db.tables.BusinessTable;
import model.Business;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class BusinessTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static BusinessTable businessTable = new BusinessTable(connectionProvider.getMySQLConnection());

    final Business business1 = new Business(new BigDecimal("12345678911"), "az1", "415452", "via Giuseppe", "Ancona", "Marche", "320256", "mail1@nicemail.it", Optional.of("fax1"));
    final Business business2 = new Business(new BigDecimal("25369874102"), "az2", "743873", "via Garibaldi", "Ferrara", "Emilia-Romagna", "45678", "mail2@nicemail.it", Optional.empty());

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), businessTable.getTableName());
        businessTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), businessTable.getTableName()));
        assertTrue(businessTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(businessTable.save(this.business1));
        assertFalse(businessTable.save(this.business1));
        assertTrue(businessTable.save(this.business2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(businessTable.update(this.business1));
        businessTable.save(this.business2);
		final Business updatedBusiness2 = new Business(new BigDecimal("25369874102"), "az2", "743873", "via Garibaldi",
				"Ferrara", "Emilia-Romagna", "45678", "mail2@nicemail.it", Optional.of("fax2"));
		assertTrue(businessTable.update(updatedBusiness2));
        final Optional<Business> foundBusiness = businessTable.findByPrimaryKey(updatedBusiness2.getPartitaIVA());
        assertFalse(foundBusiness.isEmpty());
        assertEquals(updatedBusiness2.getFax(), foundBusiness.get().getFax());
    }

    @Test
    public void deleteTest() {
        businessTable.save(this.business1);
        assertTrue(businessTable.delete(this.business1.getPartitaIVA()));
        assertFalse(businessTable.delete(this.business1.getPartitaIVA()));
        assertTrue(businessTable.findByPrimaryKey(this.business1.getPartitaIVA()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        businessTable.save(this.business1);
        businessTable.save(this.business2);
        assertEquals(this.business1, businessTable.findByPrimaryKey(this.business1.getPartitaIVA()).orElse(null));
        assertEquals(this.business2, businessTable.findByPrimaryKey(this.business2.getPartitaIVA()).orElse(null));
    }

    @Test
    public void findAllTest() {
        businessTable.save(this.business1);
        businessTable.save(this.business2);
        assertIterableEquals(
            List.of(this.business1, this.business2),
            businessTable.findAll()
        );
    }
}
